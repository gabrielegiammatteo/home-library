/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.gcube.portlets.user.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.portlets.user.homelibrary.home.workspace.Workspace;
import org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceAreaItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.Basket;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.BasketBulkCreator;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.BasketItemType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.InfoObjectType;
import org.gcube.portlets.user.homelibrary.home.workspace.basket.items.gcube.Metadata;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.BasketNotFoundException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.portlets.user.homelibrary.home.workspace.exceptions.WrongItemTypeException;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.AbstractWorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.WorkspaceAreaFactory;
import org.junit.Ignore;
import org.junit.Test;

import static org.gcube.portlets.user.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 * FIXME split into differents classes
 */
public class WorkspaceAreaTest extends AbstractWorkspaceAreaTest{

	/**
	 * @param factory the workspace area factory.
	 */
	public WorkspaceAreaTest(WorkspaceAreaFactory factory) {
		super(factory);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceArea#createAnnotation(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore("Not yet implemented")
	public final void testCreateAnnotation() {
		fail("Not yet implemented"); // TODO
	}
		
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateMetadata() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
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
		
		testItemCreation(metadata, folderBasket, expectedName, expectedDescription, WorkspaceAreaItemType.BASKET_ITEM);
		
		assertEquals("Different oid", expectedOid, metadata.getOID());
		assertEquals("Different schema", expectedSchema, metadata.getSchema());
		assertEquals("Different metadata", expectedMetadata, metadata.getData());
		assertEquals("Different collection name", expectedCollectionName, metadata.getCollectionName());
			
		BasketItemType basketItemType = metadata.getBasketItemType();
		assertEquals("Wrong basket item type", BasketItemType.INFO_OBJECT, basketItemType);
		
		InfoObjectType infoObjectType = metadata.getInfoObjectType();
		assertEquals("Wrong info object type", InfoObjectType.METADATA, infoObjectType);
		
		
		long expectedLength = expectedMetadata.length();
		assertEquals("Differents length", expectedLength, metadata.getLength());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateMetadataDuplicateBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "Test"+workspaceArea.getPathSeparator()+"Document";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= null;
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = null;
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullDestinationBasketArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullLanguageArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = null;
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = null;
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = null;
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = null;
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullSchemaArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		Basket folderBasket = root.createBasket("TestFolderBasket", "A test folder basket");
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = null;
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, folderBasket.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testCreateMetadataWrongDestinationBasketIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
	
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateMetadataWrongDestinationTypeBasketItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
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
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, metadata.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateMetadataWrongDestinationTypeWorkspace() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, BasketNotFoundException, WrongDestinationException {
		Workspace root = workspaceArea.getRoot();
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		workspaceArea.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}
		
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceArea#createQuery(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateQuery() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceArea#getCapabilities(java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testGetCapabilities() {
		fail("Not yet implemented"); // TODO
	}
	
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getNewBasketBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test
	public final void testGetNewBasketBulkCreator() throws InternalErrorException, BasketNotFoundException, WrongItemTypeException{
		
		Basket defaultBasket = workspaceArea.getDefaultBasket();
		
		BasketBulkCreator bbc = workspaceArea.getNewBasketBulkCreator(defaultBasket.getId());
		
		assertNotNull("BasketBulkCreator null", bbc);
		assertEquals("Different destination baskets", defaultBasket.getId(), bbc.getDestinationBasket().getId());
		
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getNewBasketBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method. 
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=BasketNotFoundException.class)
	public final void testGetNewBasketBulkCreatorWrongBasketId() throws BasketNotFoundException, WrongItemTypeException, InternalErrorException {
		
		workspaceArea.getNewBasketBulkCreator("");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getNewBasketBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method. 
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testGetNewBasketBulkCreatorWrongTargetNullBasketIdArgument() throws BasketNotFoundException, WrongItemTypeException, InternalErrorException {
		
		workspaceArea.getNewBasketBulkCreator(null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getNewBasketBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method. 
	 * @throws BasketNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=WrongItemTypeException.class)
	public final void testGetNewBasketBulkCreatorWrongTargetType() throws BasketNotFoundException, WrongItemTypeException, InternalErrorException{
		
		Workspace root = workspaceArea.getRoot();
		
		workspaceArea.getNewBasketBulkCreator(root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceArea#getOwner()}.
	 */
	@Test
	@Ignore
	public final void testGetOwner() {
		fail("Not yet implemented"); // TODO
	}
	

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getRoot()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetRoot() throws InternalErrorException {
		Workspace root = workspaceArea.getRoot();
		
		assertNotNull(root);
		assertNull(root.getParent());
	}
}
