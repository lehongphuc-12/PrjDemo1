package service;

import cartDAO.CartDAO;
import cartDAO.ICartDAO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.Cart;
import model.Discount;
import model.Product;
import model.User;


public class CartService {

    private final ICartDAO cartDAO;

    private static final ThreadLocal<Integer> lastAddedSellerId = new ThreadLocal<>();
    public CartService() {
        this.cartDAO = new CartDAO();
    }

    public void addToCart(User user, Product product, int quantity) {
        Cart existingCart = cartDAO.findCartByUserAndProduct(user, product.getProductID());

        if (existingCart != null) {
            int newQuantity = existingCart.getQuantity() + quantity;
            if (product.getQuantity().compareTo(newQuantity) < 0) {
                throw new IllegalArgumentException("Not enough stock available after updating quantity");
            }
            // Cập nhật số lượng và addedDate thay vì xóa và thêm lại
            existingCart.setQuantity(newQuantity);
            existingCart.setAddedDate(new Date()); // Cập nhật thời gian mới nhất
            cartDAO.updateCart(existingCart);
            lastAddedSellerId.set(product.getSellerID().getUserID()); // Lưu sellerID để sắp xếp
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm mới
            Cart cart = new Cart(user, product, quantity, product.getPrice());
            cartDAO.addCart(cart);
            lastAddedSellerId.set(product.getSellerID().getUserID()); // Lưu sellerID
        }
    }

    public static Integer getLastAddedSellerId() {
        return lastAddedSellerId.get();
    }

    public static void clearLastAddedSellerId() {
        lastAddedSellerId.remove();
    }

    public BigDecimal updateCartItem(User user, int productId, int quantity) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than 0");
        Cart cart = cartDAO.findCartByUserAndProduct(user, productId);
        if (cart == null) throw new IllegalArgumentException("Cart item not found for user " + user.getUserID() + " and product " + productId);
        if (cart.getProductID().getQuantity() == null || cart.getProductID().getQuantity().compareTo(Integer.valueOf(quantity)) < 0) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        // Cập nhật số lượng
        cart.setQuantity(quantity);

        // Tính giá mới (giá đã giảm nếu có)
        BigDecimal price = cart.getPrice();
        Discount discount = findDiscountByProduct(productId); // Giả sử bạn có phương thức này
        if (discount != null && discount.getStartDate().before(new java.util.Date()) && discount.getEndDate().after(new java.util.Date())) {
            BigDecimal discountPercent = discount.getDiscountPercent().divide(BigDecimal.valueOf(100));
            price = price.subtract(price.multiply(discountPercent));
        }

        // Cập nhật giá trong cart (nếu cần)
        cart.setPrice(price);
        cartDAO.updateCart(cart);

        return price; // Trả về giá mới
    }

    public void removeCartItem(User user, int productId) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        Cart cart = cartDAO.findCartByUserAndProduct(user, productId);
        if (cart != null) {
            cartDAO.removeCart(cart.getCartID());
        }
    }

    public List<Cart> getCartItemsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        List<Cart> cartItems = cartDAO.findCartByUser(user);
        
        // Lọc các sản phẩm trong giỏ hàng quá 30 ngày
        LocalDateTime now = LocalDateTime.now();
        cartItems = cartItems.stream()
                .filter(cart -> {
                    if (cart.getAddedDate() == null) return true; // Giữ lại nếu không có ngày thêm
                    LocalDateTime addedTime = cart.getAddedDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    return addedTime.plusDays(30).isAfter(now); // Giữ lại nếu chưa quá 30 ngày
                })
                .collect(Collectors.toList());

        // Xóa các sản phẩm quá hạn khỏi cơ sở dữ liệu
        List<Integer> expiredCartIds = cartItems.stream()
                .filter(cart -> cart.getAddedDate() != null && 
                        cart.getAddedDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime()
                                .plusDays(30)
                                .isBefore(now))
                .map(Cart::getCartID)
                .collect(Collectors.toList());
        if (!expiredCartIds.isEmpty()) {
            expiredCartIds.forEach(cartDAO::removeCart);
        }

        return cartItems != null ? cartItems : Collections.emptyList();
    }

    public BigDecimal calculateSubTotal(List<Cart> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) return BigDecimal.ZERO;
        return cartItems.stream()
                .map(item -> {
                    if (item.getPrice() == null) throw new IllegalStateException("Cart item price cannot be null");
                    return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal applyDiscount(User user, String discountCode) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        List<Cart> cartItems = getCartItemsByUser(user);
        if (cartItems.isEmpty()) return BigDecimal.ZERO;
        Discount discount = cartDAO.findDiscountByCode(discountCode);
        if (discount == null) throw new IllegalArgumentException("Invalid or inactive discount code: " + discountCode);
        if (discount.getDiscountPercent() == null) throw new IllegalStateException("Discount percent cannot be null");
        BigDecimal subTotal = calculateSubTotal(cartItems);
        BigDecimal discountPercent = discount.getDiscountPercent().divide(BigDecimal.valueOf(100));
        return subTotal.multiply(discountPercent); // Returns discount amount
    }

    public Cart getCartItemByUserAndProduct(User user, int productId) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        return cartDAO.findCartByUserAndProduct(user, productId); // Direct DAO call for efficiency
    }

    public void clearCart(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        try {
            cartDAO.deleteByUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear cart for user " + user.getUserID(), e);
        }
    }

    public Discount findDiscountByProduct(int productId) {
        return cartDAO.findDiscountByProduct(productId);
    }

    public void clearSelectedItems(User user, List<Integer> productIds) {
        cartDAO.deleteCartItemsByUserAndProductIds(user, productIds);
    }
    public void close() {
        if (cartDAO != null) {
            cartDAO.close();
        }
    }
}