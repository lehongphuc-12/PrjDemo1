package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order1;
import service.OrderService;

import java.io.IOException;
import java.util.logging.Logger;
import model.User;

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrderConfirmationServlet.class.getName());
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            LOGGER.warning("No order ID provided for order confirmation.");
            request.setAttribute("errorMessage", "Không tìm thấy mã đơn hàng.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            Order1 order = orderService.getOrderById(orderId);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            request.setAttribute("order", order);
            request.setAttribute("user", order);
            request.getRequestDispatcher("/views/order-confirmation.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid order ID format: " + orderIdParam);
            request.setAttribute("errorMessage", "Mã đơn hàng không hợp lệ.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Error fetching order for confirmation: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi tải thông tin đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}