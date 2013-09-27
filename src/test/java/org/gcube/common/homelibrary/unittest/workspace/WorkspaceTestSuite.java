/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace;

import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTest;
import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTestCloneMethods;
import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTestCopyMethods;
import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTestCreateDocumentMethods;
import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTestCreateExternalMethods;
import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTestFolderMethods;
import org.gcube.common.homelibrary.unittest.workspace.test.WorkspaceTestItemMethods;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

	WorkspaceTest.class,

	/** 
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#cloneItem(java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceTestCloneMethods.class,

	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#copy(java.lang.String, java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceTestCopyMethods.class,
	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createImageDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createPDFDocument(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.util.Map, java.util.Map, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceTestCreateDocumentMethods.class,
	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalImage(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createExternalPDFFile(java.lang.String, java.lang.String, java.io.InputStream, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceTestCreateExternalMethods.class,
	/**
	 * <ul>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#createFolder(java.lang.String, java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeChild(java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceTestFolderMethods.class,

	/**
	 * <ul>
	 * 	<li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#changeDescription(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#exists(String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#getItem(java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#moveItem(java.lang.String, java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#removeItem(java.lang.String)}</li>
	 *  <li>{@link org.gcube.portlets.user.homelibrary.home.workspace.Workspace#renameItem(java.lang.String, java.lang.String)}</li>
	 * </ul>
	 */
	WorkspaceTestItemMethods.class
})
public class WorkspaceTestSuite extends TestSuite {

}
