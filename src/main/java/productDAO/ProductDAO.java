
package productDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
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
    public void restoreProductDAO(int productId) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, productId);
            if (product != null && product.getProductName().equals("INACTIVE")) {
                String description = product.getDescription();
                if (description != null && description.contains("| Original Name: ")) {
                    // Tách Description để lấy tên gốc và mô tả ban đầu
                    int index = description.lastIndexOf("| Original Name: ");
                    String originalDescription = description.substring(0, index).trim();
                    String originalName = description.substring(index + "| Original Name: ".length()).trim();
                    product.setProductName(originalName);
                    product.setDescription(originalDescription.equals("|") ? "" : originalDescription); // Khôi phục mô tả ban đầu
                    em.merge(product);
                }
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
    @Override
    public void updateProductNameDAO(int productId, String newName) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, productId);
            if (product != null) {
                String originalName = product.getProductName();
                String originalDescription = product.getDescription() != null ? product.getDescription() : "";
                // Nối tên gốc vào Description thay vì ghi đè
                product.setDescription(originalDescription + " | Original Name: " + originalName);
                product.setProductName(newName);
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
            return em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.sellerID.userID = :sellerId", Long.class)
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
                "WHERE LOWER(p.productName) LIKE :productName", Product.class);
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
                          "WHERE p.categoryID.categoryID = :categoryId " +
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
            String jpql = "SELECT COUNT(p) FROM Product p WHERE p.categoryID.categoryID = :categoryId";
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
                          "WHERE p.categoryID.groupID.groupID = :categoryGroupId " + // Lọc theo CategoryGroup
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
            String jpql = "SELECT COUNT(p) FROM Product p WHERE p.categoryID.groupID.groupID = :categoryId";
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

            String jpql = "SELECT p FROM Product p WHERE p.productName LIKE :productName "+
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
    
    public long countProductsBySearchDAO(String productName) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Product p WHERE p.productName LIKE :productName ";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("productName", "%"+productName+" %");
            return query.getSingleResult();
        } finally {
            em.close();
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
                      "WHERE p.categoryID IN :categories " +
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
                return em.createQuery("select c from Category c where c.groupID.groupID  = :id")
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
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public List<Product> getHighStockProducts(int threshold) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE p.quantity > :threshold ORDER BY p.quantity DESC", Product.class
            );
            query.setParameter("threshold", threshold);
            query.setMaxResults(10); 
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Product findProductWithDetails(int productID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p " +
                "JOIN FETCH p.cityID " +
                "JOIN FETCH p.sellerID " +
                "WHERE p.productID = :productID", 
                Product.class
            );
            query.setParameter("productID", productID);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

        public List<Product> getProductsBySellerID(int sellerID) {
            EntityManager em = JpaUtil.getEntityManager();
            try {
                TypedQuery<Product> query = em.createQuery(
                    "SELECT p FROM Product p WHERE p.sellerID.id = :sellerID ORDER BY p.productID DESC",
                    Product.class
                );
                query.setParameter("sellerID", sellerID);       
                return query.getResultList();
            } finally {
                em.close();
            }
        }

    public List<Product> getSimilarProducts(int productID) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            StoredProcedureQuery query = em.createStoredProcedureQuery("GetSimilarProducts6", Product.class);
            query.registerStoredProcedureParameter("ProductID", Integer.class, jakarta.persistence.ParameterMode.IN);
            query.setParameter("ProductID", productID);
            query.setMaxResults(10);
            
            // Thực thi và lấy danh sách kết quả
            @SuppressWarnings("unchecked")
            List<Product> resultList = query.getResultList();
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
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
        
        long startTime = System.nanoTime(); // Bắt đầu đo
        dao.searchProductsByNameDAO("cá", 10, 1, orderProducts).forEach(p -> System.out.println(p));
        long endTime = System.nanoTime(); // Kết thúc đo
        long duration = endTime - startTime; // Thời gian chạy (nanoseconds)

        System.out.println("Thời gian chạy: " + duration / 1_000_000.0 + " ms"); 
        //C2 : 1376-1500
        //C1:  1450-1540  
        System.out.println(dao.getSimilarProducts(1));
//        System.out.println("COUNT: "+ dao.countProductsByCategoryGroupId(2));
    }
    
}
