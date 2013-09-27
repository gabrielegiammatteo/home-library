/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest.versionupdate;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.exceptions.UserNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class CreateCompleteWorkspace {

	/**
	 * @param args
	 * @throws InternalErrorException 
	 * @throws UserNotFoundException 
	 * @throws HomeNotFoundException 
	 * @throws WorkspaceFolderNotFoundException 
	 * @throws ItemAlreadyExistException 
	 * @throws InsufficientPrivilegesException 
	 */
	public static void main(String[] args) throws InternalErrorException, HomeNotFoundException, UserNotFoundException, WorkspaceFolderNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException {
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory((args.length>0)?args[0]:"/tmp/hlupdate30");
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome("test.user",GCUBEScope.getScope("/test/scope"));
		Workspace workspace = home.getWorkspace();
		
		WorkspaceFolder destination = workspace.getRoot().createFolder("TestFolder", "My Test Folder");
		TestDataFactory.getInstance().fillWorkspaceFolderItem(destination,1);
		
		System.out.println("Done");
	}

}
