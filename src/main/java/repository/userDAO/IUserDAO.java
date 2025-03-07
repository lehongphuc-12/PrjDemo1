
package repository.userDAO;


import repository.productDAO.*;
import java.util.List;
import model.Product;

public interface IUserDAO {
    public Product create(Product user);
    public Product findById(int id);
    public List<Product> findAll();
    public Product update(Product user);
}
