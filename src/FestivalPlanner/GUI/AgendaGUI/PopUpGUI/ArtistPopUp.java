package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.ArtistManager;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private Image artistPicture;
    private Image artistSprite;
    private ImageView pictureView = new ImageView();
    private ImageView spriteView = new ImageView();

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
        this.popupStage.setWidth(300);
        this.popupStage.setHeight(300);
        this.popupStage.setTitle(messages.getString("artist_editor"));
    }

    @Override
    public void additionalSetup() {
        //Initialise values.
        this.nameField.clear();

        //make items for picturesHBox
        pictureView.setImage(artistPicture);
        pictureView.setFitHeight(50);
        pictureView.setFitWidth(50);
        spriteView.setImage(artistSprite);
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
            showExceptionPopUp(e);
        }
        return null;
    }

    /**
     * Sets artist picture to selected picture.
     */
    public void setArtistPicture() {
        try {
            File picture = new File(findArtistPicture());
            this.artistPicture = new Image(picture.toURL().toString());
            pictureView.setImage(artistPicture);
        } catch (Exception e) {
            showExceptionPopUp(e);
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
            showExceptionPopUp(e);
        }
        return null;
    }

    /**
     * Sets artist sprite to selected sprite.
     */
    public void setArtistSprite() {
        try {
            File picture = new File(findArtistSprite());
            this.artistSprite = new Image(picture.toURL().toString());
            spriteView.setImage(artistSprite);
        } catch (Exception e) {
            showExceptionPopUp(e);
        }
    }

    @Override
    public void onAddButtonPress() {
        if (!this.nameField.getText().isEmpty()) {
            //Add the podium to the list and then update the ComboBox.

            //convert artistPicture Image to BufferedImage
            BufferedImage bufferedPicture = new BufferedImage((int)artistPicture.getWidth(), (int)artistPicture.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Draw the image onto the buffered image
            Graphics2D bGr1 = bufferedPicture.createGraphics();
            bGr1.drawImage(bufferedPicture, 0, 0, null);
            bGr1.dispose();

            //convert artistSprite Image to BufferedImage
            BufferedImage bufferedSprite = new BufferedImage((int)artistSprite.getWidth(), (int)artistSprite.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Draw the image onto the buffered image
            Graphics2D bGr2 = bufferedSprite.createGraphics();
            bGr2.drawImage(bufferedSprite, 0, 0, null);
            bGr2.dispose();

            this.artistManager.addArtist(new Artist(this.nameField.getText(), bufferedPicture, bufferedSprite));

            //Exit stage.
            this.popupStage.close();
        } else {
            showEmptyTextFieldsPopUp();
        }
    }
}
