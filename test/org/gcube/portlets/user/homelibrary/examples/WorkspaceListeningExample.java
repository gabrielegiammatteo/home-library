/**
 * 
 */
package org.gcube.portlets.user.homelibrary.examples;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.events.WorkspaceEvent;
import org.gcube.portlets.user.homelibrary.home.workspace.events.WorkspaceListener;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;


/**
 * This example show how to listen workspace events.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceListeningExample {

	/**
	 * @param args not used.
	 * @throws MalformedScopeExpressionException if an error occurs.
	 * @throws InternalErrorException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 * @throws InsufficientPrivilegesException if an error occurs.
	 * @throws ItemAlreadyExistException if an error occurs.
	 */
	public static void main(String[] args) throws MalformedScopeExpressionException, InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException {
		Workspace workspace = ExamplesUtil.createWorkspace();

		WorkspaceListener listener = new WorkspaceListener(){

			@Override
			public void workspaceEvent(WorkspaceEvent event) {
				System.out.println("This is a workspace event: "+event);
			}
		};
		
		workspace.addWorkspaceListener(listener);
		
		WorkspaceFolder folder = workspace.getRoot().createFolder("TestFolder", "This is a test folder");
		
		folder.createExternalUrlItem("My url", "My prefered url", "http://localhost/");
		
	}

}
