/**
 * 
 */
package org.gcube.common.homelibrary.testdata;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.WorkflowReport;
import org.gcube.common.homelibrary.home.workspace.folder.items.WorkflowTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.UrlDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.common.homelibrary.testdata.data.TestData;
import org.gcube.common.homelibrary.testdata.manager.AquaMapItemDataManager;
import org.gcube.common.homelibrary.testdata.manager.DocumentDataManager;
import org.gcube.common.homelibrary.testdata.manager.ImageDataManager;
import org.gcube.common.homelibrary.testdata.manager.MetadataDataManager;
import org.gcube.common.homelibrary.testdata.manager.PDFDataManager;
import org.gcube.common.homelibrary.testdata.manager.QueryDataManager;
import org.gcube.common.homelibrary.testdata.manager.ReportDataManager;
import org.gcube.common.homelibrary.testdata.manager.TemplateDataManager;
import org.gcube.common.homelibrary.testdata.manager.TimeSeriesDataManager;
import org.gcube.common.homelibrary.testdata.manager.UrlDataManager;
import org.gcube.common.homelibrary.testdata.manager.WorkflowReportDataManager;
import org.gcube.common.homelibrary.testdata.manager.WorkflowTemplateDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Manage all data necessary for tests and demo.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestDataFactory {

	public static final String resourceRoot = "/org/gcube/portlets/user/homelibrary/testdata/resources/";

	protected Logger logger = LoggerFactory.getLogger(TestDataFactory.class);

	protected static TestDataFactory instance;

	/**
	 * @return the test data factory.
	 */
	public static TestDataFactory getInstance()
	{
		if (instance == null) instance = new TestDataFactory();
		return instance;
	}

	
	protected Random random;

	protected ImageDataManager imageDataManager;
	protected PDFDataManager pdfDataManager;
	protected UrlDataManager urlDataManager;
	protected DocumentDataManager genericDataManager;
	protected MetadataDataManager metadataDataManager;
	protected TimeSeriesDataManager timeSeriesDataManager;
	protected QueryDataManager queryDataManager;
	protected ReportDataManager reportDataManager;
	protected TemplateDataManager templateDataManager;
	protected AquaMapItemDataManager aquaMapItemDataManager;
	protected WorkflowReportDataManager workflowReportDataManager;
	protected WorkflowTemplateDataManager workflowTemplateDataManager;

	protected TestDataFactory(){

		random = new Random();
		imageDataManager = new ImageDataManager();
		pdfDataManager = new PDFDataManager();
		urlDataManager = new UrlDataManager();
		genericDataManager = new DocumentDataManager();
		metadataDataManager = new MetadataDataManager();
		timeSeriesDataManager = new TimeSeriesDataManager();
		queryDataManager = new QueryDataManager();
		reportDataManager = new ReportDataManager();
		templateDataManager = new TemplateDataManager();
		aquaMapItemDataManager = new AquaMapItemDataManager();
		workflowReportDataManager = new WorkflowReportDataManager();
		workflowTemplateDataManager = new WorkflowTemplateDataManager();
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated items
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws IOException 
	 */
	public List<FolderItem> fillAllFolderItem(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		List<FolderItem> items = new LinkedList<FolderItem>();
		items.addAll(fillAllExternalImages(destinationFolder));
		items.addAll(fillAllExternalFiles(destinationFolder));
		items.addAll(fillAllExternalPDFFiles(destinationFolder));
		items.addAll(fillAllExternalUrls(destinationFolder));
		items.addAll(fillAllQueries(destinationFolder));
		items.addAll(fillAllReportTemplates(destinationFolder));
		items.addAll(fillAllReports(destinationFolder));
		items.addAll(fillAllTimeSeries(destinationFolder));
//		items.addAll(fillAllAquaMapsItems(destinationFolder));
		items.addAll(fillAllDocuments(destinationFolder));
		items.addAll(fillAllImageDocuments(destinationFolder));
		items.addAll(fillAllPDFDocuments(destinationFolder));
		items.addAll(fillAllUrlDocuments(destinationFolder));
		items.addAll(fillAllMetadata(destinationFolder));
		items.addAll(fillAllWorkflowReports(destinationFolder));
		items.addAll(fillAllWorkflowTemplates(destinationFolder));

		//ANNOTATION;
		//GPOD,
		return items;
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfItems the number of items to generate.
	 * @return the generated items.
	 * @throws InternalErrorException if an internal error occurs.
	 * @throws IOException 
	 */
	public List<FolderItem> fillWorkspaceFolderItem(WorkspaceFolder destinationFolder, int numberOfItems) throws InternalErrorException
	{
		List<FolderItem> items = new LinkedList<FolderItem>();
		items.addAll(fillExternalImages(destinationFolder, numberOfItems));
		items.addAll(fillExternalFiles(destinationFolder, numberOfItems));
		items.addAll(fillExternalPDFFiles(destinationFolder, numberOfItems));
		items.addAll(fillExternalUrls(destinationFolder, numberOfItems));
		items.addAll(fillQueries(destinationFolder, numberOfItems));
		items.addAll(fillReportTemplates(destinationFolder, numberOfItems));
		items.addAll(fillReports(destinationFolder, numberOfItems));
		items.addAll(fillTimeSeries(destinationFolder, numberOfItems));
//		items.addAll(fillAquaMapsItems(destinationFolder,1));
		items.addAll(fillDocuments(destinationFolder, numberOfItems));
		items.addAll(fillImageDocuments(destinationFolder, numberOfItems));
		items.addAll(fillPDFDocuments(destinationFolder, numberOfItems));
		items.addAll(fillUrlDocuments(destinationFolder, numberOfItems));
		items.addAll(fillMetadata(destinationFolder, numberOfItems));
		items.addAll(fillWorkflowReports(destinationFolder, numberOfItems));
		items.addAll(fillWorkflowTemplates(destinationFolder, numberOfItems));
		
		//ANNOTATION;
		//GPOD,
		return items;
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated ImageDocuments.
	 * @throws InternalErrorException if an error occurs.
	 * @see org.gcube.common.homelibrary.testdata.manager.ImageDataManager#fillAllImageDocuments(org.gcube.portlets.user.homelibrary.home.workspace.folder.WorkspaceFolder)
	 */
	public List<ImageDocument> fillAllImageDocuments(WorkspaceFolder destinationFolder) throws InternalErrorException {
		return imageDataManager.fillAllDocuments(destinationFolder);
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfImages the number of images to generate.
	 * @return the generated ExternalImage.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 * @see org.gcube.common.homelibrary.testdata.manager.ImageDataManager#fillExternalImages(org.gcube.portlets.user.homelibrary.home.workspace.folder.WorkspaceFolder, int)
	 */
	public List<ExternalImage> fillExternalImages(WorkspaceFolder destinationFolder, int numberOfImages) throws InternalErrorException{
		return imageDataManager.fillExternals(destinationFolder,
				numberOfImages);
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated ExternalImage.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 * @see org.gcube.common.homelibrary.testdata.manager.ImageDataManager#fillAllExternalImages(org.gcube.portlets.user.homelibrary.home.workspace.folder.WorkspaceFolder)
	 */
	public List<ExternalImage> fillAllExternalImages(WorkspaceFolder destinationFolder)	throws InternalErrorException{
		return imageDataManager.fillAllExternals(destinationFolder);
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfImages the number of images to generate.
	 * @return the generated ImageDocuments.
	 * @throws InternalErrorException if an error occurs.
	 * @see org.gcube.common.homelibrary.testdata.manager.ImageDataManager#fillImageDocuments(org.gcube.portlets.user.homelibrary.home.workspace.folder.WorkspaceFolder, int)
	 */
	public List<ImageDocument> fillImageDocuments(WorkspaceFolder destinationFolder, int numberOfImages) throws InternalErrorException {
		return imageDataManager.fillDocuments(destinationFolder,
				numberOfImages);
	}

	/**
	 * @return the random input stream.
	 * @see org.gcube.common.homelibrary.testdata.manager.ImageDataManager#getRandomImageInputStream()
	 */
	public InputStream getRandomImageInputStream() {
		return imageDataManager.getRandomInputStream();
	}

	/**
	 * @return the random image file.
	 * @throws InternalErrorException if an error occurs.
	 * @see org.gcube.common.homelibrary.testdata.manager.ImageDataManager#getTMPRandomImageFile()
	 */
	public File getTMPRandomImageFile() throws InternalErrorException {
		return imageDataManager.getTMPRandomFile();
	}

	/**
	 * Create the requested external pdf files into the destination folder.
	 * @param destinationFolder the destination folder.
	 * @param numberOfPDFs the number of pdf files to create.
	 * @return the created external pdf files.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 */
	public List<ExternalPDFFile> fillExternalPDFFiles(WorkspaceFolder destinationFolder, int numberOfPDFs) throws InternalErrorException
	{
		return pdfDataManager.fillExternals(destinationFolder, numberOfPDFs);
	}

	/**
	 * Create the requested external pdf files into the destination folder.
	 * @param destinationFolder the destination folder.
	 * @return the created external pdf files.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 */
	public List<ExternalPDFFile> fillAllExternalPDFFiles(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return pdfDataManager.fillAllExternals(destinationFolder);
	}

	/**
	 * Create a set of pdf documents.
	 * @param destinationFolder the destination folder.
	 * @param numberOfPDFs the number of document pdf to create.
	 * @return the list of create document pdf.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<PDFDocument> fillPDFDocuments(WorkspaceFolder destinationFolder, int numberOfPDFs) throws InternalErrorException
	{
		return pdfDataManager.fillDocuments(destinationFolder, numberOfPDFs);
	}

	/**
	 * Create a set of pdf documents.
	 * @param destinationFolder the destination folder.
	 * @return the list of create document pdf.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<PDFDocument> fillAllPDFDocuments(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return pdfDataManager.fillAllDocuments(destinationFolder);
	}

	protected TestData getRandomTestData()
	{
		int type = random.nextInt(2);
		switch(type){
			case 0: return imageDataManager.getRandomDocumentData();
			case 1: return pdfDataManager.getRandomDocumentData();
			default: return imageDataManager.getRandomDocumentData();
		}
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfExternalFiles number of files to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 */
	public List<ExternalFile> fillExternalFiles(WorkspaceFolder destinationFolder, int numberOfExternalFiles) throws InternalErrorException
	{
		return genericDataManager.fillExternals(destinationFolder, numberOfExternalFiles);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 */
	public List<ExternalFile> fillAllExternalFiles(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return genericDataManager.fillAllExternals(destinationFolder);
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfDocuments number of documents to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Document> fillDocuments(WorkspaceFolder destinationFolder, int numberOfDocuments) throws InternalErrorException
	{
		return genericDataManager.fillDocuments(destinationFolder, numberOfDocuments);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Document> fillAllDocuments(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return genericDataManager.fillAllDocuments(destinationFolder);
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 */
	public List<ExternalUrl> fillAllExternalUrls(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return urlDataManager.fillAllExternals(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfExternalUrls number of urls to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 * @throws IOException 
	 */
	public List<ExternalUrl> fillExternalUrls(WorkspaceFolder destinationFolder, int numberOfExternalUrls) throws InternalErrorException
	{
		return urlDataManager.fillExternals(destinationFolder, numberOfExternalUrls);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfUrlDocuments number of urls to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<UrlDocument> fillUrlDocuments(WorkspaceFolder destinationFolder, int numberOfUrlDocuments) throws InternalErrorException
	{
		return urlDataManager.fillDocuments(destinationFolder, numberOfUrlDocuments);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<UrlDocument> fillAllUrlDocuments(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return urlDataManager.fillAllDocuments(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfMetadata number of metadata to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Metadata> fillMetadata(WorkspaceFolder destinationFolder, int numberOfMetadata) throws InternalErrorException
	{
		return metadataDataManager.fillMetadatas(destinationFolder, numberOfMetadata);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Metadata> fillAllMetadata(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return metadataDataManager.fillAllMetadatas(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfTimeSeries number of TS to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<TimeSeries> fillTimeSeries(WorkspaceFolder destinationFolder, int numberOfTimeSeries) throws InternalErrorException
	{
		return timeSeriesDataManager.fillDatas(destinationFolder, numberOfTimeSeries);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<TimeSeries> fillAllTimeSeries(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return timeSeriesDataManager.fillAllDatas(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfQueries number of queries to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Query> fillQueries(WorkspaceFolder destinationFolder, int numberOfQueries) throws InternalErrorException
	{
		return queryDataManager.fillDatas(destinationFolder, numberOfQueries);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Query> fillAllQueries(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return queryDataManager.fillAllDatas(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfReports number of reports to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Report> fillReports(WorkspaceFolder destinationFolder, int numberOfReports) throws InternalErrorException
	{
		return reportDataManager.fillDatas(destinationFolder, numberOfReports);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<Report> fillAllReports(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return reportDataManager.fillAllDatas(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfTemplates number of templates to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<ReportTemplate> fillReportTemplates(WorkspaceFolder destinationFolder, int numberOfTemplates) throws InternalErrorException
	{
		return templateDataManager.fillDatas(destinationFolder, numberOfTemplates);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<ReportTemplate> fillAllReportTemplates(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return templateDataManager.fillAllDatas(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfAquaMapsItems number of items to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<WorkspaceFolder> fillAquaMapsItems(WorkspaceFolder destinationFolder, int numberOfAquaMapsItems) throws InternalErrorException
	{
		return aquaMapItemDataManager.fillDatas(destinationFolder, numberOfAquaMapsItems);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<WorkspaceFolder> fillAllAquaMapsItems(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return aquaMapItemDataManager.fillAllDatas(destinationFolder);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfWorkflowReports number of workflow reports to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<WorkflowReport> fillWorkflowReports(WorkspaceFolder destinationFolder, int numberOfWorkflowReports) throws InternalErrorException
	{
		return workflowReportDataManager.fillDatas(destinationFolder, numberOfWorkflowReports);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<WorkflowReport> fillAllWorkflowReports(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return workflowReportDataManager.fillAllDatas(destinationFolder);
	}
	
	
	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfWorkflowTemplates number of workflow templates to generate.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<WorkflowTemplate> fillWorkflowTemplates(WorkspaceFolder destinationFolder, int numberOfWorkflowTemplates) throws InternalErrorException
	{
		return workflowTemplateDataManager.fillDatas(destinationFolder, numberOfWorkflowTemplates);
	}
	
	/**
	 * @param destinationFolder the destination folder.
	 * @return the generated data.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<WorkflowTemplate> fillAllWorkflowTemplates(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		return workflowTemplateDataManager.fillAllDatas(destinationFolder);
	}

	/**
	 * Retrieve an input stream from a random pdf.
	 * @return the random input stream.
	 */
	public InputStream getRandomPDFInputStream()
	{
		return pdfDataManager.getRandomInputStream();
	}

	/**
	 * Return a random temporary pdf file. 
	 * @return the random file.
	 * @throws InternalErrorException if an error occurs.
	 */
	public File getTMPRandomPDFFile() throws InternalErrorException
	{
		return pdfDataManager.getTMPRandomFile();
	}

	/**
	 * Return a random temporary file. 
	 * @return the random file.
	 * @throws InternalErrorException if an error occurs.
	 */
	public File getTMPRandomFile() throws InternalErrorException
	{
		int type = random.nextInt(2);
		switch(type){
			case 0: return getTMPRandomImageFile();
			case 1: return getTMPRandomPDFFile();
			default: return getTMPRandomImageFile();
		}
	}


	/**
	 * @param args not used.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static void main(String[] args) throws InternalErrorException
	{

		/*TestDataManager testDataManager = new TestDataManager();
		System.out.println("Images:");
		List<ImageDocumentData> images = testDataManager.loadImages();
		for (TestData image:images) {
			System.out.println(image);
			InputStream is = testDataManager.getTestDataStream(image);
			if (is==null){
				System.err.println("The stream is null for: "+image);
				System.exit(1);
			}
			testDataManager.loadMetadatas(image.getMetadatas());
		}

		System.out.println("PDFs:");
		List<PDFDocumentData> pdfs = testDataManager.loadPDFs();
		for (PDFDocumentData pdf:pdfs) {
			System.out.println(pdf);
			InputStream is = testDataManager.getTestDataStream(pdf);
			if (is==null){
				System.err.println("The stream is null for "+pdf);
				System.exit(1);
			}
			testDataManager.loadMetadatas(pdf.getMetadatas());
		}
		
		System.out.println("URLs:");
		List<UrlDocumentData> urls = testDataManager.loadUrls();
		for (UrlDocumentData url:urls) {
			System.out.println(url);
		}*/
	}


}
