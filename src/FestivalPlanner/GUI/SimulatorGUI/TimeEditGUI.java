package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.CommonNodeRetriever;
import FestivalPlanner.Logic.SimulatorHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

/**
 * Contains methods and attributes seen in the TimeEditGUI window.
 * <p>
 * This window is used to edit the speed at which the simulation is being run.
 */
public class TimeEditGUI extends AbstractEditGUI {

    //Time.
    private Label currentTimeLabel = new Label(messages.getString("current_time"));
    private Label currentSpeedLabel = new Label(messages.getString("current_speed"));
    private HBox setTimeHBox = CommonNodeRetriever.getEditGUIHBox();
    private double speed;

    //Buttons.
        //Remove Buttons.
    private Button x025Button = new Button("x0.25");
    private Button x033Button = new Button("x0.33");
    private Button x050Button = new Button("x0.5");

    private Button pauseButton = new Button(messages.getString("pause"));

        //Add Buttons.
    private Button x15Button = new Button("x1.5");
    private Button x2Button = new Button("x2");
    private Button x125Button = new Button("x1.25");

    /**
     * Constructor of <b>TimeEditGUI</b> taking a <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorHandler.html">SimulatorHandler</a> as parameter.
     * @param handler  handler to set <code>this.handler</code> to
     */
    TimeEditGUI(SimulatorHandler handler) {
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
        CommonNodeRetriever.processEditGUIStage(this.stage, this.gridPane);
    }

    @Override
    public void setup() {
        genericSetup();

        //Adding all the children.
        setTimeHBox.getChildren().addAll(x025Button, x033Button, x050Button, pauseButton, x125Button, x15Button, x2Button);
        mainPanel.getChildren().addAll(currentTimeLabel, currentSpeedLabel, new Label(), setTimeHBox, new Label(), new Separator(), bottomHBox);

        //Adding it all together.
        gridPane.add(mainPanel, 0, 0);
    }

    @Override
    public void actionHandlingSetup() {
        genericActionHandlingSetup();

        this.x025Button.setOnAction(e -> addTime(0.25));
        this.x033Button.setOnAction(e -> addTime(0.33));
        this.x050Button.setOnAction(e -> addTime(0.5));
        this.x125Button.setOnAction(e -> addTime(1.25));
        this.x15Button.setOnAction(e -> addTime(1.5));
        this.x2Button.setOnAction(e -> addTime(2.0));

        //Pause button.
        this.pauseButton.setOnAction(e -> {
            this.handler.setPaused(!this.handler.isPaused());
        });
    }

    //TODO: Rename if this doesn't add time, but changes the speed instead.
    private void addTime(double factor) {
        this.speed *= factor;

        if (this.speed >= 300)
            this.speed = 300;
        
        currentSpeedLabel.setText(messages.getString("current_speed") + " " + this.speed + " game s/s");
    }

    @Override
    void onApply() {
        try {
            this.handler.setSpeed(this.speed);
        } catch (Exception ex) {
            AbstractDialogPopUp.showExceptionPopUp(ex);
        }
    }
}