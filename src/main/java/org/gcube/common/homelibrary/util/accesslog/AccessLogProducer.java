/**
 * 
 */
package org.gcube.common.homelibrary.util.accesslog;

import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.events.WorkspaceEvent;
import org.gcube.common.homelibrary.home.workspace.events.WorkspaceListener;
import org.gcube.common.homelibrary.home.workspace.events.WorkspaceSentEvent;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class AccessLogProducer implements WorkspaceListener {

	protected User user;

	/**
	 * @param user workspace user.
	 */
	public AccessLogProducer(User user) {
		this.user = user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void workspaceEvent(WorkspaceEvent event) {
		WorkspaceItem target = event.getTarget();
//		System.out.println("Event: "+event.getType()+" target: "+target);

		try{
			if (target.getType()==WorkspaceItemType.FOLDER_ITEM){
				FolderItem folderItem = (FolderItem)target;
				switch(event.getType()){
					case ITEM_CREATED: AccessLogUtil.logFolderItemCreated(user, folderItem); break;
					case ITEM_REMOVED: AccessLogUtil.logFolderItemRemoved(user, folderItem); break;
					case ITEM_IMPORTED: AccessLogUtil.logFolderItemImported(user, folderItem); break;
				}
			}

			switch(event.getType()){
				case ITEM_SENT: {
					WorkspaceSentEvent sentEvent = (WorkspaceSentEvent) event;
					AccessLogUtil.logItemSent(user, target, sentEvent.getAddressees()); 
					break;
				}
			}
		}catch(InternalErrorException e)
		{

		}

	}
}
