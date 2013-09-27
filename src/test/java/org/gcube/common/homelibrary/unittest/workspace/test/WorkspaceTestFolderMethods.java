package org.gcube.common.homelibrary.unittest.workspace.test;


import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongParentTypeException;
import org.gcube.common.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.common.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.*;
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
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
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
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestFolder100";
		String expectedDescription = "TestDescription";
		
		WorkspaceFolder folder = ownerWorkspace.createFolder(expectedName, expectedDescription, root.getId());
		
		testItemCreation(folder, root, expectedName, expectedDescription, WorkspaceItemType.FOLDER);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
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
	
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "Test"+ownerWorkspace.getPathSeparator()+"Workspace";
		String expectedDescription = "TestDescription";
		
		ownerWorkspace.createFolder(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
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
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestFolder101";
		String expectedDescription = null;
		
		ownerWorkspace.createFolder(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
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
	
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = null;
		String expectedDescription = "TestDescription";
		
		ownerWorkspace.createFolder(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}.
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
		
		ownerWorkspace.createFolder(expectedName, expectedDescription, "");
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
	public final void testRemoveChildSubTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder subTree = getFolderTree(root,"TestFolderTree6");
			
		ownerWorkspace.removeChild(subTree.getId(), root.getId());
		
		checkSubTreeExistence(ownerWorkspace, subTree);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException, ItemAlreadyExistException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder103", "A test Folder");
		
		ownerWorkspace.removeChild(folder.getId(), null);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
			
		ownerWorkspace.removeChild(null, root.getId());
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
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder104", "A test Folder");
		
		ownerWorkspace.removeChild(folder.getId(), root.getId());
		
		assertEquals("Children list is not empty", 0, root.getChildren().size());
		
		assertFalse("Found removed item", ownerWorkspace.exists(folder.getId()));
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveChildWrongFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException, ItemAlreadyExistException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder105", "A test Folder");
		
		ownerWorkspace.removeChild(folder.getId(), "");
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveChildWrongItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
			
		ownerWorkspace.removeChild("", root.getId());
	}

}
