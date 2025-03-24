/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
 * @author nguyenanh
 */
@Entity
@Table(name = "CategoryGroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoryGroup.findAll", query = "SELECT c FROM CategoryGroup c"),
    @NamedQuery(name = "CategoryGroup.findByGroupID", query = "SELECT c FROM CategoryGroup c WHERE c.groupID = :groupID"),
    @NamedQuery(name = "CategoryGroup.findByGroupName", query = "SELECT c FROM CategoryGroup c WHERE c.groupName = :groupName"),
    @NamedQuery(name = "CategoryGroup.findByGroupDescription", query = "SELECT c FROM CategoryGroup c WHERE c.groupDescription = :groupDescription")})
public class CategoryGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GroupID")
    private Integer groupID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "GroupName")
    private String groupName;
    @Size(max = 50)
    @Column(name = "GroupDescription")
    private String groupDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupID",  fetch = FetchType.EAGER)
    private Collection<Category> categoryCollection;

    public CategoryGroup() {
    }

    public CategoryGroup(Integer groupID) {
        this.groupID = groupID;
    }

    public CategoryGroup(Integer groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    @XmlTransient
    public Collection<Category> getCategoryCollection() {
        return categoryCollection;
    }

    public void setCategoryCollection(Collection<Category> categoryCollection) {
        this.categoryCollection = categoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupID != null ? groupID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryGroup)) {
            return false;
        }
        CategoryGroup other = (CategoryGroup) object;
        if ((this.groupID == null && other.groupID != null) || (this.groupID != null && !this.groupID.equals(other.groupID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CategoryGroup[ groupID=" + groupID + " ]";
    }
    
}
