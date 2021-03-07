package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;
import java.awt.image.*;

/**
 * Contains one Tile
 */
public class Tile {

    private BufferedImage image;
    private int key;

    /**
     * Constructor for Tile
     * @param image  The image that the tile represents
     * @param key  The id for this tile
     */
    public Tile(BufferedImage image, int key) {
        this.image = image;
        this.key = key;
    }

    /**
     * Returns <code>this.key</code>
     * @return  <code>this.key</code>
     */
    public int getKey() {
        return this.key;
    }

    /**
     * Draws this tile to the graphics
     * @param g2d  The object to draw to
     * @param x  The x position to start drawing
     * @param y  The y posistion to start drawing
     */
    public void draw(FXGraphics2D g2d, int x, int y){
        g2d.drawImage(this.image, x, y, null);
    }

}
