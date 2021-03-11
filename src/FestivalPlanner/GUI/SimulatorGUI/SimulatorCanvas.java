package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.TileMap.TileMap;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SimulatorCanvas {

        private Canvas canvas;
        private SimulatorModule simulatorModule;
        private BorderPane mainPane;
        private AffineTransform cameraTransform;
        private final int CAMERA_SPEED = 30;

        private int startX;
        private int endX;
        private int startY;
        private int endY;

        //@TODO add simulatorHandler

        //TESTING PURPOSES
        JsonConverter converter = new JsonConverter();
        private TileMap tileMap = converter.JSONToTileMap("/testMap.json");

    public SimulatorCanvas(SimulatorModule simulatorModule, double canvasWidth, double canvasHeight) {
        this.simulatorModule = simulatorModule;
        this.mainPane = new BorderPane();
        this.canvas = new ResizableCanvas(this::draw, this.mainPane);
        this.canvas.setWidth(canvasWidth);
        this.canvas.setHeight(canvasHeight);
        load();
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

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

    public void setup() {
        this.mainPane.setCenter(this.canvas);
        this.startX = 0;
        this.endX= tileMap.getMapWidth() * tileMap.getTileWidth();
        this.startY = 0;
        this.endY = tileMap.getMapHeight() * tileMap.getTileHeight();
        this.cameraTransform = new AffineTransform();
    }

    public void actionHandlingSetup() {
        //@TODO setonmousclick etc..
        this.canvas.setOnKeyPressed(this::onWASD);
        this.canvas.setOnMouseDragged(this::onMouseDragged);
        this.canvas.setOnMousePressed(this::onMousePressed);
        this.canvas.setOnMouseReleased(this::onMouseReleased);
    }

    
    private void draw(FXGraphics2D fxGraphics2D) {
        fxGraphics2D.setTransform(new AffineTransform());
        fxGraphics2D.setBackground(Color.white);
        fxGraphics2D.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        fxGraphics2D.setTransform(this.cameraTransform);
        fxGraphics2D.translate(-this.startX, -this.startY);

        this.tileMap.draw(fxGraphics2D);
    }

    public void update(Double deltaTime) {

    }

    private void onScrolled(MouseEvent mouseEvent) {
        //klinkt moeilijk
    }

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

        if(cameraInBounds(horizontalPixels, verticalPixels)) {
            this.cameraTransform.translate(horizontalPixels, verticalPixels);
        }
    }

    private Point2D dragPoint = null;

    private void onMousePressed(MouseEvent mouseEvent) {
        dragPoint = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        dragPoint = null;
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        if (this.dragPoint != null) {
            double horizontalPixels = (mouseEvent.getX() - dragPoint.getX());
            double verticalPixels = (mouseEvent.getY() - dragPoint.getY());

            if (this.cameraInBounds(horizontalPixels, verticalPixels)) {
                this.cameraTransform.translate(horizontalPixels, verticalPixels);
            }

            this.dragPoint = new Point2D.Double(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    public void moveToPoint(Point2D point) {
        this.cameraTransform.translate(point.getX() - (this.cameraTransform.getTranslateX() + this.tileMap.getMapWidth()* this.tileMap.getTileWidth()/ 2), point.getY() - (this.cameraTransform.getTranslateY() + this.tileMap.getMapHeight() * this.tileMap.getTileHeight() /2));
//        this.cameraTransform.translate(
//                point.getX() - this.cameraTransform.getTranslateX() + this.simulatorModule.getStage().getWidth() / 2,
//                point.getY() - this.cameraTransform.getTranslateY() + this.simulatorModule.getStage().getHeight() / 2
//        );
    }

    /**
     * Calculates if the given translate will fit within the set bounds.
     * <p>
     * Currently only works on translations, scale not yet implemented.
     * @return  true if the given translate is in bounds
     */
    private boolean cameraInBounds(double translateX, double translateY) {
        return (this.cameraTransform.getTranslateX() + translateX <= 1 &&
                this.cameraTransform.getTranslateX() + translateX >= -(this.endX - this.startX - this.canvas.getWidth()) &&
                this.cameraTransform.getTranslateY() + translateY <= 1 &&
                this.cameraTransform.getTranslateY() + translateY >= -(this.endY - this.startY - this.canvas.getHeight())
        );
    }
}
