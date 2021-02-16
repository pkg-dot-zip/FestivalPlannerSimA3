package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Show;
import javafx.geometry.Rectangle2D;

//Todo: Class needs documentation
public class ShowRectangle2D extends Rectangle2D {

    private Show show;

    /**
     * Creates a new instance of {@code Rectangle2D}.
     *
     * @param minX   The x coordinate of the upper-left corner of the {@code Rectangle2D}
     * @param minY   The y coordinate of the upper-left corner of the {@code Rectangle2D}
     * @param width  The width of the {@code Rectangle2D}
     * @param height The height of the {@code Rectangle2D}
     */
    public ShowRectangle2D(double minX, double minY, double width, double height) {
        super(minX, minY, width, height);
    }

    /**
     * Creates a new instance of {@code Rectangle2D}.
     *
     * @param minX   The x coordinate of the upper-left corner of the {@code Rectangle2D}
     * @param minY   The y coordinate of the upper-left corner of the {@code Rectangle2D}
     * @param width  The width of the {@code Rectangle2D}
     * @param height The height of the {@code Rectangle2D}
     * @param show The <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">show</a> that this rectangle represents.
     */
    public ShowRectangle2D(double minX, double minY, double width, double height, Show show) {
        super(minX, minY, width, height);
        this.show = show;
    }
}
