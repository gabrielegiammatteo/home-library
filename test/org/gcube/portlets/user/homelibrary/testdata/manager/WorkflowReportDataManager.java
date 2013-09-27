/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.manager;

import java.util.LinkedList;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.WorkflowReport;
import org.gcube.portlets.user.homelibrary.testdata.AbstractDataManager;
import org.gcube.portlets.user.homelibrary.testdata.data.WorkflowReportData;
import org.gcube.portlets.user.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkflowReportDataManager extends AbstractDataManager<WorkflowReportData,WorkflowReport> {

	/**
	 * 
	 */
	public WorkflowReportDataManager() {
		super("workflowreports.xml");
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("workflowreport", WorkflowReportData.class);
		xstream.alias("workflowreports", LinkedList.class);
	}

	@Override
	protected WorkflowReport fillData(WorkspaceFolder destinationFolder, WorkflowReportData testData) throws InternalErrorException {

		try{
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);

			return destinationFolder.createWorkflowReport(name, testData.getDescription(), testData.getWorkflowId(), testData.getWorkflowStatus(), testData.getWorkflowData());
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the workflow report", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the workflow report", e);
			throw new InternalErrorException(e);
		}
	}

}
