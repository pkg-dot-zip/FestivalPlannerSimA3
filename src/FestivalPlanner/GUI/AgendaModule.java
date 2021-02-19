package FestivalPlanner.GUI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AgendaModule {

	//private Rooster rooster = new Rooster();
	private HBox generalLayoutHBox;

	private ComboBox podiumComboBox;
	private ComboBox artistComboBox;

	private TextField startTimeTextField = new TextField("StartTime");
	private TextField endTimeTextField = new TextField("EndTime");

	private Slider popularitySlider;

	private Label errorLabel;
	private Label artistsLabel;
	private Label popularityLabel;

	private Button podiumRemoveButton;
	private Button artistRemoveButton;
	private Button artistAddButton;
	private Button podiumAddButton;


	public AgendaModule() {
		//this.rooster = rooster
		this.generalLayoutHBox = new HBox();

		this.podiumComboBox = new ComboBox<>();
		this.artistComboBox = new ComboBox<>();

		this.errorLabel = new Label("");
		this.errorLabel = new Label("No error;");
		this.popularityLabel = new Label("Existing podiums and artists: 50");

		this.popularitySlider = new Slider();

		this.podiumAddButton = new Button("+");
		this.artistAddButton = new Button("+");
		this.podiumRemoveButton = new Button("-");
		this.artistRemoveButton = new Button("-");
	}

	public Scene generateGUILayout() {
		this.generalLayoutHBox.setSpacing(10);

		this.podiumRemoveButton.setMinWidth(30);
		this.artistRemoveButton.setMinWidth(30);

		this.generalLayoutHBox.getChildren().addAll(generateCreationPanel(), generateTimeAndPopularityPanel());

		initEvents();

		return new Scene(this.generalLayoutHBox);
	}

	private VBox generateTimeAndPopularityPanel() {
		VBox timeAndPopluarityVBox = new VBox();

		timeAndPopluarityVBox.setSpacing(5);

		this.popularitySlider.setMin(0);
		this.popularitySlider.setMax(100);
		this.popularitySlider.setValue(50);

		this.startTimeTextField.setMinWidth(220);
		this.endTimeTextField.setMinWidth(220);

		timeAndPopluarityVBox.getChildren().addAll(this.startTimeTextField, this.endTimeTextField,
				this.popularityLabel, this.popularitySlider);


		return timeAndPopluarityVBox;
	}

	public VBox generateCreationPanel() {
		VBox creationPanelVBox = new VBox();

		HBox artistHbox = new HBox();
		HBox podiumHbox = new HBox();

		creationPanelVBox.setSpacing(5);
		artistHbox.setSpacing(5);
		podiumHbox.setSpacing(5);

		this.artistComboBox.setMinWidth(120);
		this.podiumComboBox.setMinWidth(120);

		artistHbox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton);
		podiumHbox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton);

		creationPanelVBox.getChildren().addAll(this.errorLabel, new Label(" Existing podiums and artists: "),
				artistHbox, podiumHbox);

		return creationPanelVBox;
	}


	public void initEvents() {
		this.artistAddButton.setOnAction(event -> {
			//need to make the secondary GUI
		});

		this.podiumAddButton.setOnAction(event -> {
			//need to make the secondary GUI
		});

		this.artistRemoveButton.setOnAction(event -> {
			this.artistComboBox.getItems().remove(this.artistComboBox.getValue());
		});

		this.podiumRemoveButton.setOnAction(event -> {
			this.podiumComboBox.getItems().remove(this.podiumComboBox.getValue());
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
			this.popularityLabel.setText(" Existing podiums and artists: " + (int)this.popularitySlider.getValue());
		});

	}
}
