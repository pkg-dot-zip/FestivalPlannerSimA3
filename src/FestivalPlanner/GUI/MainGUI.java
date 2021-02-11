package FestivalPlanner.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

    /**
     * Starts the MainGUI
     * @param stage  The stage that is shown on screen
     * @throws Exception Standard exeption in<a href=”https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html”></a>
     */
    @Override
    public void start(Stage stage) throws Exception {

        AgendaModule agendaModule = new AgendaModule();

        stage.setScene(agendaModule.buildAgendaModule());
        stage.show();
    }


}
