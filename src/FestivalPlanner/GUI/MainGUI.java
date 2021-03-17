package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.GUI.SimulatorGUI.SimulatorModule;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    /**
     * Starts the MainGUI.
     * <p>
     * It creates an instance of <code>AgendaModule</code>, and then loads all the settings from <code>SaveSettingsHandler</code>.
     * When that is done it will run the <code>load()</code> method in <code>AgendaModule</code>.
     * @param stage  the stage that is shown on screen
     * @throws Exception  standard exception in <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html">Application</a>
     */
    @Override
    public void start(Stage stage) throws Exception {

        AgendaModule agendaModule = new AgendaModule(stage);
        SaveSettingsHandler.firstLaunchSettingsCreation();
        agendaModule.load();
        SimulatorModule simulatorModule = new SimulatorModule(stage);
        simulatorModule.load();

        agendaModule.setSimulatorScene(simulatorModule.getSimulatorScene());
        simulatorModule.setAgendaScene(agendaModule.getAgendaScene());


    }


}
