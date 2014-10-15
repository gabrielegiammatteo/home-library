/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace;

import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.testDocumentCreation;
import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.testItemCreation;
import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.testRelationship;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public abstract class FolderTest extends WorkspaceItemTest {
	
	protected WorkspaceFolder folder;

	/**
	 * @throws java.lang.Exception if an error occurs.
	 */
	@Before
	public abstract void setUpFolder() throws Exception;

	/**
	 * @throws java.lang.Exception if an error occurs.
	 */
	@After
	public abstract void tearDownFolder() throws Exception;

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#getType()}.
	 */
	@Test
	public final void testGetType() {
		WorkspaceItemType type = folder.getType();
		
		assertEquals("Wrong type", WorkspaceItemType.FOLDER, type);
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalFileItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile externalFile = folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		testItemCreation(externalFile, folder, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalFile.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_FILE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalFile.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileItemNullNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		String expectedName = null;
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileItemNullDescriptionArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		String expectedName = "TestExternalFile";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileItemNullDataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, null);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileItemIllegalCharInNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		String expectedName = "Test/ExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalFileItemDuplicateItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
			
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		folder.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}


	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalPDFFileItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile externalPDFFile = folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		testItemCreation(externalPDFFile, folder, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalPDFFile.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_PDF_FILE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalPDFFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalPDFFile.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileItemNullNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = null;
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileItemNullDescriptionArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileItemNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, null);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileItemIllegalCharInNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "Test/ExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalPDFFileItemDuplicateItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		folder.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalImageItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ExternalImage externalImage = folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		testItemCreation(externalImage, folder, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalImage.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_IMAGE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalImage.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalImage.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageItemNullNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = null;
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageItemNullDescriptionArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalImage";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageItemNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, null);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageItemIllegalCharInNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "Test/ExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalImageItemDuplicateItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		folder.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#createAnnotationItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	@Ignore
	public final void testCreateAnnotationItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateDocumentItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		Document document = folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		testDocumentCreation(document, folder, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = document.getFolderItemType();
		assertEquals("Wrong info object type", FolderItemType.DOCUMENT, folderItemType);
		
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, document.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), document.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = null;
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullDescriptionArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = null;
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullOidArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = null;
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullCollectionNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= null;
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullMetadataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullAnnotationsArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentItemNullDataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
			
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateDocumentItemDuplicateItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
			
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	
		folder.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);	
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateImageDocumentItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ImageDocument imageDocument = folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		testDocumentCreation(imageDocument, folder, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = imageDocument.getFolderItemType();
		assertEquals("Wrong info object type", FolderItemType.IMAGE_DOCUMENT, folderItemType);
		
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocument.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocument.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = null;
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullDescription() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = null;
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullOidArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = null;
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullCollectionNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= null;
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullMetadataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullAnnotationsArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentItemNullDataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateImageDocumentItemDuplicateItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		folder.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#createMetadataItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	@Ignore
	public final void testCreateMetadataItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreatePDFDocumentItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		PDFDocument pdfDocument = folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		testDocumentCreation(pdfDocument, folder, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = pdfDocument.getFolderItemType();
		assertEquals("Wrong info object type", FolderItemType.PDF_DOCUMENT, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocument.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocument.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = null;
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullDescriptionArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = null;
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullOidArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = null;
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullCollectionNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= null;
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullMetadataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullAnnotationsArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemNullDataArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
				
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentItemIllegalCharInNameArgument() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "Test/PDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceFolder#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreatePDFDocumentItemDuplicateItem() throws IOException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		folder.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#createQueryItem(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateQueryItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#createDocumentLinkItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateDocumentLinkItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#createImageDocumentLinkItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateImageDocumentLinkItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#createPDFDocumentLinkItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreatePDFDocumentLinkItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.FSWorkspaceFolder#move(org.gcube.common.homelibrary.home.workspace.WorkspaceFolder)}.
	 */
	@Test
	@Ignore
	public final void testMove() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceFolder#removeChild(org.gcube.common.homelibrary.home.workspace.WorkspaceItem)}.
	 */
	@Test
	@Ignore
	public final void testRemoveChild() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceItem#rename(java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testRename() {
		fail("Not yet implemented"); // TODO
	}
	

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		String expectedName = "TestFolder";
		String expectedDescription = "TestDescription";

		folder.createFolder(expectedName, expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateFolderNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{

		String expectedName = null;
		String expectedDescription = "TestDescription";

		folder.createFolder(expectedName, expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateFolderNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		String expectedName = "TestFolder";
		String expectedDescription = null;

		folder.createFolder(expectedName, expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateFolderIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		String expectedName = "Test/Folder";
		String expectedDescription = "TestDescription";

		folder.createFolder(expectedName, expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateFolderDuplicateFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		String expectedName = "TestFolder";
		String expectedDescription = "TestDescription";

		folder.createFolder(expectedName, expectedDescription);
		folder.createFolder(expectedName, expectedDescription);
	}


	/**
	 * 
	 */
	@Test
	@Ignore
	public void testGetChildren() {
		fail("Not yet implemented"); // TODO

	}

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.WorkspaceItem#move(org.gcube.common.homelibrary.home.workspace.WorkspaceFolder)}, 
	 * for a workspace type;
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testMoveItemFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder testFolder = root.createFolder("TestFolder", "This is a test folder");

		folder.move(testFolder);

		//the item parent have not to be null
		assertNotNull("The moved item parent is null",folder.getParent());

		testRelationship(folder, testFolder);

	}

}
