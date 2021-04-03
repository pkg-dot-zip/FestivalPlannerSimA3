package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.Show;
import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.HelpMenu;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
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
import java.util.ResourceBundle;

/**
 * Contains all elements and methods seen and used in the edit window of the GUI.
 * <p>
 * //TODO: More detailed description.
 */
public class ShowEditorGUI extends AbstractGUI {

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Main Scene Components.
    private Stage stage = new Stage();

    //TimeAndPopularity
    private VBox timeAndPopularityVBox = HelpMenu.getEditGUIMainPanel();
    private Slider popularitySlider = new Slider();
    private Label popularityLabel = new Label(" " + messages.getString("expected_popularity") + " : 50%");
    private TextField startTimeTextField = new TextField();
    private TextField endTimeTextField = new TextField();

    //ShowName
    private VBox showNameVBox = genericVBox();
    private TextField showNameTextField = new TextField();

    //ArtistsAndPodiumPanel
    private VBox ArtistAtEventSetterVBox = new VBox();
    private VBox artistVBox = new VBox();
    private ComboBox<String> podiumComboBox = new ComboBox<>();
    private ComboBox<String> artistComboBox = new ComboBox<>();
    private Button eventArtistsAddButton = new Button(messages.getString("add_artist"));
    private Button eventArtistsRemoveButton = new Button(messages.getString("remove_artist"));
    private ListView<Artist> artistsList = new ListView<>();

    //Generic
        //Buttons
    private Button applyButton = new Button(messages.getString("apply"));

    //Non-node attributes.
    private AgendaModule agendaModule;
    private Show selectedShow;
    private boolean isNewShow;
    private ArrayList<Artist> selectedShowArtistArrayList = new ArrayList<>();
    private LocalTime attemptedStartTime;
    private LocalTime attemptedEndTime;

    public ShowEditorGUI(AgendaModule agendaModule) {
        this.agendaModule = agendaModule;
    }

