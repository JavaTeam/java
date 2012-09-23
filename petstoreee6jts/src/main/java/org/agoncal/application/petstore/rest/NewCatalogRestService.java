package org.agoncal.application.petstore.rest;

import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.domain.xml.CategoryXml;
import org.agoncal.application.petstore.domain.xml.ItemXml;
import org.agoncal.application.petstore.domain.xml.ProductXml;
import org.agoncal.application.petstore.service.CatalogService;
import org.agoncal.application.petstore.util.Loggable;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Loggable
@Path("/catalog")
@ManagedBean
public class NewCatalogRestService implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @EJB
    private CatalogService catalogService;

    @Context
    private UriInfo uriInfo;
    
    @Inject
    private transient Logger logger;    

    // ======================================
    // =          Business methods          =
    // ======================================

    @GET
    @Path("/categories")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoryXml> findAllCategories() {
        List<Category> categories	=	 catalogService.findAllCategories();
        return ConvertUtils.categoryFromDbToXml(categories);
        
    }
    
    @GET
    @Path("/category/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CategoryXml findCategory(@PathParam("id") Long categoryId) {
    	Category categoryDb	=	catalogService.findCategory(categoryId);
    	return ConvertUtils.categoryFromDbToXml(categoryDb);
        
    }

    @POST
    @Path("/category")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createCategory(JAXBElement<CategoryXml> categoryXml) {
    	logger.info("createCategory:"+categoryXml.getValue());
    	Category category	=	ConvertUtils.categoryFromXmlToDb(categoryXml.getValue());
    	category = catalogService.createCategory(category);
        URI uri = uriInfo.getAbsolutePathBuilder().path(category.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/category")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCategory(JAXBElement<CategoryXml> categoryXml) {
    	
    	logger.info("updateCategory:"+categoryXml.getValue());
    	Category category	=	ConvertUtils.categoryFromXmlToDb(categoryXml.getValue());
        category = catalogService.updateCategory(category);
        URI uri = uriInfo.getAbsolutePathBuilder().path(category.getId().toString()).build();
        return Response.ok(uri).build();
    }

    @DELETE
    @Path("/category/{id}")
    public Response removeCategory(@PathParam("id") Long categoryId) {
    	logger.info("removeCategory:"+categoryId);
        catalogService.removeCategory(categoryId);
        return Response.noContent().build();
    }

    @GET
    @Path("/products")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductXml> findAllProducts() {
    	List<Product> products	=	catalogService.findAllProducts();
    	return ConvertUtils.productFromDbToXml(products);
    }

    @GET
    @Path("/product/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ProductXml findProduct(@PathParam("id") Long productId) {
        Product product	=	catalogService.findProduct(productId);
        return ConvertUtils.productFromDbToXml(product);
    }

    @POST
    @Path("/product")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createProduct(JAXBElement<ProductXml> productXml) {
    	logger.info("createProduct:"+productXml.getValue());
    	Product product	=	ConvertUtils.productFromXmlToDb(productXml.getValue());
        product = catalogService.createProduct(product);
        URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/product")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateProduct(JAXBElement<ProductXml> productXml) {
    	logger.info("updateProduct:"+productXml.getValue());
    	Product product	=	ConvertUtils.productFromXmlToDb(productXml.getValue());
        product = catalogService.updateProduct(product);
        URI uri = uriInfo.getAbsolutePathBuilder().path(product.getId().toString()).build();
        return Response.ok(uri).build();
    }

    @DELETE
    @Path("/product/{id}")
    public Response removeProduct(@PathParam("id") Long productId) {
        catalogService.removeProduct(productId);
        return Response.noContent().build();
    }

    @GET
    @Path("/items")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ItemXml> findAllItems() {
        List<Item> items	=	 catalogService.findAllItems();
        return ConvertUtils.itemFromDbToXml(items);
    }

    @GET
    @Path("/item/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ItemXml findItem(@PathParam("id") Long itemId) {
        Item item	=	catalogService.findItem(itemId);
        return ConvertUtils.itemFromDbToXml(item);
    }

    @POST
    @Path("/item")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createItem(JAXBElement<ItemXml> itemXml) {
    	logger.info("createItem:"+itemXml.getValue());
    	Item item	=	ConvertUtils.itemFromXmlToDb(itemXml.getValue());
        item = catalogService.createItem(item);
        URI uri = uriInfo.getAbsolutePathBuilder().path(item.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/item")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateItem(JAXBElement<ItemXml> itemXml) {
    	logger.info("updateItem:"+itemXml.getValue());
    	Item item	=	ConvertUtils.itemFromXmlToDb(itemXml.getValue());
        item 		= catalogService.updateItem(item);
        URI uri = uriInfo.getAbsolutePathBuilder().path(item.getId().toString()).build();
        return Response.ok(uri).build();
    }

    @DELETE
    @Path("/item/{id}")
    public Response removeItem(@PathParam("id") Long itemId) {
        catalogService.removeItem(itemId);
        return Response.noContent().build();
    }

}
