/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.trash;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */


public interface TrashItem {
	
	void deletePermanently() throws InternalErrorException;
	
	void restore() throws InternalErrorException;
}