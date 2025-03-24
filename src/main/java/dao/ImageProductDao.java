/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Product;
import model.ProductImage;
import utils.JpaUtil;

/**
 *
 * @author ASUS
 */
public class ImageProductDao extends GenericDAO<ProductImage>{

    public ImageProductDao() {
        super();
    }
            public void insert1(ProductImage image) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(image);
            em.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public List<ProductImage> findByProductId(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<ProductImage> query = (TypedQuery<ProductImage>) em.createQuery("SELECT p FROM ProductImage p WHERE p.productID.productID = :productID",  ProductImage.class);
            query.setParameter("productID", id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void addAllImages() {
        
    }
    public static void main(String[] args) {
        ImageProductDao imageDao = new ImageProductDao();
        for (ProductImage productImg: imageDao.findAll()){
            System.out.println(productImg.getImageURL());
        }
    }
}
