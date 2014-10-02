/**
 * 
 */
package org.gcube.common.homelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceUtil {
	protected static Logger logger = LoggerFactory.getLogger(WorkspaceUtil.class);
	/**
	 * Retrieve an unique name for the specified folder.
	 * @param initialName the initial name.
	 * @param folder the item folder.
	 * @return the unique name.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static String getUniqueName(String initialName, WorkspaceFolder folder) throws InternalErrorException
	{

		List<? extends WorkspaceItem> children = folder.getChildren();

		List<String> names = new LinkedList<String>();
		for (WorkspaceItem item:children) {
			try{
				names.add(item.getName());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}

		String name = initialName;
		int i = 0;

		while(names.contains(name)){

			name = initialName+"("+i+")";
			i++;
		}

		return name;
	}


	/**
	 * Retrieve an unique name copying a item to the specified folder.
	 * @param initialName the initial name.
	 * @param folder the item folder.
	 * @return the unique name.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static String getCopyName(String initialName, WorkspaceFolder folder) throws InternalErrorException
	{

		List<? extends WorkspaceItem> children = folder.getChildren();

		List<String> names = new LinkedList<String>();
		for (WorkspaceItem item:children) {
			try{
				names.add(item.getName());
			}catch (Exception e) {
				// TODO: handle exception
			}
		}

		String name = initialName;
		int i = 1;

		while(names.contains(name)){
			//			name = initialName + "." + returnThreeDigitNo(i);
			if (i==1)
				name = initialName + "(copy)";
			else
				name = initialName + "(copy " + i +")";
			i++;
		}

		return name;
	}

	/**
	 * Add prefix to number e.g (1 = 001)
	 * @param number
	 * @return
	 */
	public static String returnThreeDigitNo(int number)
	{
		String threeDigitNo = null;
		int length = String.valueOf(number).length();

		if(length == 1)
			threeDigitNo = "00"+number;

		if(length == 2)
			threeDigitNo = "0"+number;

		if(length == 3)
			threeDigitNo = ""+number;

		return threeDigitNo;
	}


	/**
	 * Clean the given name from invalid chars.
	 * @param name the name to clean.
	 * @return the cleaned name.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static String cleanName(String name) throws InternalErrorException
	{
		return name.replace('/', '_');
	}

	/**
	 * Create a external file in the specified folder.
	 * @param destinationFolder the destination folder.
	 * @param name the external file name.
	 * @param description the external file description.
	 * @param mimeType the external file mimetype.
	 * @param is the external file data.
	 * @return the created external file. 
	 * @throws InsufficientPrivilegesException if an error occurs.
	 * @throws InternalErrorException if an error occurs.
	 * @throws ItemAlreadyExistException if an error occurs.
	 * @throws IOException 
	 */
	public static FolderItem createExternalFile(WorkspaceFolder destinationFolder, String name, String description, String mimeType, InputStream is) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException, IOException
	{	
		String mimeTypeChecked;
		File tmpFile = null;
		try{
			if (mimeType==null){
				tmpFile = WorkspaceUtil.getTmpFile(is);	

				try{						
					mimeType = MimeTypeUtil.getMimeType(name, tmpFile);
					System.out.println(mimeType);
//					is = new FileInputStream(tmpFile);
				}catch (Exception e) {
					logger.error("Error getting mimetype of file " + name);
				}finally{}
			}
			mimeTypeChecked = mimeType;

			if (mimeTypeChecked!= null) {
				if (mimeTypeChecked.startsWith("image")){
					//					System.out.println("image");
					return destinationFolder.createExternalImageItem(name, description, mimeTypeChecked, tmpFile);
				}else if (mimeTypeChecked.equals("application/pdf")){
					return destinationFolder.createExternalPDFFileItem(name, description, mimeTypeChecked, tmpFile);
				}else if (mimeTypeChecked.equals("text/uri-list")){
					System.out.println("*********** external url");
					String url = Util.readStreamAsString(new FileInputStream(tmpFile));
					return destinationFolder.createExternalUrlItem(name, description, url);
					}

				return destinationFolder.createExternalFileItem(name, description, mimeTypeChecked, tmpFile);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
//			if (tmpFile!=null)
//				tmpFile.delete();
		}

		return destinationFolder.createExternalFileItem(name, description, mimeType, tmpFile);

	}


	public static File getTmpFile(InputStream in){

		File tempFile = null;
		try{
			tempFile = File.createTempFile("tempfile", ".tmp"); 

			try (FileOutputStream out = new FileOutputStream(tempFile)) {		
				IOUtils.copy(in, out);
			}
		}catch(IOException e){
			logger.error("Error creating tmp file");
		}finally{
			try {
				in.close();
			} catch (IOException e) {logger.error("Error closing tmp file");}
		}

		return tempFile;
	}




}
