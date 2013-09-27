/**
 * 
 */
package org.gcube.portlets.user.homelibrary.consistency;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Map.Entry;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.gcube.portlets.user.homelibrary.consistency.statistics.CheckStatistics;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.portlets.user.homelibrary.util.logging.LoggingUtil;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HomeLibraryConsistencyChecker {

	/**
	 * @param args use help to get more information.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException if an error occurs.
	 */
	public static void main(String[] args) throws InternalErrorException, IOException {
		
		if (args.length == 1 && (args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("help") )){
			printUsage();
		}
		
		String rootDir = null;
		if (args.length == 0) {
			System.out.println("No parameters specified, I will try to get the Home Library Persistency root from the system");
		} else {
			System.out.println("Home Library Persistency root to use: "+args[0]);
			rootDir = args[0];
		}
		
		boolean testEntireStream = false;
		if (args.length > 1) testEntireStream = Boolean.parseBoolean(args[1]);
		System.out.println("Test entire stream: "+testEntireStream);
		

		
		boolean acceptAllRequests = false;
		if (args.length > 2) acceptAllRequests = Boolean.parseBoolean(args[2]);
		System.out.println("Accept all requests: "+acceptAllRequests);
		
		if (acceptAllRequests) {
			System.out.println("Are you sure to accept all send requests for each user? this will modify the user workspace!!! (y/n)");
			int reply = System.in.read();
			if (reply == 'n' || reply == 'N'){
				System.err.println("Abort request by user");
				System.exit(0);
			}
		}
		
		boolean loadScopeList = false;
		File scopeListFile = null;
		if (args.length>3) {
			scopeListFile = new File(args[3]);
			if (!scopeListFile.exists()){
				System.err.println("The scope list file "+scopeListFile+" don't exists.");
				System.exit(1);
			}
			loadScopeList = true;
		}
		
		LoggingUtil.reconfigureLogging();
		
		HomeManagerFactory factory;
		
		if (rootDir == null) {
			factory = HomeLibrary.getHomeManagerFactory();
		} else {
			factory = HomeLibrary.getHomeManagerFactory(rootDir);
		}
		
		Logger logger = Logger.getLogger("checker");
		logger.setLevel(Level.ALL);
		ConsoleAppender ca = new ConsoleAppender(new SimpleLayout());
		ca.setThreshold(Level.INFO);
		logger.addAppender(ca);
		FileAppender faTrace = new FileAppender(new SimpleLayout(), "hlcheck.trace.log", false);
		faTrace.setThreshold(Level.ALL);
		logger.addAppender(faTrace);
		
		FileAppender faInfo = new FileAppender(new SimpleLayout(), "hlcheck.info.log", false);
		faInfo.setThreshold(Level.INFO);
		logger.addAppender(faInfo);
		
		HomeManagerFactoryConsistencyChecker checker = new HomeManagerFactoryConsistencyChecker(logger, factory, testEntireStream, acceptAllRequests);

		boolean check = false;
		if (!loadScopeList) check = checker.checkAllScopes();
		else {
			LineNumberReader reader = new LineNumberReader(new FileReader(scopeListFile));
			String scope = reader.readLine();
			while(scope!=null) {
				check &= checker.checkScope(scope);
				scope = reader.readLine();
			}
			reader.close();
		}
		
		if (check) {
			System.out.println("No error founds");
			printStatistics(checker.getStatistics());
			System.exit(0);
		} else {
			System.err.println("Error founds");
			printStatistics(checker.getStatistics());
			System.exit(1);
		}
	}
	
	protected static void printStatistics(CheckStatistics statistics)
	{
		System.out.println();
		System.out.println("Check statistics:");
		System.out.println("- checked scopes: "+statistics.getCheckedScopes());
		System.out.println("- checked users: "+statistics.getCheckedUsers());
		System.out.println("- errors: "+statistics.getErrors());
		System.out.println("- items: "+statistics.getCheckedItems());
		System.out.println("Item types:");
		System.out.println("- folders: "+statistics.getFolders());
		for (Entry<FolderItemType, Integer> entry:statistics.getFolderItemTypesCount().entrySet()) System.out.println("- "+entry.getKey().toString().toLowerCase()+": "+entry.getValue().intValue());
		System.out.println();
	}
	
	/**
	 * 
	 */
	public static void printUsage()
	{
		System.out.println("Home Library Consistency Checker");
		System.out.println("HomeLibraryConsistencyChecker [home library persistence root] [check entire stream] [accept all requests (make the check destructive)] [scope list file]");
		System.out.println();
		System.exit(0);
	}

}
