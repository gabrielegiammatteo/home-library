/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.manager.ContentCopyMode;
import org.gcube.common.homelibrary.home.manager.HomeLibraryManager;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.util.HomeLibraryVisitor;
import org.gcube.common.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.common.scope.api.ScopeProvider;

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
		
		
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/tmp/home_library_persistence");

		String scope1 = "/test/test1";
		String userLogin1 = "user1";
		
		ScopeProvider.instance.set(scope1);
		HomeManager manager1 = factory.getHomeManager();
		User user1 = manager1.getUser(userLogin1);
		Home home1 = manager1.getHome(user1);
		Workspace workspace1 = home1.getWorkspace();
		WorkspaceFolder root1 = workspace1.getRoot();
		root1.createFolder("Folder1", "test folder 1");
		
		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();

		
		String scope2 = "/test/test2";
		String userLogin2 = "user1";
		ScopeProvider.instance.set(scope2);
		HomeLibraryManager hlManager = factory.getHomeLibraryManager();
		
		hlManager.copyScopeHomes(scope1, scope2, ContentCopyMode.REPLACE_IF_EXISTS);
		
		HomeManager manager2 = factory.getHomeManager();
		User user2 = manager2.getUser(userLogin2);
		Home home2 = manager2.getHome(user2);
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
