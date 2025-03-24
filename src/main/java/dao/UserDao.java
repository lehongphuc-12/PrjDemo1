/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.User;
/**
 *
 * @author ASUS
 */
public class UserDao extends GenericDAO<User>{
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        System.out.println(userDao.findById(95).getPassword());
    }
}
