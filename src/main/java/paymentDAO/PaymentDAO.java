package paymentDAO;

import model.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.PaymentStatus;
import utils.JpaUtil;

public class PaymentDAO implements IPaymentDAO {

    @Override
    public void create(Payment payment) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(payment);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to create payment: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    public List<Payment> findByOrderId(int orderId) {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Payment> query = em.createQuery(
            "SELECT p FROM Payment p WHERE p.orderID.orderID = :orderId", Payment.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    
}