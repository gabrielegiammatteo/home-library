/**
 * 
 */
package org.gcube.portlets.user.homelibrary.examples;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HomeManagerListAllUsersContent {

	/**
	 * @param args not used.
	 * @throws InternalErrorException if an error occurs.
	 * @throws MalformedScopeExpressionException if an error occurs.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 */
	public static void main(String[] args) throws MalformedScopeExpressionException, InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException {
		
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory();
		HomeManager homeManager = factory.getHomeManager();		
	
		listUsersContent(homeManager);

	}
	
	protected static void listUsersContent(HomeManager homeManager) throws InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException
	{
		for (User user:homeManager.getUsers()) listUserContent(homeManager, user);
	}
	
	protected static void listUserContent(HomeManager homeManager, User user) throws InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException
	{
		Home home = homeManager.getHome(user, user.getScope());
		Workspace wa = home.getWorkspace();
		
		WorkspaceTreeVisitor visitor = new WorkspaceTreeVisitor();
		visitor.visitVerbose(wa.getRoot());
	}

}
