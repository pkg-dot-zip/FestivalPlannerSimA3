package FestivalPlanner.Util.LanguageHandling;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Contains methods handling the <i>'lang'</i> Resource Bundle containing language files.
 * <p>
 * The currently supported languages are:
 * <p><ul>
 * <li>English - United States of America
 * <li>Dutch - Kingdom of The Netherlands
 * </ul>
 */
public class LanguageHandler {

    //LanguageHandling
    private static Locale aLocale = new Locale("en","US");
    private static Locale nlLocale = new Locale("nl","NL");
    private static Locale selectedLocale = restoreSettings();
    private static ResourceBundle messages = ResourceBundle.getBundle("lang", selectedLocale);

    /**
     * Called on launch. Sets <code>this.selectedLocale</code> to the language stored in the <i>configuration.xml</i> file.
     * <p>
     * If there is no configuration file yet, it defaults to Dutch <i>(nl_NL)</i>.
     * @return  locale found in the configuration file, or Dutch
     * @see SaveSettingsHandler
     */
    private static Locale restoreSettings(){
        Locale selectedLocale;
        try {
            selectedLocale = Locale.forLanguageTag(SaveSettingsHandler.getPreference("Language"));
        } catch (NullPointerException e){
            selectedLocale = nlLocale;
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
        return selectedLocale;
    }

    /**
     * Returns an ArrayList of all supported Locales.
     * @return  ArrayList containing all supported Locales.
     * @see LanguageHandler
     */
    public static ArrayList<Locale> getAllLocales(){
        ArrayList<Locale> toReturn = new ArrayList<>();
        toReturn.add(aLocale);
        toReturn.add(nlLocale);
        return toReturn;
    }

    /**
     * Getter for <code>selectedLocale</code> used to get the right language when starting our software, and by the preferenceGUI in the setup() method.
     * <p>
     * Since this method is static, we do <b>NOT</b> utilize <i>this</i>.
     * @return  selectedLocale
     */
    public static Locale getSelectedLocale() {
        return selectedLocale;
    }

    /**
     * Getter for <code>messages</code> used by other classes to get strings.
     * <p>
     * Since this method is static, we do <b>NOT</b> utilize <i>this</i>.
     * @return  messages
     */
    public static ResourceBundle getMessages() {
        return messages;
    }

    /**
     * Sets the current Locale to its parameter's value.
     * <p>
     * This method does the following in this specific order:
     * <p><ul>
     * <li>Clear the Resource Bundle's cache.
     * <li>Set <code>this.selectedLocale</code> to its parameter's value.
     * </ul>
     * @param locale
     */
    public static void setMessages(Locale locale){
        ResourceBundle.clearCache();
        selectedLocale = locale;
        SaveSettingsHandler.setPreference("Language", locale.toLanguageTag());
        messages = ResourceBundle.getBundle("lang", selectedLocale);
    }
}
