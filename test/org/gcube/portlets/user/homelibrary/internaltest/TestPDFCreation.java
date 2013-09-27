/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.io.File;
import java.io.FileInputStream;

import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestPDFCreation {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		HomeManagerFactory factory =  ExamplesUtil.getHomeManagerFactory("/tmp/test");
		
		Workspace wa = ExamplesUtil.createWorkspace(factory, "/test/scope", "test.user");
		
		WorkspaceFolder root = wa.getRoot();
		
		File tmp = File.createTempFile("test", "pdf");
		
		root.createExternalPDFFileItem("mytestpdf", "my desc", "application/pdf", new FileInputStream(tmp));
	
		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();
		
		wtv.visitVerbose(root);
	}

}
