/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestReportTemplateCreation {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		String perstistenceRoot = "/tmp/home_library_persistence";
		GCUBEScope scope = GCUBEScope.getScope("/test");
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(perstistenceRoot);
		HomeManager homeManager = factory.getHomeManager();		
		User user = homeManager.getUser("federico.defaveri");
		
		Home home = homeManager.getHome(user,scope);
		
		Workspace workspace = home.getWorkspace();
		
		WorkspaceFolder root = workspace.getRoot();
		
		InputStream templateData = new FileInputStream("HomeLibrary.jar");
		
		root.createReportTemplateItem("TestTemplate", "This is a test template", Calendar.getInstance(), Calendar.getInstance(), "Mario.Rossi", "Giuseppe.Verdi", 5, "n/a", templateData);
	}

}
