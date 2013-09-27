/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.util.LinkedList;
import java.util.List;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.sharing.WorkspaceMessageManager;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestSentItem {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		Workspace workspace = ExamplesUtil.createWorkspace("/d4science.research-infrastructures.eu/EM/GCM");

		//we create a test 
		WorkspaceFolder folder = workspace.getRoot().createFolder("TestFolder", "This is a test folder");
		
		HomeManager homeManager = workspace.getHome().getHomeManager();
		User addrUser = homeManager.createUser("test.send");
		
		WorkspaceMessageManager manager = workspace.getWorkspaceMessageManager();
		
		List<User> addressees = new LinkedList<User>();
		addressees.add(addrUser);
		
	//	manager.sendRequestToUsers(folder.getId(), addressees);
		
		WorkspaceMessageManager manager2 = homeManager.getHome(addrUser,GCUBEScope.getScope("/test/scope")).getWorkspace().getWorkspaceMessageManager();
		
//		for (WorkspaceMessage request:manager2.getRequests()) {
//			System.out.println(request);
//			manager2.acceptRequest(request.getId());
//		}
		
		WorkspaceFolder root = homeManager.getHome(addrUser, addrUser.getScope()).getWorkspace().getRoot();

		WorkspaceTreeVisitor visitor = new WorkspaceTreeVisitor();
		visitor.visitVerbose(root);

	}

}
