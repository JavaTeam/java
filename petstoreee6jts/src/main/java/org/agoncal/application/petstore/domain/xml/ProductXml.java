package org.agoncal.application.petstore.domain.xml;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */


@XmlRootElement(name="product")
public class ProductXml {

    // ======================================
    // =             Attributes             =
    // ======================================

    private Long id;
    private String name;
    private String description;
    @XmlTransient
    private CategoryXml categoryXml;

    // ======================================
    // =            Constructors            =
    // ======================================

    public ProductXml() {
    }

    public ProductXml(String name, String description, CategoryXml categoryXml) {
        this.name = name;
        this.description = description;
        this.categoryXml = categoryXml;
    }

    // ======================================
    // =         Getters & setters          =
    // ======================================

    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryXml getCategoryXml() {
        return categoryXml;
    }

    public void setCategory(CategoryXml categoryXml) {
        this.categoryXml = categoryXml;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductXml)) return false;

        ProductXml product = (ProductXml) o;

        if (!name.equals(product.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Product");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}