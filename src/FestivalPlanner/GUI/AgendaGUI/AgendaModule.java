package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.*;
import java.awt.geom.*;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.ArtistPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.EmptyPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.PodiumPopup;
import FestivalPlanner.GUI.PreferencesGUI;
import animatefx.animation.JackInTheBox;
import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private MenuItem editCurrentlySelectedShow = new MenuItem("Edit Show");
    private MenuItem editArtistsAndPodiumsMenuItem = new MenuItem("Edit Artists & Podiums");
    private MenuItem preferencesMenuItem = new MenuItem("Preferences");
        //HelpMenu
    private Menu helpMenu = new Menu("Help");
    private MenuItem helpGuideMenuItem = new MenuItem("Help Guide");
    private MenuItem javaDocMenuItem = new MenuItem("JavaDoc");
    private MenuItem aboutMenuItem = new MenuItem("About");

    // Layout components
    private AgendaCanvas agendaCanvas;
    private CreationPanel creationPanel;
    private ArtistAndPodiumPanel artistAndPodiumPanel;

    private TextField showNameTextField = new TextField();
    private Label errorLabel = new Label("No error;");

    private Button loadAgendaButton = new Button("Load Agenda");
    private Button saveAgendaButton = new Button("Save Agenda");
    private Button eventSaveButton = new Button("Save/Add Show");
    private Button eventRemoveButton = new Button("Remove");

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
        load(); //TODO: Fix order.
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
                artistAndPodiumPanel.getMainPane(),
                generateSaveAndLoadPanel());

        initEvents();
        return new Scene(this.mainLayoutPane);
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for saving and loading a Agenda.
     *
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for saving and removing events
     */
    private VBox generateSaveAndLoadPanel() {
        VBox saveLoadPanel = genericVBox();

        saveLoadPanel.getChildren().addAll(new Label("Save/Load Agenda"),
                new Label(""),
                this.saveAgendaButton,
                new Label(""),
                this.loadAgendaButton);

        return saveLoadPanel;
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for saving and removing events.
     *
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for saving and removing events
     */
    //TODO: NOTE: now referred to as "name" instead of "save".
//    private VBox generateSavePanel() {
//        VBox savePanel = genericVBox();
//
//        HBox hbox = new HBox();
//        hbox.setSpacing(5);
//        hbox.getChildren().addAll(this.eventSaveButton, this.eventRemoveButton);
//
//        savePanel.getChildren().addAll(new Label("Enter name and save"),
//                new Label("Enter show name:"),
//                this.showNameTextField,
//                new Label(""),
//                hbox);
//        return savePanel;
//    }

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

    private String getLoadAgendaPath() {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            return fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        } catch (NullPointerException e){
            EmptyPopUp emptyPopUp = new EmptyPopUp();
            emptyPopUp.showExceptionPopUp(e);
        }
        return null;
    }

    private void loadAgenda() {
        SaveHandler saveHandler = new SaveHandler();
        this.agenda = saveHandler.readAgendaFromFile(getLoadAgendaPath());
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
    }

    private void saveAgenda() {
        FileChooser fileChooser = new FileChooser();
        SaveHandler saveHandler = new SaveHandler();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            String path = fileChooser.showSaveDialog(new Stage()).getAbsolutePath();
            saveHandler.writeAgendaToFile(path, this.agenda);
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
        this.loadAgendaButton.setOnAction(e -> {
            loadAgenda();
        });

        this.saveAgendaButton.setOnAction(e -> {
            saveAgenda();
        });

        this.agendaCanvas.getCanvas().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY){ //You can only select with a left-click.
                //In case it still shows it should be hidden, since a new item is selected and some
                //actions executed by the ContextMenu depend on selected items.
                contextMenu.hide();
                onPrimaryButton(e);
            } else if (e.getButton() == MouseButton.SECONDARY){
                contextMenu.show(agendaCanvas.getMainPane(), e.getScreenX(), e.getScreenY());
            }
        });

        this.eventRemoveButton.setOnMouseClicked(e -> {
            EmptyPopUp popUp = new EmptyPopUp();
            if (popUp.showDeleteConfirmationPopUp()) {
                this.agenda.getShows().remove(this.currentShow);
                this.agendaCanvas.reBuildAgendaCanvas();
                this.currentShow = null;
            }
        });

        this.eventSaveButton.setOnAction(event -> {
            Podium selectedPodium = this.podiumManager.getPodium(this.artistAndPodiumPanel.getSelectedPodium());

            if (selectedPodium != null && this.artistAndPodiumPanel.getSelectedArtists().size() > 0) {

                this.agenda.getShows().remove(this.currentShow);
                this.agenda.addShow(new Show());

                this.agendaCanvas.reBuildAgendaCanvas();
            }

            this.currentShow = null;
        });
    }

    /**
     * Handles the selecting of <a href="{@docRoot}/FestivalPlanner/AgendaGUI/ShowRectangle2D.html">Rectangle2D</a> in
     * <code>this.agendaCanvas</code>.
     *  @param e  MouseEvent that was set on the mouseButtonClick
     */
    private void onPrimaryButton(MouseEvent e) {
        Show selectedShow = this.agendaCanvas.showAtPoint(new Point2D.Double(e.getX(), e.getY()));
        if (selectedShow != null) {
            //Reset old show.
            if (this.currentShow != null)
                this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(190/360f, .7f, .9f));

            //Starting on new selected.
            this.currentShow = selectedShow;

            //Setting correct color
            this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(100/360f, .7f, .7f));
            this.agendaCanvas.reDrawCanvas();

            //Setting all the stored information to the GUI
            this.showNameTextField.setText(currentShow.getName());
//            this.artistAndPodiumPanel.setArtistsList(this.currentShow.getArtists());
//            this.artistAndPodiumPanel.setSelectedPodium(this.currentShow.getPodium().getName());
        } else {
            if (this.currentShow != null) {
                this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(190/360f, .7f, .9f));
                this.agendaCanvas.reDrawCanvas();
                this.currentShow = null;
            }
        }
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
        editMenu.getItems().addAll(editArtistsAndPodiumsMenuItem, new SeparatorMenuItem(), editCurrentlySelectedShow, new SeparatorMenuItem(), preferencesMenuItem);
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
        editArtistsAndPodiumsMenuItem.setOnAction(e -> {
            ArtistAndPodiumEditorGUI artistAndPodiumEditorGUI = new ArtistAndPodiumEditorGUI(this);
            artistAndPodiumEditorGUI.load();
        });

        editCurrentlySelectedShow.setOnAction(e -> {
            ShowEditorGUI showEditorGUI = new ShowEditorGUI(this);
            showEditorGUI.load();
        });

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

    /**
     * Returns the <code>this.currentShow</code> attribute.
     * @return  this.currentShow
     */
    @Nullable
    public Show getCurrentShow(){
        return this.currentShow;
    }

    /**
     * Sets the <code>this.currentShow</code> attribute to the parameter's value.
     * @return  this.currentShow
     */
    public void setCurrentShow(Show show){
        this.currentShow = show;

        if(this.agenda.getShows().contains(show)){
            this.agenda.getShows().remove(show);
        }
        this.agenda.addShow(show);
        this.agendaCanvas.reBuildAgendaCanvas();
    }

    public Agenda getAgenda(){
        return this.agenda;
    }

    public ArtistManager getArtistManager(){
        return this.artistManager;
    }

    public PodiumManager getPodiumManager(){
        return this.podiumManager;
    }
}
