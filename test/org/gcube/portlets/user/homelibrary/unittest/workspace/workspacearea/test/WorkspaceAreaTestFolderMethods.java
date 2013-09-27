package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;


import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongParentTypeException;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.AbstractWorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.WorkspaceAreaFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;
import static org.junit.Assert.*;

/**
 * Test all folder related methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class WorkspaceAreaTestFolderMethods extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTestFolderMethods(WorkspaceAreaFactory factory) {
		super(factory);
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestBasket";
		String expectedDescription = "TestDescription";
		
		Basket testBasket = workspaceArea.createBasket(expectedName, expectedDescription, root.getId());
		
		testItemCreation(testBasket, root, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateBasketIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName =  "Test"+workspaceArea.getPathSeparator()+"Basket";
		String expectedDescription = "TestDescription";
		
		workspaceArea.createBasket(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateBasketNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName =  "TestBasket";
		String expectedDescription = null;
		
		workspaceArea.createBasket(expectedName, expectedDescription, root.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateBasketNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = null;
		String expectedDescription = "TestDescription";
		
		workspaceArea.createBasket(expectedName, expectedDescription, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceNotFoundException.class)
	public final void testCreateBasketWrongDestinationFolderId() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceNotFoundException, WrongDestinationException {
		
		String expectedName = "TestBasket";
		String expectedDescription = "TestDescription";
		
		workspaceArea.createBasket(expectedName, expectedDescription, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateBasketWrongDestinationFolderType() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WorkspaceNotFoundException, WrongDestinationException {
		
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestBasket";
		String expectedDescription = "TestDescription";
		
		Basket testBasket = workspaceArea.createBasket("TestContainingFolder", "TestContainingFolder", root.getId());
		
		workspaceArea.createBasket(expectedName, expectedDescription, testBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateWorkspaceStringStringString() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestWorkspace";
		String expectedDescription = "TestDescription";
		
		Workspace workspace = workspaceArea.createWorkspace(expectedName, expectedDescription, root.getId());
		
		testItemCreation(workspace, root, expectedName, expectedDescription, WorkspaceAreaItemType.WORKSPACE);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateWorkspaceStringStringStringIllegalCharInNameArgument() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
	
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"Workspace";
		String expectedDescription = "TestDescription";
		
		workspaceArea.createWorkspace(expectedName, expectedDescription, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateWorkspaceStringStringStringNullDescriptionArgument() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestWorkspace";
		String expectedDescription = null;
		
		workspaceArea.createWorkspace(expectedName, expectedDescription, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateWorkspaceStringStringStringNullNameArgument() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
	
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = null;
		String expectedDescription = "TestDescription";
		
		workspaceArea.createWorkspace(expectedName, expectedDescription, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceNotFoundException.class)
	public final void testCreateWorkspaceStringStringStringWrongDestinationFolderId() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
	
		String expectedName = "TestWorkspace";
		String expectedDescription = "TestDescription";
		
		workspaceArea.createWorkspace(expectedName, expectedDescription, "");

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateWorkspaceStringStringStringWrongDestinationFolderType() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
	
		Workspace root = workspaceArea.getRoot();
		Basket testBasket = root.createBasket("TestBasket", "TestDescription");
		
		String expectedName = "TestWorkspace";
		String expectedDescription = "TestDescription";
		
		workspaceArea.createWorkspace(expectedName, expectedDescription, testBasket.getId());
	}
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetDefaultBasket() throws InternalErrorException
	{
		Basket defaultBasket = workspaceArea.getDefaultBasket();
		assertNotNull("The default basket is null", defaultBasket);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveChildSubTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace subTree = getWorkspaceTree(root);
			
		workspaceArea.removeChild(subTree.getId(), root.getId());
		
		checkSubTreeExistence(workspaceArea, subTree);
	}
	
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=InsufficientPrivilegesException.class)
	public final void testGetDefaultBasketMoveItem() throws InternalErrorException, ItemNotFoundException, WrongDestinationException, InsufficientPrivilegesException, ItemAlreadyExistException
	{
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("Test Workspace", "A test workspace");
		
		Basket defaultBasket = workspaceArea.getDefaultBasket();
		workspaceArea.moveItem(defaultBasket.getId(), testWorkspace.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=InsufficientPrivilegesException.class)
	public final void testGetDefaultBasketRemove() throws InternalErrorException, ItemNotFoundException, InsufficientPrivilegesException
	{

		Basket defaultBasket = workspaceArea.getDefaultBasket();
		
		defaultBasket.remove();
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=InsufficientPrivilegesException.class)
	public final void testGetDefaultBasketRemoveFromRoot() throws InternalErrorException, ItemNotFoundException, InsufficientPrivilegesException
	{

		Workspace root = workspaceArea.getRoot();
		
		Basket defaultBasket = workspaceArea.getDefaultBasket();
		
		root.removeChild(defaultBasket);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=InsufficientPrivilegesException.class)
	public final void testGetDefaultBasketRemoveItem() throws InternalErrorException, ItemNotFoundException, InsufficientPrivilegesException
	{

		Basket defaultBasket = workspaceArea.getDefaultBasket();
		
		workspaceArea.removeItem(defaultBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveChildBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		Workspace root = workspaceArea.getRoot();
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
			
		workspaceArea.removeChild(basket.getId(), root.getId());
		
		//there is a default basket into the root
		assertEquals("Children list is not empty", 1, root.getChildren().size());

		assertFalse("Item found after delete", workspaceArea.exists(basket.getId()));
	}	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException, ItemAlreadyExistException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.removeChild(workspace.getId(), null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveChildNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException{
		Workspace root = workspaceArea.getRoot();
			
		workspaceArea.removeChild(null, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveChildWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongParentTypeException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.removeChild(workspace.getId(), root.getId());
		
		//there is a default basket into the root
		assertEquals("Children list is not empty", 1, root.getChildren().size());
		
		assertFalse("Found removed item", workspaceArea.exists(workspace.getId()));
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveChildWrongFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException, ItemAlreadyExistException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.removeChild(workspace.getId(), "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}.
	 * @throws WrongParentTypeException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveChildWrongItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongParentTypeException{
		Workspace root = workspaceArea.getRoot();
			
		workspaceArea.removeChild("", root.getId());
	}

}
