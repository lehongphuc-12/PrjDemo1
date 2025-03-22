/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productImagesDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.ProductImage;
import utils.JpaUtil;

public class ProductImageDAO implements IProductImageDAO {

    public ProductImageDAO() {
    }


    @Override
    public ProductImage create(ProductImage productImage) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(productImage);
            em.getTransaction().commit();
            return productImage;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<ProductImage> findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT pi FROM ProductImage pi WHERE pi.id = :id", ProductImage.class)
                     .setParameter("id", id)
                     .getResultList();
        } finally {
            em.close();
        }
    }


    @Override
    public List<ProductImage> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<ProductImage> query = em.createQuery("SELECT p FROM ProductImage p", ProductImage.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public ProductImage update(ProductImage productImage) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ProductImage updatedProductImage = em.merge(productImage);
            em.getTransaction().commit();
            return updatedProductImage;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            ProductImage productImage = em.find(ProductImage.class, id);
            if (productImage != null) {
                em.remove(productImage);
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
    @Override
    public List<ProductImage> findImagesByProductId(int productID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<ProductImage> query = em.createQuery(
                "SELECT pi FROM ProductImage pi WHERE pi.productID.productID = :productID", ProductImage.class);
            query.setParameter("productID", productID);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

