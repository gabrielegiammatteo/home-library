/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.util.HomeLibraryVisitor;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestRemoveScope {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		GCUBEScope scope = GCUBEScope.getScope("/test");
		
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory();
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome(homeManager.getUser("test.user"),scope);
		Workspace workspace = home.getWorkspace();
		TestDataFactory.getInstance().fillAllFolderItem(workspace.getRoot());
		
		HomeLibraryVisitor hlv = new HomeLibraryVisitor(true);
		hlv.visitHomeLibrary(factory);
		
		//factory.removeHomeManager(scope);
		
		//hlv.visitHomeLibrary(factory);
		
		/*HomeLibraryVisitor hlv = new HomeLibraryVisitor(true);
		hlv.visitHomeLibrary(factory);
		
		TestDataFactory.getInstance().fillExternalImages(workspace.getRoot(), 1);

		factory.removeHomeManager(scope);
		
		hlv.visitHomeLibrary(factory);*/
	}

}
