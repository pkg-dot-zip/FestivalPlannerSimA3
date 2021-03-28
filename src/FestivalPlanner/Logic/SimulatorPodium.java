package FestivalPlanner.Logic;

import FestivalPlanner.TileMap.TileLayer;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SimulatorPodium extends SimulatorObject{

    private String locationString;
    private BufferedImage offImage;

    /**
     * Main constructor for SimulatorPodium
     * @param location  The location the object is at
     * @param width  The width of the object
     * @param height  The height of the object
     */
    public SimulatorPodium(Point2D location, int width, int height, double rotation, String name, TileLayer collisionLayer, String locationString) {
        super(location, width, height, rotation, name, collisionLayer);
        this.locationString = locationString;

        try {
            this.offImage = ImageIO.read(getClass().getResourceAsStream("/Images/Stages/Stage_Off.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        Rectangle2D rectangle = new Rectangle2D.Double(location.getX(), location.getY(), width, height);
        AffineTransform rectangleTransform = new AffineTransform();
        rectangleTransform.rotate(Math.toRadians(this.rotation), this.location.getX(), this.location.getY());

        g2d.draw(rectangleTransform.createTransformedShape(rectangle));

        AffineTransform imageTransform = new AffineTransform();
        imageTransform.rotate(Math.toRadians(this.rotation), this.location.getX(), this.location.getY());
        imageTransform.translate(location.getX(), location.getY());

        g2d.drawImage(this.offImage, imageTransform, null);
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

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }
}
