/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items.gcube;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface Metadata extends InfoObject, FolderItem{
	
	/**
	 * The schema of this metadata.
	 * @return the metadata schema.
	 */
	public String getSchema();
	
	/**
	 * The language of this metadata.
	 * @return the language.
	 */
	public String getLanguage();
	
	/**
	 * Returns the metadata content.
	 * @return the metadata.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public String getData() throws InternalErrorException;
	
	/**
	 * Returns the collection who belong the object referred by metadata.
	 * @return the collection name.
	 */
	public String getCollectionName();

}
