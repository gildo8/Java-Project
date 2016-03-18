/**
 * @Project: mvp
 * @Class : CommonCharacter.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


/**
 * The Class CommonCharacter implementes Character's Interface and representes all
 * the Common for characters.
 */
public abstract class CommonCharacter extends Canvas implements Character {

	/** The cell x. */
	int cellX = 0;
	
	/** The cell y. */
	int cellY = 0;
	
	
	/**
	 * Instantiates a new common character.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	CommonCharacter(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see view.gui.Character#drawCharacter()
	 */
	@Override
	public void drawCharacter() {
		this.redraw();
		
	}
	

	/**
	 * Gets the cell x.
	 *
	 * @return the cell x
	 */
	public int getCellX() {
		return cellX;
	}

	/**
	 * Sets the cell x.
	 *
	 * @param cellX the new cell x
	 */
	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	/**
	 * Gets the cell y.
	 *
	 * @return the cell y
	 */
	public int getCellY() {
		return cellY;
	}

	/**
	 * Sets the cell y.
	 *
	 * @param cellY the new cell y
	 */
	public void setCellY(int cellY) {
		this.cellY = cellY;
	}

	/* (non-Javadoc)
	 * @see view.gui.Character#getCharacterImagesArray()
	 */
	@Override
	public abstract ImageData[] getCharacterImagesArray();

	/* (non-Javadoc)
	 * @see view.gui.Character#setCharacterImageArray(org.eclipse.swt.graphics.ImageData[])
	 */
	@Override
	public abstract void setCharacterImageArray(ImageData[] image);

	/* (non-Javadoc)
	 * @see view.gui.Character#getCharacterImageIndex()
	 */
	@Override
	public abstract int getCharacterImageIndex();

	/* (non-Javadoc)
	 * @see view.gui.Character#setCharacterImageIndex(int)
	 */
	@Override
	public abstract void setCharacterImageIndex(int index);


	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public abstract String getImagePath();

	/**
	 * Sets the image path.
	 *
	 * @param imagePath the new image path
	 */
	/* (non-Javadoc)
	 * @see view.gui.GUICharacter#setImagePath(java.lang.String)
	 */
	public abstract void setImagePath(String imagePath);
	

}
