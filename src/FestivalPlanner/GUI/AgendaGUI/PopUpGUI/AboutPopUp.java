package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import animatefx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Contains everything related to the AboutPopUp you get when clicking the About option in the
 * MenuBar of the <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/AgendaModule.html">AgendaModule</a>.
 * <p>
 * The PopUp contains 3 text <code>Node</code>s and a <code>Button</code>. The text elements show:
 * <p><ul>
 * <li> The software's author.
 * <li> The author's motto.
 * <li> The software's version.
 * </ul>
 */
public class AboutPopUp extends AbstractCreationPopUp {

    //LanguageHandling.
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Text.
    private Text authorText = new Text(messages.getString("author"));
    private Text mottoText = new Text(messages.getString("motto"));
    private Text versionText = new Text(messages.getString("version"));

    //Panes.
    private VBox aboutVBox = new VBox();

    //Animations.
    private ArrayList<AnimationFX> animationFXES = new ArrayList<>(3);

    /**
     * Constructor for <code>AbstractCreationPopUp</code>.
     * @param primaryStage  <code>Stage</code> set as the <i>initial owner</i> of the class
     */
    public AboutPopUp(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public void additionalSetup() {
        //Alignment & Spacing.
        this.aboutVBox.setSpacing(10);
        this.aboutVBox.setAlignment(Pos.CENTER);
        this.closeButton.setAlignment(Pos.CENTER);
        this.gridPane.autosize();

        //Adding all the children.
        this.aboutVBox.getChildren().addAll(authorText, mottoText, versionText);

        //Animations.
        if (SaveSettingsHandler.getPreference("use_animations").contains("true")) {
            new FadeIn(this.aboutVBox).play();
        }

        this.aboutVBox.getChildren().forEach(e -> {
            e.autosize();
            if (SaveSettingsHandler.getPreference("use_animations").contains("true")) {

                //Adding the animations to the list.
                this.animationFXES.addAll(Arrays.asList(
                        new Bounce(e).setSpeed(new Random().nextDouble() * 0.75),
                        new Flash(e).setSpeed(new Random().nextDouble() * 0.2),
                        new Jello(e).setSpeed(new Random().nextDouble() * 0.2)));

                //Setting the global settings to all animations.
                this.animationFXES.forEach(fx -> {
                    fx.setCycleCount(99).setResetOnFinished(true).setDelay(new Duration(3000 * (1.0 + new Random().nextDouble()))).play();
                });
            }
        });

        //Adding it all together.
        this.gridPane.addRow(0, this.aboutVBox);
        this.gridPane.addRow(1, this.buttonHBox);
    }

    /**
     * Empty method; this class has no additional Action Handling.
     */
    @Override
    public void additionalActionHandlingSetup() {
    }

    /**
     * Empty method; this class contains no addButton {@link #additionalLoad()}.
     */
    @Override
    public void onAddButtonPress() {
    }

    /**
     * Removes the <code>addButton</code> from the <code>buttonHBox</code>, and sets the stage's title.
     */
    @Override
    public void additionalLoad() {
        buttonHBox.getChildren().remove(this.addButton); //Remove the apply button on load, so that we only have the close button.
        this.popupStage.setTitle(messages.getString("about"));
    }
}