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
        // Kiểm tra dữ liệu đầu vào
        if (user == null) {
            throw new IllegalArgumentException("Người dùng không được để trống");
        }
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm trong giỏ hàng không được để trống hoặc rỗng");
        }
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ giao hàng không được để trống");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Phương thức thanh toán không được để trống");
        }
        if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Số tiền giảm giá không được âm");
        }

        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            LOGGER.log(Level.INFO, "Bắt đầu quá trình thanh toán cho người dùng: {0}", user.getUserID());

            // Tính toán tổng tiền
            BigDecimal subTotal = cartService.calculateSubTotal(cartItems);
            BigDecimal total = subTotal.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }

            // Lấy trạng thái đơn hàng "Chờ xử lý"
            OrderStatus pendingStatus = orderService.getStatusByName("Chờ xử lý");
            if (pendingStatus == null) {
                throw new IllegalStateException("Không tìm thấy trạng thái đơn hàng 'Chờ xử lý' trong cơ sở dữ liệu");
            }

            // Tạo đơn hàng mới
            Order1 order = new Order1();
            order.setUserID(user);
            order.setShippingAddress(shippingAddress);
            order.setOrderDate(new Date());
            order.setTotalAmount(total);
            order.setPaymentMethodID(paymentMethod);

            orderService.createOrder(order);
            if (order.getOrderID() <= 0) {
                throw new IllegalStateException("Không thể tạo mã đơn hàng hợp lệ");
            }
            LOGGER.log(Level.INFO, "Đã tạo đơn hàng với mã: {0}", order.getOrderID());

            // Tạo danh sách chi tiết đơn hàng
            List<OrderDetail> orderDetails = cartItems.stream()
                .map(cart -> {
                    Product product = cart.getProductID();
                    if (product == null || product.getProductID() <= 0) {
                        throw new IllegalArgumentException("Mã sản phẩm không hợp lệ trong giỏ hàng");
                    }
                    if (cart.getQuantity() <= 0) {
                        throw new IllegalArgumentException("Số lượng sản phẩm '" + product.getProductName() + "' không hợp lệ");
                    }
                    return new OrderDetail(null, cart.getQuantity(), cart.getPrice(), order, pendingStatus, product);
                })
                .collect(Collectors.toList());

            // Thêm chi tiết đơn hàng
            for (OrderDetail detail : orderDetails) {
                orderService.addOrderDetail(detail);
            }
            order.setOrderDetailCollection(orderDetails);
            LOGGER.log(Level.INFO, "Đã thêm {0} chi tiết đơn hàng", orderDetails.size());

            // Tạo thông tin thanh toán
            Payment payment = new Payment();
            payment.setOrderID(order);
            payment.setPaymentMethodID(paymentMethod);
            payment.setAmount(total);
            payment.setPaymentDate(new Date());
            payment.setStatusID(paymentService.getPaymentStatusByName("Đang xử lý"));
            if (payment.getStatusID() == null) {
                throw new IllegalStateException("Không tìm thấy trạng thái thanh toán 'Đang xử lý' trong cơ sở dữ liệu");
            }
            em.persist(payment);
            LOGGER.log(Level.INFO, "Đã lưu thông tin thanh toán cho đơn hàng mã: {0}", order.getOrderID());

            // Cập nhật số lượng tồn kho
            for (Cart cart : cartItems) {
                Product product = cart.getProductID();
                int currentStock = product.getQuantity();
                int quantityToSubtract = cart.getQuantity();
                int newStock = currentStock - quantityToSubtract;
                if (newStock < 0) {
                    throw new IllegalStateException("Không đủ hàng trong kho cho sản phẩm: " + product.getProductName());
                }
                product.setQuantity(newStock);
                em.merge(product);
            }
            LOGGER.log(Level.INFO, "Đã cập nhật tồn kho cho {0} sản phẩm", cartItems.size());

            // Hoàn tất giao dịch
            em.getTransaction().commit();
            LOGGER.log(Level.INFO, "Thanh toán hoàn tất thành công cho đơn hàng mã: {0}", order.getOrderID());
            return order;

        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Dữ liệu không hợp lệ khi thanh toán cho người dùng {0}: {1}", new Object[]{user.getUserID(), e.getMessage()});
            throw e; // Ném lại để CheckoutServlet xử lý
        } catch (IllegalStateException e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Lỗi khi xử lý thanh toán cho người dùng {0}: {1}", new Object[]{user.getUserID(), e.getMessage()});
            throw e;
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.log(Level.SEVERE, "Lỗi không mong muốn khi thanh toán cho người dùng {0}: {1}", new Object[]{user.getUserID(), e.getMessage()});
            throw new RuntimeException("Lỗi khi xử lý thanh toán: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}