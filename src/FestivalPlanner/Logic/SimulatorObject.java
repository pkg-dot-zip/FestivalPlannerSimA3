package FestivalPlanner.Logic;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;

abstract public class SimulatorObject {

    protected Point2D location;
    protected int width;
    protected int height;

    /**
     * Main constructor for SimulatorPodium
     * @param location  The location the object is at
     * @param width  The width of the object
     * @param height  The height of the object
     */
    public SimulatorObject(Point2D location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    /**
     * Draws the object to the given screen.
     * @param g2d  The object to draw to
     */
    abstract public void draw(FXGraphics2D g2d);

    /**
     * Updates the object.
     * @param deltaTime  The time since last update call
     */
    abstract public void update(double deltaTime);

}
