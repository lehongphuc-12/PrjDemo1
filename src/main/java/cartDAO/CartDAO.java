package cartDAO;

import model.Cart;
import model.Discount;
import model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import utils.JpaUtil;

public class CartDAO implements ICartDAO {

    private EntityManager getEntityManager() {
        return JpaUtil.getEntityManager();
    }

    @Override
    public void addCart(Cart cart) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // Detach related entities to prevent lazy loading
            if (cart.getUserID() != null) {
                em.detach(cart.getUserID());
            }
            if (cart.getProductID() != null) {
                em.detach(cart.getProductID());
            }
            em.persist(cart);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void updateCart(Cart cart) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cart);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void removeCart(int cartId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cart cart = em.find(Cart.class, cartId);
            if (cart != null) {
                em.remove(cart);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Cart findCartById(Integer cartId) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cart.class, cartId);
        } finally {
            em.close();
        }
    }

    @Override
    public Cart findCartByUserAndProduct(User user, int productID) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Cart> query = em.createQuery(
                "SELECT c FROM Cart c WHERE c.userID = :user AND c.productID.productID = :productID", Cart.class);
            query.setParameter("user", user);
            query.setParameter("productID", productID);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Discount findDiscountByCode(String discountCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Discount> query = em.createQuery(
                "SELECT d FROM Discount d WHERE d.discountCode = :code AND d.startDate <= :now AND d.endDate >= :now", Discount.class);
            query.setParameter("code", discountCode);
            query.setParameter("now", LocalDateTime.now());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cart> findCartByUser(User user) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Cart> query = em.createQuery(
                "SELECT c FROM Cart c " +
                "JOIN FETCH c.productID p " +
                "LEFT JOIN FETCH p.discountCollection d " +
                "JOIN FETCH p.sellerID " +
                "JOIN FETCH p.categoryID " +
                "WHERE c.userID = :user " +
                "ORDER BY c.addedDate DESC", Cart.class); 
            query.setParameter("user", user);
            List<Cart> result = query.getResultList();
            System.out.println("Retrieved " + (result != null ? result.size() : 0) + " cart items for user " + (user != null ? user.getUserID() : "null"));
            return result != null ? result : Collections.emptyList();
        }
    }
    @Override
    public Discount findDiscountByProduct(int productId) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Discount> query = em.createQuery(
                "SELECT d FROM Discount d WHERE d.productID.productID = :productId " +
                "AND d.startDate <= :now AND d.endDate >= :now", Discount.class);
            query.setParameter("productId", productId);
            query.setParameter("now", LocalDateTime.now());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void deleteByUser(User user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Cart c WHERE c.userID = :user")
                .setParameter("user", user)
                .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete cart items for user", e);
        } finally {
            em.close();
        }
    }

    public void deleteCartItemsByUserAndProductIds(User user, List<Integer> productIds) { 
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Cart> query = em.createQuery(
                "SELECT c FROM Cart c WHERE c.userID = :user AND c.productID.productID IN :productIds", Cart.class);
            query.setParameter("user", user);
            query.setParameter("productIds", productIds);
            List<Cart> cartItemsToDelete = query.getResultList();
            for (Cart cartItem : cartItemsToDelete) {
                em.remove(em.merge(cartItem));
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error deleting cart items: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void close() {
        // Không cần đóng EntityManager ở đây vì mỗi phương thức đã tự quản lý
        // Chỉ đóng EntityManagerFactory nếu cần
        JpaUtil.close();
    }
}