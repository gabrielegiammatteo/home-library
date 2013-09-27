/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.manager.ContentCopyMode;
import org.gcube.portlets.user.homelibrary.home.manager.HomeLibraryManager;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.util.HomeLibraryVisitor;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.portlets.user.homelibrary.util.logging.LoggingUtil;

/**
 * @author fedy2
 *
 */
public class TestCopyHomes {
	

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		
		LoggingUtil.reconfigureLogging();
		
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/tmp/home_library_persistence");

		String scope1 = "/test/test1";
		String userLogin1 = "user1";
		
		HomeManager manager1 = factory.getHomeManager();
		User user1 = manager1.getUser(userLogin1);
		Home home1 = manager1.getHome(user1,GCUBEScope.getScope(scope1));
		Workspace workspace1 = home1.getWorkspace();
		WorkspaceFolder root1 = workspace1.getRoot();
		root1.createFolder("Folder1", "test folder 1");
		
		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();

		
		String scope2 = "/test/test2";
		String userLogin2 = "user1";
		
		HomeLibraryManager hlManager = factory.getHomeLibraryManager();
		
		hlManager.copyScopeHomes(scope1, scope2, ContentCopyMode.REPLACE_IF_EXISTS);
		
		HomeManager manager2 = factory.getHomeManager();
		User user2 = manager2.getUser(userLogin2);
		Home home2 = manager2.getHome(user2,GCUBEScope.getScope(scope2));
		Workspace workspace2 = home2.getWorkspace();
		WorkspaceFolder root2 = workspace2.getRoot();
		
		wtv.visitVerbose(root1);
		wtv.visitSimple(root1);
		wtv.visitVerbose(root2);
		wtv.visitSimple(root2);
		
		HomeLibraryVisitor hlv = new HomeLibraryVisitor(true);
		hlv.visitHomeLibrary(factory);
	
	}

}
