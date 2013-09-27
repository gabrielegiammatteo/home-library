/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderBulkCreator;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderBulkCreatorManager;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestFolderBulkCreator {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		Workspace workspace = ExamplesUtil.createWorkspace("/d4science.research-infrastructures.eu/EM");

		WorkspaceFolder folder = workspace.getRoot().createFolder("TestFolder", "This is a test folder");
		
		FolderBulkCreator fbc = folder.getNewFolderBulkCreator();
		/*bbc.createMetadata("2778f1f0-fc6f-11dd-912d-ce096db3559e","testobject1");
		bbc.createMetadata("27094030-fc6f-11dd-912d-ce096db3559e","testobject2");
		bbc.createMetadata("4eb8b7b0-fc73-11dd-912d-ce096db3559e","testobject3");
		bbc.createMetadata("4e511c40-fc73-11dd-912d-ce096db3559e","testobject4");*/
		
		/*2009.03.08 07:25:57 TRACE [http-8080-Processor19] - ImportDocument oid: 3f8d4100-00c6-11de-8e55-ae4f02e984c5 collectionId: 1030ece0-00c6-11de-8e55-ae4f02e984c5
		2009.03.08 07:25:57 TRACE [http-8080-Processor19] - ImportDocument oid: 3e13edd0-00ce-11de-8e55-ae4f02e984c5 collectionId: 1030ece0-00c6-11de-8e55-ae4f02e984c5
		2009.03.08 07:25:57 TRACE [http-8080-Processor19] - ImportDocument oid: 3f9f0760-00de-11de-8e59-ae4f02e984c5 collectionId: 1030ece0-00c6-11de-8e55-ae4f02e984c5
		bbc.createDocumentItem("38282480-0106-11de-8e6b-ae4f02e984c5","f519f550-00c5-11de-8e55-ae4f02e984c5");
		
		*/
		
		/*fbc.createDocumentItem("04ef4540-09d4-11de-8f0c-a19cf0b0d020","a52c31e0-09d3-11de-8f0c-a19cf0b0d020");
		fbc.createDocumentItem("c50a4e70-09d3-11de-8f0c-a19cf0b0d020","a52c31e0-09d3-11de-8f0c-a19cf0b0d020");
		fbc.createDocumentItem("06034d50-09d4-11de-8f0c-a19cf0b0d020","a52c31e0-09d3-11de-8f0c-a19cf0b0d020");
		fbc.createDocumentItem("051cbde0-09d4-11de-8f0c-a19cf0b0d020","a52c31e0-09d3-11de-8f0c-a19cf0b0d020");*/
		
						
		fbc.commit();
		
		FolderBulkCreatorManager bulkCreatorManager = workspace.getFolderBulkCreatorManager();
		bulkCreatorManager.waitFolderBulkCreator(fbc.getId());
		
		WorkspaceFolder root = workspace.getRoot();

		WorkspaceTreeVisitor visitor = new WorkspaceTreeVisitor();
		visitor.visitVerbose(root);
	}

}
