
package service;

import java.util.List;
import model.Review;
import reviewDAO.IReviewDAO;
import reviewDAO.ReviewDAO;

public class ReviewService {
    
    private final IReviewDAO reviewDAO ;
    
    public ReviewService(){
        reviewDAO = new ReviewDAO();
    }
    
    public boolean hasReviewed(int userID, int productID){
        return reviewDAO.hasReviewedDAO(userID, productID);
    }
    
    
    public void deleteReview(int reviewID){
        reviewDAO.deleteDAO(reviewID);
    }
    
    public void updateReview(Review review){
        reviewDAO.updateDAO(review);
    }
    public void createReview(Review review){
        reviewDAO.createDAO(review);
    }
    public List<Review> findReviewsByProductID(int productID){
        return reviewDAO.findByProductIdDAO(productID);
    }
}

