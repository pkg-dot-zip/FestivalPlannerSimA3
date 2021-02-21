package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Podium;
import FestivalPlanner.Agenda.PodiumManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Is responsible for a small popup that where a new <a href="{@docRoot}/FestivalPlanner/GUI/Podium.html">podium</a>
 * can be created.
 */

public class PodiumPopup {

	private Stage primaryStage;
	private PodiumManager podiumManager;
	private AgendaModule agendaModule;
	private Stage popupStage;

	private Button addButton;
	private TextField nameField;
	private TextField locationField;

	/**
	 *
	 * @param primaryStage the stage that will become the owner of this stage
	 * @param podiumManager this class has a method that needs to be called after the list has been updated
	 * @param agendaModule the class that contains the list of podiums that will be updated
	 */

	protected PodiumPopup(Stage primaryStage, PodiumManager podiumManager, AgendaModule agendaModule) {
		this.primaryStage = primaryStage;
		this.podiumManager = podiumManager;
		this.agendaModule = agendaModule;
		this.popupStage = new Stage();

		this.addButton = new Button("add");
	}

	/**
	 * Shows a sub <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> where you can
	 * create a new podium. The primaryStage cannot be interacted with until this sub
	 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> has been closed.
	 */

	protected void show() {

		this.popupStage.setTitle("Podium creator");
		this.popupStage.showAndWait();
	}

	/**
	 *  Generates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> that is the
	 * 	layout for the sub stage and sets it as the
	 * 	<a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a>.
	 */

	protected void generateScene() {
		this.popupStage.setResizable(false);
		this.popupStage.initModality(Modality.APPLICATION_MODAL);
		this.popupStage.initOwner(this.primaryStage);

		VBox layoutVBox = new VBox();
		layoutVBox.setSpacing(10);

		HBox nameHBox = new HBox();
		this.nameField = new TextField();
		nameHBox.getChildren().addAll(new Label("Name:     "), this.nameField);

		HBox locationHBox = new HBox();
		this.locationField = new TextField();
		locationHBox.getChildren().addAll(new Label("Location: "), locationField);

		generateButton();

		HBox buttonHBox = new HBox();
		this.addButton.setMinWidth(50);
		buttonHBox.getChildren().addAll(new Label(""), this.addButton);

		layoutVBox.getChildren().addAll(nameHBox, locationHBox, buttonHBox);

		this.popupStage.setScene(new Scene(layoutVBox,250,125));
	}

	/**
	 * Generates the button responsible for adding the <a href="{@docRoot}/FestivalPlanner/GUI/Podium.html">podium</a>.
	 */

	private void generateButton() {
		this.addButton.setMinWidth(50);

		this.addButton.setOnAction(event -> {
			if (!this.nameField.getText().isEmpty() && !this.locationField.getText().isEmpty()) {
				this.podiumManager.addPodium(new Podium(this.nameField.getText(),this.locationField.getText()));
				this.agendaModule.updatePodiumComboBox();

				this.nameField.clear();
				this.locationField.clear();

				this.popupStage.close();
			}
		});
	}
}
