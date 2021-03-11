package FestivalPlanner.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class NPC {
    private Point2D position;
    private ArrayList<BufferedImage> sprites;
    private double speed = 1;
    private double frame;
    private Point2D target;
    private Direction direction = Direction.UP;

    private NPCState npcState = new MovingState();

    private final int npcTileX = 4;
    private final int npcTileY = 3;

    public NPC(Point2D position, double angle) {
        this.position = position;
        this.target = position;
        this.frame = Math.random()*10;

        this.sprites = new ArrayList<>();
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/char_1.png"));
            int w = image.getWidth()/npcTileX;
            int h = image.getHeight()/npcTileY;
            for(int y = 0; y < npcTileY; y++) {
                for(int x = 0; x < npcTileX; x++) {
                    this.sprites.add(image.getSubimage(x * w, y * h, w,h));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(ArrayList<NPC> NPCs) {
        if(target.distanceSq(position) < 32) {
            return;
        }

//        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());

        Point2D oldPosition = this.position;

        this.checkDirectionToHeadIn();

        this.walkOnce();

        boolean hasCollision = false;
        hasCollision = hasCollision || checkCollision(NPCs);

        if(hasCollision) {
            this.position = oldPosition;
            this.frame = 0;
        } else {
            this.frame += 0.1;
        }
    }

    public void checkDirectionToHeadIn(){
        if (
//                Math.abs(this.target.getX() - this.position.getX()) <= Math.abs(this.target.getY() - this.position.getY())
//                        && !(Math.abs(this.position.getX() - this.target.getX()) <= this.speed + 2.00)
                 this.position.getX() != this.target.getX()
        ){
            System.out.println("1");
            if (this.position.getX() < this.target.getX()){
                this.direction = Direction.RIGHT;
            } else {
                this.direction = Direction.LEFT;
            }
        } else if (
                this.position.getY() != this.target.getY()
//                !(Math.abs(this.position.getY() - this.target.getY()) <= this.speed + 2.00)
        ){
            System.out.println("2");
            if (this.position.getY() > this.target.getY()){
                this.direction = Direction.UP;
            } else {
                this.direction = Direction.DOWN;
            }
        }
        System.out.println("PosY: " + this.position.getY());
        System.out.println("TarY: " + this.target.getY());
        System.out.println("PosX: " + this.position.getX());
        System.out.println("TarX: " + this.target.getX());
//        System.out.println(direction);

    }

    public void walkOnce(){
        switch(this.direction){
            case UP:
                this.position = new Point2D.Double(
                        this.position.getX(),
                        this.position.getY() - this.speed);
                break;
            case DOWN:
                this.position = new Point2D.Double(
                        this.position.getX(),
                        this.position.getY() + this.speed);
                break;
            case LEFT:
                this.position = new Point2D.Double(
                        this.position.getX() - speed,
                        this.position.getY());
                break;
            case RIGHT:
                this.position = new Point2D.Double(
                        this.position.getX() + speed,
                        this.position.getY());
        }
    }

    public boolean checkCollision(ArrayList<NPC> visitors) {
        boolean hasCollision = false;
        for(NPC visitor : visitors) {
            if(visitor != this) {
                if(visitor.position.distanceSq(position) < 64*64) {
                    hasCollision = true;
                }
            }
        }
        return hasCollision;
    }

    public void draw(Graphics2D g2d) {
        int centerX = sprites.get(0).getWidth()/2;
        int centerY = sprites.get(0).getHeight()/2;
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY() - centerY);
//        tx.rotate(angle, centerX, centerY);
        tx.translate(0, 20);


        g2d.drawImage(this.sprites.get((int)Math.floor(frame) % this.sprites.size()), tx, null);


        g2d.setColor(Color.white);
        g2d.draw(new Ellipse2D.Double(position.getX()-32, position.getY()-8, 64, 64));
        g2d.draw(new Line2D.Double(position, target));
    }

    public void setTarget(Point2D newTarget) {
        this.target = newTarget;
    }
}

enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}