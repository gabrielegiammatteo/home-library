/**
 * 
 */
package org.gcube.common.homelibrary.testdata.data;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class DocMetadata {
	
	protected String schema;
	protected String language;
	protected String file;

	/**
	 * @param schema the metadata schema.
	 * @param language the metadata language.
	 * @param file the metadata file.
	 */
	public DocMetadata(String schema, String language, String file) {
		this.schema = schema;
		this.language = language;
		this.file = file;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return schema+"_"+language;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
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
		builder.append("DocMetadata [schema=");
		builder.append(schema);
		builder.append(", language=");
		builder.append(language);
		builder.append(", file=");
		builder.append(file);
		builder.append("]");
		return builder.toString();
	}
}
