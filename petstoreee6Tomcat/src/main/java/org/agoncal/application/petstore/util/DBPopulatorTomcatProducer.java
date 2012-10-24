package org.agoncal.application.petstore.util;

import java.io.InputStream;

import javax.enterprise.inject.Produces;

import org.agoncal.application.petstore.service.DBPopulatorTomcat;

public class DBPopulatorTomcatProducer {
	
	@Produces
	public DBPopulatorTomcat createPetstorePopulator() {
		InputStream stream = DBPopulatorTomcat.class.getResourceAsStream("/com/sodifrance/jdom/PetstoreDBPopulator.xml");
		return new DBPopulatorTomcat(stream);
	}
	
}
