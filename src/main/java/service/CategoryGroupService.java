/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CategoryGroupDao;
import java.util.List;
import model.CategoryGroup;

/**
 *
 * @author ASUS
 */
public class CategoryGroupService {
    private CategoryGroupDao cateGroupDao;
    public CategoryGroupService() {
        cateGroupDao = new CategoryGroupDao();
    }
    public List<CategoryGroup> getAll() {
        return cateGroupDao.findAll();
    }
}
