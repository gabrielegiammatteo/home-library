/**
 * 
 */
package org.gcube.common.homelibrary.util.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.gcube.common.homelibrary.util.zip.zipmodel.ZipFile;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipFolder;
import org.gcube.common.homelibrary.util.zip.zipmodel.ZipItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class ZipModelWriter {

	protected Logger logger = Logger.getLogger(ZipModelWriter.class);

	public File writeItem(ZipItem item) throws IOException
	{
		File zipFile = File.createTempFile("zippping", "gz");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		addZipItem(out, item);
		out.close();
		return zipFile;
	}

	protected void addZipItem(ZipOutputStream zos, ZipItem item) throws IOException
	{
		switch (item.getType()) {
			case FILE: addZipFile(zos, (ZipFile) item); break;
			case FOLDER: addZipFolder(zos, (ZipFolder) item); break;
		}
	}

	protected void addZipFolder(ZipOutputStream zos, ZipFolder folder) throws IOException
	{
		if (folder.getChildren().size() == 0) {
			logger.trace("adding ZipFile path: "+ folder.getPath());
			ZipEntry zipEntry = new ZipEntry(folder.getPath() + "/");
			zos.putNextEntry(zipEntry);
			zos.closeEntry();
		}
		for (ZipItem item:folder.getChildren()) addZipItem(zos, item); 
	}

	protected void addZipFile(ZipOutputStream zos, ZipFile file) throws IOException
	{
		logger.trace("adding ZipFile path: "+file.getPath());
		ZipEntry zipEntry = new ZipEntry(file.getPath());
		zipEntry.setComment(file.getComment());
		zipEntry.setExtra(file.getExtra());

		zos.putNextEntry(zipEntry);
		IOUtils.copy(file.getContentStream(), zos);
		zos.closeEntry();
	}

}
