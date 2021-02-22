package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Util.SoundHandling.SystemSoundEnum;
import FestivalPlanner.Util.SoundHandling.WindowsSystemSoundHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        } else {
            // ... user chose CANCEL or closed the dialog
        }
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
}
