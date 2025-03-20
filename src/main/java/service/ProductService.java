package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.CategoryGroup;
import model.Product;
import productDAO.ProductDAO;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
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

    public static void main(String[] args) {

        ProductService ps = new ProductService();

        Map<CategoryGroup, List<Product>> list = ps.listCategoryGroupProductsDAO(10);
        
//
//        for (CategoryGroup key : list.keyCollection()) {
//            System.out.println("CategoryGroup :" + key.getGroupName() + "list : \n" + "\t" + list.get(key));
//        }

//        System.out.println(ps.listTop10Products());
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
    public void updateProductNameToInactive(int productId) {
        productDAO.updateProductNameDAO(productId, "INACTIVE");
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
}
