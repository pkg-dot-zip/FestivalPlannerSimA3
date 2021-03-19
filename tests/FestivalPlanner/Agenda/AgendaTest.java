package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void getNameWithEmptyConstructor() {
        //Setup
        Agenda agenda = new Agenda();

        //Act
        String name = agenda.getName();

        //Assert
        Assertions.assertEquals("", name, "Name should be empty");
    }


    @Test
    void setNameWithNotNullValue() {
        //Setup
        String expectedName = "TestName";
        Agenda agenda = new Agenda();

        //Act
        agenda.setName(expectedName);
        String actualName = agenda.getName();

        //Assert
        Assertions.assertEquals(expectedName, actualName, "Name set by the setName method");

    }

    @Test
    void getShows() {
        //Setup
        ArrayList<Show> expectedShows = new ArrayList<>();
        Agenda agenda = new Agenda("TestName", expectedShows);

        //Act
        ArrayList<Show> actualShows = agenda.getShows();

        //Assert
        Assertions.assertEquals(expectedShows, actualShows);
    }

    @Test
    void addShowWithEmptyConstructor() {
        //Setup
        Agenda agenda = new Agenda();

        //Act
        Show expectedShow = new Show();
        agenda.addShow(expectedShow);

        //Assert
        Assertions.assertEquals(expectedShow, agenda.getShows().get(0));
    }

    @Test
    void removeShow() {
        //Setup
        Show show = new Show();
        ArrayList<Show> expectedShows = new ArrayList<>();
        expectedShows.add(show);

        Agenda agenda = new Agenda("TestAgenda", expectedShows);

        //Act
        expectedShows.remove(show);
        agenda.removeShow(show);
        ArrayList<Show> actualShows = agenda.getShows();

        //Assert
        Assertions.assertEquals(expectedShows, actualShows);

    }
}