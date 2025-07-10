package controller;

import LoginGoogle.GoogleUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import userDAO.UserDAO;

@WebServlet(name = "LoginGoogleHandler", urlPatterns = {"/LoginGoogleHandler"})
public class LoginGoogleHandler extends HttpServlet {

    private final UserDAO userDao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            System.err.println("[ERROR] Missing authorization code from Google.");
            response.sendRedirect("views/login.jsp");
            return;
        }

        String accessToken = GoogleUtils.getToken(code);
        if (accessToken == null) {
            System.err.println("[ERROR] Access token is null.");
            response.sendRedirect("views/login.jsp");
            return;
        }

        User googleUser = GoogleUtils.getUserInfo(accessToken);
        if (googleUser == null || googleUser.getEmail() == null) {
            System.err.println("[ERROR] Failed to get user info from Google.");
            response.sendRedirect("views/login.jsp");
            return;
        }
        
        System.out.println("[INFO] Logged in Google account: " + googleUser.getEmail());
        // Kiểm tra email trong database
        User user = userDao.findByEmailDAO(googleUser.getEmail());
        HttpSession session = request.getSession();

        if (user == null) {
            // Đăng nhập ngay nếu user đã có trong database
            user = new User();
        } 
        session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath()+"/products");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
