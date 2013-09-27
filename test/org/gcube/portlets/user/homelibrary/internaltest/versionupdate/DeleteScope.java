/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest.versionupdate;

import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.exceptions.UserNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class DeleteScope {

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
//		HomeManager homeManager = factory.getHomeManager("/test/scope");

	//	factory.removeHomeManager(GCUBEScope.getScope("/test/scope"));
		
		System.out.println("Done");
	}

}
