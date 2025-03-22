/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paymentDAO;

import java.util.List;
import model.Payment;

public interface IPaymentDAO {
    void create(Payment payment);
    public List<Payment> findByOrderId(int orderId);
}
