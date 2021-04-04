package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.Show;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Responsible for the rectangles representing shows on the canvas. This class contains the code for drawing rectangles
 * and stores the show as a single variable.
 * <p>
 * This show contains a Rectangle2D. This rectangle represents a show on the timeline Canvas given to draw it on.
 * Inside the rectangle text is drawn. This text shows the name and the artist of the show this rectangle represents.
 */
public class ShowRectangle2D {

    //LanguageHandling.
    private ResourceBundle messages = LanguageHandler.getMessages();

    private Rectangle2D rectangle;
    private Show show;
    private Color color;

    /**
     * Constructor for <code>ShowRectangle2D</code>.
     * <p>
     * Creates a new <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">Rectangle2D</a> instance based on the
     * given parameters.
     * @param minX   start x-coordinate for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param minY   start y-coordinate for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param width  width for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param height  height for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param show   the <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a> that this rectangle represents
     */
    public ShowRectangle2D(double minX, double minY, double width, double height, Show show) {
        this(new Rectangle2D.Double(minX, minY, width, height), show, SaveSettingsHandler.getSelectedColor());
    }

    public ShowRectangle2D(Rectangle2D rectangle, Show show) {
        this(rectangle, show, SaveSettingsHandler.getSelectedColor());
    }

    /**
     * Constructor for <code>ShowRectangle2D</code>.
     * @param rectangle  the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a> that is given
     * @param show  the <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a> that this rectangle represents
     * @param color  the color the rectangles will be filled up with when drawn
     */
    public ShowRectangle2D(Rectangle2D rectangle, Show show, Color color) {
        this.rectangle = rectangle;
        this.show = show;
        this.color = color;
    }

    /**
     * Returns <code>this.show</code>.
     * @return  <code>this.show</code>
     */
    public Show getShow() {
        return this.show;
    }

    /**
     * Returns <code>this.color</code>.
     * @return  <code>this.color</code>
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter for <code>this.color</code>.
     * @param color  the color that <code>this.color</code> should be
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns <code>this.rectangle</code>.
     * @return  the value of <code>this.rectangle</code>
     */
    Rectangle2D getRectangle() {
        return this.rectangle;
    }

    /**
     * Draws the ShowRectangle2D on the given <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html">Graphics2D</a>.
     * @param graphics  the <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html">graphics2D</a> the method should draw to
     */
    public void draw(FXGraphics2D graphics) {
        graphics.setColor(this.color);
        graphics.fill(this.rectangle);
        graphics.setColor(Color.BLACK);
        graphics.draw(this.rectangle);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 13));

        String nameString = getString(graphics,messages.getString("name") + ": " + this.show.getName());

        String artistsString = (messages.getString("artists") + ": ");
        ArrayList<Artist> artists = this.getShow().getArtists();
        artistsString += artists.get(0).getName();
        for (int i = 1; i < artists.size(); i++) {
            Artist artist = artists.get(i);
            artistsString += ", " + artist.getName();
        }
        artistsString = getString(graphics, artistsString);

        graphics.drawString(nameString, (int) (this.rectangle.getX() + 10), (int) (this.rectangle.getY() + 17));
        graphics.drawString(artistsString, (int) (this.rectangle.getX() + 10), (int) (this.rectangle.getY() + 40));
    }

    private String getString(FXGraphics2D graphics, String string) {
        if (string.length() > 10){
            double stringLength = graphics.getFontMetrics().stringWidth(string);
            String editString = string;
            while (stringLength >= this.rectangle.getWidth() - 20) {
                editString = editString.substring(0, editString.length() - 1);
                stringLength = graphics.getFontMetrics().stringWidth(editString);
            }

            if (!editString.equals(string)) {
                editString += "...";
            }

            return editString;
        } else {
            return "";
        }
    }
}