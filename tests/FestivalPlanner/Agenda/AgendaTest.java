package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    @Test
    void getNameWithNameInConstructor() {
        //Setup
        String expectedName = "TestName";
        Agenda agenda = new Agenda("TestName");

        //Act
        String actualName = agenda.getName();

        //Assert
        Assertions.assertEquals(expectedName, actualName, "The name should be: " + expectedName);
    }


}