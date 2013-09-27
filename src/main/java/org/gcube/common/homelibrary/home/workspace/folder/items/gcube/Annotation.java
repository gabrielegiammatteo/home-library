/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items.gcube;

import java.util.Map;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface Annotation extends InfoObject {
	
	/**
	 * @return the annotation data.
	 */
	public Map<String, String> getData();

}
