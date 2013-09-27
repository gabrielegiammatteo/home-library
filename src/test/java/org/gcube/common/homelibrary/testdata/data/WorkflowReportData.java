/**
 * 
 */
package org.gcube.common.homelibrary.testdata.data;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkflowReportData {
	
	protected String name;
	protected String description;
	
	protected String workflowId;
	protected String workflowStatus;
	protected String workflowData;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the workflowId
	 */
	public String getWorkflowId() {
		return workflowId;
	}

	/**
	 * @return the workflowStatus
	 */
	public String getWorkflowStatus() {
		return workflowStatus;
	}

	/**
	 * @return the workflowData
	 */
	public String getWorkflowData() {
		return workflowData;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowReportData [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", workflowId=");
		builder.append(workflowId);
		builder.append(", workflowStatus=");
		builder.append(workflowStatus);
		builder.append(", workflowData=");
		builder.append(workflowData);
		builder.append("]");
		return builder.toString();
	}
}
