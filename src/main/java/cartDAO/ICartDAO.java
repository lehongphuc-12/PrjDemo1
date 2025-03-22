package cartDAO;

import model.Cart;
import model.Discount;
import model.User;
import java.util.List;

public interface ICartDAO {
    void addCart(Cart cart);
    void updateCart(Cart cart);
    void removeCart(int cartID);
    Cart findCartById(Integer cartId);
    Cart findCartByUserAndProduct(User user, int productId);
    Discount findDiscountByCode(String discountCode);
    List<Cart> findCartByUser(User user);
    void deleteByUser(User user);
    public Discount findDiscountByProduct(int productId);
    void deleteCartItemsByUserAndProductIds(User user, List<Integer> productIds);
    void close();
}