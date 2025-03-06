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
@Table(name = "PaymentStatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentStatus.findAll", query = "SELECT p FROM PaymentStatus p"),
    @NamedQuery(name = "PaymentStatus.findByStatusID", query = "SELECT p FROM PaymentStatus p WHERE p.statusID = :statusID"),
    @NamedQuery(name = "PaymentStatus.findByStatusName", query = "SELECT p FROM PaymentStatus p WHERE p.statusName = :statusName"),
    @NamedQuery(name = "PaymentStatus.findByDescription", query = "SELECT p FROM PaymentStatus p WHERE p.description = :description")})
public class PaymentStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "StatusID")
    private Integer statusID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "StatusName")
    private String statusName;
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusID")
    private Collection<Payment> paymentCollection;

    public PaymentStatus() {
    }

    public PaymentStatus(Integer statusID) {
        this.statusID = statusID;
    }

    public PaymentStatus(Integer statusID, String statusName) {
        this.statusID = statusID;
        this.statusName = statusName;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (statusID != null ? statusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentStatus)) {
            return false;
        }
        PaymentStatus other = (PaymentStatus) object;
        if ((this.statusID == null && other.statusID != null) || (this.statusID != null && !this.statusID.equals(other.statusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PaymentStatus[ statusID=" + statusID + " ]";
    }
    
}
