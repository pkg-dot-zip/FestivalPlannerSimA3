package FestivalPlanner.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class AgendaModule{

    public AgendaModule() {
    }

    public Scene buildAgendaModule(){
        VBox mainPane = new VBox();

        AgendaCanvas agendaCanvas = new AgendaCanvas();
        mainPane.getChildren().addAll(agendaCanvas.buildAgendaCanvas());

        return new Scene(mainPane);
    }
}
