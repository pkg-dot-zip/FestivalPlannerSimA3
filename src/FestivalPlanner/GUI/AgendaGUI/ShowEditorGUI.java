package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.ArtistManager;
import FestivalPlanner.Agenda.Show;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;

public class ShowEditorGUI extends AbstractDialogPopUp {

    //Main Scene Components.
    private Stage stage = new Stage();
    private Scene scene;

    //TimeAndPopularity
    private VBox timeAndPopularityVBox = new VBox();
    private Slider popularitySlider = new Slider();
    private Label popularityLabel = new Label(" Expected popularity: 50%");
    private TextField startTimeTextField = new TextField("StartTime");
    private TextField endTimeTextField = new TextField("EndTime");

    //ShowName
    private VBox showNameVBox = genericVBox();
    private TextField showNameTextField = new TextField();

    //ArtistsAndPodiumPanel
    private ComboBox<String> podiumComboBox = new ComboBox<>();
    private ComboBox<String> artistComboBox = new ComboBox<>();
    private Button eventArtistsAddButton = new Button("Add Artist");
    private Button eventArtistsRemoveButton = new Button("Remove Artist");
    private ListView<Artist> artistsList = new ListView<>();

    //Generic
    private GridPane gridPane = new GridPane();
        //Buttons
    private HBox buttonHBox = new HBox();
    private Button applyButton = new Button("Apply");
    private Button closeButton = new Button("Close");

        //Non-node attributes.
    private AgendaModule agendaModule;
    private Show selectedShow;
    private boolean isNewShow;

    public ShowEditorGUI(AgendaModule agendaModule){
        this.agendaModule = agendaModule;
    }

    public void load(){
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.scene = new Scene(gridPane);
        this.stage.setTitle("Show Editor");
        this.stage.setScene(scene);
        this.stage.setResizable(true);
        this.stage.setWidth(900);
        this.stage.setHeight(900);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    public void setup(){
        //Value init
        //If no layer is selected, create a new one.
        if (this.agendaModule.getCurrentShow() != null) {
            isNewShow = false;
        } else {
            isNewShow = true;
        }
        loadPropertiesFromShow();

        //Alignment & Spacing
            //TimeAndPopularity
                //Slider
        this.popularitySlider.setMin(0);
        this.popularitySlider.setMax(100);
        this.popularitySlider.setValue(50);
                //TextFields
        this.startTimeTextField.setMinWidth(220);
        this.endTimeTextField.setMinWidth(220);
                //VBox
        timeAndPopularityVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        timeAndPopularityVBox.setMaxHeight(150);
        timeAndPopularityVBox.setAlignment(Pos.BASELINE_CENTER);
        timeAndPopularityVBox.setSpacing(10);

            //GridPane
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        //Adding all the children
            //TimeAndPopularity
        timeAndPopularityVBox.getChildren().addAll(new Label("Select Time"), this.startTimeTextField,
                this.endTimeTextField, this.popularityLabel, this.popularitySlider);
            //ShowName
        showNameVBox.getChildren().addAll(new Label("Enter name and save"),
                new Label("Enter show name:"),
                this.showNameTextField);

            //Generic
        buttonHBox.getChildren().addAll(applyButton, closeButton);

        //Adding it all together
            //TimeAndPopularity
        gridPane.addRow(0, timeAndPopularityVBox);
        gridPane.addRow(1, showNameVBox);
        gridPane.addRow(2, generateMainPane());
        gridPane.addRow(3, buttonHBox);
    }

    public void actionHandlingSetup(){
        //TimeAndPopularity
        this.startTimeTextField.setOnMouseClicked(event -> {
            if (this.startTimeTextField.getText().equals("StartTime")) {
                this.startTimeTextField.setText("00:00");
            }
        });

        this.endTimeTextField.setOnMouseClicked(event -> {
            if (this.endTimeTextField.getText().equals("EndTime")) {
                this.endTimeTextField.setText("24:00");
            }
        });

        this.popularitySlider.setOnMouseDragged(event -> {
            this.popularityLabel.setText(" Expected popularity: " + (int)this.popularitySlider.getValue() + "%");
        });

        //ArtistsAndPodiumPanel
        this.eventArtistsAddButton.setOnAction(event -> {
            Artist selectedArtist = this.agendaModule.getArtistManager().getArtist(this.artistComboBox.getValue());
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

        /*
        * GENERIC :
        * */

        //
        applyButton.setOnAction(e -> {
            //TODO: If isNewShow, add selectedShow to the list.

            //TimeAndPopularityPanel
            selectedShow.setExpectedPopularity((int)this.popularitySlider.getValue());
            try {
                selectedShow.setStartTime(LocalTime.parse(this.startTimeTextField.getText()));
                selectedShow.setEndTime(LocalTime.parse(this.endTimeTextField.getText()));
            } catch (Exception ex){ //"e" Is already in use.
                showExceptionPopUp(ex);
            }

            //ShowName
            selectedShow.setName(this.showNameTextField.getText());

            //ArtistAndPodiumPane;
            ArrayList<Artist> test = new ArrayList<>();
            test.addAll(this.artistsList.getItems());
            selectedShow.setArtists(test);
            selectedShow.setPodium(this.agendaModule.getPodiumManager().getPodium(this.podiumComboBox.getSelectionModel().getSelectedItem()));

            //Apply to AgendaModule
            if (isNewShow){
                this.agendaModule.setCurrentShow(selectedShow);
            } else {
                this.agendaModule.setCurrentShow(selectedShow);
            }
        });

        closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    public void loadPropertiesFromShow(){
        if (isNewShow){
            selectedShow = new Show();
        } else {
            selectedShow = this.agendaModule.getCurrentShow();
        }

        //TimeAndPopularityPanel
        this.popularitySlider.setValue(selectedShow.getExpectedPopularity());
        this.startTimeTextField.setText(selectedShow.getStartTime().toString());
        this.endTimeTextField.setText(selectedShow.getEndTime().toString());
        //ShowName
        this.showNameTextField.setText(selectedShow.getName());
        //ArtistAndPodiumPanel
        this.artistsList.getItems().clear();
        this.artistsList.setItems(FXCollections.observableArrayList(selectedShow.getArtists()));
        this.artistComboBox.setItems(FXCollections.observableArrayList(this.agendaModule.getArtistManager().getAllArtistNames()));
        this.podiumComboBox.setItems(FXCollections.observableArrayList(this.agendaModule.getPodiumManager().getAllPodiumNames()));
        this.podiumComboBox.getSelectionModel().select(selectedShow.getPodium().getName());
    }

    //TODO: Embed all code under this line into other methods.
    public VBox genericVBox(){
        VBox vBoxToReturn = new VBox();
        vBoxToReturn.setSpacing(5);
        vBoxToReturn.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        vBoxToReturn.setPadding(new Insets(0, 2, 10, 2));
        vBoxToReturn.setMaxHeight(150);
        vBoxToReturn.setAlignment(Pos.BASELINE_CENTER);
        return vBoxToReturn;
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


}
