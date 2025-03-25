/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CategoryDao;
import dao.CityDao;
import dao.ImageProductDao;
import dao.ProductDao;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Product;
import model.ProductImage;
import model.User;
import utils.NameNormalizer;


public class ProductManagerService {

    private final ProductDao productDao;
    private final CityDao cityDao;
    private final CategoryDao cateDao;
    private final ImageProductDao imageDao;

    public ProductManagerService() {
        productDao = new ProductDao();
        cityDao = new CityDao();
        cateDao = new CategoryDao();
        imageDao = new ImageProductDao();
    }

    public List<Product> getAllBySellerIdActive(int id, int page, int size) {
        return productDao.findActiveBySellerId(id, page, size);
    }

    public List<Product> getAllBySellerIdInActive(int id, int page, int size) {
        return productDao.findInactiveBySellerId(id, page, size);
    }
    public List<Product> getAllBySellerIdInActive(int id) {
        return productDao.findBySellerId(id).stream().filter(product -> product.getStatus().equals(false)).collect(Collectors.toList());
    }

    public void addProduct(User user, String name, double price, int quantity, int cityId, int categoryId, String description, List<Part> fileParts, String uploadPath) throws IOException {
        Product product = new Product();
        product.setStatus(Boolean.TRUE);
        product.setSellerID(user);
        product.setProductName(name);
        product.setPrice(BigDecimal.valueOf(price));
        product.setQuantity(quantity);
        product.setCityID(cityDao.findById(cityId));
        product.setCategoryID(cateDao.findById(categoryId));
        product.setDescription(description);

        // Lưu sản phẩm vào database
        productDao.insert1(product);
        System.out.println("id product: " + product.getProductID());

        // Thư mục upload ảnh
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            System.out.println("Thư mục đã tạo: " + created);
        }

        List<ProductImage> productImages = new ArrayList<>();
        int i = 0;
        for (Part filePart : fileParts) {
            if (filePart != null && filePart.getSize() > 0) {
                i++;
                String fileName = NameNormalizer.nameNormalized(product.getProductName(), i) + ".jpg";
                System.out.println(fileName);
                // Đường dẫn lưu trữ vật lý
                String absolutePath = uploadPath + File.separator + fileName;
                filePart.write(absolutePath);

                // Đường dẫn lưu vào database (chỉ lấy phần \FePrjProject\img)
                String relativePath = fileName;

                System.out.println("Saved File: " + absolutePath);
                System.out.println("Database Path: " + relativePath);

                ProductImage image = new ProductImage();
                image.setProductID(product);
                image.setImageURL(relativePath); // Lưu đường dẫn tương đối vào DB
                imageDao.insert1(image);
                productImages.add(image);
            }
        }

        if (!productImages.isEmpty()) {
            product.setProductImageCollection(productImages);
        }
    }

    public Product getProductById(int id) {
        return productDao.findById(id);
    }

    public void removeProductById(int id) throws Exception {
        productDao.deleteById(id);
    }

    public void removeByUpdateStatus(int id) {
        Product product = productDao.findById(id);
        product.setStatus(false);
        productDao.update(product);
    }

    public void reCreateByUpdateStatus(int id) {
        Product product = productDao.findById(id);
        product.setStatus(true);
        System.out.println(product.getStatus());
        productDao.update(product);
        System.out.println(product.getStatus());
    }

    public List<ProductImage> getImagesById(int id) {
        return imageDao.findByProductId(id);
    }

    public void updateProduct(int id, String name, double price, int quantity, int cityId, int categoryId, String description, List<Part> fileParts, String uploadPath) throws IOException {
        Product product = productDao.findById(id);
        if (product == null) {
            throw new IOException("Không tìm thấy sản phẩm với ID: " + id);
        }

        // Cập nhật thông tin cơ bản
        product.setProductName(name);
        product.setPrice(BigDecimal.valueOf(price));
        product.setQuantity(quantity);
        product.setCityID(cityDao.findById(cityId));
        product.setCategoryID(cateDao.findById(categoryId));
        product.setDescription(description);

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<ProductImage> currentImages = imageDao.findByProductId(id);
        // Đảm bảo fileParts đủ 4 phần tử (mainImage, subImage1, subImage2, subImage3)
        while (fileParts.size() < 4) {
            fileParts.add(null); // Thêm null cho các vị trí không có file
        }

        for (int i = 0; i < 4; i++) { // Giới hạn ở 4 ảnh (theo thiết kế)
            Part filePart = fileParts.get(i);
            if (filePart != null && filePart.getSize() > 0) {
                // Có file mới: ghi file và cập nhật/thêm ảnh
                String fileName = NameNormalizer.nameNormalized(product.getProductName(), i + 1) + ".jpg";
                String absolutePath = uploadPath + File.separator + fileName;
                filePart.write(absolutePath);

                String relativePath = fileName;

                if (i < currentImages.size()) {
                    // Cập nhật ảnh cũ
                    ProductImage image = currentImages.get(i);
                    image.setImageURL(relativePath);
                    imageDao.update(image);
                } else {
                    // Thêm ảnh mới
                    ProductImage image = new ProductImage();
                    image.setProductID(product);
                    image.setImageURL(relativePath);
                    imageDao.insert1(image);
                    currentImages.add(image); // Cập nhật danh sách để theo dõi
                }
            }
            // Trường hợp không có file mới:
            // - Nếu i < currentImages.size(): giữ nguyên ảnh cũ (không làm gì)
            // - Nếu muốn xóa ảnh cũ khi không có file mới, thêm logic ở đây
        }

        productDao.update(product);
    }
    public int getTotalProductActivePage(int id, int size) {
        long totalItems = productDao.countBySellerActiveId(id);
        return (int) Math.ceil((double) totalItems / size);
    }
    public int getTotalProductInActivePage(int id, int size) {
        long totalItems = productDao.countBySellerInActiveId(id);
        return (int) Math.ceil((double) totalItems / size);
    }
    public static void main(String[] args) throws Exception {
        ProductManagerService productSer = new ProductManagerService();
        System.out.println(productSer.getAllBySellerIdActive(9, 1, 10));
    }
}
