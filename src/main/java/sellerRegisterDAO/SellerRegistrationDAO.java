package sellerRegistrationDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.SellerRegistrationRequest;
import utils.JpaUtil;

import java.util.List;

public class SellerRegistrationDAO implements ISellerRegistrationDAO {
    private EntityManager em;

    public SellerRegistrationDAO() {
        em = JpaUtil.getEntityManager();
    }

    @Override
    public void createDAO(SellerRegistrationRequest request) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(request);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to create seller registration request: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SellerRegistrationRequest> findAllPendingDAO() {
        TypedQuery<SellerRegistrationRequest> query = em.createNamedQuery(
            "SellerRegistrationRequest.findByStatus", SellerRegistrationRequest.class
        );
        query.setParameter("status", "pending");
        return query.getResultList();
    }

    public List<SellerRegistrationRequest> findAllPendingDAO(int page, int pageSize) {
        TypedQuery<SellerRegistrationRequest> query = em.createNamedQuery(
            "SellerRegistrationRequest.findByStatus", SellerRegistrationRequest.class
        );
        query.setParameter("status", "pending");
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long countPendingRequestsDAO() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(s) FROM SellerRegistrationRequest s WHERE s.status = :status", Long.class
        );
        query.setParameter("status", "pending");
        return query.getSingleResult();
    }

    @Override
    public SellerRegistrationRequest findByIdDAO(int requestID) {
        return em.find(SellerRegistrationRequest.class, requestID);
    }

    @Override
    public void updateDAO(SellerRegistrationRequest request) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(request);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to update seller registration request: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}