package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;
import java.util.ArrayList;

/**
 * Class responsible for holding the information of a TileMap
 */
public class TileMap {

    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    private ArrayList<Layer> layers;
    private TileManager tileManager;


    /**
     * Base constructor for TileMap
     * @param mapWidth  Width of the map
     * @param mapHeight  Height of the map
     * @param tileWidth  Width of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>.
     * @param tileHeight  Height of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>.
     */
    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight) {
        this(mapWidth, mapHeight, tileWidth, tileHeight, new ArrayList<>(), new TileManager());
    }

    /**
     * Constructor for TileMap, doesn't include the Layers
     * @param mapWidth  Width of the map
     * @param mapHeight  Height of the map
     * @param tileWidth  Width of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tileHeight  Height of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tileManager  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> for tis TileMap
     */
    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, TileManager tileManager) {
        this(mapWidth, mapHeight, tileWidth, tileHeight, new ArrayList<>(), tileManager);
    }

    /**
     * Head constructor for TileMap
     * @param mapWidth  Width of the map
     * @param mapHeight  Height of the map
     * @param tileWidth  Width of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tileHeight  Height of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param layers  The <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layers</a> contained in the TileMap
     * @param tileManager  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> for tis TileMap
     */
    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, ArrayList<Layer> layers, TileManager tileManager) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.layers = layers;
        this.tileManager = tileManager;
    }

    /**
     * Getter for <code>this.mapWidth</code>
     * @return  <code>this.mapWidth</code>
     */
    public int getMapWidth() {
        return this.mapWidth;
    }

    /**
     * Setter for <code>this.mapHeight</code>
     * @return  <code>this.mapHeight</code>
     */
    public int getMapHeight() {
        return this.mapHeight;
    }

    /**
     * Getter for <code>this.tileWidth</code>
     * @return  <code>this.tileWidth</code>
     */
    public int getTileWidth() {
        return this.tileWidth;
    }

    /**
     * Getter for <code>this.tileHeight</code>
     * @return  <code>this.tileHeight</code>
     */
    public int getTileHeight() {
        return this.tileHeight;
    }

    /**
     * Getter for <code>this.Layers</code>
     * @return  <code>this.Layers</code>
     */
    public ArrayList<Layer> getLayers() {
        return this.layers;
    }

    /**
     * Setter for <code>this.Layers</code>
     * @param layers  <code>this.Layers</code> to be set to
     */
    public void setLayers(ArrayList<Layer> layers) {
        this.layers = layers;
    }


    /**
     * Adds <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a> to <code>this.Layers</code>
     * @param layer  The <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a> to be added
     */
    public void addLayer(Layer layer) {
        this.layers.add(layer);
    }

    /**
     * Getter for <code>this.tileManager</code>
     * @return  <code>this.tileManager</code>
     */
    public TileManager getTileManager() {
        return this.tileManager;
    }

    /**
     * Setter for <code>this.tileManager</code>
     * @param tileManager  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> that <code>this.tileManager</code> needs to be set to
     */
    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    /**
     * Draws the complete TileMap to the given graphics, always at point 0.0
     * @param g2d  Object to draw on
     */
    public void draw(FXGraphics2D g2d){
        for (Layer layer : this.layers) {
            layer.draw(g2d);
        }
    }
}
