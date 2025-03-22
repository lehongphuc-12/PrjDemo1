package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Date;
import java.util.List;
import model.Product;
import model.Review;
import model.User;
import orderDAO.OrderDAO;
import productDAO.ProductDAO;
import reviewDAO.ReviewDAO;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    private ReviewDAO reviewDAO = new ReviewDAO(); // DAO để lấy danh sách đánh giá

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    System.out.println("Received action: " + action);

    if ("createReview".equals(action)) {
        try {
            int productID = Integer.parseInt(request.getParameter("productID"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            System.out.println("Received productID: " + productID);
            System.out.println("Received rating: " + rating);
            System.out.println("Received comment: " + comment);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Lỗi: Người dùng chưa đăng nhập.");
                return;
            }

            Product product = new ProductDAO().findById(productID);
            if (product == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Lỗi: Không tìm thấy sản phẩm với ID: " + productID);
                return;
            }

            // Lưu đánh giá vào database
            Review review = new Review(rating, comment, new Date(), product, user);
            new ReviewDAO().create(review);

            // Lấy danh sách đánh giá mới nhất
            List<Review> reviews = new ReviewDAO().findByProductId(productID);

            // Render danh sách đánh giá thành HTML
            String renderedHTML = renderReviewsHTML(reviews);

            // Gửi HTML về AJAX
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(renderedHTML);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Lỗi: Dữ liệu không hợp lệ.");
            e.printStackTrace();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi: Đã xảy ra lỗi khi xử lý đánh giá.");
            e.printStackTrace();
        }
    }
}


    // Hàm tạo HTML từ danh sách review
    private String renderReviewsHTML(List<Review> reviews) {
        StringBuilder html = new StringBuilder();
        
        if (reviews.isEmpty()) {
            html.append("<p>Chưa có đánh giá nào cho sản phẩm này.</p>");
        } else {
            for (Review review : reviews) {
                html.append("<div class='review'>")
                    .append("<p><strong>Người dùng:</strong> ")
                    .append(review.getUserID() != null ? review.getUserID().getFullName() : "Ẩn danh")
                    .append("</p>")
                    .append("<p><strong>Đánh giá:</strong> <span class='rating-stars'>");

                // Hiển thị số sao
                for (int i = 1; i <= 5; i++) {
                    if (i <= review.getRating()) {
                        html.append("<span class='fa fa-star'></span>");  // Sao vàng
                    } else {
                        html.append("<span class='fa fa-star-o'></span>");  // Sao rỗng
                    }
                }
                html.append("</span></p>")
                    .append("<p><strong>Nhận xét:</strong> ")
                    .append(review.getComment() != null ? review.getComment() : "Không có nhận xét")
                    .append("</p>")
                    .append("<p><strong>Ngày:</strong> ")
                    .append(review.getReviewDate())
                    .append("</p>")
                    .append("</div>");
            }
        }

        return html.toString();
    }
}
