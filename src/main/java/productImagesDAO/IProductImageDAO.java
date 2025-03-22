/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productImagesDAO;

import java.util.List;
import model.ProductImage;

public interface IProductImageDAO {
    public ProductImage create(ProductImage productImage);
    public List<ProductImage> findById(int id);
    public List<ProductImage> findAll();
    public ProductImage update(ProductImage productImage);
    public boolean delete(int id);
    public List<ProductImage> findImagesByProductId(int productId);
}

