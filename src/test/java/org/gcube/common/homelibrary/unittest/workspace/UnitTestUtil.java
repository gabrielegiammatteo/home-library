/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItem;
import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemAlreadyExistException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WorkspaceFolderNotFoundException;
import org.gcube.common.homelibrary.home.workspace.exceptions.WrongDestinationException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.AquaMapsItem;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalImage;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalPDFFile;
import org.gcube.common.homelibrary.home.workspace.folder.items.ExternalUrl;
import org.gcube.common.homelibrary.home.workspace.folder.items.File;
import org.gcube.common.homelibrary.home.workspace.folder.items.Image;
import org.gcube.common.homelibrary.home.workspace.folder.items.PDF;
import org.gcube.common.homelibrary.home.workspace.folder.items.Query;
import org.gcube.common.homelibrary.home.workspace.folder.items.Report;
import org.gcube.common.homelibrary.home.workspace.folder.items.ReportTemplate;
import org.gcube.common.homelibrary.home.workspace.folder.items.Url;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Document;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.DocumentMetadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.ImageDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.Metadata;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.PDFDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.UrlDocument;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentAlternativeLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.gcube.link.DocumentPartLink;
import org.gcube.common.homelibrary.home.workspace.folder.items.ts.TimeSeries;
import org.gcube.common.homelibrary.testdata.TestDataFactory;

