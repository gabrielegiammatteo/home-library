/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.WorkspaceSharedFolder;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.testdata.TestDataFactory;
import org.gcube.common.homelibrary.unittest.workspace.AbstractWorkspaceTest;
import org.gcube.common.homelibrary.unittest.workspace.WorkspaceFactory;
import org.junit.Ignore;
import org.junit.Test;
/**
 * @author Antonio Gioia
 *
 */
public class WorkspaceTestSharedFolders extends AbstractWorkspaceTest {

	/**
	 * @param factory
	 */
	public WorkspaceTestSharedFolders(WorkspaceFactory factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}
	
	private void checkContentSharedFolder(String sharedFolderId, int itemsSize, boolean hasDifferentName) throws Exception {
		
		WorkspaceSharedFolder folder = (WorkspaceSharedFolder) ownerWorkspace.getItem(sharedFolderId);
		
		List<WorkspaceItem> items = folder.getChildren();
		
		assertEquals(items.size(), itemsSize);
		
		for (Workspace workspace : userWorkspaces.values()) {
			
			WorkspaceSharedFolder sharedFodler = (WorkspaceSharedFolder) workspace.getItem(sharedFolderId);
			List<WorkspaceItem> sharedItems = sharedFodler.getChildren();
			
			assertEquals(sharedItems.size(), itemsSize);
			
			// Check if each owner and user items is shared
			for (WorkspaceItem ownerItem : items) {
				
				assertTrue(ownerItem.isShared());
				
				boolean found = false;
				for (WorkspaceItem  item : sharedItems) {
				
					if (ownerItem.getId().equals(item.getId())) {
						assertTrue(item.isShared());
						found = true;
						
						// Check if items have different path
						if (hasDifferentName) {
							assertFalse(ownerItem.getPath().equals(item.getPath()));
						} else {
							assertTrue(ownerItem.getPath().equals(item.getPath()));
						}
						break;
					}
				}
				assertTrue(found);
			}
		}
	}
	
	private final WorkspaceSharedFolder createSharedFolder(String name, String destinationFolderId) 
			throws InsufficientPrivilegesException, ItemAlreadyExistException, WrongDestinationException,
			ItemNotFoundException, WorkspaceFolderNotFoundException, InternalErrorException {
		
		String description = "DescriptionSharedFolder";
	
		
		List<String> users = new ArrayList<String>();
		for (Workspace testWorkspace : userWorkspaces.values()) {
			users.add(testWorkspace.getOwner().getPortalLogin());
		}
		
		return ownerWorkspace.createSharedFolder(name,
				description, users, destinationFolderId);
	}
	
	
	private final void fillFolder(WorkspaceFolder folder, int size) throws Exception {
		
		TestDataFactory.getInstance().fillExternalFiles(folder, size);
		
	}
		
	private final void markItemsAsRead(String sharedFolderId, boolean marked) throws Exception{
		
		WorkspaceSharedFolder sharedFolder = (WorkspaceSharedFolder)ownerWorkspace.getItem(sharedFolderId);
		for (WorkspaceItem item : sharedFolder.getChildren()){
			item.markAsRead(marked);
		}
		
		for (Workspace workspace : userWorkspaces.values()) {
			WorkspaceSharedFolder userSharedFolder = (WorkspaceSharedFolder)workspace.getItem(sharedFolderId);
			for (WorkspaceItem item : userSharedFolder.getChildren()){
				item.markAsRead(marked);
			}
		}
	}
	
	private final void checkItemsIsMarkAsRead(String sharedFolderId, boolean marked) throws Exception {
		
		WorkspaceSharedFolder sharedFolder = (WorkspaceSharedFolder)ownerWorkspace.getItem(sharedFolderId);
		for (WorkspaceItem item : sharedFolder.getChildren()){
			assertTrue(item.isMarkedAsRead());
		}
		
		for (Workspace workspace : userWorkspaces.values()) {
			sharedFolder = (WorkspaceSharedFolder)workspace.getItem(sharedFolderId);
			for (WorkspaceItem item : sharedFolder.getChildren()){
				if (marked)
					assertTrue(item.isMarkedAsRead());
				else
					assertFalse(item.isMarkedAsRead());
			}
		}

	}
	
