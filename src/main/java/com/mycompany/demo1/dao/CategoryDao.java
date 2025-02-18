/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.dao;

import com.mycompany.demo1.model.Category;
import com.mycompany.demo1.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class CategoryDao implements ICategoryDao {

    private static final String SELECT_CATEGORY_BY_IDPARENT = "SELECT * FROM Categories WHERE ParentCategoryID = ?";
    private static final String SELECT_PARENT_CATEGORIES = "SELECT * FROM categories WHERE ParentCategoryID IS NULL";

    @Override
    public void insertCategory(Category cate) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Category selectCategory(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Category> selectAllCategorys() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Category> selectCategorysByParentCate(int id) {
        List<Category> categorys = new ArrayList<>();
        try (Connection conn = new DBConnection().getConnection()) {
            PreparedStatement ps;
            if (id == 0) {
                ps = conn.prepareStatement(SELECT_PARENT_CATEGORIES);
            } else {
                ps = conn.prepareStatement(SELECT_CATEGORY_BY_IDPARENT);
                ps.setInt(1, id);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (id != 0) {
                    categorys.add(new Category(rs.getInt("CategoryID"), rs.getString("CategoryName"), rs.getInt("ParentCategoryID")));
                } else {
                    categorys.add(new Category(rs.getInt("CategoryID"), rs.getString("CategoryName")));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categorys;
    }

    @Override
    public boolean deleteCategory(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateCategory(Category pro) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        CategoryDao cate = new CategoryDao();
        for (Category c : cate.selectCategorysByParentCate(0)) {
            System.out.println(c.toString());
        }
    }
}
