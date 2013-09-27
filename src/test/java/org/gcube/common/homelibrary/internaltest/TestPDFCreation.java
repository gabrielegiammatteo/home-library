/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import java.io.File;
import java.io.FileInputStream;

import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.HomeManagerFactory;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.util.WorkspaceTreeVisitor;
import org.gcube.common.scope.api.ScopeProvider;


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
		ScopeProvider.instance.set("/test/scope");
		HomeManagerFactory factory =  ExamplesUtil.getHomeManagerFactory("/tmp/test");
		
		Workspace wa = ExamplesUtil.createWorkspace(factory, "test.user");
		
		WorkspaceFolder root = wa.getRoot();
		
		File tmp = File.createTempFile("test", "pdf");
		
		root.createExternalPDFFileItem("mytestpdf", "my desc", "application/pdf", new FileInputStream(tmp));
	
		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();
		
		wtv.visitVerbose(root);
	}

}
