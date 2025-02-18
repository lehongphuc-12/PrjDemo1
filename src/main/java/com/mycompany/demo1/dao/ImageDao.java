/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.dao;

import com.mycompany.demo1.model.Image;
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
public class ImageDao implements IImageProduct{
        private static final String INSERT_IMAGE = "INSERT INTO ImageImages (name, price, description, stock) VALUES (?, ?, ?, ?)";
    private static final String SELECT_IMAGE_BY_PRODUCT_ID = "SELECT * FROM ProductImages WHERE ProductID = ?";
    private static final String UPDATE_IMAGE = "UPDATE ImageImages SET name = ?, price = ?, description = ?, stock = ? WHERE id = ?";
    private static final String SELECT_ALL_IMAGE = "SELECT * FROM ProductImages";
    private static final String DELETE_IMAGE_BY_ID = "DELETE FROM Image WHERE id = ?";

    @Override
    public void insertImage(Image cate) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Image> selectAllImageByProductID(int productId) {
        List<Image> images = new ArrayList<>();
        try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_IMAGE_BY_PRODUCT_ID)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                images.add(new Image(rs.getInt("ImageID"),rs.getInt("ProductID"), rs.getString("ImageURL"), rs.getInt("ImageType"), rs.getInt("ImageOrder")));
            }
        } catch (Exception ex) {

        }
        return images;
    }

    @Override
    public List<Image> selectAllImages() {
        List<Image> products = new ArrayList<>();
            try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_IMAGE); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(new Image(rs.getInt("ImageID"),rs.getInt("ProductID"), rs.getString("ImageURL"), rs.getInt("ImageType"), rs.getInt("ImageOrder")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

 

    @Override
    public boolean deleteImage(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateImage(Image pro) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public static void main(String[] args) {
        ImageDao images = new ImageDao();
        for (Image i : images.selectAllImageByProductID(1)) {
            System.out.println(i.toString());
        }
    }
}
