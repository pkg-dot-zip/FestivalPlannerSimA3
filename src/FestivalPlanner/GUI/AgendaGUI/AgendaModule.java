package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.*;
import java.awt.geom.*;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.ArtistPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.EmptyPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.PodiumPopup;
import FestivalPlanner.GUI.PreferencesGUI;
import animatefx.animation.JackInTheBox;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalTime;

//TODO: The way you enter a time feels counter-intuitive.

/**
 * Responsible for placing everything in the correct place in the GUI and making sure all the buttons work.
 */
public class AgendaModule {

    private Stage stage;

    // Agenda variables
    private Agenda agenda = new Agenda();
    private ArtistManager artistManager = new ArtistManager();
    private PodiumManager podiumManager = new PodiumManager();
    private Show currentShow = null;

    // Panes
    private BorderPane mainLayoutPane = new BorderPane();
    private HBox generalLayoutHBox = new HBox();

    // MenuBar
    private MenuBar menuBar = new MenuBar();
        //FileMenu
    private Menu fileMenu = new Menu("File");
    private MenuItem exitMenuItem = new MenuItem("Exit");
        //EditMenu
    private Menu editMenu = new Menu("Edit");
    private MenuItem preferencesMenuItem = new MenuItem("Preferences");
        //HelpMenu
    private Menu helpMenu = new Menu("Help");
    private MenuItem helpGuideMenuItem = new MenuItem("Help Guide");
    private MenuItem javaDocMenuItem = new MenuItem("JavaDoc");
    private MenuItem aboutMenuItem = new MenuItem("About");

    // Layout components
    private AgendaCanvas agendaCanvas;
    private CreationPanel creationPanel;
    private TimeAndPopularityPanel timeAndPopularityPanel = new TimeAndPopularityPanel();
    private ArtistAndPodiumPanel artistAndPodiumPanel;

    private TextField showNameTextField = new TextField();
    private TextField fileDirTextField = new TextField();
    private Label errorLabel = new Label("No error;");

    private Button loadAgendaButton = new Button("Load Agenda");
    private Button saveAgendaButton = new Button("Save Agenda");
    private Button eventSaveButton = new Button("Save/Add Show");
    private Button eventRemoveButton = new Button("Remove");

    private File selectedAgendaFile;

    //ContextMenu
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem swapContextItem = new MenuItem("Swap"); //TODO: Allow multiple items to be selected. Only swap if 2 are selected.
    private Menu rearrangeContextItem = new Menu("Rearrange"); //TODO: Shuffle (random), order in alphabetical order, reverse alphabetical order etc. This only switches the Artist & Podium (?).
    private MenuItem shuffleRearrangeContextItem = new MenuItem("Shuffle (Random)");
    private MenuItem alphabeticalRearrangeContextItem = new MenuItem("Alphabetical order");
    private MenuItem reverseAlphabeticalContextItem = new MenuItem("Reverse Alphabetical order");
    private MenuItem editContextItem = new MenuItem("Edit");
    private MenuItem removeContextItem = new MenuItem("Remove");


