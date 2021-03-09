package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;
import java.util.ArrayList;

/**
 * Represents one Layer filled with tiles in <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>
 */
public class TileLayer extends Layer {

    private TileMap tileMap;

    private int tileWidth;
    private int tileHeight;

    private Tile[][] tiles;

    /**
     * Constructor for Layer.
     * @param width  The width of the Layer
     * @param height  The height of the Layer
     * @param tiles  The <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a> in the Layer
     */
    public TileLayer(TileMap tileMap, int width, int height, ArrayList<Tile> tiles) {
        super(width, height, tileMap);

        this.tileWidth = tileMap.getTileWidth();
        this.tileHeight = tileMap.getTileHeight();

        this.tiles = buildTiles(tiles);
    }

    /**
     * Sets <code>this.TileMap</code>.
     * @param tileMap  The TileMap to set to
     */
    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    /**
     * Getter for <code>this.tiles</code>.
     * @return  <code>this.tiles</code>
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Builds a given arrayList with tiles to a 2d Tile Array.
     * @param tiles  The ArrayList with <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a>
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
     * @param g2d  Object this layer needs to be drawn to
     */
    public void draw(FXGraphics2D g2d) {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x] == null) {
                    continue;
                }
                tiles[y][x].draw(g2d, x * tileWidth,y * tileHeight);
            }
        }

    }

}
