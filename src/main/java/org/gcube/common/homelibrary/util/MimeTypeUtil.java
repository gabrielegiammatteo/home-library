/**
 * 
 */
package org.gcube.common.homelibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.medsea.mimeutil.MimeUtil;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class MimeTypeUtil {
	
	protected static final Logger logger = Logger.getLogger(MimeTypeUtil.class);

	static{
		/*ConsoleAppender ca = new ConsoleAppender(new SimpleLayout());
		ca.setThreshold(Level.ALL);
		ca.activateOptions();
		logger.addAppender(ca);*/
		logger.setLevel(Level.ALL);
	}
	
	/**
	 * 
	 */
	public static final String BINARY_MIMETYPE = "application/octet-stream";
	public static final String[] ZIP_MIMETYPES = new String[]{
	"application/x-compress",
	"application/x-compressed",
	"application/x-gzip",
	"application/x-winzip",
	"application/x-zip",
	"application/zip",
	"multipart/x-zip"};
	
	protected static final Map<String, String> mimetype_extension_map = new LinkedHashMap<String, String>();
	protected static final Map<String, String> extension_mimetype_map = new LinkedHashMap<String, String>();

	static{
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		
//		InputStream mapFile = MimeTypeUtil.class.getResourceAsStream("/org/gcube/portlets/user/homelibrary/util/resources/MimeTypeToExtensionMap.properties");
		InputStream extensionToMimetype = MimeTypeUtil.class.getResourceAsStream(
				"ExtensionToMimeTypeMap.properties");
		InputStream mimetypeToExtension = MimeTypeUtil.class.getResourceAsStream(
				"MimeTypeToExtensionMap.properties");
		try {
			loadExtensions(extensionToMimetype);
			loadMimeTypes(mimetypeToExtension);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static void loadExtensions(InputStream is) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String line = br.readLine();
		
		while(line != null){
			String[] split = line.split("=");
			if (split.length == 2) {
				String mimeType = split[0];
				String extension = split[1];
				extension_mimetype_map.put(extension, mimeType);
			}
			line = br.readLine();
		}
		br.close();

	}
	
	protected static void loadMimeTypes(InputStream is) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String line = br.readLine();
		
		while(line != null){
			String[] split = line.split("=");
			if (split.length == 2) {
				String mimeType = split[0];
				String extension = split[1];
				mimetype_extension_map.put(mimeType, extension);
			}
			line = br.readLine();
		}
		br.close();
	}
	
	/**
	 * @param mimeType the mime type.
	 * @return the related extension.
	 */
	public static String getExtension(String mimeType)
	{
		return mimetype_extension_map.get(mimeType);
	}
	
	/**
	 * @param extension the extension.
	 * @return the related mime type.
	 */
	public static String getMimeType(String extension)
	{
		return extension_mimetype_map.get(extension);
	}
	
	/**
	 * @param file the file to check.
	 * @return the mime type.
	 */
	public static String getMimeType(File file)
	{
		return MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file)).toString();
	}
	
	/**
	 * @param file the file to check
	 * @return the mime type.
	 */
	public static String getMimeType(InputStream file)
	{
	
		String mimeType = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file)).toString();
		return mimeType;
	}
	
	
	/**
	 * @param args arguments.
	 * @throws IOException if an error occurs.
	 */
	public static void main(String[] args) throws IOException {
//		System.out.println(MimeTypeUtil.getExtension("application/vnd.ms-works"));

	}
	
	/**
	 * @param name the file name.
	 * @param mimeType the mime type.
	 * @return the right name.
	 */
	public static String getNameWithExtension(String name, String mimeType)
	{
		logger.trace("getNameWithExtension name: "+name+" mimeType: "+mimeType);

		if (mimeType == null) return name;

		//we check if there exists an extension
		if (name.contains(".")){
			logger.trace("contains an extension");

			if (name.lastIndexOf(".") < name.length()-1) {

				String ext = name.substring(name.lastIndexOf(".")+1);
				logger.trace("ext: "+ext);

				//we check if there is a mimetype associated with the extension
				String mimetypeCandidate = MimeTypeUtil.getMimeType(ext);
				logger.trace("mimetypeCandidate: "+mimetypeCandidate);

				if (mimetypeCandidate!=null){
					//the extension is correct
					if (mimetypeCandidate.equalsIgnoreCase(mimeType)) {
						logger.trace("mimetypeCandidate: "+mimetypeCandidate+" == "+mimeType+" mimetype");

						return name;
					}
				}
			}
		} else logger.trace("no extension contained");

		String extension = MimeTypeUtil.getExtension(mimeType);
		logger.trace("extension: "+extension);

		if (extension == null) extension = "";
		else extension = "." + extension;

		if (name.toLowerCase().endsWith(extension.toLowerCase())) {
			logger.trace("extension already exist in name, returning name");
			return name;
		}

		logger.trace("returning "+name+extension);

		return name+extension;
	}
	
	/**
	 * Check if the content type is a zip type.
	 * @param contentType the content type to check.
	 * @return <code>true</code> if is a zip file, <code>false</code> otherwise.
	 */
	public static boolean isZipContentType(String contentType)
	{
		for (String zip_mimetype:ZIP_MIMETYPES) if (zip_mimetype.equals(contentType)) return true;
		return false;
	}


}
