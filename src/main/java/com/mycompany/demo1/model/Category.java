/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.model;

/**
 *
 * @author ASUS
 */
public class Category {
    private int id;
    private String categoryName;
    private int cateParent;

    public Category(int id, String categoryName, int cateParent) {
        this.id = id;
        this.categoryName = categoryName;
        this.cateParent = cateParent;
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    
    public Category() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCateParent() {
        return cateParent;
    }

    public void setCateParent(int cateParent) {
        this.cateParent = cateParent;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", categoryName=" + categoryName + ", cateParent=" + cateParent + '}';
    }
    
    
}
