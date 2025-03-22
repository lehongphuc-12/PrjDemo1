package orderStatusDAO;

import java.util.List;
import model.OrderStatus;

public interface IOrderStatusDAO {
    OrderStatus findByName(String statusName);
    OrderStatus findById(int statusId);
    void create(OrderStatus status);
    void update(OrderStatus status);
    void delete(int statusId);
    List<OrderStatus> findAll();
}