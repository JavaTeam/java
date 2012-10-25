package org.agoncal.application.petstore.servlet.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.parsers.ParserConfigurationException;

import org.agoncal.application.petstore.service.DBPopulatorTomcat;
import org.agoncal.application.petstore.util.DBPopulatorTomcatProducer;
import org.jboss.weld.environment.servlet.Listener;
import org.xml.sax.SAXException;

/**
 * Servlet listener for tomcat.
 * 
 * she cares initialize the data base.
 * 
 * @author ddacosta
 *
 */
public class PetstoreServletListener extends Listener {
	
	private final DBPopulatorTomcat parser = new DBPopulatorTomcatProducer().createPetstorePopulator();
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {		
		super.contextInitialized(arg0);
		
		try {
			parser.populateDb();
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			for(ConstraintViolation constraintViolation : e.getConstraintViolations()) {
				System.out.println(constraintViolation.getMessage());
			}
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		super.contextDestroyed(sce);		
		parser.clearDB();
	}
}
