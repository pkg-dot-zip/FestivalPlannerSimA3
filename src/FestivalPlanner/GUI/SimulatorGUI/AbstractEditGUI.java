package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.CommonNodeRetriever;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public abstract class AbstractEditGUI extends AbstractGUI {

    SimulatorHandler handler;
    ResourceBundle messages = LanguageHandler.getMessages();
    Stage stage = new Stage();
    VBox mainPanel = CommonNodeRetriever.getEditGUIMainPanel();
    HBox bottomHBox = new HBox();

    private Button applyButton = new Button(messages.getString("apply"));
    private Button closeButton = new Button(messages.getString("close"));

    /**
     * Sets properties commonly shared between subclasses.
     */
    void genericSetup(){
        //Alignment & Spacing.
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);
        bottomHBox.setAlignment(Pos.CENTER);

        //Adding all the children.
        bottomHBox.getChildren().addAll(this.applyButton, this.closeButton);
    }

    /**
     * Assigns actions commonly shared between subclasses.
     */
    void genericActionHandlingSetup(){
        this.applyButton.setOnAction(e -> onApply());

        closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    /**
     * Runs the code whenever this applyButton is pressed with the mouseButton.
     */
    abstract void onApply();
}