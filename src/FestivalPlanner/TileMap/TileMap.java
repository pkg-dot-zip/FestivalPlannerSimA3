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

    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, ArrayList<Layer> layers, TileManager tileManager) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.layers = layers;
        this.tileManager = tileManager;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public void draw(FXGraphics2D g2d){
        for (Layer layer : this.layers) {
            layer.draw(g2d);
        }
    }
}
