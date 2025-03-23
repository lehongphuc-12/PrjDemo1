package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "[SellerRegistrationRequest]")
@NamedQueries({
    @NamedQuery(name = "SellerRegistrationRequest.findAll", query = "SELECT s FROM SellerRegistrationRequest s"),
    @NamedQuery(name = "SellerRegistrationRequest.findByRequestID", query = "SELECT s FROM SellerRegistrationRequest s WHERE s.requestID = :requestID"),
    @NamedQuery(name = "SellerRegistrationRequest.findByShopName", query = "SELECT s FROM SellerRegistrationRequest s WHERE s.shopName = :shopName"),
    @NamedQuery(name = "SellerRegistrationRequest.findByStatus", query = "SELECT s FROM SellerRegistrationRequest s WHERE s.status = :status"),
    @NamedQuery(name = "SellerRegistrationRequest.findByCreatedAt", query = "SELECT s FROM SellerRegistrationRequest s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "SellerRegistrationRequest.findByUpdatedAt", query = "SELECT s FROM SellerRegistrationRequest s WHERE s.updatedAt = :updatedAt")
})
public class SellerRegistrationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "requestID")
    private Integer requestID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "shopName")
    private String shopName;

    @Size(max = 50)
    @Column(name = "status")
    private String status;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public SellerRegistrationRequest() {
    }

    public SellerRegistrationRequest(User user, String shopName) {
        this.user = user;
        this.shopName = shopName;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = "pending";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestID != null ? requestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SellerRegistrationRequest)) {
            return false;
        }
        SellerRegistrationRequest other = (SellerRegistrationRequest) object;
        if ((this.requestID == null && other.requestID != null) || (this.requestID != null && !this.requestID.equals(other.requestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.SellerRegistrationRequest[ requestID=" + requestID + " ]";
    }
}