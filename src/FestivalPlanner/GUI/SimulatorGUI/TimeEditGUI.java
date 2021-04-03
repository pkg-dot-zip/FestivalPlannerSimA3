package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.HelpMenu;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class TimeEditGUI extends AbstractGUI {

    private SimulatorHandler handler;

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Main Scene Components.
    private Stage stage = new Stage();

    //JavaFX Components
    private VBox mainPanel = HelpMenu.getEditGUIMainPanel();

    private Label currentTimeLabel = new Label(messages.getString("current_time"));
    private Label currentSpeedLabel = new Label(messages.getString("current_speed"));

    private HBox setTimeHBox = HelpMenu.getEditGUIHBox();
    private HBox bottomHBox = new HBox();

    //Remove Buttons
    private Button x025Button = new Button("x0.25");
    private Button x033Button = new Button("x0.33");
    private Button x050Button = new Button("x0.5");
    //Add Buttons
    private Button x15Button = new Button("x1.5");
    private Button x2Button = new Button("x2");
    private Button x125Button = new Button("x1.25");

    //Apply buttons
    private Button applyButton = new Button(messages.getString("apply"));
    private Button closeButton = new Button(messages.getString("close"));

    private double speed;

    public TimeEditGUI(SimulatorHandler handler) {
        this.handler = handler;
    }

    @Override
    public void load() {
        this.setup();
        this.actionHandlingSetup();

        this.speed = this.handler.getSpeed();
        currentTimeLabel.setText(messages.getString("current_time") + " " + this.handler.getTime().getHour() + ":" + this.handler.getTime().getMinute() + " " + messages.getString("hour"));
        currentSpeedLabel.setText(messages.getString("current_speed") + " " + this.speed + " game s/s");

        //Stage Settings.
        HelpMenu.processEditGUIStage(this.stage, this.gridPane);
    }

    @Override
    public void setup() {
        //Alignment & spacing.
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);
        bottomHBox.setAlignment(Pos.CENTER);

        //Adding all the children.
        bottomHBox.getChildren().addAll(this.applyButton, this.closeButton);
        setTimeHBox.getChildren().addAll(x025Button, x033Button, x050Button, x125Button, x15Button, x2Button);
        mainPanel.getChildren().addAll(currentTimeLabel, currentSpeedLabel, new Label(), setTimeHBox, new Label(), new Separator(), bottomHBox);

        //Adding it all together.
        gridPane.add(mainPanel, 0, 0);
    }

    @Override
    public void actionHandlingSetup() {
        this.x025Button.setOnAction(e -> addTime(0.25));
        this.x033Button.setOnAction(e -> addTime(0.33));
        this.x050Button.setOnAction(e -> addTime(0.5));
        this.x125Button.setOnAction(e -> addTime(1.25));
        this.x15Button.setOnAction(e -> addTime(1.5));
        this.x2Button.setOnAction(e -> addTime(2.0));

        this.applyButton.setOnAction(e -> {
            try {
                this.handler.setSpeed(this.speed);
            } catch (Exception ex) {
                AbstractDialogPopUp.showExceptionPopUp(ex);
            }
        });

        closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    private void addTime(double factor) {
        this.speed *= factor;

        if (this.speed >= 1000)
            this.speed = 1000;
        
        currentSpeedLabel.setText(messages.getString("current_speed") + " " + this.speed + " game s/s");
    }
}