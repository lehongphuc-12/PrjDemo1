package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import model.CategoryGroup;
import model.Product;
import model.User;
import service.CategoryService;
import service.ProductService;

@WebServlet(name = "StoreServlet", urlPatterns = {"/store"})
public class StoreServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sellerIdStr = request.getParameter("sellerID");
        int sellerID;
        try {
            sellerID = Integer.parseInt(sellerIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID người bán không hợp lệ.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }

        // Lấy tất cả sản phẩm của seller
        List<Product> allProducts = productService.getProductsBySellerID(sellerID);
        if (allProducts == null || allProducts.isEmpty()) {
            request.setAttribute("errorMessage", "Không tìm thấy sản phẩm nào cho cửa hàng này.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }

        // Lọc sản phẩm mới
        List<Product> newProducts = allProducts.stream()
                .sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()))
                .limit(10)
                .collect(Collectors.toList());

        // Lọc sản phẩm bán chạy (dựa trên orderDetailCollection.size())
        List<Product> bestSellers = allProducts.stream()
                .sorted((p1, p2) -> Integer.compare(
                        p2.getOrderDetailCollection() != null ? p2.getOrderDetailCollection().size() : 0,
                        p1.getOrderDetailCollection() != null ? p1.getOrderDetailCollection().size() : 0))
                .limit(10)
                .collect(Collectors.toList());

        // Lọc sản phẩm khuyến mãi
        List<Product> promotions = allProducts.stream()
                .filter(p -> p.getDiscountCollection() != null && !p.getDiscountCollection().isEmpty())
                .limit(10)
                .collect(Collectors.toList());

        // Tính số lượng sản phẩm đã bán
        int soldProducts = allProducts.stream()
                .mapToInt(p -> p.getOrderDetailCollection() != null ? p.getOrderDetailCollection().size() : 0)
                .sum();
        int productCount = allProducts.size();

        List<CategoryGroup> listCategoryGroup = new CategoryService().getAllCategoryGroup();
        
        // Lấy thông tin seller từ sản phẩm đầu tiên
        User seller = allProducts.get(0).getSellerID();
        String sellerName = seller != null && seller.getFullName() != null ? seller.getFullName() : "Tên cửa hàng không xác định";
        String sellerPhone = seller != null && seller.getPhoneNumber() != null ? seller.getPhoneNumber() : "Chưa cung cấp số điện thoại";
        String sellerAddress = seller != null && seller.getAddress() != null ? seller.getAddress() : "Chưa cung cấp địa chỉ";

        // Đặt thuộc tính để truyền sang JSP
        request.setAttribute("sellerName", sellerName);
        request.setAttribute("sellerAddress", sellerAddress);
        request.setAttribute("sellerPhone", sellerPhone);
        request.setAttribute("soldProducts", soldProducts);
        request.setAttribute("productCount", productCount);
        request.setAttribute("newProducts", newProducts);
        request.setAttribute("allProducts", allProducts);
        request.setAttribute("bestSellers", bestSellers);
        request.setAttribute("promotions", promotions);
        request.setAttribute("listCategoryGroup", listCategoryGroup);
        // Chuyển tiếp đến store_page.jsp
        request.getRequestDispatcher("/views/store_page.jsp").forward(request, response);
    }
}