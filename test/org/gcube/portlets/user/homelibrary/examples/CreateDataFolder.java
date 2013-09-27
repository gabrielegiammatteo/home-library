/**
 * 
 */
package org.gcube.portlets.user.homelibrary.examples;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.data.DataArea;
import org.gcube.portlets.user.homelibrary.home.data.exceptions.FolderAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.data.fs.DataFolder;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class CreateDataFolder {


	/**
	 * @param args not used.
	 * @throws InternalErrorException if an error occurs.
	 * @throws FolderAlreadyExistException if an error occurs.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 * @throws MalformedScopeExpressionException if an error occurs.
	 */
	public static void main(String[] args) throws InternalErrorException, FolderAlreadyExistException, MalformedScopeExpressionException, HomeNotFoundException, WorkspaceFolderNotFoundException
	{
		Home home = ExamplesUtil.createHome("/test", "test.user");
		DataArea homeManager = home.getDataArea();
		DataFolder root = homeManager.getDataFolderRoot();
		
		root.createFolder("test");

	}

}
