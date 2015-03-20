/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder;


import org.gcube.common.homelibary.model.items.type.FolderItemType;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface FolderItem extends WorkspaceItem {
	
	/**
	 * The folder item type.
	 * @return the type;
	 */
	public FolderItemType getFolderItemType();
	
	/**
	 * The folder item length.
	 * @return the length.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public long getLength() throws InternalErrorException;
	
	
	public String getMimeType() throws InternalErrorException;
	
	/**
	 * Returns the workflow id.
	 * @return the workflow id.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public String getWorkflowId() throws InternalErrorException;
	
	/**
	 * Sets the workflow id.
	 * @param id the workflow id.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public void setWorkflowId(String id) throws InternalErrorException;
	
	/**
	 * Returns the workflow status.
	 * @return the workflow status.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public String getWorkflowStatus() throws InternalErrorException;
	
	/**
	 * Sets the workflow status.
	 * @param status the workflow status.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public void setWorkflowStatus(String status) throws InternalErrorException;
	
	/**
	 * Returns the workflow data.
	 * @return the workflow data.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public String getWorkflowData() throws InternalErrorException;
	
	/**
	 * Sets the workflow data.
	 * @param data the workflow data.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public void setWorkflowData(String data) throws InternalErrorException;


}
