/**
 * 
 */
package org.gcube.common.homelibrary.consistency.processor;

import java.util.List;

import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HomeManagerFactoryProcessor extends AbstractProcessor<HomeManagerFactory, HomeManager>{
	
	protected Logger logger;

	/**
	 * @param logger
	 */
	public HomeManagerFactoryProcessor(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void process(HomeManagerFactory input) throws Exception {
		List<String> scopes = input.listScopes();
		for (String scope:scopes){
			logger.trace("processing "+scope);
			HomeManager manager = input.getHomeManager();
			subProcess(manager);
		}
	}
}