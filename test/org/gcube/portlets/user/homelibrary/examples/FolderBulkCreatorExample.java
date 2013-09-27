/**
 * 
 */
package org.gcube.portlets.user.homelibrary.examples;

import java.net.URI;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderBulkCreator;


/**
 * This example show how to clone some Workspace items.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class FolderBulkCreatorExample {

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

		//we create a test folder
		WorkspaceFolder folder = workspace.getRoot().createFolder("TestFolder", "This is a test folder");
		
		FolderBulkCreator fbc = folder.getNewFolderBulkCreator();
		
		//we request an annotation creation
		fbc.createAnnotation(URI.create("cm://sdfasdasdasdfrasdasfasfasfdasfasf"));
		
		//we request a document creation
		fbc.createDocumentItem(URI.create("cm://sdfasdasdasdfrasdasfasfasfdasfasf"));
		
		//we persist changes
		fbc.commit();
	}

}
