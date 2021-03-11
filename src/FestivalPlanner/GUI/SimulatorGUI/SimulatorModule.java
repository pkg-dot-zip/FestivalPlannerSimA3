package FestivalPlanner.GUI.SimulatorGUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulatorModule extends Application {

    private SimulatorCanvas simulatorCanvas;
    private BorderPane mainPane;

    public SimulatorModule() {
        this.mainPane = new BorderPane();
        this.simulatorCanvas = new SimulatorCanvas(this, 400, 400);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainPane.setCenter(this.simulatorCanvas.getMainPane());
        stage.setScene(new Scene(this.mainPane));
        stage.show();
    }
}
