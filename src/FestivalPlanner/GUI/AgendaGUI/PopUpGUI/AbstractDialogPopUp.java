package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class AbstractDialogPopUp {

    public void showExitConfirmationPopUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Any unsaved changes will be lost!");

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
        alert.showAndWait();
    }


}
