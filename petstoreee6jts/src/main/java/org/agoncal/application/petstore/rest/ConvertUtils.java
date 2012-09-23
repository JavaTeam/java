package org.agoncal.application.petstore.rest;

import java.util.ArrayList;
import java.util.List;

import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.domain.xml.CategoryXml;
import org.agoncal.application.petstore.domain.xml.ItemXml;
import org.agoncal.application.petstore.domain.xml.ProductXml;

public class ConvertUtils {

	public static List<CategoryXml> categoryFromDbToXml(List<Category> categories) {
		
    	List<CategoryXml> categoriesXml	=	new ArrayList<>();
    	
    	for (Category categoryDb: categories) {
    		categoriesXml.add(categoryFromDbToXml(categoryDb));
    	}
    	
    	return categoriesXml;
	}
	
	public static CategoryXml categoryFromDbToXml(Category categoryDb) {
		if (categoryDb==null) return null;
		
    	CategoryXml categoryXml	=	new CategoryXml(categoryDb.getName(), categoryDb.getDescription());
    	categoryXml.setId(categoryDb.getId());
    	return categoryXml;
	}
	
	public static Category categoryFromXmlToDb(CategoryXml categoryXml) {
		if (categoryXml==null) return null;
		
		Category categoryDb	=	new Category(categoryXml.getName(), categoryXml.getDescription());
		categoryDb.setId(categoryXml.getId());
    	return categoryDb;
	}
	

	public static List<ProductXml> productFromDbToXml(List<Product> products) {
		
    	List<ProductXml> productsXml	=	new ArrayList<>();
    	
    	for (Product productDb: products) {
    		productsXml.add(productFromDbToXml(productDb));
    	}
    	return productsXml;
	}
	
	
	
	public static ProductXml productFromDbToXml(Product productDb) {
		if (productDb==null) return null;
		
    	ProductXml productXml	=	new ProductXml(productDb.getName(), productDb.getDescription(), null);
    	productXml.setId(productDb.getId());
    	return productXml;
	}
	
	public static Product productFromXmlToDb(ProductXml productXml) {
		if (productXml==null) return null;
		
    	Product productDb	=	new Product(productXml.getName(), productXml.getDescription(), null);
    	return productDb;
	}

	public static List<ItemXml> itemFromDbToXml(List<Item> items) {
		
    	List<ItemXml> itemsXml	=	new ArrayList<>();
    	
    	for (Item item: items) {
    		itemsXml.add(itemFromDbToXml(item));
    	}
    	return itemsXml;
	}	
	
	public static ItemXml itemFromDbToXml(Item item) {
		if (item==null) return null;
		
    	ItemXml itemXml	=	new ItemXml(item.getName(), item.getUnitCost(), item.getImagePath(), item.getDescription());
    	itemXml.setId(item.getId());
    	return itemXml;
	}	
	
	public static Item itemFromXmlToDb(ItemXml itemXml) {
		if (itemXml==null) return null;
		
    	Item item	=	new Item(itemXml.getName(), itemXml.getUnitCost(), itemXml.getImagePath(), null, itemXml.getDescription());
    	return item;
	}	
}
