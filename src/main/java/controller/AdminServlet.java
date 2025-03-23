package controller;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import model.Role;
import model.SellerRegistrationRequest;
import model.User;
import service.UserService;
import service.ProductService;
import service.SellerRegistrationService;

@WebServlet(name = "AdminnServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {
    private SellerRegistrationService sellerRegistrationService;
    private UserService userService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
        this.productService = new ProductService();
        this.sellerRegistrationService = new SellerRegistrationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listUsers(request, response);
                break;
            case "nguoidung":
                listUsersByRole(request, response, 3);
                break;
            case "nguoiban":
                listUsersByRole(request, response, 2);
                break;
            case "hoso":
                showProfile(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "restore":
                restoreUser(request, response);
                break;
            case "adduser":
                showAddForm(request, response);
                break;
            case "profile":
                showUserProfile(request, response);
                break;
            case "editProfile":
                showEditProfileForm(request, response);
                break;                
            case "stats":
                showStatistics(request, response);
                break;
            case "listRequests":
                listRequests(request, response);
                break;
            case "approveRequest":
                approveRequest(request, response);
                break;
            case "rejectRequest":
                rejectRequest(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equals(action)) {
            insertUser(request, response);
        } else if ("update".equals(action)) {
            updateUser(request, response);
        } else if ("updateProfile".equals(action)) {
            updateProfile(request, response);
        }
    }
    
    private void showStatistics(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        long customerCount = userService.countUsersByRole(3);
        long sellerCount = userService.countUsersByRole(2);
        long productCount = productService.countAllProducts();
        int activeUsers = getActiveUserCount(request.getServletContext());

        Calendar cal = Calendar.getInstance();
        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");
        int year = yearParam != null ? Integer.parseInt(yearParam) : cal.get(Calendar.YEAR);
        int month = monthParam != null ? Integer.parseInt(monthParam) : cal.get(Calendar.MONTH) + 1;

        List<Object[]> registrationStats = userService.getUserRegistrationStatsByMonth(year, month);
        List<Integer> days = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        for (Object[] stat : registrationStats) {
            days.add((Integer) stat[0]);
            counts.add((Long) stat[1]);
        }

        cal.set(year, month - 1, 1);
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Kiểm tra nếu là yêu cầu AJAX
        String ajaxParam = request.getParameter("ajax");
        if ("true".equals(ajaxParam)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            // Tạo JSON response
            String jsonResponse = String.format(
                "{\"days\": [%s], \"counts\": [%s], \"maxDays\": %d}",
                days.stream().map(String::valueOf).collect(Collectors.joining(",")),
                counts.stream().map(String::valueOf).collect(Collectors.joining(",")),
                maxDays
            );
            out.print(jsonResponse);
            out.flush();
            return;
        }

        // Xử lý thông thường nếu không phải AJAX
        request.setAttribute("customerCount", customerCount);
        request.setAttribute("sellerCount", sellerCount);
        request.setAttribute("productCount", productCount);
        request.setAttribute("activeUsers", activeUsers);
        request.setAttribute("registrationDays", days);
        request.setAttribute("registrationCounts", counts);
        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("maxDays", maxDays);

        request.setAttribute("part", "statistics.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }
    // Phương thức để lấy số lượng người dùng đang đăng nhập
    private int getActiveUserCount(ServletContext context) {
        Integer count = (Integer) context.getAttribute("activeUsers");
        return (count != null) ? count : 0;
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int page = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        List<User> listUser = userService.getAllUsers(page, pageSize);
        long totalUsers = userService.countUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        request.setAttribute("listUser", listUser);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("part", "userlist.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void listUsersByRole(HttpServletRequest request, HttpServletResponse response, int roleID) 
            throws ServletException, IOException {
        int page = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<User> listUser = userService.getUsersByRole(roleID, page, pageSize);
        long totalUsers = userService.countUsersByRole(roleID);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        request.setAttribute("listUser", listUser);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("roleID", roleID);
        request.setAttribute("part", "userlist.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("part", "adminProfile.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userService.getUserById(id);
        request.setAttribute("user", existingUser);

        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "admin";
        }
        request.setAttribute("returnUrl", referer);

        request.setAttribute("part", "edituser.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("part", "addUser.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        User user = new User();
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setAddress(request.getParameter("address"));
        Role role = new Role();
        role.setRoleID(Integer.parseInt(request.getParameter("roleID")));
        user.setRoleID(role);

        userService.addUser(user);
        response.sendRedirect("admin");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");
            int roleID = Integer.parseInt(request.getParameter("roleID"));

            userService.updateUserDetails(id, fullName, email, phoneNumber, address, roleID);

            request.getSession().setAttribute("message", "Cập nhật người dùng thành công!");
            response.sendRedirect("admin");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            showEditForm(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            userService.deleteUserById(id);
            request.getSession().setAttribute("message", "Xóa người dùng thành công!");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
        } catch (IllegalStateException e) {
            request.getSession().setAttribute("error", e.getMessage());
        }
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "admin";
        }
        response.sendRedirect(referer);
    }

    private void showUserProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(id);

        if (user == null) {
            request.setAttribute("error", "Không tìm thấy người dùng với ID: " + id);
            listUsers(request, response);
            return;
        }

        request.setAttribute("profileUser", user);
        if (user.getRoleID().getRoleID() == 2) {
            request.setAttribute("part", "sellerProfile.jsp");
        } else if (user.getRoleID().getRoleID() == 3) {
            request.setAttribute("part", "customerProfile.jsp");
        } else {
            request.setAttribute("part", "userProfile.jsp");
        }
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void restoreUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            userService.restoreUser(id);
            request.getSession().setAttribute("message", "Khôi phục người dùng thành công!");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi khôi phục người dùng: " + e.getMessage());
        }
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) {
            referer = "admin";
        }
        response.sendRedirect(referer);
    }

    private void showEditProfileForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }
        request.setAttribute("user", user);
        request.setAttribute("part", "editProfile.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        userService.updateUserProfile(user, fullName, email, phoneNumber, address, password);

        request.getSession().setAttribute("message", "Cập nhật hồ sơ thành công!");
        response.sendRedirect(request.getContextPath() + "/admin?action=hoso");
    }
    
    private void listRequests(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int page = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<SellerRegistrationRequest> requestList = sellerRegistrationService.getPendingRequests(page, pageSize);
        long totalRequests = sellerRegistrationService.countPendingRequests();
        int totalPages = (int) Math.ceil((double) totalRequests / pageSize);

        request.setAttribute("requestList", requestList != null ? requestList : new ArrayList<>());
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("part", "admin_requests.jsp");
        request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
    }

    private void approveRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int requestID = Integer.parseInt(request.getParameter("requestId"));
    SellerRegistrationRequest req = sellerRegistrationService.getRequestById(requestID);
    if (req == null || !"pending".equals(req.getStatus())) {
        request.getSession().setAttribute("error", "Yêu cầu không tồn tại hoặc đã được xử lý!");
        response.sendRedirect(request.getContextPath() + "/admin?action=listRequests");
        return;
    }

    try {
        User user = userService.getUserById(req.getUser().getUserID());
        if (user != null && (user.getRoleID() == null || user.getRoleID().getRoleID() != 2)) {
            Role sellerRole = new Role();
            sellerRole.setRoleID(2);
            user.setRoleID(sellerRole);
            userService.updateUser(user);

            req.setStatus("approved");
            sellerRegistrationService.updateRequest(req);
            request.getSession().setAttribute("message", "Phê duyệt yêu cầu thành công!");
        } else {
            request.getSession().setAttribute("error", "Không thể phê duyệt: Người dùng không hợp lệ hoặc đã là người bán!");
        }
    } catch (Exception e) {
        request.getSession().setAttribute("error", "Lỗi khi phê duyệt yêu cầu: " + e.getMessage());
        e.printStackTrace();
    }
    response.sendRedirect(request.getContextPath() + "/admin?action=listRequests");
}

    private void rejectRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int requestID = Integer.parseInt(request.getParameter("requestId"));
        SellerRegistrationRequest req = sellerRegistrationService.getRequestById(requestID);
        if (req == null || !"pending".equals(req.getStatus())) {
            request.getSession().setAttribute("error", "Yêu cầu không tồn tại hoặc đã được xử lý!");
            response.sendRedirect(request.getContextPath() + "/admin?action=listRequests");
            return;
        }

        req.setStatus("rejected");
        sellerRegistrationService.updateRequest(req);
        request.getSession().setAttribute("message", "Từ chối yêu cầu thành công!");
        response.sendRedirect(request.getContextPath() + "/admin?action=listRequests");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}