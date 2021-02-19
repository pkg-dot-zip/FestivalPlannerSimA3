package FestivalPlanner.GUI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AgendaModule {

	//private Rooster rooster = new Rooster();
	private HBox generalLayoutHBox;

	private ComboBox podiumComboBox;
	private ComboBox podiumComboBoxCopy;
	private ComboBox artistComboBox;
	private ComboBox artistComboBoxCopy;

	private TextField startTimeTextField = new TextField("StartTime");
	private TextField endTimeTextField = new TextField("EndTime");

	private Slider popularitySlider;

	//TODO should be ListView<Artist>
	private ListView<String> artistsList;

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


	public AgendaModule(Stage stage) {
		this.stage = stage;

		//this.rooster = rooster
		this.generalLayoutHBox = new HBox();

		//Copies like this are data inefficient but necessary for design
		this.podiumComboBox = new ComboBox<>();
		this.podiumComboBoxCopy = new ComboBox<>();
		this.artistComboBox = new ComboBox<>();
		this.artistComboBoxCopy = new ComboBox<>();

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

	public Scene generateGUILayout() {
		this.generalLayoutHBox.setSpacing(10);

		this.podiumRemoveButton.setMinWidth(30);
		this.artistRemoveButton.setMinWidth(30);

		this.generalLayoutHBox.getChildren().addAll(generateCreationPanel(), generateTimeAndPopularityPanel(),
				generateArtistsTable(), generateEventCreator(), generatePodiumSelector(), generateSaveAndRemovePanel());

		initEvents();

		return new Scene(this.generalLayoutHBox);
	}

	private VBox generateSaveAndRemovePanel() {
		VBox saveAndRemovePanel = new VBox();

		saveAndRemovePanel.setSpacing(12);

		this.eventSaveButton.setMinWidth(74);

		saveAndRemovePanel.getChildren().addAll(new Label(""), this.selectedLabel,
				this.eventRemoveButton, this.eventSaveButton);

		return saveAndRemovePanel;
	}

	private VBox generatePodiumSelector() {
		VBox podiumVBox = new VBox();

		podiumVBox.setSpacing(5);

		this.podiumComboBoxCopy.setMinWidth(120);
		this.podiumComboBoxCopy.setMaxWidth(120);

		podiumVBox.getChildren().addAll(new Label(""), this.podiumComboBoxCopy);

		return podiumVBox;
	}

	private VBox generateEventCreator() {
		VBox eventCreatorVBox = new VBox();

		eventCreatorVBox.setSpacing(5);
		this.artistComboBoxCopy.setMinWidth(120);
		this.artistComboBoxCopy.setMaxWidth(120);

		this.eventArtistsAddButton.setMinWidth(112);

		eventCreatorVBox.getChildren().addAll(new Label(""),this.artistComboBoxCopy,
				this.eventArtistsAddButton, this.eventArtistsRemoveButton);

		return eventCreatorVBox;
	}

	private VBox generateArtistsTable() {
		VBox artistVBox = new VBox();

		artistVBox.setSpacing(5);

		this.artistsList.setMaxHeight(150);
		this.artistsList.setMaxWidth(200);

		updateArtistsList();

		artistVBox.getChildren().addAll(new Label("                  Artists:"), this.artistsList);
		return artistVBox;
	}

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

	//TODO Needs to be rewritten when the Rooster Class is done
	private void updateArtistsList() {
		this.artistsList.getItems().clear();
		this.artistsList.getItems().add("");
	}

	public void initEvents() {
		this.artistAddButton.setOnAction(event -> {
			//need to make the secondary GUI
		});

		this.podiumAddButton.setOnAction(event -> PodiumPopup.show(this.stage));

		this.artistRemoveButton.setOnAction(event -> {
			Object selectedItem = this.artistComboBox.getValue();
			this.artistComboBox.getItems().remove(selectedItem);
			this.artistComboBoxCopy.getItems().remove(selectedItem);
		});

		this.podiumRemoveButton.setOnAction(event -> {
			Object selectedItem = this.podiumComboBox.getValue();
			this.podiumComboBox.getItems().remove(selectedItem);
			this.podiumComboBoxCopy.getItems().remove(selectedItem);
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
		});

		this.eventArtistsRemoveButton.setOnAction(event -> {
			this.artistsList.getItems().remove(this.artistComboBoxCopy.getValue());
		});

		this.eventRemoveButton.setOnAction(event -> {
			//TODO Need the other classes to make this
		});

		this.eventSaveButton.setOnAction(event -> {
			//TODO Need the other classes to make this
		});

	}
}
