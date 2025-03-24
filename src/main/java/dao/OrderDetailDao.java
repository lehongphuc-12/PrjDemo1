/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.OrderDetail;
import utils.JpaUtil;

/**
 *
 * @author ASUS
 */
public class OrderDetailDao extends GenericDAO<OrderDetail> {

    public OrderDetailDao() {
        super();
    }

    public List<OrderDetail> getOrderDetailBySellerId(Integer sellerId, int page, int size) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<OrderDetail> query = em.createNamedQuery("OrderDetail.getOrderDetailBySellerId", OrderDetail.class);
            query.setParameter("sellerID", sellerId);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

public List<OrderDetail> getOrderDetailBySellerIdWithStatus(Integer sellerId, int page, int size, int status) {
    EntityManager em = null;
    try {
        em = JpaUtil.getEntityManager();
        TypedQuery<OrderDetail> query = em.createQuery(
                "SELECT od FROM OrderDetail od WHERE od.productID.sellerID.userID = :sellerId AND od.statusID.statusID = :status",
                OrderDetail.class
        );
        query.setParameter("sellerId", sellerId);
        query.setParameter("status", status); // Đảm bảo tên tham số khớp với truy vấn
        query.setFirstResult((page - 1) * size); // Phân trang: vị trí bắt đầu
        query.setMaxResults(size); // Phân trang: số lượng kết quả tối đa
        return query.getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error fetching order details by seller ID and status: " + e.getMessage(), e);
    } finally {
        if (em != null && em.isOpen()) {
            em.close(); // Đảm bảo đóng EntityManager
        }
    }
}
        public List<OrderDetail> getOrderDetailByUserIdWithStatus(Integer sellerId, int page, int size, int status) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<OrderDetail> query = em.createQuery(
                    "SELECT od FROM  OrderDetail od WHERE od.orderID.userID.userID = :sellerId AND od.statusID.statusID = :statusID",
                    OrderDetail.class
            );
            query.setParameter("sellerId", sellerId);
            query.setParameter("statusID", status);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderDetail> getOrderDetailBySellerId(int sellerId) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<OrderDetail> query = em.createNamedQuery("OrderDetail.getOrderDetailBySellerId", OrderDetail.class);
            query.setParameter("sellerID", sellerId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long countOrderDetailBySellerId(int sellerId) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Query query = em.createQuery(
                    "SELECT COUNT(od) FROM OrderDetail od WHERE od.productID.sellerID.userID = :sellerId"
            );
            query.setParameter("sellerId", sellerId);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long countOrderDetailByUserId(int userId) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Query query = em.createQuery(
                    "SELECT COUNT(od) FROM OrderDetail od WHERE od.orderID.userID.userID = :userId"
            );
            query.setParameter("userId", userId);
            return (long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // Trong OrderDetailDao.java

    public long countOrderDetailBySellerIdAndStatus(int sellerId, int statusID) {
        EntityManager em = JpaUtil.getEntityManager();
        // Logic truy vấn cơ sở dữ liệu để đếm số đơn hàng của sellerId với statusName
        // Ví dụ sử dụng JPA/Hibernate:
        String jpql = "SELECT COUNT(od) FROM OrderDetail od WHERE od.productID.sellerID.userID = :sellerId AND od.statusID.statusID = :statusID";
        return em.createQuery(jpql, Long.class)
                .setParameter("sellerId", sellerId)
                .setParameter("statusID", statusID)
                .getSingleResult();
    }

    public long countOrderDetailByUserIdAndStatus(int userId, int statusID) {
        EntityManager em = JpaUtil.getEntityManager();

        // Logic truy vấn cơ sở dữ liệu để đếm số đơn hàng của userId với statusName
        String jpql = "SELECT COUNT(od) FROM OrderDetail od WHERE od.orderID.userID.userID = :userId AND od.statusID.statusID = :statusID";
        return em.createQuery(jpql, Long.class)
                .setParameter("userId", userId)
                .setParameter("statusID", statusID)
                .getSingleResult();
    }

    public List<OrderDetail> getOrderDetailByUserId(int userId) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<OrderDetail> query = em.createNamedQuery("OrderDetail.getOrderDetailByUserId", OrderDetail.class);
            query.setParameter("userID", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderDetail> getOrderDetailByUserId(Integer userId, int page, int size) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<OrderDetail> query = em.createNamedQuery("OrderDetail.getOrderDetailByUserId", OrderDetail.class);
            query.setParameter("userID", userId);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        OrderDetailDao orderDetailDao = new OrderDetailDao();
        for (OrderDetail orde: orderDetailDao.getOrderDetailByUserIdWithStatus(1, 1, 10, 3)){
            System.out.println(orde.getProductID().getProductName());
        }
        List <OrderDetail> list = orderDetailDao.getOrderDetailBySellerId(9);
        System.out.println(list);
    }

}
