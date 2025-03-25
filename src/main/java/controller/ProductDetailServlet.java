package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryGroup;
import model.Product;
import model.Review;
import model.User;
import service.CategoryService;
import service.ProductService;
import service.ReviewService;
import service.UserService;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/detail"})
public class ProductDetailServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ProductDetailServlet.class.getName());
    private final ProductService productService = new ProductService();

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch(action){
            case "review":
                
                break;
            default:
                viewDetailedProduct(request,response);
                break;
        }
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
    
    
    private void viewDetailedProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        // Lấy và kiểm tra productID
        String idStr = request.getParameter("productID");
        int productId = 105; // Giá trị mặc định

        if (idStr != null && !idStr.isEmpty()) {
            try {
                productId = Integer.parseInt(idStr);
                if (productId <= 0) {
                    logger.log(Level.WARNING, "Invalid productID: {0}", productId);
                    request.setAttribute("errorMessage", "ID sản phẩm phải lớn hơn 0.");
                    request.getRequestDispatcher("views/error.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid productID format: {0}", idStr);
                request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
                return;
            }
        }

        try {
            // Lấy thông tin sản phẩm
            Product product = productService.getProductById(productId);
            if (product == null) {
                logger.log(Level.WARNING, "Product not found for ID: {0}", productId);
                request.setAttribute("errorMessage", "Sản phẩm không tồn tại!");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
                return;
            }

            // Lấy thông tin người bán
            User seller = new UserService().getSellerByProductID(productId);
            if (seller == null) {
                logger.log(Level.WARNING, "Seller not found for product ID: {0}", productId);
                request.setAttribute("errorMessage", "Không tìm thấy thông tin người bán.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
                return;
            }
            int sellerID = seller.getUserID();


            // Tính điểm trung bình của cửa hàng (di chuyển vào service để tối ưu)
            double averageShopRating = productService.getAverageRatingBySellerId(sellerID);
            request.setAttribute("averageShopRating", averageShopRating);

           List<Product> sellerProducts = productService.getProductsBySellerID(sellerID);

            List<Product> similarProducts = productService.getSimilarProducts(productId);
            if (similarProducts == null) {
                similarProducts = Collections.emptyList();
            }

            List<Review> reviews = productService.getReviewsByProductId(productId);
            if (reviews == null) {
                reviews = Collections.emptyList();
            }
            List<CategoryGroup> listCategoryGroup = new CategoryService().getAllCategoryGroup();
            
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            boolean hasReviewed = false;
            
            if(user != null){
                hasReviewed = new ReviewService().hasReviewed(user.getUserID(), productId);
            }
           

            // Gán các thuộc tính cho request
            request.setAttribute("product", product);
            request.setAttribute("sellerProducts", sellerProducts);
            request.setAttribute("similarProducts", similarProducts);
            request.setAttribute("reviews", reviews);
            request.setAttribute("listCategoryGroup", listCategoryGroup);
            request.setAttribute("hasReviewed", hasReviewed);

            // Xử lý ngày giờ với java.time
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'Th'MM", new Locale("vi", "VN"));

            String currentDate = today.format(formatter);
            String futureDate3Day = today.plusDays(3).format(formatter);
            String startDate4Day = today.plusDays(2).format(formatter);
            String endDate4Day = today.plusDays(7).format(formatter);

            request.setAttribute("currentShippingDate", currentDate);
            request.setAttribute("futureShippingDate", futureDate3Day);
            request.setAttribute("startBulkyShippingDate", startDate4Day);
            request.setAttribute("endBulkyShippingDate", endDate4Day);

            // Chuyển hướng đến trang chi tiết sản phẩm
            request.getRequestDispatcher("views/product_detail.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            logger.log(Level.SEVERE, "Unexpected error while loading product data: {0}", e.getMessage());
            request.setAttribute("errorMessage", "Có lỗi bất ngờ xảy ra.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }
}