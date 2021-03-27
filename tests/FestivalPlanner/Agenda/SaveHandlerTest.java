package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SaveHandlerTest {

	@Test
	void testWriteAgendaToFileCreatesFile() {
		//Arrange
		SaveHandler saveHandler = new SaveHandler();
		Agenda agenda = new Agenda();
		File file = new File("testWriteAgendaToFile.dat");
		file.delete();
		//Act
		saveHandler.writeAgendaToFile("testWriteAgendaToFile.dat", agenda);
		//Assert
		Assertions.assertTrue(file.exists(), "Agenda save has not been created.");
	}

	@Test
	void readAgendaFromFileContainsCorrectData() {
		//Arrange
		SaveHandler saveHandler = new SaveHandler();
		Agenda agenda = new Agenda("testAgenda");
		saveHandler.writeAgendaToFile("testWriteAgendaToFile.dat", agenda);
		Agenda fileAgenda = saveHandler.readAgendaFromFile("testWriteAgendaToFile.dat");
		//Act
		boolean returnResult = (agenda.getShows().equals(fileAgenda.getShows()) && agenda.getName().equals(fileAgenda.getName()));
		//Assert
		Assertions.assertTrue(returnResult, "Saving agenda's doesn't work correctly.");
	}
}