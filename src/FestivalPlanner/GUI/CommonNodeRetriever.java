package FestivalPlanner.GUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URI;
import java.util.ResourceBundle;

/**
 * Contains static methods for retrieval of common GUI elements such as the helpMenu, since it is used in both modules of our software.
 */
public class CommonNodeRetriever {

    //LanguageHandling.
    private static ResourceBundle messages = LanguageHandler.getMessages();

    /**
     * Creates the helpMenu used in both our modules, and returns it.
     * @param stage  stage used by the AboutPopUp
     * @return  newly generated Menu
     */
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

    /**
     * Applies a variety of properties to a stage given as a parameter. It also sets the scene of this
     * stage to a new instance of <b>Scene</b> with the <code>gridPane</code> as the parameter.
     * @param stage  stage to apply properties to
     * @param gridPane  gridPane to set the new <b>Scene</b> to
     */
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

    /**
     * Returns a newly created VBox with some some properties applied to it.
     * @return  a newly created VBox
     */
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

    /**
     * Returns a newly created HBox with some some properties applied to it.
     * @return  a newly created HBox
     */
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

    /**
     * Returns a newly created VBox with some some properties applied to it.
     * <p>
     * This method is used by the <a href="{@docRoot}/FestivalPlanner/GUI/ShowEditorGUI.html">ShowEditorGUI</a> class.
     * @return  a newly created VBox
     */
    public static VBox getShowEditorVBox(){
        //Initialising Values.
        VBox vBoxToReturn = new VBox();

        //Alignment & spacing.
        vBoxToReturn.setSpacing(5);
        vBoxToReturn.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        vBoxToReturn.setPadding(new Insets(0, 2, 10, 2));
        vBoxToReturn.setMaxHeight(150);
        vBoxToReturn.setAlignment(Pos.BASELINE_CENTER);
        return vBoxToReturn;
    }

    /**
     * Creates a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a>
     * that contains the parts of the GUI responsible for selecting a podium for an event.
     * @return a <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html">VBox</a> with
     * the parts of the GUI responsible for selecting a podium for an event
     */
    public static VBox getShowEditorMainPane(VBox artistVBox, VBox artistAtEventSetterVBox) {
        //Initialising Values.
        VBox mainVBox = new VBox();
        HBox hBox = new HBox();

        //Alignment & spacing.
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        mainVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        mainVBox.setMaxHeight(150);
        mainVBox.setPadding(new Insets(0,2,10,2));
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(5);

        //Adding all the children.
        hBox.getChildren().addAll(artistVBox, artistAtEventSetterVBox);
        mainVBox.getChildren().addAll(new Label(messages.getString("select_artists_and_podium")), hBox);
        return mainVBox;
    }
}