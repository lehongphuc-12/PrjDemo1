/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;


import java.util.List;
import model.Product;

/**
 *
 * @author ASUS
 */
public interface IProductRepository {
    public Product create(Product user);
    public Product findById(int id);
    public List<Product> findAll();
    public Product update(Product user);
}
