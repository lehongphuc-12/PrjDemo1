/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.dao;

import com.mycompany.demo1.model.Image;
import com.mycompany.demo1.model.Product;
import com.mycompany.demo1.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ProductDao implements IProductDao{
    private static final String INSERT_PRODUCT = "INSERT INTO Products (name, price, description, stock) VALUES (?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM Products WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE Products SET name = ?, price = ?, description = ?, stock = ? WHERE id = ?";
    private static final String SELECT_ALL_PRODUCT = "SELECT * FROM Products";
    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM Product WHERE id = ?";

    public void insertProduct(Product cate) throws SQLException {
        
    }

    public Product selectProduct(int id) {
//        try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                return new Product(id, rs.getString("name"), rs.getDouble("price"), rs.getString("description"), rs.getInt("stock"), rs.getDate("import_date"));
//            }
//        } catch (Exception ex) {
//
//        }
        return null;
    }

    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PRODUCT); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(rs.getInt("ProductID"),rs.getInt("SellerID"), rs.getString("ProductName"), rs.getString("Description"), rs.getDouble("Price"), rs.getDouble("Quantity"), rs.getInt("CategoryID"),
                            rs.getDate("CreatedDate"), rs.getInt("CityID"), rs.getBoolean("status"));
                product.setImages( new ImageDao().selectAllImageByProductID(product.getProductID()));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public List<Product> searchProductByKey(String keys) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean deleteProduct(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean updateProduct(Product pro) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public static void main(String[] args) {
        ProductDao products = new ProductDao();
        for (Product p : products.selectAllProducts()) {
            System.out.println(p.toString());
        }
    }
}
