
package productDAO;


import java.util.List;
import model.Product;


public interface IProductDAO {
    public Product create(Product user);
    public Product findById(int id);
    public List<Product> findAll();
    public void update(Product user);
    public List<Product> findBySellerIdDAO(int sellerId, int page, int pageSize);
    
}
