/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Product {

    private int productID;
    private int sellerID;
    private String productName;
    private String description;
    private Double price;
    private Double quantity;
    private int categoryID;
    private Date createDate;
    private int cityID;
    private boolean status;
    private List<Image> images;

    public Product() {
        images = new ArrayList<>();
    }

    public Product(int productID, int sellerID, String productName, String description, Double price, Double quantity, int categoryID, Date createDate, int cityID, boolean status) {
        this.productID = productID;
        this.sellerID = sellerID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.createDate = createDate;
        this.cityID = cityID;
        this.status = status;
        images = new ArrayList<>();
    }

    public Product(int sellerID, String productName, String description, Double price, Double quantity, int categoryID, int cityID, boolean status) {
        this.sellerID = sellerID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.cityID = cityID;
        this.status = status;
        images = new ArrayList<>();
    }

    public void addImage(Image img) {
        this.images.add(img);
    }
    
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String toString() {
        return "Product{" + "productID=" + productID + ", sellerID=" + sellerID + ", productName=" + productName + ", description=" + description + ", price=" + price + ", quantity=" + quantity + ", categoryID=" + categoryID + ", createDate=" + createDate + ", cityID=" + cityID + ", status=" + status + '}';
    }

}
