package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Agenda;
import FestivalPlanner.Agenda.Show;
import FestivalPlanner.Agenda.Stage;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.geom.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//todo: Class needs documentation.
public class AgendaCanvas {

    private BorderPane mainPane;
    private Canvas canvas;
    private AffineTransform cameraTransform;
    private ArrayList<ShowRectangle2D> showRectangles;

    private int startX;
    private int endX;
    private int startY;
    private int endY;

    private Agenda agenda;
    private ArrayList<Stage> usedStages;

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
     *
     * @param agenda sets <code>this.agenda</code> to this object
     */
    public AgendaCanvas(Agenda agenda) {
        this(agenda, 1920 / 4f, 1080 / 3f);
    }

    /**
     * Constructor of <code>AgendaCanvas</code>.
     * <p>
     * Uses the the given width and height for <code>this.canvas.setWidth</code> and <code>this.canvas.setHeight</code>
     * respectively. It creates a new blank Agenda for <code>this.agenda</code>.
     *
     * @param width  sets <code>this.canvas.setWidth</code> to this value
     * @param height sets <code>this.canvas.setHeight</code> to this value
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
     *
     * @param width  sets <code>this.canvas.setWidth</code> to this value
     * @param height sets <code>this.canvas.setHeight</code> to this value
     */
    public AgendaCanvas(Agenda agenda, double width, double height) {
        this.agenda = agenda;
        this.cameraTransform = new AffineTransform();
        this.mainPane = new BorderPane();

        buildAgendaCanvas();

        this.canvas = new ResizableCanvas(this::draw, this.mainPane);
        this.canvas.setHeight(height);
        this.canvas.setWidth(width);

        this.canvas.setOnScroll(this::setOnScroll);

        this.mainPane.setCenter(this.canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics);
    }

    /**
     * When this method is called it calculates and sets the following attributes:
     * <p>
     * <ul>
     * <li>{@link #calculateBounds()}</li>
     * <li>{@link #calculateUsedStages()}</li>
     * <li>{@link #showRectanglesToArrayList()}</li>
     * </ul>
     * <p>
     * When an update has been made to <code>this.agenda</code> this method will make the canvas recalculate the
     * things linked above.
     * <p>
     * This method wil also be called in the constructor of this object and after setting <code>this.agenda</code>.
     * After these actions it is unnecessary to call this method.
     */
    public void buildAgendaCanvas() {
        calculateBounds();
        this.usedStages = calculateUsedStages();
        this.showRectangles = showRectanglesToArrayList();
    }

    /**
     * Getter for <code>this.mainPane</code>.
     *
     * @return this.mainPane
     */
    public Node getMainPane() {
        return mainPane;
    }

    /**
     * Getter for <code>this.agenda</code>.
     *
     * @return Value of <code>this.agenda</code>
     */
    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Setter for <code>this.agenda</code>.
     * <p>
     *
     * @param agenda sets <code>this.agenda</code> to this value.
     */
    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
        buildAgendaCanvas();
        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Creates an ArrayList with a <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">Rectangle2D</a> for every <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a>.
     * <p>
     *
     * @return ArrayList with all the <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangles</a> from the <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">shows</a>
     */
    private ArrayList<ShowRectangle2D> showRectanglesToArrayList() {
        ArrayList<ShowRectangle2D> rectangles = new ArrayList<>();
        for (Show show : this.agenda.getShows()) {
            rectangles.add(createShowRectangle(show));
        }
        return rectangles;
    }

    /**
     * Creates an Rectangle that represents the given <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">show</a>.
     *
     * @param show The <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">show</a> that the returned <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/geom/Rectangle2D.html">rectangle</a> is based on
     * @return a rectangle, this rectangle isn't shown yet but has a size and location based on the given <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">show</a>
     */
    private ShowRectangle2D createShowRectangle(Show show) {
        double startTime = show.getStartTime().getHour() + (show.getStartTime().getMinute() / 60f);
        double endTime = show.getEndTime().getHour() + (show.getEndTime().getMinute() / 60f);
        int stageIndex = this.usedStages.indexOf(show.getStage());

        return new ShowRectangle2D(startTime * 60, stageIndex * 60 + 5, (endTime * 60) - (startTime * 60), 50, show);
    }

    /**
     * Calculates the boundaries of the canvas based on stages and shows in the Agenda. (not yet implemented)
     * <p>
     * Initializes <code>this.startX</code>, <code>this.endX</code>, <code>this.startY</code>, <code>this.endY</code> based on the calculated boundaries.
     */
    private void calculateBounds() {
        //Todo: Will later calculate these value's based on current Agenda.
        this.startX = -100;
        this.endX = 1440;
        this.startY = -50;
        this.endY = 400;
    }

