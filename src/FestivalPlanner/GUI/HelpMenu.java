package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URI;
import java.util.ResourceBundle;

/**
 * Contains static methods for retrieval of the helpMenu, since it is used in both modules of our software.
 */
public class HelpMenu {

    //LanguageHandling.
    private static ResourceBundle messages = LanguageHandler.getMessages();

    public static Menu getHelpMenu(Stage stage){
        //HelpMenu Value Initialising.
        Menu helpMenu = new Menu(messages.getString("help"));
        MenuItem gitHubMenuItem = new MenuItem(messages.getString("github"));
        MenuItem javaDocMenuItem = new MenuItem(messages.getString("javadoc"));
        MenuItem aboutMenuItem = new MenuItem(messages.getString("about"));

        //HelpMenu setOnAction Methods.
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

        //Adding all the children.
        helpMenu.getItems().addAll(gitHubMenuItem, javaDocMenuItem, aboutMenuItem);

        return helpMenu;
    }

    public static void processEditGUIStage(Stage stage, GridPane gridPane){
        //Stage Settings.
        stage.setTitle(messages.getString("show_editor"));
        stage.setScene(new Scene(gridPane));
        stage.setResizable(true);
        stage.setWidth(450);
        stage.setHeight(250);
        stage.setIconified(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static VBox getEditGUIMainPanel(){
        //Initialising Values.
        VBox mainPanel = new VBox();

        //Alignment & spacing.
        mainPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        mainPanel.setMaxHeight(150);
        mainPanel.setAlignment(Pos.BASELINE_CENTER);
        mainPanel.setSpacing(10);

        return mainPanel;
    }

    public static HBox getEditGUIHBox(){
        //Initialising Values.
        HBox hBox = new HBox();

        //Alignment & spacing.
        hBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        hBox.setMaxHeight(150);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(10);

        return hBox;
    }
}
