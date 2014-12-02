/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.accessmanager.ACLType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongItemTypeException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderBulkCreator;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalResourceLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.QueryType;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.TabularDataLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.TabularDataLink.Provenance;
import org.gcube.common.homelibrary.home.workspace.folder.items.WorkflowReport;
import org.gcube.common.homelibrary.home.workspace.folder.items.WorkflowTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Annotation;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.UrlDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.ImageDocumentLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.PDFDocumentLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface WorkspaceFolder extends WorkspaceItem {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<WorkspaceItem> getChildren() throws InternalErrorException;

	/**
	 * Check if an item with the specified name exists.
	 * @param name the name to check.
	 * @return <code>true</code> if the item exists, <code>false</code> otherwise.
	 * @throws InternalErrorException if an error occurs.
	 */
	public boolean exists(String name) throws InternalErrorException;

	/**
	 * Get an item with the specified name.
	 * @param name the item name to find.
	 * @return the item if is found, <code>null</code> otherwise.
	 * @throws InternalErrorException if an error occurs.
	 */
	public WorkspaceItem find(String name) throws InternalErrorException;

	/**
	 * Create a new folder into this folder.
	 * @param name the folder name.
	 * @param description the folder description.
	 * @return the new folder.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists in this folder.
	 */
	public WorkspaceFolder createFolder(String name, String description) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException;

	/**
	 * Create a new External Image into this folder.
	 * @param name the external image name.
	 * @param description the external image description.
	 * @param mimeType the external image mime type.
	 * @param imageData the external image data.
	 * @return the new external image.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ExternalImage createExternalImageItem(String name, String description, String mimeType, InputStream imageData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;


	/**
	 * Create a new External Image into this folder.
	 * @param name the external image name.
	 * @param description the external image description.
	 * @param mimeType the external image mime type.
	 * @param imageData the external image data.
	 * @return the new external image.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ExternalImage createExternalImageItem(String name, String description, String mimeType, File tmpFile) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;


	/**
	 * Create an External File into this folder.
	 * @param name the external file name.
	 * @param description the external file description.
	 * @param mimeType the external file mime type.
	 * @param fileData the external file data.
	 * @return the new external file.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ExternalFile createExternalFileItem(String name, String description, String mimeType, InputStream fileData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	
	/**
	 * Create an External File into this folder.
	 * @param name the external file name.
	 * @param description the external file description.
	 * @param mimeType the external file mime type.
	 * @param fileData the external file data.
	 * @return the new external file.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ExternalFile createExternalFileItem(String name, String description, String mimeType, File tmpFile) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new External PDF file into this folder.
	 * @param name the external PDF name.
	 * @param description the external PDF description.
	 * @param mimeType the external PDF mime type.
	 * @param fileData the external PDF data.
	 * @return the new external PDF.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ExternalPDFFile createExternalPDFFileItem(String name, String description, String mimeType, InputStream fileData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new External PDF file into this folder.
	 * @param name the external PDF name.
	 * @param description the external PDF description.
	 * @param mimeType the external PDF mime type.
	 * @param tmpFile the PDF tmpFile
	 * @return the new external PDF.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ExternalPDFFile createExternalPDFFileItem(String name, String description, String mimeType, File tmpFile) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	
	
	/**
	 * Create an External URL into this folder.
	 * @param name the external URL name.
	 * @param description the external URL description.
	 * @param url the URL.
	 * @return the new URL file.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 * @throws IOException 
	 */
	public ExternalUrl createExternalUrlItem(String name, String description, String url) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create an External URL into this folder.
	 * @param name the external URL name.
	 * @param description the external URL description.
	 * @param url the URL.
	 * @return the new URL file.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 * @throws IOException 
	 */
	public ExternalUrl createExternalUrlItem(String name, String description, InputStream url) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	
	/**
	 * Create an External URL into this folder.
	 * @param name the external URL name.
	 * @param description the external URL description.
	 * @param url the URL.
	 * @return the new URL file.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 * @throws IOException 
	 */
	public ExternalUrl createExternalUrlItem(String name, String description, File tmpFile) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	
	/**
	 * Create a Report Template into this folder.
	 * @param name the template name.
	 * @param description the template description.
	 * @param created the template creation time.
	 * @param lastEdit the last edit time.
	 * @param author the template author.
	 * @param lastEditBy the last template editor.
	 * @param numberOfSections the number of sections.
	 * @param status the template status.
	 * @param templateData the template data.
	 * @return the template.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ReportTemplate createReportTemplateItem(String name, String description, Calendar created, Calendar lastEdit, String author, String lastEditBy, int numberOfSections, String status, InputStream templateData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a Report into this folder.
	 * @param name the report name.
	 * @param description the report description.
	 * @param created the report creation time.
	 * @param lastEdit the last edit time.
	 * @param author the report author.
	 * @param lastEditBy the last report editor.
	 * @param templateName the source template name.
	 * @param numberOfSections the number of sections.
	 * @param status the report status.
	 * @param reportData the report data.
	 * @return the report.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public Report createReportItem(String name, String description, Calendar created, Calendar lastEdit, String author, String lastEditBy, String templateName, int numberOfSections, 
			String status, InputStream reportData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new Query into this folder.
	 * @param name the query name.
	 * @param description the query description.
	 * @param query the query.
	 * @param queryType the query type.
	 * @return the new query.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public Query createQueryItem(String name, String description, String query, QueryType queryType) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new Query into this folder.
	 * @param name the query name.
	 * @param description the query description.
	 * @param query the query.
	 * @param queryType the query type.
	 * @return the new query.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public Query createQueryItem(String name, String description, InputStream query, QueryType queryType) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new AquaMaps Item into this folder.
	 * @param name the item name.
	 * @param description the item description.
	 * @param mapName the map name.
	 * @param mapType the map type.
	 * @param author the item author.
	 * @param numberOfSpecies the number of species selected.
	 * @param boundingBox the bounding box.
	 * @param psoThreshold the PSO threshold.
	 * @param numberOfGeneratedImages the number of generated images.
	 * @param metadata the item metadata.
	 * @param images the item images.
	 * @return the new AquaMaps item.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws WorkspaceFolderNotFoundException if the destination folder has not been found.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if a folder item with same name already exist.
	 * @throws WrongDestinationException if the destination type is not a folder.
	 */
	public WorkspaceFolder createAquaMapsItem(String name, String description, String mapName, String mapType, String author, int numberOfSpecies, String boundingBox, float psoThreshold,
			int numberOfGeneratedImages, InputStream metadata, Map<String,InputStream> images) throws InsufficientPrivilegesException, WorkspaceFolderNotFoundException, InternalErrorException, ItemAlreadyExistException, WrongDestinationException;



	/**
	 * Create a new annotation into this folder.
	 * @param name the annotation name.
	 * @param description the annotation description.
	 * @param oid the oid of the object where the annotation come from.
	 * @param data the annotation data.
	 * @return the new annotation.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public Annotation createAnnotationItem(String name, String description, String oid, Map<String, String> data) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new Metadata into this folder.
	 * @param name the metadata name.
	 * @param description the metadata description.
	 * @param oid the oid of the object where the metadata come from.
	 * @param schema the metadata schema.
	 * @param language the metadata language.
	 * @param metadata the metadata data.
	 * @param collectionName the metadata referred object collection name.
	 * @return the new metadata.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public Metadata createMetadataItem(String name, String description, String oid, String schema, String language, String metadata, String collectionName) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new document into this folder.
	 * @param name the document name.
	 * @param description the document description.
	 * @param oid the document oid.
	 * @param mimeType the document mime type.
	 * @param documentData the document data.
	 * @param metadata the document metadata.
	 * @param annotations the document annotations.
	 * @param collectionName the document collection name.
	 * @return the new document.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public Document createDocumentItem(String name, String description, String oid,  String mimeType, InputStream documentData, Map<String, String> metadata, Map<String, String> annotations, String collectionName) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new image document into this folder.
	 * @param name the document image name.
	 * @param description the document image description.
	 * @param oid the document image oid.
	 * @param mimeType the image document mime type.
	 * @param imageData the document image data.
	 * @param metadata the document image metadata.
	 * @param annotations the document image annotations.
	 * @param collectionName the document image collection name.
	 * @return the new document image.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ImageDocument createImageDocumentItem(String name, String description, String oid, String mimeType, InputStream imageData, Map<String, String> metadata, Map<String, String> annotations, String collectionName) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new PDF document.
	 * @param name the PDF document name.
	 * @param description the document PDF description.
	 * @param oid the document PDF oid.
	 * @param mimeType the document PDF mime type.
	 * @param data the document PDF data.
	 * @param metadata the document PDF metadata.
	 * @param annotations the document PDF annotations.
	 * @param collectionName the document collection name.
	 * @return the new PDF document.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public PDFDocument createPDFDocumentItem(String name, String description, String oid,  String mimeType, InputStream data, Map<String, String> metadata, Map<String, String> annotations, String collectionName) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new URL document into this folder.
	 * @param name the document name.
	 * @param description the document description.
	 * @param oid the document oid.
	 * @param mimeType the document mimeType.
	 * @param documentData the document data.
	 * @param metadata the document metadata.
	 * @param annotations the document annotations.
	 * @param collectionName the document collection name.
	 * @return the new document.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public UrlDocument createUrlDocumentItem(String name, String description, String oid, String mimeType, InputStream documentData, Map<String, String> metadata, Map<String, String> annotations, String collectionName) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;


	/**
	 * Create a new document link into this folder.
	 * @param name the document name.
	 * @param description the document description.
	 * @param oid the document oid.
	 * @param metadata the document metadata.
	 * @param annotations the document annotations.
	 * @param collectionName the document collection name.
	 * @param mimeType the document mimeType.
	 * @return the new document.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public DocumentLink createDocumentLinkItem(String name, String description, String oid, Map<String, String> metadata, Map<String, String> annotations, String collectionName, String mimeType) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new image document link into this folder.
	 * @param name the document image name.
	 * @param description the document image description.
	 * @param oid the document image oid.
	 * @param metadata the document image metadata.
	 * @param annotations the document image annotations.
	 * @param collectionName the document image collection name.
	 * @param mimeType the image document mimeType.
	 * @return the new document image.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public ImageDocumentLink createImageDocumentLinkItem(String name, String description, String oid, Map<String, String> metadata, Map<String, String> annotations, String collectionName, String mimeType) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Create a new PDF document link.
	 * @param name the pdf document name.
	 * @param description the document pdf description.
	 * @param oid the document pdf oid.
	 * @param metadata the document pdf metadata.
	 * @param annotations the document pdf annotations.
	 * @param collectionName the document collection name.
	 * @param mimeType the pdf document mimeType.
	 * @return the new pdf document.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public PDFDocumentLink createPDFDocumentLinkItem(String name, String description, String oid, Map<String, String> metadata, Map<String, String> annotations, String collectionName, String mimeType) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;


	/**
	 * Create a new Time Series.
	 * @param name the item name.
	 * @param description the item description.
	 * @param timeseriesId the Time Series id.
	 * @param title the Time Series title.
	 * @param creator the Time Series creator.
	 * @param timeseriesDescription the Time Series description. 
	 * @param timeseriesCreationDate the Time Series creation date.
	 * @param publisher the Time Series publisher.
	 * @param sourceId the Time Series source id.
	 * @param sourceName the Time Series source name.
	 * @param rights the Time Series rights.
	 * @param dimension the Time Series dimension.
	 * @param headerLabels the Time Series headers label.
	 * @param compressedCSV the Time Series csv compressed representation (with labels and UTF-8 encoded).
	 * @return the created Time Series.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 */
	public TimeSeries createTimeSeries(String name, String description, String timeseriesId, String title, String creator, String timeseriesDescription, String timeseriesCreationDate, 
			String publisher, String sourceId, String sourceName, String rights, long dimension, List<String> headerLabels, InputStream compressedCSV) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Creates a Workflow Report.
	 * @param name the workflow Report name.
	 * @param description the workflow Report description.
	 * @param workflowId the workflow id.
	 * @param workflowStatus the workflow status.
	 * @param workflowData the workflow data.
	 * @return the created Workflow Report.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 * @throws WorkspaceFolderNotFoundException  if the destination folder has not been found.
	 * @throws WrongDestinationException if the destination type is not a folder.
	 */
	public WorkflowReport createWorkflowReport(String name, String description, String workflowId, String workflowStatus, String workflowData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException;

	/**
	 * Creates a Workflow Template.
	 * @param name the workflow Template name.
	 * @param description the workflow Template description.
	 * @param workflowId the workflow id.
	 * @param workflowStatus the workflow status.
	 * @param workflowData the workflow data.
	 * @return the created Workflow Template.
	 * @throws InsufficientPrivilegesException if the user don't have sufficient privileges to perform this operation.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws ItemAlreadyExistException if an item with the specified name already exists.
	 * @throws WorkspaceFolderNotFoundException  if the destination folder has not been found.
	 * @throws WrongDestinationException if the destination type is not a folder.
	 */
	public WorkflowTemplate createWorkflowTemplate(String name, String description, String workflowId, String workflowStatus, String workflowData) throws InsufficientPrivilegesException, InternalErrorException, ItemAlreadyExistException; 


	/**
	 * Create a new FolderBulkCreator for this folder.
	 * @return the new FolderBulkCreator.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public FolderBulkCreator getNewFolderBulkCreator() throws InternalErrorException;

	/**
	 * @param name
	 * @param description
	 * @param mimeType
	 * @param resourceIdString
	 * @param pluginName
	 * @return
	 * @throws WrongItemTypeException
	 * @throws WorkspaceFolderNotFoundException
	 * @throws WrongDestinationException
	 * @throws InternalErrorException
	 * @throws ItemAlreadyExistException
	 * @throws InsufficientPrivilegesException
	 * @throws ItemNotFoundException
	 */
	public ExternalResourceLink createExternalResourceLink(String name, String description,
			String mimeType, String resourceIdString, String pluginName)
					throws InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException;

	/**
	 * @param useers
	 * @return
	 * @throws InsufficientPrivilegesException
	 * @throws WrongDestinationException
	 * @throws InternalErrorException
	 */
	public WorkspaceSharedFolder share(List<String> useers) throws InsufficientPrivilegesException,
	WrongDestinationException, InternalErrorException;

	/**
	 * @param name
	 * @param description
	 * @param tableId
	 * @param template
	 * @param provenance
	 * @param operator
	 * @param runtimeResourceName
	 * @return
	 * @throws InsufficientPrivilegesException
	 * @throws InternalErrorException
	 * @throws ItemAlreadyExistException
	 */
	public TabularDataLink createTabularDataLink(String name, String description,
			String tableId, String template, Provenance provenance,
			String operator, String runtimeResourceName)
					throws InsufficientPrivilegesException, InternalErrorException,
					ItemAlreadyExistException;

	/**
	 * Set a privilege to a list of users
	 * @param users
	 * @param privilege
	 * @throws InternalErrorException
	 */
	public void setACL(List<String> users, ACLType privilege)
			throws InternalErrorException;

	/**
	 * Get an unique name for an item
	 * @param initialName
	 * @return 
	 * @throws InternalErrorException
	 */
	public String getUniqueName(String initialName, boolean b) throws InternalErrorException;

	/**
	 * Get the size of a folder
	 * @return folder size in bytes
	 * @throws InternalErrorException
	 */
	public long getSize() throws InternalErrorException;
	
	/**
	 * Get the number of items in a folder
	 * @return the number of items in a folder
	 * @throws InternalErrorException
	 */
	public int getCount() throws InternalErrorException;

}
