package org.agoncal.application.petstore.rest;

import java.io.StringWriter;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.xml.CategoryXml;
import org.agoncal.application.petstore.domain.xml.ItemXml;
import org.agoncal.application.petstore.domain.xml.ProductXml;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;

public class NewCatalogRestServiceTest {

	
	public JAXBContext jaxbContext;
	
	@Before
	public void setUp() throws JAXBException {
		
		this.jaxbContext	=	JAXBContext.newInstance(new Class[] {CategoryXml.class, ItemXml.class, ProductXml.class });
	}
	
	@Test
	public void createCategory() throws Exception {
		
		Marshaller marshaller					=	this.jaxbContext.createMarshaller();
		
		Random random	=	new Random();
		JAXBElement<CategoryXml> jaxbElement	=	new JAXBElement<CategoryXml>(new QName("category"),CategoryXml.class,create(random.nextLong()));
		
		StringWriter content	=	new StringWriter();
		marshaller.marshal(jaxbElement, content);
		
		
		DefaultHttpClient httpClient 			=	new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://localhost:8081/applicationPetstore/rs/catalog/category");
 
		StringEntity input = new StringEntity(content.toString());
		input.setContentType("application/xml");
		postRequest.setEntity(input);
 
		HttpResponse response = httpClient.execute(postRequest);
		
	}
	
	@Test
	public void updateCategory() throws Exception {
		
		Marshaller marshaller					=	this.jaxbContext.createMarshaller();
		
		Long id	=	62L;
		CategoryXml categoryXml	=	create(id);
		categoryXml.setId(id);
		
		JAXBElement<CategoryXml> jaxbElement	=	new JAXBElement<CategoryXml>(new QName("category"),CategoryXml.class,categoryXml);
		
		StringWriter content	=	new StringWriter();
		marshaller.marshal(jaxbElement, content);
		
		
		DefaultHttpClient httpClient 			=	new DefaultHttpClient();
		HttpPut putRequest = new HttpPut("http://localhost:8081/applicationPetstore/rs/catalog/category");
 
		StringEntity input = new StringEntity(content.toString());
		input.setContentType("application/xml");
		putRequest.setEntity(input);
 
		HttpResponse response = httpClient.execute(putRequest);
		
	}	
	
	
	public CategoryXml create(Long id) {
		
		CategoryXml categoryXml	=	new CategoryXml("name_test_" + id, "description_test_" + id);
		//categoryXml.setId(id);
		
		return categoryXml;
		
	}
}
