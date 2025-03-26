package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.CategoryGroup;
import model.Product;
import model.ProductImage;
import model.Review;
import productDAO.ProductDAO;
import productImagesDAO.ProductImageDAO;
import reviewDAO.ReviewDAO;

public class ProductService {

    private final ProductDAO productDAO;
    
    private final ProductImageDAO productImageDAO;
    private final ReviewDAO reviewDAO;

    public ProductService() {
        productDAO = new ProductDAO();
        this.productImageDAO = new ProductImageDAO();
        this.reviewDAO = new ReviewDAO();
    }

    //lấy ra top 10 sản phẩm bán chay của từng category group
    public Map<CategoryGroup, List<Product>> listCategoryGroupProductsDAO(int size) {
        Map<CategoryGroup, List<Product>> result = new HashMap<>();
        CategoryService c = new CategoryService();

        // Lấy danh sách tất cả CategoryGroup
        List<CategoryGroup> categoryGroups = c.getAllCategoryGroup();

        // Duyệt từng nhóm danh mục
        for (CategoryGroup cg : categoryGroups) {
            // Lấy danh sách Category (Danh mục con) trong nhóm này
            List<Category> categories = new ArrayList<>(cg.getCategoryCollection());

            // Lấy danh sách 10 sản phẩm bán chạy nhất từ các danh mục trên
            List<Product> topSellingProducts = productDAO.getTopSellingProductsDAO(categories, size);

            // Lưu vào Map
            result.put(cg, topSellingProducts);

            // In ra console để kiểm tra
//            for (Product product : topSellingProducts) {
//                System.out.println("\t - " + product.getProductName()
//                        + " (Đã bán: " + product.getOrderDetailCollection().size() + ")");
//            }
        }

        return result;
    }

    public List<Product> listTopPopularProductsDAO(int size) {
        CategoryService c = new CategoryService();

        // Lấy danh sách tất cả CategoryGroup
        List<CategoryGroup> categoryGroups = c.getAllCategoryGroup();
        List<Category> categories = new ArrayList<>();

        for (CategoryGroup group : categoryGroups) {
            categories.addAll(group.getCategoryCollection()); 
        }

        List<Product> list = productDAO.getTopSellingProductsDAO(categories, size);

        return list;
    }

    public List<Product> getProductsByName(String productName, int page, int pageSize) {
        return productDAO.findByProductNameDAO(productName, page, pageSize);
    }
    public List<Product> getProductsBySellerId(int sellerId, int page, int pageSize) {
        return productDAO.findBySellerIdDAO(sellerId, page, pageSize);
    }
    

    public List<Product> getProductsByCategory(String categoryName, int page, int pageSize) {
        return productDAO.findByCategoryDAO(categoryName, page, pageSize);
    }

    public List<Product> getDiscountedProducts(int page, int pageSize) {
        return productDAO.findDiscountedProductsDAO(page, pageSize);
    }

    public List<Product> getPopularProducts(int page, int pageSize) {
        return productDAO.findPopularProductsDAO(page, pageSize);
    }

    public List<Product> getProductsByUserHistory(int userId, int page, int pageSize) {
        return productDAO.findProductsByUserHistoryDAO(userId, page, pageSize);
    }

    public List<Product> getTopRatedProducts(int page, int pageSize) {
        return productDAO.findTopRatedProductsDAO(page, pageSize);
    }
    // Thêm phương thức đếm sản phẩm
    public long countProductsBySellerId(int sellerId) {
        return productDAO.countProductsBySellerIdDAO(sellerId);
    }
    public void deleteProduct(int productId) {
        productDAO.deleteProductDAO(productId);
    }
    public void restoreProduct(int productId) {
        productDAO.restoreProductDAO(productId);
    }
    public long countAllProducts() {
        return productDAO.countAllProductsDAO();
    }
    public List<Product> getPopularProductsByCategory(String categoryName, int page, int pageSize) {
        return productDAO.findPopularProductsByCategoryDAO(categoryName, page, pageSize);
    }
    
    
    
    
//    LINH
    
    
    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    public List<ProductImage> getProductImages(int productId) {
        return productImageDAO.findImagesByProductId(productId); 
    }

    public Map<Integer, ProductImage> getHighStockProductImages(int threshold) {
        List<Product> highProducts = productDAO.getHighStockProducts(threshold);
        Map<Integer, ProductImage> highProductImages = new HashMap<>();

        for (Product product : highProducts) {
            List<ProductImage> images = productImageDAO.findImagesByProductId(product.getProductID());
            if (images != null && !images.isEmpty()) {
                highProductImages.put(product.getProductID(), images.get(0)); 
            }
        }
        return highProductImages;
    }

    public Map<Integer, ProductImage> getProductImage(int sellerID) {
        List<Product> productsBySeller = productDAO.getProductsBySellerID(sellerID);
        Map<Integer, ProductImage> productImages = new HashMap<>();

        for (Product product : productsBySeller) {
            List<ProductImage> images = productImageDAO.findImagesByProductId(product.getProductID());
            if (images != null && !images.isEmpty()) {
                productImages.put(product.getProductID(), images.get(0)); 
            }
        }
        return productImages;
    }

    public List<Product> getHighStockProducts(int threshold) {
        return productDAO.getHighStockProducts(threshold);
    }

    public Product getProductWithDetails(int productId) {
        return productDAO.findProductWithDetails(productId);
    }

    public List<Product> getProductsBySellerID(int sellerID) {
        return productDAO.getProductsBySellerID(sellerID);
    }
    public List<Review> getReviewsByProductId(int productId) {
        return reviewDAO.findByProductIdDAO(productId);
    }
    public void createReview(Review review) {
        reviewDAO.createDAO(review);
    }
    
    public List<Product> getSimilarProducts(int productID) {
        List<Product> similarProducts = productDAO.getSimilarProducts(productID);
        if (similarProducts == null) {
            return null;
        }
        // Kết quả đã được giới hạn 10 sản phẩm từ ProductDAO
        return similarProducts;
    }
    
    public Map<Integer, ProductImage> getSimilarProductsImage(int sellerID) {
        List<Product> productsBySimilar = productDAO.getSimilarProducts(sellerID);
        Map<Integer, ProductImage> productImages = new HashMap<>();

        for (Product product : productsBySimilar) {
            List<ProductImage> images = productImageDAO.findImagesByProductId(product.getProductID());
            if (images != null && !images.isEmpty()) {
                productImages.put(product.getProductID(), images.get(0)); 
            }
        }
        return productImages;
    }
    public void updateProduct(Product product) {
        if (product == null || product.getProductID() == null) {
            throw new IllegalArgumentException("Product or Product ID cannot be null");
        }
        productDAO.update(product);
    }
    
    public double getAverageRatingBySellerId(int sellerID){
        return productDAO.getAverageRatingBySellerIdDAO(sellerID);
    }
    
    public static void main(String[] args) {
        ProductService p = new ProductService();
        System.out.println(p.getSimilarProducts(82));
    }
}
