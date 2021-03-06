package FestivalPlanner.TileMap;

import org.jfree.fx.FXGraphics2D;
import java.awt.image.*;

//Todo: needs documentation
public class Tile {

    private BufferedImage image;
    private int key;

    public Tile(BufferedImage image, int key) {
        this.image = image;
        this.key = key;
    }

    public void draw(FXGraphics2D g2d, int x, int y){
        g2d.drawImage(this.image, x, y, null);
    }

    public int getKey() {
        return this.key;
    }
}
