/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paymentMethodDAO;


import model.PaymentMethod;
import java.util.List;

public interface IPaymentMethodDAO {
    void create(PaymentMethod paymentMethod);
    PaymentMethod findById(Integer id);
    List<PaymentMethod> findAll();
    void update(PaymentMethod paymentMethod);
    void delete(Integer id);
    PaymentMethod findByCode(String code);
}
