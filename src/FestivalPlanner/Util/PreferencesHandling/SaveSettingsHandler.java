package FestivalPlanner.Util.PreferencesHandling;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * Contains code from the internet to save preferences in a .XML file.
 */
public class SaveSettingsHandler implements Serializable {

    //TODO: Write own methods for loading and saving preferences.
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
        }
        catch(IOException e) {
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        configFile.setProperty(Key, Value);
        try {
            OutputStream f = new FileOutputStream("configuration.xml");
            configFile.storeToXML(f,"Configuration file for the Preference-System");
        }
        catch(Exception e) {
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
        }
        catch(IOException e) {
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null , e.getMessage());
        }
        return (configFile.getProperty(Key));
    }

}
