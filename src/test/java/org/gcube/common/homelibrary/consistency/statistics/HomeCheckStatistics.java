/**
 * 
 */
package org.gcube.common.homelibrary.consistency.statistics;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HomeCheckStatistics {
	
	protected int checkedUsers;
	protected Map<String, WorkspaceCheckStatistics> workspacesStatistics; 
	
	protected int checkedItems;
	protected int errors;
	protected int folders;
	protected Map<FolderItemType, Integer> folderItemTypesCount;
	
	public HomeCheckStatistics()
	{
		checkedUsers = 0;
		workspacesStatistics = new LinkedHashMap<String, WorkspaceCheckStatistics>();
		
		checkedItems = 0;
		errors = 0;
		folders = 0;
		folderItemTypesCount = new LinkedHashMap<FolderItemType, Integer>();
	}
	
	public void addWorkspaceStatistics(String user, WorkspaceCheckStatistics workspaceStatistics)
	{
		checkedUsers++;
		workspacesStatistics.put(user, workspaceStatistics);
		
		checkedItems += workspaceStatistics.getCheckedItems();
		errors += workspaceStatistics.getErrors();
		folders += workspaceStatistics.getFolders();
		
		for (Entry<FolderItemType, Integer> entry:workspaceStatistics.getFolderItemTypesCount().entrySet()){
			if (!folderItemTypesCount.containsKey(entry.getKey())){
				folderItemTypesCount.put(entry.getKey(), entry.getValue());
			} else {
				int count = folderItemTypesCount.get(entry.getKey());
				count += entry.getValue();
				folderItemTypesCount.put(entry.getKey(), count);
			}
		}
	}

	/**
	 * @return the checkedUsers
	 */
	public int getCheckedUsers() {
		return checkedUsers;
	}

	/**
	 * @return the workspacesStatistics
	 */
	public Map<String, WorkspaceCheckStatistics> getWorkspacesStatistics() {
		return workspacesStatistics;
	}

	/**
	 * @return the checkedItems
	 */
	public int getCheckedItems() {
		return checkedItems;
	}

	/**
	 * @return the errors
	 */
	public int getErrors() {
		return errors;
	}

	/**
	 * @return the folders
	 */
	public int getFolders() {
		return folders;
	}

	/**
	 * @return the folderItemTypesCount
	 */
	public Map<FolderItemType, Integer> getFolderItemTypesCount() {
		return folderItemTypesCount;
	}
}
