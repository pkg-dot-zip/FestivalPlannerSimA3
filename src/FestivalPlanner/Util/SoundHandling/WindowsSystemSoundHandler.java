package FestivalPlanner.Util.SoundHandling;

import java.awt.*;

/**
 * Contains methods to load a sound from the user's operating system.
 */
public class WindowsSystemSoundHandler {

    public static final String DEFAULT = "win.sound.default";
    public static final String HAND = "win.sound.hand";

    //Code found at: https://stackoverflow.com/questions/3927941/system-sounds-in-java
    //Information found at: https://docs.oracle.com/javase/8/docs/technotes/guides/swing/1.4/w2k_props.html

    /**
     * Plays a system-sound if the user is
     * running a version of Windows on his/her/theirs/its computer.
     * <p>
     * The sound is based on the parameter's value, the string.
     * For more information check <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/swing/1.4/w2k_props.html">here</a>
     * and <a href="https://stackoverflow.com/questions/3927941/system-sounds-in-java">here</a>
     */
    public static void load(String propertyName){
        if (System.getProperty("os.name").contains("Windows")) {

            final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty(propertyName);
            if (runnable != null) {
                runnable.run();
            }
        }
    }
}
