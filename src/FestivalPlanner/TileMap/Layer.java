package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

//Todo: Layer
public class Layer {

    private TileMap tileMap;

    private int width;
    private int height;

    private Tile[][] tiles;

    public Layer(TileMap tileMap, int width, int height, ArrayList<Tile> tiles) {
        this.tileMap = tileMap;
        this.width = width;
        this.height = height;
        this.tiles = buildTiles(tiles);
    }

    //Todo: needs to implement tiles
    private Tile[][] buildTiles(ArrayList<Tile> tiles) {
        return new Tile[this.height][this.width];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    //Todo: implement body
    public void draw(FXGraphics2D g2d) {

    }

}
