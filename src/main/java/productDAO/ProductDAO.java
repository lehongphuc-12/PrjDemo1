
package productDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.CategoryGroup;
import model.Product;
import org.hibernate.Hibernate;
import utils.JpaUtil;

public class ProductDAO implements IProductDAO{
    private final String orderProducts =  "ORDER BY (0.7 * COALESCE(SIZE(p.orderDetailCollection), 0) + 0.3 * COALESCE(SIZE(p.productViewCollection), 0)) DESC";
//    private final String orderProducts = "ORDER BY (CAST(:weightPurchase AS double) * COALESCE((SIZE(od.quantity), 0) + CAST(:weightView AS double) * COALESCE(SIZE(pv.productViewCollection), 0)) DESC";


    @Override
    public List<Product> findBySellerIdDAO(int sellerId, int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection WHERE p.sellerID.userID = :sellerId", 
                Product.class);
            query.setParameter("sellerId", sellerId);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    public List<Product> findByCategoryDAO(String categoryName, int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection WHERE p.categoryID.categoryName = :categoryName", 
                Product.class);
            query.setParameter("categoryName", categoryName);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Product> findDiscountedProductsDAO(int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection JOIN p.discountCollection d WHERE d.discountPercent > 0", 
                Product.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Product> findPopularProductsDAO(int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection " +
                "ORDER BY SIZE(p.productViewCollection) DESC", Product.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Product> findProductsByUserHistoryDAO(int userId, int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productImageCollection JOIN p.orderDetailCollection od " +
                "WHERE od.orderID.userID.userID = :userId", Product.class);
            query.setParameter("userId", userId);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Product> findTopRatedProductsDAO(int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection " +
                "WHERE EXISTS (SELECT r FROM Review r WHERE r.productID = p AND r.rating >= 4) " +
                "ORDER BY (SELECT AVG(r.rating) FROM Review r WHERE r.productID = p) DESC", 
                Product.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    public void deleteProductDAO(int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, productId);
            if (product != null) {
                product.setStatus(Boolean.FALSE); // Đặt status = false khi xóa
                em.merge(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void restoreProductDAO(int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, productId);
            if (product != null && product.getStatus() == false) {
                product.setStatus(Boolean.TRUE); // Đặt status = true khi khôi phục
                em.merge(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    

    // Thêm phương thức để đếm tổng số sản phẩm theo sellerId
    public long countProductsBySellerIdDAO(int sellerId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.sellerID.userID = :sellerId and p.status = true ", Long.class)
                     .setParameter("sellerId", sellerId)
                     .getSingleResult();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    public long countAllProductsDAO() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                    .getSingleResult();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    public List<Product> findPopularProductsByCategoryDAO(String categoryName, int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection " +
                "WHERE p.categoryID.categoryName = :categoryName " +
                "ORDER BY SIZE(p.productViewCollection) DESC", Product.class);
            query.setParameter("categoryName", categoryName);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    public List<Product> findByProductNameDAO(String productName, int page, int pageSize) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImageCollection " +
                "WHERE LOWER(p.productName) LIKE :productName  and p.status = true ", Product.class);
            query.setParameter("productName", "%" + productName.toLowerCase() + "%");
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    
//    
    //List danh sách các sản phẩm dựa trên category của nó và sắp xếp theo sô lượng sản phẩm được mua, 
    //Nếu như parameter = 0 thì sẽ tìm tất cả các loại sản phẩm
    public List<Product> getProductsByCategoryIdDAO(int categoryId, int pageSize, int pageNumber,String orderProducts) {
        try (EntityManager em = JpaUtil.getEntityManager()) {

            int offset = (pageNumber - 1) * pageSize; // Tính vị trí bắt đầu            

            String jpql = "SELECT p FROM Product p " +
                          "WHERE p.categoryID.categoryID = :categoryId and p.status = true " +
                          "GROUP BY p " + orderProducts;
                          
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            
            query.setParameter("categoryId", categoryId);
                  
            query.setFirstResult(offset); // Offset - bỏ qua số bản ghi tương ứng
            query.setMaxResults(pageSize); // Giới hạn số bản ghi mỗi trang

            List<Product> productList = query.getResultList();

            // Load dữ liệu nếu FetchType.LAZY
            for (Product p : productList) {
                Hibernate.initialize(p.getProductImageCollection());
                Hibernate.initialize(p.getDiscountCollection());
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
            }
            return productList;
        }
    }
    
    
   
    
    public long countProductsByCategoryIdDAO(int categoryId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Product p WHERE p.categoryID.categoryID = :categoryId and p.status = true ";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("categoryId", categoryId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
   public Category getCategoryByIDDAO(int cateID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category c WHERE c.categoryID = :categoryId";
            TypedQuery<Category> query = em.createQuery(jpql, Category.class); // Đúng kiểu dữ liệu
            query.setParameter("categoryId", cateID);
            return query.getSingleResult(); // Lấy một kết quả duy nhất
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy
        } finally {
            em.close();
        }
    }


    
    public List<Product> getProductsByCategoryGroupIdDAO(int categoryGroupId, int pageSize, int pageNumber,String orderProducts) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            int offset = (pageNumber - 1) * pageSize; // Tính vị trí bắt đầu

            String jpql = "SELECT p FROM Product p " +
                          "WHERE p.categoryID.groupID.groupID = :categoryGroupId and p.status = true " + // Lọc theo CategoryGroup
                          "GROUP BY p " +
                          orderProducts;

            TypedQuery<Product> query = em.createQuery(jpql, Product.class);
            query.setParameter("categoryGroupId", categoryGroupId); // Gán giá trị lọc
            
            query.setFirstResult(offset); // Offset - bỏ qua số bản ghi tương ứng
            query.setMaxResults(pageSize); // Giới hạn số bản ghi mỗi trang

            List<Product> productList = query.getResultList();

            // Load dữ liệu nếu FetchType.LAZY
            for (Product p : productList) {
                Hibernate.initialize(p.getProductImageCollection());
                Hibernate.initialize(p.getDiscountCollection());
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
            }
            return productList;
        }
    }
    
    public long countProductsByCategoryGroupIdDAO(int categoryGroupId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Product p WHERE p.categoryID.groupID.groupID = :categoryId and p.status = true ";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("categoryId", categoryGroupId);
            return query.getSingleResult(); // Trả về số lượng sản phẩm
        } finally {
            em.close();
        }
    }

    public CategoryGroup getCategoryGroupByIDDAO(int cateGroupID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT cg FROM CategoryGroup cg WHERE cg.groupID = :categoryGroupId";
            TypedQuery<CategoryGroup> query = em.createQuery(jpql, CategoryGroup.class); // Đúng kiểu dữ liệu
            query.setParameter("categoryGroupId", cateGroupID);
            return query.getSingleResult(); // Lấy một kết quả duy nhất
        } catch (NoResultException e) {
            return null; // Trả về null nếu không tìm thấy
        } finally {
            em.close();
        }
    }
    
    public List<Product> searchProductsByNameDAO(String productName, int pageSize, int pageNumber,String orderProducts){
        try (EntityManager em = JpaUtil.getEntityManager()) {
            
            if(productName.trim().isEmpty() || productName.trim().isBlank()){
                return null;
            }
            
            int offset = (pageNumber - 1) * pageSize; // Tính vị trí bắt đầu            

            String jpql = "SELECT p FROM Product p WHERE p.productName LIKE :productName and p.status = true "+
                           "GROUP BY p " +
                           orderProducts;
            TypedQuery<Product> query = em.createQuery(jpql, Product.class); 
            
//            query.setParameter("productName", "%"+productName+"%");
            query.setParameter("productName", "%"+productName+" %");
            
            query.setFirstResult(offset); // Offset - bỏ qua số bản ghi tương ứng
            query.setMaxResults(pageSize);
            
            List<Product> list = query.getResultList();
            
            for (Product p : list) {
                Hibernate.initialize(p.getProductImageCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getDiscountCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
                Hibernate.initialize(p.getProductViewCollection());
            }
            return list; 
        } 
    }
    
    public double getAverageRatingBySellerIdDAO(int sellerId) {
        try (EntityManager em = JpaUtil.getEntityManager()){
            // JPQL truy vấn
            String jpql = "SELECT AVG(r.rating) " +
                         "FROM Review r " +
                         "WHERE r.productID IN (" +
                         "    SELECT p FROM Product p WHERE p.sellerID.userID = :sellerId" +
                         ")";
            
            TypedQuery<Double> query = em.createQuery(jpql, Double.class);
            query.setParameter("sellerId", sellerId);
            
            // Lấy kết quả
            Double result = query.getSingleResult();
            return (result != null) ? Math.round(result * 10.0) / 10.0 : 0.0;
        } 
    }
    
    public long countProductsBySearchDAO(String productName) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            String jpql = "SELECT COUNT(p) FROM Product p WHERE p.productName LIKE :productNameProducts and p.status = true ";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("productName", "%"+productName+" %");
            return query.getSingleResult();
        }
    }

        
        public List<CategoryGroup> getAllGroupCategoryDAO(){
            
            try(EntityManager em = JpaUtil.getEntityManager()){
                List<CategoryGroup> list = em.createQuery("select cg from CategoryGroup cg")
                        .getResultList();
                
                for (CategoryGroup cg : list) {
                    Hibernate.initialize(cg.getCategoryCollection()); // Load ảnh nếu FetchType.LAZY
                }
                return list;
            }
        }
        
        
        public List<Product> getTopSellingProductsDAO(List<Category> categories, int limit) {
            try(EntityManager em = JpaUtil.getEntityManager()){
                String jpql = "SELECT p FROM Product p " +
                      "WHERE p.categoryID IN :categories and p.status = true " +
                      "GROUP BY p " + orderProducts ;

                TypedQuery<Product> query = em.createQuery(jpql, Product.class);
                query.setParameter("categories", categories);

                query.setMaxResults(limit);
                
                List<Product> list = query.getResultList();

                for (Product p : list) {
                Hibernate.initialize(p.getProductImageCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getDiscountCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
                Hibernate.initialize(p.getProductViewCollection());
            }
                
                return list;
            }
    }
    
        public List<Category> getAllCategoryDAO(int id){
            try(EntityManager em = JpaUtil.getEntityManager()){
                return em.createQuery("select c from Category c where c.groupID.groupID  = :id",Category.class)
                        .setParameter("id", id)
                        .getResultList();
            }
        }
        
        
        
        
        
//    LINH
        
        
        @Override
    public Product create(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            return product;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Product findById(int id) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            Product p = null;
            p = em.find(Product.class, id);
            
            if(p != null){
                Hibernate.initialize(p.getProductImageCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getDiscountCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
                Hibernate.initialize(p.getProductViewCollection());
            }
            return p;
        }
    }

    @Override
    public List<Product> findAll() {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        }
    }

    
    public List<Product> getHighStockProducts(int threshold) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE p.quantity > :threshold ORDER BY p.quantity DESC", Product.class
            );
            query.setParameter("threshold", threshold);
            query.setMaxResults(10); 
            return query.getResultList();
        }
    }
    
    public Product findProductWithDetails(int productID) {
        try (EntityManager em = JpaUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p " +
                "JOIN FETCH p.cityID " +
                "JOIN FETCH p.sellerID " +
                "WHERE p.productID = :productID", 
                Product.class
            );
            query.setParameter("productID", productID);
            return query.getSingleResult();
        }
    }

        public List<Product> getProductsBySellerID(int sellerID) {
            try (EntityManager em = JpaUtil.getEntityManager()) {
                TypedQuery<Product> query = em.createQuery(
                    "SELECT p FROM Product p WHERE p.sellerID.id = :sellerID and p.status = true ORDER BY p.productID DESC",
                    Product.class
                );
                query.setParameter("sellerID", sellerID);   
                
                List<Product> list = query.getResultList();
                
                for(Product p :list){
                    Hibernate.initialize(p.getProductImageCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getDiscountCollection()); // Load ảnh nếu FetchType.LAZY
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
                Hibernate.initialize(p.getProductViewCollection());
                }
                
                return list;
            }
        }

    public List<Product> getSimilarProducts(int productID) {
    EntityManagerFactory emf = JpaUtil.getEntityManagerFactory(); // Lấy factory thay vì entity manager trực tiếp
    EntityManager em = emf.createEntityManager(); // Luôn tạo EntityManager mới
    List<Product> resultList = new ArrayList<>();

    try {
        em.getTransaction().begin(); // Bắt đầu transaction (nếu cần)

        StoredProcedureQuery query = em.createStoredProcedureQuery("GetSimilarProducts6", Product.class);
        query.registerStoredProcedureParameter("ProductID", Integer.class, jakarta.persistence.ParameterMode.IN);
        query.setParameter("ProductID", productID);

        resultList = query.getResultList(); // Lấy danh sách sản phẩm

        // Đảm bảo các collection được load trước khi đóng session
        if (resultList != null && !resultList.isEmpty()) {
            for (Product p : resultList) {
                p = em.merge(p); // Đảm bảo đối tượng nằm trong persistence context
                Hibernate.initialize(p.getProductImageCollection());
                Hibernate.initialize(p.getDiscountCollection());
                Hibernate.initialize(p.getOrderDetailCollection());
                Hibernate.initialize(p.getReviewCollection());
                Hibernate.initialize(p.getProductViewCollection());
            }
        }

        em.getTransaction().commit(); // Commit transaction (nếu có)

    } catch (Exception e) {
        e.printStackTrace();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); // Rollback nếu có lỗi
        }
    } finally {
        if (em.isOpen()) {
            em.close(); // Đóng EntityManager
        }
    }

    return resultList;
}



    @Override
    public void update(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
    
    
    public static void main(String[] args) {
        String orderProducts =  "ORDER BY (0.7 * COALESCE(SIZE(p.orderDetailCollection), 0) + 0.3 * COALESCE(SIZE(p.productViewCollection), 0)) DESC";
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.getSimilarProducts(82));
        System.out.println(dao.getProductsByCategoryIdDAO(15, 0, 0, orderProducts));
    }
    
}
