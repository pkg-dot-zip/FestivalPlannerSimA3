package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Abstract class containing the main methods and attributes all our GUI classes use.
 */
public abstract class AbstractGUI extends AbstractDialogPopUp {

    Locale aLocale = new Locale("en","US");
    Locale nlLocale = new Locale("nl","NL");
    ResourceBundle messages = ResourceBundle.getBundle("lang", nlLocale);

    public GridPane gridPane = new GridPane();
    public HBox buttonHBox = new HBox();
    public Button applyButton = new Button(messages.getString("apply"));
    public Button closeButton = new Button(messages.getString("close"));

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
