/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ProductDao;
import dao.VoucherDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Discount;
import model.Product;

/**
 *
 * @author ASUS
 */
public class VoucherService {
    private VoucherDao voucherDao;
    private ProductDao productDao;
    public VoucherService() {
        voucherDao = new VoucherDao();
        productDao = new ProductDao();
    }
    public void addVoucher(int id, String code, double discountPercent, String startDateStr,String endDateStr) throws ParseException {
        System.out.println("discount: "+ discountPercent);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        Product product = productDao.findById(id);
        Discount discount = new Discount(product, code, discountPercent,startDate, endDate);
        System.out.println("discount " + discount.getDiscountPercent());
        voucherDao.insert(discount);
        System.out.println("insert discount success!");
    }
    public List<Discount> voucherListBySellerId(int id) {
        return voucherDao.getAllVouchersBySeller(id);
    }
    public void removeVoucher(int id) throws Exception {
        Discount discount = voucherDao.findById(id);
        voucherDao.delete(discount);
    }
    public static void main(String[] args) {
        VoucherService ser = new VoucherService();
        System.out.println(ser.voucherListBySellerId(2));
    }
}
