package FestivalPlanner.TileMap;

import java.util.HashMap;
import java.util.Set;

//Todo: needs documentation
public class TileManager {

    private HashMap<Integer, Tile> tiles;

    public TileManager() {
        this.tiles = new HashMap<>();
    }

    public Set getTiles() {
        return (Set) this.tiles.values();
    }

    public Tile getTile(int id) {
        return this.tiles.get(id);
    }

    public void addTile(int id, Tile tile) {
        this.tiles.put(id, tile);
    }

    public void removeTile(int id) {
        this.tiles.remove(id);
    }

}
