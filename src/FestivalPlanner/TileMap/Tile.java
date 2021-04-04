package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Contains attributes and methods for an individual tile.
 */
public class Tile {

    private BufferedImage image;
    private int key;

    /**
     * Constructor for <b>Tile</b>.
     * @param image  the image that the tile represents
     * @param key  the id for this tile
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
     * Draws this tile on the <b>FXGraphics2D</b> parameter.
     * @param g2d  the object to draw on
     * @param x  the x-position to start drawing at
     * @param y  the y-position to start drawing at
     */
    public void draw(FXGraphics2D g2d, int x, int y){
        g2d.drawImage(this.image, x, y, null);
    }

    /**
     * Draws this tile to the <b>Graphics2D</b> parameter.
     * @param g2d  the object to draw on
     * @param x  the x-position to start drawing at
     * @param y  the y-position to start drawing at
     */
    public void draw(Graphics2D g2d, int x, int y){
        g2d.drawImage(this.image, x, y, null);
    }
}
