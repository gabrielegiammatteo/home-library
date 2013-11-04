/**
 * 
 */
package org.gcube.common.homelibrary.consistency;

import java.util.List;
import java.util.Map.Entry;

import org.gcube.common.homelibrary.consistency.statistics.WorkspaceCheckStatistics;
import org.gcube.common.homelibrary.examples.ExamplesUtil;
import org.gcube.common.homelibrary.home.exceptions.HomeNotFoundException;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.AquaMapsItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.common.homelibrary.home.workspace.folder.items.File;
import org.gcube.common.homelibrary.home.workspace.folder.items.Image;
import org.gcube.common.homelibrary.home.workspace.folder.items.PDF;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.Url;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.DocumentMetadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.UrlDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentAlternativeLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentPartLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.common.homelibrary.home.workspace.sharing.WorkspaceMessageManager;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.gcube.common.homelibrary.consistency.CheckerUtil.*;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 * 
 */
public class WorkspaceConsistencyChecker {

	private static Logger logger = LoggerFactory.getLogger(WorkspaceConsistencyChecker.class); 
	
	protected Workspace workspace;
	protected boolean testEntireStream;
	protected WorkspaceCheckStatistics statistics;
	protected boolean acceptAllSentRequests = false;

	/**
	 * @param logger the checker logger.
	 * @param workspace the workspace to check.
	 * @param testEntireStream <code>true</code> to check the folder items streams, <code>false</code> otherwise.
	 * @param acceptAllSentRequests <code>true</code> to accept all sent requests and process the received elements. This will make the check destructive.
	 */
	public WorkspaceConsistencyChecker(Workspace workspace, boolean testEntireStream, boolean acceptAllSentRequests) {
		this.workspace = workspace;
		this.testEntireStream = testEntireStream;
		this.acceptAllSentRequests = acceptAllSentRequests;
		statistics = new WorkspaceCheckStatistics();
	}

	/**
	 * @return the statistics
	 */
	public WorkspaceCheckStatistics getStatistics() {
		return statistics;
	}

	/**
	 * @return <code>true</code> if the workspace have passed the check, <code>false</code> otherwise.
	 */
	public boolean checkWorkspace() {
		logger.debug("start checking the Workspace");
		
		if (acceptAllSentRequests){
			try{
				logger.trace("Accepting all received items");
				WorkspaceMessageManager manager = workspace.getWorkspaceMessageManager();
//				List<WorkspaceMessage> requests = manager.getRequests();
//				for (WorkspaceMessage request:requests) {
//					manager.acceptRequest(request.getId());
//				}

			}catch(Exception e)
			{
				logger.debug("Accepting all received items FAILED: "+e.getMessage());
				logger.trace("Accepting all received items FAILED: "+e.getMessage(), e);
				return false;
			}
		}

		WorkspaceFolder root = null;
		try{
			root = workspace.getRoot();
			logger.trace("Checking root "+root);
			checkNotNull("ROOT", root);
			
			checkNotNull("Id", root.getId());
			checkNotNull("Name", root.getName()); 
			checkNotNull("Description", root.getDescription());
			checkNotNull("Owner", root.getOwner());
			checkNotNull("Type", root.getType());
			
			statistics.increaseFolders();
		}catch(Exception e)
		{
			logger.debug("Root check FAILED: "+e.getMessage());
			logger.trace("Root check FAILED: "+e.getMessage(), e);
			statistics.increaseErrors();
			return false;
		}
		
		

		try{
			logger.trace("Checking root children");
			
			List<? extends WorkspaceItem> itemChildren = root.getChildren();

			checkNotNull("Children", itemChildren);

			for (WorkspaceItem itemChild:itemChildren){

				checkNotNull("itemChild", itemChild);
				checkWorkspaceItem(itemChild);
			}

		}catch(Exception e)
		{
			logger.debug("Root children check FAILED: "+e.getMessage());
			logger.trace("Root children check FAILED: "+e.getMessage(), e);
			statistics.increaseErrors();
			return false;
		}

		logger.debug("Workspace OK");
		return true;
	}

	protected void checkWorkspaceItem(WorkspaceItem item) throws CheckException
	{
		logger.trace("checking item: "+item);

		try{
			checkNotNull("Id", item.getId());
			checkNotNull("Name", item.getName()); 
			checkNotNull("Description", item.getDescription());
			checkNotNull("Owner", item.getOwner());
			checkNotNull("Type", item.getType());
			checkNotNull("Parent", item.getParent());

			switch (item.getType()) {
				case FOLDER_ITEM: {
					FolderItem folderItem = (FolderItem)item;
					checkFolderItem(folderItem); 
					statistics.increaseFolderItems(folderItem.getFolderItemType());
					break;
				}
				case FOLDER: statistics.increaseFolders(); break;
			}

			List<? extends WorkspaceItem> itemChildren = item.getChildren();

			checkNotNull("Children", itemChildren.size());

			for (WorkspaceItem itemChild:itemChildren){

				checkNotNull("itemChild", itemChild);
				checkWorkspaceItem(itemChild);
			}
		}catch(InternalErrorException e)
		{
			logger.error("Error checking workspace item: "+item, e);
			statistics.increaseErrors();
		}
	}

