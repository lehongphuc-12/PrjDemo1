package sellerRegistrationDAO;

import model.SellerRegistrationRequest;
import java.util.List;


public interface ISellerRegistrationDAO {
    void createDAO(SellerRegistrationRequest request);
    List<SellerRegistrationRequest> findAllPendingDAO();
    SellerRegistrationRequest findByIdDAO(int requestID);
    void updateDAO(SellerRegistrationRequest request);
}