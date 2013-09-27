/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.portlets.user.homelibrary.testdata.TwinDataManager;
import org.gcube.portlets.user.homelibrary.testdata.data.DocMetadata;
import org.gcube.portlets.user.homelibrary.testdata.data.ImageDocumentData;
import org.gcube.portlets.user.homelibrary.testdata.data.PDFDocumentData;
import org.gcube.portlets.user.homelibrary.testdata.data.TestData;
import org.gcube.portlets.user.homelibrary.testdata.data.UrlDocumentData;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class DocumentDataManager extends TwinDataManager<TestData, ExternalFile, Document> {
	
	/**
	 * 
	 */
	public DocumentDataManager() {
		super("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<TestData> loadData(String dataListFile) {
		
		List<TestData> data = new LinkedList<TestData>();
		
		data.addAll(super.loadData(ImageDataManager.DATA_FILE));
		data.addAll(super.loadData(PDFDataManager.DATA_FILE));
		data.addAll(super.loadData(UrlDataManager.DATA_FILE));

		logger.trace("Loaded "+data.size()+" test data.");
		return data;
	}



	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("metadata", DocMetadata.class);
		xstream.alias("url", UrlDocumentData.class);
		xstream.alias("urls", LinkedList.class);
		xstream.alias("image", ImageDocumentData.class);
		xstream.alias("images", LinkedList.class);
		xstream.alias("pdf", PDFDocumentData.class);
		xstream.alias("pdfs", LinkedList.class);
	}

	@Override
	protected Document createDocument(WorkspaceFolder destinationFolder, String name, String description, String oid, String mimeType, InputStream data,	Map<String, String> metadata, Map<String, String> annotations,
			String collectionName) throws InternalErrorException,
			InsufficientPrivilegesException, ItemAlreadyExistException {
		
		System.out.println(" ######################## Start creation document ");
		Document document = destinationFolder.createDocumentItem(name, description, oid, mimeType, data, metadata, annotations, collectionName);
		System.out.println(" ####################### finish creation document ");
		return document;
	}

	@Override
	protected ExternalFile createExternal(WorkspaceFolder destinationFolder,
			String name, String description, String mimeType, InputStream data)
			throws InternalErrorException, InsufficientPrivilegesException,
			ItemAlreadyExistException {
		return destinationFolder.createExternalFileItem(name, description, mimeType, data);
	}
	

}
