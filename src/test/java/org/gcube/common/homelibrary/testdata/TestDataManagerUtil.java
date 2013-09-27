/**
 * 
 */
package org.gcube.common.homelibrary.testdata;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.testdata.data.DocMetadata;
import org.gcube.common.homelibrary.testdata.data.TestData;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestDataManagerUtil {
	
	protected static final Logger logger = Logger.getLogger(TestDataManagerUtil.class);

	
	/**
	 * Load a file as string.
	 * @param file the file input stream.
	 * @return the file as string.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static String loadFileAsString(InputStream file) throws InternalErrorException
	{
		try{
			StringBuilder sb = new StringBuilder();

			int reads = 0;
			byte[] buffer = new byte[1024];

			while((reads = file.read(buffer))>=0) sb.append(new String(buffer,0,reads));

			file.close();

			return sb.toString();
		}
		catch (Exception e)
		{
			logger.error("Error loading file from InputStream", e);
			throw new InternalErrorException(e);
		}
	}
	
	/**
	 * Load a metadata as string.
	 * @param docMetadata the metadata to load.
	 * @return the metadata string.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected static String loadMetadata(DocMetadata docMetadata) throws InternalErrorException
	{
		InputStream metadataFile = TestDataFactory.class.getResourceAsStream(TestDataFactory.resourceRoot+docMetadata.getFile());
		return loadFileAsString(metadataFile);
	}

	/**
	 * Load a list of metadata.
	 * @param docMetadatas the metadata list to load.
	 * @return the loaded metadata.
	 * @throws InternalErrorException if an error occurs.
	 */
	protected static Map<String, String> loadMetadatas(List<DocMetadata> docMetadatas) throws InternalErrorException
	{
		Map<String, String> metadatas = new LinkedHashMap<String, String>();
		for (DocMetadata docMetadata:docMetadatas){
			String data = loadMetadata(docMetadata);
			metadatas.put(docMetadata.getName(), data);
		}
		return metadatas;
	}
	
	/**
	 * Return the <code>InputStream</code> for the given testData data.
	 * @param testData the test data.
	 * @return the InputStream.
	 */
	protected static InputStream getTestDataStream(TestData testData){
		logger.trace("getTestDataStream testData: "+testData);
		String testDataPath = TestDataFactory.resourceRoot+testData.getFile();
		logger.trace("TestDataStream path: "+testDataPath);
		return TestDataFactory.class.getResourceAsStream(testDataPath);
	}
	
	/**
	 * @return the generated UUID.
	 */
	public static String getUID() {
		return UUID.randomUUID().toString();
	}

}
