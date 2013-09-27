/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItem;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.BasketItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalFile;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.ExternalPDFFile;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.Document;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.ImageDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.InfoObjectType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.Metadata;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.PDFDocument;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.BasketAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.BasketNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WorkspaceNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongParentTypeException;
import org.gcube.portlets.user.homelibrary.testdata.TestDataFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public abstract class WorkspaceAreaPersistenceTest {
	
	protected WorkspaceArea workspaceArea;

	/**
	 * Setup the workspace are before each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@Before
	public abstract void setupWorkspaceArea() throws InternalErrorException;
	
	/**
	 * Clean the workspace area after each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	@After
	public abstract void cleanWorkspaceArea() throws InternalErrorException; 

	/**
	 * Close and reopen a workspace area.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	public abstract void reloadWokspaceArea() throws InternalErrorException; 
	
	/**
	 * Test workspace persistence.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testWorkspacePersistence() throws InternalErrorException, WorkspaceAlreadyExistException, ItemNotFoundException, InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException, WorkspaceNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "FSWorkspace";
		String expectedDescription = "TestDescription";
		
		Workspace workspace = workspaceArea.createWorkspace(expectedName, expectedDescription, root.getId());
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem itemB = workspaceArea.getItem(workspace.getId());
		
		testWorkspaceAreaItemEquality(workspace, itemB);
	}
	
	/**
	 * Test basket persistence.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "FSBasket";
		String expectedDescription = "TestDescription";
		
		Basket testBasket = root.createBasket(expectedName, expectedDescription);
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem itemB = workspaceArea.getItem(testBasket.getId());
		
		testWorkspaceAreaItemEquality(testBasket, itemB);
	}
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * for a workspace type item;
	 * @throws InsufficientPrivilegesException  if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException  if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException  if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException  if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException  if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testMoveItemWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("FSWorkspace", "This is a test workspace");
		Workspace item = testWorkspace.createWorkspace("TestItem", "TestItem");
		
		testMoveItem(item, root);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}, 
	 * for a basket type item;
	 * @throws InsufficientPrivilegesException  if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException  if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException  if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testMoveItemBasket() throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		Workspace root = workspaceArea.getRoot();
		Workspace testWorkspace = root.createWorkspace("FSWorkspace", "This is a test workspace");
		Basket item = testWorkspace.createBasket("TestItem", "TestItem");
		
		testMoveItem(item, root);
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
		Workspace testWorkspace = root.createWorkspace("FSWorkspace", "This is a test workspace");
		Basket basket = testWorkspace.createBasket("FSBasket", "A test Basket");
		
		Basket destinationBasket = testWorkspace.createBasket("TestDestinationBasket", "The destination Basket");
		
		//we put a pdf in the basket
		TestDataFactory.getInstance().fillPDFDocuments(basket, 1);
		
		WorkspaceAreaItem item = basket.getChildren().get(0);
		
		testMoveItem(item, destinationBasket);
		
	}
	
	/**
	 * Generic test for move item method.
	 * @param item
	 * @param destination
	 * @param workspaceArea
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException  if an error occurs. Please refer to documentation's method.
	 */
	protected final void testMoveItem(WorkspaceAreaItem item, WorkspaceFolder destination) throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException {
		
		WorkspaceFolder parent = item.getParent();
		
		workspaceArea.moveItem(item.getId(), destination.getId());
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem movedItem = workspaceArea.getItem(item.getId());
		
		//the item parent have not to be null
		assertNotNull("The moved item parent is null",movedItem.getParent());
		//the item old parent have to be without children
		assertEquals("Children founds on old parent", 0, parent.getChildren().size());
		
		testRelationship(movedItem, destination);
		
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
		Workspace workspace = root.createWorkspace("FSWorkspace", "A Test Workspace");
		
		String expectedName = "NewTestWorkspace";
		workspaceArea.renameItem(workspace.getId(), expectedName);
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem renamedItem = workspaceArea.getItem(workspace.getId());
		
		
		assertEquals("Item not renamed",expectedName, renamedItem.getName());
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.FSWorkspaceArea#renameItem(java.lang.String, java.lang.String)}.
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
		Basket basket = root.createBasket("FSBasket", "A test basket");
		
		String expectedName = "NewTestBasket";
		workspaceArea.renameItem(basket.getId(), expectedName);
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem renamedItem = workspaceArea.getItem(basket.getId());
	
		
		assertEquals("Item not renamed",expectedName, renamedItem.getName());
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.FSWorkspaceArea#cloneItem(java.lang.String, java.lang.String)}.
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
		
		Workspace workspace = root.createWorkspace("FSWorkspace", "A test Workspace");
		
		String expectedName = "TestWorkspaceClone";
		
		WorkspaceAreaItem clone = workspaceArea.cloneItem(workspace.getId(), expectedName);

		reloadWokspaceArea();
		
		WorkspaceAreaItem clonedItem = workspaceArea.getItem(clone.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem workspaceClone = workspaceArea.getItem(workspace.getId());

		assertEquals("Wrong name", expectedName,clonedItem.getName());
		
		testCopy(workspaceClone, clonedItem, false);
		
		testRelationship(clonedItem, root);
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
		
		reloadWokspaceArea();
		
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
		
		reloadWokspaceArea();
		
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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem foundItem = workspaceArea.getItem(workspace.getId());
	
		assertNotNull("Found item null",foundItem);
		
		testWorkspaceAreaItemEquality(workspace, foundItem);
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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem foundItem = workspaceArea.getItem(basket.getId());
	
		assertNotNull("Found item null",foundItem);
		
		testWorkspaceAreaItemEquality(basket, foundItem);
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
		
		reloadWokspaceArea();
		
		root = workspaceArea.getRoot();
		
		//there is a default basket into the root
		assertEquals("Children list is not empty", 1, root.getChildren().size());
		
		try{
			workspaceArea.getItem(workspace.getId());
		}catch(ItemNotFoundException infe){
			return;
		}
		
		fail("Child not removed");
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
		
		reloadWokspaceArea();
		
		root = workspaceArea.getRoot();
		
		//there is a default baskte into the root
		assertEquals("Children list is not empty", 1, root.getChildren().size());
		
		try{
			workspaceArea.getItem(basket.getId());
		}catch(ItemNotFoundException infe){
			return;
		}
		
		fail("Child not removed");
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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem workspaceOriginal = workspaceArea.getItem(workspace.getId());
		
		assertNotNull("Copy null", copyItem);
		
		assertEquals("Wrong name", expectedName, copy.getName());
	
		testCopy(workspaceOriginal, copyItem, false);
		
		testRelationship(copyItem, root);

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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem basketOriginal = workspaceArea.getItem(basket.getId());
		
		assertEquals("Wrong name", expectedName,copyItem.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(basketOriginal, copyItem, false);
		
		testRelationship(copyItem, root);

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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem itemOriginal = workspaceArea.getItem(item.getId());
		
		assertEquals("Wrong name", expectedName,copyItem.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(itemOriginal, copyItem, false);
		
		testRelationship(copyItem, basket);

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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem workspaceOriginal = workspaceArea.getItem(workspace.getId());

		assertEquals("Wrong name", expectedName,copyItem.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(workspaceOriginal, copyItem, false);
		
		testTreeRelationship(copyItem, root);
		
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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem workspaceFolderReloaded = workspaceArea.getItem(workspaceFolder.getId());
		
		WorkspaceAreaItem workspaceOriginal = workspaceArea.getItem(workspace.getId());
		
		assertNotNull("Copy null", copyItem);
		
		assertEquals("Wrong name", workspaceOriginal.getName(), copy.getName());
	
		testCopy(workspaceOriginal, copyItem, true);
		
		testRelationship(copyItem, workspaceFolderReloaded);

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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem workspaceFolderReloaded = workspaceArea.getItem(workspaceFolder.getId());
		WorkspaceAreaItem basketOriginal = workspaceArea.getItem(basket.getId());
		
		assertEquals("Wrong name", basket.getName(), copyItem.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(basketOriginal, copyItem, true);
		
		testRelationship(copyItem, workspaceFolderReloaded);

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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem basketFolderReloaded = workspaceArea.getItem(basketFolder.getId());
		WorkspaceAreaItem itemOriginal = workspaceArea.getItem(item.getId());
		
		assertEquals("Wrong name", itemOriginal.getName(), copyItem.getName());
		
		WorkspaceAreaItem findCopy = workspaceArea.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findCopy);
		
		testCopy(itemOriginal, copyItem, true);
		
		testRelationship(copyItem, basketFolderReloaded);

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
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem copyItem = workspaceArea.getItem(copy.getId());
		
		root = workspaceArea.getRoot();
		WorkspaceAreaItem workspaceFolderReloaded = workspaceArea.getItem(workspaceFolder.getId());
		WorkspaceAreaItem workspaceOriginal = workspaceArea.getItem(workspace.getId());

		assertEquals("Wrong name", workspaceOriginal.getName(), copyItem.getName());
		
		WorkspaceAreaItem findClone = workspaceArea.getItem(copyItem.getId());
		
		assertNotNull("Find copy null",findClone);
		
		testCopy(workspaceOriginal, copyItem, true);
		
		testTreeRelationship(copyItem, workspaceFolderReloaded);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, ItemNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalFile";
		String expectedDescription = "Test external file description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomFile();
		
		ExternalFile externalFile = workspaceArea.createExternalFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem folderBasketReloaded = workspaceArea.getItem(folderBasket.getId());
		ExternalFile externalFileReloaded = (ExternalFile) workspaceArea.getItem(externalFile.getId());
		
		testWorkspaceAreaItemEquality(externalFile, externalFileReloaded);
		
		testItemCreation(externalFile, folderBasketReloaded, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalFileReloaded.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_FILE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalFileReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalFileReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateImageDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ImageDocument imageDocument = workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		reloadWokspaceArea();
		root = workspaceArea.getRoot();
		ImageDocument imageDocumentReloaded = (ImageDocument) workspaceArea.getItem(imageDocument.getId());
		WorkspaceAreaItem folderBasketReloaded =  workspaceArea.getItem(folderBasket.getId());
		
		testWorkspaceAreaItemEquality(imageDocument, imageDocumentReloaded);
		
		testDocumentCreation(imageDocumentReloaded, folderBasketReloaded, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		BasketItemType basketItemType = imageDocumentReloaded.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = imageDocumentReloaded.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.IMAGE_DOCUMENT, infoObjectType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocumentReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocumentReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		Document document = workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		reloadWokspaceArea();
		root = workspaceArea.getRoot();
		Document documentReloaded = (Document) workspaceArea.getItem(document.getId());
		WorkspaceAreaItem folderBasketReloaded =  workspaceArea.getItem(folderBasket.getId());
	
		
		testDocumentCreation(documentReloaded, folderBasketReloaded, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		BasketItemType basketItemType = documentReloaded.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = documentReloaded.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.DOCUMENT, infoObjectType);
		
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, documentReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), documentReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateExternalPDFFile() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestExternalPDFFile";
		String expectedDescription = "Test external PDFFile description";
		String expectedMimeType = "testMimeType";
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		ExternalPDFFile externalPDFFile = workspaceArea.createExternalPDFFile(expectedName, expectedDescription, expectedMimeType, new FileInputStream(fileData), folderBasket.getId());
		
		reloadWokspaceArea();
		
		WorkspaceAreaItem folderBasketReloaded = workspaceArea.getItem(folderBasket.getId());
		ExternalPDFFile externalPDFFileReloaded = (ExternalPDFFile) workspaceArea.getItem(externalPDFFile.getId());
		
		testWorkspaceAreaItemEquality(externalPDFFile, externalPDFFileReloaded);
	
		testItemCreation(externalPDFFileReloaded, folderBasketReloaded, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		BasketItemType basketItemType = externalPDFFileReloaded.getBasketItemType();
		
		assertEquals("Wrong basket item type", BasketItemType.EXTERNAL_PDF_FILE, basketItemType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, externalPDFFileReloaded.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), externalPDFFileReloaded.getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreatePDFDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		PDFDocument pdfDocument = workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		reloadWokspaceArea();
		root = workspaceArea.getRoot();
		PDFDocument pdfDocumentReloaded = (PDFDocument) workspaceArea.getItem(pdfDocument.getId());
		WorkspaceAreaItem folderBasketReloaded =  workspaceArea.getItem(folderBasket.getId());
		
		testWorkspaceAreaItemEquality(pdfDocument, pdfDocumentReloaded );
	
		
		testDocumentCreation(pdfDocumentReloaded , folderBasketReloaded, expectedName, expectedDescription, expectedMimeType, WorkspaceAreaItemType.BASKET_ITEM, expectedOid, expectedCollectionName, expectedMetadata, expectedAnnotation);
	
		BasketItemType basketItemType = pdfDocumentReloaded .getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = pdfDocumentReloaded .getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.PDF_DOCUMENT, infoObjectType);
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocumentReloaded .getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocumentReloaded .getData());
		assertTrue("Different content", dataEquals);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testCreateMetadata() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		Metadata metadata = workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, folderBasket.getId());
		
		reloadWokspaceArea();
		root = workspaceArea.getRoot();
		Metadata metadataReloaded = (Metadata) workspaceArea.getItem(metadata.getId());
		WorkspaceAreaItem folderBasketReloaded =  workspaceArea.getItem(folderBasket.getId());
		
		testWorkspaceAreaItemEquality(metadata, metadataReloaded);
	
		testItemCreation(metadataReloaded, folderBasketReloaded, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		assertEquals("Different oid", expectedOid, metadataReloaded.getOID());
		assertEquals("Different schema", expectedSchema, metadataReloaded.getSchema());
		assertEquals("Different metadata", expectedMetadata, metadataReloaded.getData());
		assertEquals("Different collection name", expectedCollectionName, metadataReloaded.getCollectionName());
			
		BasketItemType basketItemType = metadataReloaded.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = metadataReloaded.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.METADATA, infoObjectType);
		
		
		long expectedLength = expectedMetadata.length();
		assertEquals("Differents length", expectedLength, metadataReloaded.getLength());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemSameOidImageDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestImageDocument";
		String expectedDescription = "Test document image description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		ImageDocument imageDocument = workspaceArea.createImageDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		String expectedName2 = "TestImageDocument2";
		ImageDocument imageDocument2 = workspaceArea.createImageDocument(expectedName2, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());

		imageDocument.remove();
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, imageDocument2.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), imageDocument2.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemSameOidPDFDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
	Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestPDFDocument";
		String expectedDescription = "Test document PDF description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomPDFFile();
		
		PDFDocument pdfDocument = workspaceArea.createPDFDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
			
		String expectedName2 = "TestPDFDocument2";
		PDFDocument pdfDocument2 = workspaceArea.createPDFDocument(expectedName2, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
				
		pdfDocument.remove();
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, pdfDocument2.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), pdfDocument2.getData());
		assertTrue("Different content", dataEquals);
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testRemoveItemSameOidDocument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException, ItemNotFoundException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = "Test oid";
		String expectedMimeType = "testMimeType";
		String expectedCollectionName= "Test collection name";
		Map<String, String> expectedMetadata = new LinkedHashMap<String, String>();
		expectedMetadata.put("TestMetadataSchemaName", "Test metadata");
		Map<String, String> expectedAnnotation = new LinkedHashMap<String, String>(); 
		expectedAnnotation.put("TestAnnotation", "Test annotation");
		
		File fileData = TestDataFactory.getInstance().getTMPRandomImageFile();
		
		Document document = workspaceArea.createDocument(expectedName, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		String expectedName2 = "TestDocument2";
		Document document2 = workspaceArea.createDocument(expectedName2, expectedDescription, expectedOid, expectedMimeType, new FileInputStream(fileData), expectedMetadata, expectedAnnotation, expectedCollectionName, folderBasket.getId());
		
		
		document.remove();
		
		long expectedLength = fileData.length();
		assertEquals("Differents length", expectedLength, document2.getLength());
		
		boolean dataEquals = IOUtils.contentEquals(new FileInputStream(fileData), document2.getData());
		assertTrue("Different content", dataEquals);
		
	}


}
