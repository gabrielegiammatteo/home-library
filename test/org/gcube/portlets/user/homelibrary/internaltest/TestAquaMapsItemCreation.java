/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.portlets.user.homelibrary.util.WorkspaceUtil;
import org.gcube.portlets.user.homelibrary.util.WorkspaceTreeVisitor;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestAquaMapsItemCreation {

	/**
	 * @param args nmot used.
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		
		String perstistenceRoot = "/tmp/home_library_persistence";
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory(perstistenceRoot);
		
		Workspace workspace = ExamplesUtil.createWorkspace(factory, "/test", "federico.defaveri");// createWorkspace(factory,"/gcube/devsec", "federico.defaveri");
		
		WorkspaceFolder root = workspace.getRoot();
		
		Map<String, InputStream> images = new LinkedHashMap<String,InputStream>();
		
		String dirName = "test/org/gcube/portlets/user/homelibrary/testdata/resources/aquamapitems/ai1/images";
		File dir = new File(dirName);
		for (File file:dir.listFiles())
		{
			if (file.isFile()){
				images.put(file.getName(),new FileInputStream(file));
			}
		}
		
		String metadataFile = "test/org/gcube/portlets/user/homelibrary/testdata/resources/aquamapitems/ai1/metadata.xml";
		
		InputStream is = new FileInputStream(metadataFile);
		
		root.createAquaMapsItem(WorkspaceUtil.getUniqueName("My AquamapsItem", root), "this is my first ami", 
				"mapName", 
				"mapType",
				"author",
				100,
				"boundingBox",
				0.5f,
				12,
				is, images);
		
		WorkspaceTreeVisitor wtv = new WorkspaceTreeVisitor();
		
		wtv.visitVerbose(root);
		
		/*List<String> addresses = new LinkedList<String>();
		addresses.add("federico.defaveri");
		wa.getItemSendRequestManager().sendRequest(aquaMapsItem.getId(), addresses);
		
		for (ItemSendRequest request:wa.getItemSendRequestManager().getRequests()){
			System.out.println("Processing: "+request);
			wa.getItemSendRequestManager().acceptRequest(request.getId());
		}

		
		wtv.visitVerbose(root);*/
		
		String gpodId = null;
		
		for (WorkspaceItem item:root.getChildren()){
			System.out.println("processing: "+item);
			if (item.getType() == WorkspaceItemType.FOLDER_ITEM){
				FolderItem folderItem = (FolderItem)item;
			if (folderItem.getFolderItemType() == FolderItemType.AQUAMAPS_ITEM){
				System.out.println("gpod: "+folderItem);
				gpodId = folderItem.getId();
			}
			}
		}
		
		workspace.decomposeAquaMapsItem(gpodId, "mytest", workspace.getRoot().getId());
		
		wtv.visitVerbose(root);
	}
	
	

}
