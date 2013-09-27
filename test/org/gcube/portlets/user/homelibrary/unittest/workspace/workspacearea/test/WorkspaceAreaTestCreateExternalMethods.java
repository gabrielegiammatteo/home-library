/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.BasketItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalFile;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalImage;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalPDFFile;
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
 * Test all external item creation methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceAreaTestCreateExternalMethods extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTestCreateExternalMethods(WorkspaceAreaFactory factory) {
		super(factory);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile externalFile = workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		testItemCreation(externalFile, folderBasket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalFile.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_FILE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalFile.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalFileDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"ExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, null, folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = null;
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreateExternalFileWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalFileWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile testItem = workspaceArea.createExternalFile("TestItem", "A test Item", "testMimeType", new FileInputStream(fileData), folderBasket.getId());
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), testItem.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalFileWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalImage() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ExternalImage externalImage = workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		testItemCreation(externalImage, folderBasket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalImage.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_IMAGE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalImage.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalImage.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalImageDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"ExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}

	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, null, folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = null;
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreateExternalImageWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalImageWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ExternalImage testItem = workspaceArea.createExternalImage("TestItem", "A test Item", "testMimeType", new FileInputStream(fileData), folderBasket.getId());
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), testItem.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalImageWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspaceArea.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalPDFFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile externalPDFFile = workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		testItemCreation(externalPDFFile, folderBasket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalPDFFile.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_PDF_FILE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalPDFFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalPDFFile.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalPDFFileDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"ExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, null, folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = null;
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreateExternalPDFFileWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalPDFFileWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile testItem = workspaceArea.createExternalPDFFile("TestItem", "A test Item", "testMimeType", new FileInputStream(fileData), folderBasket.getId());
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), testItem.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalPDFFileWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), root.getId());

	}	
}
