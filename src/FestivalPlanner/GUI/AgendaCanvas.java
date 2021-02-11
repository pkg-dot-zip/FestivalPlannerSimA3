package FestivalPlanner.GUI;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class AgendaCanvas {

    private BorderPane mainPane;
    private Canvas canvas;
    private AffineTransform cameraTransform;

    //TODO: Needs Agenda object in constructor.

    /**
     * Blank constructor of <code>AgendaCanvas</code>.
     */
    public AgendaCanvas() {
        this(1920 / 3f, 1080 / 2f);
    }

    /**
     * Blank constructor of <code>AgendaCanvas</code>.
     * <p>
     * Initializes <code>this.cameraTransform</code> and <code>this.canvas</code>.
     * <p>
     * Last action is calling the <code>draw()</code> method.
     * @param width  sets <code>this.canvas.setWidth</code> to this value
     * @param height  sets <code>this.canvas.setHeight</code> to this value
     */
    public AgendaCanvas(double width, double height) {
        this.cameraTransform = new AffineTransform();
        this.mainPane = new BorderPane();

        this.canvas = new ResizableCanvas(this::draw, this.mainPane);
        this.canvas.setHeight(height);
        this.canvas.setWidth(width);

        this.canvas.setOnScroll(this::setOnScroll);

        this.mainPane.setCenter(this.canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics);
    }

    /**
     * Main method to draw everything on <code>this.canvas</code>.
     * @param graphics  object that draws on <code>this.canvas</code>
     */
    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setTransform(this.cameraTransform);

        int sideOffset = 150;
        int topOffset = 50;

        graphics.translate(sideOffset, topOffset);

        graphics.drawLine(-sideOffset, 0, (int) canvas.getWidth(), 0);
        graphics.drawLine(0, -topOffset, 0, (int) canvas.getHeight());
        drawTopBar(graphics);
    }

    /**
     * Draws the time information on the horizontal axis.
     * @param graphics  object to draw on
     */
    private void drawTopBar(FXGraphics2D graphics) {
        for (int i = 0; i < 24; i++) { //Later changed in starttune till endtime
            graphics.drawString(i + ".00", i * 50 + 10, -25);
        }
    }

    /**
     * Makes scrolling possible by translating <code>this.cameraTransform</code>.
     * @param scrollEvent  is the eventhandler for scrolling
     */
    private void setOnScroll(ScrollEvent scrollEvent) {

        this.cameraTransform.translate(scrollEvent.getDeltaY() / 1.5, 0);
        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));

    }

    /**
     * Returns <code>this.mainPane</code> to the class AgendaModule so AgendaCanvas can be added to the GUI.
     * @return  <code>this.mainPane</code> to the class AgendaModule
     */
    public Node buildAgendaCanvas() {
        return this.mainPane;
    }

}
