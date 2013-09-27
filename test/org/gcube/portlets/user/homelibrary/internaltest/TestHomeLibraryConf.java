/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestHomeLibraryConf {

	/**
	 * @param args none
	 * @throws InternalErrorException if an error occurs.
	 */
	public static void main(String[] args) throws InternalErrorException {
		HomeLibrary.getHomeManagerFactory();

	}

}
