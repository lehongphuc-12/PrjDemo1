package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryGroup;
import model.Product;
import service.CategoryService;
 

@WebServlet(name = "CategoryServlet", urlPatterns = {"/cates"})
public class CategoryServlet extends HttpServlet {
    
    private CategoryService categoryService;
    private final int SIZE_PAGE = 12;
    private final String ORDER_PRODUCTS = "ORDER BY (0.7 * COALESCE(SIZE(p.orderDetailCollection), 0) + 0.3 * COALESCE(SIZE(p.productViewCollection), 0)) DESC";

    public void init() {
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            listProducts(request, response, action);
            return; // ✅ Dừng sau khi gọi listProducts để tránh lỗi forward()
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response, String action)
            throws SQLException, IOException, ServletException {

        int ID = 1;
        try {
            String id = request.getParameter("ID");
            if (id != null && !id.trim().isEmpty()) {
                ID = Integer.parseInt(id);
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.WARNING, "Invalid ID format: {0}", request.getParameter("ID"));
        }

        int page = 1;
        try {
            String pageNumber = request.getParameter("pageNumber");
            if (pageNumber != null && !pageNumber.trim().isEmpty()) {
                page = Integer.parseInt(pageNumber);
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.WARNING, "Invalid pageNumber format: {0}", request.getParameter("pageNumber"));
        }

        String search = request.getParameter("searchbar");
        if (search == null) {
            search = "";
        }

        List<Product> listPro = null;
        int totalProducts = 0;
        String typeOfProducts = "";

        switch (action) {
            case "cateGroup" -> {
                listPro = categoryService.getProductsByCategoryGroupId(ID, SIZE_PAGE, page, ORDER_PRODUCTS);
                totalProducts = categoryService.countProductsByCategoryGroupId(ID);
                typeOfProducts = categoryService.getCategoryGroupByID(ID).getGroupName();
            }
            case "search" -> {
                listPro = categoryService.searchProductsByName(search, SIZE_PAGE, page, ORDER_PRODUCTS);
                totalProducts = categoryService.countProductsBySearch(search);
                typeOfProducts = search;
                request.setAttribute("search", search);
            }
            default -> {
                listPro = categoryService.getProductsByCategoryId(ID, SIZE_PAGE, page, ORDER_PRODUCTS);
                totalProducts = categoryService.countProductsByCategoryId(ID);
                typeOfProducts = categoryService.getCategoryByID(ID).getCategoryName();
            }
        }

        List<CategoryGroup> listCategoryGroup = categoryService.getAllCategoryGroup();
        int endPage = (int) Math.ceil((double) totalProducts / SIZE_PAGE); // ✅ Tối ưu tính toán endPage

        request.setAttribute("listPro", listPro);
        request.setAttribute("listCategoryGroup", listCategoryGroup);
        request.setAttribute("endPage", endPage);
        request.setAttribute("page", page);
        request.setAttribute("action", action);
        request.setAttribute("ID", ID);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("typeOfProducts", typeOfProducts);

        request.getRequestDispatcher("/views/search.jsp").forward(request, response);
    }
}
