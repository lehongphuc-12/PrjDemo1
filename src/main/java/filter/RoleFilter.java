package filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebFilter(filterName = "RoleFilter", urlPatterns = {"/*"})
public class RoleFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

    // Danh sách các trang
    private List<String> commonPages;
    private List<String> guestPages;
    private List<String> customerPages;
    private List<String> sellerPages;
    private List<String> adminPages;
    private List<String> staticResources;

    public RoleFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null && debug) {
            log("RoleFilter: Initializing filter");
        }

        // Khởi tạo danh sách các trang
        commonPages = Collections.unmodifiableList(Arrays.asList(
            "/logins",
            "/views/login.jsp",
            "/logout",    
            "/register",
            "/products",
            "/cates",
            "/filters",
            "/chatbot",
            "/LoginGoogleHandler",
            "/detail",
            "/views/product_detail.jsp",
            "/review",
            "/views/checkEmail.jsp",
            "/checkEmail",
            "/views/reset-password.jsp",
            "/reset-password",
            "/getProductTypes",
            "/views/changePassword.jsp",
            "/store",
            "/views/store_page.jsp"
        ));

        guestPages = Collections.unmodifiableList(Arrays.asList(
            "/products" // Guest chỉ xem được sản phẩm
        ));

        customerPages = Collections.unmodifiableList(Arrays.asList(
            "/products",       // Xem sản phẩm
            "/cart",          // Giỏ hàng
            "/orders",         // Lịch sử đơn hàng
            "/checkout",
            "/vnpay-return",
            "/order-confirmation",
            "/SellerRegistrationServlet",
            "/views/registerseller.jsp",
            "/user"
        ));

        sellerPages = Collections.unmodifiableList(Arrays.asList(
            "/products",      // Xem sản phẩm
            "/seller",        // Quản lý sản phẩm
            "/cart",
            "/checkout",
            "/vnpay-return",
            "/order-confirmation",
            "/user",
            "/api/data",
            "/updateProduct",
            "/addProduct",
            "/removeProduct"
        ));

        adminPages = Collections.unmodifiableList(Arrays.asList(
            "/admin",         // Trang quản trị
            "/admin/users",   // Quản lý người dùng
            "/admin/products",// Quản lý sản phẩm toàn hệ thống
            "/products"       // Xem sản phẩm
        ));

        staticResources = Collections.unmodifiableList(Arrays.asList(
            ".css", ".js", ".png", ".jpg", ".jpeg"
        ));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String relativeURI = requestURI.substring(contextPath.length()); // Lấy URI tương đối

        // Bỏ qua tài nguyên tĩnh
        if (isStaticResource(relativeURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Lấy thông tin user từ session
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        
        int roleID = (user != null) ? user.getRoleID().getRoleID() : 0; // 0 là Guest

        if(relativeURI.contains("paymentv2")){
            chain.doFilter(request, response);
        }
        
        // Kiểm tra quyền truy cập
        List<String> allowedPages = getAllowedPages(roleID);
        if (commonPages.contains(relativeURI) || allowedPages.contains(relativeURI)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(contextPath + "/views/access-denied.jsp");
        }
    }

    // Lấy danh sách trang được phép theo roleID
    private List<String> getAllowedPages(int roleID) {
        return switch (roleID) {
            case 1 -> adminPages;    // Admin
            case 2 -> sellerPages;   // Seller
            case 3 -> customerPages; // Customer
            default -> guestPages;   // Guest (roleID = 0)
        };
    }

    // Kiểm tra xem URI có phải tài nguyên tĩnh không
    private boolean isStaticResource(String uri) {
        return staticResources.stream().anyMatch(uri::endsWith);
    }

    @Override
    public void destroy() {
    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return "RoleFilter()";
        }
        StringBuffer sb = new StringBuffer("RoleFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return sb.toString();
    }

    private void log(String msg) {
        if (filterConfig != null) {
            filterConfig.getServletContext().log(msg);
        }
    }
}