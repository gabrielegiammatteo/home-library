/**
 * 
 */
package org.gcube.common.homelibrary.util;

import java.io.PrintStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.AquaMapsItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.common.homelibrary.home.workspace.folder.items.Image;
import org.gcube.common.homelibrary.home.workspace.folder.items.PDF;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.WorkflowReport;
import org.gcube.common.homelibrary.home.workspace.folder.items.WorkflowTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.InfoObject;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.UrlDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentAlternativeLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentPartLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;

/**
 * An utility to visit a workspace tree.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceTreeVisitor extends IndentedVisitor {
	
	/**
	 * 
	 */
	public WorkspaceTreeVisitor()
	{
		super();
	}

	/**
	 * @param indentationLevel the indentation level.
	 * @param indentationChar the indentation char.
	 * @param os the output stream.
	 * @param logger the visitor logger.
	 */
	public WorkspaceTreeVisitor(String indentationLevel, String indentationChar, PrintStream os, Logger logger) {
		super(indentationLevel, indentationChar, os, logger);
	}

	/**
	 * Visit the tree in verbose mode.
	 * @param folder the tree root.
	 * @throws InternalErrorException if an error occurs.
	 */
	public void visitVerbose(WorkspaceFolder folder) throws InternalErrorException
	{
		reset();
		visitWorkspaceItem(folder);
	}

	/**
	 * Visit a workspace item.
	 * @param item the item to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitWorkspaceItem(WorkspaceItem item) throws InternalErrorException
	{
		println();
		switch (item.getType()) {
			case FOLDER: visitWorkspaceFolder((WorkspaceFolder) item); break;
			case FOLDER_ITEM:visitFolderItem((FolderItem) item); break;
		}
	}

	/**
	 * Visits a workspace folder.
	 * @param folder the workspace folder to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitWorkspaceFolder(WorkspaceFolder folder) throws InternalErrorException
	{
		println("/FOLDER/");
		printWorkspaceItem(folder);
		indent();
		for (WorkspaceItem child:folder.getChildren()){
			visitWorkspaceItem(child);
		}
		outdent();
	}

	/**
	 * Visits a folder item.
	 * @param item the item to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	public void visitFolderItem(FolderItem item) throws InternalErrorException
	{
		println();
		println("/ITEM/");
		
		switch (item.getFolderItemType()) {
			case EXTERNAL_IMAGE: visitExternalImage((ExternalImage) item); break;
			case EXTERNAL_FILE: visitExternalFile((ExternalFile)item); break;
			case EXTERNAL_PDF_FILE: visitExternalPDFFile((ExternalPDFFile)item); break;
			case EXTERNAL_URL: visitExternalURL((ExternalUrl)item); break;
			case QUERY: visitQuery((Query)item); break;
			case REPORT: visitReport((Report)item); break;
			case REPORT_TEMPLATE: visitReportTemplate((ReportTemplate)item); break;
			case TIME_SERIES: visitTimeSeries((TimeSeries)item); break;
			case AQUAMAPS_ITEM: visitAquaMapsItem((AquaMapsItem)item); break;
			case DOCUMENT: visitDocument((Document)item); break;
			case PDF_DOCUMENT: visitPDFDocument((PDFDocument)item); break;
			case IMAGE_DOCUMENT: visitImageDocument((ImageDocument)item); break;
			case METADATA: visitMetadata((Metadata)item); break;
			case URL_DOCUMENT: visitUrlDocument((UrlDocument)item);break;
			case WORKFLOW_REPORT: visitWorkflowReport((WorkflowReport) item); break;
			case WORKFLOW_TEMPLATE: visitWorkflowTemplate((WorkflowTemplate) item); break;
			//TODO add more types
			default: {
				printWorkspaceItem(item);
				println("UNSUPPORTED TYPE: "+item.getFolderItemType()); break;
			}
		}
		
	}
	
	protected void visitImage(Image img) throws InternalErrorException
	{
		println("MimeType "+img.getMimeType());
		println("Width "+img.getWidth());
		println("Height "+img.getHeight());
		println("Length "+img.getLength());
	}
	
	protected void visitPDF(PDF pdf) throws InternalErrorException
	{
		println("Number Of Pages "+pdf.getNumberOfPages());
		println("Version "+pdf.getVersion());
		println("Author "+pdf.getAuthor());
		println("Title "+pdf.getTitle());
		println("Producer "+pdf.getProducer());
	}

	/**
	 * Visits an image.
	 * @param img the image to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitExternalImage(ExternalImage img) throws InternalErrorException
	{
		println("[ExternalImage]");
		printFolderItem(img);
		visitImage(img);
	}
	
	protected void visitExternalFile(ExternalFile file) throws InternalErrorException
	{
		println("[ExternalFile]");
		printFolderItem(file);
		println("MimeType "+file.getMimeType());
		println("Length "+file.getLength());
	}
	
	protected void visitAquaMapsItem(AquaMapsItem aquamapsitem) throws InternalErrorException
	{
		println("[AquaMapsItem]");
		printFolderItem(aquamapsitem);
		println("Author "+aquamapsitem.getAuthor());
		println("MapName "+aquamapsitem.getMapName());
		println("NumberOfSpecies "+aquamapsitem.getNumberOfSpecies());
		println("BoundingBox "+aquamapsitem.getBoundingBox());
		println("PsoThreshold "+aquamapsitem.getPsoThreshold());
		println("NumberOfGeneratedImages "+aquamapsitem.getNumberOfGeneratedImages());	
		println("images:");
		indent();
		for (Image image:aquamapsitem.getImages()) visitImageH(image);
		outdent();
		println("Length "+aquamapsitem.getLength());
	}
	
	protected void visitImageH(Image img) throws InternalErrorException
	{
		println("MimeType: "+img.getMimeType()+", Width: "+img.getWidth()+", Height: "+img.getHeight()+", Length: "+img.getLength());
	}
	
	protected void visitTimeSeries(TimeSeries timeseries) throws InternalErrorException
	{
		println("[TimeSeries]");
		printFolderItem(timeseries);
		println("id "+timeseries.getTimeSeriesInfo().getId());
		println("creationDate "+timeseries.getTimeSeriesInfo().getCreationDate());
		println("creator "+timeseries.getTimeSeriesInfo().getCreator());
		println("description "+timeseries.getTimeSeriesInfo().getDescription());
		println("dimension "+timeseries.getTimeSeriesInfo().getDimension());
		println("publisher "+timeseries.getTimeSeriesInfo().getPublisher());
		println("rights "+timeseries.getTimeSeriesInfo().getRights());
		println("sourceId "+timeseries.getTimeSeriesInfo().getSourceId());
		println("sourceName "+timeseries.getTimeSeriesInfo().getSourceName());
		println("title "+timeseries.getTimeSeriesInfo().getTitle());
		println("Length "+timeseries.getLength());
		println("NumberOfColumns "+timeseries.getNumberOfColumns());
		println("HeadersLabel "+timeseries.getHeaderLabels());
	}
	
	protected void visitExternalPDFFile(ExternalPDFFile pdf) throws InternalErrorException
	{
		println("[ExternalPDFFile]");
		printFolderItem(pdf);
		println("MimeType "+pdf.getMimeType());
		visitPDF(pdf);
		println("Length "+pdf.getLength());
	}
	
	protected void visitExternalURL(ExternalUrl url) throws InternalErrorException
	{
		println("[ExternalUrl]");
		printFolderItem(url);
		println("Url "+url.getUrl());
		println("Length "+url.getLength());
	}
	
	protected void visitQuery(Query query) throws InternalErrorException
	{
		println("[Query]");
		printFolderItem(query);
		println("Query "+query.getQuery());
		println("Query type "+query.getQueryType());
		println("Length "+query.getLength());
	}
	
	protected void visitReport(Report report) throws InternalErrorException
	{
		println("[Report]");
		printFolderItem(report);
		println("Author "+report.getAuthor());
		println("LastEditBy "+report.getLastEditBy());
		println("TemplateName "+report.getTemplateName());
		println("NumberOfSections "+report.getNumberOfSections());
		println("Created "+sdf.format(report.getCreated().getTime()));
		println("LastEdit "+sdf.format(report.getLastEdit().getTime()));
		println("Status "+report.getStatus());
		println("Length "+report.getLength());
	}
	
	protected void visitReportTemplate(ReportTemplate reportTemplate) throws InternalErrorException
	{
		println("[ReportTemplate]");
		printFolderItem(reportTemplate);
		println("Author "+reportTemplate.getAuthor());
		println("LastEditBy "+reportTemplate.getLastEditBy());
		println("NumberOfSections "+reportTemplate.getNumberOfSections());
		println("Created "+sdf.format(reportTemplate.getCreated().getTime()));
		println("LastEdit "+sdf.format(reportTemplate.getLastEdit().getTime()));
		println("Status "+reportTemplate.getStatus());
		println("Length "+reportTemplate.getLength());
	}
	
	protected void visitWorkflowReport(WorkflowReport report) throws InternalErrorException
	{
		println("[WorkflowReport]");
		printFolderItem(report);
	}
	
	protected void visitWorkflowTemplate(WorkflowTemplate template) throws InternalErrorException
	{
		println("[WorkflowTemplate]");
		printFolderItem(template);
	}
	
	/**
	 * Visits a info object.
	 * @param infoObject the info to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void printInfoObject(InfoObject infoObject) throws InternalErrorException
	{
		println("OID: "+infoObject.getURI());
	}
	
	/**
	 * Visits a document.
	 * @param document the document to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitDocument(Document document) throws InternalErrorException
	{
		println("{Document}");
		printInfoObject(document);
		println("N. Metadata "+document.getMetadata().size());
		println("N. Annotations "+document.getAnnotation().size());
		println("MimeType "+document.getMimeType());
		visitAlternativeLinks(document.getAlternatives());
		visitPartLinks(document.getParts());
	}
	
	/**
	 * Visits a PDF document.
	 * @param document the document to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitPDFDocument(PDFDocument document) throws InternalErrorException
	{
		println("{PDFDocument}");
		printInfoObject(document);
		println("N. Metadata "+document.getMetadata().size());
		println("N. Annotations "+document.getAnnotation().size());
		println("MimeType "+document.getMimeType());
		visitPDF(document);
		visitAlternativeLinks(document.getAlternatives());
		visitPartLinks(document.getParts());
	}
	
	protected void visitImageDocument(ImageDocument document) throws InternalErrorException
	{
		println("{ImageDocument}");
		printInfoObject(document);
		println("N. Metadata "+document.getMetadata().size());
		println("N. Annotations "+document.getAnnotation().size());
		visitImage(document);
		visitAlternativeLinks(document.getAlternatives());
		visitPartLinks(document.getParts());
	}
	
	protected void visitUrlDocument(UrlDocument document) throws InternalErrorException
	{
		println("{UrlDocument}");
		printInfoObject(document);
		println("Url: "+document.getUrl());
		println("MimeType "+document.getMimeType());
		visitAlternativeLinks(document.getAlternatives());
		visitPartLinks(document.getParts());
	}
	
	protected void visitAlternativeLinks(List<DocumentAlternativeLink> alternatives) throws InternalErrorException
	{
		println("{Alternatives}");
		for (DocumentAlternativeLink alternative: alternatives) visitAlternativeLink(alternative);
	}
	
	protected void visitAlternativeLink(DocumentAlternativeLink alternative) throws InternalErrorException
	{
		println("OID: "+alternative.getURI()+" name: "+alternative.getName()+" mimeType: "+alternative.getMimeType());
	}
	
	protected void visitPartLinks(List<DocumentPartLink> parts) throws InternalErrorException
	{
		println("{Parts}");
		for (DocumentPartLink part: parts) visitPartLink(part);
	}
	
	protected void visitPartLink(DocumentPartLink part) throws InternalErrorException
	{
		println("OID: "+part.getURI()+" name: "+part.getName()+" mimeType: "+part.getMimeType());
	}
	
	protected void visitMetadata(Metadata metadata) throws InternalErrorException
	{
		println("{Metadata}");
		printInfoObject(metadata);
		println("Schema "+metadata.getSchema());
	}

	/**
	 * Visits a Workspace item.
	 * @param item the item to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void printWorkspaceItem(WorkspaceItem item) throws InternalErrorException
	{
		println("ID: "+item.getId());
		println("NAME: "+item.getName());
		println("DESCRIPTION: "+item.getDescription());
		println("CREATION TIME: "+sdf.format(item.getCreationTime().getTime()));
		println("OWNER: "+item.getOwner());
		//TODO print metadata
	}
	
	protected void printFolderItem(FolderItem item) throws InternalErrorException
	{
		printWorkspaceItem(item);
		println("WorkflowId: "+item.getWorkflowId());
		println("WorkflowStatus: "+item.getWorkflowStatus());
		println("WorkflowData (length): "+((item.getWorkflowData()==null)?"null":item.getWorkflowData().length()));
	}
	
	

	/**
	 * Visits an user.
	 * @param user the user to visit.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitUser(User user) throws InternalErrorException
	{
		println("ID: "+user.getId());
		println("LOGIN: "+user.getPortalLogin());
	}
	
	/**
	 * Visit an item tree without verbose information.
	 * @param item the tree root.
	 * @throws InternalErrorException if an error occurs.
	 */
	public void visitSimple(WorkspaceItem item) throws InternalErrorException
	{
		reset();
		visitItem(item);
	}
	
	/**
	 * Visit an item tree without verbose information.
	 * @param item the item to visit
	 * @throws InternalErrorException if an error occurs.
	 */
	protected void visitItem(WorkspaceItem item) throws InternalErrorException
	{
		switch (item.getType()) {
			case FOLDER: println("/["+item.getName()+"]"); break;
			case FOLDER_ITEM: println("/"+item.getName()); break;
		}
		
		indent();
		
		for (WorkspaceItem child:item.getChildren()) visitItem(child);
		
		outdent();
	}

}
