/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.accounting;

import java.util.Calendar;



/**
 * @author gioia
 *
 */
public interface AccountingEntry {
	
	String getUser();
	
	Calendar getDate();
	
	AccountingEntryType getEntryType();


}
