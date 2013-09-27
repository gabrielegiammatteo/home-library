/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.text.SimpleDateFormat;

import org.gcube.common.core.scope.GCUBEScope;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.HomeManager;
import org.gcube.portlets.user.homelibrary.home.HomeManagerFactory;
import org.gcube.portlets.user.homelibrary.home.User;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.AquaMapsItem;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.Report;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ReportTemplate;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestReadingWorkspace {
	
	protected static final SimpleDateFormat sdf = new SimpleDateFormat();

	/**
	 * @param args none
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		
		
		String userLogin = "federico.defaveri";

		String scope = "/test";
		HomeManagerFactory factory = HomeLibrary.getHomeManagerFactory("/tmp/home_library_persistence/");
		HomeManager manager = factory.getHomeManager();		

		
		User user = manager.getUser(userLogin);

		System.out.println("User "+user.getId()+" - "+user.getPortalLogin());
		System.out.println();

		Home home = manager.getHome(user,GCUBEScope.getScope(scope));
		Workspace workspace = home.getWorkspace();

		WorkspaceFolder root = workspace.getRoot();
		
		checkContent(root);
		
		


		System.out.println();


	}
	
	protected static void checkContent(WorkspaceItem item) throws InternalErrorException
	{
		if (item.getType() == WorkspaceItemType.FOLDER_ITEM){
			FolderItem folderItem = (FolderItem)item;
			if (folderItem.getFolderItemType() == FolderItemType.REPORT){
				Report report = (Report)folderItem;
				System.out.println("[Report]");
				System.out.println("Author "+report.getAuthor());
				System.out.println("LastEditBy "+report.getLastEditBy());
				System.out.println("TemplateName "+report.getTemplateName());
				System.out.println("NumberOfSections "+report.getNumberOfSections());
				System.out.println("Created "+sdf.format(report.getCreated().getTime()));
				System.out.println("LastEdit "+sdf.format(report.getLastEdit().getTime()));
				System.out.println("Status "+report.getStatus());
			}
			
			if (folderItem.getFolderItemType() == FolderItemType.REPORT_TEMPLATE){
				ReportTemplate reportTemplate = (ReportTemplate)folderItem;
				System.out.println("[ReportTemplate]");
				System.out.println("Author "+reportTemplate.getAuthor());
				System.out.println("LastEditBy "+reportTemplate.getLastEditBy());
				System.out.println("NumberOfSections "+reportTemplate.getNumberOfSections());
				System.out.println("Created "+sdf.format(reportTemplate.getCreated().getTime()));
				System.out.println("LastEdit "+sdf.format(reportTemplate.getLastEdit().getTime()));
				System.out.println("Status "+reportTemplate.getStatus());
			}
			
			if (folderItem.getFolderItemType()==FolderItemType.AQUAMAPS_ITEM){
				AquaMapsItem aquamapsitem = (AquaMapsItem)folderItem;
				System.out.println("[AquaMapsItem]");
				System.out.println("Author "+aquamapsitem.getAuthor());
				System.out.println("MapName "+aquamapsitem.getMapName());
				System.out.println("NumberOfSpecies "+aquamapsitem.getNumberOfSpecies());
				System.out.println("BoundingBox "+aquamapsitem.getBoundingBox());
				System.out.println("PsoThreshold "+aquamapsitem.getPsoThreshold());
				System.out.println("NumberOfGeneratedImages "+aquamapsitem.getNumberOfGeneratedImages());	
			}
		}
		
		for (WorkspaceItem workspaceItem:item.getChildren()) checkContent(workspaceItem);
	}

}
