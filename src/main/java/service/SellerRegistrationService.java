package service;

import model.SellerRegistrationRequest;
import model.User;
import sellerRegistrationDAO.SellerRegistrationDAO;

import java.util.List;

public class SellerRegistrationService {
    private SellerRegistrationDAO dao;

    public SellerRegistrationService() {
        this.dao = new SellerRegistrationDAO();
    }

    public void createRequest(User user, String shopName) {
        SellerRegistrationRequest request = new SellerRegistrationRequest(user, shopName);
        dao.createDAO(request);
    }

    public List<SellerRegistrationRequest> getPendingRequests() {
        return dao.findAllPendingDAO();
    }

    public List<SellerRegistrationRequest> getPendingRequests(int page, int pageSize) {
        return dao.findAllPendingDAO(page, pageSize);
    }

    public long countPendingRequests() {
        return dao.countPendingRequestsDAO();
    }

    public SellerRegistrationRequest getRequestById(int requestID) {
        return dao.findByIdDAO(requestID);
    }

    public void updateRequest(SellerRegistrationRequest request) {
        dao.updateDAO(request);
    }
}