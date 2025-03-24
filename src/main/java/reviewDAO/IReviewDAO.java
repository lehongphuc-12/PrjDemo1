
package reviewDAO;

import java.util.List;
import model.Review;

public interface IReviewDAO {
    Review createDAO(Review review);
    List<Review> findByProductIdDAO(int productId);
    List<Review> findAll();
    Review updateDAO(Review review);
    boolean deleteDAO(int reviewId);
    boolean hasReviewedDAO(int userID, int productID);
}