    @Override
    public void load() {
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.stage.setTitle(messages.getString("show_editor"));
        this.stage.setScene(new Scene(gridPane));
        this.stage.setResizable(true);
        this.stage.setWidth(900);
        this.stage.setHeight(350);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    @Override
    public void setup() {
        //Value init
        //If no layer is selected, create a new one.
        isNewShow = this.agendaModule.getCurrentShow() == null;

        //Alignment & Spacing
            //TimeAndPopularity
                //Slider
        this.popularitySlider.setMin(0);
        this.popularitySlider.setMax(100);
        this.popularitySlider.setValue(50);
                //TextFields
        this.startTimeTextField.setMinWidth(220);
        this.endTimeTextField.setMinWidth(220);
                //ArtistAndPodiumPanel
        artistVBox.setSpacing(VBOX_SPACING);
        ArtistAtEventSetterVBox.setSpacing(VBOX_SPACING);
        this.artistsList.setMaxHeight(130);
        this.artistsList.setMaxWidth(200);
        this.artistComboBox.setMinWidth(120);
        this.artistComboBox.setMaxWidth(120);
        this.artistComboBox.setPromptText(messages.getString("artist") + ":");
        this.podiumComboBox.setMinWidth(120);
        this.podiumComboBox.setMaxWidth(120);
        this.podiumComboBox.setPromptText(messages.getString("podium") + ":");
        this.eventArtistsAddButton.setPrefWidth(120);
        this.eventArtistsRemoveButton.setPrefWidth(120);

           //Generic
                //ButtonHBox
        this.buttonHBox.setSpacing(HBOX_SPACING);
        this.buttonHBox.setAlignment(Pos.CENTER);
                //GridPane
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        //Adding all the children
            //TimeAndPopularity
        timeAndPopularityVBox.getChildren().addAll(new Label(messages.getString("select_time")), this.startTimeTextField,
                this.endTimeTextField, this.popularityLabel, this.popularitySlider);
            //ShowName
        showNameVBox.getChildren().addAll(new Label(messages.getString("enter_name_and_save")),
                new Label(messages.getString("enter_show_name") + ":"),
                this.showNameTextField);
            //ArtistAndPodiumPanel
        artistVBox.getChildren().addAll(this.artistsList);
        ArtistAtEventSetterVBox.getChildren().addAll(this.artistComboBox,
                this.eventArtistsAddButton,
                this.eventArtistsRemoveButton,
                new Label(messages.getString("select_podium") + ": "),
                this.podiumComboBox);

        loadPropertiesFromShow();

            //Generic
        buttonHBox.getChildren().addAll(applyButton, closeButton);

        //Adding it all together
        gridPane.add(timeAndPopularityVBox, 0, 0);
        gridPane.add(showNameVBox, 1, 0);
        gridPane.add(generateMainPane(), 2, 0);
        gridPane.add(buttonHBox, 1, 1);
    }

    @Override
    public void actionHandlingSetup(){
        //TimeAndPopularity
        this.popularitySlider.setOnMouseDragged(event -> {
            this.popularityLabel.setText(" " + messages.getString("expected_popularity") + ": " + (int) this.popularitySlider.getValue() + "%");
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
            if (isAllowedToApply() && !containsDuplicateArtist()) {
                //TimeAndPopularityPanel
                selectedShow.setExpectedPopularity((int) this.popularitySlider.getValue());

                selectedShow.setStartTime(this.attemptedStartTime);
                selectedShow.setEndTime(this.attemptedEndTime);

                //ShowName
                selectedShow.setName(this.showNameTextField.getText());

                //ArtistAndPodiumPane;
                selectedShow.setArtists(this.selectedShowArtistArrayList);
                selectedShow.setPodium(this.agendaModule.getPodiumManager().getPodium(this.podiumComboBox.getSelectionModel().getSelectedItem()));

                //Apply to AgendaModule
                if (isNewShow) {
                    this.agendaModule.setCurrentShow(selectedShow);
                } else {
                    this.agendaModule.setCurrentShow(selectedShow);
                }
            }
        });

        closeButton.setOnAction(e -> {
            this.agendaModule.setCurrentShow(null);
            this.stage.close();
        });
    }

    /**
     * Executes all code you would normally find in a <code>setup()</code> method under the
     * <i>//Initialising values.</i> comment.
     *
     * @see AbstractGUI#setup()
     */
    private void loadPropertiesFromShow() {
        if (isNewShow) {
            selectedShow = new Show();
        } else {
            selectedShow = this.agendaModule.getCurrentShow();
        }

        //TimeAndPopularityPanel
        this.popularitySlider.setValue(selectedShow.getExpectedPopularity());
        this.popularityLabel.setText(messages.getString("expected_popularity") + ": " + (int)this.popularitySlider.getValue() + "%");
        this.startTimeTextField.setText(selectedShow.getStartTime().toString());
        this.endTimeTextField.setText(selectedShow.getEndTime().toString());
        //ShowName
        this.showNameTextField.setText(selectedShow.getName());
        //ArtistAndPodiumPanel
        this.artistsList.getItems().clear();
        this.artistComboBox.setItems(FXCollections.observableArrayList(this.agendaModule.getArtistManager().getAllArtistNames()));
        this.podiumComboBox.setItems(FXCollections.observableArrayList(this.agendaModule.getPodiumManager().getAllPodiumNames()));
        //When getArtists() == null, the list in the GUI should not contain ANY names. This should
        //only occur when the show is new, but we check it like this to avoid this issue in all cases.
        if (selectedShow.getArtists() != null) {
            this.artistsList.setItems(FXCollections.observableArrayList(selectedShow.getArtists()));
        } else {
            this.artistsList.setItems(FXCollections.observableArrayList());
        }
        //Same applies here.
        if (selectedShow.getPodium() != null) {
            this.podiumComboBox.getSelectionModel().select(selectedShow.getPodium().getName());
        }
    }

    /**
     * Returns a boolean to check whether we set the current show's values to the ones in the GUI.
     * <p>
     * It checks for empty TextFields and list selections. If any of these checks return false this method
     * itself will return false and the configured values will <b>not</b> be applied.
     *
     * @return  boolean to check whether we set the current show's values to the ones in the GUI
     */
    private boolean isAllowedToApply() {
        if (
            //TimeAndPopularityPane
            startTimeTextField.getText().isEmpty() ||
            endTimeTextField.getText().isEmpty() ||

             //ShowName
            showNameTextField.getText().isEmpty() ||

            //ArtistAndPodiumPanel
            artistsList.getItems().isEmpty() ||
            podiumComboBox.getItems().isEmpty() ||
            podiumComboBox.getSelectionModel().isEmpty() ||
            podiumComboBox.getSelectionModel().getSelectedItem().isEmpty()
        ) {
            AbstractDialogPopUp.showEmptyTextFieldsPopUp();
            return false;
        } else {
            return true;
        }
    }

	/**
	 * Returns a boolean to check whether the selected artist(s) are already occupied.
	 * <p>
	 * It checks if the selected artists are available at the given time. If one of the artists is not available this
	 * method will return false, open an error pop up and the current show settings will <b>not</b> be applied.
	 *
	 * @return  boolean to check whether we set the current show's values to the ones in the GUI
	 */
    private boolean containsDuplicateArtist() {
        try {
            this.attemptedStartTime = LocalTime.parse(this.startTimeTextField.getText());
            this.attemptedEndTime = LocalTime.parse(this.endTimeTextField.getText());

            this.selectedShowArtistArrayList.clear();
            this.selectedShowArtistArrayList.addAll(this.artistsList.getItems());

            for (Show show : this.agendaModule.getAgenda().getShows()) {
                if (show.getStartTime().isBefore(this.attemptedEndTime) &&
                        show.getEndTime().isAfter(this.attemptedStartTime) &&
                        !show.equals(this.selectedShow)
                ) {
                    ArrayList<Artist> artistsFromShow = show.getArtists();
                    for (Artist artist : this.selectedShowArtistArrayList) {
                        if (artistsFromShow.contains(artist)) {
                            AbstractDialogPopUp.showDuplicateArtistPopUp();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
        return false;
    }

    //TODO: Embed all code under this line into other methods.
    private VBox genericVBox() {
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
     *
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for selecting a podium for an event
     */
    private VBox generateMainPane() {
        VBox mainVBox = new VBox();
        mainVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        mainVBox.setMaxHeight(150);
        mainVBox.setPadding(new Insets(0,2,10,2));
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(5);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(artistVBox, ArtistAtEventSetterVBox);
        hBox.setAlignment(Pos.CENTER);

        mainVBox.getChildren().addAll(new Label(messages.getString("select_artists_and_podium")), hBox);
        return mainVBox;
    }
}