	private final void checkCopySharedFodler(WorkspaceItem folder, WorkspaceItem folderCopy) throws InternalErrorException {
		
		for (WorkspaceItem item : folder.getChildren()) {
			
			boolean found = false;
			for (WorkspaceItem itemCopy : folderCopy.getChildren()) {
				
				if (item.getName().equals(itemCopy.getName())) {
					found = true;
					
					assertFalse(item.getId().equals(itemCopy.getId()));
					
					assertFalse(itemCopy.isShared());
					
					checkCopySharedFodler(item, itemCopy);
					
				}
			}
			assertTrue(found);
		}
		
	}
	
	@Test
	public final void createSharedFolderWithItems() throws Exception{
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder("SharedFolder",
				ownerWorkspace.getRoot().getId());
		int itemSize = 2;
		fillFolder(sharedFolder, itemSize);
		checkContentSharedFolder(sharedFolder.getId(), itemSize, false);
	}
	
	@Test
	public final void createSharedFolderWithItemsAndExistingNameInUsersRoot() throws Exception{
		
		String name = "FakeSharedFolder";
		
		for (Workspace workspace : userWorkspaces.values()){
			// Add an item in root with same name of shared folder will be created
			workspace.getRoot().createFolder(name, "");
		}
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder(name,
				ownerWorkspace.getRoot().getId());
		int itemSize = 2;
		fillFolder(sharedFolder, itemSize);
		checkContentSharedFolder(sharedFolder.getId(), itemSize, true);
	}
	
	@Test(expected=WrongDestinationException.class)
	public final void createSharedFolderOnDestinationFolderAlreadyShared() throws Exception {
		
		String sharedFolderName1 = "SharedFolder1";
		WorkspaceSharedFolder sharedFolder1 = createSharedFolder(sharedFolderName1,
				ownerWorkspace.getRoot().getId());
		
		String sharedFolderName2 = "SharedFolder2";
		createSharedFolder(sharedFolderName2, sharedFolder1.getId());
		 
	}
	
	@Test
	public final void shareFolder() throws Exception {
		
		String folderName = "NormalFolder";
		
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(folderName, "");
		fillFolder(folder, 10);
		
		List<String> users = new ArrayList<String>();
		for (String user : userWorkspaces.keySet()) {
			users.add(user);
		}
		
		ownerWorkspace.shareFolder(users, folder.getId());
		
	}
	
	@Test(expected=WrongDestinationException.class)
	public final void shareFolderWithAncestorsAlreadyShared() throws Exception {
		
		String sharedFolderName = "SharedFolder";
		String childSharedFolderName = "ChildFolder";
		
		WorkspaceFolder sharedFolder = createSharedFolder(sharedFolderName,
				ownerWorkspace.getRoot().getId());
		fillFolder(sharedFolder, 10);
		WorkspaceFolder folder = sharedFolder.createFolder(childSharedFolderName, "");
		
		List<String> users = new ArrayList<String>();
		for (String user : userWorkspaces.keySet()) {
			users.add(user);
		}
		ownerWorkspace.shareFolder(users, folder.getId());
		
	}
	
