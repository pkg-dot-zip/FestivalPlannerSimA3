package FestivalPlanner.Logic;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.TileMap.TileLayer;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class SimulatorToilet extends SimulatorObject{

    private BufferedImage image;
    private boolean occupied = false;

    /**
     * Main constructor for SimulatorPodium
     *
     * @param location       The location the object is at
     * @param width          The width of the object
     * @param height         The height of the object
     * @param rotation       The rotation of the object
     * @param name           The name of the object
     * @param collisionLayer The Layer to base pathfinding on
     * @param locationString The location name the object is at
     */
    public SimulatorToilet(Point2D location, int width, int height, double rotation, String name, TileLayer collisionLayer, String locationString) {
        super(location, width, height, rotation, name, collisionLayer, locationString);

        try {
            //this.image = ImageIO.read(getClass().getResourceAsStream("/Images/Toilet.png"));

        }catch (Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }

    @Override
    public void draw(FXGraphics2D g2d) {

        if (this.image != null) {
            AffineTransform imageTransform = new AffineTransform();
            imageTransform.rotate(Math.toRadians(this.rotation), this.location.getX(), this.location.getY());
            imageTransform.translate(location.getX(), location.getY());

            g2d.drawImage(this.image, imageTransform, null);
        }

    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
