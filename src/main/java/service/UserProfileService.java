/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UserDao;
import model.User;

/**
 *
 * @author ASUS
 */
public class UserProfileService {

    private UserDao userDao;
    public UserProfileService() {
        userDao = new UserDao();
    }

    public User findUserById(int id) {
        return userDao.findById(id);
    }

    public boolean verifyPassword(int userId, String currentPassword) {
        User user = userDao.findById(userId);
        return user != null && user.getPassword().equals(currentPassword);
    }

    public boolean updatePassword(int userId, String newPassword) {
        User user = userDao.findById(userId);
        user.setPassword(newPassword);
        userDao.update(user);
        return true;
    }
    public static void main(String[] args) {
        UserProfileService service = new UserProfileService();
        service.updateUserProfile(2, "jane.smith@example.com", "0987654322", "456 Seller Lane", "Hana shop1");
        
        System.out.println(service.findUserById(2).getShopName());
    }

    public boolean updateUserProfile(Integer userID, String email, String phone, String address, String shopName) {
        User user = userDao.findById(userID);
        if (user != null) {
            System.out.println("shopName đang là "+ shopName);
            user.setShopName(shopName);
            System.out.println("shopName đang là "+ shopName);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setAddress(address);
            userDao.update(user);
            return true;
        }
        return false;
    }
    
}
