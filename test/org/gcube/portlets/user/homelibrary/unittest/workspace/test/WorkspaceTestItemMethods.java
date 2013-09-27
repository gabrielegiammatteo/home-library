/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.test;

import static org.junit.Assert.*;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongItemTypeException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all generic item methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#changeDescription(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceTestItemMethods extends AbstractWorkspaceTest{

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTestItemMethods(WorkspaceFactory factory) {
		super(factory);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testChangeDescriptionNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();

		WorkspaceFolder folder = root.createFolder("TestFolderTestItem", "A test folder");

		String expectedDescription = null;

		workspace.changeDescription(folder.getId(), expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testChangeDescriptionNullIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {

		String expectedDescription = "This is a test new description";

		workspace.changeDescription(null, expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testChangeDescriptionWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();

		WorkspaceFolder folder = root.createFolder("TestFolderTestItem1", "A test folder");

		String expectedDescription = "This is a test new description";
		
		
		workspace.changeDescription(folder.getId(), expectedDescription);
		folder = (WorkspaceFolder) workspace.getItem(folder.getId());
		assertEquals("Descriptions are different", expectedDescription, folder.getDescription());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testChangeDescriptionWrongIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {

		String expectedDescription = "This is a test new description";
		workspace.changeDescription("", expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExists() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		WorkspaceFolder root = workspace.getRoot();
		boolean exists = workspace.exists(root.getId());
		assertTrue("The root has not been found",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testExistsNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		workspace.getRoot();
		workspace.exists(null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExistsStringString() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongItemTypeException{
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder testFolder = root.createFolder("Test FolderTestItem2", "test folder");
		boolean exists = workspace.exists(testFolder.getName(), root.getId());
		assertTrue("The test folder has not been found",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testExistsStringStringFoldeIdNull() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		workspace.exists("", null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testExistsStringStringNameNull() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		WorkspaceFolder root = workspace.getRoot();
		workspace.exists(null, root.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testExistsStringStringWrongFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		workspace.exists("", "");
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExistsStringStringWrongName() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		WorkspaceFolder root = workspace.getRoot();
		boolean exists = workspace.exists("", root.getId());
		assertFalse("An item with wrong name has been found",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExistsWrongId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		boolean exists = workspace.exists("");
		assertFalse("Found item with wrong id",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetItemFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();

		TestDataFactory.getInstance().fillPDFDocuments(root, 1);

		WorkspaceItem item = root.getChildren().get(0);

		WorkspaceItem foundItem = workspace.getItem(item.getId());

		assertNotNull("Found item null",foundItem);

		testWorkspaceItemEquality(item, foundItem);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testGetItemNullItemId() throws ItemNotFoundException{
		workspace.getItem(null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetItemFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();

		WorkspaceFolder folder = root.createFolder("TestFolderTestItem3", "A test folder");

		WorkspaceItem foundItem = workspace.getItem(folder.getId());

		assertNotNull("Found item null",foundItem);

		testWorkspaceItemEquality(folder, foundItem);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testGetItemWrongItemId() throws ItemNotFoundException{
		workspace.getItem("");
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
	 * with null destination arguments.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testMoveDestinationNullArguments() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();

		WorkspaceFolder testFolder = root.createFolder("TestFolderTestItem0", "A test folder");

		workspace.moveItem(testFolder.getId(), null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getPath()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	/**public final void testGetPath() throws InternalErrorException
	{
		WorkspaceFolder root = workspace.getRoot();
		String expectedPath = workspace.getPathSeparator();

		assertEquals("Path not expected", expectedPath, root.getPath());
	}*/


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
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
		WorkspaceFolder testFolder = root.createFolder("TestFolderTestItem4", "This is a test folder");

		WorkspaceFolder destinationFolder = root.createFolder("TestDestinationFolder", "The destination Folder");

		//we put a pdf in the folder
		WorkspaceItem item = TestDataFactory.getInstance().fillPDFDocuments(testFolder, 1).get(0);

		testMoveItem(item, destinationFolder, workspace);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testMoveItemDuplicateItems() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		root.createFolder("TestItem", "TestItem duplicate");

		WorkspaceFolder testFolder = root.createFolder("TestFolderTestItem5", "This is a test folder");
		WorkspaceFolder item = testFolder.createFolder("TestItem", "TestItem");

		workspace.moveItem(item.getId(), root.getId());
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
	 * with null itemId arguments.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testMoveItemNullItemIdArguments() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();

		workspace.moveItem(null, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}, 
	 * for a workspace type;
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testMoveItemFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder testFolder = root.createFolder("TestFolderTestItem6", "This is a test folder");
		WorkspaceFolder item = testFolder.createFolder("TestItemNewItem", "TestItem");

		testMoveItem(item, root, workspace);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveItemNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		workspace.getRoot();
		workspace.removeItem(null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder folder = root.createFolder("TestFolderTestItem7", "A test folder");

		workspace.removeItem(folder.getId());

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
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveItemWrongItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		workspace.removeItem("");
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameIllegalCharInNameArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder folder = root.createFolder("TestFolderTestItem8", "A Test folder");

		String expectedName = "New"+workspace.getPathSeparator()+"TestFolder";
		workspace.renameItem(folder.getId(), expectedName);

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameItemNullItemId() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {

		String expectedName = "NewTestFolder";
		workspace.renameItem(null, expectedName);

	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameItemNullName() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder folder = root.createFolder("TestFolderTestItem9", "A Test Workspace");

		String expectedName = null;
		workspace.renameItem(folder.getId(), expectedName);

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
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
		WorkspaceFolder folder = root.createFolder("TestFolderTestItem10", "A Test Workspace");

		String expectedName = "NewTestFolder";
		workspace.renameItem(folder.getId(), expectedName);
		
		folder = (WorkspaceFolder) workspace.getItem(folder.getId());
		
		assertEquals("Item not renamed",expectedName, folder.getName());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testRenameItemFolderSameName() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		WorkspaceFolder root = workspace.getRoot();
		WorkspaceFolder folder = root.createFolder("TestFolderTestItem11", "A Test Workspace");

		String expectedName = "TestFolderTestItem11";
		workspace.renameItem(folder.getId(), expectedName);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRenameItemWrongItemId() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {

		String expectedName = "NewTestFolder";
		workspace.renameItem("", expectedName);
	}

}
