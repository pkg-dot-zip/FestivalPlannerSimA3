package FestivalPlanner.GUI;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class AgendaCanvas {

    private BorderPane mainPane;
    private Canvas canvas;

    //@TODO Needs Agenda object in constructior
    public AgendaCanvas() {
        this(1920/3f, 1080/2f);
    }

    public AgendaCanvas(double width, double height) {
        this.mainPane = new BorderPane();
        this.canvas = new ResizableCanvas(g -> draw(g), this.mainPane);
        this.canvas.setHeight(height);
        this.canvas.setWidth(width);
        this.mainPane.setCenter(this.canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        int sideFraction = 8;
        int topFraction = 12;

        graphics.translate((canvas.getWidth()/ sideFraction), (canvas.getHeight()/ topFraction));

        graphics.drawLine(0,0,(int)canvas.getWidth(), 0);
        graphics.drawLine(0,0,0, (int)canvas.getHeight());
    }

    public Node buildAgendaCanvas() {
        return this.mainPane;
    }

}
