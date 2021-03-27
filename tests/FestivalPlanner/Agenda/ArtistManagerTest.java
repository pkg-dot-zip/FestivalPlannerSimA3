package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArtistManagerTest {

	@Test
	void testAddArtistAddsAnArtist() {
		//Arrange
		ArtistManager artistManager = new ArtistManager();
		Artist edwin = new Artist("Edwin", null, null);
		artistManager.addArtist(edwin);
		//Act
		boolean returnResult = (artistManager.getAllArtistNames().contains("Edwin") &&
				artistManager.getArtist("Edwin").equals(edwin));
		//Assert
		Assertions.assertTrue(returnResult, "Adding artists doesn't work correctly.");
	}

	@Test
	void testEditArtistChangesTheArtist() {
		//Arrange
		ArtistManager artistManager = new ArtistManager();
		Artist edwin = new Artist("Edwin", null, null);
		artistManager.addArtist(edwin);
		artistManager.editArtist("Edwin", new Artist("Johan", null, null));
		Set artistNames = artistManager.getAllArtistNames();
		//Act
		boolean returnResult = ((artistNames.contains("Johan") && !artistNames.contains("Edwin")) &&
				artistManager.getArtist("Johan").equals(edwin));
		//Assert
		Assertions.assertTrue(returnResult, "Editing Artists doesn't work correctly.");
	}


	@Test
	void testArtistManagerEmptyOnCreation() {
		//Arrange
		ArtistManager artistManager = new ArtistManager();
		//Act
		Set artistNames = artistManager.getAllArtistNames();
		//Assert
		Assertions.assertTrue(artistNames.isEmpty(), "The podium manager isn't empty on startup.");
	}

	@Test
	void testArtistManagerRemoveArtistRemovesArtist() {
		//Arrange
		ArtistManager artistManager = new ArtistManager();
		artistManager.addArtist(new Artist("Dababy", null, null));
		//Act
		artistManager.removeArtist("Dababy");
		Set artistNames = artistManager.getAllArtistNames();
		//Assert
		Assertions.assertTrue(artistNames.isEmpty(), "Removing artists doesn't work correctly.");
	}
}