package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.CommonNodeRetriever;
import FestivalPlanner.GUI.MainGUI;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Contains methods for placing all elements in the Simulator GUI, and making sure all the buttons work.
 */
public class SimulatorModule extends AbstractGUI {

    private MainGUI mainGUI;

    //LanguageHandling.
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Handler.
    private SimulatorHandler handler;
    private AgendaModule agendaModule;

    //Pane Classes.
    private SimulatorCanvas simulatorCanvas;
    private BorderPane mainPane = new BorderPane();
    private VBox topVBox = new VBox();
    private VBox rightVBox = new VBox();
    private HBox toggleHBox = new HBox();

    //Ux Items.
    private Stage stage;
    private Scene simulatorScene;

    //Time.
    private Label timeLabel;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_TIME;

    // MenuBar.
    private MenuBar menuBar = new MenuBar();
        //FileMenu.
    private Menu fileMenu = new Menu(messages.getString("file"));
    private MenuItem loadAgendaMenuItem = new MenuItem(messages.getString("load"));
    private MenuItem exitMenuItem = new MenuItem(messages.getString("exit"));
        //EditMenu.
    private Menu optionsMenu = new Menu(messages.getString("options"));
    private MenuItem viewPartMenuItem = new MenuItem(messages.getString("view_Item"));
    private MenuItem timeEditMenuItem = new MenuItem(messages.getString("edit_Time_Speed"));
    private MenuItem npcEditMenuItem = new MenuItem(messages.getString("npc_menu"));

    //Buttons.
    private Button agendaButton = new Button(messages.getString("agenda"));
    private Button simulatorButton = new Button(messages.getString("simulator"));

    //Active Shows.
    private Label activeShowsLabel = new Label(messages.getString("active_shows"));
    private ListView<String> listView;

    /**
     * The constructor of this class.
     * @param mainGUI  the parent class responsible for making switching work.
     * @param stage  the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Stage</a> the
     *              simulator module should assign itself to
     * @param agendaModule  the <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI.html">SimulatorModule</a>
     */
    public SimulatorModule(MainGUI mainGUI, Stage stage, AgendaModule agendaModule) {
        this.mainGUI = mainGUI;
        this.stage = stage;
        this.agendaModule = agendaModule;
        this.handler = new SimulatorHandler(this.agendaModule.getAgenda(), this.agendaModule.getPodiumManager(), this.agendaModule.getArtistManager());
        this.simulatorCanvas = new SimulatorCanvas(this.handler, this, 800, 700);
        this.listView = new ListView();
    }

    @Override
    public void load() {
        this.setup();
        this.actionHandlingSetup();
    }

    @Override
    public void setup() {
        //Initialise values.
        this.timeLabel = new Label("Time: " + handler.getTime());
        this.simulatorButton.setDisable(true);

        //Alignment & spacing.
        this.topVBox.setSpacing(VBOX_SPACING);
        this.toggleHBox.setAlignment(Pos.CENTER);
        this.toggleHBox.setSpacing(HBOX_SPACING);
        this.timeLabel.setFont(new Font(16));
        this.activeShowsLabel.setAlignment(Pos.TOP_CENTER);
            //Right VBox.
        this.rightVBox.setMinHeight(700);
        this.rightVBox.setMinWidth(50);
        this.rightVBox.setMaxWidth(150);
        this.rightVBox.setPadding(new Insets(0,10,0,0));
        this.rightVBox.setAlignment(Pos.CENTER);
        this.rightVBox.setSpacing(VBOX_SPACING);
            //ListView
        this.listView.setMinHeight(200);
        this.listView.setMaxHeight(500);

        //Adding all the children.
        this.toggleHBox.getChildren().addAll(this.agendaButton, this.simulatorButton);
        this.topVBox.getChildren().addAll(this.menuBar, this.toggleHBox, this.timeLabel);
        this.rightVBox.getChildren().addAll(this.activeShowsLabel, this.listView);
            //MenuBar.
        this.fileMenu.getItems().addAll(this.loadAgendaMenuItem, new SeparatorMenuItem(), this.exitMenuItem);
        this.optionsMenu.getItems().addAll(this.viewPartMenuItem, new SeparatorMenuItem(), this.timeEditMenuItem, new SeparatorMenuItem(), this.npcEditMenuItem);
        this.menuBar.getMenus().addAll(this.optionsMenu, CommonNodeRetriever.getHelpMenu(this.stage));

        //Adding it all together.
        this.mainPane.setTop(this.topVBox);
        this.mainPane.setCenter(this.simulatorCanvas.getMainPane());
        this.mainPane.setRight(rightVBox);
        this.simulatorScene = new Scene(this.mainPane);
    }

    @Override
    public void actionHandlingSetup() {
        //MenuBar.
        this.exitMenuItem.setOnAction(e -> {
            AbstractDialogPopUp.showExitConfirmationPopUp();
        });
            //EditMenu.
        this.viewPartMenuItem.setOnAction(e -> {
            ViewObjectView simulatorViewPopUp = new ViewObjectView(this.handler, this.simulatorCanvas);
            simulatorViewPopUp.load();
        });

        this.timeEditMenuItem.setOnAction(e -> {
            TimeEditGUI simulatorTimePopUp = new TimeEditGUI(this.handler);
            simulatorTimePopUp.load();
        });

        this.npcEditMenuItem.setOnAction(e -> {
            NPCEditGUI npcEditGUI = new NPCEditGUI(this.handler);
            npcEditGUI.load();
        });

        //Agenda Button.
        this.agendaButton.setOnAction(event -> {
            this.mainGUI.loadAgendaCallBack();
        });
    }

    /**
     * Sets / updates the timeLabel to the handler's current time.
     */
    void updateTime() {
        LocalTime time = this.handler.getTime();
        this.timeLabel.setText("\tTime: " + time.format(dateTimeFormatter));

        this.listView.getItems().clear();
        this.handler.getActiveShows(time).forEach(e -> {
            this.listView.getItems().add(e.getName());
        });
    }

    /**
     * Returns the scene this class initialised.
     * @return  the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> this class contains
     */
    public Scene getScene() {
        return this.simulatorScene;
    }

    /**
     * Returns the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html">Scene</a>
     * the components of this class are assigned to.
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html">BorderPane</a> containing all the components
     * of this class
     */
    public BorderPane getMainPane() {
        return this.mainPane;
    }

    /**
     * Returns the <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Stage</a> of this class.
     * @return  the main <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> of the program
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Resets <code>this.handler</code> to the agenda in this agendaModule.
     */
    public void resetHandler(){
        this.handler.reset(this.agendaModule.getAgenda());
    }
}