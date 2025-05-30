
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "Product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductID", query = "SELECT p FROM Product p WHERE p.productID = :productID"),
    @NamedQuery(name = "Product.findByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
    @NamedQuery(name = "Product.findByQuantity", query = "SELECT p FROM Product p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "Product.findByUnit", query = "SELECT p FROM Product p WHERE p.unit = :unit"),
    @NamedQuery(name = "Product.findByCreatedDate", query = "SELECT p FROM Product p WHERE p.createdDate = :createdDate")})
@NamedStoredProcedureQuery(
    name = "GetSimilarProducts6",
    procedureName = "GetSimilarProducts6",
    resultClasses = Product.class,
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "ProductID")
    })
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProductID")
    private Integer productID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ProductName")
    private String productName;
    @Size(max = 2147483647)
    @Column(name = "Description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Price", precision = 18, scale = 2)
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private Integer quantity;
    @Size(max = 50)
    @Column(name = "Unit")
    private String unit;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(mappedBy = "productID")
    private Collection<Discount> discountCollection;
    @OneToMany(mappedBy = "productID")
    private Collection<ProductImage> productImageCollection;
    @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID")
    @ManyToOne(optional = false)
    private Category categoryID;
    @JoinColumn(name = "CityID", referencedColumnName = "CityID")
    @ManyToOne(optional = false)
    private City cityID;
    @JoinColumn(name = "SellerID", referencedColumnName = "UserID")
    @ManyToOne(optional = false)
    private User sellerID;
    @Column(name = "Status")
    private Boolean status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productID")
    private Collection<Review> reviewCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productID")
    private Collection<OrderDetail> orderDetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productID")
    private Collection<ProductView> productViewCollection;

    public Product() {
    }

    public Product(Integer productID) {
        this.productID = productID;
    }

    public Product(Integer productID, String productName, BigDecimal price, Integer quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public Collection<Discount> getDiscountCollection() {
        return discountCollection;
    }

    public void setDiscountCollection(Collection<Discount> discountCollection) {
        this.discountCollection = discountCollection;
    }

    @XmlTransient
    public Collection<ProductImage> getProductImageCollection() {
        return productImageCollection;
    }

    public void setProductImageCollection(Collection<ProductImage> productImageCollection) {
        this.productImageCollection = productImageCollection;
    }

    public Category getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Category categoryID) {
        this.categoryID = categoryID;
    }

    public City getCityID() {
        return cityID;
    }

    public void setCityID(City cityID) {
        this.cityID = cityID;
    }

    public User getSellerID() {
        return sellerID;
    }

    public void setSellerID(User sellerID) {
        this.sellerID = sellerID;
    }

    @XmlTransient
    public Collection<Review> getReviewCollection() {
        return reviewCollection;
    }

    public void setReviewCollection(Collection<Review> reviewCollection) {
        this.reviewCollection = reviewCollection;
    }

    @XmlTransient
    public Collection<OrderDetail> getOrderDetailCollection() {
        return orderDetailCollection;
    }

    public void setOrderDetailCollection(Collection<OrderDetail> orderDetailCollection) {
        this.orderDetailCollection = orderDetailCollection;
    }

    @XmlTransient
    public Collection<ProductView> getProductViewCollection() {
        return productViewCollection;
    }

    public void setProductViewCollection(Collection<ProductView> productViewCollection) {
        this.productViewCollection = productViewCollection;
    }
    
    public double getAverageRating(){
        double avg = 0.0;
        List<Review> reviews = (List<Review>) this.getReviewCollection();
        if(reviews.isEmpty()){
            return -1;
        }    
        for(Review rv : reviews){
            avg += rv.getRating()*1.0;
        }   
        return avg/reviews.size()*1.0;
    }
    public int getTotalReview(){
        return this.reviewCollection.size();
    }
    
    public int getTotalSold(){
        return this.getOrderDetailCollection().size();
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", description=" + description + ", price=" + price + ", quantity=" + quantity + ", unit=" + unit + ", createdDate=" + createdDate + '}';
    }

    
    
}
