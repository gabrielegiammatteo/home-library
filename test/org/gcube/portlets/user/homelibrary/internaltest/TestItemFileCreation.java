/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.consistency.WorkspaceConsistencyChecker;
import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestItemFileCreation {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/test HL structure/test");
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome(homeManager.getUser("test.user"),GCUBEScope.getScope("/test"));
		Workspace workspace = home.getWorkspace();
		/*TestDataFactory.getInstance().fillAllFOlderItem(workspace.getRoot());
		List<String> addressees = new LinkedList<String>();
		addressees.add(home.getOwner().getPortalLogin());
		workspace.getItemSendRequestManager().sendRequest(workspace.getRoot().getId(), addressees);*/
		Logger logger = Logger.getLogger("checker");
		logger.setLevel(Level.ALL);
		WorkspaceConsistencyChecker checker = new WorkspaceConsistencyChecker(logger, workspace, true, false);
		checker.checkWorkspace();
	}

}
