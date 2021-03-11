package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.TileMap.TileMap;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

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
        this.mainPane.setCenter(this.canvas);
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    private void draw(FXGraphics2D fxGraphics2D) {
        this.tileMap.draw(fxGraphics2D);
    }
}
