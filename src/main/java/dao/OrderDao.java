/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Order1;
import model.OrderDetail;
import utils.JpaUtil;

/**
 *
 * @author ASUS
 */
public class OrderDao extends GenericDAO<Order1> {

    public OrderDao() {
        super();
    }

    public Map<String, BigDecimal> getDailyData(int sellerID) {
        EntityManager em = JpaUtil.getEntityManager();
        Map<String, BigDecimal> dailyMap = new TreeMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -6); // Lấy 7 ngày tính từ hôm nay trở về trước
        Date startDate = cal.getTime();

        Query query = em.createQuery(
                "SELECT od FROM OrderDetail od WHERE od.orderID.orderDate >= :startDate "
                + "AND od.statusID.statusID = 3 "
                + "AND od.productID.sellerID.userID = :sellerID "
                + "ORDER BY od.orderID.orderDate ASC");
        query.setParameter("startDate", startDate);
        query.setParameter("sellerID", sellerID);
        List<OrderDetail> orderDetails = query.getResultList();

        // Khởi tạo dữ liệu cho 7 ngày gần nhất (kể cả ngày không có doanh thu)
        for (int i = 0; i < 7; i++) {
            String dateLabel = sdf.format(cal.getTime());
            dailyMap.put(dateLabel, BigDecimal.ZERO);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Tính doanh thu
        for (OrderDetail od : orderDetails) {
            Date orderDate = od.getOrderID().getOrderDate();
            String dateLabel = sdf.format(orderDate);
            BigDecimal value = od.getPrice();
            dailyMap.put(dateLabel, dailyMap.get(dateLabel).add(value));
        }

        return dailyMap;
    }

    public Map<String, BigDecimal> getWeeklyData(int sellerID) {
        EntityManager em = JpaUtil.getEntityManager();
        Map<String, BigDecimal> weeklyMap = new TreeMap<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -3); // Lấy 4 tuần tính từ tuần hiện tại trở về trước
        Date startDate = cal.getTime();

        Query query = em.createQuery(
                "SELECT od FROM OrderDetail od WHERE od.orderID.orderDate >= :startDate " +
            "AND od.statusID.statusID = 3 " +
            "AND od.productID.sellerID.userID = :sellerID " +
            "ORDER BY od.orderID.orderDate ASC"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("sellerID", sellerID);
        List<OrderDetail> orderDetails = query.getResultList();

        // Khởi tạo dữ liệu cho 4 tuần gần nhất
        for (int i = 1; i <= 4; i++) {
            weeklyMap.put("Tuần " + i, BigDecimal.ZERO);
        }

        // Tính doanh thu theo tuần
        for (OrderDetail od : orderDetails) {
            Date orderDate = od.getOrderID().getOrderDate();
            cal.setTime(orderDate);
            int weekOffset = (cal.get(Calendar.WEEK_OF_YEAR) - cal.getFirstDayOfWeek() + 3) % 4 + 1; // Tính tuần tương đối
            String weekLabel = "Tuần " + weekOffset;
            BigDecimal value = od.getPrice().multiply(BigDecimal.valueOf(od.getQuantity()));
            weeklyMap.put(weekLabel, weeklyMap.get(weekLabel).add(value));
        }

        return weeklyMap;
    }

    public static void main(String[] args) {
        OrderDao orde = new OrderDao();
        System.out.println("Testing getDailyData:");
        Map<String, BigDecimal> dailyData = orde.getDailyData(2);
        System.out.println("Daily Data:");
        for (Map.Entry<String, BigDecimal> entry : dailyData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n------------------------\n");

        // Test getWeeklyData
        System.out.println("Testing getWeeklyData:");
        Map<String, BigDecimal> weeklyData = orde.getWeeklyData(2);
        System.out.println("Weekly Data:");
        for (Map.Entry<String, BigDecimal> entry : weeklyData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
