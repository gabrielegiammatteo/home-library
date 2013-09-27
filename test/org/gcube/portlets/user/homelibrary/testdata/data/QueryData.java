/**
 * 
 */
package org.gcube.portlets.user.homelibrary.testdata.data;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class QueryData {
	
	protected String name;
	protected String description;
	protected String type;
	protected String value;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryData [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
