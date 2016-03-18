/**
 * @Project: mvp
 * @Class : MazeCharacter.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;


/**
 * The Class MazeCharacter implementation of Character it's image and directions pic.
 */
public class MazeCharacter extends CommonCharacter {
	
	/** The gifs. */
	ImageLoader gifs = new ImageLoader();
	
	/** The image path. */
	String imagePath;
	
	/** The images. */
	ImageData [] images;
	
	/** The frame index. */
	int frameIndex = 0;
	
	/**
	 * Instantiates a new maze character.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	MazeCharacter(Composite parent, int style) {
		super(parent, style);
		this.images = gifs.load(".\\resources\\images\\sonic_right.gif"); //Character's gif
	}
	
	/**
	 * Instantiates a new maze character.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param path the path
	 */
	MazeCharacter(Composite parent, int style , String path) {
		super(parent, style);
		this.images = gifs.load(".\\resources\\images\\" + path);	//Character's gif for Direction change
	}

	/* (non-Javadoc)
	 * @see view.gui.CommonCharacter#getCharacterImagesArray()
	 */
	@Override
	public ImageData[] getCharacterImagesArray() {
		
		return images;
	}

	/* (non-Javadoc)
	 * @see view.gui.CommonCharacter#setCharacterImageArray(org.eclipse.swt.graphics.ImageData[])
	 */
	@Override
	public void setCharacterImageArray(ImageData[] image) {
		this.images = image;

	}

	/* (non-Javadoc)
	 * @see view.gui.CommonCharacter#getCharacterImageIndex()
	 */
	@Override
	public int getCharacterImageIndex() {
		
		return frameIndex;
	}

	/* (non-Javadoc)
	 * @see view.gui.CommonCharacter#setCharacterImageIndex(int)
	 */
	@Override
	public void setCharacterImageIndex(int index) {
		this.frameIndex = index;
	}
	
	/* (non-Javadoc)
	 * @see view.gui.CommonCharacter#getImagePath()
	 */
	public String getImagePath() {
		return imagePath;
	}

	/* (non-Javadoc)
	 * @see view.gui.CommonMazeCharacter#setImagePath(java.lang.String)
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
