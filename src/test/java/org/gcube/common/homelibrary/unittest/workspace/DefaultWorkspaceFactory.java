/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.scope.api.ScopeProvider;

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

		ScopeProvider.instance.set("/testRoot/test");
		
		try {
			File testDir = new File(TESTUNIT_PERSISTENCE_ROOT);
			
			if (testDir.exists()) FileUtils.cleanDirectory(testDir);
			
			testDir.mkdir();
			
			HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(testDir.getAbsolutePath());
			
			factory.initialize(TESTUNIT_PERSISTENCE_ROOT);
			
			HomeManager manager = factory.getHomeManager();
			
			User testUser = manager.createUser("user.test");
			
			Home home = manager.getHome(testUser);
		
			lastWorkspace = home.getWorkspace();
			return lastWorkspace;
		} catch (Exception e) {
			throw new InternalErrorException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory#getTestWorkspaces()
	 */
	@Override
	public Map<String, Workspace> getTestWorkspaces()
			throws InternalErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory#getListWorkspace()
	 */


	

}
