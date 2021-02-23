//package FestivalPlanner.GUI.AgendaGUI;
//
//import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.EmptyPopUp;
//
//import javafx.scene.layout.VBox;
//
//import java.time.LocalTime;
//
//public class TimeAndPopularityPanel {
//
//
//    public LocalTime getStartTime() {
//        try {
//            return LocalTime.parse(this.startTimeTextField.getText());
//        } catch (Exception e) {
//            EmptyPopUp emptyPopUp = new EmptyPopUp();
//            emptyPopUp.showExceptionPopUp(e);
//        }
//        return null;
//    }
//
//    public LocalTime getEndTime() {
//        try {
//            return LocalTime.parse(this.endTimeTextField.getText());
//        } catch (Exception e) {
//            EmptyPopUp emptyPopUp = new EmptyPopUp();
//            emptyPopUp.showExceptionPopUp(e);
//        }
//        return null;
//    }
//
//    public int getPopularity(){
//        return (int)this.popularitySlider.getValue();
//    }
//
//    public VBox getMainPane() {
//        return this.mainPane;
//    }
//
//    public void setStartTimeText(String startTimeTextField) {
//        this.startTimeTextField.setText(startTimeTextField);
//    }
//
//    public void setEndTimeText(String endTimeTextField) {
//        this.endTimeTextField.setText(endTimeTextField);
//    }
//
//    public void setPopularitySlider(int popularity) {
//        this.popularityLabel.setText(" Expected popularity: " + popularity+ "%");
//        this.popularitySlider.setValue(popularity);
//    }
//
//    public void actionHandlingSetup() {
//    }
//}
