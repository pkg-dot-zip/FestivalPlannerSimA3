package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public abstract class AbstractGUI extends AbstractDialogPopUp {

    public GridPane gridPane = new GridPane();
    public HBox buttonHBox = new HBox();

    public abstract void load();

    public abstract void setup();

    public abstract void actionHandlingSetup();

}
