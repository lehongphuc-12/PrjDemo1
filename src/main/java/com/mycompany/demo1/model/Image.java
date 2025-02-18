/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1.model;

/**
 *
 * @author ASUS
 */
public class Image {
    private  int ImageID;
    private int productId;
    private String imageURL;
    private int ImageType;
    private int ImageOrder;

    public Image() {
    }

    public Image(int ImageID, int productId, String imageURL, int ImageType, int ImageOrder) {
        this.ImageID = ImageID;
        this.productId = productId;
        this.imageURL = imageURL;
        this.ImageType = ImageType;
        this.ImageOrder = ImageOrder;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int ImageID) {
        this.ImageID = ImageID;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getImageType() {
        return ImageType;
    }

    public void setImageType(int ImageType) {
        this.ImageType = ImageType;
    }

    public int getImageOrder() {
        return ImageOrder;
    }

    public void setImageOrder(int ImageOrder) {
        this.ImageOrder = ImageOrder;
    }

    @Override
    public String toString() {
        return "Image{" + "ImageID=" + ImageID + ", productId=" + productId + ", imageURL=" + imageURL + ", ImageType=" + ImageType + ", ImageOrder=" + ImageOrder + '}';
    }
    
}
