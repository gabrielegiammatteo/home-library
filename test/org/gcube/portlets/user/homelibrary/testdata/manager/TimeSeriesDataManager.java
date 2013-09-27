/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedList;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.portlets.user.homelibrary.testdata.AbstractDataManager;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.testdata.data.TimeSeriesData;
import org.gcube.portlets.user.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TimeSeriesDataManager extends AbstractDataManager<TimeSeriesData, TimeSeries> {

	/**
	 * 
	 */
	public TimeSeriesDataManager() {
		super("timeseries.xml");
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("ts", TimeSeriesData.class);
		xstream.alias("timeseries", LinkedList.class);
	}

	/**
	 * Return the <code>InputStream</code> for the given testData data.
	 * @param testData the test data.
	 * @return the InputStream.
	 */
	protected static InputStream getTestDataStream(TimeSeriesData testData){
		String testDataPath = TestDataFactory.resourceRoot+testData.getCompressedCSV();
		return TestDataFactory.class.getResourceAsStream(testDataPath);
	}

	@Override
	protected TimeSeries fillData(WorkspaceFolder destinationFolder, TimeSeriesData testData) throws InternalErrorException {

		InputStream tsStream = getTestDataStream(testData);

		try{
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);

			return destinationFolder.createTimeSeries(name, testData.getDescription(), testData.getTimeseriesId(), 
					testData.getTitle(), testData.getCreator(), testData.getTimeseriesDescription(), testData.getTimeseriesCreationDate(), 
					testData.getPublisher(), testData.getSourceId(), testData.getSourceName(), testData.getRights(), testData.getDimension(),
					testData.getHeaderLabels(), tsStream);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the ts", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the ts", e);
			throw new InternalErrorException(e);
		}
	}

}
