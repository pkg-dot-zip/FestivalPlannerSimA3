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
    private double angle;
    private ArrayList<BufferedImage> sprites;
    private double speed;
    private double frame;
    private Point2D target;
    private static double rotationSpeed = 0.1;


    public NPC(Point2D position, double angle) {
        this.position = position;
        this.angle = angle;
        this.speed = 1+5 * Math.random();
        this.target = position;
        this.frame = Math.random()*10;

        this.sprites = new ArrayList<>();
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/images/npc.png"));
            int w = image.getWidth()/2;
            int h = image.getHeight()/3;
            for(int y = 0; y < 3; y++) {
                for(int x = 0; x < 2; x++) {
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

        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(), this.target.getX() - this.position.getX());
        double rotation = targetAngle - this.angle;
        while(rotation < -Math.PI) {
            rotation += 2*Math.PI;
        }
        while(rotation > Math.PI) {
            rotation -= 2*Math.PI;
        }
        double oldAngle = this.angle;
        if(rotation < -rotationSpeed) {
            this.angle -= rotationSpeed;
            } else if(rotation > rotationSpeed) {
                this.angle += rotationSpeed;
            } else {
                this.angle = targetAngle;
            }

        Point2D oldPosition = this.position;

        this.position = new Point2D.Double(
                this.position.getX() + this.speed * Math.cos(this.angle),
                this.position.getY() + this.speed * Math.sin(this.angle));
        boolean hasCollision = false;
        hasCollision = hasCollision || checkCollision(NPCs);

        if(hasCollision) {
            this.position = oldPosition;
            this.frame = 0;
            this.angle = oldAngle + rotationSpeed;
        } else {
            this.frame += 0.1;
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
        tx.rotate(angle + Math.PI/2, centerX, centerY);
        tx.translate(0, 20);


        g2d.drawImage(this.sprites.get((int)Math.floor(frame) % this.sprites.size()), tx, null);


        g2d.setColor(Color.white);
        g2d.draw(new Ellipse2D.Double(position.getX()-32, position.getY()-32, 64, 64));
        g2d.draw(new Line2D.Double(position, target));
    }

    public void setTarget(Point2D newTarget) {
        this.target = newTarget;
    }
}
