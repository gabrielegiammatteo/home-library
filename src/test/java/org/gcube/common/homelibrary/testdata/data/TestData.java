package org.gcube.common.homelibrary.testdata.data;

import java.util.List;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface TestData {

	/**
	 * @return the file
	 */
	public abstract String getName();

	/**
	 * @return the description
	 */
	public abstract String getDescription();

	/**
	 * @return the mimeType
	 */
	public abstract String getMimeType();

	/**
	 * @return the collectionName
	 */
	public abstract String getCollectionName();

	/**
	 * @return the file
	 */
	public abstract String getFile();

	/**
	 * @return the metadatas
	 */
	public abstract List<DocMetadata> getMetadatas();

}