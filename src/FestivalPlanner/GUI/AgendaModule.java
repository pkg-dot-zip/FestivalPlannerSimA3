package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class AgendaModule {

	private Agenda agenda;
	private ArtistManager artistManager;
	private PodiumManager podiumManager;

	private HBox generalLayoutHBox;

	private ComboBox<String> podiumComboBox;
	private ComboBox<String> podiumComboBoxCopy;
	private ComboBox<String> artistComboBox;
	private ComboBox<String> artistComboBoxCopy;

	private TextField startTimeTextField = new TextField("StartTime");
	private TextField endTimeTextField = new TextField("EndTime");

	private Slider popularitySlider;

	//TODO should be ListView<Artist>
	private ListView<Artist> artistsList;

	private ObservableList<String> observablePodiumList;
	private ObservableList<String> observableArtistList;

	private ArrayList<Artist> artistsFromCurrentShow;

	private Stage stage;

	private Label errorLabel;
	private Label popularityLabel;
	private Label selectedLabel;

	private Button podiumRemoveButton;
	private Button artistRemoveButton;
	private Button artistAddButton;
	private Button podiumAddButton;
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

		//this.rooster = rooster
		this.generalLayoutHBox = new HBox();

		//Copies like this are data inefficient but necessary for design

		this.observablePodiumList = FXCollections.observableArrayList();
		this.observableArtistList = FXCollections.observableArrayList();

		this.artistsFromCurrentShow = new ArrayList<>();

		this.podiumComboBox = new ComboBox<>(this.observablePodiumList);
		this.podiumComboBoxCopy = new ComboBox<>(this.observablePodiumList);
		this.artistComboBox = new ComboBox<>(this.observableArtistList);
		this.artistComboBoxCopy = new ComboBox<>(this.observableArtistList);

		this.errorLabel = new Label("No error;");
		this.popularityLabel = new Label(" Expected popularity: 50%");
		this.selectedLabel = new Label("Selected: None");

		this.popularitySlider = new Slider();

		this.artistsList = new ListView<>();


		this.podiumAddButton = new Button("+");
		this.artistAddButton = new Button("+");
		this.podiumRemoveButton = new Button("-");
		this.artistRemoveButton = new Button("-");
		this.eventArtistsAddButton = new Button("Add Artist");
		this.eventArtistsRemoveButton = new Button("Remove Artist");
		this.eventSaveButton = new Button("Save");
		this.eventRemoveButton = new Button("Remove");
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a>
	 * for the <a href="{@docRoot}/FestivalPlanner/GUI/MainGUI.html">MainGUI</a> that contains all the GUI components
	 * by calling all the generate methods.
	 * @return <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> with the layout
	 * for the <a href="{@docRoot}/FestivalPlanner.GUI/MainGUI.html">MainGUI</a> class
	 */

	public Scene generateGUILayout() {
		this.generalLayoutHBox.setSpacing(10);

		this.podiumRemoveButton.setMinWidth(30);
		this.artistRemoveButton.setMinWidth(30);

		this.generalLayoutHBox.getChildren().addAll(generateCreationPanel(), generateTimeAndPopularityPanel(),
				generateArtistsTable(), generateArtistAtEventSetter(), generatePodiumSelector(), generateSaveAndRemovePanel());

		initEvents();

		return new Scene(this.generalLayoutHBox);
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
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
	 * that contains the parts of the GUI responsible for selecting the time and popularity of an event.
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 * 	all the parts of the GUI responsible for selecting the time and popularity of an event
	 */

	private VBox generateTimeAndPopularityPanel() {
		VBox timeAndPopularityVBox = new VBox();

		timeAndPopularityVBox.setSpacing(5);

		this.popularitySlider.setMin(0);
		this.popularitySlider.setMax(100);
		this.popularitySlider.setValue(50);

		this.startTimeTextField.setMinWidth(220);
		this.endTimeTextField.setMinWidth(220);

		timeAndPopularityVBox.getChildren().addAll(new Label(""), this.startTimeTextField,
				this.endTimeTextField, this.popularityLabel, this.popularitySlider);

		return timeAndPopularityVBox;
	}

	/**
	 * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
	 * that contains the parts of the GUI responsible for creating and removing artists and podiums.
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 * all the parts of the GUI responsible for creating and removing artists and podiums
	 */

	private VBox generateCreationPanel() {
		VBox creationPanelVBox = new VBox();

		HBox artistHBox = new HBox();
		HBox podiumHBox = new HBox();

		creationPanelVBox.setSpacing(5);
		artistHBox.setSpacing(5);
		podiumHBox.setSpacing(5);

		this.artistComboBox.setMinWidth(120);
		this.artistComboBox.setMaxWidth(120);
		this.podiumComboBox.setMinWidth(120);
		this.podiumComboBox.setMaxWidth(120);

		artistHBox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton);
		podiumHBox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton);

		creationPanelVBox.getChildren().addAll(this.errorLabel,
				new Label("Existing artists: "), artistHBox, new Label("Existing podiums: "), podiumHBox);

		return creationPanelVBox;
	}

	/** This method is a placeholder, it wil become a method to show the artists that are preforming at the
	 * selected event in the artist list.
	 */

	//TODO Needs to be rewritten when the Rooster Class is done
	private void updateArtistsList() {
		this.artistsList.getItems().setAll(this.artistsFromCurrentShow);
	}

	protected void updatePodiumComboBox() {
		this.observablePodiumList.setAll(this.podiumManager.getAllPodiumNames());
	}

	protected void updateArtistComboBox() {
		this.observablePodiumList.setAll(this.artistManager.getAllArtistNames());
	}

	/**
	 * Initiates all the events that are used in the GUI.
	 */

	private void initEvents() {
		this.artistAddButton.setOnAction(event -> {
			//need to make the secondary GUI
		});

		this.podiumAddButton.setOnAction(event -> {
			PodiumPopup.show(this.stage, this.podiumManager, this);
		});

		this.artistRemoveButton.setOnAction(event -> {
			String selectedArtist = this.artistComboBox.getValue();
			this.artistManager.removeArtist(selectedArtist);
			updateArtistsList();
		});

		this.podiumRemoveButton.setOnAction(event -> {
			String selectedPodium = this.podiumComboBox.getValue();
			this.podiumManager.removePodium(selectedPodium);
			updatePodiumComboBox();
		});

		this.startTimeTextField.setOnMouseClicked(event -> {
			if (this.startTimeTextField.getText().equals("StartTime")) {
				this.startTimeTextField.clear();
			}
		});

		this.endTimeTextField.setOnMouseClicked(event -> {
			if (this.endTimeTextField.getText().equals("EndTime")) {
				this.endTimeTextField.clear();
			}
		});

		this.popularitySlider.setOnMouseDragged(event -> {
			this.popularityLabel.setText(" Expected popularity: " + (int)this.popularitySlider.getValue() + "%");
		});

		this.eventArtistsAddButton.setOnAction(event -> {
			//TODO Need the other classes to make this
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
			agenda.addShow(new Show("", LocalDateTime.parse(startTimeTextField.getText()),
					LocalDateTime.parse(endTimeTextField.getText()), (int)this.popularitySlider.getValue(),
					this.podiumManager.getPodium(this.podiumComboBoxCopy.getValue()),
					this.artistsFromCurrentShow));
		});

	}
}
