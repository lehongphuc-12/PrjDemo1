package service;

import model.*;
import jakarta.persistence.EntityManager;
import utils.JpaUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CheckoutService {
    private static final Logger LOGGER = Logger.getLogger(CheckoutService.class.getName());
    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public CheckoutService() {
        this.cartService = new CartService();
        this.orderService = new OrderService();
        this.paymentService = new PaymentService();
    }

    public Order1 processCheckout(User user, List<Cart> cartItems, String shippingAddress, PaymentMethod paymentMethod, BigDecimal discountAmount) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart items cannot be null or empty");
        }
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Shipping address cannot be null or empty");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            LOGGER.log(Level.INFO, "Starting checkout process for user: {0}", user.getUserID());

            BigDecimal subTotal = cartService.calculateSubTotal(cartItems);
            BigDecimal total = subTotal.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }

            OrderStatus pendingStatus = orderService.getStatusByName("Chờ xử lý");
            if (pendingStatus == null) {
                throw new IllegalStateException("Order status 'Pending' not found in database");
            }

            Order1 order = new Order1();
            order.setUserID(user);
            order.setShippingAddress(shippingAddress);
            order.setOrderDate(new Date());
            order.setTotalAmount(total);
            order.setPaymentMethodID(paymentMethod);

            orderService.createOrder(order);
            LOGGER.log(Level.INFO, "Created order with ID: {0}", order.getOrderID());

            List<OrderDetail> orderDetails = cartItems.stream()
                    .map(cart -> new OrderDetail(null, cart.getQuantity(), cart.getPrice(), order, pendingStatus, cart.getProductID()))
                    .collect(Collectors.toList());

            for (OrderDetail detail : orderDetails) {
                orderService.addOrderDetail(detail);
            }
            order.setOrderDetailCollection(orderDetails);
            LOGGER.log(Level.INFO, "Added {0} order details", orderDetails.size());

            Payment payment = new Payment();
            payment.setOrderID(order);
            payment.setPaymentMethodID(paymentMethod);
            payment.setAmount(total);
            payment.setPaymentDate(new Date());
            payment.setStatusID(paymentService.getPaymentStatusByName("Đang xử lý"));
            em.persist(payment);
            LOGGER.log(Level.INFO, "Persisted payment for order ID: {0}", order.getOrderID());

            for (Cart cart : cartItems) {
                Product product = cart.getProductID(); // Đảm bảo getProduct() trả về Product

                int currentStock = product.getQuantity(); // Lấy số lượng hiện có
                int quantityToSubtract = cart.getQuantity(); // Lấy số lượng cần trừ

                int newStock = currentStock - quantityToSubtract; // Tính số lượng mới

                if (newStock < 0) {
                    throw new IllegalStateException("Insufficient stock for product: " + product.getProductName());
                }

                product.setQuantity(newStock); // Cập nhật số lượng mới
                em.merge(product); // Lưu thay đổi vào DB
            }

            LOGGER.log(Level.INFO, "Updated stock for {0} products", cartItems.size());

            em.getTransaction().commit();
            LOGGER.log(Level.INFO, "Checkout completed successfully for order ID: {0}", order.getOrderID());
            return order;
        } catch (IllegalStateException e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Failed to process checkout for user {0}: {1}", new Object[]{user.getUserID(), e.getMessage()});
            throw new RuntimeException(e.getMessage() + "Failed to process checkout: ", e);
        }
    }
}