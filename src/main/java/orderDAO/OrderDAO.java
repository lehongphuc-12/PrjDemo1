package orderDAO;

import model.Order1;
import model.OrderDetail;
import model.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.JpaUtil;
import java.util.List;
import java.util.logging.Logger;

public class OrderDAO implements IOrderDAO {
    private static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    @Override
    public void createOrder(Order1 order) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.flush();
            em.getTransaction().commit();
            LOGGER.info("Created order with ID: " + order.getOrderID());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to create order", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Order1 getOrderById(int orderId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT o FROM Order1 o " +
                    "LEFT JOIN FETCH o.orderDetailCollection " +
                    "LEFT JOIN FETCH o.paymentMethodID " +
                    "LEFT JOIN FETCH o.userID " +
                    "WHERE o.orderID = :orderId",
                    Order1.class)
                    .setParameter("orderId", orderId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch order by ID", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order1> getAllOrders() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Order1> query = em.createNamedQuery("Order1.findAll", Order1.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all orders", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateOrder(Order1 order) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
            LOGGER.info("Updated order with ID: " + order.getOrderID());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update order", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Order1 order = em.find(Order1.class, orderId);
            if (order != null) {
                em.remove(order);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to delete order", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order1> getOrdersByUserId(int userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Order1> query = em.createQuery(
                    "SELECT o FROM Order1 o " +
                    "LEFT JOIN FETCH o.orderDetailCollection " +
                    "LEFT JOIN FETCH o.paymentMethodID " +
                    "LEFT JOIN FETCH o.userID " +
                    "WHERE o.userID.userID = :userId",
                    Order1.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch orders for user ID: " + userId, e);
        } finally {
            em.close();
        }
    }

    @Override
    public void addOrderDetail(OrderDetail detail) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(detail);
            em.getTransaction().commit();
            LOGGER.info("Added order detail for order ID: " + detail.getOrderID().getOrderID());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to add order detail", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateOrderDetailStatus(int orderDetailId, String statusName) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            OrderDetail orderDetail = em.find(OrderDetail.class, orderDetailId);
            if (orderDetail != null) {
                OrderStatus status = em.createQuery("SELECT s FROM OrderStatus s WHERE s.statusName = :statusName", OrderStatus.class)
                                       .setParameter("statusName", statusName)
                                       .getSingleResult();
                if (status != null) {
                    orderDetail.setStatusID(status);
                    em.merge(orderDetail);
                    LOGGER.info("Updated order detail status to: " + statusName + " for order detail ID: " + orderDetailId);
                } else {
                    LOGGER.warning("Order status '" + statusName + "' not found");
                }
            } else {
                LOGGER.warning("Order detail with ID: " + orderDetailId + " not found");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update order detail status", e);
        } finally {
            em.close();
        }
    }
    
    public boolean hasPurchased(int userID, int productID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(od) FROM OrderDetail od " +
                          "JOIN od.orderID o " +
                          "WHERE o.userID.userID = :userID " +
                          "AND od.productID.productID = :productID " +
                          "AND od.statusID.statusID = 3";  // Sửa lại so sánh statusID đúng cách

            Long count = em.createQuery(jpql, Long.class)
                           .setParameter("userID", userID)
                           .setParameter("productID", productID)
                           .getSingleResult();
            return count > 0; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}