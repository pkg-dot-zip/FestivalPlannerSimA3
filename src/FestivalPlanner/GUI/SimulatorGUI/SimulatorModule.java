package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class SimulatorModule extends AbstractGUI {

    private Scene simulatorScene;

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    private SimulatorCanvas simulatorCanvas;
    private BorderPane mainPane;
    private Stage stage;

    ToggleButton agendaToggleButton = new ToggleButton(messages.getString("agenda"));
    ToggleButton simulatorToggleButton = new ToggleButton(messages.getString("simulator"));

    public SimulatorModule(Stage stage) {
        this.stage = stage;
        this.mainPane = new BorderPane();
        this.simulatorCanvas = new SimulatorCanvas(this, 400, 400);
    }


    public Stage getStage() {
        return stage;
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();
        stage.setScene(this.simulatorScene);
        stage.show();
    }

    @Override
    public void setup() {
        this.mainPane.setCenter(this.simulatorCanvas.getMainPane());

        this.simulatorScene = new Scene(this.mainPane);
    }

    @Override
    public void actionHandlingSetup() {

    }
}
