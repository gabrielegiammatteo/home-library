/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.manager.ContentCopyMode;
import org.gcube.portlets.user.homelibrary.home.manager.HomeLibraryManager;
import org.gcube.portlets.user.homelibrary.util.HomeLibraryVisitor;
import org.gcube.portlets.user.homelibrary.util.ScopeHomesComparator;

/**
 * @author fedy2
 *
 */
public class ProductionCopyHomes {
	

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		//LoggingUtil.reconfigureLogging();
		
		//"/home/fedy2/Desktop/tmp/home_library_persistence"
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory();
		loadScopes(factory);
		
		String copy1_SourceScope = "/d4science.research-infrastructures.eu/FCPPS/FCPPS";
		
		String copy1_DestinationScope = "/d4science.research-infrastructures.eu/FARM/FCPPS";
		
		HomeLibraryManager hlManager = factory.getHomeLibraryManager();
		
		System.out.println("Started copiing from "+copy1_SourceScope+" to "+copy1_DestinationScope);
		
		hlManager.copyScopeHomes(copy1_SourceScope, copy1_DestinationScope, ContentCopyMode.REPLACE_IF_EXISTS);
		
		System.out.println("Copy 1 terminated");
		
		HomeLibraryVisitor hlv = new HomeLibraryVisitor(true);
		hlv.visitHomeLibrary(factory);
				
		String copy2_SourceScope = "/d4science.research-infrastructures.eu/ICIS/AquaMaps";
		
		String copy2_DestinationScope = "/d4science.research-infrastructures.eu/FARM/AquaMaps";
		
		HomeLibraryManager hlManager2 = factory.getHomeLibraryManager();
		
		System.out.println("Started copiing from "+copy2_SourceScope+" to "+copy2_DestinationScope);
		
		hlManager2.copyScopeHomes(copy2_SourceScope, copy2_DestinationScope, ContentCopyMode.REPLACE_IF_EXISTS);
		
		System.out.println("Copy 2 terminated");
		
		HomeLibraryVisitor hlv2 = new HomeLibraryVisitor(true);
		hlv2.visitHomeLibrary(factory);
		
		
		ScopeHomesComparator shc1 = new ScopeHomesComparator();
		
		boolean result1 = shc1.compareScopes(copy2_SourceScope, copy2_DestinationScope, factory);
		
		System.out.println("Result copy 1: "+result1);
		
		ScopeHomesComparator shc2 = new ScopeHomesComparator();
		
		boolean result2 = shc2.compareScopes(copy2_SourceScope, copy2_DestinationScope, factory);
		
		System.out.println("Result copy 2: "+result2);
	
	}
	
	protected static void loadScopes(HomeManagerFactory factory) throws InternalErrorException
	{
	
		//factory.getHomeManager("/d4science.research-infrastructures.eu/FCPPS/FCPPS");
		//factory.getHomeManager("/d4science.research-infrastructures.eu/ICIS/AquaMaps");
	}

}
