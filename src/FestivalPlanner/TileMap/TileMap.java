package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

/**
 * Contains information of a TileMap.
 */
public class TileMap {

    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    private ArrayList<Layer> layers;
    private TileLayer pathFindingLayer;
    private TileManager tileManager;

    /**
     * Empty constructor for <b>TileMap</b>.
     * <p>
     * This constructor calls another constructor in this class and fills in the following parameters:
     * <p><ul>
     * <li>MapWidth - 100
     * <li>MapHeight - 100
     * <li>TileWidth - 16
     * <li>TileHeight - 16
     * </ul>
     */
    public TileMap() {
        this(100,100,16,16);
    }

    /**
     * Base constructor for <b>TileMap</b>.
     * @param mapWidth  width of the map
     * @param mapHeight  height of the map
     * @param tileWidth  width of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>.
     * @param tileHeight  height of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>.
     */
    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight) {
        this(mapWidth, mapHeight, tileWidth, tileHeight, new ArrayList<>(), new TileManager());
    }

    /**
     * Constructor for TileMap, doesn't include the <b>Layer</b>s.
     * @param mapWidth  width of the map
     * @param mapHeight  height of the map
     * @param tileWidth  width of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tileHeight  height of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tileManager  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> for this TileMap
     */
    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, TileManager tileManager) {
        this(mapWidth, mapHeight, tileWidth, tileHeight, new ArrayList<>(), tileManager);
    }

    /**
     * Head constructor for TileMap.
     * @param mapWidth  width of the map
     * @param mapHeight  height of the map
     * @param tileWidth  width of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tileHeight  height of each individual <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param layers  the <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layers</a> contained in the <b>TileMap</b>
     * @param tileManager  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> for tis <b>TileMap</b>
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
     * Returns <code>this.mapWidth</code>.
     * @return  <code>this.mapWidth</code>
     */
    public int getMapWidth() {
        return this.mapWidth;
    }

    /**
     * Setter for <code>this.mapHeight</code>.
     * @return  <code>this.mapHeight</code>
     */
    public int getMapHeight() {
        return this.mapHeight;
    }

    /**
     * Returns <code>this.tileWidth</code>.
     * @return  <code>this.tileWidth</code>
     */
    public int getTileWidth() {
        return this.tileWidth;
    }

    /**
     * Returns <code>this.tileHeight</code>.
     * @return  <code>this.tileHeight</code>
     */
    public int getTileHeight() {
        return this.tileHeight;
    }

    /**
     * Returns <code>this.Layers</code>
     * @return  <code>this.Layers</code>
     */
    public ArrayList<Layer> getLayers() {
        return this.layers;
    }

    /**
     * Sets <code>this.Layers</code> to the parameter's value.
     * @param layers  <code>this.Layers</code> to be set to
     */
    public void setLayers(ArrayList<Layer> layers) {
        this.layers = layers;
    }

    /**
     * Returns <code>this.pathFindingLayer</code>.
     * @return  <code>this.pathFindingLayer</code>
     */
    public TileLayer getPathFindingLayer() {
        return this.pathFindingLayer;
    }

    /**
     * Sets <code>this.pathFindingLayer</code> to the parameter's value.
     * @param pathFindingLayer  value to be set to
     */
    public void setPathFindingLayer(TileLayer pathFindingLayer) {
        this.pathFindingLayer = pathFindingLayer;
    }

    /**
     * Adds <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a> to <code>this.Layers</code>.
     * @param layer  the <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a> to be added
     */
    public void addLayer(Layer layer) {
        this.layers.add(layer);
    }

    /**
     * Returns <code>this.tileManager</code>.
     * @return  <code>this.tileManager</code>
     */
    public TileManager getTileManager() {
        return this.tileManager;
    }

    /**
     * Sets <code>this.tileManager</code> to the parameter's value.
     * @param tileManager  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> that <code>this.tileManager</code> needs to be set to
     */
    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    /**
     * Draws the complete <b>TileMap</b> to the given graphics, always at point 0.0.
     * @param g2d  Object to draw on
     */
    public void draw(FXGraphics2D g2d){
        for (Layer layer : this.layers) {
            layer.draw(g2d);
        }
    }
}
