/**
 * @Project: mvp
 * @Class : Character.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import org.eclipse.swt.graphics.ImageData;


/**
 * The Interface Character , represents the game's Character and all it's functions.
 */
public interface Character {
	
	/**
	 * Draw character.
	 */
	public void drawCharacter();
	
	/**
	 * Gets the character images array.
	 *
	 * @return the character images array
	 */
	public ImageData[] getCharacterImagesArray();
	
	/**
	 * Sets the character image array.
	 *
	 * @param image the new character image array
	 */
	public void setCharacterImageArray(ImageData[] image);
	
	/**
	 * Gets the character image index.
	 *
	 * @return the character image index
	 */
	public int getCharacterImageIndex();
	
	/**
	 * Sets the character image index.
	 *
	 * @param index the new character image index
	 */
	public void setCharacterImageIndex(int index);

}
