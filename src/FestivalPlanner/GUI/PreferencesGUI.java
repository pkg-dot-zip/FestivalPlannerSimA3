package FestivalPlanner.GUI;

import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.MathHandling.ColorConverter;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Contains methods to either directly or indirectly call
 * <a href="{@docRoot}/FestivalPlanner/Util/PreferencesHandling">PreferencesHandling</a> to store
 * user preferences.
 */
public class PreferencesGUI extends AbstractGUI{

    //LanguageHandling.
    private ResourceBundle messages = LanguageHandler.getMessages();

    private final int STAGE_WIDTH = 360;
    private final int STAGE_HEIGHT = 240;

    private Stage primaryStage;
    private Stage stage = new Stage();

    private VBox generalSettingsVBox = new VBox();

    //Languages.
    private HBox languagesHBox = new HBox();
    private Label languagesLabel = new Label(messages.getString("select_language"));
    private ComboBox<Locale> languagesComboBox = new ComboBox<>();
    private Label languagesFlagLabel = new Label();

    //Animations.
    private HBox useAnimationsHBox = new HBox();
    private Label useAnimationsLabel = new Label(messages.getString("use_animations"));
    private CheckBox useAnimationsCheckbox = new CheckBox();

    //Delete Cache.
    private Button removeCacheButton = new Button(messages.getString("remove_cache_button"));
    private Tooltip removeCacheTooltip = new Tooltip(messages.getString("remove_cache_tooltip"));

    //Colors.
    private HBox colorHBox = new HBox();
    private Button selectedColorButton = new Button(messages.getString("color_for_selected_show"));
    private Button unselectedColorButton = new Button(messages.getString("color_for_shows_that_are_not_selected"));
    private Button colorResetToDefaultButton = new Button(messages.getString("reset_colors_to_default"));

    //ExceptionPopUps.
    private HBox exceptionHBox = new HBox();
    private Label exceptionLabel = new Label(messages.getString("exception_label"));
    private CheckBox exceptionCheckBox = new CheckBox();

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
        updateFlagEmoji();
        this.useAnimationsCheckbox.setSelected(SaveSettingsHandler.getPreference("use_animations").contains("true"));
        this.exceptionCheckBox.setSelected(SaveSettingsHandler.getPreference("use_exception_popups").contains("true"));

        //Alignment & Spacing.
            //HBox.
        this.languagesHBox.setAlignment(Pos.CENTER);
        this.languagesHBox.setSpacing(HBOX_SPACING);
        this.useAnimationsHBox.setAlignment(Pos.CENTER);
        this.useAnimationsHBox.setSpacing(HBOX_SPACING);
        this.colorHBox.setAlignment(Pos.CENTER);
        this.colorHBox.setSpacing(HBOX_SPACING);
        this.exceptionHBox.setAlignment(Pos.CENTER);
        this.exceptionHBox.setSpacing(HBOX_SPACING);
            //GeneralSettingsVBox.
        this.generalSettingsVBox.setSpacing(VBOX_SPACING);
        this.generalSettingsVBox.setAlignment(Pos.CENTER);
            //ButtonHBox.
        this.buttonHBox.setSpacing(HBOX_SPACING);
        this.buttonHBox.setAlignment(Pos.CENTER);
            //GridPane.
        this.gridPane.setHgap(GRIDPANE_HGAP);
        this.gridPane.setVgap(GRIDPANE_VGAP);
        this.gridPane.setAlignment(Pos.CENTER);

        //Tooltips.
        this.removeCacheButton.setTooltip(this.removeCacheTooltip);

        //Adding the children.
        this.languagesHBox.getChildren().addAll(this.languagesLabel, this.languagesComboBox, this.languagesFlagLabel);
        this.useAnimationsHBox.getChildren().addAll(this.useAnimationsLabel, this.useAnimationsCheckbox);
        this.colorHBox.getChildren().addAll(this.selectedColorButton, this.unselectedColorButton, this.colorResetToDefaultButton);
        this.exceptionHBox.getChildren().addAll(this.exceptionLabel, this.exceptionCheckBox);
        this.generalSettingsVBox.getChildren().addAll(this.languagesHBox, this.useAnimationsHBox, this.removeCacheButton, this.colorHBox, this.exceptionHBox);
        this.buttonHBox.getChildren().addAll(this.applyButton, this.closeButton);

