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

/**
 * Contains attributes and methods commonly shared between subclasses. Its purpose is to avoid duplicate code.
 */
public abstract class AbstractEditGUI extends AbstractGUI {

    //LanguageHandling.
    ResourceBundle messages = LanguageHandler.getMessages();

    Stage stage = new Stage();
    SimulatorHandler handler;

    //Panes.
    VBox mainPanel = CommonNodeRetriever.getEditGUIMainPanel();
    HBox bottomHBox = new HBox();

    //Buttons.
    private Button applyButton = new Button(messages.getString("apply"));
    private Button closeButton = new Button(messages.getString("close"));

    /**
     * Sets properties commonly shared between subclasses.
     */
    void genericSetup(){
        //Alignment & Spacing.
        this.gridPane.setVgap(50);
        this.gridPane.setHgap(50);
        this.gridPane.setAlignment(Pos.CENTER);
        this.bottomHBox.setAlignment(Pos.CENTER);

        //Adding all the children.
        this.bottomHBox.getChildren().addAll(this.applyButton, this.closeButton);
    }

    /**
     * Assigns actions commonly shared between subclasses.
     */
    void genericActionHandlingSetup(){
        this.applyButton.setOnAction(e -> onApply());

        this.closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    /**
     * Runs the code whenever this applyButton is pressed with the mouseButton.
     */
    abstract void onApply();
}