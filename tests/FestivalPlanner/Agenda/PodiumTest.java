package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PodiumTest {

    @Test
    void getName() {
        //Setup
        String expectedName = "TestName";
        Podium podium = new Podium(expectedName, "Test");

        //Act
        String actualName = podium.getName();

        //Assert
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void getLocation() {
        //Setup
        String expectedLocation = "TestLocation";
        Podium podium = new Podium("TestName", expectedLocation);

        //Act
        String actualLocation = podium.getLocation();

        //Assert
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void setNameNotNull() {
        //Setup
        Podium podium = new Podium("Test", "Test");
        String expectedName = "TestName";

        //Act
        podium.setName(expectedName);
        String actualName = podium.getName();

        //Assert
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void setLocation() {
        //Setup
        Podium podium = new Podium("Test", "Test");
        String expectedLocation = "TestLocation";

        //Act
        podium.setLocation(expectedLocation);
        String actualLocation = podium.getLocation();

        //Assert
        Assertions.assertEquals(expectedLocation, actualLocation);
    }
}