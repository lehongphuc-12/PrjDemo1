
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import model.Category;
import org.hibernate.Hibernate;
import utils.JpaUtil;


public class CategoryDao extends GenericDAO<Category>{
    public CategoryDao () {
        super();
    }
    public List<Category> getCategoryByGroupId(int id) {
    try (EntityManager em = JpaUtil.getEntityManager()) {
        TypedQuery<Category> query = em.createQuery(
            "SELECT c FROM Category c WHERE c.groupID.groupID = :groupID",
            Category.class
        );
        query.setParameter("groupID", id);
        List<Category> list = query.getResultList();
        
        // Ép Hibernate tải dữ liệu trước khi đóng EntityManager
        for (Category c : list) {
            c.getProductCollection().size(); // Truy cập để ép load dữ liệu
        }
        
        return list;
    } catch (Exception e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}


    public static void main(String[] args) {
        CategoryDao cateDao = new CategoryDao();
        for (Category cate : cateDao.getCategoryByGroupId(1)) {
            System.out.println(cate.getCategoryName());
        }
    }
    
    
}
