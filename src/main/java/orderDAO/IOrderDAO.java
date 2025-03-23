package orderDAO;

import model.Order1;
import model.OrderDetail;
import java.util.List;

public interface IOrderDAO {
    void createOrder(Order1 order);
    Order1 getOrderById(int orderId);
    List<Order1> getAllOrders();
    void updateOrder(Order1 order);
    void deleteOrder(int orderId);
    List<Order1> getOrdersByUserId(int userId);
    void addOrderDetail(OrderDetail detail);
    void updateOrderDetailStatus(int orderDetailId, String statusName);
    boolean hasPurchased(int userID, int productID);
}