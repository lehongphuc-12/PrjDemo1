
package userDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Role;
import model.User;
import utils.JpaUtil;

public class UserDAO implements IUserDAO{
     @PersistenceContext
    private EntityManager em;
    public UserDAO(){
        em = JpaUtil.getEntityManager();
    }
    public User findByEmailDAO(String email) {
            if (email == null || email.trim().isEmpty()) {
                return null;
            }
            try {
                TypedQuery<User> query = em.createQuery("FROM User WHERE email = :email", User.class);
                query.setParameter("email", email);
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    public User findByEmailAndPasswordDAO(String email, String password) {
            try {
             TypedQuery<User> query = em.createQuery(
                 "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
             query.setParameter("email", email);
             query.setParameter("password", password);

             List<User> users = query.getResultList();
             return users.isEmpty() ? null : users.get(0);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    }
    public Role findRoleByIdDAO(int id) {
        return em.find(Role.class, id);
}

    @Override
    public User createDAO(User user) {
if (user == null || user.getEmail() == null || user.getEmail().trim().isEmpty()) {
        System.out.println("Lỗi: User hoặc email không hợp lệ!");
        throw new IllegalArgumentException("Thông tin người dùng hoặc email không hợp lệ!");
    }
    System.out.println("Đang tạo user với email: " + user.getEmail());

    EntityTransaction transaction = em.getTransaction();
    try {
        transaction.begin();
        em.persist(user);
        transaction.commit();
        System.out.println("Tạo user thành công: " + user.getEmail());
        return user;
    } catch (Exception e) {
        if (transaction.isActive()) {
            transaction.rollback();
        }
        System.out.println("Lỗi khi tạo user: " + e.getMessage());
        throw new RuntimeException("Lỗi khi tạo người dùng: " + e.getMessage(), e);
    }    }

    

    @Override
    public List<User> findAllDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User updateDAO(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public List<User> getAllUserDAO() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Tìm người dùng theo ID
    @Override
    public User findByIdDAO(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    
    public long countUsers() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
    // Lấy tất cả người dùng
    @Override
    public List<User> findAllDAO(int page, int pageSize) {
        
    EntityManager em = JpaUtil.getEntityManager();
    try {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        query.setFirstResult((page - 1) * pageSize); // Vị trí bắt đầu
        query.setMaxResults(pageSize); // Số lượng bản ghi tối đa
        return query.getResultList();
    } catch (Exception e) {
        System.err.println("Error in findAll: " + e.getMessage());
        return null;
    } finally {
        em.close();
    }
}



    // Xóa người dùng theo ID
    @Override
    public void deleteUserByIdDAO(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id);
            if (user != null) {
                String originalName = user.getFullName();
                String originalAddress = user.getAddress() != null ? user.getAddress() : "";
                // Lưu tên gốc vào Address và đánh dấu FullName là "INACTIVE"
                user.setAddress(originalAddress + " | Original Name: " + originalName);
                user.setFullName("INACTIVE");
                em.merge(user);
            } else {
                throw new IllegalArgumentException("User with ID " + id + " not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    
    @Override
    public List<User> findAllByRoleDAO(int roleID, int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.roleID.roleID = :roleID", User.class);
            query.setParameter("roleID", roleID);
            query.setFirstResult((page - 1) * pageSize); // Vị trí bắt đầu
            query.setMaxResults(pageSize); // Số lượng bản ghi tối đa
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error in findAllByRole: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
    public long countUsersByRoleDAO(int roleID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM User u WHERE u.roleID.roleID = :roleID", Long.class)
                     .setParameter("roleID", roleID)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void restoreUserDAO(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = em.find(User.class, id);
            if (user != null && user.getFullName().equals("INACTIVE")) {
                String address = user.getAddress();
                if (address != null && address.contains("| Original Name: ")) {
                    int index = address.lastIndexOf("| Original Name: ");
                    String originalAddress = address.substring(0, index).trim();
                    String originalName = address.substring(index + "| Original Name: ".length()).trim();
                    user.setFullName(originalName);
                    user.setAddress(originalAddress.equals("|") ? "" : originalAddress);
                    em.merge(user);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    public List<Object[]> getUserRegistrationStatsByMonthDAO(int year, int month) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, 1, 0, 0, 0); // Đặt ngày đầu tháng
            Date startDate = cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date endDate = cal.getTime();

            String jpql = "SELECT DAY(u.createdAt), COUNT(u) " +
                         "FROM User u " +
                         "WHERE u.createdAt BETWEEN :startDate AND :endDate " +
                         "GROUP BY DAY(u.createdAt) " +
                         "ORDER BY DAY(u.createdAt)";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        try {
            List<User> users = dao.getAllUserDAO(); // Gọi phương thức getAllUser
            if (users != null && !users.isEmpty()) {
                System.out.println("Danh sách người dùng trong cơ sở dữ liệu:");
                for (User user : users) {
                    System.out.println("ID: " + user.getUserID() + 
                                     ", Email: " + user.getEmail() + 
                                     ", FullName: " + user.getFullName() + 
                                     ", Password: " + user.getPassword() + // Thêm password
                                     ", RoleID: " + (user.getRoleID() != null ? user.getRoleID().getRoleID() : "N/A") +
                                     ", CreatedAt: " + user.getCreatedAt());
                }
                System.out.println("Tổng số người dùng: " + users.size());
            } else {
                System.out.println("Không có người dùng nào trong cơ sở dữ liệu!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
