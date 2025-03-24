package reviewDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import model.Product;
import model.Review;
import model.User;
import utils.JpaUtil;

public class ReviewDAO implements IReviewDAO {

    @Override
    public Review createDAO(Review review) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(review);
            em.flush();
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
    public List<Review> findByProductIdDAO(int productId) {
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
    public Review updateDAO(Review review) {
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
    public boolean deleteDAO(int reviewId) {
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
            System.out.println(e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
    
    public boolean hasReviewedDAO(int userID, int productID){
        try(EntityManager em = JpaUtil.getEntityManager()){
            String jpql = "Select COUNT(r) from Review r "+
                            "where r.userID.userID = :userID and r.productID.productID = :productID";
            Long check = em.createQuery(jpql,Long.class)
                        .setParameter("userID", userID)
                        .setParameter("productID", productID)
                        .getSingleResult();
            return check > 0;
        }catch (Exception e) {
            System.out.println("ERROR: "+e.getMessage());
            return false;
        }
    }
    
    


    
    public static void main(String[] args) {
        ReviewDAO dao = new ReviewDAO();        
        System.out.println(dao.hasReviewedDAO(125, 4));
        dao.createDAO(new Review(4,"ngon",new Date(),new Product(4),new User(125)));
        System.out.println(dao.findByProductIdDAO(4));
    }
}