	@Test(expected=WrongDestinationException.class)
	public final void shareFolderWithDiscendentsAlreadyShared() throws Exception {
		
		String sharedFolderName = "SharedFolder";
		String childSharedFolderName = "ChildFolder";
		
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(childSharedFolderName, "");
		
		WorkspaceFolder sharedFolder = createSharedFolder(sharedFolderName,
				folder.getId());
		fillFolder(sharedFolder, 10);
		
		
		List<String> users = new ArrayList<String>();
		for (String user : userWorkspaces.keySet()) {
			users.add(user);
		}
		ownerWorkspace.shareFolder(users, folder.getId());
		
	}

	
	/*
	@Test
	public final void checkIsMarkedAsRead() throws Exception{
		
		WorkspaceSharedFolder ownerSharedFolder = createSharedFolder("SharedFolder",
				ownerWorkspace.getRoot().getId());
		String sharedFolderId = ownerSharedFolder.getId();
		
		int itemSize = 2;
		fillSharedFolder(ownerSharedFolder, itemSize);
		
		checkItemsIsMarkAsRead(sharedFolderId, false);
		
		markItemsAsRead(sharedFolderId, true);
		checkItemsIsMarkAsRead(sharedFolderId, true);
		
		markItemsAsRead(sharedFolderId, false);
		checkItemsIsMarkAsRead(sharedFolderId, false);
		
	}
	
	@Test
	public final void renameOwnerSharedFolder() throws Exception{
		
		String name = "SharedFolder";
		WorkspaceSharedFolder folder = createSharedFolder(name,
				ownerWorkspace.getRoot().getId());
		
		
		String newName = "SharedFolderOwner";
		folder.rename(newName);
		
		assertEquals(newName,folder.getName());
		assertEquals(newName,ownerWorkspace.getItem(folder.getId()).getName());
		
		for (Workspace workspace : userWorkspaces.values()){
			assertEquals(name,workspace.getItem(folder.getId()).getName());
		}
		
		
	}
	
	@Test(expected=ItemAlreadyExistException.class)
	public final void renameOwnerSharedFolderWithExistingName() throws Exception {
		
		String name = "SharedFolder";
		WorkspaceSharedFolder sharedFolder = createSharedFolder(name,
				ownerWorkspace.getRoot().getId());
		
		String newName = "folder";
		ownerWorkspace.createFolder(newName, "folderDescription", ownerWorkspace.getRoot().getId()); 
		
		sharedFolder.rename(newName);
	}
	
	@Test
	public final void copySharedFolderWithItems() throws Exception {
		
		String nameSharedFolder = "SharedFolderCopy";
		String nameFolder = "Folder";
		
		int itemSize = 10;
		WorkspaceSharedFolder sharedFolder = createSharedFolder(nameSharedFolder,
				ownerWorkspace.getRoot().getId());
		fillSharedFolder(sharedFolder, itemSize);

		
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(nameFolder, "");
		
		WorkspaceItem item = ownerWorkspace.copy(sharedFolder.getId(), folder.getId());
		assertEquals(WorkspaceItemType.FOLDER, item.getType());
		
		checkCopySharedFodler(sharedFolder, (WorkspaceFolder) item);
		
	}
	
	@Test(expected=ItemAlreadyExistException.class)
	public final void copySharedFolderWithExistingName() throws Exception {
		
		String nameSharedFolder = "SharedFolderCopy";
		String nameFolder = "Folder";
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder(nameSharedFolder,
				ownerWorkspace.getRoot().getId());
		
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(nameFolder, "");
		
		ownerWorkspace.copy(sharedFolder.getId(), nameFolder, ownerWorkspace.getRoot().getId());
	}
	
	@Test
	public final void moveSharedFolder() throws Exception {
		
		String nameSharedFolder = "SharedFolderCopy";
		String nameFolder = "Folder";
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder(nameSharedFolder,
				ownerWorkspace.getRoot().getId());
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(nameFolder, "");
		
		ownerWorkspace.moveItem(sharedFolder.getId(), folder.getId());
		assertEquals(sharedFolder.getParent().getName(), nameFolder);
		
		assertEquals(ownerWorkspace.getRoot().getChildren().size(),1);
	}
	
	@Test(expected=ItemAlreadyExistException.class)
	public final void moveSharedFodlerWithExistingName() throws Exception {
		
		String nameSharedFolder = "SharedFolderCopy";
		String nameFolder = "Folder";
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder(nameSharedFolder,
				ownerWorkspace.getRoot().getId());
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(nameFolder, "");
		folder.createFolder(nameSharedFolder, "");
		
		ownerWorkspace.moveItem(sharedFolder.getId(), folder.getId());
	}
	
	@Test(expected=WrongDestinationException.class)
	public final void moveSharedFolderInWrongDestination() throws Exception {
		
		String sharedFolderName = "SharedFodler";
		WorkspaceSharedFolder sharedFolder = createSharedFolder(sharedFolderName, 
				ownerWorkspace.getRoot().getId());
		
		WorkspaceSharedFolder otherSharedFolder = createSharedFolder("otherSharedFolder",
				ownerWorkspace.getRoot().getId());
		
		otherSharedFolder.move(sharedFolder);
		
	}
	
	@Test
	public final void moveItemsOnSharedFolder() throws Exception {
		
		String sharedFolderName = "SharedFodler";
		WorkspaceSharedFolder sharedFolder = createSharedFolder(sharedFolderName, 
				ownerWorkspace.getRoot().getId());
		
		WorkspaceFolder folder = sharedFolder.createFolder("ItemFolder","" );
		WorkspaceFolder folder1 = sharedFolder.createFolder("ItemFolder1", "");
		folder.move(folder1);
		
	}
	
	@Test(expected=WrongDestinationException.class)
	public final void moveFolderContainingSharedItemOnOtherSharedFolder() throws Exception {
		
		String sharedFolderName = "Shared Fodler";
		String folderName = "Folder or in bla bla";
		
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(folderName, "");
		createSharedFolder(sharedFolderName, 
				folder.getId());
		
		String otherSharedFolderName = "Other  or in  ,SharedFolder";
		WorkspaceSharedFolder otherSharedFolder = createSharedFolder(otherSharedFolderName,
				ownerWorkspace.getRoot().getId());
		
		folder.move(otherSharedFolder);
		
	}
	
	@Test
	public final void shareFolderWithItems() throws Exception{
		
		String name = "SharedFolder";
		
		int itemsSize = 10;
		WorkspaceFolder folder = ownerWorkspace.getRoot().createFolder(name, "");
		fillSharedFolder(folder, itemsSize);
		
		List<String> list = new ArrayList<String>();
		for (Workspace workspace : userWorkspaces.values()) {
			list.add(workspace.getOwner().getPortalLogin());
		}
		
		WorkspaceSharedFolder sharedFolder = folder.share(list);		
		checkContentSharedFolder(sharedFolder.getId(), itemsSize, false);

	}

	@Test
	public final void unShareFolder() throws Exception {
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder("SharedFolder", ownerWorkspace.getRoot().getId());
		String sharedFolderId = sharedFolder.getId();
		
		int itemsSize = 10;
		fillSharedFolder(sharedFolder, itemsSize);
		
		WorkspaceFolder folder = sharedFolder.unShare();
		folder.createFolder("Folder", "");
		
		assertEquals(folder.getChildren().size(), itemsSize + 1);
		
		Workspace userWorkspace = userWorkspaces.get("user.test1");
		assertEquals(userWorkspace.getItem(sharedFolderId).getChildren().size(), itemsSize);

	}
	
	@Test
	public final void removeOwnerSharedFolder() throws Exception {
		
		WorkspaceSharedFolder sharedFolder = createSharedFolder("SharedFodler",
				ownerWorkspace.getRoot().getId());
		
		String sharedFolderId = sharedFolder.getId();
		String owner = sharedFolder.getOwner().getPortalLogin();
		
		sharedFolder.remove();
		
		for (Workspace userWorkspace : userWorkspaces.values()) {
			
			WorkspaceSharedFolder userSharedFolder = (WorkspaceSharedFolder) userWorkspace.getItem(sharedFolderId);
			assertFalse(userSharedFolder.getUsers().contains(owner));
			
		}
	}
		
	
	@Test
	public final void removeSharedItemsByOwner() throws Exception {
		
		String sharedFolderName = "SharedFodler";
		WorkspaceSharedFolder sharedFolder = createSharedFolder(sharedFolderName,
				ownerWorkspace.getRoot().getId());
		
		fillSharedFolder(sharedFolder, 1);
		
		for (WorkspaceItem item : sharedFolder.getChildren()) {
			item.remove();
		}
		
		Workspace userWorkspace = userWorkspaces.get("user.test1");
		WorkspaceSharedFolder userSharedFolder = (WorkspaceSharedFolder) userWorkspace.getItem(sharedFolder.getId());
		
		fillSharedFolder(userSharedFolder, 2);
		for (WorkspaceItem item : userSharedFolder.getChildren()) {
			item.remove();
		}
		
		fillSharedFolder(userSharedFolder, 2);
		for (WorkspaceItem item : sharedFolder.getChildren()) {
			item.remove();
		}
	}
	
	@Test(expected=InsufficientPrivilegesException.class)
	public final void removeSharedItemsByDifferentOwner() throws Exception {
		
		String sharedFolderName = "SharedFodler";
		WorkspaceSharedFolder sharedFolder = createSharedFolder(sharedFolderName,
				ownerWorkspace.getRoot().getId());
		
		fillSharedFolder(sharedFolder, 1);
		
		Workspace userWorkspace = userWorkspaces.get("user.test1");
		WorkspaceSharedFolder userSharedFolder = (WorkspaceSharedFolder) userWorkspace.getItem(sharedFolder.getId());
		for (WorkspaceItem item : userSharedFolder.getChildren()) {
			item.remove();
		}
	}*/


	
}
