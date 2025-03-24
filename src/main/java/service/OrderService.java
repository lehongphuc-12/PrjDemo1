package service;

import dao.OrderDetailDao;
import dao.OrderDetailStatusDao;
import orderDAO.IOrderDAO;
import orderDAO.OrderDAO;
import model.*;
import orderStatusDAO.IOrderStatusDAO;
import orderStatusDAO.OrderStatusDAO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import model.ProductImage;
import productImagesDAO.IProductImageDAO;
import productImagesDAO.ProductImageDAO;

public class OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());
    
    private final IOrderDAO orderDAO;
    private final IOrderStatusDAO orderStatusDAO;
    private final IProductImageDAO productImageDAO;
    private final OrderDetailDao orderDetailDao;
    private final OrderDetailStatusDao orderStatus;
    

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.orderStatusDAO = new OrderStatusDAO();
        this.productImageDAO = new ProductImageDAO();
        this.orderDetailDao = new OrderDetailDao();
        this.orderStatus = new OrderDetailStatusDao();
    }

    public Order1 createOrder(User user, List<Cart> selectedItems, String shippingAddress, PaymentMethod paymentMethod, BigDecimal totalAmount, String statusName) {
        if (user == null || selectedItems == null || selectedItems.isEmpty() || shippingAddress == null || paymentMethod == null || totalAmount == null || statusName == null) {
            LOGGER.severe("Invalid input for creating order");
            throw new IllegalArgumentException("Invalid input for creating order");
        }
        
        OrderStatus status = orderStatusDAO.findByName(statusName);
        if (status == null) {
            LOGGER.severe("Order status '" + statusName + "' not found");
            throw new IllegalStateException("Order status '" + statusName + "' not found");
        }
        
        Order1 order = new Order1();
        order.setUserID(user);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setPaymentMethodID(paymentMethod);
        
        orderDAO.createOrder(order);
        
        for (Cart cartItem : selectedItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderID(order);
            detail.setProductID(cartItem.getProductID());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(cartItem.getPrice());
            detail.setStatusID(status);
            orderDAO.addOrderDetail(detail);
        }
        
        return order;
    }

    public void createOrder(Order1 order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        LOGGER.info("Creating order for user ID: " + order.getUserID().getUserID());
        orderDAO.createOrder(order);
    }

    public Order1 getOrderById(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        LOGGER.info("Fetching order with ID: " + orderId);
        Order1 order = orderDAO.getOrderById(orderId);
        if (order == null) {
            throw new IllegalStateException("Order with ID " + orderId + " not found");
        }
        return order;
    }

    public List<Order1> getAllOrders() {
        LOGGER.info("Fetching all orders");
        return orderDAO.getAllOrders();
    }

    public void updateOrder(Order1 order) {
        if (order == null || order.getOrderID() == null) {
            throw new IllegalArgumentException("Order and order ID cannot be null");
        }
        LOGGER.info("Updating order with ID: " + order.getOrderID());
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        LOGGER.info("Deleting order with ID: " + orderId);
        orderDAO.deleteOrder(orderId);
    }

    public List<Order1> getOrdersByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        LOGGER.info("Fetching orders for user ID: " + userId);
        return orderDAO.getOrdersByUserId(userId);
    }

    public OrderStatus getStatusByName(String statusName) {
        if (statusName == null || statusName.trim().isEmpty()) {
            throw new IllegalArgumentException("Status name cannot be null or empty");
        }
        LOGGER.info("Fetching OrderStatus by name: " + statusName);
        try {
            OrderStatus status = orderStatusDAO.findByName(statusName);
            if (status == null) {
                throw new IllegalStateException("Order status '" + statusName + "' not found");
            }
            return status;
        } catch (Exception e) {
            LOGGER.severe("Error retrieving order status: " + e.getMessage());
            throw new RuntimeException("Error retrieving order status: " + e.getMessage(), e);
        }
    }

    public void addOrderDetail(OrderDetail detail) {
        if (detail == null) {
            throw new IllegalArgumentException("Order detail cannot be null");
        }
        if (detail.getOrderID() == null || detail.getProductID() == null || detail.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid order detail: order ID, product ID, and quantity must be valid");
        }
        LOGGER.info("Adding order detail for order ID: " + detail.getOrderID().getOrderID());
        try {
            orderDAO.addOrderDetail(detail);
        } catch (Exception e) {
            LOGGER.severe("Error adding order detail: " + e.getMessage());
            throw new RuntimeException("Error adding order detail: " + e.getMessage(), e);
        }
    }

    public List<ProductImage> getProductImages(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        LOGGER.info("Fetching product images for product ID: " + productId);
        try {
            return productImageDAO.findImagesByProductId(productId);
        } catch (Exception e) {
            LOGGER.severe("Error fetching product images for product ID: " + productId + " - " + e.getMessage());
            throw new RuntimeException("Error fetching product images: " + e.getMessage(), e);
        }
    }
    
    public void updateOrderDetailStatus(int orderDetailId, String statusName) {
        if (orderDetailId <= 0 || statusName == null || statusName.trim().isEmpty()) {
            LOGGER.severe("Invalid order detail ID or status name");
            throw new IllegalArgumentException("Invalid order detail ID or status name");
        }
        OrderStatus status = orderStatusDAO.findByName(statusName);
        if (status == null) {
            LOGGER.severe("Order status '" + statusName + "' not found");
            throw new IllegalStateException("Order status '" + statusName + "' not found");
        }
        orderDAO.updateOrderDetailStatus(orderDetailId, statusName);
    }
    
    public boolean hasPurchased(int userID, int productID){
        return orderDAO.hasPurchased(userID, productID);
    }
    
     public List<OrderDetail> getOrderDetailBySellerId(int id, int page, int size) {
        return orderDetailDao.getOrderDetailBySellerId(id, page, size);
    }

    public List<OrderDetail> getOrderDetailByUserId(int id, int page, int size) {
        return orderDetailDao.getOrderDetailByUserId(id, page, size);
    }

    public double getTotalMoneySell(int id) {

        return getPaidMoneySell(id) + getPendingMoneySell(id);
    }

    // 2. Tiền Đã Thanh Toán
    public double getPaidMoneySell(int id) {
        List<OrderDetail> orderDetails = getOrderDetailBySellerId(id);
        if (orderDetails == null || orderDetails.isEmpty()) {
            return 0.0;
        }

        BigDecimal paidTotal = BigDecimal.ZERO;
        for (OrderDetail od : orderDetails) {
            // Giả sử statusID = 1 là "Đã thanh toán"
            if (od.getStatusID().getStatusID() == 3) {
                BigDecimal itemTotal = od.getPrice();
                paidTotal = paidTotal.add(itemTotal);
            }
        }
        return paidTotal.doubleValue();
    }

    // 3. Tiền Chờ Xử Lý
    public double getPendingMoneySell(int id) {
        List<OrderDetail> orderDetails = getOrderDetailBySellerId(id);
        if (orderDetails == null || orderDetails.isEmpty()) {
            return 0.0;
        }

        BigDecimal pendingTotal = BigDecimal.ZERO;
        for (OrderDetail od : orderDetails) {
            // Giả sử statusID = 2 là "Chờ xử lý"
            if (od.getStatusID().getStatusID() == 1) {
                BigDecimal itemTotal = od.getPrice().multiply(BigDecimal.valueOf(od.getQuantity()));
                pendingTotal = pendingTotal.add(itemTotal);
            }
        }
        return pendingTotal.doubleValue();
    }

    public String getBestSellingProductName(int sellerId) {
        List<OrderDetail> orderDetails = getOrderDetailBySellerId(sellerId);
        if (orderDetails == null || orderDetails.isEmpty()) {
            return "Không có sản phẩm nào được bán";
        }

        // Map để lưu tổng số lượng bán của từng sản phẩm
        Map<Integer, Integer> productQuantityMap = new HashMap<>(); // key: productID, value: totalQuantity
        Map<Integer, String> productNameMap = new HashMap<>();     // key: productID, value: productName

        // Tính tổng số lượng bán cho từng sản phẩm
        for (OrderDetail od : orderDetails) {
            int productId = od.getProductID().getProductID();
            int quantity = od.getQuantity();

            // Cộng dồn số lượng vào map
            productQuantityMap.put(productId, productQuantityMap.getOrDefault(productId, 0) + quantity);
            // Lưu tên sản phẩm (giả sử Product có phương thức getProductName())
            productNameMap.putIfAbsent(productId, od.getProductID().getProductName());
        }

        // Tìm sản phẩm có số lượng bán lớn nhất
        int maxQuantity = 0;
        String bestSellingProductName = null;

        for (Map.Entry<Integer, Integer> entry : productQuantityMap.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                maxQuantity = entry.getValue();
                bestSellingProductName = productNameMap.get(entry.getKey());
            }
        }

        return bestSellingProductName != null ? bestSellingProductName : "Không tìm thấy sản phẩm";
    }

    public List<OrderDetail> getOrderDetailBySellerId(int id) {
        return orderDetailDao.getOrderDetailBySellerId(id);
    }

    public List<OrderDetail> getOrderDetailByUserId(int id) {
        return orderDetailDao.getOrderDetailByUserId(id);
    }

    public int getTotalSellerOrderPage(int id, int size) {
        long totalItems = orderDetailDao.countOrderDetailBySellerId(id);
        System.out.println(totalItems);
        return (int) Math.ceil((double) totalItems / size);
    }

    public int getTotalUserOrderPage(int id, int size) {
        long totalItems = orderDetailDao.countOrderDetailByUserId(id);
        System.out.println(totalItems);
        return (int) Math.ceil((double) totalItems / size);
    }

    public List<OrderDetail> getDonChoXuli(int id, int page, int size, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.getOrderDetailBySellerIdWithStatus(id, page, size, 1);
        } else {
            return orderDetailDao.getOrderDetailByUserIdWithStatus(id, page, size, 1);
        }
    }

    public List<OrderDetail> getDonDangGiao(int id, int page, int size, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.getOrderDetailBySellerIdWithStatus(id, page, size, 2);
        } else {
            return orderDetailDao.getOrderDetailByUserIdWithStatus(id, page, size, 2);
        }

    }

    public List<OrderDetail> getDonDaNhan(int id, int page, int size, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.getOrderDetailBySellerIdWithStatus(id, page, size, 3);
        } else {
            return orderDetailDao.getOrderDetailByUserIdWithStatus(id, page, size, 3);
        }
    }

    public long countDonChoXuLy(int id, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.countOrderDetailBySellerIdAndStatus(id, 1);
        } else {
            return orderDetailDao.countOrderDetailByUserIdAndStatus(id, 1);
        }
    }

    public long countDonDangGiao(int id, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.countOrderDetailBySellerIdAndStatus(id, 2);
        } else {
            return orderDetailDao.countOrderDetailByUserIdAndStatus(id, 2);
        }
    }

    public long countDonDaNhan(int id, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.countOrderDetailBySellerIdAndStatus(id, 3);
        } else {
            return orderDetailDao.countOrderDetailByUserIdAndStatus(id, 3);
        }
    }

    public long countDonDaHuy(int id, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.countOrderDetailBySellerIdAndStatus(id, 4);
        } else {
            return orderDetailDao.countOrderDetailByUserIdAndStatus(id, 4);
        }
    }

    public int getTotalChoXuLyPage(int id, String role, int size) {
        long totalItems = countDonChoXuLy(id, role);
        return (int) Math.ceil((double) totalItems / size);
    }

