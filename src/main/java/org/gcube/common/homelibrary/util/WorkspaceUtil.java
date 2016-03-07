/**
 * 
 */
package org.gcube.common.homelibrary.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.accessmanager.ACLType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.usermanager.GCubeGroup;
import org.gcube.common.homelibrary.home.workspace.usermanager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceUtil {
	protected static Logger logger = LoggerFactory.getLogger(WorkspaceUtil.class);

	private static final String READ_ONLY 		= "jcr:read";
	private static final String WRITE_OWNER 	= "jcr:write";
	private static final String WRITE_ALL 		= "hl:writeAll";
	private static final String ADMINISTRATOR 	= "jcr:all";
	/**
	 * Retrieve an unique name for the specified folder.
	 * @param initialName the initial name.
	 * @param folder the item folder.
	 * @return the unique name.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static String getUniqueName(String initialName, WorkspaceFolder folder) throws InternalErrorException
	{

		String name = null;

		try{
			name = folder.getUniqueName(initialName, false);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if (name==null)
			name= initialName;
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
		String name = null;

		try{
			name = folder.getUniqueName(initialName, true);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if (name==null)
			name= initialName;
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
	public static FolderItem createExternalFile(WorkspaceFolder destinationFolder, String name, String description, String mimeType, String storageId) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException, IOException
	{
		return destinationFolder.createExternalFileItem(name, description, mimeType, storageId);
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
		return destinationFolder.createExternalGenericItem(name, description, is);
	}


	public static ACLType getACLTypeByKey(List<String> list) {
		switch(list.get(0)){

		case READ_ONLY:
			return ACLType.READ_ONLY;	

		case WRITE_OWNER:	
			return ACLType.WRITE_OWNER;		

		case WRITE_ALL:
			return ACLType.WRITE_ALL;	

		case ADMINISTRATOR:
			return ACLType.ADMINISTRATOR;		

		default:
			return ACLType.READ_ONLY;

		}		
	}

	public static List<String> getMembersByGroup(String id) throws InternalErrorException{

		UserManager um = HomeLibrary
				.getHomeManagerFactory().getUserManager();
		GCubeGroup group = null;
		List<String> members = null;
		try{
			group = um.getGroup(id);
			members = group.getMembers();
		}catch(Exception e){
			throw new InternalErrorException(id + " is not a valid groupId", e);
		}

		return members;
	}



}
