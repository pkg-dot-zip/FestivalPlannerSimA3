package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ResourceBundle;

/**
 * Contains all elements and methods seen in used in the artist and podium creation/removal window.
 * <p>
 * //TODO: More detailed description.
 */
public class ArtistAndPodiumEditorGUI extends AbstractGUI {

    private ResourceBundle messages = LanguageHandler.getMessages();

    private Stage stage = new Stage();

    private AgendaModule agendaModule;

    private VBox creationPanelVBox = new VBox();
    private HBox artistHBox = new HBox();
    private HBox podiumHBox = new HBox();
    private ObservableList<String> observablePodiumList = FXCollections.observableArrayList();
    private ObservableList<String> observableArtistList = FXCollections.observableArrayList();
    private ComboBox<String> podiumComboBox = new ComboBox<>(this.observablePodiumList);
    private ComboBox<String> artistComboBox = new ComboBox<>(this.observableArtistList);
    private Label availablePodiumsAndArtistsLabel = new Label(messages.getString("available_podiums_and_artists"));
    private Label existingArtistsLabel = new Label(messages.getString("existing_artists") + ": ");
    private Label existingPodiumsLabel = new Label(messages.getString("existing_podiums") + ": ");
    private Button podiumRemoveButton = new Button("-");
    private Button artistRemoveButton = new Button("-");
    private Button artistAddButton = new Button("+");
    private Button podiumAddButton = new Button("+");
    private Button podiumEditButton = new Button("edit");
    private Button artistEditButton = new Button("edit");

    public ArtistAndPodiumEditorGUI(AgendaModule agendaModule) {
        this.agendaModule = agendaModule;
    }

    @Override
    public void load(){
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.stage.setTitle(messages.getString("artists_and_podiums_editor"));
        this.stage.setScene(new Scene(gridPane));
        this.stage.setResizable(false);
        this.stage.setWidth(250);
        this.stage.setHeight(250);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    @Override
    public void setup(){
        //Initialising Values.
        updateArtistComboBox();
        updatePodiumComboBox();
        if (!this.agendaModule.getArtistManager().getAllArtistNames().isEmpty() && !this.agendaModule.getPodiumManager().getAllPodiumNames().isEmpty()){
            this.artistComboBox.getSelectionModel().select(0);
            this.podiumComboBox.getSelectionModel().select(0);
        }

        //Alignment & Spacing.
        creationPanelVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        creationPanelVBox.setMaxHeight(150);
        creationPanelVBox.setAlignment(Pos.CENTER);
        creationPanelVBox.setSpacing(VBOX_SPACING);
        artistHBox.setSpacing(HBOX_SPACING);
        podiumHBox.setSpacing(HBOX_SPACING);
        this.podiumRemoveButton.setMinWidth(30);
        this.artistRemoveButton.setMinWidth(30);
        this.artistComboBox.setMinWidth(120);
        this.artistComboBox.setMaxWidth(120);
        this.podiumComboBox.setMinWidth(120);
        this.podiumComboBox.setMaxWidth(120);
        buttonHBox.setSpacing(HBOX_SPACING);
        buttonHBox.setAlignment(Pos.CENTER);
        gridPane.setHgap(GRIDPANE_HGAP);
        gridPane.setVgap(GRIDPANE_VGAP);
        gridPane.setAlignment(Pos.CENTER);

        //Adding all the children.
        artistHBox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton,
                this.artistEditButton);
        podiumHBox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton,
                this.podiumEditButton);
        creationPanelVBox.getChildren().addAll(availablePodiumsAndArtistsLabel,
                existingArtistsLabel,
                artistHBox, existingPodiumsLabel,
                podiumHBox);
        buttonHBox.getChildren().add(closeButton);

        //Adding it all together.
        gridPane.addRow(0, creationPanelVBox);
        gridPane.addRow(1, buttonHBox);
    }

    @Override
    public void actionHandlingSetup(){
        //CreationPanel
        this.artistAddButton.setOnAction(event -> {
            agendaModule.artistPopupCallBack();
            updateArtistComboBox();
            artistComboBox.getSelectionModel().selectLast();
        });

        this.podiumAddButton.setOnAction(event -> {
            agendaModule.podiumPopupCallBack();
            updatePodiumComboBox();
            podiumComboBox.getSelectionModel().selectLast();
        });

        this.artistRemoveButton.setOnAction(event -> {
            String selectedArtist = this.artistComboBox.getValue();
            this.agendaModule.getArtistManager().removeArtist(selectedArtist);
            updateArtistComboBox();
        });

        this.podiumRemoveButton.setOnAction(event -> {
            String selectedPodium = this.podiumComboBox.getValue();
            this.agendaModule.getPodiumManager().removePodium(selectedPodium);
            updatePodiumComboBox();
        });

        this.artistEditButton.setOnAction(event -> {
            String selectedArtist = this.artistComboBox.getValue();
            this.agendaModule.artistPopupEditCallBack(this.agendaModule.getArtistManager().getArtist(selectedArtist));
            updateArtistComboBox();
        });

        this.podiumEditButton.setOnAction(event -> {
            String selectedPodium = this.podiumComboBox.getValue();
            this.agendaModule.getPodiumManager().getPodium(selectedPodium);
            updatePodiumComboBox();
        });

        this.closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    /**
     * Updates <code>this.artistComboBoc</code> to the correct value.
     */
    private void updatePodiumComboBox() {
        this.observablePodiumList.setAll(this.agendaModule.getPodiumManager().getAllPodiumNames());
    }

    /**
     * Updates the <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JComboBox.html">ComboBox</a>
     * containing all the current Artists.
     */
    private void updateArtistComboBox() {
        this.observableArtistList.setAll(this.agendaModule.getArtistManager().getAllArtistNames());
        System.out.println("done");
    }
}
