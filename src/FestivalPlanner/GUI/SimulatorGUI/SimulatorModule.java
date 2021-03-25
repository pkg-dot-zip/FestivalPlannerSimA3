package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.Agenda.Agenda;
import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.GUI.MainGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Pos;
import FestivalPlanner.Logic.SimulatorHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Responsible for placing everything in the correct place in the Simulator GUI and making sure all the buttons work.
 */
public class SimulatorModule extends AbstractGUI {

    private MainGUI mainGUI;
    private Scene simulatorScene;


    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    private SimulatorHandler handler;
    private SimulatorCanvas simulatorCanvas;
    private BorderPane mainPane;
    private Stage stage;
    private AgendaModule agendaModule;
    private ComboBox<String> comboBox;
    private Button button;

    Button agendaButton = new Button(messages.getString("agenda"));
    Button simulatorButton = new Button(messages.getString("simulator"));


    /**
     * The constructor of this class
     * @param stage the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Stage</a> the
     *              simulator module should assign itself to
     * @param agendaModule the <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI.html">SimulatorModule</a>.
     */
    public SimulatorModule(MainGUI mainGUI, Stage stage, AgendaModule agendaModule) {
        //Todo: AgendaModule kan later weg
        this.mainGUI = mainGUI;
        this.stage = stage;
        this.mainPane = new BorderPane();
        this.agendaModule = agendaModule;

        this.handler = new SimulatorHandler(this.agendaModule.getAgenda(), this.agendaModule.getPodiumManager());
        this.simulatorCanvas = new SimulatorCanvas(this.handler, this, 800, 700);
    }

    @Override
    public void load() {
        setup();
        actionHandlingSetup();

        //Initialise values.
        HBox toggleHBox = new HBox();

        //Adding the buttons
        toggleHBox.getChildren().addAll(this.agendaButton, this.simulatorButton);
        toggleHBox.setAlignment(Pos.CENTER);
        toggleHBox.setSpacing(HBOX_SPACING);

        // Disabling button
        this.simulatorButton.setDisable(true);

        this.mainPane.setTop(toggleHBox);
    }

    @Override
    public void setup() {

        //Select NPC part
        // initialising
        VBox npcVBox = new VBox();
        ComboBox npcComboBox = new ComboBox();

        //spacing
        npcVBox.setSpacing(VBOX_SPACING);
        npcVBox.setAlignment(Pos.TOP_CENTER);

        //placeholder text
        npcComboBox.setPromptText(messages.getString("select_NPC"));

        //assigning
        npcVBox.getChildren().addAll(new Label(messages.getString("view_NPC")),
                new Label(messages.getString("click_NPC")), npcComboBox);

        // Podium zoom to part
        VBox podiumZoomVBox = new VBox();
        podiumZoomVBox.setSpacing(VBOX_SPACING);
        podiumZoomVBox.setAlignment(Pos.TOP_CENTER);
        this.comboBox = new ComboBox<>(agendaModule.getPodiumManager().getObservablePodiumList());
        this.button = new Button(messages.getString("go_to"));
        this.comboBox.setPromptText(messages.getString("select_podium"));

        this.button.setOnAction(event -> {
//            simulatorCanvas.moveToPoint();
        });
        podiumZoomVBox.getChildren().addAll(new Label(messages.getString("zoom_to")), this.comboBox, this.button);

        this.mainPane.setRight(podiumZoomVBox);
        this.mainPane.setLeft(npcVBox);
        this.simulatorScene = new Scene(this.mainPane);
    }

    @Override
    public void actionHandlingSetup() {
        this.agendaButton.setOnAction(event -> {
            this.mainGUI.loadAgendaCallBack();
        });
    }

    /**
     * Getter for the scene this class makes.
     * @return The <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> this class is
     * responsible for
     */
    public Scene getSimulatorScene() {
        return this.simulatorScene;
    }

    /**
     * Getter for the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html">Scene</a>
     * the components of this class are assigned to.
     * @return a https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html containing all the components
     * of this class
     */
    public BorderPane getMainPane() {
        return this.mainPane;
    }

    /**
     * Getter for the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Stage</a> of the program
     *
     * @return the main <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> of the program
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Resets <code>this.handler</code>
     */
    public void resetHandler(){
        this.handler.reset(this.agendaModule.getAgenda());
    }
}
