/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reviewDAO;

import java.util.List;
import model.Review;

public interface IReviewDAO {
    Review create(Review review);
    List<Review> findByProductId(int productId);
    List<Review> findAll();
    Review update(Review review);
    boolean delete(int reviewId);
}
