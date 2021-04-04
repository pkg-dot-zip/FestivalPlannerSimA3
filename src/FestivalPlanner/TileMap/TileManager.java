package FestivalPlanner.TileMap;

import java.util.HashMap;
import java.util.Set;

/**
 * Contains methods to store <b>one</b> instance of each tile to prevent duplicates.
 */
public class TileManager {

    private HashMap<Integer, Tile> tiles;

    /**
     * Empty constructor for TileManager.
     */
    public TileManager() {
        this(new HashMap<>());
    }

    /**
     * Constructor for TileManager.
     * @param tiles  HashMap that stores all the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tiles</a> and their corresponding id
     */
    public TileManager(HashMap<Integer, Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Returns the valueSet of <code>this.tiles</code>.
     * @return  all the tiles in <code>this.tiles</code>
     */
    public Set getTiles() {
        return (Set) this.tiles.values();
    }

    /**
     * Returns true if the given id is in <code>this.tiles</code>.
     * @param id  the id to search for
     * @return  true if <code>this.tiles</code> contains the parameter's value
     */
    public boolean containsTile(int id) {
        return this.tiles.containsKey(id);
    }

    /**
     * Returns <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> with given id.
     * @param id  the Tile id to check for
     * @return  <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> corresponding to id
     */
    public Tile getTile(int id) {
        return this.tiles.get(id);
    }

    /**
     * Adds a <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> to <code>this.tiles</code>.
     * @param id  the id of the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a>
     * @param tile  the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> to be added
     */
    public void addTile(int id, Tile tile) {
        this.tiles.put(id, tile);
    }

    /**
     * Removes the <a href="{@docRoot}/FestivalPlanner/TileMap/Tile.html">Tile</a> with the given id.
     * @param id  the id to remove
     */
    public void removeTile(int id) {
        this.tiles.remove(id);
    }
}
