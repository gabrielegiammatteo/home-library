/**
 * 
 */
package org.gcube.common.homelibrary.consistency;

import java.util.List;

import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.consistency.statistics.CheckStatistics;
import org.gcube.common.homelibrary.consistency.statistics.HomeCheckStatistics;
import org.gcube.common.homelibrary.home.Home;
import org.gcube.common.homelibrary.home.HomeManager;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.exceptions.UserNotFoundException;
import org.gcube.common.homelibrary.home.workspace.Workspace;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HomeManagerFactoryConsistencyChecker {

	protected Logger logger;
	protected HomeManagerFactory factory;
	protected boolean testEntireStream;
	protected CheckStatistics statistics;
	protected boolean acceptAllSentRequests;

	/**
	 * @param logger the checker logger.
	 * @param factory the home manager factory.
	 * @param testEntireStream <code>true</code> to check the folder items streams, <code>false</code> otherwise.
	 * @param acceptAllSentRequests <code>true</code> to accept all sent requests and process the received elements. This will make the check destructive.
	 */
	public HomeManagerFactoryConsistencyChecker(Logger logger, HomeManagerFactory factory, boolean testEntireStream, boolean acceptAllSentRequests) {
		this.logger = logger;
		this.factory = factory;
		this.testEntireStream = testEntireStream;
		this.acceptAllSentRequests = acceptAllSentRequests;
		statistics = new CheckStatistics();
	}

	/**
	 * @return the statistics
	 */
	public CheckStatistics getStatistics() {
		return statistics;
	}

	/**
	 * @return <code>true</code> if all the scope have passed the check, <code>false</code> otherwise.
	 * @throws InternalErrorException if an error occurs.
	 */
	public boolean checkAllScopes() throws InternalErrorException
	{
		List<String> scopes = factory.listScopes();
		boolean checkResult = true;
		for (String scope:scopes){
			checkResult &= checkScope(scope);
		}
		return checkResult;
	}
	
	public boolean checkScope(String scope) throws InternalErrorException
	{
		logger.info("start checking "+scope);
		HomeManager manager = factory.getHomeManager();
		boolean passed = checkHomeManager(manager);
		logger.info(scope+" "+(passed?"OK":"FAILED"));
		return passed;
	}

	protected boolean checkHomeManager(HomeManager manager) throws InternalErrorException{
		boolean managerCheck = true;
		List<User> users = manager.getUsers();
		String scope = manager.toString();
		HomeCheckStatistics homeStatistics = new HomeCheckStatistics();
		
		for(User user:users){
			try{
				logger.debug("checking user "+user.getPortalLogin());
				Home home = manager.getHome(user.getPortalLogin());
				boolean passed = checkHome(home, homeStatistics);
				managerCheck &= passed;
				logger.info(scope+" "+user.getPortalLogin()+" "+(passed?"OK":"FAILED"));
			}catch(HomeNotFoundException hne)
			{
				logger.error(scope+" "+user.getPortalLogin()+" FAILED", hne);
				managerCheck = false;
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	//	statistics.addHomeStatistics(manager.getScope().toString(), homeStatistics);
		
		return managerCheck;
	}

	protected boolean checkHome(Home home, HomeCheckStatistics homeStatistics)
	{
		try{
			Workspace wa = home.getWorkspace();
			WorkspaceConsistencyChecker waChecker = new WorkspaceConsistencyChecker(logger, wa, testEntireStream, acceptAllSentRequests);
			boolean result = waChecker.checkWorkspace();
			homeStatistics.addWorkspaceStatistics(home.getOwner().getPortalLogin(), waChecker.getStatistics());
			return result;
		}catch(Exception e)
		{
			logger.error("Error checking the home", e);
			return false;
		}
	}

}
