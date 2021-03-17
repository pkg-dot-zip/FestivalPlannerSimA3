package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.ArtistManager;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Contains a PopUp used for creating a new <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a>.
 */
public class ArtistPopUp extends AbstractCreationPopUp{

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    private ArtistManager artistManager;
    private HBox nameHBox = new HBox();
    private HBox picturesHBox = new HBox();
    private HBox spritesHBox = new HBox();
    private TextField nameField = new TextField();
    private Button pictureButton = new Button("Picture");
    private Button spriteButton = new Button("Sprite");

    private BufferedImage artistPicture;
    private BufferedImage artistSprite;
    private ImageView pictureView = new ImageView();
    private ImageView spriteView = new ImageView();

    private Artist selectedArtist;

    /**
     * Constructor for the <code>ArtistPopUp</code> class.
     * @param primaryStage  stage calling the superclass
     * @param artistManager  artistManager to receive and send information to
     */
    public ArtistPopUp(Stage primaryStage, ArtistManager artistManager) {
        super(primaryStage);
        this.artistManager = artistManager;
        this.selectedArtist = null;
    }

    public ArtistPopUp(Stage primaryStage, ArtistManager artistManager, Artist selectedArtist) {
        super(primaryStage);
        this.artistManager = artistManager;
        this.selectedArtist = selectedArtist;
    }

    @Override
    public void additionalLoad() {
        this.popupStage.setWidth(300);
        this.popupStage.setHeight(300);
        this.popupStage.setTitle(messages.getString("artist_editor"));

        if (this.selectedArtist != null) {
            this.nameField.setText(this.selectedArtist.getName());
            this.artistPicture = this.selectedArtist.getPicture();
            pictureView.setImage(SwingFXUtils.toFXImage(this.artistPicture, null));
            this.artistSprite = this.selectedArtist.getSprite();
            spriteView.setImage(SwingFXUtils.toFXImage(this.artistSprite, null));
            this.addButton.setText(messages.getString("edit_button"));
        }
    }

    @Override
    public void additionalSetup() {
        //Initialise values.
        this.nameField.clear();

        //make items for picturesHBox
        pictureView.setImage(null);
        pictureView.setFitHeight(50);
        pictureView.setFitWidth(50);
        spriteView.setImage(null);
        spriteView.setFitHeight(50);
        spriteView.setFitWidth(50);


        //Alignment & Spacing.
        nameHBox.setAlignment(Pos.CENTER);
        picturesHBox.setAlignment(Pos.BASELINE_LEFT);
        spritesHBox.setAlignment(Pos.BASELINE_LEFT);

        //Adding all the children.
        nameHBox.getChildren().addAll(new Label(messages.getString("name") + ":     "), this.nameField);
        picturesHBox.getChildren().addAll(pictureButton, new Label("     "), pictureView);
        spritesHBox.getChildren().addAll(spriteButton, new Label("     "), spriteView);

        //Adding it all together.
        gridPane.addRow(0, nameHBox);
        gridPane.addRow(1, picturesHBox);
        gridPane.addRow(2, spritesHBox);
        gridPane.addRow(3, buttonHBox);
    }

    /**
     * picture button activates the artist picture setter
     * sprite button activates the artist picture setter
     */
    @Override
    public void additionalActionHandlingSetup() {
        pictureButton.setOnAction(event -> {
            setArtistPicture();
        });

        spriteButton.setOnAction(event -> {
            setArtistSprite();
        });
    }

    /**
     * Opens fileChooser dialog to select Artist Photo
     * @return  absolute path to photo of artist
     */
    private String findArtistPicture() {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Artist Photo (.png)", "*.png"));
            return fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        } catch (NullPointerException e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
        return null;
    }

    /**
     * Sets artist picture to selected picture.
     */
    public void setArtistPicture() {
        try {
            File pictureURL = new File(findArtistPicture());
            this.artistPicture = ImageIO.read(pictureURL);
            pictureView.setImage(SwingFXUtils.toFXImage(this.artistPicture, null));
        } catch (Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }

    /**
     * Opens fileChooser dialog to select Artist Sprite
     * @return  absolute path to sprite of artist
     */
    private String findArtistSprite() {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Artist Sprite (.png)", "*.png"));
            return fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        } catch (NullPointerException e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
        return null;
    }

    /**
     * Sets artist sprite to selected sprite.
     */
    public void setArtistSprite() {
        try {
            File pictureURL = new File(findArtistSprite());
            this.artistSprite = ImageIO.read(pictureURL);
            spriteView.setImage(SwingFXUtils.toFXImage(this.artistSprite, null));
        } catch (Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }

    @Override
    public void onAddButtonPress() {
        if (!this.nameField.getText().isEmpty()) {

            if (this.selectedArtist != null) {
                this.artistManager.editArtist(this.selectedArtist.getName(),
                        new Artist(this.nameField.getText(), this.artistPicture, this.artistSprite));

            } else {
                this.artistManager.addArtist(new Artist(this.nameField.getText(), this.artistPicture, this.artistSprite));
            }

            //Exit stage.
            this.popupStage.close();
        } else {
            AbstractDialogPopUp.showEmptyTextFieldsPopUp();
        }
    }
}
