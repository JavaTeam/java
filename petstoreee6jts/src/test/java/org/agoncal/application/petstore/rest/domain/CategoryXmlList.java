package org.agoncal.application.petstore.rest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.agoncal.application.petstore.domain.xml.CategoryXml;

/**
 * Object created for purpose test: enable to get the response /categories and to unmarshall it as a list of Category 
 * @author eduport
 *
 */
@XmlRootElement(name="collection")
public class CategoryXmlList {


	private List<CategoryXml> categories;

	@XmlElement(name="category")
	public List<CategoryXml> getCategories() {
		
		if (this.categories==null) {
			this.categories	=	new ArrayList<>();
		}		return categories;
	}

	public void setCategories(List<CategoryXml> categories) {
		this.categories = categories;
	}
	
	
}
