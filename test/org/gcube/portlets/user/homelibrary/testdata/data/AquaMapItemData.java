/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.data;

import java.util.Map;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class AquaMapItemData {
	
	protected String name;
	protected String description;
	protected String mapName;
	protected String mapType;
	protected String author;
	protected int numberOfSpecies;
	protected String boundingBox;
	protected float psoThreshold;
	protected int numberOfGeneratedImages;
	protected String metadataFile;
	protected Map<String,String> imageFiles;
	
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
	 * @return the metadataFile
	 */
	public String getMetadataFile() {
		return metadataFile;
	}
	
	/**
	 * @return the imageFiles
	 */
	public Map<String,String> getImageFiles() {
		return imageFiles;
	}
	
	

	/**
	 * @return the mapName
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * @return the mapType
	 */
	public String getMapType() {
		return mapType;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the numberOfSpecies
	 */
	public int getNumberOfSpecies() {
		return numberOfSpecies;
	}

	/**
	 * @return the boundingBox
	 */
	public String getBoundingBox() {
		return boundingBox;
	}

	/**
	 * @return the psoThreshold
	 */
	public float getPsoThreshold() {
		return psoThreshold;
	}

	/**
	 * @return the numberOfGeneratedImages
	 */
	public int getNumberOfGeneratedImages() {
		return numberOfGeneratedImages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AquaMapItemData [author=");
		builder.append(author);
		builder.append(", boundingBox=");
		builder.append(boundingBox);
		builder.append(", description=");
		builder.append(description);
		builder.append(", imageFiles=");
		builder.append(imageFiles);
		builder.append(", mapName=");
		builder.append(mapName);
		builder.append(", mapType=");
		builder.append(mapType);
		builder.append(", metadataFile=");
		builder.append(metadataFile);
		builder.append(", name=");
		builder.append(name);
		builder.append(", numberOfGeneratedImages=");
		builder.append(numberOfGeneratedImages);
		builder.append(", numberOfSpecies=");
		builder.append(numberOfSpecies);
		builder.append(", psoThreshold=");
		builder.append(psoThreshold);
		builder.append("]");
		return builder.toString();
	}
}
