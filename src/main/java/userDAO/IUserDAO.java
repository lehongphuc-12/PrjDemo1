
package userDAO;


import java.util.List;
import model.Product;
import model.User;

public interface IUserDAO {
    public User createDAO(User user);
    public User findByIdDAO(int id);
    public List<User> findAllDAO();
    public void updateDAO(User user);
    public List<User> findAllDAO(int page, int pageSize);
    public void deleteUserByIdDAO(int id);
    public List<User> findAllByRoleDAO(int roleID, int page, int pageSize);
    public void restoreUserDAO(int id);
}
