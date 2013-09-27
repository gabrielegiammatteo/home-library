/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;

import static org.junit.Assert.*;

import java.util.List;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItem;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.PDFDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.BasketAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongItemTypeException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.AbstractWorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.WorkspaceAreaFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all generic item methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#changeDescription(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceAreaTestItemMethods extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTestItemMethods(WorkspaceAreaFactory factory) {
		super(factory);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testChangeDescriptionNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();

		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");

		String expectedDescription = null;

		workspaceArea.changeDescription(workspace.getId(), expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testChangeDescriptionNullIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {

		String expectedDescription = "This is a test new description";

		workspaceArea.changeDescription(null, expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testChangeDescriptionWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();

		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");

		String expectedDescription = "This is a test new description";

		workspaceArea.changeDescription(workspace.getId(), expectedDescription);

		assertEquals("Descriptions are different", expectedDescription, workspace.getDescription());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#changeDescription(java.lang.String, java.lang.String)}.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testChangeDescriptionWrongIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, ItemNotFoundException {

		String expectedDescription = "This is a test new description";

		workspaceArea.changeDescription("", expectedDescription);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExists() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		Workspace root = workspaceArea.getRoot();
		boolean exists = workspaceArea.exists(root.getId());
		assertTrue("The root has not been found",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testExistsNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		workspaceArea.getRoot();
		workspaceArea.exists(null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExistsStringString() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongItemTypeException{
		Workspace root = workspaceArea.getRoot();
		Basket testBasket = root.createBasket("Test Basket", "test basket");
		boolean exists = workspaceArea.exists(testBasket.getName(), root.getId());
		assertTrue("The test basket has not been found",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testExistsStringStringFoldeIdNull() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		workspaceArea.exists("", null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testExistsStringStringNameNull() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		Workspace root = workspaceArea.getRoot();
		workspaceArea.exists(null, root.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testExistsStringStringWrongFolderId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		workspaceArea.exists("", "");
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongItemTypeException.class)
	public final void testExistsStringStringWrongFolderType() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException, ItemAlreadyExistException{
		Workspace root = workspaceArea.getRoot();

		Basket basket = root.createBasket("TestBasket", "Test basket");

		List<PDFDocument> documents = TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		workspaceArea.exists("", documents.get(0).getId());
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String, String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExistsStringStringWrongName() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException, WrongItemTypeException{
		Workspace root = workspaceArea.getRoot();
		boolean exists = workspaceArea.exists("", root.getId());
		assertFalse("An item with wrong name has been found",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testExistsWrongId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		boolean exists = workspaceArea.exists("");
		assertFalse("Found item with wrong id",exists);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetItemBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();

		Basket basket = root.createBasket("TestBasket", "Test basket");

		WorkspaceAreaItem foundItem = workspaceArea.getItem(basket.getId());

		assertNotNull("Found item null",foundItem);

		testWorkspaceAreaItemEquality(basket, foundItem);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetItemBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();

		Basket basket = root.createBasket("TestBasket", "Test basket");

		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);

		WorkspaceAreaItem item = basket.getChildren().get(0);

		WorkspaceAreaItem foundItem = workspaceArea.getItem(item.getId());

		assertNotNull("Found item null",foundItem);

		testWorkspaceAreaItemEquality(item, foundItem);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testGetItemNullItemId() throws ItemNotFoundException{
		workspaceArea.getItem(null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();

		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");

		WorkspaceAreaItem foundItem = workspaceArea.getItem(workspace.getId());

		assertNotNull("Found item null",foundItem);

		testWorkspaceAreaItemEquality(workspace, foundItem);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testGetItemWrongItemId() throws ItemNotFoundException{
		workspaceArea.getItem("");
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * with null destination arguments.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testMoveDestinationNullArguments() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();

		Basket testBasket = root.createBasket("TestBasket", "A test basket");

		workspaceArea.moveItem(testBasket.getId(), null);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getPath()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	/**public final void testGetPath() throws InternalErrorException
	{
		Workspace root = workspaceArea.getRoot();
		String expectedPath = workspaceArea.getPathSeparator();

		assertEquals("Path not expected", expectedPath, root.getPath());
	}*/

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * for a basket type item;
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testMoveItemBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("TestWorkspace", "This is a test workspace");
		Basket item = testWorkspace.createBasket("TestItem", "TestItem");

		testMoveItem(item, root, workspaceArea);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}. 
	 * Try to move a basket into a basket.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testMoveItemBasketIntoBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Basket testBasket = root.createBasket("TestBasket", "A test basket");
		Basket destinationBasket = root.createBasket("TestDestinationBasket", "The destination basket");

		workspaceArea.moveItem(testBasket.getId(), destinationBasket.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * for a BasketItem type item;
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testMoveItemBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("TestWorkspace", "This is a test workspace");
		Basket basket = testWorkspace.createBasket("TestBasket", "A test Basket");

		Basket destinationBasket = testWorkspace.createBasket("TestDestinationBasket", "The destination Basket");

		//we put a pdf in the basket
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);

		WorkspaceAreaItem item = basket.getChildren().get(0);

		testMoveItem(item, destinationBasket, workspaceArea);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testMoveItemDuplicateItems() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		root.createWorkspace("TestItem", "TestItem duplicate");

		Workspace testWorkspace = root.createWorkspace("TestWorkspace", "This is a test workspace");
		Workspace item = testWorkspace.createWorkspace("TestItem", "TestItem");

		workspaceArea.moveItem(item.getId(), root.getId());
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * with null itemId arguments.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testMoveItemNullItemIdArguments() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();

		workspaceArea.moveItem(null, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * for a workspace type;
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testMoveItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("TestWorkspace", "This is a test workspace");
		Workspace item = testWorkspace.createWorkspace("TestItem", "TestItem");

		testMoveItem(item, root, workspaceArea);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}. 
	 * Try to move a workspace in a basket.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testMoveItemWorkspaceIntoBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("TestWorkspace", "This is a test workspace");
		Basket testBasket = root.createBasket("TestBasket", "A test basket");

		workspaceArea.moveItem(testWorkspace.getId(), testBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		Basket basket = root.createBasket("TestBasket", "A test basket");

		workspaceArea.removeItem(basket.getId());

		try{
			workspaceArea.getItem(basket.getId());
		}catch(ItemNotFoundException infe)
		{
			return;
		}

		fail("Item not removed");

		assertEquals("Wrong number of children", 0, root.getChildren().size());
	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRemoveItemNullItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		workspaceArea.getRoot();
		workspaceArea.removeItem(null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test workspace");

		workspaceArea.removeItem(workspace.getId());

		try{
			workspaceArea.getItem(workspace.getId());
		}catch(ItemNotFoundException infe)
		{
			return;
		}

		fail("Item not removed");

		assertEquals("Wrong number of children", 0, root.getChildren().size());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRemoveItemWrongItemId() throws ItemNotFoundException, InternalErrorException, InsufficientPrivilegesException{
		workspaceArea.removeItem("");
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameIllegalCharInNameArgument() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		Workspace workspace = root.createWorkspace("TestWorkspace", "A Test Workspace");

		String expectedName = "New"+workspaceArea.getPathSeparator()+"TestWorkspace";
		workspaceArea.renameItem(workspace.getId(), expectedName);

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws BasketAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRenameItemBasket() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, BasketAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Basket basket = root.createBasket("TestBasket", "A test basket");

		String expectedName = "NewTestBasket";
		workspaceArea.renameItem(basket.getId(), expectedName);

		assertEquals("Item not renamed",expectedName, basket.getName());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameItemNullItemId() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {

		String expectedName = "NewTestWorkspace";
		workspaceArea.renameItem(null, expectedName);

	}


	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testRenameItemNullName() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		Workspace workspace = root.createWorkspace("TestWorkspace", "A Test Workspace");

		String expectedName = null;
		workspaceArea.renameItem(workspace.getId(), expectedName);

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRenameItemWorkspace() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		Workspace workspace = root.createWorkspace("TestWorkspace", "A Test Workspace");

		String expectedName = "NewTestWorkspace";
		workspaceArea.renameItem(workspace.getId(), expectedName);

		assertEquals("Item not renamed",expectedName, workspace.getName());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testRenameItemWorkspaceSameName() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		Workspace workspace = root.createWorkspace("TestWorkspace", "A Test Workspace");

		String expectedName = "TestWorkspace";
		workspaceArea.renameItem(workspace.getId(), expectedName);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testRenameItemWrongItemId() throws InternalErrorException, WorkspaceAlreadyExistException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException, WrongDestinationException {

		String expectedName = "NewTestWorkspace";
		workspaceArea.renameItem("", expectedName);
	}

}
