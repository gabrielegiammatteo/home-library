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
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.gcube.UrlDocument;
import org.gcube.portlets.user.homelibrary.testdata.TwinDataManager;
import org.gcube.portlets.user.homelibrary.testdata.data.DocMetadata;
import org.gcube.portlets.user.homelibrary.testdata.data.UrlDocumentData;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class UrlDataManager extends TwinDataManager<UrlDocumentData, ExternalUrl, UrlDocument> {

	/**
	 * 
	 */
	public static final String DATA_FILE = "urls.xml";

	/**
	 * 
	 */
	public UrlDataManager() {
		super(DATA_FILE);
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("metadata", DocMetadata.class);
		xstream.alias("url", UrlDocumentData.class);
		xstream.alias("urls", LinkedList.class);
	}

	@Override
	protected UrlDocument createDocument(WorkspaceFolder destinationFolder, String name, String description, String oid, String mimeType, InputStream data, Map<String, String> metadata,
			Map<String, String> annotations, String collectionName) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		return destinationFolder.createUrlDocumentItem(name, description, oid, mimeType, data, metadata, annotations, collectionName);
	}

	@Override
	protected ExternalUrl createExternal(WorkspaceFolder destinationFolder, String name, String description, String mimeType, InputStream data)
			throws InternalErrorException, InsufficientPrivilegesException,	ItemAlreadyExistException {
		return destinationFolder.createExternalUrlItem(name, description, data);
	}

}
