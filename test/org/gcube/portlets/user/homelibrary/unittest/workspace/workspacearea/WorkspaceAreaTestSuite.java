/**
 * 
 */
package org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea;

import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTest;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTestCloneMethods;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTestCopyMethods;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTestCreateDocumentMethods;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTestCreateExternalMethods;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTestFolderMethods;
import org.gcube.portlets.user.homelibrary.unittest.workspace.workspacearea.test.WorkspaceAreaTestItemMethods;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

	WorkspaceAreaTest.class,

	/** 
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#cloneItem(java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceAreaTestCloneMethods.class,

	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#copy(java.lang.String, java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceAreaTestCopyMethods.class,
	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceAreaTestCreateDocumentMethods.class,
	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceAreaTestCreateExternalMethods.class,
	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createBasket(java.lang.String, java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#createWorkspace(java.lang.String, java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeChild(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getDefaultBasket()}</li>
	 * </ul>
	 */
	WorkspaceAreaTestFolderMethods.class,

	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.fs.home.workspace.WorkspaceArea#changeDescription(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#exists(String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#getItem(java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#moveItem(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#removeItem(java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.WorkspaceArea#renameItem(java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceAreaTestItemMethods.class
})
public class WorkspaceAreaTestSuite extends TestSuite {

}
