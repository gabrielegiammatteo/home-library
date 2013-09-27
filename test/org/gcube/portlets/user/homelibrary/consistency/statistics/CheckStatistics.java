/**
 * 
 */
package org.gcube.portlets.user.homelibrary.consistency.statistics;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class CheckStatistics {
	
	protected int checkedScopes;
	
	protected int checkedUsers;
	protected Map<String, HomeCheckStatistics> homesStatistics; 
	
	protected int checkedItems;
	protected int errors;
	protected int folders;
	protected Map<FolderItemType, Integer> folderItemTypesCount;
	
	public CheckStatistics()
	{
		checkedScopes = 0;
		
		checkedUsers = 0;
		homesStatistics = new LinkedHashMap<String, HomeCheckStatistics>();
		
		checkedItems = 0;
		errors = 0;
		folders = 0;
		folderItemTypesCount = new LinkedHashMap<FolderItemType, Integer>();
	}
	
	public void addHomeStatistics(String scope, HomeCheckStatistics homeCheckStatistics)
	{
		checkedScopes++;
		
		checkedUsers+= homeCheckStatistics.getCheckedUsers();
		homesStatistics.put(scope, homeCheckStatistics);
		
		checkedItems += homeCheckStatistics.getCheckedItems();
		errors += homeCheckStatistics.getErrors();
		folders += homeCheckStatistics.getFolders();
		
		for (Entry<FolderItemType, Integer> entry:homeCheckStatistics.getFolderItemTypesCount().entrySet()){
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
	 * @return the checkedScopes
	 */
	public int getCheckedScopes() {
		return checkedScopes;
	}

	/**
	 * @return the checkedUsers
	 */
	public int getCheckedUsers() {
		return checkedUsers;
	}

	/**
	 * @return the homesStatistics
	 */
	public Map<String, HomeCheckStatistics> getHomesStatistics() {
		return homesStatistics;
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
