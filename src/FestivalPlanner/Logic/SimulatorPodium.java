package FestivalPlanner.Logic;

import FestivalPlanner.TileMap.TileLayer;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SimulatorPodium extends SimulatorObject{

    /**
     * Main constructor for SimulatorPodium
     * @param location  The location the object is at
     * @param width  The width of the object
     * @param height  The height of the object
     */
    public SimulatorPodium(Point2D location, int width, int height, TileLayer collisionLayer) {
        super(location, width, height, collisionLayer);
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        g2d.draw(new Rectangle2D.Double(location.getX(), location.getY(), width, height));
    }

    @Override
    public void debugDraw(Graphics2D g2d) {
        Point[][] map = this.pathMap;
        for (int y = 0; y < map.length; y++) {
            Point[] points = map[y];
            for (int x = 0; x < points.length; x++) {
                Point point = points[x];
                if (point != null) {
                    g2d.setColor(Color.black);
                    g2d.drawLine(x * 16 + 8, y * 16 + 8, point.x, point.y);
                    g2d.setColor(Color.white);
                }
            }
        }
    }

    @Override
    public void update(double deltaTime) {

    }
}
