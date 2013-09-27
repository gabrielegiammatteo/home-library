/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.consistency.WorkspaceConsistencyChecker;
import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.scope.api.ScopeProvider;

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
		ScopeProvider.instance.set("/test");
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory("/home/fedy2/Desktop/test HL structure/test");
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome(homeManager.getUser("test.user"));
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
