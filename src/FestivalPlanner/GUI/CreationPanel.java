package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.ArtistManager;
import FestivalPlanner.Agenda.PodiumManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CreationPanel {

    private AgendaModule agendaModule;
    private VBox mainPane;

    private PodiumManager podiumManager;
    private ArtistManager artistManager;

    private ObservableList<String> observablePodiumList;
    private ObservableList<String> observableArtistList;

    private ComboBox<String> podiumComboBox;
    private ComboBox<String> artistComboBox;

    private Button podiumRemoveButton;
    private Button artistRemoveButton;
    private Button artistAddButton;
    private Button podiumAddButton;


    public CreationPanel(AgendaModule agendaModule, PodiumManager podiumManager, ArtistManager artistManager) {
        this.agendaModule = agendaModule;

        this.podiumManager = podiumManager;
        this.artistManager = artistManager;

        this.observablePodiumList = FXCollections.observableArrayList();
        this.observableArtistList = FXCollections.observableArrayList();

        this.podiumComboBox = new ComboBox<>(this.observablePodiumList);
        this.artistComboBox = new ComboBox<>(this.observableArtistList);

        this.podiumAddButton = new Button("+");
        this.artistAddButton = new Button("+");
        this.podiumRemoveButton = new Button("-");
        this.artistRemoveButton = new Button("-");

        this.mainPane = generateCreationPanel();

        this.artistAddButton.setOnAction(event -> {
            //need to make the secondary GUI
        });

        this.podiumAddButton.setOnAction(event -> {
            agendaModule.podiumPopupCallBack();
        });

        this.artistRemoveButton.setOnAction(event -> {
            String selectedArtist = this.artistComboBox.getValue();
            this.artistManager.removeArtist(selectedArtist);
            //updateArtistsList();
        });

        this.podiumRemoveButton.setOnAction(event -> {
            String selectedPodium = this.podiumComboBox.getValue();
            this.podiumManager.removePodium(selectedPodium);
            updatePodiumComboBox();
        });

    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for creating and removing artists and podiums.
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * all the parts of the GUI responsible for creating and removing artists and podiums
     */
    private VBox generateCreationPanel() {
        VBox creationPanelVBox = new VBox();
        creationPanelVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));

        HBox artistHBox = new HBox();
        HBox podiumHBox = new HBox();

        this.podiumRemoveButton.setMinWidth(30);
        this.artistRemoveButton.setMinWidth(30);

        creationPanelVBox.setSpacing(5);
        artistHBox.setSpacing(5);
        podiumHBox.setSpacing(5);

        this.artistComboBox.setMinWidth(120);
        this.artistComboBox.setMaxWidth(120);
        this.podiumComboBox.setMinWidth(120);
        this.podiumComboBox.setMaxWidth(120);


        artistHBox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton);
        podiumHBox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton);

        creationPanelVBox.getChildren().addAll(new Label("Available Podiums & Artists"),
                new Label("Existing artists: "),
                artistHBox, new Label("Existing podiums: "),
                podiumHBox);

        return creationPanelVBox;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public ObservableList<String> getObservablePodiumList() {
        return observablePodiumList;
    }

    public ObservableList<String> getObservableArtistList() {
        return observableArtistList;
    }

    public ComboBox<String> getArtistComboBox() {
        return artistComboBox;
    }

    public void updatePodiumComboBox() {
        this.observablePodiumList.setAll(this.podiumManager.getAllPodiumNames());
    }

    /**
     * Updates the <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JComboBox.html">ComboBox</a>
     * containing all the current Artists.
     */

    public void updateArtistComboBox() {
        this.observablePodiumList.setAll(this.artistManager.getAllArtistNames());
    }



}
