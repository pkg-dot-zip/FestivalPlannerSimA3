package FestivalPlanner.TileMap;

import java.awt.geom.Point2D;

public class TileObject {

    private String name;
    private String type;

    private Point2D location;
    private int width;
    private int height;

    public TileObject(String name, String type, Point2D location, int width, int height) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Point2D getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
