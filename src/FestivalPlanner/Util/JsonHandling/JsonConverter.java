package FestivalPlanner.Util.JsonHandling;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.TileMap.*;
import com.sun.istack.internal.NotNull;
import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Contains methods for converting <i>.JSON</i> files into usable TileMaps for our software.
 */
public class JsonConverter extends AbstractDialogPopUp {

    /**
     * Reads the <i>.JSON</i> file and turns it into
     * a <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> based on the given directory / parameter's value.
     * @param fileName  directory of the <i>.JSON</i> file
     * @return  <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> created from the JSON file
     */
    public TileMap JSONToTileMap(String fileName) {
        TileManager tileManager = new TileManager();

        try (InputStream inputStream = getClass().getResourceAsStream(fileName);) {

            //Init.
            JsonReader reader = Json.createReader(inputStream);
            JsonObject root = reader.readObject();

            //Reading values.
            int width = root.getInt("width");
            int height = root.getInt("height");

            int tileWidth = root.getInt("tilewidth");
            int tileHeight = root.getInt("tileheight");

            //Creating the new TileMap based on the values above.
            TileMap tileMap = new TileMap(width, height, tileWidth, tileHeight, tileManager);

            //Loading in the tile-sets.
            loadInTilesets(tileManager, root);

            //Loading in the layers.
            loadInLayers(tileManager, tileMap, root, width, height);

            return tileMap;
        } catch (Exception e) {
            showExceptionPopUp(e);
        } finally {
            return null;
        }
    }

    /**
     * Reads the <i>.JSON</i> file and adds all
     * the <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>s to
     * the <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>.
     * @param tileManager  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> where all the tiles are stored
     * @param tileMap  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> to add the layers to.
     * @param root  the root of the given <i>.JSON</i> file
     * @param width  the width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     * @param height  the width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     */
    private void loadInLayers(TileManager tileManager, TileMap tileMap, JsonObject root, int width, int height) {
        try {
            JsonArray jsonLayers = root.getJsonArray("layers");

            //Loop through layers.
            for (int i = 0; i < jsonLayers.size(); i++) {
                JsonObject layer = jsonLayers.getJsonObject(i);

                String type = layer.getString("type");
                if (type.equals("tilelayer")) {

                    //Add TileLayer to map.
                    tileMap.addLayer(buildTileLayer(tileMap, width, height, layer, tileManager));

                } else if (type.equals("objectgroup")) {

                    //Add ObjectLayer to map.
                    tileMap.addLayer(buildObjectLayer(tileMap, width, height, layer));
                }
            }
        } catch (Exception e) {
            showExceptionPopUp(e);
        }
    }

    /**
     * Builds a new <a href="{@docRoot}/FestivalPlanner/TileMap/ObjectLayer.html">ObjectLayer</a> based on the given <i>.JSON</i> Layer.
     * @param tileMap  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> to add the layers to
     * @param width  the width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     * @param height  the width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     * @param layer  the Json Layer to convert to <a href="{@docRoot}/FestivalPlanner/TileMap/ObjectLayer.html">ObjectLayer</a>
     * @return  the build <a href="{@docRoot}/FestivalPlanner/TileMap/ObjectLayer.html">ObjectLayer</a>
     */
    @NotNull
    private ObjectLayer buildObjectLayer(TileMap tileMap, int width, int height, JsonObject layer) {
        ObjectLayer objectLayer = new ObjectLayer(tileMap, width, height);

        JsonArray objectsArray = layer.getJsonArray("objects");
        for (int q = 0; q < objectsArray.size(); q++) {
            JsonObject jsonObject = objectsArray.getJsonObject(q);

            objectLayer.addTileObject(new TileObject(
                    jsonObject.getString("name"),
                    jsonObject.getString("type"),
                    new Point2D.Double(jsonObject.getInt("x"), jsonObject.getInt("y")),
                    jsonObject.getInt("width"),
                    jsonObject.getInt("height"),
                    jsonObject.getInt("rotation"),
                    jsonObject.getJsonArray("properties").getJsonObject(0).getString("value")
            ));
        }
        return objectLayer;
    }

    /**
     * Builds a new <a href="{@docRoot}/FestivalPlanner/TileMap/TileLayer.html">TileLayer</a> based on the given <i>.JSON</i> Layer.
     * @param tileMap  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> to add the layers to
     * @param width  the width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     * @param height  the width of each <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layer</a>
     * @param layer  the Json Layer to convert to <a href="{@docRoot}/FestivalPlanner/TileMap/ObjectLayer.html">ObjectLayer</a>
     * @param tileManager  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> where all the tiles are stored
     * @return  the build <a href="{@docRoot}/FestivalPlanner/TileMap/TileLayer.html">TileLayer</a>
     */
    @NotNull
    private TileLayer buildTileLayer(TileMap tileMap, int width, int height, JsonObject layer, TileManager tileManager) {

        JsonArray tilesArray = layer.getJsonArray("data");
        String layerName = layer.getString("name");
        ArrayList<Tile> layerTiles = new ArrayList<>();

        //Loop though tiles to see if they are stored.
        for (int q = 0; q < tilesArray.size(); q++) {
            int tileId = tilesArray.getInt(q);
            if (tileId != 0 && tileManager.containsTile(tileId)) {
                layerTiles.add(tileManager.getTile(tileId));
            } else {
                layerTiles.add(null);
            }
        }

        TileLayer tileLayer = new TileLayer(tileMap, width, height, layerTiles);
        if (layerName.equals("PathLayer")) {
            tileMap.setPathFindingLayer(tileLayer);
            System.out.println(tileMap.getLayers().size());
        }
        return tileLayer;
    }


    /**
     * Loads in all the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a> from the <i>.JSON</i> file.
     * @param tileManager  the <a href="{@docRoot}/FestivalPlanner/TileMap/TileManager.html">TileManager</a> to store the tiles to
     * @param root  the root of the given <i>.JSON</i> file
     */
    private void loadInTilesets(TileManager tileManager, JsonObject root) {
        try {

            JsonArray tileSets = root.getJsonArray("tilesets");

            //Loops through all tileSets.
            for (int i = 0; i < tileSets.size(); i++) {
                JsonObject tileSet = tileSets.getJsonObject(i);

                int tileWidth = tileSet.getInt("tilewidth");
                int tileHeight = tileSet.getInt("tileheight");
                int collums = tileSet.getInt("columns"); //TODO: Remove this if this will never be used.

                //Loading image in tileSet.
                BufferedImage tileImage = ImageIO.read(getClass().getResourceAsStream(tileSet.getString("image")));

                //Loading in all the images.
                int id = 1;
                for (int y = 0; y < tileImage.getHeight(); y += tileHeight + 1) {
                    for (int x = 0; x < tileImage.getWidth(); x += tileWidth + 1) {
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