// Tính số trang cho "Đang giao"
    public int getTotalDangGiaoPage(int id, String role, int size) {
        long totalItems = countDonDangGiao(id, role);
        return (int) Math.ceil((double) totalItems / size);
    }

// Tính số trang cho "Đã nhận"
    public int getTotalDaNhanPage(int id, String role, int size) {
        long totalItems = countDonDaNhan(id, role);
        return (int) Math.ceil((double) totalItems / size);
    }

// Tính số trang cho "Đã hủy"
    public int getTotalDaHuyPage(int id, String role, int size) {
        long totalItems = countDonDaHuy(id, role);
        return (int) Math.ceil((double) totalItems / size);
    }

    public List<OrderDetail> getDonDaHuy(int id, int page, int size, String role) {
        if (role.equalsIgnoreCase("seller")) {
            return orderDetailDao.getOrderDetailBySellerIdWithStatus(id, page, size, 4);
        } else {
            return orderDetailDao.getOrderDetailByUserIdWithStatus(id, page, size, 4);
        }

    }

    public void xacNhanDon(int id) {
        OrderDetail orderItem = orderDetailDao.findById(id);
        OrderStatus status = orderStatus.findById(2);
        orderItem.setStatusID(status);
        orderDetailDao.update(orderItem);
    }

    public void huyDon(int id) {
        OrderDetail orderItem = orderDetailDao.findById(id);
        OrderStatus status = orderStatus.findById(4);
        orderItem.setStatusID(status);
        orderDetailDao.update(orderItem);
        System.out.println("status: " + orderItem.getStatusID().getStatusName());
    }

    public void muaLai(int id) {
        OrderDetail orderItem = orderDetailDao.findById(id);
        OrderStatus status = orderStatus.findById(1);
        orderItem.setStatusID(status);
        orderDetailDao.update(orderItem);
    }

    public void daNhan(int id) {
        OrderDetail orderItem = orderDetailDao.findById(id);
        OrderStatus status = orderStatus.findById(3);
        orderItem.setStatusID(status);
        orderDetailDao.update(orderItem);
    }

    public Map<String, BigDecimal> getWeeklyData(int sellerID) {
        return orderDAO.getWeeklyData(sellerID);
    }

    public Map<String, BigDecimal> getDailyData(int sellerID) {
        return orderDAO.getDailyData(sellerID);
    }
    
    public static void main(String[] args) {
        OrderService o = new OrderService();
        System.out.println(o.getWeeklyData(9));
        System.out.println(o.getDailyData(9));
        
    }
}
