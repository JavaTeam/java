package org.agoncal.application.petstore.util;

import java.io.InputStream;

import javax.enterprise.inject.Produces;

import org.agoncal.application.petstore.service.DBPopulatorTomcat;

public class DBPopulatorTomcatProducer {
	
	@Produces
	public DBPopulatorTomcat createPetstorePopulator() {
		InputStream catalogStream = DBPopulatorTomcat.class.getResourceAsStream("/com/sodifrance/petstore/database/data/PetstoreCatalog.xml");
		InputStream customersStream = DBPopulatorTomcat.class.getResourceAsStream("/com/sodifrance/petstore/database/data/PetstoreDefaultCustomers.xml");
		
		return new DBPopulatorTomcat(catalogStream, customersStream);
	}
	
}
