/**
 * @Project: mvp
 * @Class : Tile.java
 * @author Gil Doron
 * @since 28/10/2015	
 * @version 1.0
 */
package view.gui;

import org.eclipse.swt.events.PaintEvent;


/**
 * The Interface Tile represent a Tile.
 */
public interface Tile {
	
	/**
	 * Draw tile.
	 *
	 * @param e the e
	 */
	void drawTile(PaintEvent e);

}
