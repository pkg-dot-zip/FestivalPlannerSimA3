package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;

/**
 * Abstract class
 * <p>
 * Represents one Layer in <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a>
 */
public abstract class Layer {

    TileMap tileMap;

    int width;
    int height;

    /**
     * Constructor for Layer
     * @param width  The width of the Layer
     * @param height  The height of the layer
     * @param tileMap  The <a href="{@docRoot}/FestivalPlanner/TileMap/TileMap.html">TileMap</a> that holds this layer
     */
    public Layer(int width, int height, TileMap tileMap) {
        this.tileMap = tileMap;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter for <code>this.width</code>
     * @return  <code>this.width</code>
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter for <code>this.width</code>
     * @param width  sets <code>this.width</code>
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for <code>this.height</code>
     * @return  <code>this.height</code>
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for <code>this.height</code>
     * @param height  sets <code>this.height</code>
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Abstract method
     * <p>
     * Draws the complete TileMap to the given graphics, always starts at point 0.0
     * @param g2d  Object to draw on
     */
    public abstract void draw(FXGraphics2D g2d);
}
