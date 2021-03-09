package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

public abstract class Layer {

    TileMap tileMap;

    int width;
    int height;

    public Layer(int width, int height, TileMap tileMap) {
        this.tileMap = tileMap;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void draw(FXGraphics2D g2d);
}
