package FestivalPlanner.TileMap;

import java.util.HashMap;
import java.util.Set;

/**
 * Class store one instance of each tile to prevent duplicates
 */
public class TileManager {

    private HashMap<Integer, Tile> tiles;

    /**
     * Empty constructor for TileManager
     */
    public TileManager() {
        this(new HashMap<>());
    }

    /**
     * Construct for TileManager
     * @param tiles  HashMap that stores all the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a> and there corresponding id
     */
    public TileManager(HashMap<Integer, Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Getter for the valuSet of <code>this.tiles</code>
     * @return  All the tiles in <code>this.tiles</code>
     */
    public Set getTiles() {
        return (Set) this.tiles.values();
    }

    /**
     * Checks if given id is in <code>this.tiles</code>
     * @param id  The id to check for
     * @return  true if <code>this.tiles</code> contains that id
     */
    public boolean containsTile(int id) {
        return this.tiles.containsKey(id);
    }

    /**
     * Returns <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> with given id
     * @param id  The Tile id to check for
     * @return  <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> corresponding to id
     */
    public Tile getTile(int id) {
        return this.tiles.get(id);
    }

    /**
     * Adds a <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> to <code>this.tiles</code>
     * @param id  The id of the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tile  The <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> to be added
     */
    public void addTile(int id, Tile tile) {
        this.tiles.put(id, tile);
    }

    /**
     * Removes the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> with the given id.
     * @param id  The id to remove
     */
    public void removeTile(int id) {
        this.tiles.remove(id);
    }

}
