package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.NPC.NPC;
import FestivalPlanner.TileMap.TileMap;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Class that will draw a <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> to a canvas.
 */
public class SimulatorCanvas extends AbstractGUI {

        private Canvas canvas;
        private SimulatorModule simulatorModule;
        private SimulatorHandler simulatorHandler;
        private BorderPane mainPane;

        private AffineTransform cameraTransform;

        private final int CAMERA_SPEED = 30;

        private int startX;
        private int endX;
        private int startY;
        private int endY;


    /**
     * Constructor for SimulatorCanvas
     * @param simulatorModule  The <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI/SimulatorModule.html">SimulatorModule</a> that contains this canvas
     * @param canvasWidth  The initial width of the canvas
     * @param canvasHeight  The initial height of this canvas
     */
    public SimulatorCanvas(SimulatorHandler simulatorHandler, SimulatorModule simulatorModule, double canvasWidth, double canvasHeight) {
        this.simulatorHandler = simulatorHandler;
        this.simulatorModule = simulatorModule;
        this.mainPane = new BorderPane();
        this.canvas = new ResizableCanvas(this::draw, this.mainPane);
        this.canvas.setWidth(canvasWidth);
        this.canvas.setHeight(canvasHeight);
        load();
    }

    @Override
    public void load() {
        setup();
        this.actionHandlingSetup();

        FXGraphics2D g2d = new FXGraphics2D(this.canvas.getGraphicsContext2D());

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
        this.canvas.setFocusTraversable(true);
    }

    @Override
    public void setup() {
        this.mainPane.setCenter(this.canvas);
        this.startX = 0;
        this.endX= this.simulatorHandler.getTileMap().getMapWidth() * this.simulatorHandler.getTileMap().getTileWidth();
        this.startY = 0;
        this.endY = this.simulatorHandler.getTileMap().getMapHeight() * this.simulatorHandler.getTileMap().getTileHeight();
        this.cameraTransform = new AffineTransform();
    }

    @Override
    public void actionHandlingSetup() {
        this.canvas.setOnKeyPressed(this::onWASD);
        this.canvas.setOnMouseDragged(this::onMouseDragged);
        this.canvas.setOnMousePressed(this::onMousePressed);
        this.canvas.setOnMouseReleased(this::onMouseReleased);
        this.canvas.setOnScroll(this::onScrolled);
    }

    /**
     * Getter for <code>this.mainPane</code>
     * @return  <code>this.mainPane</code>
     */
    public BorderPane getMainPane() {
        return mainPane;
    }

    /**
     * Draws everything on <code>this.canvas</code>.
     * @param g2d  object that draws on <code>this.canvas</code>
     */
    private void draw(FXGraphics2D g2d) {
        g2d.setTransform(new AffineTransform());
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        g2d.setTransform(this.cameraTransform);
        g2d.translate(-this.startX, -this.startY);

        this.simulatorHandler.draw(g2d);
    }

    /**
     * Updates all the items, cals {@link #draw(FXGraphics2D)} when done
     * @param deltaTime  The time it took between last update call (FPS = 1/deltaTime)
     */
    public void update(Double deltaTime) {
//        System.out.println(1/deltaTime);
        this.simulatorHandler.update(deltaTime);
    }

