/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import java.util.LinkedList;
import java.util.List;

import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.sharing.WorkspaceMessageManager;
import org.gcube.common.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.common.scope.api.ScopeProvider;

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
		
		ScopeProvider.instance.set("/test/scope");
		
		WorkspaceMessageManager manager2 = homeManager.getHome(addrUser).getWorkspace().getWorkspaceMessageManager();
		
//		for (WorkspaceMessage request:manager2.getRequests()) {
//			System.out.println(request);
//			manager2.acceptRequest(request.getId());
//		}
		
		WorkspaceFolder root = homeManager.getHome(addrUser).getWorkspace().getRoot();

		WorkspaceTreeVisitor visitor = new WorkspaceTreeVisitor();
		visitor.visitVerbose(root);

	}

}
