package org.agoncal.application.petstore.domain.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@XmlRootElement(name="category")
public class CategoryXml {

    // ======================================
    // =             Attributes             =
    // ======================================

    private Long id;
    private String name;
    private String description;

    // ======================================
    // =            Constructors            =
    // ======================================

    public CategoryXml() {
    }

    public CategoryXml(String name, String description) {
        this.name = name;
        this.description = description;
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

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryXml)) return false;

        CategoryXml category = (CategoryXml) o;

        if (!name.equals(category.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Category");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
