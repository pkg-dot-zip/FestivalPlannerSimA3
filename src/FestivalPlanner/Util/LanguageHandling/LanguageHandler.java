package FestivalPlanner.Util.LanguageHandling;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageHandler {

    //LanguageHandling
    static Locale aLocale = new Locale("en","US");
    static Locale nlLocale = new Locale("nl","NL");
    static Locale selectedLocale = nlLocale;
    static ResourceBundle messages = ResourceBundle.getBundle("lang", selectedLocale);

    public static Locale getSelectedLocale() {
        return selectedLocale;
    }

    public static void setSelectedLocale(Locale locale) {
        selectedLocale = locale;
    }

    public static ResourceBundle getMessages() {
        return messages;
    }

    public static void setMessages(Locale locale){
        selectedLocale = locale;
        messages = ResourceBundle.getBundle("lang", selectedLocale);
    }
}
