package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Agenda.Podium;
import FestivalPlanner.Agenda.PodiumManager;
import FestivalPlanner.GUI.AgendaGUI.CreationPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Is responsible for a small popup that where a new <a href="{@docRoot}/FestivalPlanner/GUI/Podium.html">podium</a>
 * can be created.
 */
public class PodiumPopup extends AbstractCreationPopUp{

	//TODO: Rename this since I made a typo; PodiumPopup -> PodiumPopUp

	private PodiumManager podiumManager;
	private CreationPanel creationPanel;

	private HBox nameHBox = new HBox();
	private HBox locationHBox = new HBox();

	private TextField nameField = new TextField();
	private TextField locationField = new TextField();

	/**
	 *
	 * @param primaryStage  the stage that will become the owner of this stage
	 * @param podiumManager  this class has a method that needs to be called after the list has been updated
	 * @param creationPanel  the class that contains the list of podiums that will be updated
	 */
	public PodiumPopup(Stage primaryStage, PodiumManager podiumManager, CreationPanel creationPanel) {
		super(primaryStage);
		this.podiumManager = podiumManager;
		this.creationPanel = creationPanel;
	}

	@Override
	public void additionalSetup() {
		//Initialise values
		//TODO: Make this work in case of editing a podium.
		this.nameField.clear();
		this.locationField.clear();

		//Alignment & Spacing.
		nameHBox.setAlignment(Pos.CENTER);
		locationHBox.setAlignment(Pos.CENTER);

		//Adding all the children.
		nameHBox.getChildren().addAll(new Label("Name:     "), this.nameField);
		locationHBox.getChildren().addAll(new Label("Location: "), this.locationField);

		//Adding it all together.
		gridPane.addRow(0, nameHBox);
		gridPane.addRow(1, locationHBox);
		gridPane.addRow(2, buttonHBox);
	}

	@Override
	public void additionalActionHandlingSetup() {

	}

	@Override
	public void onAddButtonPress() {
		if (!this.nameField.getText().isEmpty() && !this.locationField.getText().isEmpty()) {
			//Add the podium to the list and then update the ComboBox.
			this.podiumManager.addPodium(new Podium(this.nameField.getText(),this.locationField.getText()));
			this.creationPanel.updatePodiumComboBox();

			//Exit stage.
			this.popupStage.close();
		} else {
			showEmptyTextFieldsPopUp();
		}
	}

	@Override
	public void additionalLoad() {
		this.popupStage.setTitle("Podium Editor");
	}
}