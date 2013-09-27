/**
 * 
 */
package org.gcube.common.homelibrary.testdata.data;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class PDFDocumentData extends AbstractTestData {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PDFData [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
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
