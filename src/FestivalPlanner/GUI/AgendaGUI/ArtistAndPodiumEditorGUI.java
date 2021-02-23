package FestivalPlanner.GUI.AgendaGUI;

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

public class ArtistAndPodiumEditorGUI {

    Stage stage = new Stage();
    Scene scene;

    GridPane gridPane = new GridPane();
    AgendaModule agendaModule;

    private VBox creationPanelVBox = new VBox();
    private HBox artistHBox = new HBox();
    private HBox podiumHBox = new HBox();
    private ObservableList<String> observablePodiumList = FXCollections.observableArrayList();
    private ObservableList<String> observableArtistList = FXCollections.observableArrayList();
    private ComboBox<String> podiumComboBox = new ComboBox<>(this.observablePodiumList);
    private ComboBox<String> artistComboBox = new ComboBox<>(this.observableArtistList);
    private Label availablePodiumsAndArtistsLabel = new Label("Available Podiums & Artists");
    private Label existingArtistsLabel = new Label("Existing artists: ");
    private Label existingPodiumsLabel = new Label("Existing podiums: ");
    private Button podiumRemoveButton = new Button("-");
    private Button artistRemoveButton = new Button("-");
    private Button artistAddButton = new Button("+");
    private Button podiumAddButton = new Button("+");

    public ArtistAndPodiumEditorGUI(AgendaModule agendaModule) {
        this.agendaModule = agendaModule;
    }

    public void load(){
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.scene = new Scene(gridPane);
        this.stage.setTitle("Artists and Podiums Editor");
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setWidth(400);
        this.stage.setHeight(400);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    public void setup(){
        //Alignment & Spacing
        creationPanelVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        creationPanelVBox.setMaxHeight(150);
        creationPanelVBox.setAlignment(Pos.CENTER);
        creationPanelVBox.setSpacing(5);
        artistHBox.setSpacing(5);
        podiumHBox.setSpacing(5);
        this.podiumRemoveButton.setMinWidth(30);
        this.artistRemoveButton.setMinWidth(30);
        this.artistComboBox.setMinWidth(120);
        this.artistComboBox.setMaxWidth(120);
        this.podiumComboBox.setMinWidth(120);
        this.podiumComboBox.setMaxWidth(120);
        gridPane.setAlignment(Pos.CENTER);

        //Adding all the children.
        artistHBox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton);
        podiumHBox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton);
        creationPanelVBox.getChildren().addAll(availablePodiumsAndArtistsLabel,
                existingArtistsLabel,
                artistHBox, existingPodiumsLabel,
                podiumHBox);

        //Adding it all together.
        gridPane.addRow(0, creationPanelVBox);
    }

    public void actionHandlingSetup(){
        //CreationPanel
        this.artistAddButton.setOnAction(event -> {
            agendaModule.artistPopupCallBack();
        });

        this.podiumAddButton.setOnAction(event -> {
            agendaModule.podiumPopupCallBack();
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
    }

    /**
     * Updates <code>this.artistComboBoc</code> to the correct value.
     */
    public void updatePodiumComboBox() {
        this.observablePodiumList.setAll(this.agendaModule.getPodiumManager().getAllPodiumNames());
    }

    /**
     * Updates the <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JComboBox.html">ComboBox</a>
     * containing all the current Artists.
     */
    public void updateArtistComboBox() {
        this.observableArtistList.setAll(this.agendaModule.getArtistManager().getAllArtistNames());
    }
}
