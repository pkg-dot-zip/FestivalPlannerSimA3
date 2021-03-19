package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArtistManagerTest {

	@Test
	void testAddArtist() {
		//Arrange
		ArtistManager artistManager = new ArtistManager();
		artistManager.addArtist(new Artist("Edwin", null, null));
		//Act
		Boolean returnResult = artistManager.getAllArtistNames().contains("Edwin");
		//Assert
		Assertions.assertEquals(returnResult, true, "Adding artists doesn't work");
	}

	@Test
	void testEditArtist() {
		//Arrange
		ArtistManager artistManager = new ArtistManager();
		artistManager.addArtist(new Artist("Edwin",null,null));
		//Act
		artistManager.editArtist("Edwin", new Artist("Johan", null, null));
		Set artistNames = artistManager.getAllArtistNames();
		boolean returnResult = (artistNames.contains("Johan") && !artistNames.contains("Edwin"));
		//Assert
		Assertions.assertEquals(returnResult, true, "Editing Artists doesn't work");
	}
}