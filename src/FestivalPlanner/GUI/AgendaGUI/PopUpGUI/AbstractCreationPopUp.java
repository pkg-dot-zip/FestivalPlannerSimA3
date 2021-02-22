package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractCreationPopUp {

    //TODO: Fix method order to make functionality more clear.
    //TODO: Add documentation (currently the old one is still in place).

    final int STAGE_WIDTH = 275;
    final int STAGE_HEIGHT = 200;

    Stage primaryStage;
    Stage popupStage = new Stage();

    HBox buttonHBox = new HBox();
    Button addButton = new Button("Add");
    Button closeButton = new Button("Close");

    GridPane gridPane = new GridPane();

    /**
     *
     * @param primaryStage  the stage that will become the owner of this stage
     */
    public AbstractCreationPopUp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Shows a sub <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> where you can
     * create a new podium. The primaryStage cannot be interacted with until this sub
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> has been closed.
     */
    public void load() {
        //Setup methods.
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.additionalLoad();
        this.popupStage.setScene(new Scene(this.gridPane));
        this.popupStage.setWidth(STAGE_WIDTH);
        this.popupStage.setHeight(STAGE_HEIGHT);
        this.popupStage.setResizable(false);
        this.popupStage.initModality(Modality.APPLICATION_MODAL);
        this.popupStage.initOwner(this.primaryStage);
        this.popupStage.showAndWait();
    }

    public void setup(){
        additionalSetup();

        //Initialise values
        //Nothing here because this is an abstract class.

        //Alignment & Spacing.
            //Buttons
        this.addButton.setMinWidth(50);
            //HBox
        this.buttonHBox.setSpacing(5);
        this.buttonHBox.setAlignment(Pos.CENTER);
            //GridPane
        this.gridPane.setVgap(10);
        this.gridPane.setAlignment(Pos.CENTER);

        //Adding all the children.
        this.buttonHBox.getChildren().addAll(this.addButton, closeButton);

        //Adding it all together.
        //Nothing here because this is an abstract class.
    }

    public abstract void additionalSetup();

    public void actionHandlingSetup(){
        additionalActionHandlingSetup();

        this.addButton.setOnAction(e -> {
            onAddButtonPress();
        });

        this.closeButton.setOnAction(e -> {
            this.popupStage.close();
        });
    }

    public abstract void additionalActionHandlingSetup();

    public abstract void onAddButtonPress();

    public void showEmptyTextFieldsPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Empty Textfields");
        alert.setHeaderText("Missing values");
        alert.setContentText("Please fill in the required textfields.");
        alert.showAndWait();
    }

    public abstract void additionalLoad();
}
