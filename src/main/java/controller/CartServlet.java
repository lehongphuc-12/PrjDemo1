package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Discount;
import model.Product;
import model.ProductImage;
import model.User;
import service.CartService;
import service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductService();
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        handleRequest(request, response, action != null ? action : "view");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        handleRequest(request, response, action != null ? action : "view");
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        switch (action) {
            case "view": viewCart(request, response); break;
            case "add": addToCart(request, response); break;
            case "buyNow": buyNow(request, response); break;
            case "remove": removeFromCart(request, response); break;
            case "removeMultiple": removeMultipleFromCart(request, response); break;
            case "update": updateCart(request, response); break;
            case "clear": clearCart(request, response); break;
            case "applyDiscount": applyDiscount(request, response); break;
            case "checkout": checkout(request, response); break;
            default: viewCart(request, response); break;
        }
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        LOGGER.info("Viewing cart for user: " + user.getUserID());

        List<Cart> cartItems = null;
        try {
            cartItems = cartService.getCartItemsByUser(user);
        } catch (Exception e) {
            LOGGER.severe("Error fetching cart items: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi tải giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            return;
        }
        if (cartItems == null) {
            cartItems = Collections.emptyList();
        }
        // Nhóm cartItems theo sellerID
        Map<Integer, List<Cart>> groupedCartItems = groupCartItemsBySellerID(cartItems);

        // Lấy thông tin seller (tên shop)
        Map<Integer, String> sellerNames = new HashMap<>();
        for (Cart cartItem : cartItems) {
            if (cartItem != null && cartItem.getProductID() != null && cartItem.getProductID().getSellerID() != null) {
                User seller = cartItem.getProductID().getSellerID();
                sellerNames.put(seller.getUserID(), seller.getFullName()); // Giả sử User có getUsername()
            }
        }

        Map<Integer, List<ProductImage>> cartItemImages = new HashMap<>();
        Map<Integer, BigDecimal> discountedPrices = new HashMap<>();
        Map<Integer, BigDecimal> itemTotals = new HashMap<>();
        BigDecimal productDiscounts = BigDecimal.ZERO;

        for (Cart cartItem : cartItems) {
            if (cartItem == null) {
                LOGGER.warning("Found null cartItem, skipping...");
                continue;
            }

            Product product = cartItem.getProductID();
            if (product == null) {
                LOGGER.warning("Product is null for cartItem: " + cartItem.getCartID());
                continue;
            }

            List<ProductImage> images = null;
            try {
                images = productService.getProductImages(product.getProductID());
            } catch (Exception e) {
                LOGGER.severe("Error fetching images for product " + product.getProductID() + ": " + e.getMessage());
            }
            cartItemImages.put(product.getProductID(), images != null ? images : Collections.emptyList());

            BigDecimal originalPrice = cartItem.getPrice() != null ? cartItem.getPrice() : BigDecimal.ZERO;
            BigDecimal discountedPrice = originalPrice;
            Discount discount = null;
            try {
                discount = cartService.findDiscountByProduct(product.getProductID());
            } catch (Exception e) {
                LOGGER.severe("Error fetching discount for product " + product.getProductID() + ": " + e.getMessage());
            }
            if (discount != null && discount.getStartDate().before(new java.util.Date())
                    && discount.getEndDate().after(new java.util.Date())) {
                BigDecimal discountPercent = discount.getDiscountPercent() != null
                        ? discount.getDiscountPercent().divide(BigDecimal.valueOf(100))
                        : BigDecimal.ZERO;
                discountedPrice = originalPrice.subtract(originalPrice.multiply(discountPercent));
            }
            discountedPrices.put(product.getProductID(), discountedPrice);

            BigDecimal itemTotal = BigDecimal.ZERO;
            if (cartItem.getPrice() != null) {
                itemTotal = discountedPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            }
            itemTotals.put(product.getProductID(), itemTotal);

            BigDecimal original = originalPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            BigDecimal discounted = discountedPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            productDiscounts = productDiscounts.add(original.subtract(discounted));
        }

        BigDecimal subTotal = BigDecimal.ZERO;
        try {
            subTotal = cartService.calculateSubTotal(cartItems);
        } catch (Exception e) {
            LOGGER.severe("Error calculating subtotal: " + e.getMessage());
        }
        BigDecimal discountAmount = (BigDecimal) session.getAttribute("discountAmount");
        discountAmount = discountAmount != null ? discountAmount.add(productDiscounts) : productDiscounts;
        session.setAttribute("discountAmount", discountAmount);
        BigDecimal total = subTotal.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("groupedCartItems", groupedCartItems);
        request.setAttribute("sellerNames", sellerNames); // Truyền tên seller vào JSP
        request.setAttribute("cartItemImages", cartItemImages);
        request.setAttribute("discountedPrices", discountedPrices);
        request.setAttribute("itemTotals", itemTotals);
        request.setAttribute("subTotal", subTotal);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("total", total);

        LOGGER.info("Forwarding to /views/cart.jsp with cartItems size: " + cartItems.size());
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
    }
    private Map<Integer, List<Cart>> groupCartItemsBySellerID(List<Cart> cartItems) {
        return cartItems.stream()
                .filter(cartItem -> cartItem != null && cartItem.getProductID() != null && cartItem.getProductID().getSellerID() != null)
                .collect(Collectors.groupingBy(
                        cartItem -> cartItem.getProductID().getSellerID().getUserID(),
                        HashMap::new,
                        Collectors.toList()
                ));
    }
    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productID"));
        int quantity = Integer.parseInt(request.getParameter("quantity") != null ? request.getParameter("quantity") : "1");

        if (productId <= 0 || quantity <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID sản phẩm hoặc số lượng không hợp lệ.");
            return;
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Sản phẩm không tồn tại.");
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return;
        }

        try {
            cartService.addToCart(user, product, quantity);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("success");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
        }
    }

    private void buyNow(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Bạn cần đăng nhập để mua ngay.");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productID"));
        int quantity = Integer.parseInt(request.getParameter("quantity") != null ? request.getParameter("quantity") : "1");

        Product product = productService.getProductById(productId);
        if (product == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Sản phẩm không tồn tại.");
            return;
        }

        if (product.getQuantity().compareTo(BigDecimal.valueOf(quantity)) < 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Sản phẩm không đủ số lượng.");
            return;
        }

        try {
            cartService.addToCart(user, product, quantity);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("success");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi khi mua ngay: " + e.getMessage());
        }
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        int productId = parseIntParameter(request, "productID");
        if (productId == -1) {
            request.setAttribute("errorMessage", "Invalid product ID.");
            viewCart(request, response);
            return;
        }

        try {
            cartService.removeCartItem(user, productId);
            response.sendRedirect(request.getContextPath() + "/cart?action=view");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to remove item: " + e.getMessage());
            viewCart(request, response);
        }
    }

    private void removeMultipleFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        String productIdsParam = request.getParameter("productIds");
        if (productIdsParam == null || productIdsParam.isEmpty()) {
            request.setAttribute("errorMessage", "Không có sản phẩm nào được chọn để xóa.");
            viewCart(request, response);
            return;
        }

        try {
            String[] productIds = productIdsParam.split(",");
            for (String productIdStr : productIds) {
                int productId = Integer.parseInt(productIdStr.trim());
                cartService.removeCartItem(user, productId);
            }
            request.setAttribute("successMessage", "Đã xóa các sản phẩm được chọn.");
            viewCart(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
            viewCart(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể xóa sản phẩm: " + e.getMessage());
            viewCart(request, response);
        }
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Vui lòng đăng nhập.\"}");
            return;
        }

        int productId = parseIntParameter(request, "productID");
        int newQuantity = parseIntParameter(request, "newQuantity", 1);

        if (productId == -1 || newQuantity <= 0) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"ID sản phẩm hoặc số lượng không hợp lệ.\"}");
            return;
        }

        try {
            BigDecimal newPrice = cartService.updateCartItem(user, productId, newQuantity);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"newPrice\": " + newPrice.toString() + "}");
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi khi cập nhật giỏ hàng: " + e.getMessage() + "\"}");
        }
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        try {
            cartService.clearCart(user);
            session.removeAttribute("discountAmount");
            response.sendRedirect(request.getContextPath() + "/cart?action=view");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to clear cart: " + e.getMessage());
            viewCart(request, response);
        }
    }

    private void applyDiscount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        String discountCode = request.getParameter("discountCode");
        if (discountCode == null || discountCode.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter a valid coupon code.");
            viewCart(request, response);
            return;
        }

        try {
            BigDecimal discountAmount = cartService.applyDiscount(user, discountCode);
            session.setAttribute("discountAmount", discountAmount);
            response.sendRedirect(request.getContextPath() + "/cart?action=view");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Invalid or expired coupon code: " + e.getMessage());
            viewCart(request, response);
        }
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || session.isNew()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String productIdsParam = request.getParameter("productIds");
        if (productIdsParam == null || productIdsParam.trim().isEmpty()) {
            request.setAttribute("errorMessage", "No products selected for checkout.");
            viewCart(request, response);
            return;
        }

        String[] productIds = productIdsParam.split(",");
        List<Integer> validProductIds = new ArrayList<>();
        for (String id : productIds) {
            try {
                validProductIds.add(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid product ID: " + id);
            }
        }

        if (validProductIds.isEmpty()) {
            request.setAttribute("errorMessage", "No valid products for checkout.");
            viewCart(request, response);
            return;
        }

        List<Cart> cartItems = cartService.getCartItemsByUser(user);
        if (cartItems != null && !cartItems.isEmpty()) {
            List<Integer> cartProductIds = cartItems.stream()
                    .map(cart -> cart.getProductID().getProductID())
                    .collect(Collectors.toList());
            validProductIds.removeIf(id -> !cartProductIds.contains(id));
            if (validProductIds.isEmpty()) {
                request.setAttribute("errorMessage", "Selected products not found in cart.");
                viewCart(request, response);
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/checkout?productIds=" + URLEncoder.encode(productIdsParam, StandardCharsets.UTF_8.toString()));
    }

    private int parseIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        try {
            String value = request.getParameter(paramName);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int parseIntParameter(HttpServletRequest request, String paramName) {
        return parseIntParameter(request, paramName, -1);
    }
}