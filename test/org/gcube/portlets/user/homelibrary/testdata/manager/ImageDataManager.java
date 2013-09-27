/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Map;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.portlets.user.homelibrary.testdata.TwinDataManager;
import org.gcube.portlets.user.homelibrary.testdata.data.DocMetadata;
import org.gcube.portlets.user.homelibrary.testdata.data.ImageDocumentData;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ImageDataManager extends TwinDataManager<ImageDocumentData, ExternalImage, ImageDocument> {

	/**
	 * 
	 */
	public static final String DATA_FILE = "images.xml";

	/**
	 * 
	 */
	public ImageDataManager() {
		super(DATA_FILE);
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("image", ImageDocumentData.class);
		xstream.alias("images", LinkedList.class);
		xstream.alias("metadata", DocMetadata.class);
	}

	@Override
	protected ImageDocument createDocument(WorkspaceFolder destinationFolder, String name, String description, String oid, String mimeType, InputStream data, Map<String, String> metadata,
			Map<String, String> annotations, String collectionName) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		return destinationFolder.createImageDocumentItem(name, description, oid, mimeType, data, metadata, annotations, collectionName);
	}

	@Override
	protected ExternalImage createExternal(WorkspaceFolder destinationFolder, String name, String description, String mimeType, InputStream data)
			throws InternalErrorException, InsufficientPrivilegesException,	ItemAlreadyExistException {
		return destinationFolder.createExternalImageItem(name, description, mimeType, data);
	}

}
