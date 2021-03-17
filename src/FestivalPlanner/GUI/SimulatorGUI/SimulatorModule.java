package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class SimulatorModule extends AbstractGUI {

    private Scene simulatorScene;
    private Scene agendaScene;

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
        this.simulatorCanvas = new SimulatorCanvas(this, 600, 500);
    }


    public Stage getStage() {
        return stage;
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();

        //Initialise values.
        HBox toggleHBox = new HBox();

        //Adding the buttons
        toggleHBox.getChildren().addAll(this.agendaToggleButton, this.simulatorToggleButton);
        toggleHBox.setAlignment(Pos.CENTER);
        toggleHBox.setSpacing(HBOX_SPACING);

        // Disabling button
        this.simulatorToggleButton.setDisable(true);

        this.mainPane.setTop(toggleHBox);
    }

    @Override
    public void setup() {
        this.simulatorScene = new Scene(this.mainPane);
    }

    @Override
    public void actionHandlingSetup() {
        this.agendaToggleButton.setOnAction(event -> stage.setScene(this.agendaScene));
    }

    public void setAgendaScene(Scene agendaScene) {
        this.agendaScene = agendaScene;
    }

    public Scene getSimulatorScene() {
        return simulatorScene;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
