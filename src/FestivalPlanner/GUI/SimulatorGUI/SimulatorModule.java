package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.Logic.SimulatorHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulatorModule extends Application {

    private SimulatorHandler handler;
    private SimulatorCanvas simulatorCanvas;
    private BorderPane mainPane;
    private Stage stage;

    public SimulatorModule() {
        this.mainPane = new BorderPane();
        this.handler = new SimulatorHandler();
        this.simulatorCanvas = new SimulatorCanvas(this.handler, this, 400, 400);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.mainPane.setCenter(this.simulatorCanvas.getMainPane());
        stage.setScene(new Scene(this.mainPane));
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
