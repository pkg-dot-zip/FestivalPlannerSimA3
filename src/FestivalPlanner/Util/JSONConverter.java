package FestivalPlanner.Util;

import FestivalPlanner.TileMap.Tile;
import FestivalPlanner.TileMap.TileManager;
import FestivalPlanner.TileMap.TileMap;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.image.*;
import java.io.InputStream;

public class JSONConverter {

    public JSONConverter() {
    }

    public void JSONToTileMap(String fileName) {
        TileManager tileManager = new TileManager();

        InputStream inputStream = getClass().getResourceAsStream(fileName);
        JsonReader reader = Json.createReader(inputStream);
        JsonObject root = reader.readObject();

        int width = root.getInt("width");
        int height = root.getInt("height");

        //loading in Tilesets
        try {

            JsonArray tileSets = root.getJsonArray("tilesets");

            //loops through all tileSets
            for (int i = 0; i < tileSets.size(); i++) {
                JsonObject tileSet = tileSets.getJsonObject(i);

                int tileWidth = tileSet.getInt("tilewidth");
                int tileHeight = tileSet.getInt("tileheight");
                int collums = tileSet.getInt("columns");

                //Loading image in tileSet
                BufferedImage tileImage = ImageIO.read(getClass().getResourceAsStream(tileSet.getString("image")));

                //only loop through used images for efficiency
                JsonArray tiles = tileSet.getJsonArray("tiles");
                for (int q = 0; q < tiles.size(); q++) {
                    JsonObject tile = tiles.getJsonObject(q);
                    int id = tile.getInt("id");

                    //only loops through used images for efficiency
                    Tile tileObject = new Tile(tileImage.getSubimage(
                            tileWidth * (id % collums),         //
                            tileHeight * (id / collums),        // Voodoo magic splitting the images
                            tileWidth,                            //
                            tileHeight), id);                     //

                    tileManager.addTile(id, tileObject);

                }

            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }


    }

}
