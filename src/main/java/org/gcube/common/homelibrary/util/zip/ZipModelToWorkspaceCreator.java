/**
 * 
 */
package org.gcube.common.homelibrary.util.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.util.MimeTypeUtil;
import org.gcube.common.homelibrary.util.WorkspaceUtil;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipFile;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipFolder;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipItem;

/**
 * This utility class read the zip model and create the corresponding element on the specified WorkspaceFolder.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ZipModelToWorkspaceCreator {

	protected Logger logger = Logger.getLogger(ZipModelToWorkspaceCreator.class);


	/**
	 * @param root the folder to zip.
	 * @param items the resulting zip folders.
	 */
	public void create(WorkspaceFolder root, List<ZipItem> items)
	{
		for (ZipItem item:items) {
			try {
				createWorkspaceItem(root, item);
			}catch(Exception e)
			{
				logger.error("Error creating item "+item,e);
			}
		}
	}

	protected void createWorkspaceItem(WorkspaceFolder parentFolder, ZipItem item) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, FileNotFoundException
	{
		switch (item.getType()) {
			case FOLDER: createWorkspace(parentFolder, (ZipFolder)item);break;
			case FILE: createItem(parentFolder, (ZipFile) item); break;
		}
	}

	protected void createWorkspace(WorkspaceFolder parentFolder, ZipFolder zipFolder) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException
	{
		String name  = WorkspaceUtil.getUniqueName(zipFolder.getName(), parentFolder);
		String description = (zipFolder.getComment()!=null)?zipFolder.getComment():"";

		WorkspaceFolder folder = parentFolder.createFolder(name, description);

		for (ZipItem workspaceFolder:zipFolder.getChildren()) {
			try{
				createWorkspaceItem(folder, workspaceFolder);
			}catch(Exception e)
			{
				logger.error("Error creating item "+workspaceFolder,e);
			}
		}

	}

	protected void createItem(WorkspaceFolder folder, ZipFile zipFile) throws FileNotFoundException, InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException
	{
		logger.trace("Creating item "+zipFile);

		String zipItemName = zipFile.getName();

		String name = WorkspaceUtil.getUniqueName(zipItemName, folder);	
		String description = (zipFile.getComment()!=null)?zipFile.getComment():"";
		InputStream is = new FileInputStream(zipFile.getContentFile());

		String mimeType = MimeTypeUtil.getMimeType(zipFile.getContentFile());
		
//		//we don't have an extension, we create a generic external file
//		FolderItem item = folder.createExternalFileItem(name, description, mimeType, is);

		FolderItem item = WorkspaceUtil.createExternalFile(folder, name, description, mimeType, is);
		
		logger.trace("Item created "+item);

		zipFile.getContentFile().delete();
		logger.trace("Tmp file deleted");
	}

}
