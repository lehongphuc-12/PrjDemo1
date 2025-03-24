/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.CategoryGroup;

/**
 *
 * @author ASUS
 */
public class CategoryGroupDao extends GenericDAO<CategoryGroup>{
    public CategoryGroupDao() {
        super();
    }
    public static void main(String[] args) {
        CategoryGroupDao cateDao = new CategoryGroupDao();
        for(CategoryGroup cate: cateDao.findAll()) {
            System.out.println(cate.getGroupName());
        }
    }
}
