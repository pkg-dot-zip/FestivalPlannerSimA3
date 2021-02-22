package FestivalPlanner.Util.SoundHandling;

import com.sun.istack.internal.NotNull;

import java.awt.*;

/**
 * Contains methods to load a sound from the <a href={@docRoot}/FestivalPlanner/Util/SoundHandling/SystemSoundEnum.html>SystemSoundEnum</a>
 * enumerator.
 */
public class WindowsSystemSoundHandler {

    //Code found at: https://stackoverflow.com/questions/3927941/system-sounds-in-java
    //Information found at: https://docs.oracle.com/javase/8/docs/technotes/guides/swing/1.4/w2k_props.html

    /**
     * Plays a sound from <a href={@docRoot}/FestivalPlanner/Util/SoundHandling/SystemSoundEnum.html>SystemSoundEnum</a> if the user is
     * running a version of Windows on his/her/theirs/its computer.
     * @param systemSoundEnum  enumerator referring to a native Windows sound
     */
    public static void load(SystemSoundEnum systemSoundEnum){
        if (System.getProperty("os.name").contains("Windows")) {
            String propertyName = findSoundWithEnum(systemSoundEnum);

            final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty(propertyName);
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /**
     * Converts an enumerator from <a href={@docRoot}/FestivalPlanner/Util/SoundHandling/SystemSoundEnum.html>SystemSoundEnum</a>
     * to a readable string, containing a reference to a sound from the Windows Operating System.
     * @param systemSoundEnum  enumerator referring to a native Windows sound
     * @return  reference to a sound from the Windows Operating System
     */
    @NotNull
    private static String findSoundWithEnum(SystemSoundEnum systemSoundEnum){
        String stringToReturn;
        switch(systemSoundEnum){
            case DEFAULT:
                stringToReturn = "win.sound.default";
                break;
            case CLOSE:
                stringToReturn = "win.sound.close";
                break;
            case MAXIMIZE:
                stringToReturn = "win.sound.maximize";
                break;
            case MINIMIZE:
                stringToReturn = "win.sound.minimize";
                break;
            case MENU_COMMAND:
                stringToReturn = "win.sound.menuCommand";
                break;
            case MENU_POPUP:
                stringToReturn = "win.sound.menuPopup";
                break;
            case OPEN:
                stringToReturn = "win.sound.open";
                break;
            case RESTORE_DOWN:
                stringToReturn = "win.sound.restoreDown";
                break;
            case RESTORE_UP:
                stringToReturn = "win.sound.restoreUp";
                break;
            case ASTERISK:
                stringToReturn = "win.sound.asterisk";
                break;
            case EXCLAMATION:
                stringToReturn = "win.sound.exclamation";
                break;
            case EXIT:
                stringToReturn = "win.sound.exit";
                break;
            case HAND:
                stringToReturn = "win.sound.hand";
                break;
            case QUESTION:
                stringToReturn = "win.sound.question";
                break;
            case START:
                stringToReturn = "win.sound.start";
                break;
            default:
                System.out.println("Njet Komrad, the motherland has fallen victim to an unreachable sound! :( ");
                return "win.sound.default";
        }

        return stringToReturn;
    }
}
