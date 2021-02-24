package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Agenda.Podium;
import FestivalPlanner.Agenda.PodiumManager;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Contains a PopUp used for creating a new <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a>.
 */
public class PodiumPopup extends AbstractCreationPopUp{

	//TODO: Rename this since I made a typo; PodiumPopup -> PodiumPopUp

	//LanguageHandling
	private ResourceBundle messages = LanguageHandler.getMessages();

	private PodiumManager podiumManager;
	private HBox nameHBox = new HBox();
	private HBox locationHBox = new HBox();
	private TextField nameField = new TextField();
	private TextField locationField = new TextField();

	/**
	 * Constructor for the <code>PodiumPopUp</code> class.
	 * @param primaryStage  the stage that will become the owner of this stage
	 * @param podiumManager  this class has a method that needs to be called after the list has been updated
	 */
	public PodiumPopup(Stage primaryStage, PodiumManager podiumManager) {
		super(primaryStage);
		this.podiumManager = podiumManager;
	}

	@Override
	public void additionalLoad() {
		this.popupStage.setTitle(messages.getString("podium_editor"));
	}

	@Override
	public void additionalSetup() {
		//Initialise values
		this.nameField.clear();
		this.locationField.clear();

		//Alignment & Spacing.
		nameHBox.setAlignment(Pos.CENTER);
		locationHBox.setAlignment(Pos.CENTER);

		//Adding all the children.
		nameHBox.getChildren().addAll(new Label(messages.getString("name") + ":     "), this.nameField);
		locationHBox.getChildren().addAll(new Label(messages.getString("location") + ": "), this.locationField);

		//Adding it all together.
		gridPane.addRow(0, nameHBox);
		gridPane.addRow(1, locationHBox);
		gridPane.addRow(2, buttonHBox);
	}

	/**
	 * Empty method; this class has no additional Action Handling.
	 */
	@Override
	public void additionalActionHandlingSetup() {

	}

	@Override
	public void onAddButtonPress() {
		if (!this.nameField.getText().isEmpty() && !this.locationField.getText().isEmpty()) {
			//Add the podium to the list and then update the ComboBox.
			this.podiumManager.addPodium(new Podium(this.nameField.getText(),this.locationField.getText()));

			//Exit stage.
			this.popupStage.close();
		} else {
			showEmptyTextFieldsPopUp();
		}
	}
}
