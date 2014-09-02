/**
 * 
 */
package org.gcube.common.homelibrary.util.zip;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.common.homelibrary.home.workspace.folder.items.AquaMapsItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.common.homelibrary.home.workspace.folder.items.File;
import org.gcube.common.homelibrary.home.workspace.folder.items.Image;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.DocumentMetadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeriesInfo;
import org.gcube.common.homelibrary.util.Extensions;
import org.gcube.common.homelibrary.util.FileSystemNameUtil;
import org.gcube.common.homelibrary.util.MimeTypeUtil;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipFile;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipFolder;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipItem;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceToZipModelConverter {

	protected XStream xstream;

	protected XStream getXStream()
	{
		if (xstream!=null) return xstream;
		xstream = new XStream();
		xstream.alias("timeseriesinfo", TimeSeriesInfo.class, TimeSeriesInfo.class);
		return xstream;
	}

	public ZipItem convert(WorkspaceItem workspaceItem) throws InternalErrorException, IOException
	{
		switch (workspaceItem.getType()) {
		case SHARED_FOLDER: 	
		case FOLDER: return convertFolder((WorkspaceFolder) workspaceItem);	
		case FOLDER_ITEM: return convertFolderItem((FolderItem) workspaceItem);	
		}
		return null;
	}

	protected ZipFolder convertFolder(WorkspaceFolder workspaceFolder) throws InternalErrorException, IOException
	{
		String name = FileSystemNameUtil.cleanFileName(workspaceFolder.getName());
		String comment = workspaceFolder.getDescription();

		ZipFolder zipFolder = new ZipFolder(null, name, comment, new byte[0]);

		List<String> childrenNames = new LinkedList<String>();

		for (WorkspaceItem child:workspaceFolder.getChildren()){
			ZipItem childItem = convert(child);
			if (childItem==null) continue;

			//we check if the name is unique
			String childName = getUniqueName(childrenNames, childItem.getName());
			childItem.setName(childName);
			childrenNames.add(childName);

			childItem.setParent(zipFolder);
			zipFolder.addChild(childItem);
		}

		return zipFolder;
	}

	protected ZipItem convertFolderItem(FolderItem folderItem) throws InternalErrorException, IOException
	{
		String cleanedItemName = FileSystemNameUtil.cleanFileName(folderItem.getName());
		String comment = folderItem.getDescription();
		FolderItemType type = folderItem.getFolderItemType();

		switch (type) {

		case EXTERNAL_IMAGE: //an external image is also an external file
		case EXTERNAL_PDF_FILE: //an external pdf file is also an external file
		case EXTERNAL_FILE:{
			ExternalFile externalFile = (ExternalFile)folderItem;
			String mimeType = externalFile.getMimeType();
			String name = MimeTypeUtil.getNameWithExtension(cleanedItemName, mimeType);

			InputStream data = externalFile.getData();
			if (data!=null)
				return new ZipFile(data, name, comment);	
			else
				return null;
		}
		case REPORT_TEMPLATE: {
			ReportTemplate reportTemplate = (ReportTemplate)folderItem;
			String name = cleanedItemName+"."+Extensions.REPORT_TEMPLATE.getValue();
			InputStream data = reportTemplate.getData();
			if (data!=null)
				return new ZipFile(data, name, comment);
		}
		case REPORT: {
			Report report = (Report)folderItem;
			String name = cleanedItemName+"."+Extensions.REPORT.getValue();
			InputStream data = report.getData();
			if (data!=null)
				return new ZipFile(data, name, comment);
		}
		case EXTERNAL_URL: {
			ExternalUrl externalUrl = (ExternalUrl)folderItem;
			String name = cleanedItemName+".xml";
			InputStream is = new ByteArrayInputStream(externalUrl.getUrl().getBytes());
			if (is!=null)
				return new ZipFile(is, name, comment);
		}
		case QUERY: {
			Query query = (Query)folderItem;
			String name = cleanedItemName+"."+Extensions.QUERY.getValue();
			InputStream is = new ByteArrayInputStream(query.getQuery().getBytes());
			//FIXME there are lost informations
			if (is!=null)
				return new ZipFile(is, name, comment);
		}
		case TIME_SERIES: {
			TimeSeries timeSeries = (TimeSeries)folderItem;
			return convertTimeSeries(cleanedItemName, timeSeries);
		}
		case AQUAMAPS_ITEM: {
			AquaMapsItem aquaMapsItem = (AquaMapsItem)folderItem;
			return convertAquaMapsItem(cleanedItemName, aquaMapsItem);
		}

		case IMAGE_DOCUMENT: //an ImageDocument is also a document
		case PDF_DOCUMENT: //a PDFDocument is also a document
		case URL_DOCUMENT: //an URLDocument is also a document
		case DOCUMENT: {
			Document document = (Document)folderItem;
			return convertDocument(cleanedItemName, document);	
		}
		default:{
			return null;
		}
		}
	}

	protected ZipItem convertTimeSeries(String cleanedItemName, TimeSeries timeSeries) throws InternalErrorException
	{
		String comment = timeSeries.getDescription();
		ZipFolder timeseriesFolder = new ZipFolder(cleanedItemName, comment);

		String dataFileName = "data.zip";
		ZipFile dataFile = new ZipFile(timeseriesFolder, timeSeries.getCompressedData(), dataFileName, "Compressed Time Series CSV file");
		timeseriesFolder.addChild(dataFile);

		String informationFileName = "TimeSeriesInfo.xml";
		XStream xStream = getXStream();
		String informationFileXml = xStream.toXML(timeSeries.getTimeSeriesInfo());
		InputStream is = new ByteArrayInputStream(informationFileXml.getBytes());
		ZipFile informationFile = new ZipFile(timeseriesFolder, is, informationFileName, "Time Series information file");
		timeseriesFolder.addChild(informationFile);

		return timeseriesFolder;
	}

	protected ZipItem convertAquaMapsItem(String cleanedItemName, AquaMapsItem aquaMapsItem) throws InternalErrorException, IOException
	{
		String comment = aquaMapsItem.getDescription();
		ZipFolder aquamapsFolder = new ZipFolder(cleanedItemName, comment);

		List<String> childrenNames = new LinkedList<String>();

		File aquamapsMetadata = aquaMapsItem.getMetadata();
		String metadataFileName = FileSystemNameUtil.cleanFileName(aquamapsMetadata.getName());
		childrenNames.add(metadataFileName);
		ZipFile metadataFile = new ZipFile(aquamapsFolder, aquamapsMetadata.getData(), metadataFileName, "AquaMapsItem metadata file");
		aquamapsFolder.addChild(metadataFile);

		for (Image image:aquaMapsItem.getImages())
		{
			ZipFile imageFile = convertImage(aquamapsFolder, image);
			//we check if the name is unique
			String childName = getUniqueName(childrenNames, imageFile.getName());
			imageFile.setName(childName);
			childrenNames.add(childName);

			aquamapsFolder.addChild(imageFile);
		}

		return aquamapsFolder;
	}

	protected ZipFile convertImage(ZipFolder parent, Image image) throws InternalErrorException, IOException
	{
		String name = FileSystemNameUtil.cleanFileName(image.getName());
		name = MimeTypeUtil.getNameWithExtension(name, image.getMimeType());


		return new ZipFile(parent, image.getData(), name, "");
	}



	protected ZipItem convertDocument(String cleanedItemName, Document document) throws InternalErrorException, IOException
	{
		if (document.getMetadata().size() == 0){
			//there are not Metadata then we return a simple file
			return convertDocumentFile(document);
		}

		//the document is complex, we return a folder
		String comment = document.getDescription();
		ZipFolder documentFolder = new ZipFolder(cleanedItemName, comment);

		ZipFile documentFile = convertDocumentFile(document);
		documentFile.setParent(documentFolder);
		documentFolder.addChild(documentFile);

		List<String> childrenNames = new LinkedList<String>();

		for (Map.Entry<String, DocumentMetadata> metadata: document.getMetadata().entrySet())
		{
			String metadataName = metadata.getKey();
			DocumentMetadata documentMetadata = metadata.getValue();

			ZipFile metadataZipItem = convertDocumentMetadata(documentFolder, metadataName, documentMetadata);

			//we check if the name is unique
			String childName = getUniqueName(childrenNames, metadataZipItem.getName());
			metadataZipItem.setName(childName);
			childrenNames.add(childName);

			documentFolder.addChild(metadataZipItem);
		}

		return documentFolder;
	}

	protected ZipFile convertDocumentFile(Document document) throws InternalErrorException, IOException
	{
		String mimeType = document.getMimeType();
		String cleanedItemName = FileSystemNameUtil.cleanFileName(document.getName());
		String name = MimeTypeUtil.getNameWithExtension(cleanedItemName, mimeType);
		String comment = document.getDescription();
		return new ZipFile(document.getData(), name, comment);
	}

	protected ZipFile convertDocumentMetadata(ZipFolder parent, String metadataName, DocumentMetadata documentMetadata) throws InternalErrorException
	{
		String cleanedItemName = FileSystemNameUtil.cleanFileName(metadataName);
		String name = cleanedItemName+"."+Extensions.METADATA.getValue();
		InputStream is = new ByteArrayInputStream(documentMetadata.getXML().getBytes());
		return new ZipFile(parent, is, name, metadataName);
	}

	/**
	 * Calculates an unique name for the passed name and list of already used names
	 * @param usedNames the already used names.
	 * @param name the candidate name,
	 * @return the unique name.
	 */
	protected static String getUniqueName(List<String> usedNames, String name)
	{
		String candidatePrefix = name;
		String candidateSuffix = "";

		//if there is any file name extension we preserve it
		if (name.contains(".")){
			int dotIndex = name.lastIndexOf('.');
			candidatePrefix = name.substring(0,dotIndex);
			candidateSuffix = name.substring(dotIndex);
		}

		String candidateName = candidatePrefix+candidateSuffix;

		int index = 1;
		while(usedNames.contains(candidateName)){
			candidateName = candidatePrefix+"("+index+")"+candidateSuffix;
			index++;
		}

		return candidateName;
	}

}
