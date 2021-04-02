package FestivalPlanner.Logic;

import FestivalPlanner.TileMap.TileLayer;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SimulatorObject {

    protected Point2D location;
    private String locationString;

    protected int width;
    protected int height;

    protected String name;
    protected double rotation;

    private TileLayer collisionLayer;
    protected Point[][] pathMap;

    /**
     * Main constructor for SimulatorPodium
     * @param location  The location the object is at
     * @param width  The width of the object
     * @param height  The height of the object
     */
    public SimulatorObject(Point2D location, int width, int height, double rotation, String name, TileLayer collisionLayer, String locationString) {
        this.location = location;
        this.locationString = locationString;

        this.width = width;
        this.height = height;

        this.name = name;
        this.rotation = rotation;

        this.collisionLayer = collisionLayer;
        buildPathMap();
    }


    /**
     * Builds the pathfinding map to this object
     */
    protected void buildPathMap() {
        this.pathMap = new Point[collisionLayer.getWidth()][collisionLayer.getHeight()];
        for (int x = 0; x < collisionLayer.getWidth(); x++) {
            for (int y = 0; y < collisionLayer.getHeight(); y++) {
                this.pathMap[x][y] = null;
            }
        }

        Point thisLocation = new Point((int)this.location.getX() + (this.width / 2) + (collisionLayer.getTileWidth() / 2),
                (int)this.location.getY() + (this.height / 2) + (collisionLayer.getTileHeight() / 2));

        Queue<Point> todoQueue = new LinkedList<>();
        ArrayList<Point> visited = new ArrayList<>();

        this.pathMap[(int)Math.floor(this.location.getX() / collisionLayer.getTileWidth())]
                [(int)Math.floor(this.location.getY() / collisionLayer.getTileHeight())] =
                thisLocation;


        todoQueue.add(thisLocation);
        visited.add(thisLocation);

        Point[] offsets = {new Point(collisionLayer.getTileHeight(), 0), new Point(-collisionLayer.getTileHeight(), 0),
        new Point(0, collisionLayer.getTileWidth()), new Point(0, -collisionLayer.getTileWidth())};

        while (!todoQueue.isEmpty()) {

            Point current = todoQueue.remove();

            for (Point offset : offsets) {

                Point newPoint = new Point(current.x + offset.x, current.y + offset.y);
                if (
                        newPoint.getX() < 0 ||
                                newPoint.getY() < 0 ||
                                newPoint.getX() >= collisionLayer.getTileWidth() * collisionLayer.getWidth() ||
                                newPoint.getY() >= collisionLayer.getTileHeight() * collisionLayer.getHeight()
                ) {
                    continue;
                }
                if (visited.contains(newPoint)) {
                    continue;
                }
                if (collisionLayer.getTiles()[(int) Math.floor(newPoint.getY() / collisionLayer.getTileHeight())][(int) Math.floor(newPoint.getX() / collisionLayer.getTileWidth())] == null) {
                    continue;
                }

                this.pathMap[(int) Math.floor(newPoint.getY() / collisionLayer.getTileHeight())][(int) Math.floor(newPoint.getX() / collisionLayer.getTileWidth())] = current;
                visited.add(newPoint);
                todoQueue.add(newPoint);
            }

        }

    }

    /**
     * Given a point, it looks in the pathfinding map to see what point to go to.
     * @param currentPoint  The position to look for
     * @return  The position to go to, based on given currentPoint
     */
    public Point2D getNextDirection(Point2D currentPoint) {
        Point toPoint = this.pathMap[(int)Math.floor(currentPoint.getY() / collisionLayer.getTileHeight())][(int)Math.floor(currentPoint.getX() / collisionLayer.getTileWidth())];
        if (toPoint == null) {
            toPoint = this.pathMap[(int)Math.floor(this.location.getX() / collisionLayer.getTileWidth())]
                    [(int)Math.floor(this.location.getY() / collisionLayer.getTileHeight())];
        }

        return new Point2D.Double(toPoint.x, toPoint.y);
    }

    /**
     * Draws the object to the given screen.
     * @param g2d  The object to draw to
     */
    public void draw(FXGraphics2D g2d) {

    }

    /**
     * Updates the object.
     * @param deltaTime  The time since last update call
     */
    public void update(double deltaTime) {

    }

    /**
     * Getter for <code>this.location</code>
     * @return <code>this.location</code>
     */
    public Point2D getLocation() {
        return location;
    }

    public void debugDraw(Graphics2D g2d){}

    public String getLocationString() {
        return locationString;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean isWithin(Point2D point){
        Rectangle2D rectangle = new Rectangle2D.Double(location.getX(), location.getY(), width, height);
        AffineTransform rectangleTransform = new AffineTransform();
        rectangleTransform.rotate(Math.toRadians(this.rotation), this.location.getX(), this.location.getY());

        return rectangle.contains(point);
    }
}
