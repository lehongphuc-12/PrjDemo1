package service;

import java.util.List;
import java.util.logging.Logger;
import model.Payment;
import model.PaymentStatus;
import paymentDAO.IPaymentDAO;
import paymentStatusDAO.IPaymentStatusDAO;
import paymentDAO.PaymentDAO;
import paymentStatusDAO.PaymentStatusDAO;

public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());
    private final IPaymentDAO paymentDAO;
    private final IPaymentStatusDAO paymentStatusDAO; // Added for PaymentStatus

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
        this.paymentStatusDAO = new PaymentStatusDAO(); // Initialize the DAO
    }

    public void createPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        LOGGER.info("Creating payment for order ID: " + payment.getOrderID().getOrderID());
        paymentDAO.create(payment);
    }

    public List<Payment> getPaymentsByOrderId(int orderId) {
        LOGGER.info("Fetching payments for order ID: " + orderId);
        return paymentDAO.findByOrderId(orderId);
    }

    public PaymentStatus getPaymentStatusByName(String statusName) {
        if (statusName == null || statusName.trim().isEmpty()) {
            throw new IllegalArgumentException("Status name cannot be null or empty");
        }
        LOGGER.info("Fetching PaymentStatus by name: " + statusName);
        try {
            PaymentStatus status = paymentStatusDAO.findByName(statusName);
            if (status == null) {
                throw new IllegalStateException("Payment status '" + statusName + "' not found");
            }
            return status;
        } catch (Exception e) {
            LOGGER.severe("Error retrieving payment status: " + e.getMessage());
            throw new RuntimeException("Error retrieving payment status: " + e.getMessage(), e);
        }
    }
}