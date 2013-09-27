/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.testCopy;
import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.testRelationship;
import static org.junit.Assert.*;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.junit.Test;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public abstract class WorkspaceItemTest {

	protected Workspace workspace;
	protected WorkspaceItem item;

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#cloneItem()}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException{
		
		String expectedName = "TestItemClone";
		
		WorkspaceItem clone = item.cloneItem(expectedName);
		
		assertEquals("Wrong name", expectedName, clone.getName());
			
		WorkspaceItem findClone = workspace.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(item, clone, false);
		
		testRelationship(clone, item.getParent());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#cloneItem()}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		String expectedName = null;
		
		item.cloneItem(expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#cloneItem()}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{
		String expectedName = "Test"+workspace.getPathSeparator()+"WorkspaceClone";
		
		item.cloneItem(expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#cloneItem()}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCloneItemDuplicateItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException{

		String expectedName = item.getName();
		
		item.cloneItem(expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#move(org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder)}, 
	 * with null destination arguments.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testMoveDestinationNullArguments() throws InternalErrorException, WrongDestinationException, InsufficientPrivilegesException, ItemAlreadyExistException{
		item.move(null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#move(org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder)}, 
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testMoveItemDuplicateItems() throws InternalErrorException, WrongDestinationException, InsufficientPrivilegesException, ItemAlreadyExistException {
		item.move(item.getParent());
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#rename(java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public void testRename() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		String actualName = item.getName();
		String newName = actualName+"new";
		
		item.rename(newName);
		
		actualName = item.getName();
		
		assertEquals(newName, actualName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#rename(java.lang.String)}
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameItemNullName() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		item.rename(null);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#rename(java.lang.String)}
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException {
		String expectedName = "New"+workspace.getPathSeparator()+"TestFolder";
		item.rename(expectedName);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.AbstractWorkspaceItem#removeChild(org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullChildArgument() throws InternalErrorException, InsufficientPrivilegesException {
		item.removeChild(null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.AbstractWorkspaceItem#remove()}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemove() throws InternalErrorException, InsufficientPrivilegesException {
		
		WorkspaceFolder parent = item.getParent();
		
		item.remove();
		
		try{
			workspace.getItem(item.getId());
		}catch(ItemNotFoundException infe)
		{
			return;
		}
		
		fail("Item not removed");
		
		assertEquals("Wrong number of children", 0, parent.getChildren().size());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceItem#getPath()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	public final void testGetPath() throws InternalErrorException
	{
		assertNotNull("Path null", item.getPath());
	}

}
