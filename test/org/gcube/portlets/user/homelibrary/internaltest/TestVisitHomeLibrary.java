/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.util.HomeLibraryVisitor;
import org.gcube.portlets.user.homelibrary.util.logging.LoggingUtil;

/**
 * 
 * @author fedy2
 *
 */
public class TestVisitHomeLibrary {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		//loadScopes();
		LoggingUtil.reconfigureLogging();
		
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/tmp/home_library_persistence");
		
		HomeLibraryVisitor hlv = new HomeLibraryVisitor(true);
		hlv.visitHomeLibrary(factory);

		
	}

	
	protected static void loadScopes() throws InternalErrorException
	{
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/tmp/home_library_persistence");
		
	//	factory.getHomeManager("/d4science.research-infrastructures.eu/EM/Demo");
	//	factory.getHomeManager("/d4science.research-infrastructures.eu/EM/GCM");
	//	factory.getHomeManager("/d4science.research-infrastructures.eu/FCPPS/Demo");
	//	factory.getHomeManager("/d4science.research-infrastructures.eu/FCPPS/FCPPS");
	}
}
