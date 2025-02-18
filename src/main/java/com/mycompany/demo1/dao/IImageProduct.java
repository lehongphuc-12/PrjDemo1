/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.dao;

import com.mycompany.demo1.model.Image;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IImageProduct {
     public void insertImage(Image cate) throws SQLException;

    public List<Image> selectAllImageByProductID(int id);

    public List<Image> selectAllImages();

    public boolean deleteImage(int id) throws SQLException;

    public boolean updateImage(Image pro) throws SQLException;
}
