/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.exceptions.UserNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestWorkflowData {
	
	static String myWorkflowId = "myId";
	static String myWorkflowStatus = "myStatus";
	static String myWorkflowData = "myData";
	static ExternalFile file;

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		create();
		read();
	}
	
	protected static void create() throws InternalErrorException, HomeNotFoundException, UserNotFoundException, WorkspaceFolderNotFoundException
	{
		System.out.println("CREATING");
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory(true);
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome("test.user",GCUBEScope.getScope("/test/scope"));
		Workspace workspace = home.getWorkspace();
		WorkspaceFolder root = workspace.getRoot();
		file = TestDataFactory.getInstance().fillExternalFiles(root, 1).get(0);
		
		file.setWorkflowId(myWorkflowId);
		file.setWorkflowStatus(myWorkflowStatus);
		file.setWorkflowData(myWorkflowData);
	}
	
	protected static void read() throws InternalErrorException, HomeNotFoundException, UserNotFoundException, WorkspaceFolderNotFoundException, ItemNotFoundException
	{
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory(false);
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome("test.user",GCUBEScope.getScope("/test/scope"));
		Workspace workspace = home.getWorkspace();
		WorkspaceFolder root = workspace.getRoot();
		file = (ExternalFile) workspace.getItem(file.getId());

		System.out.println("myWorkflowId equals: "+myWorkflowId.equals(file.getWorkflowId()));
		System.out.println("myWorkflowStatus equals: "+myWorkflowStatus.equals(file.getWorkflowStatus()));
		System.out.println("myWorkflowData equals: "+myWorkflowData.equals(file.getWorkflowData()));
	}

}
