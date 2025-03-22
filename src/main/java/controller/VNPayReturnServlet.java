package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order1;
import model.User;
import service.CartService;
import service.EmailService;
import service.OrderService;
import service.VNPayService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/vnpay-return")
public class VNPayReturnServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(VNPayReturnServlet.class.getName());
    private VNPayService vnpayService;
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        vnpayService = new VNPayService();
        cartService = new CartService();
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            LOGGER.warning("User not logged in during VNPay return.");
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        User user = (User) session.getAttribute("user");
        String productIdsParam = (String) session.getAttribute("productIds");

        try {
            // Xác thực phản hồi từ VNPay
            if (vnpayService.validateResponse(request)) {
                String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
                String vnp_TxnRef = request.getParameter("vnp_TxnRef");
                int orderId = Integer.parseInt(vnp_TxnRef);

                LOGGER.info("VNPay response for order #" + orderId + ": ResponseCode=" + vnp_ResponseCode);

                if ("00".equals(vnp_ResponseCode)) {
                    // Thanh toán thành công
                    LOGGER.info("Payment successful for order #" + orderId);

                    // Xóa các sản phẩm đã chọn trong giỏ hàng
                    if (productIdsParam != null && !productIdsParam.isEmpty()) {
                        List<Integer> productIds = parseProductIds(productIdsParam);
                        cartService.clearSelectedItems(user, productIds);
                        LOGGER.info("Cleared selected items from cart for user: " + user.getUserID());
                        session.removeAttribute("productIds");
                    } else {
                        LOGGER.warning("productIds not found in session for order #" + orderId);
                    }

                    // Lấy thông tin đơn hàng để gửi email
                    Order1 order = orderService.getOrderById(orderId);
                    if (order == null) {
                        LOGGER.severe("Order #" + orderId + " not found in database.");
                        request.setAttribute("errorMessage", "Không tìm thấy đơn hàng #" + orderId);
                        request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                        return;
                    }

                    // Gửi email xác nhận đơn hàng
                    String userEmail = user.getEmail();
                    String username = user.getFullName();
                    try {
                        EmailService.sendOrderConfirmationEmail(userEmail, username, order);
                        LOGGER.info("Confirmation email sent to " + userEmail + " for order #" + orderId);
                    } catch (Exception e) {
                        LOGGER.severe("Failed to send confirmation email to " + userEmail + ": " + e.getMessage());
                        // Không làm gián đoạn luồng chính nếu gửi email thất bại
                    }

                    // Xóa thông tin giảm giá nếu có
                    session.removeAttribute("discountAmount");

                    // Chuyển hướng đến trang xác nhận đơn hàng
                    response.sendRedirect(request.getContextPath() + "/order-confirmation?orderId=" + orderId);
                } else {
                    // Thanh toán thất bại
                    LOGGER.warning("Payment failed for order #" + orderId + ". ResponseCode: " + vnp_ResponseCode);
                    request.setAttribute("errorMessage", "Thanh toán thất bại: Mã lỗi " + vnp_ResponseCode);
                    request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
                }
            } else {
                // Xác thực thất bại
                LOGGER.warning("VNPay response validation failed.");
                request.setAttribute("errorMessage", "Xác thực thanh toán không thành công.");
                request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.severe("Encoding error during VNPay response validation: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi mã hóa khi xác thực phản hồi từ VNPay: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid order ID format in vnp_TxnRef: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi định dạng mã đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Unexpected error during VNPay return processing: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi xử lý phản hồi từ VNPay: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    // Phương thức để phân tích productIds từ session
    private List<Integer> parseProductIds(String productIdsParam) {
        List<Integer> productIds = new ArrayList<>();
        if (productIdsParam == null || productIdsParam.trim().isEmpty()) {
            LOGGER.warning("productIds parameter is empty or null.");
            return productIds;
        }

        String[] productIdsArray = productIdsParam.split(",");
        for (String id : productIdsArray) {
            try {
                productIds.add(Integer.parseInt(id.trim()));
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid product ID in productIds: " + id);
            }
        }
        return productIds;
    }
}