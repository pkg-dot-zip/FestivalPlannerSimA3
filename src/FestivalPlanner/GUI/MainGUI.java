package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.GUI.SimulatorGUI.SimulatorModule;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.ImageLoader;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private Stage stage;

    private AgendaModule agendaModule;
    private SimulatorModule simulatorModule;


    /**
     * Starts the MainGUI.
     * <p>
     * It creates an instance of <code>AgendaModule</code>, and then loads all the settings from <code>SaveSettingsHandler</code>.
     * When that is done it will run the <code>load()</code> method in <code>AgendaModule</code>.
     *
     * @param stage the stage that is shown on screen
     * @throws Exception standard exception in <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html">Application</a>
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        AgendaModule agendaModule = new AgendaModule(stage);
        SaveSettingsHandler.firstLaunchSettingsCreation();
        agendaModule.load();
        ImageLoader.loadImages();
        SimulatorModule simulatorModule = new SimulatorModule(stage, agendaModule);
        simulatorModule.load();
        loadAgendaCallBack();

//        this.agendaModule = new AgendaModule(stage);
//        SaveSettingsHandler.firstLaunchSettingsCreation();
//        agendaModule.load();
//        SimulatorModule simulatorModule = new SimulatorModule(stage, agendaModule);
//        simulatorModule.load();

//        agendaModule.setSimulatorScene(simulatorModule.getSimulatorScene());
//        simulatorModule.setAgendaScene(agendaModule.getAgendaScene());

    }

    public void loadSimulatorCallBack() {
        //Setting w/h
        this.stage.setWidth(1100);
        this.stage.setHeight(800);

        this.stage.setScene(this.simulatorModule.getSimulatorScene());
    }

    public void constructSimulatorCallBack(AgendaModule agendaModule) {
        if (this.simulatorModule == null) {
            this.simulatorModule = new SimulatorModule(this, stage, agendaModule);
            simulatorModule.load();
        } else {
            this.simulatorModule.resetHandler();
        }
    }

    public void loadAgendaCallBack() {
        if (this.agendaModule == null) {
            this.agendaModule = new AgendaModule(this, stage);
            SaveSettingsHandler.firstLaunchSettingsCreation();
            agendaModule.load();
        }

        //Setting stage width/height
        this.stage.setWidth(1450);
        this.stage.setHeight(350);

        this.stage.setScene(this.agendaModule.getAgendaScene());
    }

}
