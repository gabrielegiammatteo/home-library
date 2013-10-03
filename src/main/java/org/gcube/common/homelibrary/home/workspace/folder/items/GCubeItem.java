/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items;

import java.util.List;

import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;

/**
 * @author Lucio Lelii lucio.lelii@isti.cnr.it
 *
 */
public interface GCubeItem extends WorkspaceItem {
	
	List<String> getScopes();
	
	String getItemType();
	
	String getCreator();

}
