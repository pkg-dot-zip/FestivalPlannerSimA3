package FestivalPlanner.Logic;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;

abstract public class SimulatorObject {

    protected Point2D location;
    protected int width;
    protected int height;

    public SimulatorObject(Point2D location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    abstract public void draw(FXGraphics2D g2d);
    abstract public void update(double deltaTime);

}
