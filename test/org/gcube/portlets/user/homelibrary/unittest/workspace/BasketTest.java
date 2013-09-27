/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.testDocumentCreation;
import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.testItemCreation;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.BasketItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalFile;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalImage;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalPDFFile;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.Document;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.ImageDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.InfoObjectType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.PDFDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public abstract class BasketTest extends WorkspaceFolderTest {
	
	protected Basket basket;

	/**
	 * @throws java.lang.Exception if an error occurs.
	 */
	@Before
	public abstract void setUpBasket() throws Exception;

	/**
	 * @throws java.lang.Exception if an error occurs.
	 */
	@After
	public abstract void tearDownBasket() throws Exception;

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#getType()}.
	 */
	@Test
	public final void testGetType() {
		WorkspaceAreaItemType type = basket.getType();
		
		assertEquals("Wrong type", WorkspaceAreaItemType.BASKET, type);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		ExternalFile externalFile = basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		testItemCreation(externalFile, basket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalFile.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_FILE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalFile.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, null);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		basket.createExternalFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		ExternalPDFFile externalPDFFile = basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		testItemCreation(externalPDFFile, basket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalPDFFile.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_PDF_FILE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalPDFFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalPDFFile.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileItemNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, null);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalPDFFileItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		basket.createExternalPDFFileItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		ExternalImage externalImage = basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		testItemCreation(externalImage, basket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalImage.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_IMAGE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalImage.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalImage.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageItemNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createExternalImageItem(java.lang.String, java.lang.String, java.io.InputStream)}.
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
		
		basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
		
		basket.createExternalImageItem(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData));
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.basket.FSBasket#createAnnotationItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	@Ignore
	public final void testCreateAnnotationItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		Document document = basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		testDocumentCreation(document, basket, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
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
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
			
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	
		basket.createDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);	
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		ImageDocument imageDocument = basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		testDocumentCreation(imageDocument, basket, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
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
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createImageDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		basket.createImageDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.basket.FSBasket#createMetadataItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map)}.
	 */
	@Test
	@Ignore
	public final void testCreateMetadataItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		PDFDocument pdfDocument = basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		testDocumentCreation(pdfDocument, basket, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
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
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
				
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, null, expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket#createPDFDocumentItem(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
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
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
		
		basket.createPDFDocumentItem(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName);
	}
	
	

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.basket.FSBasket#createQueryItem(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateQueryItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.basket.FSBasket#createDocumentLinkItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateDocumentLinkItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.basket.FSBasket#createImageDocumentLinkItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateImageDocumentLinkItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.basket.FSBasket#createPDFDocumentLinkItem(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreatePDFDocumentLinkItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.FSWorkspaceFolder#move(org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder)}.
	 */
	@Test
	@Ignore
	public final void testMove() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.FSWorkspaceFolder#removeChild(org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItem)}.
	 */
	@Test
	@Ignore
	public final void testRemoveChild() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.FSWorkspaceAreaItem#rename(java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testRename() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder#listFolders()}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void listFolders() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {

		List<WorkspaceFolder> folders = basket.listFolders();

		assertEquals("Found a folder in basket", 0, folders.size());
	}

}
