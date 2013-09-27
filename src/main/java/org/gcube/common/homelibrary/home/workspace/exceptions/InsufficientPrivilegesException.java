/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.exceptions;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class InsufficientPrivilegesException extends WorkspaceException {

	private static final long serialVersionUID = 2423742342338757212L;
	
	/**
	 * @param msg the exception message.
	 */
	public InsufficientPrivilegesException(String msg) {
		super(msg);
	}

}
