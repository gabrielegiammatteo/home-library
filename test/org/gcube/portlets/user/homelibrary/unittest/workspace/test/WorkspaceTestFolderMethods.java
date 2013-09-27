package org.gcube.portlets.user.homelibrary.unittest.workspace.test;


import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongParentTypeException;
import org.gcube.portlets.user.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;
import static org.junit.Assert.*;

/**
 * Test all folder related methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceTestFolderMethods extends AbstractWorkspaceTest{

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTestFolderMethods(WorkspaceFactory factory) {
		super(factory);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateWorkspaceStringStringString() throws InternalErrorException, WorkspaceFolderAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "TestFolder100";
		String expectedDescription = "TestDescription";
		
		WorkspaceFolder folder = workspace.createFolder(expectedName, expectedDescription, root.getId());
		
		testItemCreation(folder, root, expectedName, expectedDescription, WorkspaceItemType.FOLDER);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateWorkspaceStringStringStringIllegalCharInNameArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
	
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "Test"+workspace.getPathSeparator()+"Workspace";
		String expectedDescription = "TestDescription";
		
		workspace.createFolder(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateWorkspaceStringStringStringNullDescriptionArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = "TestFolder101";
		String expectedDescription = null;
		
		workspace.createFolder(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateWorkspaceStringStringStringNullNameArgument() throws InternalErrorException, WorkspaceFolderAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
	
		WorkspaceFolder root = workspace.getRoot();
		
		String expectedName = null;
		String expectedDescription = "TestDescription";
		
		workspace.createFolder(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateWorkspaceStringStringStringWrongDestinationFolderId() throws InternalErrorException, WorkspaceFolderAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceFolderNotFoundException {
	
		String expectedName = "TestFolder102";
		String expectedDescription = "TestDescription";
		
		workspace.createFolder(expectedName, expectedDescription, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveChildSubTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder subTree = getFolderTree(root,"TestFolderTree6");
			
		workspace.removeChild(subTree.getId(), root.getId());
		
		checkSubTreeExistence(workspace, subTree);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException, ItemAlreadyExistException{
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder103", "A test Folder");
		
		workspace.removeChild(folder.getId(), null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException{
		WorkspaceFolder root = workspace.getRoot();
			
		workspace.removeChild(null, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveChildWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder104", "A test Folder");
		
		workspace.removeChild(folder.getId(), root.getId());
		
		assertEquals("Children list is not empty", 0, root.getChildren().size());
		
		assertFalse("Found removed item", workspace.exists(folder.getId()));
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveChildWrongFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException, ItemAlreadyExistException{
		WorkspaceFolder root = workspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder105", "A test Folder");
		
		workspace.removeChild(folder.getId(), "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveChildWrongItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException{
		WorkspaceFolder root = workspace.getRoot();
			
		workspace.removeChild("", root.getId());
	}

}
