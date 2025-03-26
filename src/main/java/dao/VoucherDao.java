/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.Discount;

/**
 *
 * @author ASUS
 */
public class VoucherDao extends GenericDAO<Discount>{

    public VoucherDao() {
        super();
    }
    public List<Discount> getAllVouchersBySeller(int sellerId) {
        EntityManager em = getEntityManager();
        try {
            // JPQL truy vấn
            String jpql = "SELECT v FROM Discount v JOIN v.productID p WHERE p.sellerID.userID = :sellerId";
            TypedQuery<Discount> query = em.createQuery(jpql, Discount.class);
            query.setParameter("sellerId", sellerId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Trả về danh sách rỗng nếu có lỗi
        } finally {
            em.close();
        }
    }
    public static void main(String[] args) {
        VoucherDao vouchers = new VoucherDao();
        System.out.println(vouchers.getAllVouchersBySeller(2));
        
    }
}
