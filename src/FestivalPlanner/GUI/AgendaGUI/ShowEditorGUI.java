package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.Show;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalTime;

public class ShowEditorGUI extends AbstractDialogPopUp {

    //Main Scene Components.
    Stage stage = new Stage();
    Scene scene;

    //TimeAndPopularity
    private VBox timeAndPopularityVBox = new VBox();
    private Slider popularitySlider = new Slider();
    private Label popularityLabel = new Label(" Expected popularity: 50%");
    private TextField startTimeTextField = new TextField("StartTime");
    private TextField endTimeTextField = new TextField("EndTime");

    //ShowName
    private VBox showNameVBox = genericVBox();
    private TextField showNameTextField = new TextField();

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
        //If no layer is selected, create a new one.
        if (this.agendaModule.getCurrentShow() != null) {
            isNewShow = false;
        } else {
            isNewShow = true;
        }

        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        this.scene = new Scene(gridPane);
        this.stage.setTitle("Show Editor");
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.setWidth(600);
        this.stage.setHeight(600);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    public void setup(){
        //Value init
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
        gridPane.setVgap(10);
        gridPane.setHgap(10);
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
        gridPane.addRow(2, buttonHBox);
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

        //Generic
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

            //Apply to AgendaModule
            if (isNewShow){
                this.agendaModule.setCurrentShow(selectedShow);
            } else {
                this.agendaModule.getCurrentShow().replaceShow(selectedShow);
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
    }

    public VBox genericVBox(){
        VBox vBoxToReturn = new VBox();
        vBoxToReturn.setSpacing(5);
        vBoxToReturn.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        vBoxToReturn.setPadding(new Insets(0, 2, 10, 2));
        vBoxToReturn.setMaxHeight(150);
        vBoxToReturn.setAlignment(Pos.BASELINE_CENTER);
        return vBoxToReturn;
    }
}
