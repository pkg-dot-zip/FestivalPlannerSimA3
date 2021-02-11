package FestivalPlanner.GUI;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

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
        this.mainPane.setCenter(this.canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.drawLine(100,100,1000,1000);
    }

    public Node buildAgendaCanvas() {
        return this.mainPane;
    }

}
