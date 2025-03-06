/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1_1.repository;

import com.mycompany.demo1_1.model.Product;
import java.util.List;

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
