package FestivalPlanner.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PreferencesGUI {

    private final int STAGE_WIDTH = 275;
    private final int STAGE_HEIGHT = 200;

    private Stage primaryStage;
    private Stage stage = new Stage();
    private Scene scene;

    private GridPane gridPane = new GridPane();

    public PreferencesGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void load(){
        this.stage.setScene(scene);
        this.stage.setScene(new Scene(this.gridPane));
        this.stage.setWidth(STAGE_WIDTH);
        this.stage.setHeight(STAGE_HEIGHT);
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(this.primaryStage);
        this.stage.showAndWait();
    }

    public void setup(){

    }
}