/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.util.config.easyconf.ComponentConfiguration;
import org.gcube.portlets.user.homelibrary.util.config.easyconf.ComponentProperties;
import org.gcube.portlets.user.homelibrary.util.config.easyconf.EasyConf;
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
public abstract class AbstractWorkspaceTest {

	protected static final String DEFAULT_PROPERTY_FILES = "/org/gcube/portlets/user/homelibrary/unittest/workspace/config/unittest.properties";

	protected static List<Object[]> factories;


	protected WorkspaceFactory factory;
	protected Workspace workspace;

	/**
	 * @param factory the workspace factory.
	 */
	public AbstractWorkspaceTest(WorkspaceFactory factory) {
		this.factory = factory;
	}

	/**
	 * @return the workspace instances.
	 */
	@Parameters
	public static List<Object[]> instances() {

		if (factories==null){
			factories = new LinkedList<Object[]>();
			
			URL url = EasyConf.class.getResource(DEFAULT_PROPERTY_FILES);
			String name = url.toExternalForm();		
			int pos = name.lastIndexOf(".properties");
			if (pos != -1) name = name.substring(0, pos);
			ComponentConfiguration componentConfiguration = EasyConf.getConfiguration(name);
			ComponentProperties properties = componentConfiguration.getProperties();
			try {

				Class<?>[] classes = properties.getClassArray("default-workspace-area-factories");
				for (Class<?> clazz:classes) {
					Object[] array = new Object[1];
					array[0] = clazz.newInstance();
					factories.add(array);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return factories;
	}

	/**
	 * Setup the workspace are before each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@Before
	public void setupWorkspace() throws InternalErrorException{
		this.workspace = factory.getWorkspace();

	}

	/**
	 * Clean the workspace after each test.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws HomeNotFoundException 
	 */
	@After
	public void clean() throws InternalErrorException, HomeNotFoundException{
		factory.cleanWorkspace();
		
	}

}
