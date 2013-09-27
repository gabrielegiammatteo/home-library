/**
 * 
 */
package org.gcube.common.homelibrary.testdata.manager;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.testdata.AbstractDataManager;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.testdata.data.AquaMapItemData;
import org.gcube.common.homelibrary.util.WorkspaceUtil;

import com.thoughtworks.xstream.XStream;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class AquaMapItemDataManager extends AbstractDataManager<AquaMapItemData, WorkspaceFolder> {

	/**
	 * 
	 */
	public AquaMapItemDataManager() {
		super("aquamapitems.xml");
	}

	@Override
	protected void configureXStream() {
		xstream = new XStream();
		xstream.alias("aquamapitem", AquaMapItemData.class);
		xstream.alias("aquamapitems", LinkedList.class);
		xstream.alias("images", LinkedHashMap.class);
	}

	/**
	 * Return the <code>InputStream</code> for the given testData data.
	 * @param testData the test data.
	 * @return the InputStream.
	 */
	protected static InputStream getTestDataStream(String file){
		String testDataPath = TestDataFactory.resourceRoot+file;
		return TestDataFactory.class.getResourceAsStream(testDataPath);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WorkspaceFolder fillData(WorkspaceFolder destinationFolder, AquaMapItemData testData) throws InternalErrorException {
		try{
			InputStream metadata = getTestDataStream(testData.getMetadataFile());
			Map<String,InputStream> images = new LinkedHashMap<String,InputStream>();
			for (Entry<String, String> image:testData.getImageFiles().entrySet()) images.put(image.getKey(),getTestDataStream(image.getValue()));
			String name = WorkspaceUtil.getUniqueName(testData.getName(), destinationFolder);
			return destinationFolder.createAquaMapsItem(name, testData.getDescription(), testData.getMapName(), testData.getMapType(), testData.getAuthor(), testData.getNumberOfSpecies(), testData.getBoundingBox(), testData.getPsoThreshold(),
					testData.getNumberOfGeneratedImages(), metadata, images);
		}catch(InsufficientPrivilegesException e)
		{
			logger.error("Error creating the aquamaps item", e);
			throw new InternalErrorException(e);
		}catch(ItemAlreadyExistException e)
		{
			logger.error("Error creating the aquamaps item", e);
			throw new InternalErrorException(e);
		} catch (WorkspaceFolderNotFoundException e) {
			logger.error("Error creating the aquamaps item", e);
			throw new InternalErrorException(e);
		} catch (WrongDestinationException e) {
			logger.error("Error creating the aquamaps item", e);
			throw new InternalErrorException(e);
		}
	}

}
