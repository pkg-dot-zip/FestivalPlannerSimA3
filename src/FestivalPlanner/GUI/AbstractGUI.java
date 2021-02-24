package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public abstract class AbstractGUI extends AbstractDialogPopUp {

    public GridPane gridPane = new GridPane();
    public HBox buttonHBox = new HBox();
    public Button applyButton = new Button("Apply");
    public Button closeButton = new Button("Close");

    public abstract void load();

    public abstract void setup();

    //TODO: Refactor to additional actionHandlingSetup() and make this method setup
    // onApplybutton() and setOnAction() for closeButton.
    public abstract void actionHandlingSetup();

}
