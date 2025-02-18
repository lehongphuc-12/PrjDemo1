/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.dao;

import com.mycompany.demo1.model.Category;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface ICategoryDao {
     public void insertCategory(Category cate) throws SQLException;

    public Category selectCategory(int id);

    public List<Category> selectAllCategorys();

    public List<Category> selectCategorysByParentCate(int id);

    public boolean deleteCategory(int id) throws SQLException;

    public boolean updateCategory(Category pro) throws SQLException;
}
