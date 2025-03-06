/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


import com.mycompany.demo1_1.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.User;

/**
 *
 * @author ASUS
 */
public class UserRepository implements IUserRepository{

    public User create(User user) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    public User findById(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.find(User.class, id);
        }
    }

    public List<User> findAll() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }

    public User update(User user) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            em.getTransaction().begin();
            User updatedUser = em.merge(user);
            em.getTransaction().commit();
            return updatedUser;
        }
    }

public boolean delete(int id) {
    try (EntityManager em = JpaUtil.getEntityManager()) {
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
            em.getTransaction().commit();
            return true;
        }
        em.getTransaction().commit(); 
        return false;
    } catch (Exception e) {
        return false;
    }
}
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        for (User user :  userRepo.findAll()) {
            System.out.println(user.toString());
        }
    }
    
}
