package FestivalPlanner.Agenda;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;

/**
 * Contains all the data for an artist, such as the name, picture and sprite.
 */
public class Artist implements Serializable {

    private String name;
    private transient BufferedImage picture;
    private transient BufferedImage sprite;

    /**
     * Constructor for the <code>Artist</code> class.
     * @param name  sets <code>this.name</code> to the given parameter's value
     * @param picture  sets <code>this.picture</code> to the given parameter's value
     * @param sprite  sets <code>this.sprite</code> to the given parameter's value
     */
    public Artist(String name, BufferedImage picture, BufferedImage sprite) {
        this.name = name;
        this.picture = picture;
        this.sprite = sprite;
    }

    /**
     * Returns a string containing the name of this artist.
     * @return  this.name, a string containing this name of this artist
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a BufferedImage containing the picture of this artist.
     * @return  this.picture, a BufferedImage containing this picture of this artist
     */
    public BufferedImage getPicture() {
        return this.picture;
    }

    /**
     * Returns a BufferedImage containing the picture of this artist.
     * @return  this.sprite, a BufferedImage containing this picture of this artist
     */
    public BufferedImage getSprite() {
        return this.sprite;
    }

    /**
     * Changes the name to a new name.
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the picture to a new picture.
     * @param picture the new picture
     */
    public void setPicture(BufferedImage picture) {
        this.picture = picture;
    }

    /**
     * Changes the sprite to a new sprite.
     * @param sprite the new sprite
     */
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Writing the sprite and image of the artist to the saveFile.
     * @param out  the stream the images needs to be written to
     */
    //TODO: Separate agenda resources from other agendas to avoid overriding files.
    private void writeObject(ObjectOutputStream out) throws IOException, URISyntaxException {
        out.defaultWriteObject();

        try {
            System.out.println("test");
            File pictureFile = new File(System.getenv("LOCALAPPDATA") + "/A3/Resources/" + "AgendaName/" + this.toString() + "Picture.png");
            pictureFile.mkdirs();
            File spriteFile = new File(System.getenv("LOCALAPPDATA") + "/A3/Resources/" + "AgendaName/" + this.toString() + "Sprite.png");
            spriteFile.mkdirs();
            ImageIO.write(this.picture, "png", pictureFile);
            ImageIO.write(this.sprite, "png", spriteFile);
        } catch (Exception e) {
            e.printStackTrace();
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }

    /**
     * Reads the sprite and image of the artist to the save file.
     * @param in  the stream used to read the class
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        try {
            FileInputStream pictureFile = new FileInputStream(System.getenv("LOCALAPPDATA") + "/A3/Resources/" + "AgendaName/" + this.toString() + "Picture.png");
            FileInputStream spriteFile = new FileInputStream(System.getenv("LOCALAPPDATA") + "/A3/Resources/" + "AgendaName/" + this.toString() + "Sprite.png");
            this.picture = ImageIO.read(pictureFile);
            this.sprite = ImageIO.read(spriteFile);
        } catch (FileNotFoundException e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
            this.picture = null;
            this.sprite = null;
        } catch (Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }
}
