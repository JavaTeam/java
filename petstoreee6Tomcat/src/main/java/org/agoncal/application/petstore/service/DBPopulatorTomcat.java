package org.agoncal.application.petstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.agoncal.application.petstore.domain.Address;
import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Customer;
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
 * 
 * @author ddacosta
 *
 */
@Loggable
public class DBPopulatorTomcat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1701523902845087985L;


	// ======================================
	// =             Attributes             =
	// ======================================

	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_DESCRIPTION = "description";
	private final static String ATTRIBUTE_IMAGE_PATH = "imagePath";
	private final static String ATTRIBUTE_UNIT_COST = "unitCost";
	
	private final static String ATTRIBUTE_FIRSTNAME = "firstname";
	private final static String ATTRIBUTE_LASTNAME = "lastname";
	private final static String ATTRIBUTE_LOGIN = "login";
	private final static String ATTRIBUTE_PWD = "password";
	private final static String ATTRIBUTE_EMAIL = "email";
	private final static String ATTRIBUTE_STREET = "street";
	private final static String ATTRIBUTE_CITY = "city";
	private final static String ATTRIBUTE_ZIPCODE = "zipcode";
	private final static String ATTRIBUTE_COUNTRY = "country";
	
	
	private final InputStream catalogStream;
	private final InputStream customersStream;
	
	private final java.util.List<Category> categories;
	private final java.util.List<Customer> customers;
	

	private final EntityManager em = new DatabaseProducer().createEntityManager();		


	// ======================================
	// =              Constructor           =
	// ======================================
	public DBPopulatorTomcat(InputStream catalogStream, InputStream customersStream) {
		this.catalogStream = catalogStream;
		this.customersStream = customersStream;
		
		this.categories = new ArrayList<Category>();
		this.customers = new ArrayList<Customer>();
	}


	// ======================================
	// =              Public Methods        =
	// ======================================

	public void populateDb() throws ParserConfigurationException, SAXException, IOException {
		initCatalog();
		initCustomers();
	}

	public void clearDB() {
		for(Category category : categories) {
			removeCategory(category);
		}
		
		for(Customer customer : customers) {
			removeCustomer(customer);
		}
	}

	// ======================================
	// =              Private Methods       =
	// ======================================

	private void initCatalog() throws ParserConfigurationException, SAXException, IOException {				
		Document dom = parserFile(catalogStream);
		parseCatalogDom(dom);
		persisteCategories();		
	}

	private void persisteCategories() {
		for(Category category : categories) {
			createCategory(category);
		}
	}

	private Document parserFile(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(stream);
	}


	private void parseCatalogDom(Document dom) {
		org.w3c.dom.Element docEle = dom.getDocumentElement();

		String CATEGORY_TAG_NAME = "category";
		NodeList nl =  docEle.getElementsByTagName(CATEGORY_TAG_NAME);		
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

		String PRODUCT_TAG_NAME = "product";
		NodeList nlProduct = catEl.getElementsByTagName(PRODUCT_TAG_NAME);

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
		String ITEM_TAG_NAME = "item";
		NodeList nlItems = elProduct.getElementsByTagName(ITEM_TAG_NAME);

		if(nlItems != null && nlItems.getLength() > 0) {
			for(int i = 0 ; i < nlItems.getLength();i++) {

				Element el = (Element)nlItems.item(i);				
				Item item = getItem(el, product);

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
	
	
	private Category createCategory(Category category) {
		if (category == null) {
			throw new ValidationException("Category object is null");
		}

		em.getTransaction().begin();
		em.persist(category);
		em.flush();
		em.getTransaction().commit();

		return category;
	}
	
	private void removeCategory(Category category) {
		if (category == null) {
			throw new ValidationException("Category object is null");
		}

		em.getTransaction().begin();
		em.remove(em.merge(category));
		em.flush();
		em.getTransaction().commit();
	}
	
	private void initCustomers() throws ParserConfigurationException, SAXException, IOException {
		Document dom = parserFile(customersStream);		
		parseCustomersDom(dom);
		persisteCustomers();
	}
	
	private void parseCustomersDom(Document dom) {
		org.w3c.dom.Element docEle = dom.getDocumentElement();

		String CUSTOMER_TAG_NAME = "customer";
		NodeList nl =  docEle.getElementsByTagName(CUSTOMER_TAG_NAME);		
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				// get the customer element
				Element el = (Element)nl.item(i);
				
				Customer customer = getCustomer(el);


				customers.add(customer);
			}
		}
	}
	
	
	private Customer getCustomer(Element el) {
		String firstname = el.getAttribute(ATTRIBUTE_FIRSTNAME);
		String lastname = el.getAttribute(ATTRIBUTE_LASTNAME);
		String login = el.getAttribute(ATTRIBUTE_LOGIN);		
		String password = el.getAttribute(ATTRIBUTE_PWD);		
		String email = el.getAttribute(ATTRIBUTE_EMAIL);
		String street = el.getAttribute(ATTRIBUTE_STREET);
		String city = el.getAttribute(ATTRIBUTE_CITY);
		String zipcode = el.getAttribute(ATTRIBUTE_ZIPCODE);
		String country = el.getAttribute(ATTRIBUTE_COUNTRY);
		
		
		return new Customer(firstname, lastname, login, password, email, new Address(street, city, zipcode, country));
	}
	
	private void persisteCustomers() {
		for(Customer customer : customers) {
			createCustomer(customer);
		}
	}
	
	private Customer createCustomer(final Customer customer) {
        if (customer == null) {
            throw new ValidationException("Customer object is null");
        }

        em.getTransaction().begin();
        em.persist(customer);
        em.flush();
        em.getTransaction().commit();

        return customer;
    }
	
	private void removeCustomer(final Customer customer) {
        if (customer == null) {
            throw new ValidationException("Customer object is null");
        }
        
        em.getTransaction().begin();
        em.remove(em.merge(customer));
        em.flush();
        em.getTransaction().commit();
    }
}
