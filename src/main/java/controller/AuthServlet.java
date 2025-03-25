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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;
import service.AuthService;

@WebServlet(name = "LoginServlet", urlPatterns = {"/logins", "/register", "/logout","/checkEmail","/reset-password"})
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
                case "/checkEmail" -> checkEmail(request,response);
                case "/reset-password" -> resetPassword(request,response);
                default -> response.sendRedirect(request.getContextPath() + "/logins");
            }
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email-login");
        String password = request.getParameter("password-login");
        String remember = request.getParameter("remember_me");
        HttpSession session = request.getSession();
        
        
        int fail = -1;
        if(session.getAttribute("failedAttempts")==null){
            session.setAttribute("failedAttempts", 1);
        } else {
            fail = (int) session.getAttribute("failedAttempts");
        }
            
//        session.setAttribute("failedAttempts", 1);
        
        try {
            User user = authservice.findByEmailAndPassword(email, password);
            if (user != null) {
                if(user.getStatus()==false){
                    String errorMessage = "Bạn đã bị chặn bởi admin!";
                    request.setAttribute("errorMessage", errorMessage);
                    request.setAttribute("rememberEmail", email);
                    request.setAttribute("rememberPassword", password);
                    request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                }
                
                session.setAttribute("user", user);
                setLoginCookies(response, email, password, "on".equals(remember));
                String redirectUrl = getRedirectUrlByRole(user.getRoleID().getRoleID());
                response.sendRedirect(request.getContextPath() + redirectUrl);
            } else {
//                String errorMessage = "Sai email hoặc mật khẩu "+(Integer) session.getAttribute("failedAttempts")+" lần";
//                if (fail >= 3) { // Khi thất bại 3 lần, hiển thị gợi ý
                    String errorMessage = " Bạn quên mật khẩu? <a href='/demo1/views/checkEmail.jsp'>Nhấn vào đây</a>";
//                }
                request.setAttribute("failedAttempts", fail+1);
                request.setAttribute("errorMessage", errorMessage);
                request.setAttribute("rememberEmail", email);
                request.setAttribute("rememberPassword", password);
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                

            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Đã xảy ra lỗi, vui lòng thử lại!");
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
            case 2 -> "/seller?action=sellerPage";
            case 3 -> "/products";
            default -> "/products";
        };
    }

    private void checkEmail(HttpServletRequest request, HttpServletResponse response) {
        try {
            String email = request.getParameter("email-register");
            User user = authservice.findByEmail(email);
            
            if (user != null) {
                request.setAttribute("message", "Một email đặt lại mật khẩu đã được gửi.");
            } else {
                request.setAttribute("message", "Email không tồn tại!");
            }
            request.setAttribute("email", email);
            request.getRequestDispatcher("views/reset-password.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String email = request.getParameter("email-register");
            String newPassword = request.getParameter("password-register");
            
            authservice.updatePassword(email, newPassword);
            request.setAttribute("message", "Mật khẩu đã được đặt lại thành công.");    
            response.sendRedirect("/demo1/views/login.jsp");
        } catch (IOException ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}