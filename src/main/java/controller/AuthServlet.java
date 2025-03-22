package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import model.Role;
import model.User;
import service.AuthService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/logins", "/register", "/logout"})
public class AuthServlet extends HttpServlet {
    private final AuthService authservice = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/logout".equals(action)) {
            handleLogout(request, response);
        } else {
            String email = "";
            String password = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("remember_email".equals(cookie.getName())) {
                        email = cookie.getValue();
                    }
                    if ("remember_password".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }
            request.setAttribute("rememberEmail", email);
            request.setAttribute("rememberPassword", password);
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/logins");
        } else {
            switch (action) {
                case "/logins" -> handleLogin(request, response);
                case "/register" -> handleRegister(request, response);
                default -> response.sendRedirect(request.getContextPath() + "/logins");
            }
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email-login");
        String password = request.getParameter("password-login");
        String remember = request.getParameter("remember_me");

        try {
            User user = authservice.findByEmailAndPassword(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                setLoginCookies(response, email, password, "on".equals(remember));
                String redirectUrl = getRedirectUrlByRole(user.getRoleID().getRoleID());
                response.sendRedirect(request.getContextPath() + redirectUrl);
            } else {
                String errorMessage = "Sai email hoặc mật khẩu!";
                request.setAttribute("errorMessage", errorMessage);
                request.setAttribute("rememberEmail", email);
                request.setAttribute("rememberPassword", password);
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);

            }
        } catch (Exception e) {
            request.setAttribute("error", "Đã xảy ra lỗi, vui lòng thử lại!");
            request.setAttribute("rememberEmail", email);
            request.setAttribute("rememberPassword", password);
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User googleUser = (User) session.getAttribute("googleUser");

        String fullName = request.getParameter("full-name-register");
        String email = request.getParameter("email-register");
        String password = request.getParameter("password-register");
        String passwordConfirm = request.getParameter("password_confirmation-register");
        String phoneNumber = request.getParameter("phone-register");
        String address = request.getParameter("address-register");

        try {
            User existingUser = authservice.findByEmail(email);
            if (existingUser != null) {
                request.setAttribute("error", "Email đã tồn tại!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                request.setAttribute("error", "Email không hợp lệ!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
            if (googleUser == null) {
                if (password == null || password.length() < 6 || !password.equals(passwordConfirm)) {
                    request.setAttribute("error", "Mật khẩu không hợp lệ hoặc không khớp!");
                    request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                    return;
                }
            }
            if (phoneNumber == null || !phoneNumber.matches("^[0-9]{10}$")) {
                request.setAttribute("error", "Số điện thoại phải là 10 chữ số!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("error", "Họ và tên không được để trống!");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            Role role = authservice.findRoleById(3); // Customer
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPhoneNumber(phoneNumber);
            newUser.setAddress(address);
            newUser.setRoleID(role);
            newUser.setCreatedAt(new Date());
            newUser.setPassword(googleUser != null ? "" : password); // Lưu plain text

            authservice.create(newUser);
            session.setAttribute("user", newUser);
            session.removeAttribute("user");
            setLoginCookies(response, email, password, true);
            response.sendRedirect(request.getContextPath() + "/logins");
        } catch (Exception e) {
            request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        String redirectUrl = "/products";
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRoleID().getRoleID() == 1) {
                redirectUrl = "/logins";
            }
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }

    private void setLoginCookies(HttpServletResponse response, String email, String password, boolean remember) {
        Cookie emailCookie = new Cookie("remember_email", email);
        emailCookie.setMaxAge(60 * 60); // 1 giờ
        emailCookie.setHttpOnly(true);
        emailCookie.setSecure(true);
        response.addCookie(emailCookie);

        if (remember && password != null && !password.isEmpty()) {
            Cookie passwordCookie = new Cookie("remember_password", password);
            passwordCookie.setMaxAge(60 * 60); // 1 giờ
            passwordCookie.setHttpOnly(true);
            passwordCookie.setSecure(true);
            response.addCookie(passwordCookie);
        }
    }

    private String getRedirectUrlByRole(int roleID) {
        return switch (roleID) {
            case 1 -> "/admin";
            case 2 -> "/views/sellerPage.jsp";
            case 3 -> "/products";
            default -> "/products";
        };
    }
}