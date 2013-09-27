/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongParentTypeException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public abstract class WorkspacePersistenceTest {
	
	protected Workspace workspace;

	/**
	 * Setup the workspace are before each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@Before
	public abstract void setupWorkspace() throws InternalErrorException;
	
	/**
	 * Clean the workspace after each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@After
	public abstract void cleanWorkspace() throws InternalErrorException; 

	/**
	 * Close and reopen a workspace.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	public abstract void reloadWokspaceArea() throws InternalErrorException; 
	
	/**
	 * Test workspace persistence.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testFolderPersistence() throws InternalErrorException, WorkspaceFolderAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "TestFolder";
		String expectedDescription = "TestDescription";
		
		WorkspaceFolder folder = workspace.createFolder(expectedName, expectedDescription, root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem itemB = workspace.getItem(folder.getId());
		
		testWorkspaceItemEquality(folder, itemB);
	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
	 * for a workspace type item;
	 * @throws InsufficientPrivilegesException  if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException  if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException  if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException  if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException  if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testMoveItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder testFolder = root.createFolder("TestFolder", "This is a test folder");
		WorkspaceFolder item = testFolder.createFolder("TestItem", "TestItem");
		
		testMoveItem(item, root);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
	 * for a FolderItem type item;
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testMoveItemFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder testFolder = root.createFolder("TestFolder", "This is a test folder");
		
		WorkspaceFolder destinationFolder = root.createFolder("TestDestinationFolder", "The destination Folder");
		
		//we put a pdf in the folder
		TestDataFactory.getInstance().fillPDFDocuments(testFolder, 1);
		
		WorkspaceItem item = testFolder.getChildren().get(0);
		
		testMoveItem(item, destinationFolder);
		
	}
	
	/**
	 * Generic test for move item method.
	 * @param item
	 * @param destination
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	protected final void testMoveItem(WorkspaceItem item, WorkspaceFolder destination) throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		
		WorkspaceFolder parent = item.getParent();
		
		workspace.moveItem(item.getId(), destination.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem movedItem = workspace.getItem(item.getId());
		
		//the item parent have not to be null
		assertNotNull("The moved item parent is null",movedItem.getParent());
		//the item old parent have to be without children
		assertEquals("Children founds on old parent", 0, parent.getChildren().size());
		
		testRelationship(movedItem, destination);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRenameItemFolder() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder folder = root.createFolder("FSWorkspace", "A Test Workspace");
		
		String expectedName = "NewTestWorkspace";
		workspace.renameItem(folder.getId(), expectedName);
		
		reloadWokspaceArea();
		
		WorkspaceItem renamedItem = workspace.getItem(folder.getId());
		
		
		assertEquals("Item not renamed",expectedName, renamedItem.getName());
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemWorkspace() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("FSWorkspace", "A test Workspace");
		
		String expectedName = "TestWorkspaceClone";
		
		WorkspaceItem clone = workspace.cloneItem(folder.getId(), expectedName);

		reloadWokspaceArea();
		
		WorkspaceItem clonedItem = workspace.getItem(clone.getId());
		
		root = workspace.getRoot();
		WorkspaceItem workspaceClone = workspace.getItem(folder.getId());

		assertEquals("Wrong name", expectedName,clonedItem.getName());
		
		testCopy(workspaceClone, clonedItem, false);
		
		testRelationship(clonedItem, root);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder folder = root.createFolder("TestWorkspace", "A test workspace");
		
		workspace.removeItem(folder.getId());
		
		reloadWokspaceArea();
		
		try{
			workspace.getItem(folder.getId());
		}catch(ItemNotFoundException infe)
		{
			return;
		}
		
		fail("Item not removed");
		
		assertEquals("Wrong number of children", 0, root.getChildren().size());
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder", "A test Folder");
		
		reloadWokspaceArea();
		
		WorkspaceItem foundItem = workspace.getItem(folder.getId());
	
		assertNotNull("Found item null",foundItem);
		
		testWorkspaceItemEquality(folder, foundItem);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveChildWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder", "A test Folder");
		
		workspace.removeChild(folder.getId(), root.getId());
		
		reloadWokspaceArea();
		
		root = workspace.getRoot();
		
		assertEquals("Children list is not empty", 0, root.getChildren().size());
		
		try{
			workspace.getItem(folder.getId());
		}catch(ItemNotFoundException infe){
			return;
		}
		
		fail("Child not removed");
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder", "A test Folder");
		
		String expectedName = "TestFolderCopy";
		
		WorkspaceItem copy = workspace.copy(folder.getId(), expectedName, root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem copyItem = workspace.getItem(copy.getId());
		
		root = workspace.getRoot();
		WorkspaceItem workspaceOriginal = workspace.getItem(folder.getId());
		
		assertNotNull("Copy null", copyItem);
		
		assertEquals("Wrong name", expectedName, copy.getName());
	
		testCopy(workspaceOriginal, copyItem, false);
		
		testRelationship(copyItem, root);

	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		TestDataFactory.getInstance().fillPDFDocuments(root, 1);
		
		WorkspaceItem item = root.getChildren().get(0);
		
		String expectedName = "TestFolderItemClone";
		
		WorkspaceItem copy = workspace.copy(item.getId(), expectedName, root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem copyItem = workspace.getItem(copy.getId());
		
		root = workspace.getRoot();
		WorkspaceItem itemOriginal = workspace.getItem(item.getId());
		
		assertEquals("Wrong name", expectedName,copyItem.getName());
		
		WorkspaceItem findCopy = workspace.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(itemOriginal, copyItem, false);
		
		testRelationship(copyItem, root);

	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringWorkspaceTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, IOException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = getFolderTree(root, "TestFolderTree4");
				
		String expectedName = "TestFolderCopy";
		
		WorkspaceItem copy = workspace.copy(folder.getId(), expectedName, root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem copyItem = workspace.getItem(copy.getId());
		
		root = workspace.getRoot();
		WorkspaceItem workspaceOriginal = workspace.getItem(folder.getId());

		assertEquals("Wrong name", expectedName,copyItem.getName());
		
		WorkspaceItem findClone = workspace.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(workspaceOriginal, copyItem, false);
		
		testTreeRelationship(copyItem, root);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCopyStringStringWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder workspaceFolder = root.createFolder("TestWorkspaceFolder", "A test Workspace folder");
		
		WorkspaceFolder folder = root.createFolder("TestFolder", "A test Folder");
		
		WorkspaceItem copy = workspace.copy(folder.getId(), workspaceFolder.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem copyItem = workspace.getItem(copy.getId());
		
		root = workspace.getRoot();
		WorkspaceItem workspaceFolderReloaded = workspace.getItem(workspaceFolder.getId());
		
		WorkspaceItem workspaceOriginal = workspace.getItem(folder.getId());
		
		assertNotNull("Copy null", copyItem);
		
		assertEquals("Wrong name", workspaceOriginal.getName(), copy.getName());
	
		testCopy(workspaceOriginal, copyItem, true);
		
		testRelationship(copyItem, workspaceFolderReloaded);

	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCopyStringStringFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder testDestination = root.createFolder("TestFolder", "My Test Folder");
		
		WorkspaceItem item = TestDataFactory.getInstance().fillPDFDocuments(root, 1).get(0);
		
		WorkspaceItem copy = workspace.copy(item.getId(), testDestination.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem copyItem = workspace.getItem(copy.getId());
		
		root = workspace.getRoot();
		WorkspaceItem folderReloaded = workspace.getItem(testDestination.getId());
		WorkspaceItem itemOriginal = workspace.getItem(item.getId());
		
		assertEquals("Wrong name", itemOriginal.getName(), copyItem.getName());
		
		WorkspaceItem findCopy = workspace.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(itemOriginal, copyItem, true);
		
		testRelationship(copyItem, folderReloaded);

	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringWorkspaceTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, IOException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder workspaceFolder = root.createFolder("TestWorkspaceFolder", "A test Workspace folder");
		
		WorkspaceFolder folder = getFolderTree(root, "TestFolderTree5");
		
		WorkspaceItem copy = workspace.copy(folder.getId(), workspaceFolder.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem copyItem = workspace.getItem(copy.getId());
		
		root = workspace.getRoot();
		WorkspaceItem workspaceFolderReloaded = workspace.getItem(workspaceFolder.getId());
		WorkspaceItem workspaceOriginal = workspace.getItem(folder.getId());

		assertEquals("Wrong name", workspaceOriginal.getName(), copyItem.getName());
		
		WorkspaceItem findClone = workspace.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(workspaceOriginal, copyItem, true);
		
		testTreeRelationship(copyItem, workspaceFolderReloaded);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCreateExternalFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile externalFile = workspace.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem folderReloaded = workspace.getItem(root.getId());
		ExternalFile externalFileReloaded = (ExternalFile) workspace.getItem(externalFile.getId());
		
		testWorkspaceItemEquality(externalFile, externalFileReloaded);
		
		testItemCreation(externalFile, folderReloaded, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalFileReloaded.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_FILE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalFileReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalFileReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCreateImageDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
	
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
		
		ImageDocument imageDocument = workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
		
		reloadWokspaceArea();
		root = workspace.getRoot();
		ImageDocument imageDocumentReloaded = (ImageDocument) workspace.getItem(imageDocument.getId());
		WorkspaceItem folderReloaded =  workspace.getItem(root.getId());
		
		testWorkspaceItemEquality(imageDocument, imageDocumentReloaded);
		
		testDocumentCreation(imageDocumentReloaded, folderReloaded, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = imageDocumentReloaded.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.IMAGE_DOCUMENT, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocumentReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocumentReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
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
		
		Document document = workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
		
		reloadWokspaceArea();
		root = workspace.getRoot();
		Document documentReloaded = (Document) workspace.getItem(document.getId());
		WorkspaceItem folderReloaded =  workspace.getItem(root.getId());
	
		
		testDocumentCreation(documentReloaded, folderReloaded, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = documentReloaded.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.DOCUMENT, folderItemType);
		
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, documentReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), documentReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCreateExternalPDFFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile externalPDFFile = workspace.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceItem folderReloaded = workspace.getItem(root.getId());
		ExternalPDFFile externalPDFFileReloaded = (ExternalPDFFile) workspace.getItem(externalPDFFile.getId());
		
		testWorkspaceItemEquality(externalPDFFile, externalPDFFileReloaded);
	
		testItemCreation(externalPDFFileReloaded, folderReloaded, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		FolderItemType folderItemType = externalPDFFileReloaded.getFolderItemType();
		
		assertEquals("Wrong folder item type", FolderItemType.EXTERNAL_PDF_FILE, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalPDFFileReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalPDFFileReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreatePDFDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
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
		
		PDFDocument pdfDocument = workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
		
		reloadWokspaceArea();
		root = workspace.getRoot();
		PDFDocument pdfDocumentReloaded = (PDFDocument) workspace.getItem(pdfDocument.getId());
		WorkspaceItem folderReloaded =  workspace.getItem(root.getId());
		
		testWorkspaceItemEquality(pdfDocument, pdfDocumentReloaded );
	
		
		testDocumentCreation(pdfDocumentReloaded , folderReloaded, expectedName, expectedDescription, expectedMimeType, WorkspaceItemType.FOLDER_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		FolderItemType folderItemType = pdfDocumentReloaded .getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.PDF_DOCUMENT, folderItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocumentReloaded .getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocumentReloaded .getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method. 
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCreateMetadata() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		Metadata metadata = workspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, root.getId());
		
		reloadWokspaceArea();
		root = workspace.getRoot();
		Metadata metadataReloaded = (Metadata) workspace.getItem(metadata.getId());
		WorkspaceItem folderReloaded =  workspace.getItem(root.getId());
		
		testWorkspaceItemEquality(metadata, metadataReloaded);
	
		testItemCreation(metadataReloaded, folderReloaded, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		assertEquals("Different oid", expectedOid, metadataReloaded.getURI());
		assertEquals("Different schema", expectedSchema, metadataReloaded.getSchema());
		assertEquals("Different metadata", expectedMetadata, metadataReloaded.getData());
		assertEquals("Different collection name", expectedCollectionName, metadataReloaded.getCollectionName());
			
		FolderItemType folderItemType = metadataReloaded.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.METADATA, folderItemType);
		
		
		long expectedLength = expectedMetadata.length();
		assertEquals("Differents length", expectedLength, metadataReloaded.getLength());
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemSameOidImageDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
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
		
		ImageDocument imageDocument = workspace.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
		
		String expectedName2 = "TestImageDocument2";
		ImageDocument imageDocument2 = workspace.createImageDocument(expectedName2, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());

		imageDocument.remove();
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocument2.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocument2.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemSameOidPDFDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
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
		
		PDFDocument pdfDocument = workspace.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
			
		String expectedName2 = "TestPDFDocument2";
		PDFDocument pdfDocument2 = workspace.createPDFDocument(expectedName2, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
				
		pdfDocument.remove();
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocument2.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocument2.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemSameOidDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, ItemNotFoundException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
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
		
		Document document = workspace.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
		
		String expectedName2 = "TestDocument2";
		Document document2 = workspace.createDocument(expectedName2, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, root.getId());
		
		
		document.remove();
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, document2.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), document2.getData());
		assertTrue("Different content", dataEquals);
		
	}


}
