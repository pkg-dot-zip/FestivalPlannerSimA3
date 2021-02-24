package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Abstract class containing the main methods and attributes all our GUI classes use.
 */
public abstract class AbstractGUI extends AbstractDialogPopUp {

    public GridPane gridPane = new GridPane();
    public HBox buttonHBox = new HBox();
    public Button applyButton = new Button("Apply");
    public Button closeButton = new Button("Close");

    /**
     * Runs initialisation methods and sets general stage settings.
     */
    public abstract void load();

    /**
     * Sets the values, alignment and spacing and children on initialisation.
     * Finally, the method setups the main layout, rarely anything else than a <code>GridPane</code>.
     */
    public abstract void setup();

    //TODO: Refactor to additional actionHandlingSetup() and make this method setup
    // onApplybutton() and setOnAction() for closeButton. (Currently present in AbstractCreationPopUp)
    /**
     * Sets EventHandling of JavaFX <code>Nodes</code>. Mostly <code>setOnAction(e -> {<i>code</i>})</code>s.
     */
    public abstract void actionHandlingSetup();

}