        //Adding it all together.
        this.gridPane.addRow(0, this.generalSettingsVBox);
        this.gridPane.addRow(1, this.buttonHBox);
    }

    //TODO: Refactor so that there is no duplicate code.
    @Override
    public void actionHandlingSetup() {
        this.applyButton.setOnAction(e -> {
            if (!this.languagesComboBox.getSelectionModel().isEmpty()){
                LanguageHandler.setMessages(this.languagesComboBox.getSelectionModel().getSelectedItem());
            }
            SaveSettingsHandler.setPreference("use_animations", "" + this.useAnimationsCheckbox.isSelected());
            SaveSettingsHandler.setPreference("use_exception_popups", "" + this.exceptionCheckBox.isSelected());
        });

        this.removeCacheButton.setOnAction(e -> {
            File cacheDirectory = new File(System.getenv("LOCALAPPDATA") + "/A3/Resources/");
            File[] arrayOfFiles = cacheDirectory.listFiles();
            if (cacheDirectory.isDirectory()){
                for (File directory : arrayOfFiles){
                    for (File file : directory.listFiles()){
                        file.delete();
                    }
                    directory.delete();
                }
            }
        });

        this.languagesComboBox.setOnAction(e -> {
            updateFlagEmoji();
        });

            //Colors
        this.selectedColorButton.setOnAction(e -> {
            Stage stage = new Stage();
            java.awt.Color c = SaveSettingsHandler.getSelectedColor();
            ColorPicker colorPicker = new ColorPicker(ColorConverter.fromAwtToJavaFX(c)); //TODO: show currently saved color.

            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SaveSettingsHandler.setSelectedColor(colorPicker.getValue());
                }
            });

            VBox vBox = new VBox();
            Button closeButton =  new Button(messages.getString("close"));
            closeButton.setOnAction(actionEvent -> {
                stage.close();
            });

            closeButton.setAlignment(Pos.CENTER);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(colorPicker, closeButton);

            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setHeight(300);
            stage.setWidth(300);
            stage.setIconified(false);
            stage.setAlwaysOnTop(true);
            stage.initOwner(this.stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });

        this.unselectedColorButton.setOnAction(e -> {

            Stage stage = new Stage();
            java.awt.Color c = SaveSettingsHandler.getSelectedColor();
            ColorPicker colorPicker = new ColorPicker(ColorConverter.fromAwtToJavaFX(c)); //TODO: show currently saved color.

            colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    SaveSettingsHandler.setUnselectedColor(colorPicker.getValue());
                }
            });

            VBox vBox = new VBox();
            Button closeButton =  new Button(messages.getString("close"));
            closeButton.setOnAction(actionEvent -> {
                stage.close();
            });

            closeButton.setAlignment(Pos.CENTER);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(colorPicker, closeButton);

            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setHeight(300);
            stage.setWidth(300);
            stage.setIconified(false);
            stage.setAlwaysOnTop(true);
            stage.initOwner(this.stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });

        this.colorResetToDefaultButton.setOnAction(e -> {
            SaveSettingsHandler.restoreDefaultColors();
        });

        this.closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    /**
     * Changes the emoji next to the Language selection <code>ComboBox</code> to the one representing the language.
     */
    private void updateFlagEmoji(){
        if (languagesComboBox.getSelectionModel().getSelectedItem().equals(Locale.US)){
            this.languagesFlagLabel.setText("\uD83C\uDDFA\uD83C\uDDF8");
        } else if (languagesComboBox.getSelectionModel().getSelectedItem().equals(Locale.forLanguageTag("nl-NL"))){
            this.languagesFlagLabel.setText("\uD83C\uDDF3\uD83C\uDDF1");
        }
    }
}