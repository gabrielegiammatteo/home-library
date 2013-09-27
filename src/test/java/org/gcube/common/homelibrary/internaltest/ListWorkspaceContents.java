/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import java.io.FileNotFoundException;
import java.util.List;

import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.common.scope.api.ScopeProvider;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ListWorkspaceContents {


	/**
	 * @param args not used.
	 * @throws InternalErrorException if an error occurs.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 * @throws InsufficientPrivilegesException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs.
	 * @throws FileNotFoundException if an error occurs.
	 * @throws ItemAlreadyExistException if an error occurs.
	 * @throws ItemNotFoundException if an error occurs.
	 * @throws WrongDestinationException if an error occurs.
	 */
	public static void main(String[] args) throws InternalErrorException, WorkspaceFolderNotFoundException, InsufficientPrivilegesException, HomeNotFoundException, WorkspaceFolderAlreadyExistException, FileNotFoundException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {

		String scope = "/d4science.research-infrastructures.eu/EM/Demo";
		ScopeProvider.instance.set(scope);
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory("/home/fedy2/downloads/home_library_persistence/");
		HomeManager manager = factory.getHomeManager();		


		List<User> users = manager.getUsers();

		for (User user:users){

			System.out.println("User "+user.getId()+" - "+user.getPortalLogin());
			System.out.println();

			Home home = manager.getHome(user);
			Workspace workspace = home.getWorkspace();

	//		System.out.println("Requests: "+workspace.getItemSendRequestManager().getRequests());
			
			WorkspaceFolder root = workspace.getRoot();

			WorkspaceTreeVisitor visitor = new WorkspaceTreeVisitor();
			visitor.visitVerbose(root);
			
			System.out.println();
			System.out.println();
		}

	}



}
