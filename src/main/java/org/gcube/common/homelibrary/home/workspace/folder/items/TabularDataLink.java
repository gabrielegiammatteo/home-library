/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items;

import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;



/**
 * @author Antonio Gioia
 *
 */
@Deprecated
public interface TabularDataLink extends FolderItem{
	
	public enum Provenance {
		IMPORTERD,
		
		COMPUTED,
		
		SYSTEM
	}
	
	public String getTableId();
		
	public String getTemplate();
	
	public Provenance getProvenance();
	
	public String getOperator();
	
	public String getDBRuntimeResource();
	
}
