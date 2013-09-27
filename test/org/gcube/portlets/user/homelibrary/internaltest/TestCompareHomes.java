/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.util.ScopeHomesComparator;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.portlets.user.homelibrary.util.logging.LoggingUtil;

/**
 * @author fedy2
 *
 */
public class TestCompareHomes {
	

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		LoggingUtil.reconfigureLogging();
		
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory("/home/fedy2/Desktop/tmp/home_library_persistence");
		loadScopes(factory);
		
		String copy1_SourceScope = "/d4science.research-infrastructures.eu/FCPPS/FCPPS";
		
		String copy1_DestinationScope = "/d4science.research-infrastructures.eu/FARM/FCPPS";
		
		String userLogin1 = "Gentile";
		
		HomeManager manager1 = factory.getHomeManager();
		User user1 = manager1.getUser(userLogin1);
		Home home1 = manager1.getHome(user1,GCUBEScope.getScope(copy1_SourceScope));
		Workspace workspace1 = home1.getWorkspace();
		
		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();

		wtv.visitSimple(workspace1.getRoot());
		
		HomeManager manager2 = factory.getHomeManager();
		User user2 = manager2.getUser(userLogin1);
		Home home2 = manager2.getHome(user2,GCUBEScope.getScope(copy1_DestinationScope));
		Workspace workspace2 = home2.getWorkspace();
		
		wtv.visitSimple(workspace2.getRoot());
		
		
		ScopeHomesComparator shc1 = new ScopeHomesComparator();
		
		boolean result1 = shc1.compareWorkspaceItem(workspace1.getRoot(), workspace2.getRoot());
		
		System.out.println("result "+result1);


	
	}
	
	protected static void loadScopes(HomeManagerFactory factory) throws InternalErrorException
	{
	
	//	factory.getHomeManager("/d4science.research-infrastructures.eu/FCPPS/FCPPS");
	//	factory.getHomeManager("/d4science.research-infrastructures.eu/ICIS/AquaMaps");
	}

}
