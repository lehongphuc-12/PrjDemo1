package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;
import org.apache.commons.text.StringEscapeUtils;
import service.CategoryService;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FilterServlet", urlPatterns = {"/filters"})
public class FilterServlet extends HttpServlet {

    private CategoryService categoryService;
    private static final int SIZE_PAGE = 12;
    private static final Logger LOGGER = Logger.getLogger(FilterServlet.class.getName());

    private static final String ORDER_PRODUCTS_DEFAULT = "ORDER BY (0.7 * COALESCE(SIZE(p.orderDetailCollection), 0) + 0.3 * COALESCE(SIZE(p.productViewCollection), 0)) DESC";
    private static final String ORDER_NEWEST_PRODUCTS = "ORDER BY p.createdDate DESC";
    private static final String ORDER_PRODUCTS_ASC = "ORDER BY p.price ASC";
    private static final String ORDER_PRODUCTS_DESC = "ORDER BY p.price DESC";
    private static final Map<String, String> FILTER_LABELS = new LinkedHashMap<>() {{
        put("popular", "Bán chạy");
        put("newest", "Hàng mới");
        put("asc", "Giá từ thấp đến cao");
        put("desc", "Giá từ cao đến thấp");
    }};

    @Override
    public void init() throws ServletException {
        try {
            categoryService = new CategoryService();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize CategoryService", e);
            throw new ServletException("Initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        action = (action == null) ? "" : action;

        try {
            listProducts(request, response, action);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }

    private String getOrderQuery(String filter) {
        return switch (filter) {
            case "newest" -> ORDER_NEWEST_PRODUCTS;
            case "asc" -> ORDER_PRODUCTS_ASC;
            case "desc" -> ORDER_PRODUCTS_DESC;
            default -> ORDER_PRODUCTS_DEFAULT;
        };
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response, String action)
            throws SQLException, IOException {
        int id = 1;
        try {
            String idParam = request.getParameter("ID");
            if (idParam != null) id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid ID parameter", e);
        }

        int page = 1;
        try {
            String pageParam = request.getParameter("pageNumber");
            if (pageParam != null) page = Integer.parseInt(pageParam);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid page number", e);
        }

        String filter = request.getParameter("filter");
        filter = (filter == null) ? "" : filter;

        String search = request.getParameter("search");
        search = (search == null) ? "" : search;

        List<Product> products;
        int totalProducts;
        String typeOfProducts;

        switch (action) {
            case "cateGroup" -> {
                products = categoryService.getProductsByCategoryGroupId(id, SIZE_PAGE, page, getOrderQuery(filter));
                totalProducts = categoryService.countProductsByCategoryGroupId(id);
                typeOfProducts = categoryService.getCategoryGroupByID(id).getGroupName();
            }
            case "search" -> {
                products = categoryService.searchProductsByName(search, SIZE_PAGE, page, getOrderQuery(filter));
                totalProducts = categoryService.countProductsBySearch(search);
                typeOfProducts = search;
            }
            default -> {
                products = categoryService.getProductsByCategoryId(id, SIZE_PAGE, page, getOrderQuery(filter));
                totalProducts = categoryService.countProductsByCategoryId(id);
                typeOfProducts = categoryService.getCategoryByID(id).getCategoryName();
            }
        }

        int endPage = (totalProducts + SIZE_PAGE - 1) / SIZE_PAGE;

        PrintWriter out = response.getWriter();
        out.print(renderFilters(id, search, page, action, filter, totalProducts, typeOfProducts)
                + "<div class=\"list_products\" style=\"display: grid; grid-template-columns: repeat(4, 1fr); gap: 10px;\">" 
                + renderProducts(products,request) + "</div>"
                + renderPagination(id, search, page, endPage, action, filter));
    }

    private String renderFilters(int id, String search, int page, String action, String selectedFilter, int total, String typeOfProducts) {
        StringBuilder html = new StringBuilder();
        search = StringEscapeUtils.escapeEcmaScript(search);
        action = StringEscapeUtils.escapeEcmaScript(action);
        selectedFilter = StringEscapeUtils.escapeEcmaScript(selectedFilter);
        typeOfProducts = StringEscapeUtils.escapeHtml4(typeOfProducts);

        html.append(String.format("""
                                  <div class="ranking_criteria">
                                      <div class="number_of_products">
                                          <p>Hi\u1ec7n c\u00f3 <span class="text-primary">%d</span> s\u1ea3n ph\u1ea9m <span class="text-primary">%s</span></p>
                                      </div>
                                      <div class="criterias">
                                  """, total, typeOfProducts));

        for (String filter : FILTER_LABELS.keySet()) {
            String escapedFilter = StringEscapeUtils.escapeEcmaScript(filter);
            String filterLabel = StringEscapeUtils.escapeHtml4(FILTER_LABELS.get(filter));
            html.append(String.format("""
                                              <a href="javascript:void(0);" onclick="filter(%d, '%s', %d, '%s', '%s')" class='%s'>
                                                  <p>%s</p>
                                              </a>
                                      """,
                    id, search, page, action, escapedFilter,
                    escapedFilter.equals(selectedFilter) ? "text-primary" : "",
                    filterLabel));
        }
        html.append("    </div>\n</div>");
        return html.toString();
    }

    private String renderProducts(List<Product> products, HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        HttpSession session = request.getSession(false);

        for (Product p : products) {
            String imageURL = p.getProductImageCollection().isEmpty() 
                              ? "default-image.jpg" 
                              : p.getProductImageCollection().iterator().next().getImageURL();

            BigDecimal discount = p.getDiscountCollection().isEmpty() 
                                  ? BigDecimal.ZERO 
                                  : p.getDiscountCollection().iterator().next().getDiscountPercent();

            BigDecimal discountedPrice = p.getPrice()
                                          .multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)));

            double avgRating = p.getReviewCollection().isEmpty() ? -1 : p.getAverageRating();

            int userRole = (session != null && session.getAttribute("userRole") != null) 
                   ? (int) session.getAttribute("userRole") 
                   : 0;
            
            html.append(String.format("""
                <div class="product">
                    <div class="product_image">
                        <img src="/demo1/assets/images/productImages/%s" alt="%s">
                    </div>
                    <div class="product_info">
                        <div class="product_name text">
                            <p>%s</p>
                        </div>
                        <div class="product_price">
                """, imageURL, p.getProductName(), StringEscapeUtils.escapeHtml4(p.getProductName())));

            if (discount.compareTo(BigDecimal.ZERO) > 0) {
                html.append(String.format("""
                            <div class="price text-primary">
                                %,.0fđ
                            </div>
                            <div class="discount">
                                <div class="discount_price text-deleted">
                                    <del>%,.0fđ</del>
                                </div>
                                <div class="discount_tag">
                                    <small>GIẢM %d%%</small>
                                </div>
                            </div>
                    """, discountedPrice, p.getPrice(), discount.intValue()));
            } else {
                html.append(String.format("""
                            <div class="price text-primary">
                                %,.0fđ
                            </div>
                    """, p.getPrice()));
            }

            // Hiển thị số điểm rating nếu có review, nếu không thì báo "Chưa có đánh giá"
            html.append("<div class=\"product_rate text-warning\">");
            if (avgRating == -1) {
                html.append("<p class=\"no_rating\">Chưa có đánh giá</p>");
            } else {
                html.append(String.format("<p class=\"total_rating text\">%.1f sao</p>", avgRating));
            }
            html.append("</div>");

            html.append("""
                        </div>
                    </div>
            """);

            // Kiểm tra userRole để hiển thị giỏ hàng
            if (userRole != 0) {
                html.append("""
                    <div class="product_actions">
                        <a href="#">
                            <span class="material-icons-sharp">
                                add_shopping_cart
                            </span>
                        </a>
                    </div>
                """);
            }

            // Nút "Mua ngay"
            html.append("""
                    <div class="buy-now">
                        <a href="#">Mua ngay</a>
                    </div>
                </div>
            """);
        }

        return html.toString();
}






    private String renderPagination(int id, String search, int currentPage, int totalPages, String action, String filter) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"page_numbers\">\n");
        search = StringEscapeUtils.escapeEcmaScript(search);
        action = StringEscapeUtils.escapeEcmaScript(action);
        filter = StringEscapeUtils.escapeEcmaScript(filter);

        for (int i = 1; i <= totalPages; i++) {
            html.append("    <span class=\"page-item ").append(i == currentPage ? "active" : "").append("\">\n")
                    .append("        <a href=\"javascript:void(0);\" onclick=\"filter(")
                    .append(id).append(", '").append(search).append("', ").append(i).append(", '")
                    .append(action).append("', '").append(filter).append("')\">").append(i).append("</a>\n")
                    .append("    </span>\n");
        }
        html.append("</div>\n");
        return html.toString();
    }
}