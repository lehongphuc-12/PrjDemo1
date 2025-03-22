/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paymentMethodDAO;

import model.PaymentMethod;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.JpaUtil;
import java.util.List;

public class PaymentMethodDAO implements IPaymentMethodDAO {

    @Override
    public void create(PaymentMethod paymentMethod) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(paymentMethod);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public PaymentMethod findById(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(PaymentMethod.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<PaymentMethod> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<PaymentMethod> query = em.createNamedQuery("PaymentMethod.findAll", PaymentMethod.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(PaymentMethod paymentMethod) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(paymentMethod);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            PaymentMethod paymentMethod = em.find(PaymentMethod.class, id);
            if (paymentMethod != null) {
                em.remove(paymentMethod);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public PaymentMethod findByCode(String code) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<PaymentMethod> query = em.createQuery(
                "SELECT p FROM PaymentMethod p WHERE p.methodName = :methodName", PaymentMethod.class);
            query.setParameter("methodName", code);
            return query.getResultList().stream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
}