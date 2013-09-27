/**
 * 
 */
package org.gcube.common.homelibrary.consistency.statistics;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceCheckStatistics {
	
	protected int checkedItems;
	protected int errors;
	protected int folders;
	protected Map<FolderItemType, Integer> folderItemTypesCount;
	
	public WorkspaceCheckStatistics()
	{
		checkedItems = 0;
		errors = 0;
		folders = 0;
		folderItemTypesCount = new LinkedHashMap<FolderItemType, Integer>();
	}
	
	
	public void increaseFolderItems(FolderItemType type)
	{
		checkedItems++;
		if (!folderItemTypesCount.containsKey(type)) folderItemTypesCount.put(type, new Integer(1));
		else {
			int count = folderItemTypesCount.get(type);
			count++;
			folderItemTypesCount.put(type, count);
		}
	}

	public void increaseErrors()
	{
		errors++;
	}
	
	public void increaseFolders()
	{
		folders++;
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
