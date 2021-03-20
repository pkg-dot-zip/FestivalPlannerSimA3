package FestivalPlanner.Util.LanguageHandling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageHandlerTest {

    private final String getMessagesApplyMessage = "Should've returned \"Apply\", but returned another string instead!";
    private final String getMessagesCloseMessage = "Should've returned \"Close\", but returned another string instead!";

    @Test
    void testGetMessages_withApply_returnsApply() {
        // Arrange
        // Act
        String actualValue = LanguageHandler.getMessages().getString("apply");
        String returnValue = "Apply";
        // Assert
        Assertions.assertEquals(returnValue, actualValue, getMessagesApplyMessage);
    }

    @Test
    void testGetMessages_withClose_returnsClose() {
        // Arrange
        // Act
        String actualValue = LanguageHandler.getMessages().getString("close");
        String returnValue = "Close";
        // Assert
        Assertions.assertEquals(returnValue, actualValue, getMessagesCloseMessage);
    }
}