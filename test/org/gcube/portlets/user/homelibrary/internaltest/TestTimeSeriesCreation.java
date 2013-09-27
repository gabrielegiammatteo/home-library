/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;


import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.portlets.user.homelibrary.util.WorkspaceUtil;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestTimeSeriesCreation {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		
		
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory("/home/fedy2/workspace/Workspace Portlet/tomcat/webapps/usersArea/home_library_persistence/");
		
		Workspace wa = ExamplesUtil.createWorkspace(factory,"/gcube/devsec", "test.user");
		
		WorkspaceFolder root = wa.getRoot();
		
		String resourceRoot = "/org/gcube/portlets/user/homelibrary/testdata/resources/";

		InputStream compressedTS = TestTimeSeriesCreation.class.getResourceAsStream(resourceRoot+"timeseries-big.zip");
		List<String> headerLabels = new LinkedList<String>();
		headerLabels.add("Owner");
		headerLabels.add("Flag");
		headerLabels.add("Area");
		headerLabels.add("Year");
		headerLabels.add("Species");
		headerLabels.add("Quantity");
		
		TimeSeries ts = root.createTimeSeries(WorkspaceUtil.getUniqueName("My firs TS",root), "the best TS", "001010", "TestTS", "federico.defaveri", "A simple TS", "11/11/11", "D4Science", "0002200", "CSV-123", "none", 645300, headerLabels, compressedTS);

		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();
		
		wtv.visitVerbose(root);
		
		
		ts.getData();
		
		ts.getCompressedData();
		
		
	}
	
	

}