    /**
     * Constructor of <code>AgendaModule</code>.
     * <p>
     * The given <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> will be stored
     * as a parameter so this stage can be referenced as the main
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>.
     * </p>
     *
     * @param stage  will be stored
     *              as a parameter so this stage can be referenced as the owner of the sub stages
     *              <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>
     */
    public AgendaModule(Stage stage) {
        this.stage = stage;

        setup();
        actionHandlingSetup();
        load();
        this.creationPanel.updateArtistComboBox();
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a>
     * for the <a href="{@docRoot}/FestivalPlanner/GUI/MainGUI.html">MainGUI</a> that contains all the GUI components
     * by calling all the generate methods.
     *
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> with the layout
     * for the <a href="{@docRoot}/FestivalPlanner.GUI/MainGUI.html">MainGUI</a> class
     */
    public Scene generateGUILayout() {
        this.generalLayoutHBox.setSpacing(20);
        this.generalLayoutHBox.setPadding(new Insets(0, 10, 0, 10));

        this.generalLayoutHBox.getChildren().addAll(this.creationPanel.getMainPane(),
                this.timeAndPopularityPanel.getMainPane(),
                artistAndPodiumPanel.getMainPane(),
                generateSavePanel(),
                generateSaveAndRemovePanel());

        initEvents();
        return new Scene(this.mainLayoutPane);
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for saving and removing events.
     *
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for saving and removing events
     */
    private VBox generateSaveAndRemovePanel() {
        VBox saveLoadPanel = genericVBox();

        HBox hBox = new HBox();
        hBox.setSpacing(3);
        hBox.getChildren().addAll(this.saveAgendaButton, this.loadAgendaButton);

        this.fileDirTextField.setEditable(false);

        saveLoadPanel.getChildren().addAll(new Label("Save/Load Agenda"),
                new Label("Select file: "),
                this.fileDirTextField,
                new Label(""),
                hBox);

        return saveLoadPanel;
    }

    private VBox generateSavePanel() {
        VBox savePanel = genericVBox();

        savePanel.getChildren().addAll(new Label("Enter name and save"),
                new Label("Enter show name:"),
                this.showNameTextField,
                new Label(""),
                this.eventSaveButton);
        return savePanel;
    }

    /**
     * CallBack method to open <code>this.artistPopup</code>.
     */
    public void artistPopupCallBack() {
        ArtistPopUp artistPopUp = new ArtistPopUp(this.stage, this.artistManager, this.creationPanel);
        artistPopUp.load();
    }

    /**
     * CallBack method to open <code>this.podiumCallBack</code>.
     */
    public void podiumPopupCallBack() {
        PodiumPopup podiumPopup = new PodiumPopup(this.stage, this.podiumManager, this.creationPanel);
        podiumPopup.load();
    }

    private void loadAgenda() {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            this.fileDirTextField.setText(fileChooser.showOpenDialog(new Stage()).getAbsolutePath());
        } catch (NullPointerException e){
            EmptyPopUp emptyPopUp = new EmptyPopUp();
            emptyPopUp.showExceptionPopUp(e);
        }
    }

    private void saveAgenda() {
        FileChooser fileChooser = new FileChooser();
        SaveHandler saveHandler = new SaveHandler();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            this.fileDirTextField.setText(fileChooser.showSaveDialog(new Stage()).getAbsolutePath());
            saveHandler.writeAgendaToFile(this.fileDirTextField.getText(), this.agenda);
        } catch (NullPointerException e){
            EmptyPopUp emptyPopUp = new EmptyPopUp();
            emptyPopUp.showExceptionPopUp(e);
        }
    }

