package orderStatusDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.OrderStatus;
import utils.JpaUtil;

import java.util.List;

public class OrderStatusDAO implements IOrderStatusDAO {
    private EntityManager entityManager;

    public OrderStatusDAO() {
        // Sử dụng JpaUtil để lấy EntityManager
        this.entityManager = JpaUtil.getEntityManager();
    }

    @Override
    public OrderStatus findByName(String statusName) {
        try {
            TypedQuery<OrderStatus> query = entityManager.createQuery(
                "SELECT os FROM OrderStatus os WHERE os.statusName = :statusName", 
                OrderStatus.class
            );
            query.setParameter("statusName", statusName);
            return query.getSingleResult();
        } catch (Exception e) {
            // Nếu không tìm thấy, trả về null hoặc ném ngoại lệ tùy yêu cầu
            return null;
        }
    }

    @Override
    public OrderStatus findById(int statusId) {
        return entityManager.find(OrderStatus.class, statusId);
    }

    @Override
    public void create(OrderStatus status) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(status);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Error creating OrderStatus: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(OrderStatus status) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(status);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Error updating OrderStatus: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int statusId) {
        try {
            entityManager.getTransaction().begin();
            OrderStatus status = findById(statusId);
            if (status != null) {
                entityManager.remove(status);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Error deleting OrderStatus: " + e.getMessage(), e);
        }
    }

    @Override
    public List<OrderStatus> findAll() {
        TypedQuery<OrderStatus> query = entityManager.createQuery(
            "SELECT os FROM OrderStatus os", 
            OrderStatus.class
        );
        return query.getResultList();
    }
}