    /**
     * Gets a list of all the used <a href="{@docRoot}/FestivalPlanner/Agenda/Stages.html">Stages</a> in <code>this.agenda</code>, duplicates won't show up
     *
     * @return an ArrayList with all the used <a href="{@docRoot}/FestivalPlanner/Agenda/Stages.html">Stages</a> in <code>this.agenda</code>
     */
    private ArrayList<Stage> calculateUsedStages() {
        Set<Stage> stageSet = new HashSet<>();

        for (Show show : this.agenda.getShows()) {
            stageSet.add(show.getStage());
        }
        return new ArrayList<>(stageSet);
    }

    /**
     * Main method to draw everything on <code>this.canvas</code>.
     *
     * @param graphics object that draws on <code>this.canvas</code>
     */
    private void draw(FXGraphics2D graphics) {
        //resetting the screen
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setTransform(this.cameraTransform);
        graphics.translate(-this.startX, -this.startY);

        drawTopBar(graphics);
        drawStages(graphics);

        for (ShowRectangle2D showRectangle : this.showRectangles) {
            showRectangle.draw(graphics);
            Area overlap = getIntersectArea(showRectangle.getRectangle());
            if (overlap != null) {
                graphics.setColor(Color.RED);
                graphics.fill(overlap);
                graphics.setColor(Color.BLACK);
                graphics.draw(overlap);
            }
        }

    }

    private Area getIntersectArea(Rectangle2D mainRect) {
        for (ShowRectangle2D otherShowRect : this.showRectangles) {
            if (otherShowRect.getRectangle() != mainRect && mainRect.intersects(otherShowRect.getRectangle())) {
                Area mainArea = new Area(mainRect);
                Area otherArea = new Area(otherShowRect.getRectangle());
                mainArea.intersect(otherArea);
                return mainArea;
            }
        }
        return null;
    }

    /**
     * Draws the time information on the horizontal axis to <code>this.canvas</code>.
     *
     * @param graphics object to draw on
     */
    private void drawTopBar(FXGraphics2D graphics) {
        graphics.drawLine(this.startX, 0, this.endX, 0);
        graphics.drawLine(0, this.startY, 0, this.endY);
        for (int i = 0; i < 24; i++) { //Later changed in starttime till endtime
            graphics.drawString(i + ".00", i * 60 + 10, -25); //magic numbers will later be based on cameraTransform etc.
            graphics.setColor(Color.lightGray);
            graphics.drawLine(i * 60 + 60, this.startY, i * 60 + 60, this.endY);
            graphics.setColor(Color.BLACK);
        }
    }

    /**
     * Draws the lines and names for all <a href="{@docRoot}/FestivalPlanner/Agenda/Stages.html">stages</a> in <code>this.usedStages</code>
     *
     * @param graphics object to draw on
     */
    private void drawStages(FXGraphics2D graphics) {
        int stageHeight = 60;
        ArrayList<Stage> usedStages1 = this.usedStages;
        for (int i = 0; i < usedStages1.size(); i++) {
            Stage stage = usedStages1.get(i);
            graphics.drawLine(this.startX, stageHeight * (i + 1), this.endX, stageHeight * (i + 1));
            graphics.drawString(stage.getName(), this.startX + 10, stageHeight * (i + 1) - stageHeight / 2);
        }
    }

    /**
     * Makes scrolling possible by translating <code>this.cameraTransform</code>.
     *
     * @param scrollEvent is the eventhandler for scrolling
     */
    private void setOnScroll(ScrollEvent scrollEvent) {
        double scrollPixels = scrollEvent.getDeltaY() / 1.5;
        AffineTransform translate = new AffineTransform();
        translate.translate(scrollPixels, 0);
        if (cameraInBounds(translate)) {
            this.cameraTransform.translate(scrollPixels, 0);
            draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
        }
    }

    /**
     * Calculates if the given translate will fit within the set bounds.
     * <p>
     * Currently only works on translations, scale not yet implemented.
     *
     * @param transform AffineTransform that is proposed
     * @return returns true if the given translate is in bounds
     */
    private boolean cameraInBounds(AffineTransform transform) {
        return (this.cameraTransform.getTranslateX() + transform.getTranslateX() <= 1 &&
                this.cameraTransform.getTranslateX() + transform.getTranslateX() >= -(this.endX - this.startX - this.canvas.getWidth()) &&
                this.cameraTransform.getTranslateY() + transform.getTranslateY() <= 1 &&
                this.cameraTransform.getTranslateY() + transform.getTranslateY() >= -(this.endY - this.startY - this.canvas.getHeight())
        );
    }

}
