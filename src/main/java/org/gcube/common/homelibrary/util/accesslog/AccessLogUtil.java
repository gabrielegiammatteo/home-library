/**
 * 
 */
package org.gcube.common.homelibrary.util.accesslog;

import java.util.List;

import org.gcube.application.framework.accesslogger.library.impl.AccessLogger;
import org.gcube.application.framework.accesslogger.model.AccessLogEntry;
import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.util.WorkspaceTypes;
import org.gcube.common.scope.api.ScopeProvider;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class AccessLogUtil {
	
	private static final String USER_SCOPE_SEPARATOR = ":";

	protected static final String USERS_SEPARATOR = ";";

	/**
	 * 
	 */
	public final static char ATTRIBUTE_SEPARATOR = '|';
	
	/**
	 * 
	 */
	public final static String LABEL_SEPARATOR = " = ";
	
	/**
	 * 
	 */
	public final static String ITEM_ID_LABEL = "ID";
	/**
	 * 
	 */
	public final static String ITEM_NAME_LABEL = "NAME";
	/**
	 * 
	 */
	public final static String ITEM_TYPE_LABEL = "TYPE";
	/**
	 * 
	 */
	public final static String ADDRESSEES_LABEL = "ADDRESSEES";
	
	protected static void logAction(User user, HLAccessLogEntryType action, String message)
	{
		AccessLogger accessLogger = AccessLogger.getAccessLogger();
		
		accessLogger.logEntry(user.getPortalLogin(), ScopeProvider.instance.get(), new HLAccessLogEntry(action, message));
		
	}
	
	protected static void logItemAction(User user, FolderItem item, HLAccessLogEntryType type) throws InternalErrorException
	{
		StringBuilder message = new StringBuilder(ITEM_ID_LABEL);
		message.append(LABEL_SEPARATOR);
		message.append(item.getId());
		message.append(ATTRIBUTE_SEPARATOR);
		message.append(ITEM_NAME_LABEL);
		message.append(LABEL_SEPARATOR);
		message.append(AccessLogEntry.replaceReservedChars(item.getName()));
		message.append(ATTRIBUTE_SEPARATOR);
		message.append(ITEM_TYPE_LABEL);
		message.append(LABEL_SEPARATOR);
		message.append(WorkspaceTypes.getItemTypeAsString(item));
		logAction(user, type, message.toString());
	}
	
	/**
	 * Log an item created action
	 * @param user the user who made the action.
	 * @param item the created item.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public static void logFolderItemCreated(User user, FolderItem item) throws InternalErrorException
	{
		logItemAction(user, item, HLAccessLogEntryType.HL_FOLDER_ITEM_CREATED);		
	}
	
	/**
	 * Log an item removed action
	 * @param user the user who made the action.
	 * @param item the removed item.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public static void logFolderItemRemoved(User user, FolderItem item) throws InternalErrorException
	{
		logItemAction(user, item, HLAccessLogEntryType.HL_FOLDER_ITEM_REMOVED);
	}
	
	/**
	 * Log an item imported action
	 * @param user the user who made the action.
	 * @param item the imported item.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public static void logFolderItemImported(User user, FolderItem item) throws InternalErrorException
	{
		logItemAction(user, item, HLAccessLogEntryType.HL_FOLDER_ITEM_IMPORTED);
	}
	
	/**
	 * Log an item sent action.
	 * @param user the user who made the action.
	 * @param item the sent item.
	 * @param addressees the addressees users.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public static void logItemSent(User user, WorkspaceItem item, List<User> addressees) throws InternalErrorException
	{
		StringBuilder message = new StringBuilder(ITEM_ID_LABEL);
		message.append(LABEL_SEPARATOR);
		message.append(item.getId());
		message.append(ATTRIBUTE_SEPARATOR);
		message.append(ITEM_NAME_LABEL);
		message.append(LABEL_SEPARATOR);
		message.append(item.getName());
		message.append(ATTRIBUTE_SEPARATOR);
		message.append(ITEM_TYPE_LABEL);
		message.append(LABEL_SEPARATOR);
		message.append(WorkspaceTypes.getItemTypeAsString(item));
		message.append(ATTRIBUTE_SEPARATOR);
		message.append(ADDRESSEES_LABEL);
		message.append(LABEL_SEPARATOR);
		
		for (int i = 0; i<addressees.size(); i++){
			User addressee = addressees.get(i);			
			message.append(addressee.getPortalLogin());
			message.append(USER_SCOPE_SEPARATOR);
			message.append(ScopeProvider.instance.get());

			if (i<addressees.size()-1) message.append(USERS_SEPARATOR);
		}
		logAction(user, HLAccessLogEntryType.HL_ITEM_SENT, message.toString());
	}
	
	/**
	 * Log a workspace creation.
	 * @param user the workspace owner.
	 */
	public static void logWorkspaceCreated(User user)
	{
		logAction(user, HLAccessLogEntryType.HL_WORKSPACE_CREATED, "");
	}

}
