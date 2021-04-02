package FestivalPlanner.GUI.AgendaGUI.PopUpGUI;

import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
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
import java.util.ResourceBundle;

/**
 * Contains methods for all PopUps used in the program.
 * <p>
 * Most PopUps do or contain the following:
 * <p><ul>
 * <li>Set a Title
 * <li>Set the header text
 * <li>Set the content text
 * <li>Play a sound
 * <p>
 *  Some PopUps allow choices to be made.
 */
public class AbstractDialogPopUp {

    //LanguageHandling
    private static ResourceBundle messages = LanguageHandler.getMessages();

    /**
     * Prompts the user with the choice to exit the program or cancel this operation.
     */
    public static void showExitConfirmationPopUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(messages.getString("exit_confirmation"));
        alert.setHeaderText(messages.getString("are_you_sure_you_want_to_exit"));
        alert.setContentText(messages.getString("any_unsaved_changes_will_be_lost"));

        WindowsSystemSoundHandler.load(SystemSoundEnum.HAND);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    /**
     * Prompts the user with the choice to delete a show or cancel this operation.
     */
    public static boolean showDeleteConfirmationPopUp(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(messages.getString("remove_confirmation"));
        alert.setHeaderText(messages.getString("are_you_sure_you_want_to_remove_this_show"));
        alert.setContentText(messages.getString("the_show_will_be_lost"));

        WindowsSystemSoundHandler.load(SystemSoundEnum.HAND);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that <code>TextFields</code> are
     * empty and should be filled in.
     */
    public static void showEmptyTextFieldsPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(messages.getString("empty_textfields"));
        alert.setHeaderText(messages.getString("missing_values"));
        alert.setContentText(messages.getString("please_fill_in_the_required_textfields"));
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }


    /**
     * Opens a window containing a message, with the goal of informing the user that an already occupied
     * <code>Artist</code> was assigned and should be changed/removed.
     */
    public static void showDuplicateArtistPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(messages.getString("duplicate_artists"));
        alert.setHeaderText(messages.getString("artist_already_occupied"));
        alert.setContentText(messages.getString("please_remove_already_assigned_artists"));
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }

    /**
     * Opens a dialog window with a text area, with its text being set to the <code>printStackTrace()</code> method
     * from the exception received as a parameter.
     * @param e  exception to get the text from
     */
    public static void showExceptionPopUp(Exception e){
        if (SaveSettingsHandler.getPreference("use_exception_popups").contains("true")){
            GridPane exceptionGridPane = new GridPane();

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            TextArea textArea = new TextArea(stringWriter.toString());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(messages.getString("exception"));
            alert.setHeaderText(messages.getString("program_interrupted"));
            alert.setContentText(messages.getString("an_exception_has_occurred"));
            WindowsSystemSoundHandler.load(SystemSoundEnum.HAND);

            exceptionGridPane.setMaxWidth(Double.MAX_VALUE);
            exceptionGridPane.addRow(0, textArea);

            alert.getDialogPane().setExpandableContent(exceptionGridPane);

            alert.showAndWait();
        }
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that <code>this.currentShow</code> in
     * <code>AgendaModule</code> is equal to <i>Null</i>.
     */
    public static void showNoArtistsOrPodiumsPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(messages.getString("cant_create_show"));
        alert.setHeaderText(messages.getString("no_artists_and_or_podiums_to_choose_from"));
        alert.setContentText(messages.getString("please_open_the_artist"));
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that <code>this.currentShow</code> in
     * <code>AgendaModule</code> is equal to <i>Null</i>.
     */
    public static void showNoLayerSelectedPopUp(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(messages.getString("cant_get_selected_show"));
        alert.setHeaderText(messages.getString("no_selected_layer"));
        alert.setContentText(messages.getString("please_select_a_show_and_try_again"));
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }

    /**
     * Opens a window containing a message, with the goal of informing the user that there have been selected too many shows
     * in <code>AgendaModule</code>.
     * <p>
     * This PopUp should only be called when the user clicks on the "Swap" ContextMenuItem while having more than 2 layers selected.
     */
    public static void showTooManyLayersSelectedPopUp(int showCount){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(messages.getString("too_many_layers_selected"));
        alert.setHeaderText(messages.getString("expected_two_layers"));
        alert.setContentText(messages.getString("expected_two_layers_content") + showCount + "!");
        WindowsSystemSoundHandler.load(SystemSoundEnum.DEFAULT);
        alert.showAndWait();
    }
}