    /**
     * Handles the event when the user scrolls.
     * @param scrollEvent  The ScrollEvent that was used to scroll
     */
    private void onScrolled(ScrollEvent scrollEvent) {
        double scaleFactor = 1 + (scrollEvent.getDeltaY() / 1000);
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleFactor, scaleFactor);
        if (cameraInBounds(transform)) {
            double tempX = this.cameraTransform.getTranslateX();
            double tempY = this.cameraTransform.getTranslateY();
            this.cameraTransform.translate(-tempX, -tempY);
            this.cameraTransform.scale(scaleFactor, scaleFactor);
            this.cameraTransform.translate(tempX, tempY);
        }
    }

    /**
     * Handles the event of a keyboard key pressed by the user.
     * <p>
     * When WASD or Arrow-keys are pressed the method will move the screen in
     * the corresponding direction.
     * @param keyEvent  The KeyEvent that detected the users keyboardpress
     */
    private void onWASD(KeyEvent keyEvent) {
        double verticalPixels = 0;
        double horizontalPixels = 0;

        switch (keyEvent.getCode()) {
            case UP:
            case W:
                verticalPixels = CAMERA_SPEED;
                break;
            case LEFT:
            case A:
                horizontalPixels = CAMERA_SPEED;
                break;
            case DOWN:
            case S:
                verticalPixels = -CAMERA_SPEED;
                break;
            case RIGHT:
            case D:
                horizontalPixels = -CAMERA_SPEED;
                break;
        }

        AffineTransform transform = new AffineTransform();
        transform.translate(horizontalPixels, verticalPixels);
        if(cameraInBounds(transform)) {
            this.cameraTransform.translate(horizontalPixels, verticalPixels);
        }
    }

    private Point2D dragPoint = null;

    /**
     * Handles the event when the user presses the mouse-button
     * <p>
     * If the primary button is pressed <code>this.dragPoint</code> will be set to the
     * position the mouse has at that time.
     * @param mouseEvent  The MouseEvent the user used to click
     */
    private void onMousePressed(MouseEvent mouseEvent) {

        //Todo: Only for debugging NPC, needs to be removed when done.
        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            Point2D canvasPoint = getCanvasPoint(new Point2D.Double(mouseEvent.getX(), mouseEvent.getY()));
            for (NPC npc : this.simulatorHandler.getNpcList()) {
                npc.setTarget(canvasPoint);
            }
        } else {
            dragPoint = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());
        }

    }

    /**
     * Handles the event when the user releases the mouse-button
     * <p>
     * If the primary button is released <code>this.dragPoint</code> will be reset to null
     * @param mouseEvent  The MouseEvent the user used to click
     */
    private void onMouseReleased(MouseEvent mouseEvent) {
        dragPoint = null;
    }

    /**
     * Handles the event when the user drags the mouse-button across the screen
     * <p>
     * If the primary button is dragged the method wil check if the operation is allowed by calling {@link #cameraInBounds(AffineTransform)}
     * If correct the screen will be moved to the new position. When done <code>this.dragPoint</code> will be reset to null
     * @param mouseEvent  The MouseEvent the user used to drag
     */
    private void onMouseDragged(MouseEvent mouseEvent) {
        if (this.dragPoint != null) {
            double horizontalPixels = (mouseEvent.getX() - dragPoint.getX()) / this.cameraTransform.getScaleX();
            double verticalPixels = (mouseEvent.getY() - dragPoint.getY()) / this.cameraTransform.getScaleY();

            AffineTransform transform = new AffineTransform();
            transform.translate(horizontalPixels, verticalPixels);
            if (this.cameraInBounds(transform)) {
                this.cameraTransform.translate(horizontalPixels, verticalPixels);
            }

            this.dragPoint = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    /**
     * Moves the screen to the centre of the proposed point.
     * @param point  The proposed point to move to
     */
    public void moveToPoint(Point2D point) {
        Point2D centrePoint = getCanvasPoint(new Point2D.Double(
                this.canvas.getWidth() / 2f,
                this.canvas.getHeight() / 2f
        ));
        cameraTransform.translate(centrePoint.getX() - point.getX(), centrePoint.getY() - point.getY());
    }

    /**
     * Given a point on the screen this methode wil return the coördinates that that point represents.
     * <p>
     * The given point wil be moved based on the current panning and zoom, stored in <code>rhis.cameraTransform</code>
     * @param point2D  The position on the screen that needs to be transformed
     * @return  The point on the field that the given point represents
     */
    public Point2D getCanvasPoint(Point2D point2D) {
        return new Point2D.Double(
                (point2D.getX() - this.cameraTransform.getTranslateX()) / this.cameraTransform.getScaleX(),
                (point2D.getY() - this.cameraTransform.getTranslateY()) / this.cameraTransform.getScaleY()
        );
    }

    /**
     * Calculates if the given translate will fit within the set bounds.
     * <p>
     * Currently only works on translations, scale not yet implemented.
     * @return  true if the given translate is in bounds
     */
    private boolean cameraInBounds(AffineTransform transform) {
        return ((this.cameraTransform.getTranslateX() + transform.getTranslateX()) / this.cameraTransform.getScaleX() <= 1 &&
                (this.cameraTransform.getTranslateX() + transform.getTranslateX()) / this.cameraTransform.getScaleX() >= -((this.endX - this.startX) - (this.canvas.getWidth() / this.cameraTransform.getScaleX())) &&
                (this.cameraTransform.getTranslateY() + transform.getTranslateY()) / this.cameraTransform.getScaleY() <= 1 &&
                (this.cameraTransform.getTranslateY() + transform.getTranslateY()) / this.cameraTransform.getScaleY() >= -((this.endY - this.startY) - (this.canvas.getHeight() / this.cameraTransform.getScaleY())) &&
                (this.cameraTransform.getScaleX() * transform.getScaleX()) < 2.5 &&
                (this.cameraTransform.getScaleX() * transform.getScaleX()) > 0.5
        );
    }
}
