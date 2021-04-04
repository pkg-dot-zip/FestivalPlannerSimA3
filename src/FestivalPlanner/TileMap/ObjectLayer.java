package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

/**
 * Represents one ObjectLayer in <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>
 */
public class ObjectLayer extends Layer {

    private String name;
    private ArrayList<TileObject> tileObjects;

    /**
     * Constructor for <b>ObjectLayer</b>.
     * @param width  width of superclass <b>Layer</b>
     * @param height  height of superclass <b>Layer</b>
     * @param tileMap  <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> for the superclass
     */
    public ObjectLayer(TileMap tileMap, int width, int height) {
        this(tileMap, width, height, "", new ArrayList<>());
    }

    /**
     * Constructor for ObjectLayer with a name parameter.
     * @param width  width of superclass <b>Layer</b>
     * @param height  height of superclass <b>Layer</b>
     * @param tileMap  <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> for the superclass
     * @param name  the name of this <b>Layer</b>
     */
    public ObjectLayer(TileMap tileMap, int width, int height, String name) {
        this(tileMap, width, height, name, new ArrayList<>());
    }

    /**
     * Main constructor for <b>ObjectLayer</b>.
     * @param width  width of superclass <b>Layer</b>
     * @param height  height of superclass <b>Layer</b>
     * @param tileMap  <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> for the superclass
     * @param name  the name of this <b>Layer</b>
     * @param objects  ArrayList with all the <a href="{@docRoot}/FestivalPlanner/TileMap/TileObject.html">TileObjects</a> this Layer holds
     */
    public ObjectLayer(TileMap tileMap, int width, int height, String name, ArrayList<TileObject> objects) {
        super(width, height, tileMap);
        this.name = name;
        this.tileObjects = objects;
    }

    /**
     * Returns <code>this.name</code>.
     * @return  <code>this.name</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Returns <code>this.tileObjects</code>.
     * @return  <code>this.tileObjects</code>
     */
    public ArrayList<TileObject> getTileObjects() {
        return tileObjects;
    }

    /**
     * Sets <code>this.tileObjects</code> to the parameter's value.
     * @param tileObjects  value to set to
     */
    public void setTileObjects(ArrayList<TileObject> tileObjects) {
        this.tileObjects = tileObjects;
    }

    /**
     * Adds a <a href="{@docRoot}/FestivalPlanner/TileMap/TileObject.html">TileObject</a> to <code>this.tileObjects</code>.
     * @param tileObject  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileObject.html">TileObject</a> to add to <code>this.tileObjects</code>
     */
    public void addTileObject(TileObject tileObject) {
        this.tileObjects.add(tileObject);
    }

    @Override
    public void draw(FXGraphics2D g2d) {}
}
