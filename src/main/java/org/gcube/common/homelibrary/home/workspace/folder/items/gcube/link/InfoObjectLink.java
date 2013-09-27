/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link;

import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface InfoObjectLink extends FolderItem {
	
	/**
	 * The info object oid.
	 * @return the oid.
	 */
	public String getOID();
	
}