	protected void checkFolderItem(FolderItem item) throws CheckException {

		logger.trace("checking FolderItem: "+item);

		try{
			checkNotNull("FolderItemType", item.getFolderItemType());
			checkNotNull("Length", item.getLength());

			switch (item.getFolderItemType()) {
				case EXTERNAL_FILE: checkFile((ExternalFile) item); break;
				case EXTERNAL_IMAGE: checkImage((ExternalImage) item); break;
				case EXTERNAL_PDF_FILE: {
					checkFile((ExternalPDFFile) item);
					checkPDF((ExternalPDFFile) item);
				}
				break;
				case EXTERNAL_URL: checkUrl((ExternalUrl) item); break;
				case TIME_SERIES: checkTimeSeries((TimeSeries) item); break;
				case QUERY: checkQuery((Query) item); break;
				case REPORT: checkReport((Report) item); break;
				case REPORT_TEMPLATE: checkReportTemplate((ReportTemplate) item); break;
				case AQUAMAPS_ITEM: checkAquaMapsItem((AquaMapsItem) item); break;
				case DOCUMENT: checkDocument((Document) item); break;
				case IMAGE_DOCUMENT: {
					checkDocument((ImageDocument) item);
					checkImage((ImageDocument) item);
					break;
				}
				
				case PDF_DOCUMENT: {
					checkDocument((PDFDocument) item);
					checkPDF((PDFDocument) item);
					break;
				}
				case URL_DOCUMENT: {
					checkDocument((UrlDocument) item);
					checkUrl((UrlDocument) item);
					break;
				}
				case METADATA: checkMetadata((Metadata) item); break;
			}
		} catch (InternalErrorException e) {
			logger.error("Error checking folder item "+item, e);
			throw new CheckException("Error checking folder item "+item, e);
		}

	}

	protected void checkFile(File item) throws CheckException {
		try{
			checkNotNull("Name", item.getName());
			//FIXME checkNotNull("MimeType", item.getMimeType());
			checkNotNull("Length", item.getLength());
			checkNotNull("Data", item.getData());
			if (testEntireStream) checkStream("Data", item.getData());
		} catch (InternalErrorException e) {
			logger.error("Error checking file "+item, e);
			throw new CheckException("Error checking file "+item, e);
		}
	}

	protected void checkImage(Image item) throws CheckException {
		try{
			checkFile(item);
			checkNotNull("Width", item.getWidth());
			checkNotNull("Height", item.getHeight());
			checkNotNull("ThumbnailWidth", item.getThumbnailWidth());
			checkNotNull("ThumbnailHeight", item.getThumbnailHeight());
			checkNotNull("ThumbnailLength", item.getThumbnailLength());
			checkNotNull("Thumbnail", item.getThumbnail());
			if (testEntireStream) checkStream("Thumbnail", item.getThumbnail());
		} catch (InternalErrorException e) {
			logger.error("Error checking image "+item, e);
			throw new CheckException("Error checking image "+item, e);
		}
	}

	protected void checkPDF(PDF item) throws CheckException {
		checkNotNull("Title", item.getTitle());
		checkNotNull("Author", item.getAuthor());
		checkNotNull("Producer", item.getProducer());
		checkNotNull("Version", item.getVersion());
		checkNotNull("NumberOfPages", item.getNumberOfPages());
	}

	protected void checkUrl(Url item) throws CheckException {
		try{
			checkNotNull("Url", item.getUrl());
		} catch (InternalErrorException e) {
			logger.error("Error checking url "+item, e);
			throw new CheckException("Error checking url "+item, e);
		}
	}

