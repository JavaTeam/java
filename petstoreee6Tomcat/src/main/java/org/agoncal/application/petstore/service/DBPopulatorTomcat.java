package org.agoncal.application.petstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.exception.ValidationException;
import org.agoncal.application.petstore.util.DatabaseProducer;
import org.agoncal.application.petstore.util.Loggable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/** 
 * @author ddacosta
 *
 */
@Loggable
public class DBPopulatorTomcat implements Serializable {

	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_DESCRIPTION = "description";
	private final static String ATTRIBUTE_IMAGE_PATH = "imagePath";
	private final static String ATTRIBUTE_UNIT_COST = "unitCost";

	private Document dom;
	private final InputStream file;
	private final java.util.List<Category> categories;

	private final EntityManager em = new DatabaseProducer().createEntityManager();		


	public DBPopulatorTomcat(InputStream file) {
		this.file = file;

		this.categories = new ArrayList<Category>();
	}


	public void populateDb() throws ParserConfigurationException, SAXException, IOException {
		initCatalog();
	}

	public void clearDB() {
		for(Category category : categories) {
			removeCategory(category);
		}
	}

	public void removeCategory(Category category) {
		if (category == null)
			throw new ValidationException("Category object is null");

		em.remove(em.merge(category));
	}

	private void initCatalog() throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Start initCatalog");		
		parserFile();
		parseDocument();
		persisteCategory();		
		System.out.println("End initCatalog");
	}

	private void persisteCategory() {
		for(Category category : categories) {
			System.out.println("Start persiste : " + category);
			createCategory(category);
			System.out.println("End persiste : " + category);			
		}
	}

	private Category createCategory(Category category) {
		if (category == null)
			throw new ValidationException("Category object is null");

		em.getTransaction().begin();
		em.persist(category);
		em.flush();
		em.getTransaction().commit();

		return category;
	}

	private void parserFile() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();
		dom = db.parse(file);
	}


	private void parseDocument() {
		org.w3c.dom.Element docEle = dom.getDocumentElement();

		NodeList nl =  docEle.getElementsByTagName("category");		
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				// get the category element
				Element el = (Element)nl.item(i);

				//get the category object
				Category e = buildCategory(el);


				categories.add(e);
			}
		}
	}

	private Category buildCategory(Element catEl) {
		Category category = getCategory(catEl);
		System.out.println(category.toString());

		NodeList nlProduct = catEl.getElementsByTagName("product");

		if(nlProduct != null && nlProduct.getLength() > 0) {
			// Get products of category
			for(int i = 0 ; i < nlProduct.getLength();i++) {
				Element elProduct = (Element)nlProduct.item(i);
				Product product = builProduct(category, elProduct);			

				category.addProduct(product);				
			}
		}		
		return category;
	}

	private Product builProduct(Category category, Element elProduct) {
		Product product = getProduct(elProduct, category);
		System.out.println(product.toString());

		NodeList nlItems = elProduct.getElementsByTagName("item");

		if(nlItems != null && nlItems.getLength() > 0) {
			for(int i = 0 ; i < nlItems.getLength();i++) {

				Element el = (Element)nlItems.item(i);				
				Item item = getItem(el, product);
				System.out.println(item.toString());

				product.addItem(item);
			}
		}

		return product;
	}

	private Category getCategory(Element catEl) {
		String name = catEl.getAttribute(ATTRIBUTE_NAME);
		String description = catEl.getAttribute(ATTRIBUTE_DESCRIPTION);

		return new Category(name, description);
	}

	private Product getProduct(Element proEl, Category category) {
		String name = proEl.getAttribute(ATTRIBUTE_NAME);
		String description = proEl.getAttribute(ATTRIBUTE_DESCRIPTION);

		return new Product(name, description, category);		
	}

	private Item getItem(Element itemEl, Product product) {
		String name = itemEl.getAttribute(ATTRIBUTE_NAME);
		String description = itemEl.getAttribute(ATTRIBUTE_DESCRIPTION);
		String imagePath = itemEl.getAttribute(ATTRIBUTE_IMAGE_PATH);
		Float unitCost = Float.valueOf(itemEl.getAttribute(ATTRIBUTE_UNIT_COST));


		return new Item(name, unitCost, imagePath, product, description);
	}


	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		InputStream stream = DBPopulatorTomcat.class.getResourceAsStream("/com/sodifrance/jdom/PetstoreDBPopulator.xml");		
		new DBPopulatorTomcat(stream).populateDb();		
	}
}
