
package productDAO;


import java.util.List;
import model.Product;


public interface IProductDAO {
    public Product create(Product user);
    public Product findById(int id);
    public List<Product> findAll();
    public Product update(Product user);
}
