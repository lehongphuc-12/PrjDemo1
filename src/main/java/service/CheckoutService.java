package service;

import model.*;
import jakarta.persistence.EntityManager;
import utils.JpaUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CheckoutService {
    private static final Logger LOGGER = Logger.getLogger(CheckoutService.class.getName());
    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public CheckoutService() {
        this.cartService = new CartService();
        this.productService = new ProductService();
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

            LOGGER.info("Starting checkout process for user: " + user.getUserID());

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
            LOGGER.info("Created order with ID: " + order.getOrderID());

            List<OrderDetail> orderDetails = cartItems.stream()
                    .map(cart -> new OrderDetail(null, cart.getQuantity(), cart.getPrice(), order, pendingStatus, cart.getProductID()))
                    .collect(Collectors.toList());

            for (OrderDetail detail : orderDetails) {
                orderService.addOrderDetail(detail);
            }
            order.setOrderDetailCollection(orderDetails);
            LOGGER.info("Added " + orderDetails.size() + " order details");

            Payment payment = new Payment();
            payment.setOrderID(order);
            payment.setPaymentMethodID(paymentMethod);
            payment.setAmount(total);
            payment.setPaymentDate(new Date());
            payment.setStatusID(paymentService.getPaymentStatusByName("Đang xử lý"));
            em.persist(payment);
            LOGGER.info("Persisted payment for order ID: " + order.getOrderID());

            for (Cart cart : cartItems) {
                Product product = cart.getProductID();
                BigDecimal newStock = product.getQuantity().subtract(BigDecimal.valueOf(cart.getQuantity()));
                if (newStock.compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalStateException("Insufficient stock for product: " + product.getProductName());
                }
                product.setQuantity(newStock);
                em.merge(product);
            }
            LOGGER.info("Updated stock for " + cartItems.size() + " products");

            em.getTransaction().commit();
            LOGGER.info("Checkout completed successfully for order ID: " + order.getOrderID());
            return order;
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.severe("Failed to process checkout for user " + user.getUserID() + ": " + e.getMessage());
            throw new RuntimeException("Failed to process checkout: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}