/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.BasketItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.Document;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.ImageDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.InfoObjectType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.PDFDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.BasketNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.AbstractWorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.WorkspaceAreaFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all document creation methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceAreaTestCreateDocumentMethods extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTestCreateDocumentMethods(WorkspaceAreaFactory factory) {
		super(factory);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		Document document = workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		testDocumentCreation(document, folderBasket, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		BasketItemType basketItemType = document.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = document.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.DOCUMENT, infoObjectType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, document.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), document.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateDocumentDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"Document";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullAnnotationArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullDestinationBasketArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullImageDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateDocumentNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreateDocumentWrongDestinationBasketIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateDocumentWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		ImageDocument imageDocument = workspaceArea.createImageDocument("testItem", "An item description", getID(), expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, imageDocument.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateDocumentWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
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
		
		workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateImageDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		ImageDocument imageDocument = workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		testDocumentCreation(imageDocument, folderBasket, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		BasketItemType basketItemType = imageDocument.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = imageDocument.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.IMAGE_DOCUMENT, infoObjectType);
		
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocument.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocument.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method. 
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateImageDocumentDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"ImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullAnnotationArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullDestinationBasketArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullImageDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateImageDocumentNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreateImageDocumentWrongDestinationBasketIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateImageDocumentWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		ImageDocument imageDocument = workspaceArea.createImageDocument("testItem", "An item description", getID(), expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, imageDocument.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateImageDocumentWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
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
		
		workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
	}
		
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreatePDFDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		PDFDocument pdfDocument = workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		testDocumentCreation(pdfDocument, folderBasket, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		BasketItemType basketItemType = pdfDocument.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = pdfDocument.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.PDF_DOCUMENT, infoObjectType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocument.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocument.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreatePDFDocumentDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"PDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullAnnotationArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = null;
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullDestinationBasketArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = null;
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreatePDFDocumentNullPDFDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = getID();
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreatePDFDocumentWrongDestinationBasketIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreatePDFDocumentWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
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
		
		PDFDocument PDFDocument = workspaceArea.createPDFDocument("testItem", "An item description", getID(), expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, PDFDocument.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreatePDFDocumentWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
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
		
		workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
	}	
	
}
