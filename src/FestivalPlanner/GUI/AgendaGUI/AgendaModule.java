package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
	private Label errorLabel;
	private Label selectedLabel;

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
		Stage stage1 = stage;

		this.agenda = new Agenda();
		this.podiumManager = new PodiumManager();
		this.artistManager = new ArtistManager();

		this.agendaCanvas = new AgendaCanvas(this.agenda);

		this.mainLayoutPane = new BorderPane();
		this.generalLayoutHBox = new HBox();

		this.creationPanel = new CreationPanel(this, this.podiumManager, this.artistManager);
		this.timeAndPopularityPanel = new TimeAndPopularityPanel();
		this.artistAndPodiumPanel = new ArtistAndPodiumPanel(new ComboBox<>(this.creationPanel.getObservablePodiumList()), new ComboBox<>(this.creationPanel.getObservableArtistList()), this.artistManager);

		this.podiumPopup = new PodiumPopup(stage1,this.podiumManager,this.creationPanel);

		this.mainLayoutPane.setTop(this.generalLayoutHBox);
		this.mainLayoutPane.setCenter(this.agendaCanvas.getMainPane());

		this.showNameTextField = new TextField();
		this.errorLabel = new Label("No error;");
		this.selectedLabel = new Label("Selected: None");

		this.eventSaveButton = new Button("Save/Add Show");
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
	 * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
	 * the parts of the GUI responsible for saving and removing events
	 */
	private VBox generateSaveAndRemovePanel() {
		VBox saveAndRemovePanel = new VBox();

		saveAndRemovePanel.setSpacing(12);

		this.eventSaveButton.setMinWidth(74);

		saveAndRemovePanel.getChildren().addAll(new Label(""), this.selectedLabel,
				this.eventRemoveButton);

		return saveAndRemovePanel;
	}

	private VBox generateSavePanel() {
		VBox savePanel = new VBox();
		savePanel.setSpacing(5);
		savePanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
		savePanel.setPadding(new Insets(0,2,10,2));
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
	public void artistPopupCallBack(){
		//need to make the secondary GUI
	}

	/**
	 * CallBack mehtod to open <code>this.podiumCallBack</code>
	 */
	public void podiumPopupCallBack(){
		this.podiumPopup.show();
	}


	/**
	 * Initiates all the events that are used in the GUI.
	 */
	private void initEvents() {

		this.eventRemoveButton.setOnAction(event -> {
			//TODO Need a way to select classes first
		});

		this.eventSaveButton.setOnAction(event -> {
			LocalTime startTime = this.timeAndPopularityPanel.getStartTime();
			LocalTime endTime = this.timeAndPopularityPanel.getEndTime();
			Podium selectedPodium = this.podiumManager.getPodium(this.artistAndPodiumPanel.getSelectedPodium());

			if (startTime != null && endTime != null && selectedPodium != null && this.artistAndPodiumPanel.getSelectedArtists().size() > 0) {

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
