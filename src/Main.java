import FestivalPlanner.TileMap.TileManager;
import FestivalPlanner.Util.JSONConverter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Main extends Application {

    private TileManager tileManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fileName = "/testMap.json";
        JSONConverter jsonConverter = new JSONConverter();
        this.tileManager = jsonConverter.JSONToTileMap(fileName);

        BorderPane pane = new BorderPane();
        Canvas canvas = new ResizableCanvas(this::draw, pane);
        pane.setCenter(canvas);

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    private void draw(FXGraphics2D graphics2D) {
        graphics2D.scale(2,2);
        tileManager.getTile(408).draw(graphics2D, 0,0);
    }

}
