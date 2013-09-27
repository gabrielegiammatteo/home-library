package org.gcube.portlets.user.homelibrary.examples;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;

/**
 * Show how to move a workspace item.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class MoveWorkspaceItem {
	
	/**
	 * @param args not used.
	 * @throws MalformedScopeExpressionException if an error occurs.
	 * @throws InternalErrorException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 * @throws InsufficientPrivilegesException if an error occurs.
	 * @throws ItemAlreadyExistException if an error occurs.
	 * @throws ItemNotFoundException if an error occurs.
	 * @throws WrongDestinationException if an error occurs.
	 */
	public static void main(String[] args) throws MalformedScopeExpressionException, InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException
	{
		Workspace workspace = ExamplesUtil.createWorkspace();
		
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder testFolder = root.createFolder("Folder", "This is a test folder");
		
		WorkspaceFolder item = testFolder.createFolder("TestItem", "TestItem");
		
		workspace.moveItem(item.getId(), root.getId());
		
		workspace.getItem(item.getId());
	}

}
