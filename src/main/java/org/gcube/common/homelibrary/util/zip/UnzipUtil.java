/**
 * 
 */
package org.gcube.common.homelibrary.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileCleaner;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.gcube.common.homelibrary.home.HomeLibrary;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipItem;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class UnzipUtil {
	
	protected static final Logger logger = Logger.getLogger(HomeLibrary.class.getPackage().getName());
	
	/**
	 * Unzip the specified file into the specified workspace.
	 * @param destinationFolder the destination workspace.
	 * @param is the zip input stream.
	 * @param zipName the zip name.
	 * @throws IOException if an error occurs.
	 */
	public static void unzip(WorkspaceFolder destinationFolder, InputStream is, String zipName) throws IOException
	{
		logger.trace("unzip destinationWorkspace: "+destinationFolder+", zipName: "+zipName);
		
		logger.trace("Extracting zip model from zip file.");
		ZipFileModelExtractor zme = new ZipFileModelExtractor(is);
		List<ZipItem> items = zme.getModel();
		
		logger.trace("Zip Model:");
		ZipModelVisitor visitor = new ZipModelVisitor(logger);
		visitor.visit(items);
		
		logger.trace("Creating the items");
		ZipModelToWorkspaceCreator creator = new ZipModelToWorkspaceCreator();
		creator.create(destinationFolder, items);
	}
	
	/**
	 * Unzip the specified stream.
	 * @param is the input stream.
	 * @param os the destination stream.
	 * @throws Exception if an error occurs.
	 */
	public static void unzip(InputStream is, OutputStream os) throws Exception
	{
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry entry;
		
		while ((entry = zis.getNextEntry())!=null){
			if (!entry.isDirectory()){
				IOUtils.copy(zis, os);
				zis.closeEntry();
				zis.close();
				os.close();
				return;
			}
		}
		
		throw new Exception("No file entry found");
	}
	
	/**
	 * @param is the zipped input stream.
	 * @return the resutil output stream.
	 * @throws Exception if an error occurs.
	 */
	public static InputStream unzipToTmp(InputStream is) throws Exception
	{
		
		File tmpFile = File.createTempFile("unzippedts", "tmp");
		OutputStream os = new FileOutputStream(tmpFile);
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry entry;
		
		while ((entry = zis.getNextEntry())!=null){
			if (!entry.isDirectory()){
				IOUtils.copy(zis, os);
				zis.closeEntry();
				zis.close();
				os.close();
				InputStream tmpis = new FileInputStream(tmpFile);
				FileCleaner.track(tmpFile, tmpis);
				return tmpis;
			}
		}
		
		throw new Exception("No file entry found");
	}
	

}
