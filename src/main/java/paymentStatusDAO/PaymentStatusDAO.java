/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paymentStatusDAO;

import model.PaymentStatus;
import jakarta.persistence.EntityManager;
import utils.JpaUtil;

public class PaymentStatusDAO implements IPaymentStatusDAO {
    @Override
    public PaymentStatus findByName(String statusName) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT ps FROM PaymentStatus ps WHERE ps.statusName = :statusName", PaymentStatus.class)
                    .setParameter("statusName", statusName)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Return null if not found
        } finally {
            em.close();
        }
    }
}
