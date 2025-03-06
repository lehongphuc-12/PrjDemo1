/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo1_1.model;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "ProductView")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductView.findAll", query = "SELECT p FROM ProductView p"),
    @NamedQuery(name = "ProductView.findByViewID", query = "SELECT p FROM ProductView p WHERE p.viewID = :viewID"),
    @NamedQuery(name = "ProductView.findByViewDate", query = "SELECT p FROM ProductView p WHERE p.viewDate = :viewDate")})
public class ProductView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ViewID")
    private Integer viewID;
    @Column(name = "ViewDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viewDate;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Product productID;
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    @ManyToOne
    private User userID;

    public ProductView() {
    }

    public ProductView(Integer viewID) {
        this.viewID = viewID;
    }

    public Integer getViewID() {
        return viewID;
    }

    public void setViewID(Integer viewID) {
        this.viewID = viewID;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewID != null ? viewID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductView)) {
            return false;
        }
        ProductView other = (ProductView) object;
        if ((this.viewID == null && other.viewID != null) || (this.viewID != null && !this.viewID.equals(other.viewID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.demo1_1.model.ProductView[ viewID=" + viewID + " ]";
    }
    
}
