/**
 * 
 */
package org.gcube.portlets.user.homelibrary.consistency.processor;

import java.util.List;

import org.apache.log4j.Logger;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HomeManagerProcessor extends AbstractProcessor<HomeManager, Home>{
	
	protected Logger logger;

	/**
	 * @param logger
	 */
	public HomeManagerProcessor(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void process(HomeManager manager) throws Exception {
		List<User> users = manager.getUsers();
		for(User user:users){
			try{
				logger.trace("checking user "+user.getPortalLogin());
				Home home = manager.getHome(user.getPortalLogin(),user.getScope());
				subProcess(home);
			}catch(HomeNotFoundException hne)
			{
				System.err.println(hne);
			}

		}
	}
}