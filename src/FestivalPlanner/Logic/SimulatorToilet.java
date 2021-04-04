package FestivalPlanner.Logic;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.TileMap.TileLayer;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Contains methods to handle everything toilet-related.
 */
public class SimulatorToilet extends SimulatorObject{

    private BufferedImage image;
    private boolean occupied = false;

    /**
     * Main constructor for <b>SimulatorPodium</b>.
     * @param location  the location the object is at
     * @param width  the width of the object
     * @param height  the height of the object
     * @param rotation  the rotation of the object
     * @param name  the name of the object
     * @param collisionLayer  the Layer to base pathFinding on
     * @param locationString  the location name the object is at
     */
    public SimulatorToilet(Point2D location, int width, int height, double rotation, String name, TileLayer collisionLayer, String locationString) {
        super(location, width, height, rotation, name, collisionLayer, locationString);

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/Images/toilet.png"));

        } catch (Exception e) {
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

    /**
     * Returns <code>this.occupied</code>.
     * @return  <code>this.occupied</code>
     */
    boolean isOccupied() {
        return this.occupied;
    }

    /**
     * Sets <code>this.occupied</code> to the parameter's value.
     * @param occupied  value to set occupied to
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}