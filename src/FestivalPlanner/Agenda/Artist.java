package FestivalPlanner.Agenda;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Contains all the data for an artist, such as the name, picture and sprite.
 */
public class Artist implements Serializable {

    private String name;
    private BufferedImage picture;
    private BufferedImage sprite;

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

    @Override
    public String toString() {
        return this.name;
    }
}
