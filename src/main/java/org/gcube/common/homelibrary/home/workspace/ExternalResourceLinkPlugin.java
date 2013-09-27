/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace;

import java.io.InputStream;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ExternalResourceBrokenLinkException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ExternalResourcePluginNotFoundException;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalResourceLink;

/**
 * @author gioia
 *
 */
public interface ExternalResourceLinkPlugin {
	
	/**
	 * @return
	 */
	String getPluginName();

	/**
	 * @param item
	 * @return
	 * @throws ExternalResourceBrokenLinkException
	 * @throws InternalErrorException
	 */
	InputStream getContent(ExternalResourceLink item) throws ExternalResourceBrokenLinkException,
	InternalErrorException;
	
	/**
	 * @param item
	 * @return
	 * @throws ExternalResourceBrokenLinkException
	 * @throws InternalErrorException
	 */
	long getSize(ExternalResourceLink item)
			throws ExternalResourceBrokenLinkException, InternalErrorException;
}
