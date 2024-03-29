package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.AgendaModule;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.SimulatorGUI.SimulatorModule;
import FestivalPlanner.Util.ImageLoader;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainGUI extends Application {

    private Stage stage;
    private AgendaModule agendaModule;
    private SimulatorModule simulatorModule;

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
        this.stage = stage;

        loadAgendaCallBack();
        ImageLoader.loadImages();
        this.stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/Images/sus.png")));
    }

    /**
     * Sets the current scene to the scene of <code>this.simulatorModule</code>.
     */
    public void loadSimulatorCallBack() {
        //Setting width & height.
        this.stage.setWidth(1100);
        this.stage.setHeight(800);

        this.stage.setScene(this.simulatorModule.getScene());
        preventExit();
    }

    /**
     * creates a new <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI/SimulatorModule.html">SimulatorModule</a> if
     * <code>this.simulatorModule</code> is <i>null</i>.
     * <p>
     * Else it calls the {@link SimulatorModule#resetHandler()} method.
     * @param agendaModule the <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/AgendaModule.html">AgendaModule</a>
     *                     that will be called back upon.
     */
    public void constructSimulatorCallBack(AgendaModule agendaModule) {
        if (this.simulatorModule == null) {
            this.simulatorModule = new SimulatorModule(this, stage, agendaModule);
            simulatorModule.load();
        } else {
            this.simulatorModule.resetHandler();
        }
    }

    /**
     * Sets the current scene to the scene of <code>this.agendaModule</code>.
     */
    public void loadAgendaCallBack() {
        if (this.agendaModule == null) {
            this.agendaModule = new AgendaModule(this, stage);
            SaveSettingsHandler.firstLaunchSettingsCreation();
            this.agendaModule.load();
        }

        //Setting stage width & height.
        this.stage.setWidth(1450);
        this.stage.setHeight(350);

        this.stage.setScene(this.agendaModule.getAgendaScene());
        preventExit();
    }

    private void preventExit(){
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                AbstractDialogPopUp.showExitConfirmationPopUp();
            }
        });
    }
}