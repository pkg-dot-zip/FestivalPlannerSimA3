package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    /**
     * Starts the MainGUI.
     * @param stage  The stage that is shown on screen
     * @throws Exception  Standard exception in <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html">Application</a>
     */
    @Override
    public void start(Stage stage) throws Exception {

        AgendaModule agendaModule = new AgendaModule(stage);
        SaveSettingsHandler.firstLaunchSettingsCreation();
        agendaModule.load();

    }


}
