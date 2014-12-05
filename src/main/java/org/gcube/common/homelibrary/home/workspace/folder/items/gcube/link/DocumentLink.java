/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link;

import java.util.Map;

import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.DocumentMetadata;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@Deprecated
public interface DocumentLink extends InfoObjectLink {
	
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
	 * The document collection name.
	 * @return the collection name.
	 */
	public String getCollectionName();
	
	/**
	 * The image mime type.
	 * @return the mime type.
	 */
	public String getMimeType();

}
