/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.testdata.data.TestData;
import org.gcube.portlets.user.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 * @param <T> the test data.
 * @param <E> the external generated item.
 * @param <D> the document generated item.
 */
public abstract class TwinDataManager<T extends TestData, E, D> {
	
	protected Logger logger = Logger.getLogger(TwinDataManager.class);

	protected XStream xstream;
	protected Random random;
	protected List<T> data;

	
	/**
	 * Create a twin data manager from the specified file.
	 * @param dataListFile the data list file.
	 */
	public TwinDataManager(String dataListFile)
	{
		configureXStream();
		random = new Random();
		data = loadData(dataListFile);
	}
	
	protected abstract void configureXStream();
	
	/**
	 * Load the data list.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> loadData(String dataListFile)
	{
		String dataListPath = TestDataFactory.resourceRoot+dataListFile;
		logger.debug("Loading the data list from: "+dataListPath);

		InputStream dataList = TestDataFactory.class.getResourceAsStream(dataListPath);
		List<T> data = (List<T>) xstream.fromXML(dataList);
		logger.trace("Loaded "+data.size()+" test data.");
		return data;
	}
	
	protected abstract E createExternal(WorkspaceFolder destinationFolder, String name, String description, String mimeType, InputStream data) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException;
	
	/**
	 * Create an external data into the destination folder.
	 * @param destinationFolder where to create the destination folder.
	 * @param testData the image to create.
	 * @return the created external image.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected E fillExternal(WorkspaceFolder destinationFolder, T testData) throws InternalErrorException
	{
		InputStream dataStream = TestDataManagerUtil.getTestDataStream(testData);
		try{
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);
			return createExternal(destinationFolder, name, testData.getDescription(), testData.getMimeType(), dataStream);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the external", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the external", e);
			throw new InternalErrorException(e);
		}
	}
	
	
	/**
	 * Create the requested external into the destination folder.
	 * @param destinationFolder the destination folder.
	 * @param numberOfData the number of data to create.
	 * @return the created external images.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<E> fillExternals(WorkspaceFolder destinationFolder, int numberOfData) throws InternalErrorException
	{
		int counter = 0;
		List<E> externals = new LinkedList<E>();

		while(counter<numberOfData){
			int dataIndex = counter % data.size();
			T image = data.get(dataIndex);
			E external = fillExternal(destinationFolder, image);
			externals.add(external);
			counter++;
		}

		return externals;
	}

	/**
	 * Create the requested external into the destination folder.
	 * @param destinationFolder the destination folder.
	 * @return the created external images.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<E> fillAllExternals(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		List<E> externals = new LinkedList<E>();
		for (T image : data){
			E external = fillExternal(destinationFolder, image);
			externals.add(external);
		}
		return externals;
	}
	
	protected abstract D createDocument(WorkspaceFolder destinationFolder, String name, String description, String oid, String mimeType, InputStream data, Map<String, String> metadata, Map<String,String> annotations, String collectionName) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException;
	
	/**
	 * Create a Document into the destination folder.
	 * @param destinationFolder the destination folder.
	 * @param document the image data.
	 * @return the created Image Document.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected D fillDocument(WorkspaceFolder destinationFolder, T document) throws InternalErrorException
	{
		InputStream documentStream = TestDataManagerUtil.getTestDataStream(document);
		try{
			String name = WorkspaceUtil.getUniqueName(document.getName(), destinationFolder);
			Map<String, String> metadata = TestDataManagerUtil.loadMetadatas(document.getMetadatas());
			return createDocument(destinationFolder, name, document.getDescription(), TestDataManagerUtil.getUID(), document.getMimeType(), documentStream, metadata, new LinkedHashMap<String, String>(), document.getCollectionName());
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the document", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the document", e);
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Create a set of image documents.
	 * @param destinationFolder the destination folder.
	 * @param numberOfDocuments the number of document images to create.
	 * @return the list of create document images.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<D> fillDocuments(WorkspaceFolder destinationFolder, int numberOfDocuments) throws InternalErrorException
	{
		int counter = 0;
		List<D> documents = new LinkedList<D>();
		while(counter<numberOfDocuments){
			int imgIndex = counter % data.size();
			T testData = data.get(imgIndex);
			D document = fillDocument(destinationFolder, testData);
			documents.add(document);
			counter++;
		}
		return documents;
	}

	/**
	 * Create a set of image documents.
	 * @param destinationFolder the destination folder.
	 * @return the list of create document images.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<D> fillAllDocuments(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		List<D> documents = new LinkedList<D>();

		for(T testData : data){
			D document = fillDocument(destinationFolder, testData);
			documents.add(document);
		}
		return documents;
	}

	/**
	 * Retrieve an input stream from a random image.
	 * @return the random input stream.
	 */
	protected T getRandomDocumentData()
	{
		int dataIndex = random.nextInt(data.size());
		return data.get(dataIndex);
	}

	/**
	 * Retrieve an input stream from a random image.
	 * @return the random input stream.
	 */
	public InputStream getRandomInputStream()
	{
		T image = getRandomDocumentData();
		return TestDataManagerUtil.getTestDataStream(image);
	}

	/**
	 * Return a random temporary image file. 
	 * @return the random file.
	 * @throws InternalErrorException if an error occurs.
	 */
	public File getTMPRandomFile() throws InternalErrorException
	{
		try{
			File tmp = File.createTempFile("randomFile", "tmp");
			tmp.deleteOnExit();

			InputStream is = getRandomInputStream();
			IOUtils.copy(is, new FileOutputStream(tmp));

			return tmp;
		}catch (IOException e) {
			logger.error("Error creating tmp data", e);
			throw new InternalErrorException(e);
		}
	}

}
