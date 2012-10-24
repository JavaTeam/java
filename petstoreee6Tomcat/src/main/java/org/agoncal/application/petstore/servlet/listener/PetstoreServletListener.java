package org.agoncal.application.petstore.servlet.listener;

import javax.servlet.ServletContextEvent;

import org.jboss.weld.environment.servlet.Listener;

public class PetstoreServletListener extends Listener {
	
//	private final DBPopulatorTomcat parser = DBPopulatorTomcat.createPetstorePopulator();
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {		
		super.contextInitialized(arg0);
		
//		try {
//			parser.populateDb();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ConstraintViolationException e) {
//			for(ConstraintViolation constraintViolation : e.getConstraintViolations()) {
//				System.out.println(constraintViolation.getMessage());
//			}
//		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		super.contextDestroyed(sce);
		
//		parser.clearDB();
	}
}
