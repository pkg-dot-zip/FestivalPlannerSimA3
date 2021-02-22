package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.*;

import java.awt.geom.*;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalTime;

//TODO The way you enter a time feels counterintuitive
//TODO Artist popup hasn't been made yet

/**
 * Responsible for placing everything in the correct place in the GUI and making sure all the buttons work.
 */

public class AgendaModule {

    // Agenda variables
    private Agenda agenda;
    private ArtistManager artistManager;
    private PodiumManager podiumManager;
    private Show currentShow;

    // Popups
    private PodiumPopup podiumPopup;

    // Panes
    private BorderPane mainLayoutPane;
    private HBox generalLayoutHBox;

    // Layout components
    private AgendaCanvas agendaCanvas;
    private CreationPanel creationPanel;
    private TimeAndPopularityPanel timeAndPopularityPanel;
    private ArtistAndPodiumPanel artistAndPodiumPanel;

    private TextField showNameTextField;
    private TextField fileDirTextField;
    private Label errorLabel;

    private Button loadAgendaButton;
    private Button saveAgendaButton;
    private Button eventSaveButton;
    private Button eventRemoveButton;

    private File selectedAgendaFile;


    /**
     * Constructor of <code>AgendaModule</code>.
     * <p>
     * The given <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> will be stored
     * as a parameter so this stage can be refrenced as the main
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>.
     * </p>
     *
     * @param stage will be stored
     *              as a parameter so this stage can be refrenced as the owner of the sub stages
     *              <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>
     */
    public AgendaModule(Stage stage) {
        this.agenda = new Agenda();
        this.podiumManager = new PodiumManager();
        this.artistManager = new ArtistManager();
        this.currentShow = null;

        this.agendaCanvas = new AgendaCanvas(this.agenda);

        this.mainLayoutPane = new BorderPane();
        this.generalLayoutHBox = new HBox();

        this.creationPanel = new CreationPanel(this, this.podiumManager, this.artistManager);
        this.timeAndPopularityPanel = new TimeAndPopularityPanel();
        this.artistAndPodiumPanel = new ArtistAndPodiumPanel(new ComboBox<>(this.creationPanel.getObservablePodiumList()), new ComboBox<>(this.creationPanel.getObservableArtistList()), this.artistManager);

        this.podiumPopup = new PodiumPopup(stage, this.podiumManager, this.creationPanel);

        this.mainLayoutPane.setTop(this.generalLayoutHBox);
        this.mainLayoutPane.setCenter(this.agendaCanvas.getMainPane());

        this.showNameTextField = new TextField();
        this.fileDirTextField = new TextField();
        this.errorLabel = new Label("No error;");

        this.loadAgendaButton = new Button("Load Agenda");
        this.saveAgendaButton = new Button("Save Agenda");
        this.eventSaveButton = new Button("Save/Add Show");
        this.eventRemoveButton = new Button("Remove");

        
        this.artistManager.addArtist(new Artist("Peter Gabriel", null, null));
        this.artistManager.addArtist(new Artist("Frans Bauer", null, null));
        this.artistManager.addArtist(new Artist("The Police", null, null));
        this.artistManager.addArtist(new Artist("Elton John", null, null));
        this.artistManager.addArtist(new Artist("Fleetwood Mac", null, null));
        this.artistManager.addArtist(new Artist("Fools Garden", null, null));
        this.creationPanel.updateArtistComboBox();
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a>
     * for the <a href="{@docRoot}/FestivalPlanner/GUI/MainGUI.html">MainGUI</a> that contains all the GUI components
     * by calling all the generate methods.
     *
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> with the layout
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

        this.podiumPopup.generateScene();

        return new Scene(this.mainLayoutPane);
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for saving and removing events.
     *
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for saving and removing events
     */
    private VBox generateSaveAndRemovePanel() {
        VBox saveLoadPanel = new VBox();
        saveLoadPanel.setSpacing(5);
        saveLoadPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        saveLoadPanel.setPadding(new Insets(0, 2, 10, 2));
        saveLoadPanel.setMaxHeight(150);
        saveLoadPanel.setAlignment(Pos.BASELINE_CENTER);

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
        VBox savePanel = new VBox();
        savePanel.setSpacing(5);
        savePanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        savePanel.setPadding(new Insets(0, 2, 10, 2));
        savePanel.setMaxHeight(150);
        savePanel.setAlignment(Pos.BASELINE_CENTER);

        savePanel.getChildren().addAll(new Label("Enter name and save"),
                new Label("Enter show name:"),
                this.showNameTextField,
                new Label(""),
                this.eventSaveButton);
        return savePanel;
    }

    /**
     * CallBack method to open <code>this.artistPopup</code>
     */
    public void artistPopupCallBack() {
        //need to make the secondary GUI
    }

    /**
     * CallBack mehtod to open <code>this.podiumCallBack</code>
     */
    public void podiumPopupCallBack() {
        this.podiumPopup.show();
    }

    private void loadAgenda() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
        this.fileDirTextField.setText(fileChooser.showOpenDialog(new Stage()).getAbsolutePath());
    }

    private void saveAgenda() {
        FileChooser fileChooser = new FileChooser();
        SaveHandler saveHandler = new SaveHandler();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
        this.fileDirTextField.setText(fileChooser.showSaveDialog(new Stage()).getAbsolutePath());
        saveHandler.writeAgendaToFile(this.fileDirTextField.getText(), this.agenda);
    }

    /**
     * Initiates all the events that are used in the GUI.
     */
    private void initEvents() {

        this.fileDirTextField.setOnMouseClicked(e -> {
            saveAgenda();
        });

        this.loadAgendaButton.setOnAction(e -> {
            SaveHandler saveHandler = new SaveHandler();
            if (this.fileDirTextField.getText().equals("")) {
                loadAgenda();
            }
            this.agenda = saveHandler.readAgendaFromFile(this.fileDirTextField.getText());
            this.agendaCanvas.setAgenda(this.agenda);

            //update podiumManager and ArtistManager TODO werk niet helemaal
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
            Show selectedShow = this.agendaCanvas.showAtPoint(new Point2D.Double(e.getX(), e.getY()));
            if (selectedShow != null) {
                this.currentShow = selectedShow;
                this.agendaCanvas.rectangleOnShow(this.currentShow).setColor(java.awt.Color.getHSBColor(100/360f, .7f, .9f));
                this.agendaCanvas.reDrawCanvas();
                this.showNameTextField.setText(currentShow.getName());
                this.artistAndPodiumPanel.setArtistsList(new ListView<>(FXCollections.observableArrayList(this.currentShow.getArtists())));
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
        });

        this.eventRemoveButton.setOnAction(event -> {
            //TODO Need a way to select classes first
        });

        this.eventSaveButton.setOnAction(event -> {
            LocalTime startTime = this.timeAndPopularityPanel.getStartTime();
            LocalTime endTime = this.timeAndPopularityPanel.getEndTime();
            Podium selectedPodium = this.podiumManager.getPodium(this.artistAndPodiumPanel.getSelectedPodium());

            if (startTime != null && endTime != null && selectedPodium != null && this.artistAndPodiumPanel.getSelectedArtists().size() > 0) {

                this.agenda.getShows().remove(this.currentShow);
                agenda.addShow(new Show(this.showNameTextField.getText(),
                        startTime,
                        endTime,
                        this.timeAndPopularityPanel.getPopularity(),
                        selectedPodium,
                        this.artistAndPodiumPanel.getSelectedArtists()));

                this.agendaCanvas.reBuildAgendaCanvas();
            }
        });

    }
}
