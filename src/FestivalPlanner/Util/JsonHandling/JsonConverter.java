package FestivalPlanner.Util.JsonHandling;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.TileMap.Tile;
import FestivalPlanner.TileMap.TileLayer;
import FestivalPlanner.TileMap.TileManager;
import FestivalPlanner.TileMap.TileMap;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.image.*;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Contains methods for converting JSON files into usable TileMaps for our software.
 */
public class JsonConverter extends AbstractDialogPopUp {

    /**
     * Given the directory of a JSON file, this method wil read the JSON file and turn it into
     * a <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>.
     * @param fileName  The directory of the JSON file to be loaded
     * @return  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> created from the JSON file
     */
    public TileMap JSONToTileMap(String fileName) {
        TileManager tileManager = new TileManager();


        try (InputStream inputStream = getClass().getResourceAsStream(fileName);) {

            JsonReader reader = Json.createReader(inputStream);
            JsonObject root = reader.readObject();

            int width = root.getInt("width");
            int height = root.getInt("height");

            int tileWidth = root.getInt("tilewidth");
            int tileHeight = root.getInt("tileheight");

            TileMap tileMap = new TileMap(width, height, tileWidth, tileHeight, tileManager);

            //loading in Tile-sets
            loadInTilesets(tileManager, root);

            //Loading in Layers
            loadInLayers(tileManager, tileMap, root, width, height);

            return tileMap;
        } catch (Exception e) {
            showExceptionPopUp(e);
        }

        return null;
    }

    /**
     * Reads the JSON file and adds all the <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a> to the <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>.
     * @param tileManager  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> where all the tiles are stored
     * @param tileMap  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> to add the layers to.
     * @param root  The root of the given JSON file
     * @param width  The width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     * @param height  The width of echt <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     */
    private void loadInLayers(TileManager tileManager, TileMap tileMap, JsonObject root, int width, int height) {
        try {
            JsonArray jsonLayers = root.getJsonArray("layers");
            for (int i = 0; i < jsonLayers.size()-1; i++) {
                JsonObject layer = jsonLayers.getJsonObject(i);
                if (layer.getString("type").equals("tilelayer")) {
                    JsonArray tilesArray = layer.getJsonArray("data");
                    ArrayList<Tile> layerTiles = new ArrayList<>();
                    for (int q = 0; q < tilesArray.size(); q++) {
                        int tileId = tilesArray.getInt(q);
                        if (tileId != 0 && tileManager.containsTile(tileId)) {
                            layerTiles.add(tileManager.getTile(tileId));
                        } else {
                            layerTiles.add(null);
                        }
                    }
                    tileMap.addLayer(new TileLayer(tileMap, width, height, layerTiles));
                }
            }


        } catch (Exception e){
            showExceptionPopUp(e);
        }
    }


    /**
     * Loads in all the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a> from the JSON file
     * @param tileManager  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> to store the tiles to
     * @param root  he root of the given JSON file
     */
    private void loadInTilesets(TileManager tileManager, JsonObject root) {
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

/*
                //Todo: Testing possible error, ask Johan (error prob in JSON)
                //only loop through used images for efficiency
                JsonArray tiles = tileSet.getJsonArray("tiles");
                for (int q = 0; q < tiles.size(); q++) {
                    JsonObject tile = tiles.getJsonObject(q);
                    int id = tile.getInt("id");
                    //System.out.println(id);

                    //only loops through used images for efficiency
                    Tile tileObject = new Tile(tileImage.getSubimage(
                            (tileWidth + 1) * (id % collums),        //
                            (tileHeight + 1) * (id / collums),       // Voodoo magic splitting the images based on id
                            tileWidth,                                 //
                            tileHeight), id);                          //

                    tileManager.addTile(id, tileObject);

                }
*/
                //loading in all the images
                int id = 1;
                for(int y = 0; y < tileImage.getHeight(); y += tileHeight + 1)
                {
                    for(int x = 0; x < tileImage.getWidth(); x += tileWidth + 1)
                    {
                        Tile tileObject = new Tile(tileImage.getSubimage(x, y, tileWidth, tileHeight), id);
                        tileManager.addTile(id, tileObject);
                        id++;
                    }
                }
            }

        } catch (Exception e) {
            showExceptionPopUp(e);
        }
    }
}
