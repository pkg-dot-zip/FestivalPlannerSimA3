package FestivalPlanner.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AgendaModule {

	//private Rooster rooster = new Rooster();
	private ComboBox podiumComboBox;
	private ComboBox artistComboBox;

	private Label errorLabel;
	private Label artistsLabel;

	private Button podiumRemoveButton;
	private Button artistRemoveButton;
	private Button artistAddButton;
	private Button podiumAddButton;


	public AgendaModule() {
		//this.rooster = rooster
		this.podiumComboBox = new ComboBox<>();
		this.artistComboBox = new ComboBox<>();

		this.errorLabel = new Label("");
		this.errorLabel = new Label("No error;");

		this.podiumAddButton = new Button("+");
		this.artistAddButton = new Button("+");
		this.podiumRemoveButton = new Button("-");
		this.artistRemoveButton = new Button("-");
	}

	public Scene generateGUILayout() {
		GridPane gridPane = new GridPane();

		gridPane.setVgap(5);

		this.podiumRemoveButton.setMinWidth(30);
		this.artistRemoveButton.setMinWidth(30);


		gridPane.add(generateCreationPanel(),0,0);
		initAddAndRemoveButtons();

		return new Scene(gridPane);
	}

	public VBox generateCreationPanel() {
		VBox creationPanelVBox = new VBox();

		HBox artistHbox = new HBox();
		HBox podiumHbox = new HBox();

		artistHbox.setSpacing(5);
		podiumHbox.setSpacing(5);

		this.artistComboBox.setMinWidth(120);
		this.podiumComboBox.setMinWidth(120);

		artistHbox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton);
		podiumHbox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton);

		creationPanelVBox.getChildren().addAll(this.errorLabel, new Label("Existing podiums and artists: "),
				artistHbox, podiumHbox);

		return creationPanelVBox;
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
