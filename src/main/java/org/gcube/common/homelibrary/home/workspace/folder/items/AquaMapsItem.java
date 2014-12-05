/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.folder.items;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
@Deprecated
public interface AquaMapsItem extends FolderItem {
	
	/**
	 * Returns the map name.
	 * @return the map name.
	 */
	public String getMapName();

	/**
	 * Returns the map type.
	 * @return the map type.
	 */
	public String getMapType();

	/**
	 * Returns the item author.
	 * @return the author
	 */
	public String getAuthor();

	/**
	 * Returns the number of species selected.
	 * @return the numberOfSpecies
	 */
	public int getNumberOfSpecies();

	/**
	 * Returns the bounding box.
	 * @return the boundingBox
	 */
	public String getBoundingBox();

	/**
	 * Returns the PSO threshold.
	 * @return the psoThreshold
	 */
	public float getPsoThreshold();

	/**
	 * Returns the number of generated images.
	 * @return the numberOfGeneratedImages
	 */
	public int getNumberOfGeneratedImages();

	/**
	 * Return the metadata file.
	 * @return the metadata file.
	 * @throws InternalErrorException if an error occurs.
	 */
	public File getMetadata() throws InternalErrorException;
	
	/**
	 * Return the images associated with this AquamapsItem.
	 * @return a list of images.
	 */
	public List<Image> getImages();

}
