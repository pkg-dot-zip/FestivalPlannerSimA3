package FestivalPlanner.Util.JsonHandling;

        import FestivalPlanner.TileMap.TileMap;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {

    @Test
    void testJSONToTileMap() {
        //arrange
        JsonConverter converter = new JsonConverter();
        String fileName = "/testMap.json";

        //act
        TileMap actualTilemap = converter.JSONToTileMap(fileName);

        //assert
        assertNotNull(actualTilemap, "Exception thrown");

    }
}