/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestUnzip {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("test2.zip");

		ZipInputStream zis = new ZipInputStream(is);
		
		ZipEntry zipEntry;
		while((zipEntry = zis.getNextEntry())!=null)
		{
			String name = zipEntry.getName();
			System.out.println("Name: "+name);
			
			File f = new File(name);
			
			System.out.println("Path: "+f.getPath());
			
			boolean isDirectory = zipEntry.isDirectory();
			System.out.println("isDirectory: "+isDirectory);
		}

	}

}
