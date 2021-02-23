package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.ArtistManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ArtistAndPodiumPanel {

    private VBox mainPane;

    private ArtistManager artistManager;

    private ComboBox<String> podiumComboBox;
    private ComboBox<String> artistComboBox;

    private Button eventArtistsAddButton;
    private Button eventArtistsRemoveButton;

    private ListView<Artist> artistsList;

    /**
     * Constructor for ArtistAndPodiumPanel.
     * @param podiumComboBox  Combobox where the podiums are stored
     * @param artistComboBox  Combobox where the artists are stored
     * @param artistManager  The <a href="{@docRoot}/FestivalPlanner/Agenda/ArtistManager.html">ArtistManager</a> used in the GUI
     */
    public ArtistAndPodiumPanel(ComboBox<String> podiumComboBox, ComboBox<String> artistComboBox, ArtistManager artistManager) {
        this.podiumComboBox = podiumComboBox;
        this.artistComboBox = artistComboBox;

        this.artistManager = artistManager;

        this.eventArtistsAddButton = new Button("Add Artist");
        this.eventArtistsRemoveButton = new Button("Remove Artist");

        this.artistsList = new ListView<>();

        this.mainPane = generateMainPane();

        this.eventArtistsAddButton.setOnAction(event -> {
            Artist selectedArtist = this.artistManager.getArtist(this.artistComboBox.getValue());
            if (selectedArtist != null) {
                this.artistsList.getItems().add(selectedArtist);
                this.artistsList.refresh();
            }
        });

        this.eventArtistsRemoveButton.setOnAction(event -> {
            Artist selectedArtist = this.artistsList.getSelectionModel().getSelectedItem();
            if (selectedArtist != null) {
                this.artistsList.getItems().remove(selectedArtist);
                this.artistsList.refresh();
            }
        });
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for selecting a podium for an event.
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     *  the parts of the GUI responsible for selecting a podium for an event
     */
    private VBox generateMainPane() {
        VBox mainVBox = new VBox();
        mainVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        mainVBox.setMaxHeight(150);
        mainVBox.setPadding(new Insets(0,2,10,2));
        mainVBox.setAlignment(Pos.BASELINE_CENTER);
        mainVBox.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(generateArtistsTable(), generateArtistAtEventSetter());

        mainVBox.getChildren().addAll(new Label("Select Artists and podium"), hBox);
        return mainVBox;
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for adding artists to an event.
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for adding artists to an event
     */
    private VBox generateArtistAtEventSetter() {
        VBox ArtistAtEventSetterVBox = new VBox();

        ArtistAtEventSetterVBox.setSpacing(5);
        this.artistComboBox.setMinWidth(120);
        this.artistComboBox.setMaxWidth(120);
        this.artistComboBox.setPromptText("Artist:");

        this.podiumComboBox.setMinWidth(120);
        this.podiumComboBox.setMaxWidth(120);
        this.podiumComboBox.setPromptText("Podium:");

        this.eventArtistsAddButton.setPrefWidth(120);
        this.eventArtistsRemoveButton.setPrefWidth(120);

        ArtistAtEventSetterVBox.getChildren().addAll(this.artistComboBox,
                this.eventArtistsAddButton,
                this.eventArtistsRemoveButton,
                new Label("Select podium: "),
                this.podiumComboBox);

        return ArtistAtEventSetterVBox;
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for showing all of the artists in an event.
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for showing all of the artists in an event
     */
    private VBox generateArtistsTable() {
        VBox artistVBox = new VBox();

        artistVBox.setSpacing(5);

        this.artistsList.setMaxHeight(130);
        this.artistsList.setMaxWidth(200);

        artistVBox.getChildren().addAll(this.artistsList);
        return artistVBox;
    }

    /**
     * Getter for <code>this.mainPane</code>.
     * @return  <code>this.mainPane</code>
     */
    public VBox getMainPane() {
        return mainPane;
    }

    /**
     * Getter for the selected podium in <code>this.podiumComboBox</code>.
     * @return  Current value in <code>this.podiumComboBox</code>
     */
    public String getSelectedPodium(){
        return this.podiumComboBox.getValue();
    }

    /**
     * Getter for all the <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artists</a> selected.
     * @return  Returns an ArrayList with all the selected <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artists</a>
     */
    public ArrayList<Artist> getSelectedArtists() {
           return new ArrayList<>(this.artistsList.getItems());
    }

    public void setArtistsList(ArrayList<Artist> artistsList) {
        this.artistsList.getItems().clear();
        this.artistsList.setItems(FXCollections.observableArrayList(artistsList));
    }

    public void setSelectedPodium(String podium) {
        this.podiumComboBox.getSelectionModel().select(podium);
    }
}
