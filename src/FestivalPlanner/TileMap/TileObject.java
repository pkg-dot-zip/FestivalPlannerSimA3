package FestivalPlanner.TileMap;

import java.awt.geom.Point2D;

/**
 * Represents an object stored in a <i>.JSON</i> file. Part of <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>.
 */
public class TileObject {

    private String name;
    private String type;

    private Point2D location;
    private String locationString;
    private int width;
    private int height;
    private double rotation;

    /**
     * Only constructor for <b>TileObject</b>.
     * @param name  the name this object has
     * @param type  the type of object this object represents
     * @param location  the start location of this object, top right corner
     * @param width  the width of this object
     * @param height  the height of the object
     * @param rotation  the rotation of the object
     * @param locationString  TODO: write this
     */
    public TileObject(String name, String type, Point2D location, int width, int height, double rotation, String locationString) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.locationString = locationString;
    }

    /**
     * Returns <code>this.name</code>.
     * @return  <code>this.name</code>
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns <code>this.type</code>.
     * @return  <code>this.type</code>
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns <code>this.location</code>.
     * @return  <code>this.location</code>
     */
    public Point2D getLocation() {
        return this.location;
    }

    /**
     * Sets <code>this.location</code> to the parameter's value.
     * @param location  Sets <code>this.location</code>
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }

    /**
     * Returns <code>this.width</code>.
     * @return  <code>this.width</code>
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets <code>this.width</code> to the parameter's value.
     * @param width  <code>this.width</code>
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns <code>this.height</code>.
     * @return  <code>this.height</code>
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets <code>this.height</code> to the parameter's value.
     * @param height  <code>this.height</code>
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns <code>this.rotation</code>.
     * @return  <code>this.rotation</code>
     */
    public double getRotation() {
        return this.rotation;
    }

    /**
     * Returns <code>this.locationString</code>.
     * @return  <code>this.locationString</code>
     */
    public String getLocationString() {
        return this.locationString;
    }
}
