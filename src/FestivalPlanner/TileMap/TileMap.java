package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;
import java.util.ArrayList;

//Todo: needs documentation
public class TileMap {

    private int mapWidth;
    private int mapHeight;

    private int tileWidth;
    private int tileHeight;

    private ArrayList<Layer> layers;
    private TileManager tileManager;

    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, TileManager tileManager) {
        this(mapWidth, mapHeight, tileWidth, tileHeight, new ArrayList<>(), tileManager);
    }

    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, ArrayList<Layer> layers, TileManager tileManager) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.layers = layers;
        this.tileManager = tileManager;
    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight() {
        return this.mapHeight;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public ArrayList<Layer> getLayers() {
        return this.layers;
    }

    public TileManager getTileManager() {
        return this.tileManager;
    }

    public void setLayers(ArrayList<Layer> layers) {
        this.layers = layers;
    }

    public void addLayer(Layer layer) {
        this.layers.add(layer);
    }

    public void draw(FXGraphics2D g2d){
        for (Layer layer : this.layers) {
            layer.draw(g2d);
        }
    }
}
