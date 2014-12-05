/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items;

import java.io.InputStream;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ExternalResourceBrokenLinkException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ExternalResourcePluginNotFoundException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Antonio Gioia
 *
 */
@Deprecated
public interface ExternalResourceLink extends FolderItem {
	
	String getResourceId();
	
	String getExternalResourcePlugin();
	
	String getMimeType();
	
	InputStream getData() throws InternalErrorException, ExternalResourceBrokenLinkException,
	ExternalResourcePluginNotFoundException;
}
