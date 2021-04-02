package FestivalPlanner.Logic;

import FestivalPlanner.TileMap.TileLayer;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SimulatorPodium extends SimulatorObject{



    private boolean isActive;

    private BufferedImage offImage;
    private BufferedImage[] activeImages;
    private int activeSprite;

    /**
     * Main constructor for SimulatorPodium
     * @param location  The location the object is at
     * @param width  The width of the object
     * @param height  The height of the object
     */
    public SimulatorPodium(Point2D location, int width, int height, double rotation, String name, TileLayer collisionLayer, String locationString) {
        super(location, width, height, rotation, name, collisionLayer, locationString);
        this.activeSprite = 0;

        final int NUMBER_OF_IMAGES = 6;
        try {
            this.offImage = ImageIO.read(getClass().getResourceAsStream("/Images/Stages/Stage_Off.png"));

            BufferedImage activeImage = ImageIO.read(getClass().getResourceAsStream("/Images/Stages/Stage_Active.png"));

            this.activeImages = new BufferedImage[NUMBER_OF_IMAGES];
            for (int i = 0; i < NUMBER_OF_IMAGES; i++) {
                this.activeImages[i] = activeImage.getSubimage(0, 111 * i, 207, 111);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        AffineTransform imageTransform = new AffineTransform();
        imageTransform.rotate(Math.toRadians(this.rotation), this.location.getX(), this.location.getY());
        imageTransform.translate(location.getX(), location.getY());

        if (this.isActive) {
            g2d.drawImage(this.activeImages[this.activeSprite], imageTransform, null);
        } else {
            g2d.drawImage(this.offImage, imageTransform, null);
        }

        Ellipse2D ellipse2D = new Ellipse2D.Double(this.getCentre().getX(), this.getCentre().getY(), 10,10);
        g2d.setColor(Color.white);
        g2d.fill(ellipse2D);
        g2d.setColor(Color.black);
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

    private double activeAnimationTimer = 0.5;

    @Override
    public void update(double deltaTime) {
        this.activeAnimationTimer -= deltaTime;

        if (this.activeAnimationTimer < 0) {
            this.activeAnimationTimer = 0.5;
            if (this.activeSprite < this.activeImages.length - 1) {
                this.activeSprite++;
            } else {
                this.activeSprite = 0;
            }
        }
    }


    public void setActive(boolean active) {
        isActive = active;
    }

}
