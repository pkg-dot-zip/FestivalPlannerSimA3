package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.ArtistManager;
import FestivalPlanner.Agenda.PodiumManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CreationPanel {

    private AgendaModule agendaModule;
    private VBox mainPane;

    private VBox creationPanelVBox = new VBox();
    private HBox artistHBox = new HBox();
    private HBox podiumHBox = new HBox();

    private PodiumManager podiumManager;
    private ArtistManager artistManager;

    private ObservableList<String> observablePodiumList = FXCollections.observableArrayList();
    private ObservableList<String> observableArtistList = FXCollections.observableArrayList();

    private ComboBox<String> podiumComboBox = new ComboBox<>(this.observablePodiumList);
    private ComboBox<String> artistComboBox = new ComboBox<>(this.observableArtistList);

    private Button podiumRemoveButton = new Button("-");
    private Button artistRemoveButton = new Button("-");
    private Button artistAddButton = new Button("+");
    private Button podiumAddButton = new Button("+");


    public CreationPanel(AgendaModule agendaModule, PodiumManager podiumManager, ArtistManager artistManager) {
        this.agendaModule = agendaModule;
        this.podiumManager = podiumManager;
        this.artistManager = artistManager;
        this.mainPane = generateCreationPanel();
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for creating and removing artists and podiums.
     * @return  a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * all the parts of the GUI responsible for creating and removing artists and podiums
     */
    private VBox generateCreationPanel() {
        //Setup methods
        this.setup();
        this.actionHandlingSetup();

        return creationPanelVBox;
    }

    /**
     * Getter for <code>this.mainPane</code>.
     * @return <code>this.mainPane</code>
     */
    public VBox getMainPane() {
        return mainPane;
    }

    /**
     * Getter for <code>this.observablePodiumList</code>
     * @return  <code>this.observablePodiumList</code>
     */
    public ObservableList<String> getObservablePodiumList() {
        return observablePodiumList;
    }

    /**
     * Getter for <code>this.observableArtistList</code>
     * @return  <code>this.observableArtistList</code>
     */
    public ObservableList<String> getObservableArtistList() {
        return observableArtistList;
    }

    /**
     * Getter for <code>this.artistComboBox</code>
     * @return
     */
    public ComboBox<String> getArtistComboBox() {
        return artistComboBox;
    }

    /**
     * Updates <code>this.artistComboBoc</code> to the correct value.
     */
    public void updatePodiumComboBox() {
        this.observablePodiumList.setAll(this.podiumManager.getAllPodiumNames());
    }

    /**
     * Updates the <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/JComboBox.html">ComboBox</a>
     * containing all the current Artists.
     */
    public void updateArtistComboBox() {
        this.observableArtistList.setAll(this.artistManager.getAllArtistNames());
    }

    /**
     * Sets EventHandling of JavaFX <code>Nodes</code>.
     */
    public void actionHandlingSetup(){
        this.artistAddButton.setOnAction(event -> {
            agendaModule.artistPopupCallBack();
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
     * Sets alignment and spacing of JavaFX <code>Nodes</code>, and sets the children for panes.
     */
    public void setup(){
        creationPanelVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));

        //Alignment & Spacing.
        creationPanelVBox.setMaxHeight(150);
        creationPanelVBox.setAlignment(Pos.BASELINE_CENTER);
        creationPanelVBox.setSpacing(5);
        artistHBox.setSpacing(5);
        podiumHBox.setSpacing(5);
        this.podiumRemoveButton.setMinWidth(30);
        this.artistRemoveButton.setMinWidth(30);
        this.artistComboBox.setMinWidth(120);
        this.artistComboBox.setMaxWidth(120);
        this.podiumComboBox.setMinWidth(120);
        this.podiumComboBox.setMaxWidth(120);

        //Adding all the Children.
        artistHBox.getChildren().addAll(this.artistComboBox, this.artistAddButton, this.artistRemoveButton);
        podiumHBox.getChildren().addAll(this.podiumComboBox, this.podiumAddButton, this.podiumRemoveButton);

        //Adding everything together.
        creationPanelVBox.getChildren().addAll(new Label("Available Podiums & Artists"),
                new Label("Existing artists: "),
                artistHBox, new Label("Existing podiums: "),
                podiumHBox);
    }
}
