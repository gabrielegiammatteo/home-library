/**
 * 
 */
package org.gcube.common.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.testdata.TwinDataManager;
import org.gcube.common.homelibrary.testdata.data.DocMetadata;
import org.gcube.common.homelibrary.testdata.data.PDFDocumentData;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class PDFDataManager extends TwinDataManager<PDFDocumentData, ExternalPDFFile, PDFDocument> {

	/**
	 * 
	 */
	public static final String DATA_FILE = "pdfs.xml";

	/**
	 * 
	 */
	public PDFDataManager() {
		super(DATA_FILE);
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("pdf", PDFDocumentData.class);
		xstream.alias("pdfs", LinkedList.class);
		xstream.alias("metadata", DocMetadata.class);
	}

	@Override
	protected PDFDocument createDocument(WorkspaceFolder destinationFolder, String name, String description, String oid, String mimeType, InputStream data, Map<String, String> metadata,
			Map<String, String> annotations, String collectionName) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		return destinationFolder.createPDFDocumentItem(name, description, oid, mimeType, data, metadata, annotations, collectionName);
	}

	@Override
	protected ExternalPDFFile createExternal(WorkspaceFolder destinationFolder, String name, String description, String mimeType, InputStream data)
			throws InternalErrorException, InsufficientPrivilegesException,	ItemAlreadyExistException {
		return destinationFolder.createExternalPDFFileItem(name, description, mimeType, data);
	}

}
