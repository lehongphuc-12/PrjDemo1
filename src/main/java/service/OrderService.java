package service;

import orderDAO.IOrderDAO;
import orderDAO.OrderDAO;
import model.*;
import orderStatusDAO.IOrderStatusDAO;
import orderStatusDAO.OrderStatusDAO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import model.ProductImage;
import productImagesDAO.IProductImageDAO;
import productImagesDAO.ProductImageDAO;

public class OrderService {
    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());
    private IOrderDAO orderDAO;
    private IOrderStatusDAO orderStatusDAO;
    private IProductImageDAO productImageDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.orderStatusDAO = new OrderStatusDAO();
        this.productImageDAO = new ProductImageDAO();
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
}