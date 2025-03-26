
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import model.CategoryGroup;
import model.Product;
import service.CategoryService;
import service.ProductService;



@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {
    
    private ProductService productService;
    
    @Override
    public void init(){
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        try{
        switch(action){
             case "listProductsBySeller":
                listProductsBySeller(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case "restore":
                restoreProduct(request, response);
                break;
            default:
                listAllProducts(request,response);
                break;
        }
        }catch (SQLException ex) {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    private void listAllProducts(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    
        List<CategoryGroup> listCategoryGroup = new CategoryService().getAllCategoryGroup();
        Map<CategoryGroup, List<Product>> listCategoryGroupProducts = productService.listCategoryGroupProductsDAO(10);
        List<Product> listPopularProducts = productService.listTopPopularProductsDAO(15);
        
        
        request.setAttribute("listCategoryGroup", listCategoryGroup);
        request.setAttribute("listCategoryGroupProducts", listCategoryGroupProducts);
        request.setAttribute("listPopularProducts", listPopularProducts);
        

        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }
    private void listProductsBySeller(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sellerIdStr = request.getParameter("sellerId");
        String pageStr = request.getParameter("page");

        try {
            int sellerId = Integer.parseInt(sellerIdStr);
            int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1; // Mặc định là trang 1
            int pageSize = 6; // Số sản phẩm mỗi trang

            // Lấy danh sách sản phẩm theo trang
            List<Product> productList = productService.getProductsBySellerId(sellerId, page, pageSize);
            // Đếm tổng số sản phẩm
            long totalProducts = productService.countProductsBySellerId(sellerId);
            // Tính tổng số trang
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            // Truyền dữ liệu sang JSP
            request.setAttribute("sellerId", sellerId);
            request.setAttribute("productList", productList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            if (productList == null || productList.isEmpty()) {
                request.setAttribute("message", "Không tìm thấy sản phẩm nào của người bán này.");
            }

            request.setAttribute("part", "productList.jsp");
            request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID người bán không hợp lệ");
            request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            request.setAttribute("error", "Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
        }
    }
    
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        String sellerIdStr = request.getParameter("sellerId");

        try {
            int productId = Integer.parseInt(productIdStr);
            int sellerId = Integer.parseInt(sellerIdStr);

            productService.deleteProduct(productId);

            response.sendRedirect(request.getContextPath() + "/products?action=listProductsBySeller&sellerId=" + sellerId);

        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID sản phẩm hoặc người bán không hợp lệ");
            listProductsBySeller(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi xóa sản phẩm: " + e.getMessage());
            listProductsBySeller(request, response);
        }
    }

    private void restoreProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        String sellerIdStr = request.getParameter("sellerId");

        try {
            int productId = Integer.parseInt(productIdStr);
            int sellerId = Integer.parseInt(sellerIdStr);

            productService.restoreProduct(productId);
            response.sendRedirect(request.getContextPath() + "/products?action=listProductsBySeller&sellerId=" + sellerId);

        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID sản phẩm hoặc người bán không hợp lệ");
            listProductsBySeller(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi khôi phục sản phẩm: " + e.getMessage());
            listProductsBySeller(request, response);
        }
    }

}
