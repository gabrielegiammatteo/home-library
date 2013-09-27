/**
 * 
 */
package org.gcube.common.homelibrary.internaltest.versionupdate;

import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.exceptions.UserNotFoundException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.scope.api.ScopeProvider;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestReadingWorkspace {

	/**
	 * @param args
	 * @throws InternalErrorException 
	 * @throws UserNotFoundException 
	 * @throws HomeNotFoundException 
	 * @throws WorkspaceFolderNotFoundException 
	 */
	public static void main(String[] args) throws InternalErrorException, HomeNotFoundException, UserNotFoundException, WorkspaceFolderNotFoundException {
		
		ScopeProvider.instance.set("/test/scope");
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory((args.length>0)?args[0]:"/tmp/hlupdate30");
		HomeManager homeManager = factory.getHomeManager();
		Home home = homeManager.getHome("test.user");
		Workspace workspace = home.getWorkspace();

	}

}
