package FestivalPlanner.Util.PreferencesHandling;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.Util.MathHandling.ColorConverter;
import com.sun.istack.internal.NotNull;
import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * Contains methods for saving preferences in a <i>.XML</i> file.
 * <p>
 * Based on code found on the internet.
 * For more information, check <a href="https://www.binarytides.com/read-write-save-configuration-file-java/">here</>.
 */
public class SaveSettingsHandler implements Serializable {

    //Code from: https://www.binarytides.com/read-write-save-configuration-file-java/

    /**
     * Stores the preference given in its parameters.
     * @param Key  string representing the value in the <i>configuration.xml</i> file
     * @param Value  preference we want to store
     */
    public static void setPreference(String Key, String Value) {
        Properties configFile = new Properties();
        try {
            InputStream f = new FileInputStream("configuration.xml");
            configFile.loadFromXML(f);
            f.close();
        } catch(Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
        configFile.setProperty(Key, Value);
        try {
            OutputStream f = new FileOutputStream("configuration.xml");
            configFile.storeToXML(f,"Configuration file for the Preference-System");
        } catch(Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }

    /**
     * Gets the value of a given key from the <i>configuration.xml</i> file.
     * @param Key  string to look up in the <i>configuration.xml</i> file
     * @return  string value thing thing
     */
    public static String getPreference(String Key) {
        Properties configFile = new Properties();
        try {
            InputStream f = new FileInputStream("configuration.xml");
            configFile.loadFromXML(f);
            f.close();
        } catch(Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
        return (configFile.getProperty(Key));
    }

    /**
     * Handles all settings on the first launch, apart from the language preference.
     * @see FestivalPlanner.Util.LanguageHandling.LanguageHandler
     */
    public static void firstLaunchSettingsCreation(){
        String b1 = SaveSettingsHandler.getPreference("use_animations");
        if (b1 == null){
            setPreference("use_animations", "true");
        }

        String b2 = SaveSettingsHandler.getPreference("use_exception_popups");
        if (b2 == null){
            setPreference("use_exception_popups", "false");
        }

        String c1 = SaveSettingsHandler.getPreference("selected_show_color");
        String c2 = SaveSettingsHandler.getPreference("unselected_show_color");
        if (c1 == null && c2 == null || c1.isEmpty() || c2.isEmpty()){
            restoreDefaultColors();
        }
    }

    /**
     * Sets the values related to color in the <b>configuration.xml</b> file to the default ones.
     */
    public static void restoreDefaultColors(){
        Color selectedColor = Color.getHSBColor(190 / 360f, .7f, .7f);
        Color unselectedColor = Color.getHSBColor(100 / 360f, .7f, .9f);

        setPreference("selected_show_color", String.valueOf(selectedColor.getRGB()));
        setPreference("unselected_show_color", String.valueOf(unselectedColor.getRGB()));
    }

    /**
     * Returns the color for selected shows.
     * @return  java.awt.color of selected shows
     */
    @NotNull
    public static Color getSelectedColor(){
        return Color.decode(getPreference("selected_show_color"));
    }

    /**
     * Returns the color for unselected shows.
     * @return  java.awt.color of unselected shows
     */
    @NotNull
    public static Color getUnselectedColor(){
        return Color.decode(getPreference("unselected_show_color"));
    }

    /**
     * Sets the color of selected shows to the parameter's value.
     * @param colorInput  javafx.scene.paint.color
     */
    @NotNull
    public static void setSelectedColor(javafx.scene.paint.Color colorInput){
        Color color = ColorConverter.fromJavaFXToAwt(colorInput);
        setPreference("selected_show_color", String.valueOf(color.getRGB()));
        Color.decode(getPreference("selected_show_color"));
    }

    /**
     * Sets the color of unselected shows to the parameter's value.
     * @param colorInput  javafx.scene.paint.color
     */
    @NotNull
    public static void setUnselectedColor(javafx.scene.paint.Color colorInput){
        Color color = ColorConverter.fromJavaFXToAwt(colorInput);
        setPreference("unselected_show_color", String.valueOf(color.getRGB()));
        Color.decode(getPreference("unselected_show_color"));
    }
}