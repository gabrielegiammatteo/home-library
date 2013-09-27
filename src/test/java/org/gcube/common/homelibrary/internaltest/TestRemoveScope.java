/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.util.HomeLibraryVisitor;
import org.gcube.common.scope.api.ScopeProvider;

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
		
		ScopeProvider.instance.set("/test");
		
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory();
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome(homeManager.getUser("test.user"));
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
