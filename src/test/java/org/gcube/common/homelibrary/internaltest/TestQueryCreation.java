/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.folder.items.QueryType;
import org.gcube.common.scope.api.ScopeProvider;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestQueryCreation {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		String perstistenceRoot = "/home/fedy2/tomcat/webapps/usersArea/home_library_persistence";
		ScopeProvider.instance.set("/d4science.research-infrastructures.eu/EM/Demo");
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(perstistenceRoot);
		HomeManager homeManager = factory.getHomeManager();
		
		User user = homeManager.getUser("federico.defaveri");
		
		Home home = homeManager.getHome(user);
		
		Workspace workspace = home.getWorkspace();
		
		WorkspaceFolder root = workspace.getRoot();
		
		root.createQueryItem("TestQuery","This is a test query", "select * from query", QueryType.GOOGLE_SEARCH);
	}

}
