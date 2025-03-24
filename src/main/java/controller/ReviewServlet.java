package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import model.Product;
import model.Review;
import model.User;
import org.apache.commons.text.StringEscapeUtils;
import productDAO.ProductDAO;
import reviewDAO.ReviewDAO;
import service.OrderService;
import service.ReviewService;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    private  ReviewService reviewService;
    private OrderService orderService;
    
    @Override
    public void init(){
        orderService = new OrderService();
        reviewService = new ReviewService();
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    System.out.println("Received action: " + action);

    switch(action){
        
        case "createReview" -> createReview(request,response);
        
        case "deleteReview" ->deleteReview(request,response);
        
        case "updateReview" ->updateReview(request,response);
        
        default -> {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Lỗi: có lỗi không mong muốn xảy ra, vui lòng thử lại");
        }
    }
}


    // Hàm tạo HTML từ danh sách review
     public  String renderReviewsHTML(List<Review> reviews, User currentUser) {
        if (reviews.isEmpty()) {
            return "<p>Chưa có đánh giá nào cho sản phẩm này.</p>";
        }

        StringBuilder html = new StringBuilder();

        for (Review review : reviews) {
            boolean isUserReview = (currentUser != null && review.getUserID() != null) 
                                    && (review.getUserID().getUserID().equals(currentUser.getUserID()));

            String userName = Objects.toString(
                review.getUserID() != null ? review.getUserID().getFullName() : "Ẩn danh", "Ẩn danh"
            );
            
            String reviewDate = Objects.toString(review.getReviewDate(), "Không có ngày");
            // Tạo HTML phần đánh giá
            html.append("""
                <div class='review'>
                    <div class='review-header'>
                        <span class='user-avatar-review'>
                            <img src='/demo1/assets/images/userImages/user-avatar.jpg' alt='User Avatar'>
                        </span>
                        <p>%s</p>
                    </div>
                    <p class='text-muted' style='font-size: 12px'>%s</p>
                    <p>
                        <span class='rating-stars'>
                """.formatted(userName, reviewDate));

            // Hiển thị số sao
            for (int i = 1; i <= 5; i++) {
                html.append(i <= review.getRating()
                    ? "<span class='fa fa-star'></span>"  // Sao vàng
                    : "<span class='fa fa-star-o'></span>" // Sao rỗng
                );
            }

            html.append("""
                        </span>
                    </p>
                    <p><strong>Nhận xét:</strong> %s</p>
                """.formatted(Objects.toString(review.getComment(), "Không có nhận xét")));

            // Nếu là người dùng hiện tại -> Thêm nút "Chỉnh sửa" & "Xóa"
            if (isUserReview) {
                
                String escapedComment = StringEscapeUtils.escapeHtml4(review.getComment());
                html.append("""
                    <div class='review-action' style='font-size: 12px'>
                        <input type="hidden" name="productID" id="productID" value="%d">
                        <input type="hidden" name="rating" id="rating" value="%d">
                        <input type="hidden" name="comment" id="comment" value="%s">    
                        <a href='#' data-reviewid=%d class='text-muted update-review'>Chỉnh sửa đánh giá</a> | 
                        <a href='#' data-reviewid=%d class='text-muted delete-review'>Xóa</a>
                    </div>
                """.formatted(review.getProductID().getProductID(),review.getRating(),escapedComment,review.getReviewID(), review.getReviewID()));
            }

            html.append("</div>"); // Kết thúc phần review
        }

        return html.toString();
    }
     
    public void createReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            System.out.println("Received productID: " + productID);
            System.out.println("Received rating: " + rating);
            System.out.println("Received comment: " + comment);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            boolean idPurchased = true;
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Vui lòng đăng nhập để đánh giá sản phẩm");
                return;
            }
            else{
                idPurchased = orderService.hasPurchased(user.getUserID(), productID);
                    if(!idPurchased){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Vui lòng mua sản phẩm để tiếp tục đánh giá");
                    return;
            }
            }

            Product product = new ProductDAO().findById(productID);
            if (product == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Lỗi: Không tìm thấy sản phẩm với ID: " + productID);
                return;
            }

            // Lưu đánh giá vào database
            Review review = new Review(rating, comment, new Date(), product, user);
            reviewService.createReview(review);

            // Lấy danh sách đánh giá mới nhất
            List<Review> reviews = reviewService.findReviewsByProductID(productID);

            // Render danh sách đánh giá thành HTML
            String renderedHTML = renderReviewsHTML(reviews,user);

            // Gửi HTML về AJAX
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(renderedHTML);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Lỗi: Dữ liệu không hợp lệ.");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi: Đã xảy ra lỗi khi xử lý đánh giá.");
        }
    }     
 


    public void deleteReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
            String reviewID = request.getParameter("reviewID");
            String productID = request.getParameter("productID");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int reID;
            int proID;
            if(reviewID != null){
                reID = Integer.parseInt(reviewID);
                proID = Integer.parseInt(productID);
                try{
    //          XOA REVIEW
                reviewService.deleteReview(reID);
                
                 // Lấy danh sách đánh giá mới nhất
                List<Review> reviews = reviewService.findReviewsByProductID(proID);

                // Render danh sách đánh giá thành HTML
                String renderedHTML = renderReviewsHTML(reviews,user);

                // Gửi HTML về AJAX
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(renderedHTML);
                
                }catch(Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Lỗi: Dữ liệu không hợp lệ.");
                    return;
                }
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Lỗi: Dữ liệu không hợp lệ.");
                return;
            }    
    }
    
    public void updateReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        String reviewID = request.getParameter("reviewID");
        String productID = request.getParameter("productID");
        String rating = request.getParameter("rating");
        String comment = request.getParameter("comment");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if(reviewID != null){
            int reID = Integer.parseInt(reviewID);
            int proID = Integer.parseInt(productID);
            int rate = Integer.parseInt(rating);
            Product product = new Product(proID);
            Review review = new Review(reID,rate,comment,new Date(),product,user);
            
            try{
    //          XOA REVIEW
                reviewService.updateReview(review);
                
                 // Lấy danh sách đánh giá mới nhất
                List<Review> reviews = reviewService.findReviewsByProductID(proID);

                // Render danh sách đánh giá thành HTML
                String renderedHTML ="";
                try{
                 renderedHTML = renderReviewsHTML(reviews,user);
                }catch(Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Lỗi: ko thể render dữ liệu.");
                    return;
                }
                // Gửi HTML về AJAX
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(renderedHTML);
                
                }catch(Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Lỗi: Review không đúng định dạng.");
                    return;
                }
            
        }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Lỗi: khong co reviewID.");
                return;
            }      
    }
}
