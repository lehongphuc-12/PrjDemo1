/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import model.GoogleUser;
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


@WebServlet(name="LoginGoogleHandler", urlPatterns={"/LoginGoogleHandler"})
public class LoginGoogleHandler extends HttpServlet {
       private UserDAO userDao = new UserDAO();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("views/login.jsp");
            return;
        }

        // Lấy access token từ Google
        String accessToken = GoogleUtils.getToken(code);
        GoogleUser googleUser = GoogleUtils.getUserInfo(accessToken);

        if (googleUser == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }

        // Kiểm tra email trong database
        User user = userDao.findByEmailDAO(googleUser.getEmail());
        HttpSession session = request.getSession();

        if (user != null) {
            // Đăng nhập ngay nếu user đã có trong database
            session.setAttribute("user", user);
            response.sendRedirect("views/home.jsp");
        } else {
            // Lưu thông tin Google vào session và chuyển sang trang đăng ký
            session.setAttribute("googleUser", googleUser);
              response.sendRedirect(request.getContextPath() + "/views/login.jsp?register=true");
        }
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
