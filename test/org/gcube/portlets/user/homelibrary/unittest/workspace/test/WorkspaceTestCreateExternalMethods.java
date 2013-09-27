/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.portlets.user.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all external item creation methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceTestCreateExternalMethods extends AbstractWorkspaceTest{

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTestCreateExternalMethods(WorkspaceFactory factory) {
		super(factory);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder60", "A test folder");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile externalFile = workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());
		
		testItemCreation(externalFile, folder, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalFile.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_FILE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalFile.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalFileDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder61", "A test folder");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder62", "A test folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"ExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder63", "A test folder");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, null, folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder64", "A test folder");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalFileNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder65", "A test folder");
		
		String expectedName = null;
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateExternalFileWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalFileWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder66", "A test folder");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile testItem = workspace.createExternalFile("TestItem", "A test Item", "testMimeType", new FileInputStream(fileData), folder.getId());
		
		workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), testItem.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalImage() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder67", "A test folder");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ExternalImage externalImage = workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());
		
		testItemCreation(externalImage, folder, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalImage.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_IMAGE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalImage.getLength());
		

		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalImage.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalImageDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder68", "A test folder");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder69", "A test folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"ExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}

	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder70", "A test folder");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, null, folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder71", "A test folder");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalImageNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder72", "A test folder");
		
		String expectedName = null;
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateExternalImageWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalImageWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder73", "A test folder");
		
		String expectedName = "TestExternalImage";
		String expectedDescription = "Test external image description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ExternalImage testItem = workspace.createExternalImage("TestItem", "A test Item", "testMimeType", new FileInputStream(fileData), folder.getId());
		
		workspace.createExternalImage(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), testItem.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalPDFFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder74", "A test folder");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile externalPDFFile = workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());
		
		testItemCreation(externalPDFFile, folder, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalPDFFile.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_PDF_FILE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalPDFFile.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalPDFFile.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateExternalPDFFileDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder75", "A test folder");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder76", "A test folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"ExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullDataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder77", "A test folder");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, null, folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder78", "A test folder");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = null;
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateExternalPDFFileNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder80", "A test folder");
		
		String expectedName = null;
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateExternalPDFFileWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
	
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateExternalPDFFileWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WorkspaceFolderNotFoundException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder81", "A test folder");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile testItem = workspace.createExternalPDFFile("TestItem", "A test Item", "testMimeType", new FileInputStream(fileData), folder.getId());
		
		workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), testItem.getId());

	}
}
