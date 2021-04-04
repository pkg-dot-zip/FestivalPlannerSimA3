package FestivalPlanner.GUI;

import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ResourceBundle;

/**
 * Contains the main methods and attributes all our GUI classes use.
 * <p>
 * This class contains final attributes used by GUI classes for spacing, to ensure consistency in our graphical user interface.
 * <p>
 * Furthermore, this class contains a buttonHBox, applyButton and closeButton, to avoid duplicate code in subclasses.
 */
public abstract class AbstractGUI {

    protected final int HBOX_SPACING = 5;
    protected final int VBOX_SPACING = 5;
    protected final int GRIDPANE_HGAP = 10;
    protected final int GRIDPANE_VGAP = 10;

    private ResourceBundle messages = LanguageHandler.getMessages();

    protected GridPane gridPane = new GridPane();
    protected HBox buttonHBox = new HBox();
    Button applyButton = new Button(messages.getString("apply"));
    protected Button closeButton = new Button(messages.getString("close"));

    /**
     * Runs initialisation methods and sets general stage settings, then runs the <code>show()</code> or <code>showAndWait()</code> method.
     * <p>
     * These include, but are not limited to:
     * <p><ul>
     * <li><code>setScene(Scene value)</code>
     * <li><code>setTitle(String value)</code>
     * <li><code>setWidth(Double value)</code>
     * <li><code>setResizable(boolean value)</code>
     * <li><code>initModality(Modality value)</code>
     * </ul>
     */
    public abstract void load();

    /**
     * Sets the values, alignment and spacing and children on initialisation.
     * Finally, the method setups the main layout, rarely anything else than a <code>GridPane</code>.
     * <p>
     * All the values that will be set here belong to one of the following:
     * <p><ul>
     * <li>Initialising Values (For example: Setting the items of a combobox to a list)
     * <li>Alignment and Spacing
     * <li>Tooltips
     * <li>Adding the children (Setting contents of HBox-s etc.)
     * <li>Adding it all together (Adding everything to a pane)
     * </ul>
     */
    public abstract void setup();

    /**
     * Sets EventHandling of <i>JavaFX</i> <code>Nodes</code>. Mostly <code>setOnAction</code>s for <code>Button</code>s.
     */
    public abstract void actionHandlingSetup();
}