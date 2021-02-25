package FestivalPlanner.GUI;

import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Contains methods to either directly or indirectly call
 * <a href="{@docRoot}/FestivalPlanner/Util/PreferencesHandling">PreferencesHandling</a> to store
 * user preferences.
 */
public class PreferencesGUI extends AbstractGUI{

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    private final int STAGE_WIDTH = 360;
    private final int STAGE_HEIGHT = 240;

    private Stage primaryStage;
    private Stage stage = new Stage();

    private VBox generalSettingsVBox = new VBox();

    //Languages
    private HBox languagesHBox = new HBox();
    private Label languagesLabel = new Label(messages.getString("select_language"));
    private ComboBox<Locale> languagesComboBox = new ComboBox<>();

    //Animations
    private HBox useAnimationsHBox = new HBox();
    private Label useAnimationsLabel = new Label(messages.getString("use_animations"));
    private CheckBox useAnimationsCheckbox = new CheckBox();

    public PreferencesGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void load(){
        this.setup();
        this.actionHandlingSetup();

        this.stage.setTitle(messages.getString("preferences"));
        this.stage.setIconified(false);
        this.stage.setScene(new Scene(this.gridPane));
        this.stage.setWidth(STAGE_WIDTH);
        this.stage.setHeight(STAGE_HEIGHT);
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(this.primaryStage);
        this.stage.showAndWait();
    }

    @Override
    public void setup(){
        //Initialising Values.
        this.languagesComboBox.setItems(FXCollections.observableArrayList(LanguageHandler.getAllLocales()));
        this.languagesComboBox.getSelectionModel().select(LanguageHandler.getSelectedLocale());
        this.useAnimationsCheckbox.setSelected(SaveSettingsHandler.getPreference("use_animations").contains("true"));

        //Alignment & Spacing.
            //HBox
        languagesHBox.setAlignment(Pos.CENTER);
        languagesHBox.setSpacing(5);
        useAnimationsHBox.setAlignment(Pos.CENTER);
        useAnimationsHBox.setSpacing(5);
            //GeneralSettingsVBox
        generalSettingsVBox.setSpacing(5);
        generalSettingsVBox.setAlignment(Pos.CENTER);
            //ButtonHBox
        buttonHBox.setSpacing(5);
        buttonHBox.setAlignment(Pos.CENTER);
            //Gridpane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        //Adding the children.
        languagesHBox.getChildren().addAll(languagesLabel, languagesComboBox);
        useAnimationsHBox.getChildren().addAll(useAnimationsLabel, useAnimationsCheckbox);
        generalSettingsVBox.getChildren().addAll(languagesHBox, useAnimationsHBox);
        buttonHBox.getChildren().addAll(applyButton, closeButton);

        //Adding it all together.
        gridPane.addRow(0, generalSettingsVBox);
        gridPane.addRow(1, buttonHBox);
    }

    @Override
    public void actionHandlingSetup() {
        this.applyButton.setOnAction(e -> {
            if (!this.languagesComboBox.getSelectionModel().isEmpty()){
                LanguageHandler.setMessages(this.languagesComboBox.getSelectionModel().getSelectedItem());
            }
            SaveSettingsHandler.setPreference("use_animations", "" + this.useAnimationsCheckbox.isSelected());
        });

        this.closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }


}