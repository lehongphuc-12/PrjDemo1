package reviewDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Review;
import utils.JpaUtil;

public class ReviewDAO implements IReviewDAO {

    @Override
    public Review create(Review review) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(review);
            em.getTransaction().commit();
            return review;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Review> findByProductId(int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r JOIN r.productID p WHERE p.productID = :productId ORDER BY r.reviewDate DESC", Review.class);
            query.setParameter("productId", productId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    
    public List<Review> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r", Review.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Review update(Review review) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Review updatedReview = em.merge(review);
            em.getTransaction().commit();
            return updatedReview;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int reviewId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Review review = em.find(Review.class, reviewId);
            if (review != null) {
                em.remove(review);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}