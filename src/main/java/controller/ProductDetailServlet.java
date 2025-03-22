package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import model.Product;
import model.ProductImage;
import model.Review;
import model.User;
import service.ProductService;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/detail"})
public class ProductDetailServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ProductDetailServlet.class.getName());
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("productID");
        int productId = 105; // Giá trị mặc định
        if (idStr != null && !idStr.isEmpty()) {
            try {
                productId = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                logger.warning("Invalid productID: " + idStr);
                request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
                request.getRequestDispatcher("product/error.jsp").forward(request, response);
                return;
            }
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            logger.warning("Product not found for ID: " + productId);
            request.setAttribute("errorMessage", "Sản phẩm không tồn tại!");
            request.getRequestDispatcher("product/error.jsp").forward(request, response);
            return;
        }

        List<ProductImage> images = productService.getProductImages(productId);
        if (images == null) {
            images = Collections.emptyList();
        }

        // Lấy sellerID từ đối tượng User trong Product
        int sellerID = product.getSellerID().getUserID();

        // Lấy danh sách sản phẩm của shop
        List<Product> shopProducts = productService.getProductsBySellerID(sellerID);
        if (shopProducts == null) {
            shopProducts = Collections.emptyList();
        }

        // Tính số lượng sản phẩm của shop
        int shopProductCount = shopProducts.size();
        request.setAttribute("shopProductCount", shopProductCount);

        // Tính trung bình đánh giá của shop
        double totalRating = 0.0;
        int totalReviews = 0;
        for (Product p : shopProducts) {
            List<Review> productReviews = productService.getReviewsByProductId(p.getProductID());
            if (productReviews != null && !productReviews.isEmpty()) {
                for (Review review : productReviews) {
                    totalRating += review.getRating();
                    totalReviews++;
                }
            }
        }
        double averageShopRating = (totalReviews > 0) ? totalRating / totalReviews : 0.0;
        averageShopRating = Math.round(averageShopRating * 10.0) / 10.0; // Làm tròn 1 chữ số thập phân
        request.setAttribute("averageShopRating", averageShopRating);

        Map<Integer, ProductImage> productImages = productService.getProductImage(sellerID);
        List<Product> highProducts = productService.getHighStockProducts(10);
        Map<Integer, ProductImage> highProductImages = productService.getHighStockProductImages(10);
        Map<Integer, ProductImage> highSimilarImages = productService.getSimilarProductsImage(productId);
        List<Product> similarProducts = productService.getSimilarProducts(productId);
        List<Review> reviews = productService.getReviewsByProductId(productId);
        if (reviews == null) {
            reviews = Collections.emptyList();
        }

        // Đặt các thuộc tính vào request
        request.setAttribute("product", product);
        request.setAttribute("productImage", images);
        request.setAttribute("products", shopProducts);
        request.setAttribute("productImages", productImages);
        request.setAttribute("highProductImages", highProductImages);
        request.setAttribute("highProducts", highProducts);
        request.setAttribute("highSimilarImages", highSimilarImages);
        request.setAttribute("similarProducts", similarProducts);
        request.setAttribute("reviews", reviews);

        // Tính toán ngày giao hàng
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM", new Locale("vi", "VN"));
        String currentDate = sdf.format(cal.getTime()).replace(" ", " Th");
        cal.add(Calendar.DAY_OF_YEAR, 3);
        String futureDate3Day = sdf.format(cal.getTime()).replace(" ", " Th");
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 2);
        String startDate4Day = sdf.format(cal.getTime()).replace(" ", " Th");
        cal.add(Calendar.DAY_OF_YEAR, 5);
        String endDate4Day = sdf.format(cal.getTime()).replace(" ", " Th");

        request.setAttribute("currentShippingDate", currentDate);
        request.setAttribute("futureShippingDate", futureDate3Day);
        request.setAttribute("startBulkyShippingDate", startDate4Day);
        request.setAttribute("endBulkyShippingDate", endDate4Day);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("views/product_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productID");
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");

        int productId;
        int rating;
        try {
            productId = Integer.parseInt(productIdStr);
            rating = Integer.parseInt(ratingStr);
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5");
            }
        } catch (NumberFormatException e) {
            logger.warning("Invalid input: productID=" + productIdStr + ", rating=" + ratingStr + " - " + e.getMessage());
            request.setAttribute("errorMessage", "Dữ liệu đánh giá không hợp lệ (sai định dạng số).");
            doGet(request, response);
            return;
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid input: productID=" + productIdStr + ", rating=" + ratingStr + " - " + e.getMessage());
            request.setAttribute("errorMessage", "Dữ liệu đánh giá không hợp lệ (rating phải từ 1 đến 5).");
            doGet(request, response);
            return;
        }

        Integer userID = (Integer) request.getSession().getAttribute("userID");
        if (userID == null) {
            logger.warning("User not logged in.");
            request.setAttribute("errorMessage", "Vui lòng đăng nhập để gửi đánh giá.");
            doGet(request, response);
            return;
        }

        Review review = new Review();
        review.setProductID(productService.getProductById(productId));
        User user = new User();
        user.setUserID(userID);
        review.setUserID(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(new Date());

        productService.createReview(review);

        response.sendRedirect("detail?productID=" + productId);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to handle product detail requests";
    }
}