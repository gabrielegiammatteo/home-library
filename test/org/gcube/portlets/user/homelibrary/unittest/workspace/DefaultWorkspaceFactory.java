/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class DefaultWorkspaceFactory implements WorkspaceFactory {
	
	protected static final String TESTUNIT_PERSISTENCE_ROOT = System.getProperty("java.io.tmpdir")+File.separator+"home_library_test_unit";

	protected Workspace lastWorkspace;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cleanWorkspace() throws InternalErrorException {
		if (lastWorkspace!=null) lastWorkspace.getHome().getHomeManager().getHomeManagerFactory().shutdown();
		File testDir = new File(TESTUNIT_PERSISTENCE_ROOT);
		
		try {
			if (testDir.exists()) FileUtils.cleanDirectory(testDir);
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Workspace getWorkspace() throws InternalErrorException {

		try {
			File testDir = new File(TESTUNIT_PERSISTENCE_ROOT);
			
			if (testDir.exists()) FileUtils.cleanDirectory(testDir);
			
			testDir.mkdir();
			
			HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(testDir.getAbsolutePath());
			
			factory.initialize(TESTUNIT_PERSISTENCE_ROOT);
			
			HomeManager manager = factory.getHomeManager();
			
			User testUser = manager.createUser("user.test");
			
			Home home = manager.getHome(testUser,GCUBEScope.getScope("/testRoot/test"));
		
			lastWorkspace = home.getWorkspace();
			return lastWorkspace;
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
		
	}

	

}
