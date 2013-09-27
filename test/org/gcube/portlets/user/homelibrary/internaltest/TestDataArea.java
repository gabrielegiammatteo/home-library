/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.util.Map.Entry;

import org.gcube.portlets.user.homelibrary.examples.ExamplesUtil;
import org.gcube.portlets.user.homelibrary.home.Home;
import org.gcube.portlets.user.homelibrary.home.data.DataArea;
import org.gcube.portlets.user.homelibrary.home.data.application.ApplicationDataArea;
import org.gcube.portlets.user.homelibrary.home.data.application.ApplicationList;
import org.gcube.portlets.user.homelibrary.home.data.application.ApplicationMap;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestDataArea {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		Home home = ExamplesUtil.createHome(ExamplesUtil.getHomeManagerFactory("/tmp/test"),"/gcube/devsec", "federico.defaveri");
		
		DataArea dataArea = home.getDataArea();
		
		ApplicationDataArea applicationDataArea = dataArea.getApplicationDataArea("myApplication");
		
		String dataName = "MyList";
		ApplicationList<String>  mylist = applicationDataArea.<String>getList(dataName);
		
		mylist.add("data1");
		mylist.add("data2");
		mylist.add("data3");
		mylist.add("data4");
		
		for (String data:mylist){
			System.out.println("data: "+data);
		}
		
		ApplicationMap<String, String> mymap = applicationDataArea.<String,String>getMap("test");
		
		mymap.put("test1"+System.currentTimeMillis(), "value1");
		mymap.put("test2"+System.currentTimeMillis(), "value2");
		mymap.put("test3"+System.currentTimeMillis(), "value3");
		mymap.put("test4"+System.currentTimeMillis(), "value4");
		
		for (Entry<String, String> entry:mymap.entrySet()){
			System.out.println("key: "+entry.getKey()+" value: "+entry.getValue());
		}
	}

}
