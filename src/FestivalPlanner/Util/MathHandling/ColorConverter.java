package FestivalPlanner.Util.MathHandling;

import javafx.scene.paint.Color;

/**
 * Contains methods for converting JavaFX colors to AWT colors.
 */
public class ColorConverter {

    /**
     * Returns a JavaFX color from the parameter's value, which is a Java.awt.Color.
     * @param colorInput  java.awt.Color to convert
     * @return  JavaFX color
     */
    public static Color fromAwtToJavaFX(java.awt.Color colorInput){
        return Color.rgb(colorInput.getRed(), colorInput.getGreen(), colorInput.getBlue(), colorInput.getAlpha() / 255.0);
    }

    /**
     * Returns a java.awt.Color color from the parameter's value, which is a JavaFX Color.
     * @param colorInput  JavaFX color to convert
     * @return  java.awt.Color color
     */
    public static java.awt.Color fromJavaFXToAwt(Color colorInput){
        return new java.awt.Color((float) colorInput.getRed(), (float) colorInput.getGreen(), (float) colorInput.getBlue(), (float) colorInput.getOpacity());
    }
}
