package org.agoncal.application.petstore.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

public class DatabaseProducer {

    // ======================================
    // =             Attributes             =
    // ======================================

//    @Produces
//    @PersistenceContext(unitName = "applicationPetstorePU")
//    private EntityManager em;
	
	public static String PERSISTENCE_UNIT_NAME = "applicationPetstorePU";

	@Produces
	public synchronized EntityManager createEntityManager() {	
		EntityManager manager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();		
		return manager;		
	}	
}
