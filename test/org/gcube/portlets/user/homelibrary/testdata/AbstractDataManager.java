/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 * @param <T> the test data.
 * @param <D> the generated items.
 */
public abstract class AbstractDataManager<T, D> {
	
	protected Logger logger = Logger.getLogger(AbstractDataManager.class);

	protected XStream xstream;
	protected Random random;
	protected List<T> data;
	
	/**
	 * Create a data manager with the specified file.
	 * @param dataListFile the data list file.
	 */
	public AbstractDataManager(String dataListFile)
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
	
	/**
	 * Create the data into the destination folder.
	 * @param destinationFolder where to create the destination folder.
	 * @param testData the data to create.
	 * @return the created external image.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected abstract D fillData(WorkspaceFolder destinationFolder, T testData) throws InternalErrorException;
	
	/**
	 * Create the requested external into the destination folder.
	 * @param destinationFolder the destination folder.
	 * @param numberOfData the number of data to create.
	 * @return the created external images.
	 * @throws InternalErrorException if an error occurs.
	 */
	public List<D> fillDatas(WorkspaceFolder destinationFolder, int numberOfData) throws InternalErrorException
	{
		int counter = 0;
		List<D> externals = new LinkedList<D>();

		while(counter<numberOfData){
			int dataIndex = counter % data.size();
			T image = data.get(dataIndex);
			D external = fillData(destinationFolder, image);
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
	public List<D> fillAllDatas(WorkspaceFolder destinationFolder) throws InternalErrorException
	{
		List<D> externals = new LinkedList<D>();
		for (T image : data){
			D external = fillData(destinationFolder, image);
			externals.add(external);
		}
		return externals;
	}

}
