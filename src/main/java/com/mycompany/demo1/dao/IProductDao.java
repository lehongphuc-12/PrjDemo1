/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.dao;

import com.mycompany.demo1.model.Product;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IProductDao {
    public void insertProduct(Product cate) throws SQLException;

    public Product selectProduct(int id);

    public List<Product> selectAllProducts();

    public List<Product> searchProductByKey(String keys);

    public boolean deleteProduct(int id) throws SQLException;

    public boolean updateProduct(Product pro) throws SQLException;
}
