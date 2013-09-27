/**
 * 
 */
package org.gcube.common.homelibrary.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceUtil {

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
			names.add(item.getName());
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
	 */
	public static FolderItem createExternalFile(WorkspaceFolder destinationFolder, String name, String description, String mimeType, InputStream is) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException
	{
		String mimeTypeChecked = mimeType;
			
		String extension = null;
		
		String[] values = name.split("\\.");
		if (values.length > 1)
		 extension = values[values.length - 1];
		
		List<String> zipMimeTypes = Arrays.asList(MimeTypeUtil.ZIP_MIMETYPES);
		if ((mimeTypeChecked.equals(MimeTypeUtil.BINARY_MIMETYPE) 
				|| zipMimeTypes.contains(mimeTypeChecked) 
				|| mimeTypeChecked.startsWith("application")) && (extension!= null)) {
			mimeTypeChecked = MimeTypeUtil.getMimeType(extension);			
		} 
		
		if (mimeTypeChecked!= null) {
			if (mimeTypeChecked.startsWith("image")){
				return destinationFolder.createExternalImageItem(name, description, mimeTypeChecked, is);
			}else if (mimeTypeChecked.equals("application/pdf")){
				return destinationFolder.createExternalPDFFileItem(name, description, mimeTypeChecked, is);
			}else if (mimeTypeChecked.equals("text/uri-list")){
				return destinationFolder.createExternalUrlItem(name, description, is);
			}
			
			return destinationFolder.createExternalFileItem(name, description, mimeTypeChecked, is);
		}
		
		return destinationFolder.createExternalFileItem(name, description, mimeType, is);
		
	}

}
