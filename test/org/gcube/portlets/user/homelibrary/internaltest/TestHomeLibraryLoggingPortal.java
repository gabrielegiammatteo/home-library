/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderBulkCreator;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestHomeLibraryLoggingPortal {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		System.setProperty("catalina.base", "/home/fedy2/test");
		
		Thread th1 = new Thread(){

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {
				try {
					testUser("test.user1");
				} catch (InternalErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HomeNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InsufficientPrivilegesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ItemAlreadyExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WorkspaceFolderNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		Thread th2 = new Thread(){

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {
				try {
					testUser("test.user2");
				} catch (InternalErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HomeNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InsufficientPrivilegesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ItemAlreadyExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WorkspaceFolderNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		Thread th3 = new Thread(){

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {
				try {
					testUser("test.user3");
				} catch (InternalErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HomeNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InsufficientPrivilegesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ItemAlreadyExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WorkspaceFolderNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		th1.start();
		th2.start();
		th3.start();

	}
	
	protected static void testUser(String portalLogin) throws InternalErrorException, HomeNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceFolderNotFoundException{
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory();
		
		HomeManager homeManager = factory.getHomeManager();

		User user = homeManager.getUser(portalLogin);
		
		Home home = homeManager.getHome(user,GCUBEScope.getScope("/d4science.research-infrastructures.eu/FCPPS/FCPPS"));
		
		Workspace workspace = home.getWorkspace();
		
		WorkspaceFolder root = workspace.getRoot();
		
		FolderBulkCreator fbc = root.getNewFolderBulkCreator();
		
		/*fbc.createDocumentItem("c9dd0210-f35f-11dd-9a37-9b05ac676cca","c5b83790-f35f-11dd-9a37-9b05ac676cca");
		fbc.createDocumentItem("a153abb0-f363-11dd-9a37-9b05ac676cca","c5b83790-f35f-11dd-9a37-9b05ac676cca");
		fbc.createDocumentItem("b0cf5330-ff40-11dd-8117-acc6e633ea9e","c5b83790-f35f-11dd-9a37-9b05ac676cca");*/
						
		fbc.commit();
	}

}
