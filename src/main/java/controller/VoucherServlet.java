package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Discount;
import model.User;
import service.VoucherService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "VoucherServlet", urlPatterns = {"/voucher"})
public class VoucherServlet extends HttpServlet {

    private VoucherService voucherService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        voucherService = new VoucherService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để truy cập!");
            return;
        }

        int sellerId = user.getUserID();

        if ("voucherManager".equals(action)) {
            List<Discount> voucherList = voucherService.voucherListBySellerId(sellerId);
            request.setAttribute("voucherList", voucherList);
            request.getRequestDispatcher("/seller/voucherManager.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Sử dụng POST cho các hành động voucher");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để truy cập!");
            return;
        }

        int sellerId = user.getUserID();
        String action = request.getParameter("action");

        // Sử dụng switch với biến action thay vì chuỗi "action"
        switch (action != null ? action : "") {
            case "addVoucherToProduct":
                int productId = Integer.parseInt(request.getParameter("productId"));
                String code = request.getParameter("code");
                double discountPercent = Double.parseDouble(request.getParameter("discountPercent"));
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");

                try {
                    voucherService.addVoucher(productId, code, discountPercent, startDateStr, endDateStr);
                    System.out.println("addSuccess!");
                    // Tải lại danh sách voucher của seller
                    List<Discount> voucherList = voucherService.voucherListBySellerId(sellerId);
                    request.setAttribute("voucherList", voucherList);
                    request.getRequestDispatcher("/seller/voucherManager.jsp").forward(request, response);
                } catch (ParseException ex) {
                    Logger.getLogger(VoucherServlet.class.getName()).log(Level.SEVERE, null, ex);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thêm voucher: " + ex.getMessage());
                }
                break;

            case "removeVoucher":
                int discountID = Integer.parseInt(request.getParameter("discountID"));
                try {
                    voucherService.removeVoucher(discountID);
                    // Tải lại danh sách voucher của seller
                    List<Discount> voucherList = voucherService.voucherListBySellerId(sellerId);
                    request.setAttribute("voucherList", voucherList);
                    request.getRequestDispatcher("/seller/voucherManager.jsp").forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(VoucherServlet.class.getName()).log(Level.SEVERE, null, ex);
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xóa voucher: " + ex.getMessage());
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ: " + action);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý các hành động liên quan đến voucher";
    }
}