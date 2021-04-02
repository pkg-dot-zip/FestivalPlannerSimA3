package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Logic.SimulatorObject;
import FestivalPlanner.NPC.NPC;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewObjectView extends AbstractGUI {

    private SimulatorHandler handler;
    private SimulatorCanvas canvas;
    private HashMap<String, NPC> artistNPCMap;

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Main Scene Components.
    private Stage stage = new Stage();

    //JavaFX Components
    private VBox mainPanel = new VBox();

    //Podium select
    private Label podiumSelectLabel = new Label(messages.getString("select_podium_view"));
    private ComboBox<SimulatorObject> podiumSelectBox = new ComboBox<>();
    private Button viewPodiumButton = new Button(messages.getString("view"));

    private Label artistSelectLabel = new Label(messages.getString("select_artist_view"));
    private ComboBox<String> artistSelectBox = new ComboBox<>();
    private Button viewArtistButton = new Button(messages.getString("view"));

    private Button closeButton = new Button(messages.getString("close"));


    public ViewObjectView(SimulatorHandler handler, SimulatorCanvas canvas){
        this.handler = handler;
        this.canvas = canvas;
    }

    @Override
    public void load() {
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.stage.setTitle(messages.getString("show_editor"));
        this.stage.setScene(new Scene(gridPane));
        this.stage.setResizable(true);
        this.stage.setWidth(350);
        this.stage.setHeight(250);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    @Override
    public void setup() {
        this.podiumSelectBox.setItems(FXCollections.observableArrayList(this.handler.getPodiums()));

        this.artistNPCMap = this.handler.getArtistNPCHashMap();
        this.artistSelectBox.setItems(FXCollections.observableArrayList(this.artistNPCMap.keySet()));


        HBox mainHBox = new HBox();

        //Set podium panel
        VBox podiumBox = new VBox();
        podiumBox.setMaxHeight(150);
        podiumBox.setMinWidth(150);
        podiumBox.setAlignment(Pos.BASELINE_CENTER);
        podiumBox.setSpacing(10);
        podiumBox.getChildren().addAll(this.podiumSelectLabel, this.podiumSelectBox, this.viewPodiumButton);

        //Set artist panel
        VBox artistBox = new VBox();
        artistBox.setMaxHeight(150);
        artistBox.setMinWidth(150);
        artistBox.setAlignment(Pos.BASELINE_CENTER);
        artistBox.setSpacing(10);
        artistBox.getChildren().addAll(this.artistSelectLabel, this.artistSelectBox, this.viewArtistButton);

        mainHBox.getChildren().addAll(podiumBox, new Separator(Orientation.VERTICAL), artistBox);

        //Main VBox setup
        mainPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        mainPanel.setMinHeight(150);
        mainPanel.setAlignment(Pos.BASELINE_CENTER);
        mainPanel.setSpacing(10);

        mainPanel.getChildren().addAll(mainHBox, this.closeButton);

        //GridPane
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        //Adding it all together
        gridPane.add(mainPanel, 0, 0);


    }

    @Override
    public void actionHandlingSetup() {

        this.viewPodiumButton.setOnAction(e -> this.canvas.moveToPoint(this.podiumSelectBox.getSelectionModel().getSelectedItem().getLocation()));
        this.viewArtistButton.setOnAction(e -> this.canvas.moveToPoint(this.artistNPCMap.get(this.artistSelectBox.getSelectionModel().getSelectedItem()).getPosition()));

        closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

}
