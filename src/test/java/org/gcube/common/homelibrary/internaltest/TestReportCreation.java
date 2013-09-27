/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.scope.api.ScopeProvider;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestReportCreation {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		String perstistenceRoot = "/tmp/home_library_persistence";
		ScopeProvider.instance.set("/test");
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(perstistenceRoot);
		HomeManager homeManager = factory.getHomeManager();
		
		User user = homeManager.getUser("federico.defaveri");
		
		Home home = homeManager.getHome(user);
		
		Workspace workspace = home.getWorkspace();
		
		WorkspaceFolder root = workspace.getRoot();
		
		InputStream templateData = new FileInputStream("HomeLibrary.jar");
		
		root.createReportItem("TestReport", "This is a test report", Calendar.getInstance(), Calendar.getInstance(), "Mario.Rossi", "Giuseppe.Verdi", "TestTemplate", 5, "n/a", templateData);
	}

}
