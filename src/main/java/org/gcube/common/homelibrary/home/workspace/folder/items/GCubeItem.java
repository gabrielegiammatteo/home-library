/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items;

import java.util.List;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Lucio Lelii lucio.lelii@isti.cnr.it
 *
 */
public interface GCubeItem extends FolderItem {
	
	List<String> getScopes() throws InternalErrorException;
	
	String getItemType();
	
	String getCreator();

	Map<String, String> getItemProperties() throws InternalErrorException;
	
	void setItemProperties(Map<String, String> properties) throws InternalErrorException;

}
