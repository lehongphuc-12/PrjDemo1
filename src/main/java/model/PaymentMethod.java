/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "PaymentMethod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentMethod.findAll", query = "SELECT p FROM PaymentMethod p"),
    @NamedQuery(name = "PaymentMethod.findByPaymentMethodID", query = "SELECT p FROM PaymentMethod p WHERE p.paymentMethodID = :paymentMethodID"),
    @NamedQuery(name = "PaymentMethod.findByMethodName", query = "SELECT p FROM PaymentMethod p WHERE p.methodName = :methodName"),
    @NamedQuery(name = "PaymentMethod.findByDescription", query = "SELECT p FROM PaymentMethod p WHERE p.description = :description")})
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PaymentMethodID")
    private Integer paymentMethodID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MethodName")
    private String methodName;
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentMethodID")
    private Collection<Order1> order1Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentMethodID")
    private Collection<Payment> paymentCollection;

    public PaymentMethod() {
    }

    public PaymentMethod(Integer paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    public PaymentMethod(Integer paymentMethodID, String methodName) {
        this.paymentMethodID = paymentMethodID;
        this.methodName = methodName;
    }

    public Integer getPaymentMethodID() {
        return paymentMethodID;
    }

    public void setPaymentMethodID(Integer paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Order1> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order1> order1Collection) {
        this.order1Collection = order1Collection;
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentMethodID != null ? paymentMethodID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentMethod)) {
            return false;
        }
        PaymentMethod other = (PaymentMethod) object;
        if ((this.paymentMethodID == null && other.paymentMethodID != null) || (this.paymentMethodID != null && !this.paymentMethodID.equals(other.paymentMethodID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PaymentMethod[ paymentMethodID=" + paymentMethodID + " ]";
    }
    
}