    /**
     * Initiates all the events that are used in the GUI.
     */
    private void initEvents() {
//TODO: Move to actionHandlingSetup()!!!!!!!!!!!!!
        this.fileDirTextField.setOnMouseClicked(e -> {
            //Removed saveAgenda(); here since we have a method for that.
        });

        this.loadAgendaButton.setOnAction(e -> {
            SaveHandler saveHandler = new SaveHandler();
            if (this.fileDirTextField.getText().equals("")) {
                loadAgenda();
            }
            this.agenda = saveHandler.readAgendaFromFile(this.fileDirTextField.getText());
            this.agendaCanvas.setAgenda(this.agenda);
            this.currentShow = null;

            //Update podiumManager and ArtistManager.
            for (Show show : this.agenda.getShows()) {
                if (show != null && show.getPodium() != null) {
                    if (!this.podiumManager.containsPodium(show.getPodium().getName()))
                        this.podiumManager.addPodium(show.getPodium());
                }

                if (show != null && show.getArtists() != null) {
                    for (Artist artist : show.getArtists()) {
                        if (artist != null && !this.artistManager.containsArtist(artist.getName()))
                            this.artistManager.addArtist(artist);
                    }
                }
            }
            this.creationPanel.updatePodiumComboBox();
            this.creationPanel.updateArtistComboBox();
        });

        this.saveAgendaButton.setOnAction(e -> {
            saveAgenda();
        });

        this.agendaCanvas.getCanvas().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY){ //You can only select with a left-click.
                Show selectedShow = this.agendaCanvas.showAtPoint(new Point2D.Double(e.getX(), e.getY()));
                //In case it still shows it should be hidden, since a new item is selected and some
                //actions executed by the ContextMenu depend on selected items.
                contextMenu.hide();
                if (selectedShow != null) {
                    //Reset old show.
                    if (this.currentShow != null)
                        this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(190/360f, .7f, .9f));

                    //Starting on new selected.
                    this.currentShow = selectedShow;

                    this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(100/360f, .7f, .7f));
                    this.agendaCanvas.reDrawCanvas();
                    this.showNameTextField.setText(currentShow.getName());
                    this.artistAndPodiumPanel.setArtistsList(this.currentShow.getArtists());
                    this.artistAndPodiumPanel.setSelectedPodium(this.currentShow.getPodium().getName());
                    this.timeAndPopularityPanel.setStartTimeText(this.currentShow.getStartTime().toString());
                    this.timeAndPopularityPanel.setEndTimeText(this.currentShow.getEndTime().toString());
                    this.timeAndPopularityPanel.setPopularitySlider(this.currentShow.getExpectedPopularity());
                } else {
                    if (this.currentShow != null) {
                        this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(190/360f, .7f, .9f));
                        this.agendaCanvas.reDrawCanvas();
                        this.currentShow = null;
                    }
                }
            } else if (e.getButton() == MouseButton.SECONDARY){
                contextMenu.show(agendaCanvas.getMainPane(), e.getScreenX(), e.getScreenY());
            }
        });

        this.eventRemoveButton.setOnAction(event -> {
            //TODO: Need a way to select classes first.
        });

        this.eventSaveButton.setOnAction(event -> {
            LocalTime startTime = this.timeAndPopularityPanel.getStartTime();
            LocalTime endTime = this.timeAndPopularityPanel.getEndTime();
            Podium selectedPodium = this.podiumManager.getPodium(this.artistAndPodiumPanel.getSelectedPodium());

            if (startTime != null && endTime != null && selectedPodium != null && this.artistAndPodiumPanel.getSelectedArtists().size() > 0) {

                this.agenda.getShows().remove(this.currentShow);
                this.agenda.addShow(new Show(this.showNameTextField.getText(),
                        startTime,
                        endTime,
                        this.timeAndPopularityPanel.getPopularity(),
                        selectedPodium,
                        this.artistAndPodiumPanel.getSelectedArtists()));

                this.agendaCanvas.reBuildAgendaCanvas();
            }

            this.currentShow = null;
        });
    }

    /**
     * Returns a VBox with pre-configured settings. This method was made to avoid duplicate code.
     * @return  VBox.
     */
    public VBox genericVBox(){
        VBox vBoxToReturn = new VBox();
        vBoxToReturn.setSpacing(5);
        vBoxToReturn.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        vBoxToReturn.setPadding(new Insets(0, 2, 10, 2));
        vBoxToReturn.setMaxHeight(150);
        vBoxToReturn.setAlignment(Pos.BASELINE_CENTER);
        return vBoxToReturn;
    }

    public void setup(){
        //Initialise values.
        this.agendaCanvas = new AgendaCanvas(this.agenda);
        this.creationPanel = new CreationPanel(this, this.podiumManager, this.artistManager);
        this.artistAndPodiumPanel = new ArtistAndPodiumPanel(new ComboBox<>(this.creationPanel.getObservablePodiumList()), new ComboBox<>(this.creationPanel.getObservableArtistList()), this.artistManager);

        //Adding all the children
            //MenuBar
        fileMenu.getItems().addAll(exitMenuItem);
        editMenu.getItems().addAll(preferencesMenuItem);
        helpMenu.getItems().addAll(helpGuideMenuItem, javaDocMenuItem, aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
            //ContextMenu
        rearrangeContextItem.getItems().addAll(alphabeticalRearrangeContextItem, reverseAlphabeticalContextItem, new SeparatorMenuItem(), shuffleRearrangeContextItem);
        contextMenu.getItems().addAll(swapContextItem, rearrangeContextItem, editContextItem, new SeparatorMenuItem(), removeContextItem);
    }

    public void load(){
        //Adding it all together.
        //TODO: Put this elsewhere:
        VBox tempVBox = new VBox();
        tempVBox.getChildren().addAll(this.menuBar, this.generalLayoutHBox);
        this.mainLayoutPane.setTop(tempVBox);
        this.mainLayoutPane.setCenter(this.agendaCanvas.getMainPane());
        new JackInTheBox(this.mainLayoutPane).play();
    }

    public void actionHandlingSetup(){
        this.stage.setOnCloseRequest(e -> { //When the main window is closed -> Close the entire program.
            Platform.exit();
        });

        //MenuBar
            //FileMenu
        exitMenuItem.setOnAction(e -> {
            EmptyPopUp emptyPopUp = new EmptyPopUp();
            emptyPopUp.showExitConfirmationPopUp();
        });
            //EditMenu
        preferencesMenuItem.setOnAction(e -> {
            PreferencesGUI preferencesGUI = new PreferencesGUI(this.stage);
            preferencesGUI.load();
        });
            //HelpMenu
        aboutMenuItem.setOnAction(e -> {
            AboutPopUp aboutPopUp = new AboutPopUp(this.stage);
            aboutPopUp.load();
        });
    }
}
