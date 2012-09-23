package org.agoncal.application.petstore.rest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.agoncal.application.petstore.domain.xml.CategoryXml;
import org.agoncal.application.petstore.domain.xml.ItemXml;
import org.agoncal.application.petstore.domain.xml.ProductXml;
import org.agoncal.application.petstore.rest.domain.CategoryXmlList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NewCatalogRestServiceTest {

	private static final Logger logger	=	Logger.getLogger(NewCatalogRestServiceTest.class);
	
	public JAXBContext jaxbContext;
	
	@Before
	public void setUp() throws JAXBException {
		
		this.jaxbContext	=	JAXBContext.newInstance(new Class[] {CategoryXmlList.class, CategoryXml.class, ItemXml.class, ProductXml.class });
	}
	
	@Test
	public void createCategory() throws Exception {
		
		Marshaller marshaller					=	this.jaxbContext.createMarshaller();
		
		CategoryXml requestCategoryXml			=	create(null);

		JAXBElement<CategoryXml> jaxbElement	=	new JAXBElement<CategoryXml>(new QName("category"),CategoryXml.class,requestCategoryXml);
		
		StringWriter content	=	new StringWriter();
		marshaller.marshal(jaxbElement, content);
		
		DefaultHttpClient httpClient 			=	new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://localhost:8080/applicationPetstore/rs/catalog/category");
 
		StringEntity input = new StringEntity(content.toString());
		input.setContentType("application/xml");
		postRequest.setEntity(input);
 
		HttpResponse response = httpClient.execute(postRequest);
		
		CategoryXml responseCategoryXml	=	findCategoryByName(requestCategoryXml.getName());
		Assert.assertNotNull(responseCategoryXml);
		Assert.assertEquals(requestCategoryXml.getDescription(), responseCategoryXml.getDescription());
		
	}
	
	@Test
	public void updateCategory() throws Exception {
		
		Marshaller marshaller					=	this.jaxbContext.createMarshaller();
		
		// get the list of existing categories
		CategoryXmlList categories				=	getCategoryList();
		
		// take the first to update it
		CategoryXml requestCategoryXml			=	categories.getCategories().get(0);
		// update attributes
		UUID uuid	=	UUID.randomUUID();
		requestCategoryXml.setDescription("description updated " + uuid.toString() );
		requestCategoryXml.setName(uuid.toString());
		
		JAXBElement<CategoryXml> jaxbElement	=	new JAXBElement<CategoryXml>(new QName("category"),CategoryXml.class,requestCategoryXml);
		
		StringWriter content	=	new StringWriter();
		marshaller.marshal(jaxbElement, content);
		
		DefaultHttpClient httpClient 			=	new DefaultHttpClient();
		HttpPut putRequest = new HttpPut("http://localhost:8080/applicationPetstore/rs/catalog/category");
 
		StringEntity input = new StringEntity(content.toString());
		input.setContentType("application/xml");
		putRequest.setEntity(input);
 
		HttpResponse response = httpClient.execute(putRequest);
		
		// get the response if exists and test if attributes  are updated
		CategoryXml responseCategoryXml	=	findCategoryByName(requestCategoryXml.getName());
		Assert.assertNotNull(responseCategoryXml);
		Assert.assertEquals(requestCategoryXml.getDescription(), responseCategoryXml.getDescription());
		
	}
	
	@Test
	public void getCategories() throws Exception {
		
		CategoryXmlList categories	=	getCategoryList();
		logger.info("categories size=" + categories.getCategories().size());
		Assert.assertNotNull(categories);
		
	}
	
	@Test
	public void deleteCategory() throws Exception {
		
		// get the list of existing categories
		CategoryXmlList categories				=	getCategoryList();
		
		// take the first to update it
		CategoryXml requestCategoryXml			=	categories.getCategories().get(0);
		logger.info("id=" + requestCategoryXml.getId());
		
		HttpDelete httpDelete					=	new HttpDelete("http://localhost:8080/applicationPetstore/rs/catalog/category/" + requestCategoryXml.getId());
		DefaultHttpClient httpClient	=	new DefaultHttpClient();
		
		HttpResponse response			=	httpClient.execute(httpDelete);		
		
		// get the response if exists and test if attributes  are updated
		CategoryXml responseCategoryXml	=	findCategoryByName(requestCategoryXml.getName());
		Assert.assertNull(responseCategoryXml);
	}	
	
	/*
	 *  Utility method to get list and make assertions
	 */
	public CategoryXmlList getCategoryList() throws Exception {
		
		HttpGet httpGet					=	new HttpGet("http://localhost:8080/applicationPetstore/rs/catalog/categories");
		DefaultHttpClient httpClient	=	new DefaultHttpClient();
		
		HttpResponse response			=	httpClient.execute(httpGet);
		
		Unmarshaller unmarshaller	=	this.jaxbContext.createUnmarshaller();
		// wrap to a stringReader ibecause of pb : org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; Fin prématurée du fichier
		// to see later	
		InputStream inputStream		=	response.getEntity().getContent();
		String content				=	readcontent(inputStream);
		logger.debug("response=" + content);
		StringReader reader			=	new StringReader(content);
		
		CategoryXmlList categories	=	(CategoryXmlList)unmarshaller.unmarshal(reader);
		logger.debug("categories size=" + categories.getCategories().size());
		
		return categories;
		
	}	
	
	/**
	 * Get the list of category and find the category with the specified name if exists 
	 * @param name
	 * @return
	 */
	public CategoryXml findCategoryByName(String name) throws Exception {
		
		CategoryXmlList categories	=	getCategoryList();
		
		logger.debug("response size=" + categories.getCategories().size());
		logger.debug("request name=" + name);
		
		for (CategoryXml category:categories.getCategories()) {
			logger.debug("response name=" + category.getName());
			if (name.equals(category.getName())) {
				return category;
			}
		}
		return null;
	}
	
	
	public CategoryXml create(Long id) {
		
		UUID uuid	=	UUID.randomUUID();
		
		CategoryXml categoryXml	=	new CategoryXml(uuid.toString(), "description " + uuid.toString());
		if (id!=null) {
			categoryXml.setId(id);
		}
		return categoryXml;
		
	}
	
	public String readcontent(InputStream content) throws Exception {
		
		  char[] buf 				=	new char[2048];
		  Reader reader 			=	new InputStreamReader(content, "UTF-8");
		  StringBuilder builder 	=	new StringBuilder();
		  
		  while (true) {
		    int n = reader.read(buf);
		    if (n < 0)
		      break;
		    builder.append(buf, 0, n);
		  }
		  
		  return builder.toString();
	}
}
