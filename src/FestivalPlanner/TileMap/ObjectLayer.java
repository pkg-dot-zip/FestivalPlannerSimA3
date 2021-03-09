package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

public class ObjectLayer extends Layer{

    private String name;

    private ArrayList<TileObject> tileObjects;

    public ObjectLayer(int width, int height, TileMap tileMap) {
        this(tileMap, width, height, "", new ArrayList<>());
    }

    public ObjectLayer(int width, int height, TileMap tileMap, String name) {
        this(tileMap, width, height, name, new ArrayList<>());
    }

    public ObjectLayer(TileMap tileMap, int width, int height, String name, ArrayList<TileObject> objects) {
        super(width, height, tileMap);
        this.name = name;
        this.tileObjects = objects;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TileObject> getTileObjects() {
        return tileObjects;
    }

    public void setTileObjects(ArrayList<TileObject> tileObjects) {
        this.tileObjects = tileObjects;
    }

    public void addTileObject(TileObject tileObject) {
        this.tileObjects.add(tileObject);
    }

    @Override
    public void draw(FXGraphics2D g2d) {

    }
}
