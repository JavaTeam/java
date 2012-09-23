package org.agoncal.application.petstore.domain.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@XmlRootElement
public class ItemXml {

    // ======================================
    // =             Attributes             =
    // ======================================

    private Long id;
    private String name;
    private String description;
    private Float unitCost;
    private String imagePath;

    // ======================================
    // =            Constructors            =
    // ======================================

    public ItemXml() {
    }

    public ItemXml(String name, Float unitCost, String imagePath, String description) {
        this.name = name;
        this.unitCost = unitCost;
        this.imagePath = imagePath;
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

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
        if (!(o instanceof ItemXml)) return false;

        ItemXml item = (ItemXml) o;

        if (!imagePath.equals(item.imagePath)) return false;
        if (!name.equals(item.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + imagePath.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Item");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", unitCost=").append(unitCost).append('\'');
        sb.append(", imagePath='").append(imagePath).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}