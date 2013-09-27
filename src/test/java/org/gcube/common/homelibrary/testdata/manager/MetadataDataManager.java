/**
 * 
 */
package org.gcube.common.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.testdata.TestDataManagerUtil;
import org.gcube.common.homelibrary.testdata.data.DocMetadata;
import org.gcube.common.homelibrary.testdata.data.ImageDocumentData;
import org.gcube.common.homelibrary.testdata.data.PDFDocumentData;
import org.gcube.common.homelibrary.testdata.data.TestData;
import org.gcube.common.homelibrary.testdata.data.UrlDocumentData;
import org.gcube.common.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class MetadataDataManager {

	protected Logger logger = Logger.getLogger(MetadataDataManager.class);

	protected XStream xstream;
	protected Random random;
	protected List<MetadataInfo> data;

	/**
	 * 
	 */
	public MetadataDataManager()
	{
		configureXStream();
		random = new Random();
		data = loadAllMetadata();
	}


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


	protected List<MetadataInfo> loadAllMetadata()
	{
		List<TestData> data = loadAllData();
		List<MetadataInfo> metadata = new LinkedList<MetadataInfo>();
		for (TestData testData:data) {
			String name = testData.getName();
			String description = testData.getDescription();
			String oid = TestDataManagerUtil.getUID();
			String collectionName = testData.getCollectionName();

			for (DocMetadata docMetadata:testData.getMetadatas()){
				metadata.add(new MetadataInfo(name, description, oid, docMetadata.getSchema(), docMetadata.getLanguage(), docMetadata.getFile(), collectionName));
			}
		}
		return metadata;
	}

	/**
	 * {@inheritDoc}
	 */
	protected List<TestData> loadAllData() {

		List<TestData> data = new LinkedList<TestData>();

		data.addAll(loadData(ImageDataManager.DATA_FILE));
		data.addAll(loadData(PDFDataManager.DATA_FILE));
		data.addAll(loadData(UrlDataManager.DATA_FILE));

		logger.trace("Loaded "+data.size()+" test data.");
		return data;
	}

	/**
	 * Load the data list.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<TestData> loadData(String dataListFile)
	{
		String dataListPath = TestDataFactory.resourceRoot+dataListFile;
		logger.debug("Loading the data list from: "+dataListPath);

		InputStream dataList = TestDataFactory.class.getResourceAsStream(dataListPath);
		List<TestData> data = (List<TestData>) xstream.fromXML(dataList);
		logger.trace("Loaded "+data.size()+" test data.");
		return data;
	}

	protected InputStream getMetadataStream(MetadataInfo metadataInfo){
		logger.trace("getTestDataStream testData: "+metadataInfo);
		String imagePath = TestDataFactory.resourceRoot+metadataInfo.metadataFile;
		return TestDataFactory.class.getResourceAsStream(imagePath);
	}

	protected Metadata fillMetadata(WorkspaceFolder destinationFolder, MetadataInfo metadataInfo) throws InternalErrorException {
		try{
			InputStream metadataStream = getMetadataStream(metadataInfo);
			String metadata = TestDataManagerUtil.loadFileAsString(metadataStream);
			String name = WorkspaceUtil.getUniqueName(metadataInfo.name, destinationFolder);
			return destinationFolder.createMetadataItem(name, metadataInfo.description, metadataInfo.oid, metadataInfo.schema, metadataInfo.language, metadata, metadataInfo.collectionName);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the metadata", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the metadata", e);
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @param numberOfMetadata the number of metadata to fill.
	 * @return the created metadata.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public List<Metadata> fillMetadatas(WorkspaceFolder destinationFolder, int numberOfMetadata) throws InternalErrorException {
		int counter = 0;
		List<Metadata> metadatas = new LinkedList<Metadata>();
		while(counter<numberOfMetadata){
			int imgIndex = counter % data.size();
			MetadataInfo metadataInfo = data.get(imgIndex);
			Metadata metadata = fillMetadata(destinationFolder, metadataInfo);
			metadatas.add(metadata);
			counter++;
		}
		return metadatas;
	}

	/**
	 * @param destinationFolder the destination folder.
	 * @return the created data.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public List<Metadata> fillAllMetadatas(WorkspaceFolder destinationFolder) throws InternalErrorException {
		List<Metadata> metadatas = new LinkedList<Metadata>();
		for (MetadataInfo metadataInfo:data){
			Metadata metadata = fillMetadata(destinationFolder, metadataInfo);
			metadatas.add(metadata);
		}
		return metadatas;
	}

	/**
	 * @author Federico De Faveri defaveri@isti.cnr.it
	 *
	 */
	class MetadataInfo{
		String name;
		String description;
		String oid;
		String schema;
		String language;
		String metadataFile;
		String collectionName;

		/**
		 * @param name
		 * @param description
		 * @param oid
		 * @param schema
		 * @param language
		 * @param metadataFile
		 * @param collectionName
		 */
		public MetadataInfo(String name, String description, String oid,
				String schema, String language, String metadataFile,
				String collectionName) {
			this.name = name;
			this.description = description;
			this.oid = oid;
			this.schema = schema;
			this.language = language;
			this.metadataFile = metadataFile;
			this.collectionName = collectionName;
		}
	}

}
