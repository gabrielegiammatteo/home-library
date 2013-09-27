/**
 * 
 */
package org.gcube.portlets.user.homelibrary.examples;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class CreateAndFillFolder {


	/**
	 * Create and fill a folder.
	 * @param args not used.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 * @throws InternalErrorException if an error occurs.
	 * @throws MalformedScopeExpressionException if an error occurs.
	 * @throws ItemAlreadyExistException if an error occurs.
	 * @throws InsufficientPrivilegesException if an error occurs.
	 */
	public static void main(String[] args) throws MalformedScopeExpressionException, InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		Workspace workspace = ExamplesUtil.createWorkspace();
		
		WorkspaceFolder root = workspace.getRoot();
		
		//we use some utility methods to fill the folder
		TestDataFactory.getInstance().fillAllPDFDocuments(root);
		TestDataFactory.getInstance().fillAllImageDocuments(root);
		
		//then we show the content.
		WorkspaceTreeVisitor visitor = new WorkspaceTreeVisitor();
		visitor.visitVerbose(root);
		
	}
	


}
