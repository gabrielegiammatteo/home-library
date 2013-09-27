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
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.portlets.user.homelibrary.testdata.AbstractDataManager;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.testdata.data.TemplateData;
import org.gcube.portlets.user.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TemplateDataManager extends AbstractDataManager<TemplateData,ReportTemplate> {

	/**
	 * 
	 */
	public TemplateDataManager() {
		super("templates.xml");
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("template", TemplateData.class);
		xstream.alias("templates", LinkedList.class);

	}

	/**
	 * Return the <code>InputStream</code> for the given testData data.
	 * @param testData the test data.
	 * @return the InputStream.
	 */
	protected static InputStream getTestDataStream(TemplateData testData){
		String testDataPath = TestDataFactory.resourceRoot+testData.getFile();
		return TestDataFactory.class.getResourceAsStream(testDataPath);
	}

	@Override
	protected ReportTemplate fillData(WorkspaceFolder destinationFolder, TemplateData testData) throws InternalErrorException {
		InputStream reportTemplateData = getTestDataStream(testData);

		try{
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);

			return destinationFolder.createReportTemplateItem(name, testData.getDescription(), testData.getCreated(), testData.getLastEdit(), testData.getAuthor(), testData.getLastEditBy(), 
					testData.getNumberOfSections(), testData.getStatus(), reportTemplateData);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the report template", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the report template", e);
			throw new InternalErrorException(e);
		}
	}

}
