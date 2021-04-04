package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Contains attributes and methods for one Layer with tiles in <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>.
 */
public class TileLayer extends Layer {

    private TileMap tileMap;

    private int tileWidth;
    private int tileHeight;

    private Tile[][] tiles;
    private BufferedImage imageLayer;

    /**
     * Constructor for <b>Layer</b>.
     * @param tileMap the <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> that holds this <b>layer</b>
     * @param width  the width of the <b>Layer</b>
     * @param height  the height of the <b>Layer</b>
     * @param tiles  the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a> in the <b>Layer</b>
     */
    public TileLayer(TileMap tileMap, int width, int height, ArrayList<Tile> tiles) {
        super(width, height, tileMap);

        this.tileWidth = tileMap.getTileWidth();
        this.tileHeight = tileMap.getTileHeight();

        this.tiles = buildTiles(tiles);
        this.imageLayer = constructImageLayer();
    }

    /**
     * Returns a new BufferedImage based on <code>this.tiles</code>.
     * @return  a new BufferedImage base on this layer
     */
    private BufferedImage constructImageLayer() {
        BufferedImage imageLayer = new BufferedImage(this.width * this.tileWidth, this.height * tileHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageLayer.createGraphics();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x] == null) {
                    continue;
                }
                tiles[y][x].draw(g2d, x * tileWidth,y * tileHeight);
            }
        }

        return imageLayer;
    }

    /**
     * Sets <code>this.TileMap</code>. After the value is set it rebuilds <code>this.imageLayer</code>.
     * @param tileMap  the <b>TileMap</b> to set to
     */
    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
        this.imageLayer = constructImageLayer();
    }

    /**
     * Getter for <code>this.tiles</code>.
     * @return  <code>this.tiles</code>
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Builds a given ArrayList with tiles to a 2D Tile-Array.
     * @param tiles  the ArrayList with <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a>
     * @return  2D-Array with <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a>
     */
    private Tile[][] buildTiles(ArrayList<Tile> tiles) {
        Tile[][] tilesArr = new Tile[this.height][this.width];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                tilesArr[y][x] = tiles.get(y * this.width + x);
            }
        }

        return tilesArr;
    }

    /**
     * Draws the <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a> to the graphics.
     * @param g2d  object this layer needs to be drawn to
     */
    public void draw(FXGraphics2D g2d) {
        AffineTransform tx = new AffineTransform();
        g2d.drawImage(this.imageLayer, tx, null);
    }

    /**
     * Returns <code>this.tileWidth</code>.
     * @return  <code>this.tileWidth</code>
     */
    public int getTileWidth() {
        return this.tileWidth;
    }

    /**
     * Sets <code>this.tileWidth</code> to the parameter's value.
     * @param tileWidth  value to set <code>this.tileWidth</code> to
     */
    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    /**
     * Returns <code>this.tileHeight</code>.
     * @return  <code>this.tileHeight</code>
     */
    public int getTileHeight() {
        return this.tileHeight;
    }

    /**
     * Sets <code>this.tileHeight</code> to the parameter's value.
     * @param tileHeight  value to set <code>this.tileHeight</code> to
     */
    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }
}
