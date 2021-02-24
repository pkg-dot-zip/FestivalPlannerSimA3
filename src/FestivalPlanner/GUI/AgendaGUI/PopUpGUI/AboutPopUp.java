package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import animatefx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

/**
 * Contains everything related to the AboutPopUp you get when clicking the About option in the
 * MenuBar of the <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/AgendaModule.html">AgendaModule</a>.
 * <p>
 * The PopUp contains 3 text <code>Node</code>s and a button. The text elements show:
 * <p><ul>
 * <li> The software's author.
 * <li> The author's motto.
 * <li> The software's version.
 * <p></ul>
 * The button closes this window.
 */
public class AboutPopUp extends AbstractCreationPopUp {

    private Text authorLabel = new Text("Projectgroep A3");
    private Text mottoLabel = new Text("Altijd een stap verder©");
    private Text versionLabel = new Text("0.0.1");
    private VBox aboutVBox = new VBox();

    /**
     * Constructor for <code>AbstractCreationPopUp</code>.
     * @param primaryStage  <code>Stage</code> set as the <i>initial owner</i> of the class
     */
    public AboutPopUp(Stage primaryStage) {
        super(primaryStage);
    }


    @Override
    public void additionalSetup() {
        //Alignment & Spacing
        aboutVBox.setSpacing(10);
        aboutVBox.setAlignment(Pos.CENTER);
        this.closeButton.setAlignment(Pos.CENTER);
        this.gridPane.autosize();

        //Adding all the children
        aboutVBox.getChildren().addAll(authorLabel, mottoLabel, versionLabel);

        //Animations
        new FadeIn(aboutVBox).play();

        aboutVBox.getChildren().forEach(e -> {
            e.autosize();
            //TODO: Write this shorter.
            new Bounce(e).setCycleCount(99).setResetOnFinished(true).setSpeed(new Random().nextDouble() * 0.75).setDelay(new Duration(3000 * (1.0 + new Random().nextDouble()))).play();
            new Flash(e).setCycleCount(99).setResetOnFinished(true).setSpeed(new Random().nextDouble() * 0.2).setDelay(new Duration(3000 * (1.0 + new Random().nextDouble()))).play();
            new Jello(e).setCycleCount(99).setResetOnFinished(true).setSpeed(new Random().nextDouble() * 0.2).setDelay(new Duration(3000 * (1.0 + new Random().nextDouble()))).play();
        });

        //Adding it all together
        this.gridPane.addRow(0, aboutVBox);
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
     * Removes the addButton from the buttonHBox, and sets the stage's title.
     */
    @Override
    public void additionalLoad() {
        buttonHBox.getChildren().remove(this.addButton); //Remove the apply button on load, so that we only have the close button.
        this.popupStage.setTitle("About");
    }
}
