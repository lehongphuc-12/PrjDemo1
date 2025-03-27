package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CheckoutServlet.class.getName());
    private ProductService productService;
    private CartService cartService;
    private PaymentMethodService paymentMethodService;
    private CheckoutService checkoutService;
    private VNPayService vnpayService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        cartService = new CartService();
        paymentMethodService = new PaymentMethodService();
        checkoutService = new CheckoutService();
        vnpayService = new VNPayService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            LOGGER.warning("User not logged in for checkout.");
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        User user = (User) session.getAttribute("user");

        String productIdsParam = request.getParameter("productIds");
        if (productIdsParam == null || productIdsParam.trim().isEmpty()) {
            LOGGER.warning("No products selected for checkout.");
            request.setAttribute("errorMessage", "Vui lòng chọn ít nhất một sản phẩm để thanh toán.");
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            return;
        }

        List<Integer> productIds = parseProductIds(productIdsParam);
        if (productIds.isEmpty()) {
            LOGGER.warning("No valid product IDs for checkout.");
            request.setAttribute("errorMessage", "Danh sách sản phẩm không hợp lệ.");
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            return;
        }

        List<Cart> cartItems = getCartItems(user, request, response);
        if (cartItems == null) return;

        List<Cart> selectedItems = filterSelectedItems(cartItems, productIds, request, response);
        if (selectedItems == null) return;

        prepareCheckoutPage(request, selectedItems);
        request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
            if ("clearSession".equals(action)) {
                clearCheckoutSession(request);
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        LOGGER.warning("User not logged in for checkout.");
        response.sendRedirect(request.getContextPath() + "/logins");
        return;
    }

    User user = (User) session.getAttribute("user");

    String productIdsParam = request.getParameter("productIds");
    if (productIdsParam == null || productIdsParam.trim().isEmpty()) {
        LOGGER.warning("No products selected for checkout.");
        request.setAttribute("errorMessage", "Vui lòng chọn ít nhất một sản phẩm để thanh toán.");
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
        return;
    }

    List<Integer> productIds = parseProductIds(productIdsParam);
    if (productIds.isEmpty()) {
        LOGGER.warning("No valid product IDs for checkout.");
        request.setAttribute("errorMessage", "Danh sách sản phẩm không hợp lệ.");
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
        return;
    }

    List<Cart> cartItems = getCartItems(user, request, response);
    if (cartItems == null) return;

    List<Cart> selectedItems = filterSelectedItems(cartItems, productIds, request, response);
    if (selectedItems == null) return;

    // Kiểm tra tồn kho trước khi xử lý
    for (Cart cart : selectedItems) {
        Product product = cart.getProductID();
        if (product.getQuantity().compareTo(cart.getQuantity()) < 0) {
            LOGGER.log(Level.WARNING, "Insufficient stock for product: {0}", product.getProductName());
            request.setAttribute("errorMessage", "Sản phẩm '" + product.getProductName() + "' không đủ hàng.");
            prepareCheckoutPage(request, selectedItems);
            request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
            request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            return;
        }
    }

    String shippingAddress = extractShippingAddress(request, user);
    if (shippingAddress == null) {
        request.setAttribute("errorMessage", "Vui lòng cung cấp địa chỉ giao hàng hợp lệ.");
        prepareCheckoutPage(request, selectedItems);
        request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
        return;
    }

    PaymentMethod paymentMethod = validateAndGetPaymentMethod(request, response, selectedItems);
    if (paymentMethod == null) return;

    try {
        BigDecimal discountAmount = (BigDecimal) session.getAttribute("discountAmount");
        Order1 order = checkoutService.processCheckout(user, selectedItems, shippingAddress, paymentMethod, discountAmount);
        if (order == null || order.getOrderID() == 0) {
            LOGGER.severe("Failed to create order.");
            throw new IllegalStateException("Không thể tạo đơn hàng.");
        }

        String paymentMethodCode = request.getParameter("paymentMethodCode");
        if ("Chuyển khoản VNPAY".equals(paymentMethodCode)) {
            session.setAttribute("productIds", productIdsParam);
            session.setAttribute("orderId", order.getOrderID()); // Lưu orderId để dùng trong callback
            String total = order.getTotalAmount().toString();
            String paymentUrl = vnpayService.createPaymentUrl(request, order.getOrderID(), total);
            if (paymentUrl == null || paymentUrl.trim().isEmpty()) {
                LOGGER.severe("Failed to create VNPay payment URL.");
                throw new IllegalStateException("Không thể tạo URL thanh toán VNPay.");
            }
            response.sendRedirect(paymentUrl);
        } else {
            cartService.clearSelectedItems(user, productIds);
            clearSessionDiscount(request);
            LOGGER.log(Level.INFO, "Checkout successful, redirecting to order confirmation for order: {0}", order.getOrderID());
            response.sendRedirect(request.getContextPath() + "/order-confirmation?orderId=" + order.getOrderID());
        }
    } catch (IllegalArgumentException e) {
        LOGGER.log(Level.SEVERE, "Dữ liệu không hợp lệ: {0}", e.getMessage());
        request.setAttribute("errorMessage", e.getMessage()); 
        prepareCheckoutPage(request, selectedItems);
        request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
    } catch (IllegalStateException e) {
        LOGGER.log(Level.SEVERE, "Lỗi thanh toán: {0}", e.getMessage());
        request.setAttribute("errorMessage", e.getMessage()); 
        prepareCheckoutPage(request, selectedItems);
        request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Lỗi không mong muốn: {0}", e.getMessage());
        request.setAttribute("errorMessage", "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.");
        prepareCheckoutPage(request, selectedItems);
        request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
        request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
    }
}

    private List<Integer> parseProductIds(String productIdsParam) {
        List<Integer> productIds = new ArrayList<>();
        String[] productIdsArray = productIdsParam.split(",");
        for (String id : productIdsArray) {
            try {
                productIds.add(Integer.valueOf(id.trim()));
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid product ID in checkout: {0}", id);
            }
        }
        return productIds;
    }

    private List<Cart> getCartItems(User user, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cart> cartItems;
        try {
            cartItems = cartService.getCartItemsByUser(user);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching cart items: {0}", e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi tải giỏ hàng. Vui lòng thử lại.");
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            return null;
        }

        if (cartItems == null || cartItems.isEmpty()) {
            LOGGER.log(Level.WARNING, "Cart is empty for user: {0}", user.getUserID());
            request.setAttribute("errorMessage", "Giỏ hàng của bạn trống.");
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            return null;
        }
        return cartItems;
    }

    private List<Cart> filterSelectedItems(List<Cart> cartItems, List<Integer> productIds, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cart> selectedItems = new ArrayList<>();
        for (Cart cart : cartItems) {
            if (cart != null && cart.getProductID() != null && productIds.contains(cart.getProductID().getProductID())) {
                selectedItems.add(cart);
            }
        }

        if (selectedItems.isEmpty()) {
            LOGGER.warning("No selected items found in cart for checkout.");
            request.setAttribute("errorMessage", "Không tìm thấy sản phẩm được chọn trong giỏ hàng.");
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            return null;
        }
        return selectedItems;
    }

    private String extractShippingAddress(HttpServletRequest request, User user) {
        // Lấy thông tin tạm thời từ request
        String tempFullName = request.getParameter("tempFullName");
        String tempPhoneNumber = request.getParameter("tempPhoneNumber");
        String tempAddress = request.getParameter("tempAddress");

        LOGGER.log(Level.INFO, "Temp shipping info - FullName: {0}, PhoneNumber: {1}, Address: {2}", new Object[]{tempFullName, tempPhoneNumber, tempAddress});

        String fullName, phoneNumber, address;

        // Kiểm tra thông tin tạm thời có đầy đủ không
        if (tempFullName != null && !tempFullName.trim().isEmpty() &&
            tempPhoneNumber != null && !tempPhoneNumber.trim().isEmpty() &&
            tempAddress != null && !tempAddress.trim().isEmpty()) {
            fullName = tempFullName.trim();
            phoneNumber = tempPhoneNumber.trim();
            address = tempAddress.trim();
            LOGGER.log(Level.INFO,"Using temporary shipping info.");
        } else {
            // Nếu không có thông tin tạm thời, lấy từ user
            if (user == null) {
                LOGGER.severe("User object is null, cannot extract shipping address.");
                return null;
            }

            fullName = user.getFullName();
            phoneNumber = user.getPhoneNumber();
            address = user.getAddress();

            LOGGER.log(Level.INFO, "Using user shipping info - FullName: {0}, PhoneNumber: {1}, Address: {2}", new Object[]{fullName, phoneNumber, address});

            // Kiểm tra xem thông tin từ user có hợp lệ không
            if (fullName == null || fullName.trim().isEmpty() ||
                phoneNumber == null || phoneNumber.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
                LOGGER.severe("User shipping info is incomplete or null.");
                return null;
            }

            fullName = fullName.trim();
            phoneNumber = phoneNumber.trim();
            address = address.trim();
        }

        // Kiểm tra thông tin giao hàng có hợp lệ không
        if (!isValidShippingInfo(fullName, phoneNumber, address)) {
            LOGGER.log(Level.WARNING, "Shipping info is invalid - FullName: {0}, PhoneNumber: {1}, Address: {2}", new Object[]{fullName, phoneNumber, address});
            return null;
        }

        // Định dạng chuỗi địa chỉ giao hàng
        String shippingAddress = String.format("Họ và tên: %s\nSố điện thoại: %s\nĐịa chỉ Giao Hàng: %s",
                fullName, phoneNumber, address);
        LOGGER.log(Level.INFO, "Formatted shipping address: {0}", shippingAddress);

        return shippingAddress.replace("\n", "<br>");
    }

    private boolean isValidShippingInfo(String fullName, String phoneNumber, String address) {
        // Kiểm tra cơ bản: không null và không rỗng
        if (fullName == null || fullName.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            return false;
        }

        // Kiểm tra định dạng số điện thoại (ví dụ: 10 chữ số, bắt đầu bằng 0)
        if (!phoneNumber.matches("0\\d{9}")) {
            LOGGER.log(Level.WARNING, "Invalid phone number format: {0}", phoneNumber);
            return false;
        }

        // Có thể thêm các kiểm tra khác nếu cần (ví dụ: độ dài tối thiểu của fullName, address)
        return true;
    }

    private PaymentMethod validateAndGetPaymentMethod(HttpServletRequest request, HttpServletResponse response, List<Cart> selectedItems)
            throws ServletException, IOException {
        String paymentMethodIdStr = request.getParameter("paymentMethod");
        if (paymentMethodIdStr == null || paymentMethodIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng chọn phương thức thanh toán.");
            prepareCheckoutPage(request, selectedItems);
            request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
            request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            return null;
        }

        try {
            int paymentMethodId = Integer.parseInt(paymentMethodIdStr);
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
            if (paymentMethod == null) {
                request.setAttribute("errorMessage", "Phương thức thanh toán không hợp lệ.");
                prepareCheckoutPage(request, selectedItems);
                request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
                request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
                return null;
            }
            return paymentMethod;
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Phương thức thanh toán không hợp lệ.");
            prepareCheckoutPage(request, selectedItems);
            request.setAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());
            request.getRequestDispatcher("/views/checkout.jsp").forward(request, response);
            return null;
        }
    }

    private void prepareCheckoutPage(HttpServletRequest request, List<Cart> selectedItems) {
        HttpSession session = request.getSession(false);
        BigDecimal subTotal = calculateTotal(selectedItems);
        BigDecimal discountAmount = session != null ? (BigDecimal) session.getAttribute("discountAmount") : BigDecimal.ZERO;
        BigDecimal total = subTotal.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);

        Map<Integer, BigDecimal> itemTotals = selectedItems.stream()
                .collect(Collectors.toMap(
                        cart -> cart.getProductID().getProductID(),
                        cart -> cart.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        (existing, replacement) -> existing));

        Map<Integer, List<ProductImage>> cartItemImages = selectedItems.stream()
                .collect(Collectors.toMap(
                        cart -> cart.getProductID().getProductID(),
                        cart -> productService.getProductImages(cart.getProductID().getProductID()),
                        (existing, replacement) -> existing));

        request.setAttribute("cartItems", selectedItems);
        request.setAttribute("subTotal", subTotal);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("total", total);
        request.setAttribute("itemTotals", itemTotals);
        request.setAttribute("cartItemImages", cartItemImages);
    }

    private BigDecimal calculateTotal(List<Cart> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (Cart item : items) {
            if (item.getPrice() != null && item.getQuantity() > 0) {
                BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }

    private void clearCheckoutSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("productIds");
            session.removeAttribute("orderId");
            session.removeAttribute("discountAmount");
            session.removeAttribute("discountCode");
            session.removeAttribute("selectedItems"); // Nếu có
            LOGGER.info("Checkout session cleared.");
        }
    }
    private void clearSessionDiscount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("discountAmount");
            session.removeAttribute("discountCode");
            session.removeAttribute("productIds");
            session.removeAttribute("orderId");
            session.removeAttribute("selectedItems"); // Nếu có
            LOGGER.info("All checkout-related session attributes cleared.");
        }
    }
}