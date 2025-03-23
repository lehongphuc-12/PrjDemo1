package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.SellerRegistrationService;

import java.io.IOException;

@WebServlet("/SellerRegistrationServlet")
public class SellerRegistrationServlet extends HttpServlet {
    private SellerRegistrationService service;

    @Override
    public void init() throws ServletException {
        service = new SellerRegistrationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || (user.getRoleID() == null || user.getRoleID().getRoleID() != 3)) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        // Đặt user vào request attribute để JSP sử dụng
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/registerseller.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || (user.getRoleID() == null || user.getRoleID().getRoleID() != 3)) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        String shopName = request.getParameter("shopName");
        if (shopName == null || shopName.trim().isEmpty()) {
            request.setAttribute("message", "Tên cửa hàng không được để trống!");
            request.setAttribute("user", user); // Đặt lại user để JSP sử dụng
            request.getRequestDispatcher("/views/registerseller.jsp").forward(request, response);
            return;
        }
        if (shopName.length() > 255) {
            request.setAttribute("message", "Tên cửa hàng không được vượt quá 255 ký tự!");
            request.setAttribute("user", user); // Đặt lại user để JSP sử dụng
            request.getRequestDispatcher("/views/registerseller.jsp").forward(request, response);
            return;
        }

        try {
            service.createRequest(user, shopName);
            request.setAttribute("message", "Yêu cầu đăng ký đã được gửi thành công!");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi gửi yêu cầu: " + e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("user", user); // Đặt lại user để JSP sử dụng
        request.getRequestDispatcher("/views/registerseller.jsp").forward(request, response);
    }
}