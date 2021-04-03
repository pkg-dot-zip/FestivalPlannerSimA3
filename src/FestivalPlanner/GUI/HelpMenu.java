package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.net.URI;
import java.util.ResourceBundle;

/**
 * Contains static methods for retrieval of the helpMenu, since it is used in both modules of our software.
 */
public class HelpMenu {

    public static Menu helpMenu(Stage stage){
        //LanguageHandling.
        ResourceBundle messages = LanguageHandler.getMessages();

        //HelpMenu.
        Menu helpMenu = new Menu(messages.getString("help"));
        MenuItem gitHubMenuItem = new MenuItem(messages.getString("github"));
        MenuItem javaDocMenuItem = new MenuItem(messages.getString("javadoc"));
        MenuItem aboutMenuItem = new MenuItem(messages.getString("about"));

        //HelpMenu
        gitHubMenuItem.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(new URI("https://github.com/ZimonIsHim/FestivalPlannerSimA3/tree/devAftermath"));
            } catch (Exception ex){
                AbstractDialogPopUp.showExceptionPopUp(ex);
            }
        });

        aboutMenuItem.setOnAction(e -> {
            AboutPopUp aboutPopUp = new AboutPopUp(stage);
            aboutPopUp.load();
        });

        helpMenu.getItems().addAll(gitHubMenuItem, javaDocMenuItem, aboutMenuItem);

        return helpMenu;
    }
}
