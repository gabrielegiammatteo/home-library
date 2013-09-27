/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItem;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.PDFDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.AbstractWorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.WorkspaceAreaFactory;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * Test all copy methods.
 * Tested methods:
 * <ul>
 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}</li>
 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}</li>
 * </ul>
 * @author Federico De Faveri defaveri@isti.cnr.it
 */
public class WorkspaceAreaTestCopyMethods extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTestCopyMethods(WorkspaceAreaFactory factory) {
		super(factory);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCopyStringStringAlreadyExistNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspaceFolder = root.createWorkspace("TestWorkspaceFolder", "A test Workspace folder");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		WorkspaceAreaItem copy = workspaceArea.copy(basket.getId(), workspaceFolder.getId());
		
		assertEquals("Wrong name", basket.getName(), copy.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copy.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(basket, copy, true);
		
		testRelationship(copy, workspaceFolder);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket basketFolder = root.createBasket("TestBasketFolder", "Test basket folder");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		WorkspaceAreaItem item = basket.getChildren().get(0);
		
		WorkspaceAreaItem copy = workspaceArea.copy(item.getId(), basketFolder.getId());
		
		assertEquals("Wrong name", item.getName(), copy.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copy.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(item, copy, true);
		
		testRelationship(copy, basketFolder);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy( java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringBasketItemWrongDestinationIdArgumentTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Basket basketItems = root.createBasket("TestBasketItems", "Test basket");
		
		List<PDFDocument> documents = TestDataFactory.getInstance().fillPDFDocuments(basketItems, 2);
		
		WorkspaceAreaItem item = documents.get(0);
		WorkspaceAreaItem itemFolder = documents.get(1);
		
		workspaceArea.copy(item.getId(), itemFolder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringBasketItemWrongDestinationIdArgumentTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Basket basketItems = root.createBasket("TestBasketItems", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basketItems, 1);
		
		WorkspaceAreaItem item = basketItems.getChildren().get(0);
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		
		workspaceArea.copy(item.getId(), workspace.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringBasketWrongDestinationIdArgumentTypeBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Basket basketFolder = root.createBasket("TestBasketFolder", "Test basket");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		workspaceArea.copy(basket.getId(), basketFolder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringBasketWrongDestinationIdArgumentTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Basket basketItems = root.createBasket("TestBasketItem", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basketItems, 1);
		
		WorkspaceAreaItem item = basketItems.getChildren().get(0);
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		workspaceArea.copy(basket.getId(), item.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), null);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringNullItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException{
		Workspace root = workspaceArea.getRoot();
		
		workspaceArea.copy(null, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCopyStringStringStringAlreadyExistNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspace";
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), expectedName, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		String expectedName = "TestBasketClone";
		
		WorkspaceAreaItem copy = workspaceArea.copy(basket.getId(), expectedName, root.getId());
		
		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copy.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(basket, copy, false);
		
		testRelationship(copy, root);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		WorkspaceAreaItem item = basket.getChildren().get(0);
		
		String expectedName = "TestBasketItemClone";
		
		WorkspaceAreaItem copy = workspaceArea.copy(item.getId(), expectedName, basket.getId());
		
		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copy.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(item, copy, false);
		
		testRelationship(copy, basket);

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringBasketItemWrongDestinationIdArgumentTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestBasketItemCopy";
		
		Basket basketItems = root.createBasket("TestBasketItems", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basketItems, 2);
		
		WorkspaceAreaItem item = basketItems.getChildren().get(0);
		WorkspaceAreaItem itemFolder = basketItems.getChildren().get(1);
		
		workspaceArea.copy(item.getId(), expectedName, itemFolder.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringBasketItemWrongDestinationIdArgumentTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestBasketItemCopy";
		
		Basket basketItems = root.createBasket("TestBasketItems", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basketItems, 1);
		
		WorkspaceAreaItem item = basketItems.getChildren().get(0);
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		
		workspaceArea.copy(item.getId(), expectedName, workspace.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringBasketWrongDestinationIdArgumentTypeBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestBasketCopy";
		
		Basket basketFolder = root.createBasket("TestBasketFolder", "Test basket");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		workspaceArea.copy(basket.getId(), expectedName, basketFolder.getId());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringBasketWrongDestinationIdArgumentTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestBasketCopy";
		
		Basket basketItems = root.createBasket("TestBasketItem", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basketItems, 1);
		
		WorkspaceAreaItem item = basketItems.getChildren().get(0);
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		workspaceArea.copy(basket.getId(), expectedName, item.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringIllegalCharInCopyNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "Copy"+workspaceArea.getPathSeparator()+"TestWorkspace";
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), expectedName, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringNullCopyName() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = null;
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), expectedName, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringNullDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspace";
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), expectedName, null);

	}
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCopyStringStringStringNullItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspaceCopy";
		
		workspaceArea.copy(null, expectedName, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		String expectedName = "TestWorkspaceCopy";
		
		WorkspaceAreaItem copy = workspaceArea.copy(workspace.getId(), expectedName, root.getId());
		
		assertNotNull("Copy null", copy);
		
		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copy.getId());
		
		assertNotNull(findCopy);
		
		testCopy(workspace, copy, false);
		
		testRelationship(copy, root);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringStringWorkspaceTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, IOException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = getWorkspaceTree(root);
				
		String expectedName = "TestWorkspaceCopy";
		
		WorkspaceAreaItem copy = workspaceArea.copy(workspace.getId(), expectedName, root.getId());

		assertEquals("Wrong name", expectedName, copy.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(copy.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(workspace, copy, false);
		
		testTreeRelationship(copy, root);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringWorkspaceWrongDestinationIdArgumentTypeBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspace";
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		workspaceArea.copy(workspace.getId(), expectedName, basket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringStringWorkspaceWrongDestinationIdArgumentTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspace";
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		WorkspaceAreaItem item = basket.getChildren().get(0);
		
		
		workspaceArea.copy(workspace.getId(), expectedName, item.getId());

	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCopyStringStringStringWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspace";
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), expectedName, "");

	}
	

	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCopyStringStringStringWrongItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException{
		Workspace root = workspaceArea.getRoot();

		String expectedName = "TestWorkspaceCopy";
		
		workspaceArea.copy("", expectedName, root.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspaceFolder = root.createWorkspace("TestWorkspaceFolder", "A test Workspace folder");
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		WorkspaceAreaItem copy = workspaceArea.copy(workspace.getId(), workspaceFolder.getId());
		
		assertNotNull("Copy null", copy);
		
		assertEquals("Wrong name", workspace.getName(), copy.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copy.getId());
		
		assertNotNull(findCopy);
		
		testCopy(workspace, copy, true);
		
		testRelationship(copy, workspaceFolder);

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCopyStringStringWorkspaceTree() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException, IOException {
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspaceFolder = root.createWorkspace("TestWorkspaceFolder", "A test Workspace folder");
		
		Workspace workspace = getWorkspaceTree(root);
		
		WorkspaceAreaItem copy = workspaceArea.copy(workspace.getId(), workspaceFolder.getId());

		assertEquals("Wrong name", workspace.getName(), copy.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(copy.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(workspace, copy, true);
		
		testTreeRelationship(copy, workspaceFolder);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringWorkspaceWrongDestinationIdArgumentTypeBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		workspaceArea.copy(workspace.getId(), basket.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCopyStringStringWorkspaceWrongDestinationIdArgumentTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		Basket basket = root.createBasket("TestBasket", "Test basket");
		
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		WorkspaceAreaItem item = basket.getChildren().get(0);
		
		
		workspaceArea.copy(workspace.getId(), item.getId());

	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy( java.lang.String, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCopyStringStringWrongDestinationIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, ItemNotFoundException, WrongDestinationException{
		Workspace root = workspaceArea.getRoot();
		
		Workspace workspace = root.createWorkspace("TestWorkspace", "A test Workspace");
		
		workspaceArea.copy(workspace.getId(), "");

	}
	
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemNotFoundException.class)
	public final void testCopyStringStringWrongItemId() throws ItemNotFoundException, WrongDestinationException, InternalErrorException, ItemAlreadyExistException, InsufficientPrivilegesException{
		Workspace root = workspaceArea.getRoot();
		
		workspaceArea.copy("", root.getId());

	}	
	
}
