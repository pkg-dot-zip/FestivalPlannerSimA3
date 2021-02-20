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

public class PodiumPopup {

	/**
	 * Shows a sub <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> where you can
	 * create a new podium. The primaryStage cannot be interacted with until this sub
	 * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> has been closed.
	 * @param primaryStage The stage that will become the owner of this stage.
	 */

	public static void show(Stage primaryStage, PodiumManager podiumManager, AgendaModule agendaModule) {

		Stage stage = new Stage();
		primaryStage.setResizable(false);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(primaryStage);

		stage.setScene(generateScene(podiumManager, stage, agendaModule));
		stage.setTitle("Podium creator");
		stage.show();
	}

	/**
	 *  generates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> that is the
	 * 	layout for the sub stage.
	 * @return <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html">Scene</a> that is the
	 * layout for the sub stage
	 */

	private static Scene generateScene(PodiumManager podiumManager, Stage stage, AgendaModule agendaModule) {
		VBox layoutVBox = new VBox();
		layoutVBox.setSpacing(10);

		HBox nameHBox = new HBox();
		TextField nameField = new TextField();
		nameHBox.getChildren().addAll(new Label("Name:     "), nameField);

		HBox locationHBox = new HBox();
		TextField locationField = new TextField();
		locationHBox.getChildren().addAll(new Label("Location: "), locationField);

		HBox buttonHBox = new HBox();
		buttonHBox.setSpacing(100);
		Button addButton = new Button("add");
		addButton.setMinWidth(50);
		buttonHBox.getChildren().addAll(new Label(""), addButton);

		layoutVBox.getChildren().addAll(nameHBox, locationHBox, buttonHBox);



		addButton.setOnAction(event -> {
			if (!nameField.getText().isEmpty() && !locationField.getText().isEmpty()) {
				podiumManager.addPodium(new Podium(nameField.getText(),locationField.getText()));
				agendaModule.updatePodiumComboBox();
				stage.close();
			}
		});

		return new Scene(layoutVBox,250,125);
	}
}
