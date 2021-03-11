package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.TileMap.TileMap;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class SimulatorCanvas {

        private Canvas canvas;
        private SimulatorModule simulatorModule;
        private BorderPane mainPane;
        private AffineTransform cameraTransform;

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
        System.out.println("ik ben aan het laden");
        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
    }

    public void setup() {
        this.mainPane.setCenter(this.canvas);
        this.startX = 0;
        this.endX= tileMap.getMapWidth() * tileMap.getTileWidth();
        this.startY = 0;
        this.endY = tileMap.getMapHeight() * tileMap.getTileHeight();
        this.canvas.setWidth(this.endX);
        this.canvas.setHeight(this.endY);
    }

    public void actionHandlingSetup() {
        //@TODO setonmousclick etc..
    }


    private void draw(FXGraphics2D fxGraphics2D) {
        fxGraphics2D.setTransform(new AffineTransform());
        fxGraphics2D.setBackground(Color.white);
        fxGraphics2D.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        fxGraphics2D.setTransform(this.cameraTransform);
        fxGraphics2D.translate(-this.startX, -this.startY);

        this.tileMap.draw(fxGraphics2D);

    }

    public void update() {
        //@TODO afblijven
    }

    private void onScrolled(MouseEvent mouseEvent) {
        //klinkt moeilijk
    }

    private void onWASD(KeyEvent keyEvent) {
        keyEvent.
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        //later
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        //gepland voor [na de vakantie]
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        //geen zin in
    }

    public void moveToPoint(Point2D point) {
        //jesse is verdrietig
    }
}
