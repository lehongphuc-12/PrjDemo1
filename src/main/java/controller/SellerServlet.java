package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import model.CategoryGroup;
import model.City;
import model.OrderDetail;
import model.Product;
import model.ProductImage;
import model.User;
import service.CategoryGroupService;
import service.CityService;
import service.OrderService;
import service.ProductManagerService;
import service.UserProfileService;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SellerServlet", urlPatterns = {"/seller"})
public class SellerServlet extends HttpServlet {

    private static final int SIZE = 8;
    private CityService cityService;
    private ProductManagerService productService;
    private UserProfileService userService;
    private CategoryGroupService cateGroupService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserProfileService();
        productService = new ProductManagerService();
        cateGroupService = new CategoryGroupService();
        cityService = new CityService();
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;

        String action = request.getParameter("action");
        String part = "/seller/";
        if (action == null) {
            action = "";
        }
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        switch (action) {

            case "sellerPage" -> {
                part += "SellerProfile.jsp";
            }

            case "donhang" -> {
                part += "DonHang.jsp";
                int id = user.getUserID();
                String role = "seller";
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

                // Chỉ tải dữ liệu cần thiết dựa trên status
                switch (status) {
                    case "cho-xu-ly":
                        List<OrderDetail> choXuLyList = orderService.getDonChoXuli(id, page, SIZE, role);
                        int totalPageChoXuLy = orderService.getTotalChoXuLyPage(id, role, SIZE);
                        request.setAttribute("choXuLyList", choXuLyList);
                        request.setAttribute("totalPageChoXuLy", totalPageChoXuLy);
                        break;
                    case "dang-giao":
                        List<OrderDetail> dangGiaoList = orderService.getDonDangGiao(id, page, SIZE, role);
                        int totalPageDangGiao = orderService.getTotalDangGiaoPage(id, role, SIZE);
                        request.setAttribute("dangGiaoList", dangGiaoList);
                        request.setAttribute("totalPageDangGiao", totalPageDangGiao);
                        break;
                    case "da-nhan":
                        List<OrderDetail> daNhanList = orderService.getDonDaNhan(id, page, SIZE, role);
                        int totalPageDaNhan = orderService.getTotalDaNhanPage(id, role, SIZE);
                        request.setAttribute("daNhanList", daNhanList);
                        request.setAttribute("totalPageDaNhan", totalPageDaNhan);
                        break;
                    case "da-huy":
                        List<OrderDetail> daHuyList = orderService.getDonDaHuy(id, page, SIZE, role);
                        int totalPageDaHuy = orderService.getTotalDaHuyPage(id, role, SIZE);
                        request.setAttribute("daHuyList", daHuyList);
                        request.setAttribute("totalPageDaHuy", totalPageDaHuy);
                        break;
                    case "all":
                        List<OrderDetail> orderList = orderService.getOrderDetailBySellerId(id, page, SIZE);
                        System.out.println("page: " + page);
                        for (OrderDetail order : orderList) {

                        }
                        int totalPage = orderService.getTotalSellerOrderPage(id, SIZE);
                        request.setAttribute("orderList", orderList);
                        request.setAttribute("totalPage", totalPage);
                        break;
                }

                // Truyền các giá trị hiện tại để JSP sử dụng
                request.setAttribute("currentStatus", status);
                request.setAttribute("currentPage", page);

//                // Tải các danh sách khác với page = 1 (cho hiển thị mặc định nếu cần)
//                request.setAttribute("choXuLyList", orderService.getDonChoXuli(id, 1, SIZE, role));
//                request.setAttribute("dangGiaoList", orderService.getDonDangGiao(id, 1, SIZE, role));
//                request.setAttribute("daNhanList", orderService.getDonDaNhan(id, 1, SIZE, role));
//                request.setAttribute("daHuyList", orderService.getDonDaHuy(id, 1, SIZE, role));
                // Truyền tổng số trang cho tất cả trạng thái
                request.setAttribute("totalPage", orderService.getTotalSellerOrderPage(id, SIZE));
                System.out.println("totalPage: " + orderService.getTotalSellerOrderPage(id, SIZE));
                request.setAttribute("totalPageChoXuLy", orderService.getTotalChoXuLyPage(id, role, SIZE));
                request.setAttribute("totalPageDangGiao", orderService.getTotalDangGiaoPage(id, role, SIZE));
                request.setAttribute("totalPageDaNhan", orderService.getTotalDaNhanPage(id, role, SIZE));
                request.setAttribute("totalPageDaHuy", orderService.getTotalDaHuyPage(id, role, SIZE));
            }

            case "hieuqua" -> {
                part += "HieuQua.jsp";
                int id = user.getUserID();
                page = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                String role = "seller";
                List<OrderDetail> orderList = orderService.getOrderDetailBySellerId(user.getUserID(), page, SIZE);
                int totalPage = orderService.getTotalSellerOrderPage(id, SIZE);
                request.setAttribute("totalPage", totalPage);
                System.out.println(totalPage);
                request.setAttribute("choXuLyList", orderService.getDonChoXuli(id, page, SIZE, role));
                request.setAttribute("dangGiaoList", orderService.getDonDangGiao(id, page, SIZE, role));
                request.setAttribute("daNhanList", orderService.getDonDaNhan(id, page, SIZE, role));
                request.setAttribute("daHuyList", orderService.getDonDaHuy(id, page, SIZE, role));
                request.setAttribute("choXuLyListSize", orderService.countDonChoXuLy(id, role));
                // Truyền các danh sách vào request
                request.setAttribute("orderList", orderList); // Danh sách tổng
                request.setAttribute("totalPageChoXuLy", orderService.getTotalChoXuLyPage(id, role, SIZE));
                request.setAttribute("totalPageDangGiao", orderService.getTotalDangGiaoPage(id, role, SIZE));
                request.setAttribute("totalPageDaNhan", orderService.getTotalDaNhanPage(id, role, SIZE));
                request.setAttribute("totalPageDaHuy", orderService.getTotalDaHuyPage(id, role, SIZE));
                request.setAttribute("currentStatus", status);
                request.setAttribute("currentPage", page);
            }
            case "doanhthu" -> {
                part += "DoanhThu.jsp";
                double totalMoney = orderService.getTotalMoneySell(user.getUserID());
                double paidMoney = orderService.getPaidMoneySell(user.getUserID());
                double pendingMoney = orderService.getPendingMoneySell(user.getUserID());
                request.setAttribute("totalMoney", totalMoney);
                request.setAttribute("paidMoney", paidMoney);
                request.setAttribute("pendingMoney", pendingMoney);

            }
            case "phantich" -> {
                part += "PhanTich.jsp";
                int id = user.getUserID();
                String role = "seller";
                page = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
                String status = request.getParameter("status") != null ? request.getParameter("status") : "all";
                double totalMoney = orderService.getTotalMoneySell(user.getUserID());

                request.setAttribute("totalMoney", totalMoney);
                List<OrderDetail> orderList = orderService.getOrderDetailBySellerId(user.getUserID(), page, SIZE);
                List<OrderDetail> orderList_1 = orderService.getOrderDetailBySellerId(user.getUserID());

                request.setAttribute("soDon", orderList_1.size());
                int totalPage = orderService.getTotalSellerOrderPage(id, SIZE);
                System.out.println("totalPage: Phan tich :" + totalPage);
                request.setAttribute("totalPage", totalPage);
                System.out.println(orderList_1.size() + " &&" + totalMoney);
                request.setAttribute("choXuLyList", orderService.getDonChoXuli(id, page, SIZE, role));
                request.setAttribute("dangGiaoList", orderService.getDonDangGiao(id, page, SIZE, role));
                request.setAttribute("daNhanList", orderService.getDonDaNhan(id, page, SIZE, role));
                request.setAttribute("daHuyList", orderService.getDonDaHuy(id, page, SIZE, role));
                request.setAttribute("choXuLyListSize", orderService.countDonChoXuLy(id, role));
                request.setAttribute("dangGiaoListSize", orderService.countDonDangGiao(id, role));
                request.setAttribute("daNhanListSize", orderService.countDonDaNhan(id, role));
                request.setAttribute("daHuyListSize", orderService.countDonDaHuy(id, role));

                // Truyền các danh sách vào request
                request.setAttribute("orderList", orderList); // Danh sách tổng
                request.setAttribute("totalPageChoXuLy", orderService.countDonChoXuLy(id, role));
                request.setAttribute("totalPageDangGiao", orderService.countDonDangGiao(id, role));
                request.setAttribute("totalPageDaNhan", orderService.countDonDaNhan(id, role));
                request.setAttribute("totalPageDaHuy", orderService.countDonDaHuy(id, role));
                request.setAttribute("currentStatus", status);
                request.setAttribute("currentPage", page);

                request.setAttribute("bestProductSell", orderService.getBestSellingProductName(user.getUserID()));
            }
            case "sanphamlist" -> {
                if (request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
                int id = user.getUserID();
                part += "SanPhamList.jsp";
                int totalPage = productService.getTotalProductActivePage(id, SIZE);
                System.out.println(totalPage);
                request.setAttribute("totalPage", totalPage);
                List<Product> productList = productService.getAllBySellerIdActive(user.getUserID(), page, SIZE);
                request.setAttribute("productList", productList);

                String pageStr = request.getParameter("page");
                page = (pageStr == null || pageStr.isEmpty()) ? 1 : Integer.parseInt(pageStr);
            }
            case "addSanpham" -> {
                part += "addProduct.jsp";
                List<City> cityList = cityService.getAllCity();
                request.setAttribute("cityList", cityList);
                List<CategoryGroup> cateGroupList = cateGroupService.getAll();
                request.setAttribute("cateGroupList", cateGroupList);
            }
            case "password" -> {
                part += "changePassword.jsp";
//                List<OrderDetail> orderList = orderService.getOrderDetailBySellerId(user.getUserID());
            }
            case "updateProduct" -> {
                part += "updateProduct.jsp";
                int id = Integer.parseInt(request.getParameter("productId"));
                List<ProductImage> images = productService.getImagesById(id);
                request.setAttribute("images", images);
                Product product = productService.getProductById(id);
                request.setAttribute("product", product);
                List<City> cityList = cityService.getAllCity();
                request.setAttribute("cityList", cityList);
                List<CategoryGroup> cateGroupList = cateGroupService.getAll();
                request.setAttribute("cateGroupList", cateGroupList);
            }
            case "xulidon" -> {
                part += "xuLiDon.jsp";
                List<OrderDetail> orderList = orderService.getOrderDetailBySellerId(user.getUserID());
                List<OrderDetail> choXuLyList = orderList.stream()
                        .filter(od -> "Chờ xử lý".equals(od.getStatusID().getStatusName()))
                        .collect(Collectors.toList());
                request.setAttribute("choXuLyList", choXuLyList);
            }
            case "removedProduct" -> {
                if (request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
                part += "phucHoiSanPham.jsp";
                int id = user.getUserID();
                int totalPage = productService.getTotalProductInActivePage(id, SIZE);
                request.setAttribute("totalPage", totalPage);
                List<Product> productRemovedList = productService.getAllBySellerIdInActive(id, page, SIZE);
                request.setAttribute("productRemovedList", productRemovedList);
            }
            default -> {
                part += "Hoso.jsp";
            }
        }
        request.getRequestDispatcher(part).forward(request, response);
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
        System.out.println("123");
        String action = request.getParameter("action");
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Query String: " + request.getQueryString());
        System.out.println("Action: " + request.getParameter("action"));
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if ("updateProfile".equals(action)) {

            String shopName = request.getParameter("shopName");
            System.out.println(shopName);
            String email = request.getParameter("email");
            System.out.println(email);
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            user.setShopName(shopName);
            user.setAddress(address);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            session.setAttribute("user", user);

            if (user != null) {
                boolean success = userService.updateUserProfile(user.getUserID(), email, phone, address, shopName);
                System.out.println(user.getShopName() + user.getAddress());
                if (success) {
                    response.getWriter().write("success");
                } else {
                    response.getWriter().write("failure");
                }
            } else {
                response.getWriter().write("unauthorized"); // Chưa đăng nhập
            }

        } else if ("updatePassword".equals(action)) {
            String newPassword = request.getParameter("newPassword");
            if (user != null && userService.updatePassword(user.getUserID(), newPassword)) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } else if ("verifyPassword".equals(action)) {
            String currentPassword = request.getParameter("currentPassword");
            if (user != null && userService.verifyPassword(user.getUserID(), currentPassword)) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } else if ("reCreate".equals(action)) {
            int id = Integer.parseInt(request.getParameter("productID"));
            productService.reCreateByUpdateStatus(id);
            response.sendRedirect(request.getContextPath() + "/seller?action=removedProduct");
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
