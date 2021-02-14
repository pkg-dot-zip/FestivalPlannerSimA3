package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Agenda;
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

    private Agenda agenda;

    private int startX;
    private int endX;
    private int startY;
    private int endY;

    //TODO: Remember to remove Agenda package before merging since this one is temporary.

    /**
     * Blank constructor of <code>AgendaCanvas</code>.
     */
    public AgendaCanvas() {
        this(new Agenda(), 1920 / 4f, 1080 / 3f);
    }

    /**
     * Constructor of <code>AgendaCanvas</code>.
     * <p>
     * Uses the Agenda given as parameter for <code>this.agenda</code>.
     * @param agenda  sets <code>this.agenda</code> to this object
     */
    public AgendaCanvas(Agenda agenda) {
        this(agenda, 1920 / 4f, 1080 / 3f);
    }

    /**
     * Constructor of <code>AgendaCanvas</code>.
     * <p>
     * Uses the the given width and height for <code>this.canvas.setWidth</code> and <code>this.canvas.setHeight</code>
     * respectively. It creates a new blank Agenda for <code>this.agenda</code>.
     * @param width  sets <code>this.canvas.setWidth</code> to this value
     * @param height  sets <code>this.canvas.setHeight</code> to this value
     */
    public AgendaCanvas(double width, double height) {
        this(new Agenda(), width, height);
    }

    /**
     * Top constructor of <code>AgendaCanvas</code>.
     * <p>
     * Initializes all the attributes of <code>AgendaCanvas</code>.
     * <p>
     * Last action is calling the <code>draw()</code> method,
     * so the canvas will be drawn after initialisation of <code>AgendaCanvas</code>.
     * @param width  sets <code>this.canvas.setWidth</code> to this value
     * @param height  sets <code>this.canvas.setHeight</code> to this value
     */
    public AgendaCanvas(Agenda agenda, double width, double height) {
        this.agenda = agenda;
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
        for (int i = 0; i < 24; i++) { //Later changed in starttime till endtime
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
        AffineTransform translate = new AffineTransform();
        translate.translate(scrollPixels,0);
        if (cameraInBounds(translate)) {
            this.cameraTransform.translate(scrollPixels, 0);
            draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
        }


    }

    /**
     * Calculates if the given translate will fit within the set bounds.
     * <p>
     * Currently only works on translations, scale not yet implemented.
     * @param transform  AffineTransform that is proposed
     * @return returns true if the given translate is in bounds
     */
    private boolean cameraInBounds(AffineTransform transform) {
        return (this.cameraTransform.getTranslateX() + transform.getTranslateX() <= 0 &&
                this.cameraTransform.getTranslateX() + transform.getTranslateX() >= -(this.endX - this.startX - this.canvas.getWidth()) &&
                this.cameraTransform.getTranslateY() + transform.getTranslateY() <= 0 &&
                this.cameraTransform.getTranslateY() + transform.getTranslateY() >= -(this.endY - this.startY - this.canvas.getHeight())
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
