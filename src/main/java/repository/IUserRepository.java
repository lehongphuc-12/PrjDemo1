/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.List;
import model.User;

/**
 *
 * @author ASUS
 */
public interface IUserRepository {
     public User create(User user);
    public User findById(int id);
    public List<User> findAll();
    public User update(User user);
}
