package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {

    @Test
    void getDuration() {
        //Arrange
        Show show = new Show();
        show.setStartTime(LocalTime.of(12, 50, 40));
        show.setEndTime(LocalTime.of(12, 51, 20));
        long expectedDuration = 40;
        //Act
        long actualDuration = show.getDuration();
        //Assert
        Assertions.assertEquals(expectedDuration, actualDuration, "Show duration doesn't add up");
    }

    @Test
    void setAndGetName() {
        //Arrange
        Show show = new Show();
        String expectedName = "Test show";
        //Act
        show.setName("Test show");
        //Assert
        Assertions.assertEquals(expectedName, show.getName(), "Names don't match");
    }

}