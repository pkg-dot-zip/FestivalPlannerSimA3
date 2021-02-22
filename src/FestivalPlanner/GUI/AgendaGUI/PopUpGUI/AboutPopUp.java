package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import animatefx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class AboutPopUp extends AbstractCreationPopUp{

    private Text authorLabel = new Text("Projectgroep A3");
    private Text mottoLabel = new Text("Altijd een stap verder©");
    private Text versionLabel = new Text("0.0.1");

    private VBox aboutVBox = new VBox();

    /**
     * Constructor for <code>AbstractCreationPopUp</code>.
     * @param primaryStage <code>Stage</code> set as the <i>initial owner</i> of the class
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

    @Override
    public void additionalActionHandlingSetup() {

    }

    @Override
    public void onAddButtonPress() {

    }

    @Override
    public void additionalLoad() {
        buttonHBox.getChildren().remove(this.addButton); //Remove the apply button on load, so that we only have the close button.
        this.popupStage.setTitle("About");
    }
}
