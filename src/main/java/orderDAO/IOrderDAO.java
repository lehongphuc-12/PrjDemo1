package orderDAO;

import java.math.BigDecimal;
import model.Order1;
import model.OrderDetail;
import java.util.List;
import java.util.Map;

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

    public Map<String, BigDecimal> getDailyData(int sellerID);

    public Map<String, BigDecimal> getWeeklyData(int sellerID);
}