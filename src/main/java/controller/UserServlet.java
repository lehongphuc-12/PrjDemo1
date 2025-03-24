/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.OrderDetail;
import model.User;
import service.OrderService;
import service.UserProfileService;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    private static final int SIZE = 8;
    private UserProfileService userService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserProfileService();
        orderService = new OrderService();
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String part = "/user/";
        int page = 1;
        if (action == null) {
            action = "";
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = user.getUserID();
        switch (action) {
            case "userPage" -> {
                part += "UserProfile.jsp";
           
            }
            case "donHang" -> {
                part += "DonHang.jsp";
                page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                List<OrderDetail> orderList = orderService.getOrderDetailByUserId(id, page,SIZE);
                String role = "user";
                request.setAttribute("choXuLyList", orderService.getDonChoXuli(id, page, 20, role));
                request.setAttribute("dangGiaoList", orderService.getDonDangGiao(id, page, 20, role));
                request.setAttribute("daNhanList", orderService.getDonDaNhan(id, page, 20, role));
                request.setAttribute("daHuyList", orderService.getDonDaHuy(id, page, 20, role));
                int totalPage = orderService.getTotalUserOrderPage(id, SIZE);
                request.setAttribute("totalPage", totalPage);
                System.out.println("totalPage User: "+ totalPage);
                // Truyền các danh sách vào request
                request.setAttribute("orderList", orderList); // Danh sách tổng
                request.setAttribute("totalPageChoXuLy", orderService.getTotalChoXuLyPage(id, role, SIZE));
                request.setAttribute("totalPageDangGiao", orderService.getTotalDangGiaoPage(id, role, SIZE));
                request.setAttribute("totalPageDaNhan", orderService.getTotalDaNhanPage(id, role, SIZE));
                request.setAttribute("totalPageDaHuy", orderService.getTotalDaHuyPage(id, role, SIZE));
                request.setAttribute("currentStatus", status);
                request.setAttribute("currentPage", page);
            }
            case "password" ->
                part += "changePassword.jsp";
            case "notifications" ->
                part += "ThongBao.jsp";
            case "huyDon" -> {
                part += "xuLiDon.jsp";
                request.setAttribute("choXuLyList", orderService.getDonChoXuli(id, page, SIZE, "user"));

            }
            default ->
                part += "Hoso.jsp";
        }

        // Gửi response trả về AJAX thay vì chuyển hướng
        RequestDispatcher dispatcher = request.getRequestDispatcher(part);
        dispatcher.include(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute("user");

        if ("verifyPassword".equals(action)) {
            String currentPassword = request.getParameter("currentPassword");
            if (user != null && userService.verifyPassword(user.getUserID(), currentPassword)) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } else if ("updatePassword".equals(action)) {
            String newPassword = request.getParameter("newPassword");
            if (user != null && userService.updatePassword(user.getUserID(), newPassword)) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } else if ("updateProfile".equals(action)) {

            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String shopName = null;
            user.setAddress(address);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            
            session.setAttribute("user", user);
            if (user != null) {
                boolean success = userService.updateUserProfile(user.getUserID(), email, phone, address, shopName);
                if (success) {
                    response.getWriter().write("success");
                } else {
                    response.getWriter().write("failure");
                }
            } else {
                response.getWriter().write("unauthorized"); // Chưa đăng nhập
            }
        } else if ("cancelOrder".equals(action)) {
            int orderDetailId = Integer.parseInt(request.getParameter("orderdetailId"));
            orderService.huyDon(orderDetailId);
            response.sendRedirect(request.getContextPath() + "/user?action=donHang");
        } else if("confirmReceived".equals(action)) {
            int orderDetailId = Integer.parseInt(request.getParameter("orderdetailId"));
            orderService.daNhan(orderDetailId);
            response.sendRedirect(request.getContextPath() + "/user?action=donHang");
        } else if("repurchaseOrder".equals(action)){
            int orderDetailId = Integer.parseInt(request.getParameter("orderdetailId"));
            orderService.muaLai(orderDetailId);
            response.sendRedirect(request.getContextPath() + "/user?action=donHang");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
