package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;
import java.util.ArrayList;

//Todo: Layer
public class Layer {

    private TileMap tileMap;

    private int width;
    private int height;

    private Tile[][] tiles;

    public Layer(int width, int height, ArrayList<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.tiles = buildTiles(tiles);
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    //Todo: needs to implement tiles
    private Tile[][] buildTiles(ArrayList<Tile> tiles) {
        Tile[][] tilesArr = new Tile[this.height][this.width];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                tilesArr[y][x] = tiles.get(y * this.width + x);
            }
        }

        return tilesArr;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    //Todo: implement body
    public void draw(FXGraphics2D g2d) {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x] == null) {
                    continue;
                }
                tiles[y][x].draw(g2d, x * 16,y * 16);
            }
        }

    }

}
