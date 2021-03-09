package FestivalPlanner.TileMap;

import java.awt.geom.Point2D;

/**
 * Represents a object stored in a JSon-file. Part of <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>.
 */
public class TileObject {

    private String name;
    private String type;

    private Point2D location;
    private int width;
    private int height;

    /**
     * Only constructor for TileObject
     * @param name  The name this object has
     * @param type  The type of object this object represents
     * @param location  The start location of this object, top right corner
     * @param width  The width of this object
     * @param height  The height of the object
     */
    public TileObject(String name, String type, Point2D location, int width, int height) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter for <code>this.name</code>
     * @return  <code>this.name</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for <code>this.type</code>
     * @return  <code>this.type</code>
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for <code>this.location</code>
     * @return  <code>this.location</code>
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     * Setter for <code>this.location</code>
     * @param location  Sets <code>this.location</code>
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }

    /**
     * Getter for <code>this.width</code>
     * @return  <code>this.width</code>
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter for <code>this.width</code>
     * @param width  Sets <code>this.width</code>
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for <code>this.height</code>
     * @return  <code>this.height</code>
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Setter for <code>this.height</code>
     * @param height  sets <code>this.height</code>
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
