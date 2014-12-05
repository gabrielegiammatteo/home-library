/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items.gcube;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentAlternativeLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentPartLink;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@Deprecated
public interface Document extends InfoObject, FolderItem{
	
	/**
	 * The document mime type.
	 * @return the mime type.
	 */
	public String getMimeType();
	
	/**
	 * The document associated metatada.
	 * @return the metadata.
	 */
	public Map<String, DocumentMetadata> getMetadata();
	
	/**
	 * The document associated annotations.
	 * @return the annotations.
	 */
	public Map<String, String> getAnnotation();
	
	
	/**
	 * Return this document alternatives.
	 * @return the alternatives.
	 */
	public List<DocumentAlternativeLink> getAlternatives();
	
	/**
	 * Return this document parts.
	 * @return the document parts.
	 */
	public List<DocumentPartLink> getParts();
	
	/**
	 * The document collection name.
	 * @return the collection name.
	 */
	public String getCollectionName();
	
	/**
	 * The document data.
	 * @return the data.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public InputStream getData() throws InternalErrorException;

}
