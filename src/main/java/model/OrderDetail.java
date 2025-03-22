/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Pc
 */
@Entity
@Table(name = "OrderDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o"),
    @NamedQuery(name = "OrderDetail.findByOrderD1", query = "SELECT o FROM OrderDetail o WHERE o.orderD1 = :orderD1"),
    @NamedQuery(name = "OrderDetail.findByQuantity", query = "SELECT o FROM OrderDetail o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "OrderDetail.findByPrice", query = "SELECT o FROM OrderDetail o WHERE o.price = :price")})
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OrderDetailID")
    private Integer orderD1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity")
    private int quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Price")
    private BigDecimal price;
    @JoinColumn(name = "OrderID", referencedColumnName = "OrderID")
    @ManyToOne(optional = false)
    private Order1 orderID;
    @JoinColumn(name = "StatusID", referencedColumnName = "StatusID")
    @ManyToOne(optional = false)
    private OrderStatus statusID;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Product productID;

    public OrderDetail() {
    }

    public OrderDetail(Integer orderD1) {
        this.orderD1 = orderD1;
    }

    public OrderDetail(Integer orderD1, int quantity, BigDecimal price) {
        this.orderD1 = orderD1;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail(Integer orderD1, int quantity, BigDecimal price, Order1 orderID, OrderStatus statusID, Product productID) {
        this.orderD1 = orderD1;
        this.quantity = quantity;
        this.price = price;
        this.orderID = orderID;
        this.statusID = statusID;
        this.productID = productID;
    }

    
    public Integer getOrderD1() {
        return orderD1;
    }

    public void setOrderD1(Integer orderD1) {
        this.orderD1 = orderD1;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Order1 getOrderID() {
        return orderID;
    }

    public void setOrderID(Order1 orderID) {
        this.orderID = orderID;
    }

    public OrderStatus getStatusID() {
        return statusID;
    }

    public void setStatusID(OrderStatus statusID) {
        this.statusID = statusID;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderD1 != null ? orderD1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetail)) {
            return false;
        }
        OrderDetail other = (OrderDetail) object;
        if ((this.orderD1 == null && other.orderD1 != null) || (this.orderD1 != null && !this.orderD1.equals(other.orderD1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.OrderDetail[ orderD1=" + orderD1 + " ]";
    }
    
}
