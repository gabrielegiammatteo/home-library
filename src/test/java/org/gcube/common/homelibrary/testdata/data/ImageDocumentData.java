/**
 * 
 */
package org.gcube.common.homelibrary.testdata.data;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ImageDocumentData extends AbstractTestData {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageData [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", mimeType=");
		builder.append(mimeType);
		builder.append(", collectionName=");
		builder.append(collectionName);
		builder.append(", file=");
		builder.append(file);
		builder.append(", metadatas=");
		builder.append(metadatas);
		builder.append("]");
		return builder.toString();
	}
}
