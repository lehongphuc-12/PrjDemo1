package dao;

import model.*;
import model.CategoryGroup;

public class CategoryDTO {
    private Integer categoryID;
    private String name;
    private Integer groupID; // Chỉ lưu ID thay vì cả đối tượng CategoryGroup

    public CategoryDTO() {
    }

    public CategoryDTO(Category category) {
        if (category != null) {
            this.categoryID = category.getCategoryID();
            this.name = category.getCategoryName();
            this.groupID = (category.getGroupID() != null) ? category.getGroupID().getGroupID() : null;
        }
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return name;
    }

    public void setCategoryName(String name) {
        this.name = name;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
}
