/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all cloning methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceTestCloneMethods extends AbstractWorkspaceTest{

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTestCloneMethods(WorkspaceFactory factory) {
		super(factory);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCloneItemFolderItem() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		TestDataFactory.getInstance().fillPDFDocuments(root, 1);
		
		WorkspaceItem item = root.getChildren().get(0);
		System.err.println("-------------- ITEM " + item.getType());
		
		String expectedName = "TestItemClone";

		WorkspaceItem clone = workspace.cloneItem(item.getId(), expectedName);
		System.err.println("------------- CLONE " + clone.getType());
		
		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceItem findClone = workspace.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(item, clone, false);
		
		testRelationship(item, root);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCloneItemDuplicateItem() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder", "A test Folder");
		
		String expectedName = "TestFolder";
		
		workspace.cloneItem(folder.getId(), expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemIllegalCharInNameArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder1", "A test Folder");
		
		String expectedName = "Test"+workspace.getPathSeparator()+"WorkspaceClone";
		
		workspace.cloneItem(folder.getId(), expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemNullItemIdArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		String expectedName = "TestFolderClone";
		
		workspace.cloneItem(null, expectedName);

	}
	


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemNullNameArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder2", "A test Folder");
		
		String expectedName = null;
		
		workspace.cloneItem(folder.getId(), expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemFolder() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder3", "A test Folder");
		
		String expectedName = "TestFolderClone";
		
		WorkspaceItem clone = workspace.cloneItem(folder.getId(), expectedName);

		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceItem findClone = workspace.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(folder, clone, false);
		
		testRelationship(clone, root);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemFolderTree() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, IOException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = getFolderTree(root,"TestFolderTree");

		String expectedName = "TestFolderClone1";
		
		WorkspaceItem clone = workspace.cloneItem(folder.getId(), expectedName);

		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceItem findClone = workspace.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(folder, clone, false);
		
		testTreeRelationship(clone, root);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCloneItemWrongItemIdArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		String expectedName = "TestFolderClone";
		
		workspace.cloneItem("", expectedName);
	}	
	
}
