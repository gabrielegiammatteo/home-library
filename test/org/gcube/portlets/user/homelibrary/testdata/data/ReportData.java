/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.data;

import java.util.Calendar;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ReportData {
	
	protected String name;
	protected String description;
	
	protected Calendar created;
	protected Calendar lastEdit;
	protected String author;
	protected String lastEditBy;
	protected String templateName;
	protected int numberOfSections;
	protected String status;
	
	protected String file;
	
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
	 * @return the created
	 */
	public Calendar getCreated() {
		return created;
	}

	/**
	 * @return the lastEdit
	 */
	public Calendar getLastEdit() {
		return lastEdit;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the lastEditBy
	 */
	public String getLastEditBy() {
		return lastEditBy;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @return the numberOfSections
	 */
	public int getNumberOfSections() {
		return numberOfSections;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportData [author=");
		builder.append(author);
		builder.append(", created=");
		builder.append(created);
		builder.append(", description=");
		builder.append(description);
		builder.append(", file=");
		builder.append(file);
		builder.append(", lastEdit=");
		builder.append(lastEdit);
		builder.append(", lastEditBy=");
		builder.append(lastEditBy);
		builder.append(", name=");
		builder.append(name);
		builder.append(", numberOfSections=");
		builder.append(numberOfSections);
		builder.append(", status=");
		builder.append(status);
		builder.append(", templateName=");
		builder.append(templateName);
		builder.append("]");
		return builder.toString();
	}
}
