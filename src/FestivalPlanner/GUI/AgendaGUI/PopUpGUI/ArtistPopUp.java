package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.ArtistManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Contains a PopUp used for creating a new <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a>.
 */
public class ArtistPopUp extends AbstractCreationPopUp{

    private ArtistManager artistManager;

    private HBox nameHBox = new HBox();

    private TextField nameField = new TextField();
    //TODO: Add sprite & picture functionality.

    /**
     * Constructor for the <code>ArtistPopUp</code> class.
     * @param primaryStage  stage calling the superclass
     * @param artistManager  artistManager to receive and send information to
     */
    public ArtistPopUp(Stage primaryStage, ArtistManager artistManager) {
        super(primaryStage);
        this.artistManager = artistManager;
    }

    @Override
    public void additionalLoad() {
        this.popupStage.setTitle("Artist Editor");
    }

    @Override
    public void additionalSetup() {
        //Initialise values.
        this.nameField.clear();

        //Alignment & Spacing.
        nameHBox.setAlignment(Pos.CENTER);

        //Adding all the children.
        nameHBox.getChildren().addAll(new Label("Name:     "), this.nameField);

        //Adding it all together.
        gridPane.addRow(0, nameHBox);
        gridPane.addRow(1, buttonHBox);
    }

    /**
     * Empty method; this class has no additional Action Handling.
     */
    @Override
    public void additionalActionHandlingSetup() {

    }

    @Override
    public void onAddButtonPress() {
        if (!this.nameField.getText().isEmpty()) {
            //Add the podium to the list and then update the ComboBox.
            //TODO: Replace null for actual values
            this.artistManager.addArtist(new Artist(this.nameField.getText(), null, null));

            //Exit stage.
            this.popupStage.close();
        } else {
            showEmptyTextFieldsPopUp();
        }
    }
}
