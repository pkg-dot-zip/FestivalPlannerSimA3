package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Util.SoundHandling.SystemSoundEnum;
import FestivalPlanner.Util.SoundHandling.WindowsSystemSoundHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Contains methods for all PopUps used in the program.
 */
public abstract class AbstractDialogPopUp {

    /**
     * Prompts the user with the choice to exit the program or cancel this operation.
     */
    public void showExitConfirmationPopUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Any unsaved changes will be lost!");

        WindowsSystemSoundHandler.load(SystemSoundEnum.HAND);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    /**
     * Prompts the user with the choice to delete a show or cancel this operation.
     */
    public boolean showDeleteConfirmationPopUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Confirmation");
        alert.setHeaderText("Are you sure you want to remove this show?");
        alert.setContentText("The show will be lost");

        WindowsSystemSoundHandler.load(SystemSoundEnum.HAND);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that <code>TextFields</code> are
     * empty and should be filled in.
     */
    public void showEmptyTextFieldsPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Empty Textfields");
        alert.setHeaderText("Missing values");
        alert.setContentText("Please fill in the required textfields.");
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }

    /**
     * Opens a dialog window with a text area, with its text being set to the <code>printStackTrace()</code> method
     * from the exception received as a parameter.
     * @param e  exception to get the text from
     */
    public void showExceptionPopUp(Exception e){
        GridPane exceptionGridPane = new GridPane();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        TextArea textArea = new TextArea(stringWriter.toString());

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("Program interrupted");
        alert.setContentText("An exception has occurred. Apologies for the inconvenience.");
        WindowsSystemSoundHandler.load(SystemSoundEnum.HAND);

        exceptionGridPane.setMaxWidth(Double.MAX_VALUE);
        exceptionGridPane.addRow(0, textArea);

        alert.getDialogPane().setExpandableContent(exceptionGridPane);

        alert.showAndWait();
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that <code>this.currentShow</code> in
     * <code>AgendaModule</code> is equal to <i>Null</i>.
     */
    public void showNoLayerSelectedPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Can't get selected show");
        alert.setHeaderText("No selected layer");
        alert.setContentText("Please select a show and try again.");
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that <code>this.currentShow</code> in
     * <code>AgendaModule</code> is equal to <i>Null</i>.
     */
    public void showNoArtistsOrPodiumsPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Can't create show");
        alert.setHeaderText("No Artists and/or podiums to choose from");
        alert.setContentText("Please open the Artist & Podium editor and create artists and/or podiums" +
                " before opening the show editor.");
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }
}
