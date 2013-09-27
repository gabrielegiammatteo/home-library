/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.manager.ContentCopyMode;
import org.gcube.common.homelibrary.util.ScopeHomesComparator;
import org.gcube.common.homelibrary.util.logging.LoggingUtil;

/**
 * 
 * @author fedy2
 *
 */
public class TestCompareScopeHomes {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		//loadScopes();
		LoggingUtil.reconfigureLogging();
		
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/tmp/home_library_persistence");
		
		
		String sourceScope = "/d4science.research-infrastructures.eu/EM/Demo";
		String destinationScope = "/myTest";
		
		factory.getHomeLibraryManager().copyScopeHomes(sourceScope, destinationScope, ContentCopyMode.FAIL_IF_EXISTS);
				
		ScopeHomesComparator shc = new ScopeHomesComparator();
		
		boolean result = shc.compareScopes(sourceScope, destinationScope, factory);
		
		System.out.println("Result: "+result);
		
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
