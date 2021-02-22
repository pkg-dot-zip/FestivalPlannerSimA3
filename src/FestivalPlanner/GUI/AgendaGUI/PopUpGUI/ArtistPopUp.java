package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.ArtistManager;
import FestivalPlanner.GUI.AgendaGUI.CreationPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ArtistPopUp extends AbstractCreationPopUp{

    private ArtistManager artistManager;
    private CreationPanel creationPanel;

    private HBox nameHBox = new HBox();

    private TextField nameField = new TextField();
    //TODO: Add sprite & picture functionality.

    public ArtistPopUp(Stage primaryStage, ArtistManager artistManager, CreationPanel creationPanel) {
        super(primaryStage);
        this.artistManager = artistManager;
        this.creationPanel = creationPanel;
    }

    @Override
    public void additionalSetup() {
        //Initialise values
        //TODO: Make this work in case of editing a podium.
        this.nameField.clear();

        //Alignment & Spacing.
        nameHBox.setAlignment(Pos.CENTER);

        //Adding all the children.
        nameHBox.getChildren().addAll(new Label("Name:     "), this.nameField);

        //Adding it all together.
        gridPane.addRow(0, nameHBox);
        gridPane.addRow(1, buttonHBox);
    }

    @Override
    public void additionalActionHandlingSetup() {

    }

    @Override
    public void onAddButtonPress() {
        if (!this.nameField.getText().isEmpty()) {
            //Add the podium to the list and then update the ComboBox.
            //TODO: Replace null for actual values
            this.artistManager.addArtist(new Artist(this.nameField.getText(), null, null));
            this.creationPanel.updateArtistComboBox();

            //Exit stage.
            this.popupStage.close();
        } else {
            showEmptyTextFieldsPopUp();
        }
    }

    @Override
    public void additionalLoad() {
        this.popupStage.setTitle("Artist Editor");
    }
}
