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

    private int startX;
    private int endX;
    private int startY;
    private int endY;

    //TODO: Needs Agenda object in constructor.

    /**
     * Blank constructor of <code>AgendaCanvas</code>.
     */
    public AgendaCanvas() {
        this(1920 / 3f, 400);
    }

    /**
     * Blank constructor of <code>AgendaCanvas</code>.
     * <p>
     * Initializes <code>this.cameraTransform</code> and <code>this.canvas</code>.
     * <p>
     * Last action is calling the <code>draw()</code> method.
     *
     * @param width  sets <code>this.canvas.setWidth</code> to this value
     * @param height  sets <code>this.canvas.setHeight</code> to this value
     */
    public AgendaCanvas(double width, double height) {
        this.cameraTransform = new AffineTransform();
        this.mainPane = new BorderPane();

        this.canvas = new ResizableCanvas(this::draw, this.mainPane);
        this.canvas.setHeight(height);
        this.canvas.setWidth(width);

        calculateBounds();

        this.canvas.setOnScroll(this::setOnScroll);

        this.mainPane.setCenter(this.canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics);
    }

    /**
     * Calculates the boundaries of the canvas based on stages and shows in the Agenda. (not yet implemented)
     * <p>
     * Initializes <code>this.startX</code>, <code>this.endX</code>, <code>this.startY</code>, <code>this.endY</code> based on the calculated boundaries.
     */
    private void calculateBounds() {
        //Todo: Will later calculate these value's based on current Agenda.
        this.startX = -100;
        this.endX = 1255;
        this.startY = -50;
        this.endY = 400;
    }

    /**
     * Main method to draw everything on <code>this.canvas</code>.
     *
     * @param graphics object that draws on <code>this.canvas</code>
     */
    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setTransform(this.cameraTransform);

        graphics.translate(-this.startX, -this.startY);

        graphics.drawLine(this.startX, 0, this.endX, 0);
        graphics.drawLine(0, this.startY, 0, this.endY);
        drawTopBar(graphics);
    }

    /**
     * Draws the time information on the horizontal axis.
     *
     * @param graphics  object to draw on
     */
    private void drawTopBar(FXGraphics2D graphics) {
        for (int i = 0; i < 24; i++) { //Later changed in starttune till endtime
            graphics.drawString(i + ".00", i * 50 + 10, -25);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(i * 50 + 50, this.startY,i * 50 + 50, this.endY);
            graphics.setColor(Color.BLACK);
        }
    }

    /**
     * Makes scrolling possible by translating <code>this.cameraTransform</code>.
     *
     * @param scrollEvent  is the eventhandler for scrolling
     */
    private void setOnScroll(ScrollEvent scrollEvent) {
        double scrollPixels = scrollEvent.getDeltaY() / 1.5;
        if (cameraInBounds(scrollPixels, 0)) {
            this.cameraTransform.translate(scrollPixels, 0);
            draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
        }


    }

    /**
     * Calculates if the given translate will fit within the set bounds.
     * @param additionX  the given translate in the X-axis
     * @param additionY  the given translate in the Y-axis
     * @return returns true if the given translate is in bounds
     */
    private boolean cameraInBounds(double additionX, double additionY) {
        return (this.cameraTransform.getTranslateX() + additionX <= 0 &&
                this.cameraTransform.getTranslateX() + additionX >= -(this.endX - this.startX - this.canvas.getWidth()) &&
                this.cameraTransform.getTranslateY() + additionY <= 0 &&
                this.cameraTransform.getTranslateY() + additionY >= -(this.endY - this.startY - this.canvas.getHeight())
        );
    }

    /**
     * Returns <code>this.mainPane</code> to the class AgendaModule so AgendaCanvas can be added to the GUI.
     *
     * @return <code>this.mainPane</code> to the class AgendaModule
     */
    public Node buildAgendaCanvas() {
        return this.mainPane;
    }

}
