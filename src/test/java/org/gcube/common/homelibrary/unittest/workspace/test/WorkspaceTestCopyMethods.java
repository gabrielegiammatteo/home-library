/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.common.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Test;

import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all copy methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceTestCopyMethods extends AbstractWorkspaceTest {

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTestCopyMethods(WorkspaceFactory factory) {
		super(factory);
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
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCopyStringStringAlreadyExistNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), root.getId());

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
		WorkspaceFolder root = ownerWorkspace.getRoot();

		WorkspaceFolder destination = root.createFolder("TestFolder11", "My Test Folder");
		
		WorkspaceItem item = TestDataFactory.getInstance().fillPDFDocuments(root, 1).get(0);

		WorkspaceItem copy = ownerWorkspace.copy(item.getId(), destination.getId());
		
		assertEquals("Wrong name", item.getName(), copy.getName());
		
		WorkspaceItem findCopy = ownerWorkspace.getItem(copy.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(item, copy, true);
		
		testRelationship(copy, destination);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy( java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringFolderItemWrongDestinationIdArgumentTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		List<PDFDocument> documents = TestDataFactory.getInstance().fillPDFDocuments(root, 2);
		
		WorkspaceItem item = documents.get(0);
		WorkspaceItem itemFolder = documents.get(1);
		
		ownerWorkspace.copy(item.getId(), itemFolder.getId());

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
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder12", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), null);

	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringNullItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		ownerWorkspace.copy(null, root.getId());

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
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCopyStringStringStringAlreadyExistNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolder13";
		
		WorkspaceFolder folder = root.createFolder("TestFolder13", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), expectedName, root.getId());

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
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		TestDataFactory.getInstance().fillPDFDocuments(root, 1);
		
		WorkspaceItem item = root.getChildren().get(0);
		
		String expectedName = "TestFolderItemClone";
		
		WorkspaceItem copy = ownerWorkspace.copy(item.getId(), expectedName, root.getId());
		
		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceItem findCopy = ownerWorkspace.getItem(copy.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(item, copy, false);
		
		testRelationship(copy, root);

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
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringFolderItemWrongDestinationIdArgumentTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolderItemCopy";
		
		TestDataFactory.getInstance().fillPDFDocuments(root, 2);
		
		WorkspaceItem item = root.getChildren().get(0);
		WorkspaceItem itemFolder = root.getChildren().get(1);
		
		ownerWorkspace.copy(item.getId(), expectedName, itemFolder.getId());

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
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringIllegalCharInCopyNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "Copy"+ownerWorkspace.getPathSeparator()+"TestFolder";
		
		WorkspaceFolder folder = root.createFolder("TestFolder14", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), expectedName, root.getId());

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
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringNullCopyName() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = null;
		
		WorkspaceFolder folder = root.createFolder("TestFolder15", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), expectedName, root.getId());

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
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolder";
		
		WorkspaceFolder folder = root.createFolder("TestFolder16", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), expectedName, null);

	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringNullItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolderCopy";
		
		ownerWorkspace.copy(null, expectedName, root.getId());

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
	public final void testCopyStringStringStringFolder() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder17", "A test Folder");
		
		String expectedName = "TestFolderCopy";
		
		WorkspaceItem copy = ownerWorkspace.copy(folder.getId(), expectedName, root.getId());
		
		assertNotNull("Copy null", copy);
		
		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceItem findCopy = ownerWorkspace.getItem(copy.getId());
		
		assertNotNull(findCopy);
		
		testCopy(folder, copy, false);
		
		testRelationship(copy, root);

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
	public final void testCopyStringStringStringFolderTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, IOException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = getFolderTree(root, "TestFolderTree2");
				
		String expectedName = "TestFolderCopy2";
		
		WorkspaceItem copy = ownerWorkspace.copy(folder.getId(), expectedName, root.getId());

		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceItem findClone = ownerWorkspace.getItem(copy.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testTreeRelationship(copy, root);
		
		testCopy(folder, copy, false);
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
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringFolderWrongDestinationIdArgumentTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolder";
		
		WorkspaceFolder folder = root.createFolder("TestFolder18", "A test Folder");
		
		WorkspaceItem wrongDestination = TestDataFactory.getInstance().fillPDFDocuments(root, 1).get(0);
		
		ownerWorkspace.copy(folder.getId(), expectedName, wrongDestination.getId());
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
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCopyStringStringStringWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolder";
		
		WorkspaceFolder folder = root.createFolder("TestFolder19", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), expectedName, "");

	}
	

	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCopyStringStringStringWrongItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();

		String expectedName = "TestFolderCopy";
		
		ownerWorkspace.copy("", expectedName, root.getId());
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
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder destinationFolder = root.createFolder("TestDestinationFolder", "A test Folder");
		
		WorkspaceFolder folder = root.createFolder("TestFolder20", "A test Folder");
		
		WorkspaceItem copy = ownerWorkspace.copy(folder.getId(), destinationFolder.getId());
		
		assertNotNull("Copy null", copy);
		
		assertEquals("Wrong name", folder.getName(), copy.getName());
		
		WorkspaceItem findCopy = ownerWorkspace.getItem(copy.getId());
		
		assertNotNull(findCopy);
		
		testCopy(folder, copy, true);
		
		testRelationship(copy, destinationFolder);

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
	public final void testCopyStringStringFolderTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, IOException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder destinationFolder = root.createFolder("TestDestinationFolder1", "A test folder");
		
		WorkspaceFolder folder = getFolderTree(root, "TestFolderTree3");
		
		WorkspaceItem copy = ownerWorkspace.copy(folder.getId(), destinationFolder.getId());

		assertEquals("Wrong name", folder.getName(), copy.getName());
		
		WorkspaceItem findClone = ownerWorkspace.getItem(copy.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(folder, copy, true);
		
		testTreeRelationship(copy, destinationFolder);
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
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringFolderWrongDestinationIdArgumentTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder21", "A test Folder");
		
		WorkspaceItem item = TestDataFactory.getInstance().fillPDFDocuments(root, 1).get(0);
		
		ownerWorkspace.copy(folder.getId(), item.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy( java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCopyStringStringWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceFolder folder = root.createFolder("TestFolder22", "A test Folder");
		
		ownerWorkspace.copy(folder.getId(), "");
	}
		
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCopyStringStringWrongItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException, WorkspaceFolderNotFoundException{
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		ownerWorkspace.copy("", root.getId());

	}	
	
}
