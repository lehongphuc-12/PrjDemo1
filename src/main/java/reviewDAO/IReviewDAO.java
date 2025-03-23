
package reviewDAO;

import java.util.List;
import model.Review;

public interface IReviewDAO {
    Review create(Review review);
    List<Review> findByProductId(int productId);
    List<Review> findAll();
    Review updateDAO(Review review);
    boolean deleteDAO(int reviewId);
    boolean hasReviewedDAO(int userID, int productID);
}
