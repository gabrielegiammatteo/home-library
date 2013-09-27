/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongItemTypeException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderBulkCreator;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.common.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Ignore;
import org.junit.Test;

import static org.gcube.common.homelibrary.unittest.workspace.UnitTestUtil.*;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 * FIXME split into differents classes
 */
public class WorkspaceTest extends AbstractWorkspaceTest {

	/**
	 * @param factory the workspace factory.
	 */
	public WorkspaceTest(WorkspaceFactory factory) {
		super(factory);
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.Workspace.home.workspace.Workspace#createAnnotation(java.lang.String, java.lang.String, java.lang.String, java.util.Map, java.lang.String)}.
	 */
	@Test
	@Ignore("Not yet implemented")
	public final void testCreateAnnotation() {
		fail("Not yet implemented"); // TODO
	}
		
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testCreateMetadata() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		Metadata metadata = ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, root.getId());
		
		System.err.println("Child expeted :" + metadata.getId());
		testItemCreation(metadata, root, expectedName, expectedDescription, WorkspaceItemType.FOLDER_ITEM);
		
		assertEquals("Different oid", expectedOid, metadata.getURI());
		assertEquals("Different schema", expectedSchema, metadata.getSchema());
		assertEquals("Different metadata", expectedMetadata, metadata.getData());
		assertEquals("Different collection name", expectedCollectionName, metadata.getCollectionName());
			
		FolderItemType folderItemType = metadata.getFolderItemType();
		assertEquals("Wrong folder item type", FolderItemType.METADATA, folderItemType);
		
		long expectedLength = expectedMetadata.length();
		assertEquals("Differents length", expectedLength, metadata.getLength());
	}

	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=ItemAlreadyExistException.class)
	public final void testCreateMetadataDuplicateFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestDocument";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataIllegalCharInNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "Test"+ownerWorkspace.getPathSeparator()+"Document";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}

	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullCollectionNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestDocument1";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= null;
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullDescriptionArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestDocument2";
		String expectedDescription = null;
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullDestinationFolderArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
	
		String expectedName = "TestDocument3";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, null);
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullLanguageArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
	
		String expectedName = "TestDocument4";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = null;
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullMetadataArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestDocument5";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = null;
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullNameArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = null;
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullOidArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
	
		String expectedName = "TestDocument6";
		String expectedDescription = "Test document description";
		String expectedOid = null;
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testCreateMetadataNullSchemaArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
	
		String expectedName = "TestDocument7";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = null;
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, root.getId());
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testCreateMetadataWrongDestinationFolderIdArgument() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
	
		String expectedName = "TestDocument8";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedMetadata, expectedLanguage, expectedCollectionName, "");
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createMetadata(java.lang.String, java.lang.String, java.lang.String, java.util.String, java.util.String, java.lang.String java.util.String)}.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws IOException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=WrongDestinationException.class)
	public final void testCreateMetadataWrongDestinationTypeFolderItem() throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException, IOException, WrongDestinationException, WorkspaceFolderNotFoundException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		String expectedName = "TestDocument9";
		String expectedDescription = "Test document description";
		String expectedOid = getID();
		String expectedSchema = "dc";
		String expectedLanguage = "en";
		String expectedMetadata = "<test></test>";
		String expectedCollectionName= "Test collection name";
		
		Metadata metadata = ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, root.getId());
		
		ownerWorkspace.createMetadata(expectedName, expectedDescription, expectedOid, expectedSchema, expectedLanguage, expectedMetadata, expectedCollectionName, metadata.getId());
	}
		
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.Workspace.home.workspace.Workspace#createQuery(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testCreateQuery() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.Workspace.home.workspace.Workspace#getCapabilities(java.lang.String)}.
	 */
	@Test
	@Ignore
	public final void testGetCapabilities() {
		fail("Not yet implemented"); // TODO
	}
	
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#getNewFolderBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method 
	 */
	@Test
	public final void testGetNewFolderBulkCreator() throws InternalErrorException, WrongItemTypeException, WorkspaceFolderNotFoundException{
		
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		FolderBulkCreator bbc = ownerWorkspace.getNewFolderBulkCreator(root.getId());
		
		assertNotNull("FolderBulkCreator null", bbc);
		assertEquals("Different destination folders", root.getId(), bbc.getDestinationFolder().getId());
		
		
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#getNewFolderBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method. 
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.  
	 */
	@Test(expected=WorkspaceFolderNotFoundException.class)
	public final void testGetNewFolderBulkCreatorWrongFolderId() throws WrongItemTypeException, InternalErrorException, WorkspaceFolderNotFoundException {
		
		ownerWorkspace.getNewFolderBulkCreator("");
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#getNewFolderBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method. 
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method.
	 */
	@Test(expected=IllegalArgumentException.class)
	public final void testGetNewFOlderBulkCreatorWrongTargetNullFolderIdArgument() throws WrongItemTypeException, InternalErrorException, WorkspaceFolderNotFoundException {
		
		ownerWorkspace.getNewFolderBulkCreator(null);
	}
	
	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#getNewFolderBulkCreator(java.lang.String)}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 * @throws WrongItemTypeException if an error occurs. Please refer to documentation's method. 
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	@Test(expected=WrongItemTypeException.class)
	public final void testGetNewFolderBulkCreatorWrongTargetType() throws WrongItemTypeException, InternalErrorException, WorkspaceFolderNotFoundException{
		
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		WorkspaceItem wrongDestination = TestDataFactory.getInstance().fillExternalFiles(root,1).get(0);
		
		ownerWorkspace.getNewFolderBulkCreator(wrongDestination.getId());
	}
	
	@Test
	public final void testCreateWorkspaceSharedFolder() {
		
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		
		
	}
	
	/**
	 * Test method for {@link org.gcube.portlets.user.homelibrary.Workspace.home.workspace.Workspace#getOwner()}.
	 */
	@Test
	@Ignore
	public final void testGetOwner() {
		fail("Not yet implemented"); // TODO
	}
	

	/**
	 * Test method for {@link org.gcube.common.homelibrary.home.workspace.Workspace#getRoot()}.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 */
	@Test
	public final void testGetRoot() throws InternalErrorException {
		WorkspaceFolder root = ownerWorkspace.getRoot();
		
		assertNotNull(root);
		assertNull(root.getParent());
	}
}
