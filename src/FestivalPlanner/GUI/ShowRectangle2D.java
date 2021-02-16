package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Show;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import org.jfree.fx.FXGraphics2D;

//Todo: Class needs documentation
public class ShowRectangle2D{

    private Rectangle2D rectangle;
    private Show show;
    private Color color;

    public ShowRectangle2D(double minX, double minY, double width, double height, Show show) {
        this(new Rectangle2D.Double(minX, minY, width, height), show);
    }

    public ShowRectangle2D(Rectangle2D rectangle, Show show) {
        this.rectangle = rectangle;
        this.show = show;
        this.color = Color.CYAN;
    }

    public Show getShow() {
        return show;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(FXGraphics2D graphics) {
        graphics.drawString(this.show.getName(), (int)(this.rectangle.getX() + 5), (int)(this.rectangle.getY() + 5));
        graphics.setColor(this.color);
        graphics.draw(this.rectangle);
    }

}
