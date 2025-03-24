/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Product;
import model.ProductImage;
import utils.JpaUtil;

/**
 *
 * @author ASUS
 */
public class ProductDao extends GenericDAO<Product> {

    public ProductDao() {
        super();
    }

    public void insert1(Product product) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(product);
            em.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Product> findBySellerId(int id, int page, int size) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.sellerID.userID = :sellerId", Product.class);
            query.setParameter("sellerId", id);

            // Thiết lập phân trang
            query.setFirstResult((page - 1) * size); // Vị trí bắt đầu (offset) - sửa lại công thức
            query.setMaxResults(size); // Số lượng bản ghi tối đa mỗi trang

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> findBySellerId(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.sellerID.userID = :sellerId", Product.class);
            query.setParameter("sellerId", id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> findInactiveBySellerId(int id, int page, int size) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery(
                    "SELECT p FROM Product p WHERE p.sellerID.userID = :sellerId AND p.status = false",
                    Product.class
            );
            query.setParameter("sellerId", id);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Product> findActiveBySellerId(int id, int page, int size) {
    try (EntityManager em = JpaUtil.getEntityManager()) {
        TypedQuery<Product> query = em.createQuery(
            "SELECT p FROM Product p WHERE p.sellerID.userID = :sellerId AND p.status = true", 
            Product.class
        );
        query.setParameter("sellerId", id);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    public long countBySellerActiveId(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Query query = em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.sellerID.userID = :sellerId AND p.status = true");
            query.setParameter("sellerId", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public long countBySellerInActiveId(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Query query = em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.sellerID.userID = :sellerId AND p.status = false");
            query.setParameter("sellerId", id);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void insertProductWithImages(Product product) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(product);
            em.flush(); // Đảm bảo ID của Product được tạo trước khi lưu ảnh
            tx.commit();
            tx.begin();
            System.out.println("Số lượng ảnh trước khi lưu: " + product.getProductImageCollection().size());
            for (ProductImage img : product.getProductImageCollection()) {
                System.out.println("Ảnh: " + img.getImageURL());
                img.setProductID(product); // Đảm bảo liên kết chính xác
                em.persist(img);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ProductDao dao = new ProductDao();
        System.out.println(dao.findActiveBySellerId(8, 1, 10));
    }
}
