package FestivalPlanner.GUI;
import java.awt.geom.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class AgendaCanvas {

    private BorderPane mainPane;
    private Canvas canvas;
    private AffineTransform cameraTransform;

    //@TODO Needs Agenda object in constructior
    public AgendaCanvas() {
        this(1920/3f, 1080/2f);
    }

    public AgendaCanvas(double width, double height) {
        this.cameraTransform = new AffineTransform();
        this.mainPane = new BorderPane();

        this.canvas = new ResizableCanvas(this::draw, this.mainPane);
        this.canvas.setHeight(height);
        this.canvas.setWidth(width);

        this.canvas.setOnMousePressed(this::onMousePressed);
        this.canvas.setOnMouseDragged(this::onMouseDragged);

        this.mainPane.setCenter(this.canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        graphics.setTransform(this.cameraTransform);

        int sideOffset = 150;
        int topOffset = 50;

        graphics.translate(sideOffset, topOffset);

        graphics.drawLine(-sideOffset,0,(int)canvas.getWidth(), 0);
        graphics.drawLine(0,-topOffset,0, (int)canvas.getHeight());
        drawTopBar(graphics);
    }

    private void drawTopBar(FXGraphics2D graphics){
        for(int i = 0; i < 24; i++) { //Later veranderen in van begintijd tot eindtijd
            graphics.drawString(i + ".00", i * 50+10, -25);
        }
    }

    private boolean dragging = false;
    private double xpos;
    private double ypos;

    private void onMousePressed(MouseEvent e) {
        this.dragging = true;
        xpos = e.getX();
        ypos = e.getY();
        System.out.println(xpos);
    }

    private void onMouseDragged(MouseEvent e) {
//        System.out.println(xpos);
//        System.out.println("x" + (e.getX()-xpos) + " y" + (e.getY()-ypos));
        Point2D movePoint = new Point2D.Double(e.getX()-xpos, e.getY()-ypos);
        cameraTransform.translate(movePoint.getX(), movePoint.getY());

        xpos = e.getX();
        ypos = e.getY();
        draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
    }

    public Node buildAgendaCanvas() {
        return this.mainPane;
    }

}
