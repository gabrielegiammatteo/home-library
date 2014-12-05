/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items.gcube;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@Deprecated
public interface DocumentMetadata {
	
	/**
	 * Metadata schema name.
	 * @return the schema name.
	 */
	public String getSchemaName();
	
	/**
	 * Metadata XML.
	 * @return the XML
	 * @throws InternalErrorException if an internal error occurs. 
	 */
	public String getXML() throws InternalErrorException;

}
