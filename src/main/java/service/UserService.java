/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.Role;
import model.User;
import userDAO.UserDAO;

/**
 *
 * @author ASUS
 */
public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User addUser(User user) {
        return userDAO.createDAO(user);
    }

    public User getUserById(int id) {
        return userDAO.findByIdDAO(id);
    }

    public List<User> getAllUsers(int page, int pageSize) {
        return userDAO.findAllDAO(page, pageSize);
    }
    public long countUsers() {
        return userDAO.countUsers();
    }
    public User updateUser(User user) {
        return userDAO.updateDAO(user);
    }
    public List<User> getUsersByRole(int roleID, int page, int pageSize) {
        return userDAO.findAllByRoleDAO(roleID, page, pageSize);
    }
    public long countUsersByRole(int roleID) {
        return userDAO.countUsersByRoleDAO(roleID);
    }
    
    public void deleteUserById(int id) {
        try {
            User user = userDAO.findByIdDAO(id);
            if (user != null && user.getRoleID().getRoleID() == 1) {
                throw new IllegalStateException("Không thể xóa tài khoản Admin!");
            }
            userDAO.deleteUserByIdDAO(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user with ID " + id + ": " + e.getMessage(), e);
        }
    }
    public void restoreUser(int id) {
        try {
            userDAO.restoreUserDAO(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to restore user with ID " + id + ": " + e.getMessage(), e);
        }
    }
    
    public List<Object[]> getUserRegistrationStatsByMonth(int year, int month) {
        return userDAO.getUserRegistrationStatsByMonthDAO(year, month);
    }
    
    public void updateUserDetails(int id, String fullName, String email, String phoneNumber, String address, int roleID) {
        User user = getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + id);
        }
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        Role role = new Role();
        role.setRoleID(roleID);
        user.setRoleID(role);
        updateUser(user);
    }

    // Logic từ updateProfile trong Servlet
    public void updateUserProfile(User user, String fullName, String email, String phoneNumber, String address, String password) {
        if (fullName != null && !fullName.trim().isEmpty()) user.setFullName(fullName);
        if (email != null && !email.trim().isEmpty()) user.setEmail(email);
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) user.setPhoneNumber(phoneNumber);
        if (address != null && !address.trim().isEmpty()) user.setAddress(address);
        if (password != null && !password.trim().isEmpty()) user.setPassword(password);
        updateUser(user);
    }
    
    public User getSellerByProductID(int productID){
        return userDAO.getSellerByProductIdDAO(productID);
    }
}
