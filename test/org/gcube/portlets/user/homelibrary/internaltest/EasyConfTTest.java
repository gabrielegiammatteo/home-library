/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import org.gcube.portlets.user.homelibrary.util.config.easyconf.ComponentConfiguration;
import org.gcube.portlets.user.homelibrary.util.config.easyconf.EasyConf;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class EasyConfTTest {

	/**
	 * @param args none.
	 */
	public static void main(String[] args) {
		ComponentConfiguration conf = EasyConf.getConfiguration("homelibrary");
		String root = conf.getProperties().getString("root-folder");
		System.out.println(root);

	}

}
