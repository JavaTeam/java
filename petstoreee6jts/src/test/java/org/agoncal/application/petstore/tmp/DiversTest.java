package org.agoncal.application.petstore.tmp;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.agoncal.application.petstore.security.LoginContextProducer;
import org.junit.Test;

public class DiversTest {

	@Test
	public void testLoadFile() throws Exception {

			String loginConfigFileName	=	"/petstore-test.login";
			
			URL url	=	LoginContextProducer.class.getResource(loginConfigFileName);
			System.out.println("url=" + url);
			System.out.println("url path=" + url.getPath());
			URI uri	=	url.toURI();
			System.out.println("uri=" + uri);
			File file	=	new File(uri);
			System.out.println("file=" + file);			
			String path	=	file.getPath();
			System.out.println("path=" + path);
			
			File file2	=	new File(url.getPath());
			System.out.println("file2=" + file2);			
			String path2	=	file.getPath();
			System.out.println("path2=" + path2);			

			
			//new File(LoginContextProducer.class.getResource(loginConfigFileName).toURI()).getPath()
	}
	
	
	//=vfs:/G:/jboss/jboss-as-7.1.1.Final/bin/content/applicationPetstore.war/WEB-INF/classes/petstore-test.login
	
	@Test
	public void testLoadFilewithPb() throws Exception {

			String loginConfigFileName	=	"/petstore-test.login";
			
			URL url	=	new URL("vfs:/G:/jboss/jboss-as-7.1.1.Final/bin/content/applicationPetstore.war/WEB-INF/classes/petstore-test.login");
	}	
}
