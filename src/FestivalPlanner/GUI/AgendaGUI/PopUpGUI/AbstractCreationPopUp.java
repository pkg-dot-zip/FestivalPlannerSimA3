package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import animatefx.animation.FadeIn;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ResourceBundle;

/**
 * Abstract class used by creation window PopUps such as <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/PopUpGUI/ArtistPopUp.html">ArtistPopUp</a> and
 * <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/PopUpGUI/PodiumPopUp.html">PodiumPopUp</a>.
 * <p>
 *  This class contains methods with filled bodies, whom call abstract methods. This way extending this class is quite flexible
 *  to ensure an efficient and relatively fast workflow.
 *  Examples:
 *  <p><ul>
 *  <li><code>load()</code> calls abstract method <code>additionalLoad()</code>.
 *  <li><code>setup()</code> calls abstract method <code>additionalSetup()</code>.
 *  <li><code>actionHandlingSetup()</code> calls abstract method <code>additionalActionHandlingSetup()</code>.
 *  </ul>
 */
public abstract class AbstractCreationPopUp extends AbstractGUI {

    private ResourceBundle messages = LanguageHandler.getMessages();

    private final int STAGE_WIDTH = 275;
    private final int STAGE_HEIGHT = 200;

    private Stage primaryStage;
    Stage popupStage = new Stage();

    Button addButton = new Button(messages.getString("add"));

    /**
     * Constructor for <code>AbstractCreationPopUp</code>.
     * @param primaryStage  <code>Stage</code> set as the <i>initial owner</i> of the class
     */
    public AbstractCreationPopUp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Runs initialisation methods and sets general stage settings. Calls abstract method <code>additionalLoad()</code>.
     * @see #additionalLoad()
     */
    @Override
    public void load() {
        //Setup methods.
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.popupStage.setScene(new Scene(this.gridPane));
        this.popupStage.setWidth(STAGE_WIDTH);
        this.popupStage.setHeight(STAGE_HEIGHT);
        this.additionalLoad();
        this.popupStage.setResizable(false);
        this.popupStage.initModality(Modality.APPLICATION_MODAL);
        this.popupStage.initOwner(this.primaryStage);
        this.popupStage.showAndWait();
    }

    /**
     * Sets alignment and spacing of JavaFX <code>Nodes</code> and adds the buttons to this buttonsHBox.
     * @see #additionalSetup()
     */
    @Override
    public void setup(){
        additionalSetup();

        //Initialise values.
        //Nothing here because this is an abstract class.

        //Alignment & Spacing.
            //Buttons.
        this.addButton.setMinWidth(50);
            //HBox.
        this.buttonHBox.setSpacing(HBOX_SPACING);
        this.buttonHBox.setAlignment(Pos.CENTER);
            //GridPane.
        this.gridPane.setVgap(GRIDPANE_VGAP);
        this.gridPane.setAlignment(Pos.CENTER);

        //Adding all the children.
        this.buttonHBox.getChildren().addAll(this.addButton, closeButton);

        //Adding it all together.
        //Nothing here because this is an abstract class.
    }

    /**
     * Sets EventHandling of JavaFX <code>Nodes</code> used by all subclasses of this class (<code>AbstractCreationPopUp</code>),
     * and runs the abstract method <code>additionalActionHandlingSetup()</code>.
     * <p>
     * Sets two <code>setOnAction()</code> lambda expressions. Does this for:
     * <p><ul>
     * <li>addButton -&gt; <code>onAddButtonPress</code>, an abstract method in this class.</li>
     * <li>closeButton -&gt; <code>this.popStage.close();</code></li>
     * </ul>
     * @see #additionalActionHandlingSetup()
     */
    @Override
    public void actionHandlingSetup(){
        additionalActionHandlingSetup();

        this.popupStage.setOnShown(e -> {
            //Animation to play on launch.
            new FadeIn(gridPane).play();
        });

        this.addButton.setOnAction(e -> {
            onAddButtonPress();
        });

        this.closeButton.setOnAction(e -> {
            this.popupStage.close();
        });
    }

    /**
     * Abstract method used by subclasses to set additional Stage Settings. Automatically called in <code>load()</code>.
     * @see #load()
     */
    public abstract void additionalLoad();

    /**
     * Abstract method that runs additional setup code.
     * @see #setup()
     */
    public abstract void additionalSetup();

    /**
     * Abstract method that runs additional ActionHandlingSetup code.
     * @see #actionHandlingSetup()
     */
    public abstract void additionalActionHandlingSetup();

    /**
     * Abstract method that runs when this addButton is pressed. This is automatically handled by actionHandlingSetup().
     * @see #actionHandlingSetup()
     */
    public abstract void onAddButtonPress();
}
