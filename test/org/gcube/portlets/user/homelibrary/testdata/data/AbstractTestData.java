/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.data;

import java.util.List;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public abstract class AbstractTestData implements TestData {
	
	protected String name;
	protected String description;
	protected String mimeType;
	protected String collectionName;
	protected String file;
	protected List<DocMetadata> metadatas;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMimeType() {
		return mimeType;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFile() {
		return file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocMetadata> getMetadatas() {
		return metadatas;
	}

}
