/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all document creation methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceTestCreateDocumentMethods extends AbstractWorkspaceTest{

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTestCreateDocumentMethods(WorkspaceFactory factory) {
		super(factory);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolderDocument", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		Document document = workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		testDocumentCreation(document, folder, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = document.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.DOCUMENT, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, document.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), document.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateDocumentDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder22", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder23", "A test folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"Document";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullAnnotationArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder24", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder25", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= null;
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder26", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = null;
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullDestinationFolderArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullImageDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder27", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder28", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder29", "A test folder");
		
		String expectedName = null;
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder30", "A test folder");
		
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
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateDocumentWrongDestinationFolderIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateDocumentWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder31", "A test folder");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ImageDocument imageDocument = workspace.createImageDocument("testItem", "An item description", getID(), expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, imageDocument.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateImageDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder32", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ImageDocument imageDocument = workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		testDocumentCreation(imageDocument, folder, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = imageDocument.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.IMAGE_DOCUMENT, folderItemType);
		
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocument.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocument.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method. 
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateImageDocumentDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder33", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder34", "A test folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"ImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullAnnotationArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder35", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder36", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= null;
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder37", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = null;
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullDestinationFolderArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullImageDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder38", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder39", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder40", "A test folder");
		
		String expectedName = null;
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder41", "A test folder");
		
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
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateImageDocumentWrongDestinationFolderIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateImageDocumentWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder42", "A test folder");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ImageDocument imageDocument = workspace.createImageDocument("testItem", "An item description", getID(), expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, imageDocument.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreatePDFDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder43", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		PDFDocument pdfDocument = workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		testDocumentCreation(pdfDocument, folder, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = pdfDocument.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.PDF_DOCUMENT, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocument.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocument.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreatePDFDocumentDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder44", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder45", "A test folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"PDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullAnnotationArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder46", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder47", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= null;
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder48", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = null;
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullDestinationFolderArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder49", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder50", "A test folder");
		
		String expectedName = null;
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder51", "A test folder");
		
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
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullPDFDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder52", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreatePDFDocumentWrongDestinationFolderIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreatePDFDocumentWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder53", "A test folder");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		PDFDocument PDFDocument = workspace.createPDFDocument("testItem", "An item description", getID(), expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folder.getId());
		
		workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, PDFDocument.getId());
	}
}
