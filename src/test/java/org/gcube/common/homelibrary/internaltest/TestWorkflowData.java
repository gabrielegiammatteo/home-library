/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.exceptions.UserNotFoundException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.scope.api.ScopeProvider;

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
		ScopeProvider.instance.set("/test/scope");
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory(true);
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome("test.user");
		Workspace workspace = home.getWorkspace();
		WorkspaceFolder root = workspace.getRoot();
		file = TestDataFactory.getInstance().fillExternalFiles(root, 1).get(0);
		
		file.setWorkflowId(myWorkflowId);
		file.setWorkflowStatus(myWorkflowStatus);
		file.setWorkflowData(myWorkflowData);
	}
	
	protected static void read() throws InternalErrorException, HomeNotFoundException, UserNotFoundException, WorkspaceFolderNotFoundException, ItemNotFoundException
	{
		
		ScopeProvider.instance.set("/test/scope");
		HomeManagerFactory factory = ExamplesUtil.getHomeManagerFactory(false);
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome("test.user");
		Workspace workspace = home.getWorkspace();
		WorkspaceFolder root = workspace.getRoot();
		file = (ExternalFile) workspace.getItem(file.getId());

		System.out.println("myWorkflowId equals: "+myWorkflowId.equals(file.getWorkflowId()));
		System.out.println("myWorkflowStatus equals: "+myWorkflowStatus.equals(file.getWorkflowStatus()));
		System.out.println("myWorkflowData equals: "+myWorkflowData.equals(file.getWorkflowData()));
	}

}
