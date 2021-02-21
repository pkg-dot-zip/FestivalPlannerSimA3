package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;

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

	private AgendaCanvas agendaCanvas;

	// Popups
	private PodiumPopup podiumPopup;

	// Layout components
	private BorderPane mainLayoutPane;
	private HBox generalLayoutHBox;

	private CreationPanel creationPanel;
	private TimeAndPopularityPanel timeAndPopularityPanel;

	private ComboBox<String> podiumComboBoxCopy;
	private ComboBox<String> artistComboBoxCopy;

	private ListView<Artist> artistsList;

	private ArrayList<Artist> artistsFromCurrentShow;

	private Stage stage;

	private Label errorLabel;
	private Label selectedLabel;

	private Button eventArtistsAddButton;
	private Button eventArtistsRemoveButton;
	private Button eventSaveButton;
	private Button eventRemoveButton;


	/**
	 * Constructor of <code>AgendaModule</code>.
	 * <p>
	 * The given <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> will be stored
	 * as a parameter so this stage can be refrenced as the main
	 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>.
	 * </p>
	 * @param stage will be stored
	 * as a parameter so this stage can be refrenced as the owner of the sub stages
	 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>
	 */

	public AgendaModule(Stage stage) {
		this.stage = stage;

		this.agenda = new Agenda();
		this.podiumManager = new PodiumManager();
		this.artistManager = new ArtistManager();

		this.agendaCanvas = new AgendaCanvas(this.agenda);

		this.mainLayoutPane = new BorderPane();
		this.generalLayoutHBox = new HBox();

		this.creationPanel = new CreationPanel(this, this.podiumManager, this.artistManager);
		this.timeAndPopularityPanel = new TimeAndPopularityPanel();

		this.podiumPopup = new PodiumPopup(this.stage,this.podiumManager,this.creationPanel);

		this.podiumComboBoxCopy = new ComboBox<>(this.creationPanel.getObservablePodiumList());
		this.artistComboBoxCopy = new ComboBox<>(this.creationPanel.getObservableArtistList());

		this.mainLayoutPane.setTop(this.generalLayoutHBox);
		this.mainLayoutPane.setCenter(this.agendaCanvas.getMainPane());

		this.artistsFromCurrentShow = new ArrayList<>();

		this.errorLabel = new Label("No error;");
		this.selectedLabel = new Label("Selected: None");

		this.artistsList = new ListView<>();

		this.eventArtistsAddButton = new Button("Add Artist");
		this.eventArtistsRemoveButton = new Button("Remove Artist");
		this.eventSaveButton = new Button("Save");
		this.eventRemoveButton = new Button("Remove");
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a>
	 * for the <a href="{@docRoot}/FestivalPlanner/GUI/MainGUI.html">MainGUI</a> that contains all the GUI components
	 * by calling all the generate methods.
	 * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> with the layout
	 * for the <a href="{@docRoot}/FestivalPlanner.GUI/MainGUI.html">MainGUI</a> class
	 */

	public Scene generateGUILayout() {
		this.generalLayoutHBox.setSpacing(20);
		this.generalLayoutHBox.setPadding(new Insets(0,10,0,10));

		this.generalLayoutHBox.getChildren().addAll(this.creationPanel.getMainPane(),
				this.timeAndPopularityPanel.getMainPane(),
				generateArtistsTable(),
				generateArtistAtEventSetter(),
				generatePodiumSelector(),
				generateSaveAndRemovePanel());

		initEvents();

		this.podiumPopup.generateScene();

		return new Scene(this.mainLayoutPane);
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
	 * that contains the parts of the GUI responsible for saving and removing events.
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 * the parts of the GUI responsible for saving and removing events
	 */

	private VBox generateSaveAndRemovePanel() {
		VBox saveAndRemovePanel = new VBox();

		saveAndRemovePanel.setSpacing(12);

		this.eventSaveButton.setMinWidth(74);

		saveAndRemovePanel.getChildren().addAll(new Label(""), this.selectedLabel,
				this.eventRemoveButton, this.eventSaveButton);

		return saveAndRemovePanel;
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
	 * that contains the parts of the GUI responsible for selecting a podium for an event.
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 *  the parts of the GUI responsible for selecting a podium for an event
	 */

	private VBox generatePodiumSelector() {
		VBox podiumVBox = new VBox();

		podiumVBox.setSpacing(5);

		this.podiumComboBoxCopy.setMinWidth(120);
		this.podiumComboBoxCopy.setMaxWidth(120);
		this.podiumComboBoxCopy.setPromptText("Podium:");

		podiumVBox.getChildren().addAll(new Label(""), this.podiumComboBoxCopy);

		return podiumVBox;
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
	 * that contains the parts of the GUI responsible for adding artists to an event.
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 * the parts of the GUI responsible for adding artists to an event
	 */

	private VBox generateArtistAtEventSetter() {
		VBox ArtistAtEventSetterVBox = new VBox();

		ArtistAtEventSetterVBox.setSpacing(5);
		this.artistComboBoxCopy.setMinWidth(120);
		this.artistComboBoxCopy.setMaxWidth(120);
		this.artistComboBoxCopy.setPromptText("Artist:");

		this.eventArtistsAddButton.setMinWidth(112);

		ArtistAtEventSetterVBox.getChildren().addAll(new Label(""),this.artistComboBoxCopy,
				this.eventArtistsAddButton, this.eventArtistsRemoveButton);

		return ArtistAtEventSetterVBox;
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
	 * that contains the parts of the GUI responsible for showing all of the artists in an event.
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 * the parts of the GUI responsible for showing all of the artists in an event
	 */

	private VBox generateArtistsTable() {
		VBox artistVBox = new VBox();

		artistVBox.setSpacing(5);

		this.artistsList.setMaxHeight(150);
		this.artistsList.setMaxWidth(200);

		updateArtistsList();

		artistVBox.getChildren().addAll(new Label("                  Artists:"), this.artistsList);
		return artistVBox;
	}

	/**
	 * This method updates the
	 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ListView.html">ListView</a>
	 * containing all artists form the current show.
	 */

	private void updateArtistsList() {
		this.artistsList.getItems().setAll(this.artistsFromCurrentShow);
	}


	public void artistPopupCallBack(){
		//need to make the secondary GUI
	}

	public void podiumPopupCallBack(){
		this.podiumPopup.show();
	}


	/**
	 * Initiates all the events that are used in the GUI.
	 */
	private void initEvents() {

		this.eventArtistsAddButton.setOnAction(event -> {
			this.artistsFromCurrentShow.add(this.artistManager.getArtist(this.artistComboBoxCopy.getValue()));
			updateArtistsList();
		});

		this.eventArtistsRemoveButton.setOnAction(event -> {
			Artist selectedArtist = this.artistManager.getArtist(artistComboBoxCopy.getValue());

			if (selectedArtist != null)
			{
				this.artistsFromCurrentShow.remove(selectedArtist);
			}
		});

		this.eventRemoveButton.setOnAction(event -> {
			//TODO Need a way to select classes first
		});

		this.eventSaveButton.setOnAction(event -> {
			LocalTime startTime = this.timeAndPopularityPanel.getStartTime();
			LocalTime endTime = this.timeAndPopularityPanel.getEndTime();
			Podium selectedPodium = this.podiumManager.getPodium(this.podiumComboBoxCopy.getValue());

			if (startTime != null && endTime != null && selectedPodium != null && this.artistsFromCurrentShow.size() > 0) {

				agenda.addShow(new Show("",
						startTime,
						endTime,
						this.timeAndPopularityPanel.getPopularity(),
						selectedPodium,
						this.artistsFromCurrentShow));
				this.agendaCanvas.reBuildAgendaCanvas();
			}
		});

	}
}
