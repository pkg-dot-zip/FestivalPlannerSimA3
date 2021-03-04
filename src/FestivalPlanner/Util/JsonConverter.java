package FestivalPlanner.Util;

import FestivalPlanner.TileMap.Layer;
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
import java.util.ArrayList;

public class JsonConverter {

    public JsonConverter() {
    }

    public TileMap JSONToTileMap(String fileName) {
        TileManager tileManager = new TileManager();
        ArrayList<Layer> layers = new ArrayList<>();

        InputStream inputStream = getClass().getResourceAsStream(fileName);
        JsonReader reader = Json.createReader(inputStream);
        JsonObject root = reader.readObject();

        int width = root.getInt("width");
        int height = root.getInt("height");

        //loading in Tilesets
        loadInTilesets(tileManager, root);

        //Loading in Layers
        loadInLayers(tileManager, layers, root, width, height);

        //Todo: load tilewWidth
        TileMap tileMap = new TileMap(width, height, 16, 16, layers, tileManager);
        return tileMap;
    }

    private void loadInLayers(TileManager tileManager, ArrayList<Layer> layers, JsonObject root, int width, int height) {
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
                    layers.add(new Layer(width, height, layerTiles));
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }


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

//                //Todo: Testing possible error, ask Johan
//                //only loop through used images for efficiency
//                JsonArray tiles = tileSet.getJsonArray("tiles");
//                for (int q = 0; q < tiles.size(); q++) {
//                    JsonObject tile = tiles.getJsonObject(q);
//                    int id = tile.getInt("id");
//                    //System.out.println(id);
//
//                    //only loops through used images for efficiency
//                    Tile tileObject = new Tile(tileImage.getSubimage(
//                            (tileWidth + 1) * (id % collums),        //
//                            (tileHeight + 1) * (id / collums),       // Voodoo magic splitting the images based on id
//                            tileWidth,                                 //
//                            tileHeight), id);                          //
//
//                    tileManager.addTile(id, tileObject);
//
//                }


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

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

}
