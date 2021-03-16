package FestivalPlanner.Logic;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SimulatorPodium extends SimulatorObject{

    public SimulatorPodium(Point2D location, int width, int height) {
        super(location, width, height);
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        g2d.draw(new Rectangle2D.Double(location.getX(), location.getY(), width, height));
    }

    @Override
    public void update(double deltaTime) {

    }
}
