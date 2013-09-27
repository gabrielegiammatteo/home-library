/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface WorkspaceAreaFactory {
	
	/**
	 * Setup the workspace are before each test.
	 * @return the under testing workspace area.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public WorkspaceArea getWorkspaceArea() throws InternalErrorException;
	
	/**
	 * Clean the workspace area after each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public void cleanWorkspaceArea() throws InternalErrorException; 

}
