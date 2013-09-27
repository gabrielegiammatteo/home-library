/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.gcube.common.core.scope.GCUBEScope.MalformedScopeExpressionException;
import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.util.zip.ZipUtil;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestZipUtil {

	/**
	 * @param args
	 * @throws HomeNotFoundException 
	 * @throws InternalErrorException 
	 * @throws WorkspaceFolderNotFoundException 
	 * @throws MalformedScopeExpressionException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws MalformedScopeExpressionException, WorkspaceFolderNotFoundException, InternalErrorException, HomeNotFoundException, IOException {
		/*String perstistenceRoot = "/tmp/home_library_persistence";
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(perstistenceRoot);
		Workspace workspace = createWorkspace(factory,"/gcube/devsec", "federico.defaveri");
		*/
		
		//TestDataFactory.getInstance().fillAllFolderItem(workspace.getRoot());
		
		
		Workspace workspace = ExamplesUtil.createWorkspace();
		
		List<TimeSeries> ts = TestDataFactory.getInstance().fillTimeSeries(workspace.getRoot(), 1);
		
		System.out.println("Zipping");
		File zipFile = ZipUtil.zipTimeSeries(ts.get(0));
		System.out.println("Zip file: "+zipFile);
	}

}
