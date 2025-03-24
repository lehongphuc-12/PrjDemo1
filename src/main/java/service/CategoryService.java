
package service;


import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.CategoryGroup;
import model.Product;
import productDAO.ProductDAO;
import dao.CategoryDao;


public class CategoryService {
    
    private ProductDAO productDAO;
    private CategoryDao cateDao;


    public CategoryService() {
        productDAO = new ProductDAO();
        cateDao = new CategoryDao();
    }
    public List<Category> findAllByCateGroupID(int id) {
        return cateDao.getCategoryByGroupId(id);
    }
//    public List<Product> getProductsByCategory(int category, String type, int page, int pageSize){ 
//        return productDAO.getProductsByCategoryDAO(category,type,page,pageSize);
//    }
    
    public List<Product> getProductsByCategoryId(int categoryId, int pageSize, int pageNumber, String orderProducts){ 
        return productDAO.getProductsByCategoryIdDAO(categoryId, pageSize, pageNumber,orderProducts);
    }
    
    public int countProductsByCategoryId(int categoryId){
        return (int) productDAO.countProductsByCategoryIdDAO(categoryId);
    }
    
    public List<Product> getProductsByCategoryGroupId(int categoryId, int pageSize, int pageNumber, String orderProducts){ 
        return productDAO.getProductsByCategoryGroupIdDAO(categoryId, pageSize, pageNumber, orderProducts);
    }
    
    public int countProductsByCategoryGroupId(int categoryId){
        return (int) productDAO.countProductsByCategoryGroupIdDAO(categoryId);
    }
    
    public List<Product> searchProductsByName(String productName, int pageSize, int pageNumber,String orderProducts){
        return productDAO.searchProductsByNameDAO(productName, pageSize, pageNumber, orderProducts);
    }
    
    public int countProductsBySearch(String productName) {
        return (int) productDAO.countProductsBySearchDAO(productName);
    }
    
    public List<CategoryGroup> getAllCategoryGroup() {

        List<CategoryGroup> listCategoryGroup = productDAO.getAllGroupCategoryDAO();

        return listCategoryGroup;
    }

    public Category getCategoryByID(int categoryID){
        return productDAO.getCategoryByIDDAO(categoryID);
    }
    public CategoryGroup getCategoryGroupByID(int categoryID){
        return productDAO.getCategoryGroupByIDDAO(categoryID);
    }
    
    
    
//    TEST
    public static void main(String[] args) {
        CategoryService c = new CategoryService();
        String orderProducts =  "ORDER BY (0.7 * COALESCE(SIZE(p.orderDetailCollection), 0) + 0.3 * COALESCE(SIZE(p.productViewCollection), 0)) DESC";

        List<Product> list = c.getProductsByCategoryGroupId(4, 10, 1,orderProducts);
        for(Product p : list){
            System.out.println("Id: "+p.getProductID() +", Name: "+p.getProductName()+", Cate: "+p.getCategoryID());
        }
        List<Category> lists = c.findAllByCateGroupID(1);
        for(Category cate : lists){
            System.out.println(cate);
        }
   

    }
}
