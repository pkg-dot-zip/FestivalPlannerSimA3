package FestivalPlanner.Util.MathHandling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorConverterTest {

    private final String fromAwtToJavaFXMessage = "The converted color is not the same! It should have returned a proper JavaFX Color!";
    private final String fromJavaFXToAWTMessage = "The converted color is not the same! It should have returned a proper AWT Color!";

    @Test
    void testFromAwtToJavaFX_withBlack_returnsBlack() {
        // Arrange

        // Act
        Color colorAWT = Color.BLACK;
        javafx.scene.paint.Color colorJavaFX = javafx.scene.paint.Color.color(0, 0, 0);
        javafx.scene.paint.Color resultColorJavaFX = ColorConverter.fromAwtToJavaFX(colorAWT);
        // Assert
        Assertions.assertEquals(resultColorJavaFX, colorJavaFX, fromAwtToJavaFXMessage);
    }

    @Test
    void testFromJavaFXToAwt_withBlack_returnsBlack() {
        // Arrange

        // Act
        Color colorAWT = Color.BLACK;
        javafx.scene.paint.Color colorJavaFX = javafx.scene.paint.Color.color(0, 0, 0);
        Color resultColorAWT = ColorConverter.fromJavaFXToAwt(colorJavaFX);
        // Assert
        Assertions.assertEquals(resultColorAWT, colorAWT, fromJavaFXToAWTMessage);
    }

    @Test
    void testFromAwtToJavaFX_withWhite_returnsWhite() {
        // Arrange

        // Act
        Color colorAWT = Color.WHITE;
        javafx.scene.paint.Color colorJavaFX = javafx.scene.paint.Color.color(1, 1, 1);
        javafx.scene.paint.Color resultColorJavaFX = ColorConverter.fromAwtToJavaFX(colorAWT);
        // Assert
        Assertions.assertEquals(resultColorJavaFX, colorJavaFX, fromAwtToJavaFXMessage);
    }

    @Test
    void testFromJavaFXToAwt_withWhite_returnsWhite() {
        // Arrange

        // Act
        Color colorAWT = Color.WHITE;
        javafx.scene.paint.Color colorJavaFX = javafx.scene.paint.Color.color(1, 1, 1);
        Color resultColorAWT = ColorConverter.fromJavaFXToAwt(colorJavaFX);
        // Assert
        Assertions.assertEquals(resultColorAWT, colorAWT, fromJavaFXToAWTMessage);
    }
}