import static org.junit.Assert.*;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class UnitTestUtil {

	/**
	 * Generate a random ID.
	 * @return the generated ID.
	 */
	public static final String getID()
	{
		return UUID.randomUUID().toString();
	}

	/**
	 * Utility method for item creation.
	 * This method test created item and destination folder.
	 * @param item the item to test.
	 * @param expectedParent the expected parent.
	 * @param expectedName the expected name.
	 * @param expectedDescription the expected description.
	 * @param expectedItemType the expected item type.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static final void testItemCreation(WorkspaceItem item, WorkspaceItem expectedParent, String expectedName, String expectedDescription, WorkspaceItemType expectedItemType) throws InternalErrorException
	{
		assertNotNull("Created item null", item);
		assertEquals("Created item have an unexpected name", expectedName, item.getName());
		assertEquals("Created item have an unexpected description", expectedDescription, item.getDescription());

		assertEquals("One child expected on the parent", 1, expectedParent.getChildren().size());

		testRelationship(item, expectedParent);

		WorkspaceItemType itemType = item.getType();

		assertEquals("Wrong workspace item type", expectedItemType, itemType);
	}

	/**
	 * Utility test method, check relationship between an expected child item and a expected parent item.
	 * This method check if the expected parent children list contains the expected child and if the expected child parent is the expected parent.
	 * @param expectedChild the expected children.
	 * @param expectedParent the expected parent.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static final void testRelationship(WorkspaceItem expectedChild, WorkspaceItem expectedParent) throws InternalErrorException
	{
		boolean found = false;

		for (WorkspaceItem child : expectedParent.getChildren()){
			if (child.getId().equals(expectedChild.getId())) found = true;
		}

		assertTrue("The expected children has not found in the expected parent.", found);
		String parentId = expectedChild.getParent().getId();
		String expectedParentId = expectedParent.getId();

		assertEquals("The expected child parent is wrong", expectedParentId, parentId);

	}

	/**
	 * Utility test method, check relationship between an expected child item and a expected parent item in an entire tree.
	 * @param expectedChild the expected child.
	 * @param expectedParent the expected parent.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static final void testTreeRelationship(WorkspaceItem expectedChild, WorkspaceItem expectedParent) throws InternalErrorException
	{
		testRelationship(expectedChild, expectedParent);

		for (WorkspaceItem child:expectedChild.getChildren())
		{
			testTreeRelationship(child, expectedChild);
		}

	}

	/**
	 * Check if an item is equals to another one.
	 * The check is performed recursively with the children.
	 * @param itemA the first child.
	 * @param itemB the second child.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static final void testWorkspaceItemEquality(WorkspaceItem itemA, WorkspaceItem itemB) throws InternalErrorException
	{
		assertEquals("Different id", itemA.getId(), itemB.getId());
		assertEquals("Different name", itemA.getName(), itemB.getName());
		assertEquals("Different description", itemA.getDescription(), itemB.getDescription());
		assertEquals("Different creation time", itemA.getCreationTime(), itemB.getCreationTime());
		assertEquals("Different last modification time", itemA.getLastModificationTime(), itemB.getLastModificationTime());
		assertEquals("Different last action", itemA.getLastAction(), itemB.getLastAction());
		assertEquals("Different owner", itemA.getOwner().getPortalLogin(), itemB.getOwner().getPortalLogin());
		assertEquals("Different type", itemA.getType(), itemB.getType());
		assertEquals("Different parent", itemA.getParent().getId(), itemB.getParent().getId());

		testChildren(itemA, itemB);
	}

	/**
	 * Check if two item children are equals.
	 * @param itemA the first child.
	 * @param itemB the second child.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static final void testChildren(WorkspaceItem itemA, WorkspaceItem itemB) throws InternalErrorException
	{
		List<? extends WorkspaceItem> itemAChildren = itemA.getChildren();
		List<? extends WorkspaceItem> itemBChildren = itemB.getChildren();

		assertEquals("Children cardinality different", itemAChildren.size(), itemBChildren.size());

		Map<String, WorkspaceItem> itemAChildrenMap = new LinkedHashMap<String, WorkspaceItem>();
		for (WorkspaceItem child:itemAChildren) itemAChildrenMap.put(child.getId(), child);

		Map<String, WorkspaceItem> itemBChildrenMap = new LinkedHashMap<String, WorkspaceItem>();
		for (WorkspaceItem child:itemBChildren) itemBChildrenMap.put(child.getId(), child);

		for (Map.Entry<String, WorkspaceItem> child:itemAChildrenMap.entrySet()) {
			if (!itemBChildrenMap.containsKey(child.getKey())) fail("Child id on A "+child.getKey()+" not containted in B");

			WorkspaceItem childA = child.getValue();
			WorkspaceItem childB = itemBChildrenMap.get(childA.getId());

			testWorkspaceItemEquality(childA, childB);

		}

		for (String childId:itemBChildrenMap.keySet()) if (!itemAChildrenMap.keySet().contains(childId)) fail("Child id on B "+childId+" not containted in A");

	}

	/**
	 * Utility test for clone testing.
	 * @param item the original item.
	 * @param clone the item to test.
	 * @param differentDestination if the two element have different destinations.
	 * @throws InternalErrorException if an error occurs.
	 */
	public static final void testCopy(WorkspaceItem item, WorkspaceItem clone, boolean differentDestination) throws InternalErrorException
	{
		assertNotSame("Same id", item.getId(), clone.getId());

		if (differentDestination) assertEquals("Different name", item.getName(), clone.getName()); 
		else assertNotSame("Same name", item.getName(), clone.getName());

		assertEquals("Different description", item.getDescription(), clone.getDescription());
		
		if ((item.getOwner() != null ) && (clone.getOwner() != null))
			assertEquals("Different owner", item.getOwner().getPortalLogin(), clone.getOwner().getPortalLogin());

		assertEquals("Different type", item.getType(), clone.getType());

		if (differentDestination) assertNotSame("Parent equals", item.getParent(), clone.getParent());
		else assertEquals("Different parents", item.getParent().getId(), clone.getParent().getId());

		List<? extends WorkspaceItem> itemChildren = item.getChildren();
		List<? extends WorkspaceItem> cloneChildren = clone.getChildren();
		System.err.println("ItemChildren " + itemChildren + "of itemType" + item.getType() );
		System.err.println("CloneChildren " + cloneChildren + "of cloneType" + clone.getType());
		
		assertEquals("Different number of children", itemChildren.size(), cloneChildren.size());

		for (WorkspaceItem itemChild:itemChildren){
			WorkspaceItem cloneChild = null;
			for (WorkspaceItem candidateChild:cloneChildren) {
				if (candidateChild.getName().equals(itemChild.getName())) {
					cloneChild = candidateChild;
					break;
				}
			}

			assertNotNull("Corresponding child not found for "+itemChild, cloneChild);
			internalTestCopy(itemChild, cloneChild);
		}

	}

	protected static final void internalTestCopy(WorkspaceItem item, WorkspaceItem clone) throws InternalErrorException
	{
		assertNotSame("Same id", item.getId(), clone.getId());
		assertEquals("Different name", item.getName(), clone.getName()); 
		assertEquals("Different description", item.getDescription(), clone.getDescription());
		
		if((item.getOwner() != null) && (item.getOwner() != null));
			assertEquals("Different owner", item.getOwner().getPortalLogin(), clone.getOwner().getPortalLogin());
		
		assertEquals("Different type", item.getType(), clone.getType());
		assertNotSame("Parent equals", item.getParent(), clone.getParent());

		switch (item.getType()) {
			case FOLDER_ITEM: internalTestCopyFolderItem((FolderItem)item, (FolderItem)clone); break;
			default:
				break;
		}

		List<? extends WorkspaceItem> itemChildren = item.getChildren();
		List<? extends WorkspaceItem> cloneChildren = clone.getChildren();

		assertEquals("Different number of children", itemChildren.size(), cloneChildren.size());

		for (WorkspaceItem itemChild:itemChildren){
			WorkspaceItem cloneChild = null;
			for (WorkspaceItem candidateChild:cloneChildren) {
				if (candidateChild.getName().equals(itemChild.getName())) {
					cloneChild = candidateChild;
					break;
				}
			}

			assertNotNull("Corresponding child not found for "+itemChild, cloneChild);
			internalTestCopy(itemChild, cloneChild);
		}

	}

	protected static final void internalTestCopyFolderItem(FolderItem item, FolderItem clone) throws InternalErrorException
	{
		assertEquals("Different folder item type", item.getFolderItemType(), clone.getFolderItemType());
		assertEquals("Different length", item.getLength(), clone.getLength());
		assertEquals("Different Workflow Id", item.getWorkflowId(), clone.getWorkflowId());
		assertEquals("Different Workflow Status", item.getWorkflowStatus(), clone.getWorkflowStatus());
		assertEquals("Different Workflow Data", item.getWorkflowData(), clone.getWorkflowData());

		switch (item.getFolderItemType()) {
			case EXTERNAL_FILE: internalTestCopyFile((ExternalFile)item, (ExternalFile)clone); break;
			case EXTERNAL_IMAGE: internalTestCopyImage((ExternalImage)item, (ExternalImage)clone); break;
			case EXTERNAL_PDF_FILE:{
				internalTestCopyFile((ExternalPDFFile)item, (ExternalPDFFile)clone);
				internalTestCopyPDF((ExternalPDFFile)item, (ExternalPDFFile)clone);
			}break;
			case EXTERNAL_URL: internalTestCopyUrl((ExternalUrl)item, (ExternalUrl)clone); break;
			case TIME_SERIES: internalTestCopyTimeSeries((TimeSeries)item, (TimeSeries)clone); break;
			case QUERY: internalTestCopyQuery((Query)item, (Query)clone); break;
			case REPORT: internalTestCopyReport((Report)item, (Report)clone); break;
			case REPORT_TEMPLATE: internalTestCopyReportTemplate((ReportTemplate)item, (ReportTemplate)clone); break;
			case AQUAMAPS_ITEM: internalTestCopyAquaMapsItem((AquaMapsItem)item, (AquaMapsItem)clone); break;
			case DOCUMENT: internalTestCopyDocument((Document)item, (Document)clone); break;
			case IMAGE_DOCUMENT:{
				internalTestCopyDocument((ImageDocument)item, (ImageDocument)clone);
				internalTestCopyImage((ImageDocument)item, (ImageDocument)clone);
			}break;
			case PDF_DOCUMENT:{
				internalTestCopyDocument((PDFDocument)item, (PDFDocument)clone);
				internalTestCopyPDF((PDFDocument)item, (PDFDocument)clone);
			}break;
			case URL_DOCUMENT:{
				internalTestCopyDocument((UrlDocument)item, (UrlDocument)clone);
				internalTestCopyUrl((UrlDocument)item, (UrlDocument)clone);
			}break;
			case METADATA:internalTestCopyMetadata((Metadata)item, (Metadata)clone); break;
			default:
				//Workflow Report
				//Workflow Template
				break;
		}

	}

	protected static final void internalTestCopyFile(File item, File clone) throws InternalErrorException
	{
		assertEquals("Different name", item.getName(), clone.getName());
		assertEquals("Different mimetype", item.getMimeType(), clone.getMimeType());
		assertEquals("Different length", item.getLength(), clone.getLength());

		try {
			boolean sameContent = IOUtils.contentEquals(item.getData(), clone.getData());
			assertTrue("The data are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}

	}

	protected static final void internalTestCopyImage(Image item, Image clone) throws InternalErrorException
	{
		internalTestCopyFile(item, clone);
		assertEquals("Different widths", item.getWidth(), clone.getWidth());
		assertEquals("Different heights", item.getHeight(), clone.getHeight());
		assertEquals("Different thumbnail widths", item.getThumbnailWidth(), clone.getThumbnailHeight());
		assertEquals("Different thumbnail height", item.getThumbnailHeight(), clone.getThumbnailHeight());
		assertEquals("Different thumbnail length", item.getThumbnailLength(), clone.getThumbnailLength());
		try {
			boolean sameContent = IOUtils.contentEquals(item.getThumbnail(), clone.getThumbnail());
			assertTrue("The thumbnail content are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}
	}

	protected static final void internalTestCopyPDF(PDF item, PDF clone) throws InternalErrorException
	{
		assertEquals("Different titles", item.getTitle(), clone.getTitle());
		assertEquals("Different authors", item.getAuthor(), clone.getAuthor());
		assertEquals("Different producers", item.getProducer(), clone.getProducer());
		assertEquals("Different versions", item.getVersion(), clone.getVersion());
		assertEquals("Different number of pages", item.getNumberOfPages(), clone.getNumberOfPages());
	}

	protected static final void internalTestCopyUrl(Url item, Url clone) throws InternalErrorException
	{
		assertEquals("Different url values", item.getUrl(), clone.getUrl());
	}

	protected static final void internalTestCopyDocument(Document item, Document clone) throws InternalErrorException
	{
		assertEquals("Different oid values", item.getURI(), clone.getURI());
		assertEquals("Different mimetype", item.getMimeType(), clone.getMimeType());
		assertEquals("Different collection name", item.getCollectionName(), clone.getCollectionName());

		
		System.err.println("ITEM METADATA :" + item.getMetadata());
		System.err.println("CLONE METADA "+ clone.getMetadata());
		assertEquals("Different Metadata cardinality", item.getMetadata().size(), clone.getMetadata().size());
		
		assertTrue("Different metadata (keys)",item.getMetadata().keySet().containsAll(clone.getMetadata().keySet()));
		
		
		for (Entry<String, DocumentMetadata> entry:item.getMetadata().entrySet()) {
			
			DocumentMetadata cloneMetadata = clone.getMetadata().get(entry.getKey());
			
			assertEquals("(Metadata) Different schema name", entry.getValue().getSchemaName(), cloneMetadata.getSchemaName());
			assertEquals("(Metadata) Different xmls", entry.getValue().getXML(), cloneMetadata.getXML());
		}

		assertEquals("Different Alternatives cardinality", item.getAlternatives().size(), clone.getAlternatives().size());
		for (int i =0; i<item.getAlternatives().size(); i++){
			DocumentAlternativeLink itemAlternative = item.getAlternatives().get(i);
			DocumentAlternativeLink cloneAlternative = clone.getAlternatives().get(i);

			assertEquals("(Alternative) Different parent oid", itemAlternative.getParentURI(), cloneAlternative.getParentURI());
			assertEquals("(Alternative) Different oid", itemAlternative.getURI(), cloneAlternative.getURI());
			assertEquals("(Alternative) Different name", itemAlternative.getName(), cloneAlternative.getName());
			assertEquals("(Alternative) Different mime type", itemAlternative.getMimeType(), cloneAlternative.getMimeType());
		}

		assertEquals("Different Annotation cardinality", item.getAnnotation().size(), clone.getAnnotation().size());
		assertTrue("Different Annotation (keys)",item.getAnnotation().keySet().containsAll(clone.getAnnotation().keySet()));
		for (Entry<String, String> entry:item.getAnnotation().entrySet()) {
			String cloneAnnotation = clone.getAnnotation().get(entry.getKey());
			assertEquals("(Annotation) Different annotation", entry.getValue(), cloneAnnotation);
		}

		assertEquals("Different Parts cardinality", item.getParts().size(), clone.getParts().size());
		for (int i =0; i<item.getParts().size(); i++){
			DocumentPartLink itemPart = item.getParts().get(i);
			DocumentPartLink clonePart = clone.getParts().get(i);

			assertEquals("(Part) Different parent oid", itemPart.getParentURI(), clonePart.getParentURI());
			assertEquals("(Part) Different oid", itemPart.getURI(), clonePart.getURI());
			assertEquals("(Part) Different name", itemPart.getName(), clonePart.getName());
			assertEquals("(Part) Different mime type", itemPart.getMimeType(), clonePart.getMimeType());
		}

		try {
			boolean sameContent = IOUtils.contentEquals(item.getData(), clone.getData());
			assertTrue("The data are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}
	}

	protected static final void internalTestCopyMetadata(Metadata item, Metadata clone) throws InternalErrorException
	{
		assertEquals("Different oid values", item.getURI(), clone.getURI());
		assertEquals("Different schema", item.getSchema(), clone.getSchema());
		assertEquals("Different language", item.getLanguage(), clone.getLanguage());
		assertEquals("Different collection name", item.getCollectionName(), clone.getCollectionName());		
		assertEquals("Different data", item.getData(), clone.getData());
	}

	protected static final void internalTestCopyTimeSeries(TimeSeries item, TimeSeries clone) throws InternalErrorException
	{
		assertEquals("Different TimeSeries info", item.getTimeSeriesInfo(), clone.getTimeSeriesInfo());
		assertEquals("Different number of columns", item.getNumberOfColumns(), clone.getNumberOfColumns());
		assertEquals("Different header labels", item.getHeaderLabels(), clone.getHeaderLabels());		
		try {
			boolean sameContent = IOUtils.contentEquals(item.getData(), clone.getData());
			assertTrue("The data are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}

		try {
			boolean sameContent = IOUtils.contentEquals(item.getCompressedData(), clone.getCompressedData());
			assertTrue("The compressed data are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}
	}

	protected static final void internalTestCopyQuery(Query item, Query clone) throws InternalErrorException
	{
		assertEquals("Different type", item.getQueryType(), clone.getQueryType());
		assertEquals("Different query value", item.getQuery(), clone.getQuery());
	}

	protected static final void internalTestCopyReport(Report item, Report clone) throws InternalErrorException
	{

		assertEquals("Different created", item.getCreated(), clone.getCreated());
		assertEquals("Different lastEdit", item.getLastEdit(), clone.getLastEdit());
		assertEquals("Different author", item.getAuthor(), clone.getAuthor());
		assertEquals("Different lastEditBy", item.getLastEditBy(), clone.getLastEditBy());
		assertEquals("Different templateName", item.getTemplateName(), clone.getTemplateName());
		assertEquals("Different numberOfSections", item.getNumberOfSections(), clone.getNumberOfSections());
		assertEquals("Different status", item.getStatus(), clone.getStatus());

		try {
			boolean sameContent = IOUtils.contentEquals(item.getData(), clone.getData());
			assertTrue("The data are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}
	}

	protected static final void internalTestCopyReportTemplate(ReportTemplate item, ReportTemplate clone) throws InternalErrorException
	{
		assertEquals("Different created", item.getCreated(), clone.getCreated());
		assertEquals("Different lastEdit", item.getLastEdit(), clone.getLastEdit());
		assertEquals("Different author", item.getAuthor(), clone.getAuthor());
		assertEquals("Different lastEditBy", item.getLastEditBy(), clone.getLastEditBy());
		assertEquals("Different numberOfSections", item.getNumberOfSections(), clone.getNumberOfSections());
		assertEquals("Different status", item.getStatus(), clone.getStatus());
		
		try {
			boolean sameContent = IOUtils.contentEquals(item.getData(), clone.getData());
			assertTrue("The data are differents", sameContent);
		} catch (IOException e) {
			throw new InternalErrorException(e);
		}
	}

	protected static final void internalTestCopyAquaMapsItem(AquaMapsItem item, AquaMapsItem clone) throws InternalErrorException
	{
		assertEquals("Different MapName", item.getMapName(), clone.getMapName());
		assertEquals("Different MapType", item.getMapType(), clone.getMapType());
		assertEquals("Different Author", item.getAuthor(), clone.getAuthor());
		assertEquals("Different NumberOfSpecies", item.getNumberOfSpecies(), clone.getNumberOfSpecies());
		assertEquals("Different BoundingBox", item.getBoundingBox(), clone.getBoundingBox());
		assertEquals("Different PsoThreshold", item.getPsoThreshold(), clone.getPsoThreshold(), 0);
		assertEquals("Different NumberOfGeneratedImages", item.getNumberOfGeneratedImages(), clone.getNumberOfGeneratedImages());

		internalTestCopyFile(item.getMetadata(), clone.getMetadata());

		assertEquals("Different number of images", item.getImages().size(), clone.getImages().size());
		//TODO is order fixed?
		//for (int i = 0; i<item.getImages().size(); i++) internalTestCopyImage(item.getImages().get(i), clone.getImages().get(i));
	}

	/**
	 * Create a testing workspace tree.
	 * @param root the workspace parent.
	 * @return the testin tree.
	 * @throws InternalErrorException if an error occurs.
	 * @throws InsufficientPrivilegesException if an error occurs.
	 * @throws ItemAlreadyExistException if an error occurs.
	 */
	public static final WorkspaceFolder getFolderTree(WorkspaceFolder root, String folderName) throws InternalErrorException, InsufficientPrivilegesException, ItemAlreadyExistException
	{
		WorkspaceFolder folder = root.createFolder(folderName, "A test Folder");

		TestDataFactory testDataManager = TestDataFactory.getInstance();


		//EXTERNAL_IMAGE,
		testDataManager.fillExternalImages(folder, 1);

		//EXTERNAL_FILE,
		testDataManager.fillExternalFiles(folder, 1);

		//EXTERNAL_PDF_FILE,
		testDataManager.fillExternalPDFFiles(folder, 1);

		//EXTERNAL_URL,
		testDataManager.fillExternalUrls(folder, 1);

		//QUERY,
		testDataManager.fillQueries(folder, 1);

		//REPORT_TEMPLATE,
		testDataManager.fillReportTemplates(folder, 1);

		//REPORT,
		testDataManager.fillReports(folder, 1);

		//TIME_SERIES,
		testDataManager.fillTimeSeries(folder, 1);

		//AQUAMAPS_ITEM;
		testDataManager.fillAquaMapsItems(folder,1);

		//DOCUMENT,
		testDataManager.fillDocuments(folder, 1); 

		//IMAGE_DOCUMENT,
		testDataManager.fillImageDocuments(folder, 1); 

		//PDF_DOCUMENT,
		testDataManager.fillPDFDocuments(folder, 1); 

		//URL_DOCUMENT,
		testDataManager.fillUrlDocuments(folder, 1);

		//METADATA,
		testDataManager.fillMetadata(folder, 1);

		//ANNOTATION;
		
		//WORKFLOW_REPORT,
		testDataManager.fillWorkflowReports(folder, 1);
		
		//WORKFLOW_TEMPLATE,
		testDataManager.fillWorkflowTemplates(folder, 1);


		return folder;
	}

	/**
	 * @param item the item to check.
	 * @param expectedParent the expected parent.
	 * @param expectedName the expected name
	 * @param expectedDescription the expected description. 
	 * @param expectedMimeType the expected mime type.
	 * @param expectedItemType the expected item type.
	 * @param expectedOid the expected oid.
	 * @param expectedCollectionName the expected collection name. 
	 * @param expectedMetadata the expected metadata.
	 * @param expectedAnnotation the expected annotation.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 */
	public static final void testDocumentCreation(Document item, WorkspaceItem expectedParent, String expectedName, String expectedDescription, String expectedMimeType, WorkspaceItemType expectedItemType, String expectedOid, String expectedCollectionName, Map<String, String> expectedMetadata, Map<String, String> expectedAnnotation) throws InternalErrorException
	{
		testItemCreation(item, expectedParent, expectedName, expectedDescription, expectedItemType);

		assertEquals("Different oid", expectedOid, item.getURI());
		assertEquals("Different collection name", expectedCollectionName, item.getCollectionName());
		assertEquals("Different mime type",expectedMimeType, item.getMimeType());

		//FIXME check metadata
		//checkMapEquality(expectedMetadata, item.getMetadata())

	}

	/**
	 * @param <K> key type.
	 * @param <V> value type.
	 * @param a first map.
	 * @param b second map.
	 */
	public static final <K,V> void checkMapEquality(Map<K, V> a, Map<K, V> b)
	{
		assertEquals("Different cardinality", a.size(), b.size());
		assertEquals("Different keyset cardinality", a.keySet().size(), b.keySet().size());
		for (K key:a.keySet()) if (!b.containsKey(key)) fail("Key "+key+" in A is not in B");
		for (K key:b.keySet()) if (!a.containsKey(key)) fail("Key "+key+" in B is not in A");

		assertEquals("Different valueset cardinality", a.values().size(), b.values().size());

		for (Entry<K, V> entryA:a.entrySet()){
			K key = entryA.getKey();
			if (!b.containsKey(key)) fail("Key "+key+" in A is not in B");

			V bValue = b.get(key);

			V value = entryA.getValue();
			assertEquals("Different values for key "+key, value, bValue);
		}

	}

	/**
	 * Generic test for move item method.
	 * @param item the item to test.
	 * @param destination the item destination.
	 * @param workspace the item workspace.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method.
	 * @throws InsufficientPrivilegesException if an error occurs. Please refer to documentation's method.
	 * @throws ItemNotFoundException if an error occurs. Please refer to documentation's method.
	 * @throws WrongDestinationException if an error occurs. Please refer to documentation's method.
	 * @throws ItemAlreadyExistException if an error occurs. Please refer to documentation's method. 
	 * @throws WorkspaceFolderNotFoundException if an error occurs. Please refer to documentation's method. 
	 */
	public static final void testMoveItem(WorkspaceItem item, WorkspaceFolder destination, Workspace workspace) throws InternalErrorException, InsufficientPrivilegesException, ItemNotFoundException, WrongDestinationException, ItemAlreadyExistException, WorkspaceFolderNotFoundException {

		WorkspaceFolder parent = item.getParent();

		workspace.moveItem(item.getId(), destination.getId());

		//the item parent have not to be null
		assertNotNull("The moved item parent is null",item.getParent());
		//the item old parent have to be without children
		assertEquals("Children founds on old parent", 0, parent.getChildren().size());

		try{
			workspace.getItem(item.getId());
		}catch (ItemNotFoundException e) {
			fail("Item not found after move.");
		}

		testRelationship(item, destination);

	}

	/**
	 * @param wa the workspace.
	 * @param root the subtree root.
	 * @throws InternalErrorException if an error occurs. Please refer to documentation's method. 
	 */
	public static final void checkSubTreeExistence(Workspace wa, WorkspaceItem root) throws InternalErrorException
	{
		assertFalse("Found item "+root, wa.exists(root.getId()));

		for (WorkspaceItem child:root.getChildren()) checkSubTreeExistence(wa, child);
	}

}
