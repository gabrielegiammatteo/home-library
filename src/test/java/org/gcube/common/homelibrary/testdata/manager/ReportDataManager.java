/**
 * 
 */
package org.gcube.common.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedList;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.testdata.AbstractDataManager;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.testdata.data.ReportData;
import org.gcube.common.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ReportDataManager extends AbstractDataManager<ReportData,Report> {

	/**
	 * 
	 */
	public ReportDataManager() {
		super("reports.xml");
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("report", ReportData.class);
		xstream.alias("reports", LinkedList.class);
	}

	/**
	 * Return the <code>InputStream</code> for the given testData data.
	 * @param testData the test data.
	 * @return the InputStream.
	 */
	protected static InputStream getTestDataStream(ReportData testData){
		String testDataPath = TestDataFactory.resourceRoot+testData.getFile();
		return TestDataFactory.class.getResourceAsStream(testDataPath);
	}

	@Override
	protected Report fillData(WorkspaceFolder destinationFolder, ReportData testData) throws InternalErrorException {
		InputStream reportData = getTestDataStream(testData);

		try{
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);

			return destinationFolder.createReportItem(name, testData.getDescription(), testData.getCreated(), testData.getLastEdit(), testData.getAuthor(), testData.getLastEditBy(), 
					testData.getTemplateName(), testData.getNumberOfSections(), testData.getStatus(), reportData);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the report", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the report", e);
			throw new InternalErrorException(e);
		}
	}

}
