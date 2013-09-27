/**
 * 
 */
package org.gcube.common.homelibrary.testdata.data;

import java.util.List;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TimeSeriesData {

	protected String name;
	protected String description;
	protected String timeseriesId;
	protected String title;
	protected String creator;
	protected String timeseriesDescription;
	protected String timeseriesCreationDate;
	protected String publisher;
	protected String sourceId;
	protected String sourceName;
	protected String rights;
	protected long dimension;
	protected List<String> headerLabels;
	protected String compressedCSV;
	
	/**
	 * @param name the time series name.
	 * @param description the time series description. 
	 * @param timeseriesId the time series id.
	 * @param title the time series title.
	 * @param creator the time series creator.
	 * @param timeseriesDescription the time series description. 
	 * @param timeseriesCreationDate the time series creation time.
	 * @param publisher the time series publisher.
	 * @param sourceId the time series source id.
	 * @param sourceName the time series source name.
	 * @param rights the time series rights.
	 * @param dimension the time series dimension.
	 * @param headerLabels the time series headers.
	 * @param compressedCSV the time series compressed csv.
	 */
	public TimeSeriesData(String name, String description, String timeseriesId,
			String title, String creator, String timeseriesDescription,
			String timeseriesCreationDate, String publisher, String sourceId,
			String sourceName, String rights, long dimension,
			List<String> headerLabels, String compressedCSV) {
		this.name = name;
		this.description = description;
		this.timeseriesId = timeseriesId;
		this.title = title;
		this.creator = creator;
		this.timeseriesDescription = timeseriesDescription;
		this.timeseriesCreationDate = timeseriesCreationDate;
		this.publisher = publisher;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.rights = rights;
		this.dimension = dimension;
		this.headerLabels = headerLabels;
		this.compressedCSV = compressedCSV;
	}

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
	 * @return the timeseriesId
	 */
	public String getTimeseriesId() {
		return timeseriesId;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	
	/**
	 * @return the timeseriesDescription
	 */
	public String getTimeseriesDescription() {
		return timeseriesDescription;
	}
	
	/**
	 * @return the timeseriesCreationDate
	 */
	public String getTimeseriesCreationDate() {
		return timeseriesCreationDate;
	}
	
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}
	
	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}
	
	/**
	 * @return the rights
	 */
	public String getRights() {
		return rights;
	}
	
	/**
	 * @return the dimension
	 */
	public long getDimension() {
		return dimension;
	}
	
	/**
	 * @return the headerLabels
	 */
	public List<String> getHeaderLabels() {
		return headerLabels;
	}
	
	/**
	 * @return the compressedCSV
	 */
	public String getCompressedCSV() {
		return compressedCSV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeSeriesData [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", title=");
		builder.append(title);
		builder.append(", timeseriesDescription=");
		builder.append(timeseriesDescription);
		builder.append(", compressedCSV=");
		builder.append(compressedCSV);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", dimension=");
		builder.append(dimension);
		builder.append(", headerLabels=");
		builder.append(headerLabels);
		builder.append(", publisher=");
		builder.append(publisher);
		builder.append(", rights=");
		builder.append(rights);
		builder.append(", sourceId=");
		builder.append(sourceId);
		builder.append(", sourceName=");
		builder.append(sourceName);
		builder.append(", timeseriesCreationDate=");
		builder.append(timeseriesCreationDate);
		builder.append(", timeseriesId=");
		builder.append(timeseriesId);
		builder.append("]");
		return builder.toString();
	}
}