	protected void checkDocument(Document item) throws CheckException {

		try{
			//FIXME checkNotNull("MimeType", item.getMimeType());
			checkNotNull("OID", item.getURI());
			checkNotNull("CollectionName", item.getCollectionName());

			checkNotNull("Metadata", item.getMetadata());
			for (Entry<String, DocumentMetadata> entry : item.getMetadata().entrySet()) {
				checkNotNull("(Metadata) SchemaName", entry.getValue().getSchemaName());
				checkNotNull("(Metadata) XML", entry.getValue().getXML());
			}

			checkNotNull("Alternatives", item.getAlternatives());
			for (DocumentAlternativeLink itemAlternative : item.getAlternatives()) {
				checkNotNull("(Alternative) ParentOID", itemAlternative.getParentURI());
				checkNotNull("(Alternative) OID", itemAlternative.getURI());
				checkNotNull("(Alternative) Name", itemAlternative.getName());
				//FIXME checkNotNull("(Alternative) MimeType", itemAlternative.getMimeType());
			}

			checkNotNull("Annotation", item.getAnnotation());
			for (Entry<String, String> entry : item.getAnnotation().entrySet()) {
				checkNotNull("(Annotation) Value", entry.getValue());
			}

			checkNotNull("Parts", item.getParts());
			for (DocumentPartLink itemPart : item.getParts()) {
				checkNotNull("(Part) ParentOID", itemPart.getParentURI());
				checkNotNull("(Part) OID", itemPart.getURI());
				checkNotNull("(Part) Name", itemPart.getName());
				//FIXME checkNotNull("(Part) MimeType", itemPart.getMimeType());
			}

			checkNotNull("Data", item.getData());
			if (testEntireStream)
				checkStream("Data", item.getData());

		} catch (InternalErrorException e) {
			logger.error("Error checking document "+item, e);
			throw new CheckException("Error checking document "+item, e);
		}
	}

	protected void checkMetadata(Metadata item) throws CheckException {
		try{
			checkNotNull("OID", item.getURI());
			checkNotNull("Schema", item.getSchema());
			checkNotNull("Language", item.getLanguage());
			checkNotNull("CollectionName", item.getCollectionName());
			checkNotNull("Data", item.getData());
		} catch (InternalErrorException e) {
			logger.error("Error checking metadata "+item, e);
			throw new CheckException("Error checking metadata "+item, e);
		}
	}

	protected void checkTimeSeries(TimeSeries item) throws CheckException {
		try{
			checkNotNull("TimeSeriesInfo", item.getTimeSeriesInfo());
			checkNotNull("NumberOfColumns", item.getNumberOfColumns());
			checkNotNull("HeaderLabels", item.getHeaderLabels());

			checkNotNull("Data", item.getData());
			if (testEntireStream) checkStream("Data", item.getData());

			checkNotNull("CompressedData", item.getCompressedData());
			if (testEntireStream) checkStream("CompressedData", item.getCompressedData());
		} catch (InternalErrorException e) {
			logger.error("Error checking timeseries "+item, e);
			throw new CheckException("Error checking timeseries "+item, e);
		}
	}

	protected void checkQuery(Query item) throws CheckException {
		//FIXME checkNotNull("QueryType", item.getQueryType());
		checkNotNull("Query", item.getQuery());
	}

	protected void checkReport(Report item) throws CheckException {
		try{
			checkNotNull("Data", item.getData());
			if (testEntireStream) checkStream("Data", item.getData());
		} catch (InternalErrorException e) {
			logger.error("Error checking report "+item, e);
			throw new CheckException("Error checking report "+item, e);
		}
	}

	protected void checkReportTemplate(ReportTemplate item)	throws CheckException {
		try{
			checkNotNull("Data", item.getData());
			if (testEntireStream) checkStream("Data", item.getData());
		} catch (InternalErrorException e) {
			logger.error("Error checking report template "+item, e);
			throw new CheckException("Error checking report template "+item, e);
		}
	}

	protected void checkAquaMapsItem(AquaMapsItem item)	throws CheckException {
		try{
			checkFile(item.getMetadata());

			checkNotNull("Images", item.getImages());
			for (Image image : item.getImages()) checkImage(image);
		} catch (InternalErrorException e) {
			logger.error("Error checking aquamaps item "+item, e);
			throw new CheckException("Error checking aquamaps "+item, e);
		}
	}

	/**
	 * @param args not used
	 * @throws MalformedScopeExpressionException if an error occurs.
	 * @throws InternalErrorException if an error occurs.
	 * @throws HomeNotFoundException if an error occurs.
	 * @throws WorkspaceFolderNotFoundException if an error occurs.
	 */
	public static void main(String[] args) throws InternalErrorException, HomeNotFoundException, WorkspaceFolderNotFoundException
	{
		Workspace wa = ExamplesUtil.createWorkspace();
		TestDataFactory.getInstance().fillAllFolderItem(wa.getRoot());
		System.out.println("WA ready");
		WorkspaceConsistencyChecker checker = new WorkspaceConsistencyChecker(wa, true, false);
		boolean check = checker.checkWorkspace();
		System.out.println("is ok? "+check);
	}
}
