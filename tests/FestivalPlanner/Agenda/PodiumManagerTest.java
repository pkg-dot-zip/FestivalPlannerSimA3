package FestivalPlanner.Agenda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class PodiumManagerTest {

    @Test
    void containsPodiumTestEmptyManager() {
        //Setup
        PodiumManager podiumManager = new PodiumManager();

        //Act
        boolean contains = podiumManager.containsPodium("Test");

        //Assert
        Assertions.assertFalse(contains, "Empty manager should not contain podium");
    }

    @Test
    void containsPodiumTestNotPodium() {
        //Setup
        PodiumManager podiumManager = new PodiumManager();
        Podium podium = new Podium("Podium1", "Left");
        podiumManager.addPodium(podium);

        //Act
        boolean contains = podiumManager.containsPodium("Podium2");

        //Assert
        Assertions.assertFalse(contains, "Manager should not contain podium");
    }

    @Test
    void containsPodiumTestTrue() {
        //Setup
        PodiumManager podiumManager = new PodiumManager();
        Podium podium = new Podium("Podium1", "Left");
        podiumManager.addPodium(podium);

        //Act
        boolean contains = podiumManager.containsPodium("Podium1");

        //Assert
        Assertions.assertTrue(contains, "Manager should contain podium");
    }

}