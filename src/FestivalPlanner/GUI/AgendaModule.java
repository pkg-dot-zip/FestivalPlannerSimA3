package FestivalPlanner.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AgendaModule {

	//private Rooster rooster = new Rooster();
	private ComboBox podiumComboBox;
	private ComboBox artistComboBox;

	private Label errorLabel;
	private Label existingPodiumAndArtistsLabel;
	private Label artistsLabel;

	private HBox artistHbox;
	private HBox podiumHbox;

	private Button podiumRemoveButton;
	private Button artistRemoveButton;
	private Button artistAddButton;
	private Button podiumAddButton;


	public AgendaModule() {
		//this.rooster = rooster
		this.podiumComboBox = new ComboBox<>();
		this.artistComboBox = new ComboBox<>();

		this.errorLabel = new Label("");
		this.existingPodiumAndArtistsLabel = new Label("Existing podiums and artists: ");
		this.errorLabel = new Label("Artists");

		this.artistHbox = new HBox();
		this.podiumHbox = new HBox();

		this.podiumAddButton = new Button("+");
		this.artistAddButton = new Button("+");
		this.podiumRemoveButton = new Button("-");
		this.artistRemoveButton = new Button("-");
	}

	public Scene generateGUILayout() {
		GridPane gridPane = new GridPane();

		gridPane.setVgap(5);
		this.artistHbox.setSpacing(5);
		this.podiumHbox.setSpacing(5);

		this.podiumRemoveButton.setMinWidth(30);
		this.artistRemoveButton.setMinWidth(30);

		initCombobox();
		initAddAndRemoveButtons();

		this.artistHbox.getChildren().addAll(this.artistComboBox,this.artistAddButton,this.artistRemoveButton);
		this.podiumHbox.getChildren().addAll(this.podiumComboBox,this.podiumAddButton,this.podiumRemoveButton);

		gridPane.add(this.errorLabel,0,0);
		gridPane.add(this.existingPodiumAndArtistsLabel,0,1);
		gridPane.add(this.artistHbox,0,2);
		gridPane.add(this.podiumHbox,0,3);


		return new Scene(gridPane);
	}

	public void initCombobox() {
		this.artistComboBox.setMinWidth(120);
		this.podiumComboBox.setMinWidth(120);
	}

	public void initAddAndRemoveButtons() {
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

	}
}
