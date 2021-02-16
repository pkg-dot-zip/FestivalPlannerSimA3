package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.Show;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import org.jfree.fx.FXGraphics2D;

//Todo: Class needs documentation
public class ShowRectangle2D{

    private Rectangle2D rectangle;
    private Show show;
    private Color color;

    /**
     * Constructor for <code>ShowRectangle2D</code>.
     * <p>
     * creates new <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">Rectangle2D</a> based on given parameters
     * @param minX  Start x-coordinate for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param minY  Start y-coordinate for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param width  Width for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param height Height for the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a>
     * @param show  The <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a> that this rectangle represents
     */
    public ShowRectangle2D(double minX, double minY, double width, double height, Show show) {
        this(new Rectangle2D.Double(minX, minY, width, height), show);
    }

    /**
     * Constructor for <code>ShowRectangle2D</code>.
     * @param rectangle  The <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle2D</a> that is given
     * @param show  The <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a> that this rectangle represents
     */
    public ShowRectangle2D(Rectangle2D rectangle, Show show) {
        this.rectangle = rectangle;
        this.show = show;
        this.color = Color.GREEN;
    }

    /**
     * Getter for <code>this.Show</code>
     * @return <code>this.show</code>
     */
    public Show getShow() {
        return show;
    }

    /**
     * Getter for <code>this.Color</code>
     * @return <code>this.Color</code>
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for <code>this.Color</code>
     * @param color  The color that <code>this.Color</code> should be
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for <code>this.rectangle</code>.
     * @return  The value of <code>this.rectangle</code>
     */
    public Rectangle2D getRectangle() {
        return this.rectangle;
    }

    /**
     * Draws the ShowRectangle2D on the given <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html">Graphics2D</a>.
     * @param graphics  The <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html">graphics2D</a> the method should draw to
     */
    public void draw(FXGraphics2D graphics) {
        graphics.setColor(this.color);
        graphics.fill(this.rectangle);
        graphics.setColor(Color.BLACK);
        graphics.draw(this.rectangle);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        graphics.drawString("Name: " + this.show.getName(), (int)(this.rectangle.getX() + 25), (int)(this.rectangle.getY() + 30));
    }

}
