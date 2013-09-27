/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItem;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.BasketAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.AbstractWorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.WorkspaceAreaFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all cloning methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceAreaTestCloneMethods extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTestCloneMethods(WorkspaceAreaFactory factory) {
		super(factory);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemBasket() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		String expectedName = "TestBasketClone";
		
		WorkspaceAreaItem clone = workspaceArea.cloneItem(basket.getId(), expectedName);
		
		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(basket, clone, false);
		
		testRelationship(basket, root);

		
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemBasketItem() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
	
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		WorkspaceAreaItem item = basket.getChildren().get(0);
		
		String expectedName = "TestItemClone";

		WorkspaceAreaItem clone = workspaceArea.cloneItem(item.getId(), expectedName);
		
		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(item, clone, false);
		
		testRelationship(item, basket);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCloneItemDuplicateItem() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		String expectedName = "TestWorkspace";
		
		workspaceArea.cloneItem(workspace.getId(), expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemIllegalCharInNameArgument() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"WorkspaceClone";
		
		workspaceArea.cloneItem(workspace.getId(), expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemNullItemIdArgument() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		String expectedName = "TestWorkspaceClone";
		
		workspaceArea.cloneItem(null, expectedName);

	}
	


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCloneItemNullNameArgument() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		String expectedName = null;
		
		workspaceArea.cloneItem(workspace.getId(), expectedName);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemWorkspace() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		String expectedName = "TestWorkspaceClone";
		
		WorkspaceAreaItem clone = workspaceArea.cloneItem(workspace.getId(), expectedName);

		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(workspace, clone, false);
		
		testRelationship(clone, root);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCloneItemWorkspaceTree() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException, IOException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = getWorkspaceTree(root);

		String expectedName = "TestWorkspaceClone";
		
		WorkspaceAreaItem clone = workspaceArea.cloneItem(workspace.getId(), expectedName);

		assertEquals("Wrong name", expectedName,clone.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(clone.getId());
		
		assertNotNull("Find clone null",findClone);
		
		testCopy(workspace, clone, false);
		
		testTreeRelationship(clone, root);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCloneItemWrongItemIdArgument() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, BasketAlreadyExistException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		String expectedName = "TestWorkspaceClone";
		
		workspaceArea.cloneItem("", expectedName);
	}	
	
}
