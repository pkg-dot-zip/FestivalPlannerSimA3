package FestivalPlanner.GUI.AgendaGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeAndPopularityPanel {

    private VBox mainPane;

    private Slider popularitySlider;

    private Label popularityLabel;
    private TextField startTimeTextField;
    private TextField endTimeTextField;

    public TimeAndPopularityPanel(){
        this.popularitySlider = new Slider();
        this.startTimeTextField = new TextField("StartTime");
        this.endTimeTextField = new TextField("EndTime");
        this.popularityLabel = new Label(" Expected popularity: 50%");

        this.mainPane = generateTimeAndPopularityPanel();

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
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for selecting the time and popularity of an event.
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * 	all the parts of the GUI responsible for selecting the time and popularity of an event
     */
    private VBox generateTimeAndPopularityPanel() {
        VBox timeAndPopularityVBox = new VBox();
        timeAndPopularityVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        timeAndPopularityVBox.setMaxHeight(150);
        timeAndPopularityVBox.setAlignment(Pos.BASELINE_CENTER);
        timeAndPopularityVBox.setSpacing(10);

        this.popularitySlider.setMin(0);
        this.popularitySlider.setMax(100);
        this.popularitySlider.setValue(50);

        this.startTimeTextField.setMinWidth(220);
        this.endTimeTextField.setMinWidth(220);

        timeAndPopularityVBox.getChildren().addAll(new Label("Select Time"), this.startTimeTextField,
                this.endTimeTextField, this.popularityLabel, this.popularitySlider);

        return timeAndPopularityVBox;
    }


    public LocalTime getStartTime() {
        try {
            return LocalTime.parse(this.startTimeTextField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalTime getEndTime() {
        try {
            return LocalTime.parse(this.endTimeTextField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPopularity(){
        return (int)this.popularitySlider.getValue();
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public void setStartTimeText(String startTimeTextField) {
        this.startTimeTextField.setText(startTimeTextField);
    }

    public void setEndTimeText(String endTimeTextField) {
        this.endTimeTextField.setText(endTimeTextField);
    }

    public void setPopularitySlider(int popularity) {
        this.popularitySlider.setValue(popularity);
    }
}
