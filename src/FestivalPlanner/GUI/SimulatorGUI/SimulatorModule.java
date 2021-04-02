package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.MainGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.application.Platform;
import javafx.geometry.Pos;
import FestivalPlanner.Logic.SimulatorHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
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


    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Handler
    private SimulatorHandler handler;
    private AgendaModule agendaModule;

    //Pane Classes
    private SimulatorCanvas simulatorCanvas;

    //Ux Items
    private BorderPane mainPane;
    private Stage stage;
    private Scene simulatorScene;

    // MenuBar
    private MenuBar menuBar = new MenuBar();
    //FileMenu
    private Menu fileMenu = new Menu(messages.getString("file"));
    private MenuItem loadAgendaMenuItem = new MenuItem(messages.getString("load"));
    private MenuItem exitMenuItem = new MenuItem(messages.getString("exit"));
    //EditMenu
    private Menu optionsMenu = new Menu(messages.getString("options"));
    private MenuItem viewPartMenuItem = new MenuItem(messages.getString("view_Item"));
    private MenuItem timeEditMenuItem = new MenuItem(messages.getString("edit_Time_Speed"));
    private MenuItem npcEditMenuItem = new MenuItem(messages.getString("npc_menu"));
    //HelpMenu
    private Menu helpMenu = new Menu(messages.getString("help"));
    private MenuItem helpGuideMenuItem = new MenuItem(messages.getString("help_guide"));
    private MenuItem javaDocMenuItem = new MenuItem(messages.getString("javadoc"));
    private MenuItem aboutMenuItem = new MenuItem(messages.getString("about"));


    Button agendaButton = new Button(messages.getString("agenda"));
    Button simulatorButton = new Button(messages.getString("simulator"));


    /**
     * The constructor of this class
     * @param stage the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Stage</a> the
     *              simulator module should assign itself to
     * @param agendaModule the <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI.html">SimulatorModule</a>.
     */
    public SimulatorModule(MainGUI mainGUI, Stage stage, AgendaModule agendaModule) {
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

    }

    @Override
    public void setup() {

        //Adding all the children.
        //MenuBar
        fileMenu.getItems().addAll(loadAgendaMenuItem, new SeparatorMenuItem(), exitMenuItem);
        optionsMenu.getItems().addAll(viewPartMenuItem, new SeparatorMenuItem(), timeEditMenuItem, new SeparatorMenuItem(), npcEditMenuItem);
        helpMenu.getItems().addAll(helpGuideMenuItem, javaDocMenuItem, aboutMenuItem);
        menuBar.getMenus().addAll(optionsMenu, helpMenu);


        //Switching buttons
            //Initialise values.
            HBox toggleHBox = new HBox();

            //Adding the buttons
            toggleHBox.getChildren().addAll(this.agendaButton, this.simulatorButton);
            toggleHBox.setAlignment(Pos.CENTER);
            toggleHBox.setSpacing(HBOX_SPACING);

            // Disabling button
            this.simulatorButton.setDisable(true);

        // Top bar
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(this.menuBar, toggleHBox);
        topVBox.setSpacing(5);

        // Setup mainPane

        this.mainPane.setTop(topVBox);
        this.mainPane.setCenter(this.simulatorCanvas.getMainPane());

        this.simulatorScene = new Scene(this.mainPane);
    }

    @Override
    public void actionHandlingSetup() {
        //Generic
        this.stage.setOnCloseRequest(e -> { //When the main window is closed -> Close the entire program.
            Platform.exit();
        });

        //MenuBar

        exitMenuItem.setOnAction(e -> {
            AbstractDialogPopUp.showExitConfirmationPopUp();
        });

        //EditMenu
        viewPartMenuItem.setOnAction(e -> {
            ViewObjectView simulatorViewPopUp = new ViewObjectView(this.handler, this.simulatorCanvas);
            simulatorViewPopUp.load();
        });

        timeEditMenuItem.setOnAction(e -> {
            TimeEditGUI simulatorTimePopUp = new TimeEditGUI(this.handler);
            simulatorTimePopUp.load();
        });

        npcEditMenuItem.setOnAction(e -> {
            NPCEditGUI npcEditGUI = new NPCEditGUI(this.handler);
            npcEditGUI.load();
        });

        //HelpMenu
        aboutMenuItem.setOnAction(e -> {
            AboutPopUp aboutPopUp = new AboutPopUp(this.stage);
            aboutPopUp.load();
        });

        //Agenda button
        this.agendaButton.setOnAction(event -> {
            this.mainGUI.loadAgendaCallBack();
        });
    }

    /**
     * Getter for the scene this class makes.
     * @return The <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> this class is
     * responsible for
     */
    public Scene getScene() {
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
