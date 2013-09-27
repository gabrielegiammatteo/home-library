/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea;

import java.util.Arrays;
import java.util.List;

import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@RunWith(Parameterized.class)
public abstract class AbstractWorkspaceAreaTest {

	protected WorkspaceAreaFactory factory;
	protected WorkspaceArea workspaceArea;

	/**
	 * @param factory the workspace area factory.
	 */
	public AbstractWorkspaceAreaTest(WorkspaceAreaFactory factory) {
		this.factory = factory;
	}

	/**
	 * @return the workspace area instances.
	 */
	@Parameters
	public static List<Object[]> instances() {
		return Arrays.asList(new Object[][] {
				{ getInstance() }
		});
	}
	
	protected static HomeManagerFactory getInstance()
	{
		try {
			return HomeLibrary.getHomeManagerFactory();
		} catch (InternalErrorException e) {
			System.err.println("Error getting the HomeManagerFactory");
			return null;
		}
	}

	/**
	 * Setup the workspace are before each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@Before
	public void setupWorkspaceArea() throws InternalErrorException{
		this.workspaceArea = factory.getWorkspaceArea();

	}

	/**
	 * Clean the workspace area after each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@After
	public void cleanWorkspaceArea() throws InternalErrorException{
		factory.cleanWorkspaceArea();
	}

}
