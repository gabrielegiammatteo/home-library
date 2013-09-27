/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

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
