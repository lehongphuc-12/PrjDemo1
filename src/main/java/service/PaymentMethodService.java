/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import paymentMethodDAO.IPaymentMethodDAO;
import paymentMethodDAO.PaymentMethodDAO;
import model.PaymentMethod;
import java.util.List;

public class PaymentMethodService {
    private IPaymentMethodDAO paymentMethodDAO;

    public PaymentMethodService() {
        this.paymentMethodDAO = new PaymentMethodDAO();
    }

    public void createPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethodDAO.create(paymentMethod);
    }

    public PaymentMethod getPaymentMethodById(Integer id) {
        return paymentMethodDAO.findById(id);
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodDAO.findAll();
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        paymentMethodDAO.update(paymentMethod);
    }

    public void deletePaymentMethod(Integer id) {
        paymentMethodDAO.delete(id);
    }
    
    public PaymentMethod getPaymentMethodByCode(String code) {
        return paymentMethodDAO.findByCode(code);
    }
}

