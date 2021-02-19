package FestivalPlanner.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AgendaModule agendaModule = new AgendaModule(stage);
        stage.setScene(agendaModule.generateGUILayout());
        stage.show();
    }


}
