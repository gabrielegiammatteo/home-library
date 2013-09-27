/**
 * 
 */
package org.gcube.common.homelibrary.testdata.manager;

import java.util.LinkedList;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.QueryType;
import org.gcube.common.homelibrary.testdata.AbstractDataManager;
import org.gcube.common.homelibrary.testdata.data.QueryData;
import org.gcube.common.homelibrary.util.WorkspaceUtil;


import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class QueryDataManager extends AbstractDataManager<QueryData, Query> {

	/**
	 * 
	 */
	public QueryDataManager() {
		super("queries.xml");
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("query", QueryData.class);
		xstream.alias("queries", LinkedList.class);
	}

	@Override
	protected Query fillData(WorkspaceFolder destinationFolder, QueryData testData) throws InternalErrorException {
		try{
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);
			QueryType queryType = QueryType.valueOf(testData.getType());
			return destinationFolder.createQueryItem(name, testData.getDescription(), testData.getValue(), queryType);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the query", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the query", e);
			throw new InternalErrorException(e);
		}
	}

}
