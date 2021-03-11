package FestivalPlanner.NPC;

import com.sun.istack.internal.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains methods for everything related to NPC instances, which can be seen in <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI/SimulatorCanvas.html">SimulatorCanvas</a>.
 */
public class NPC {
    private Point2D position;
    private final double SPEED = 1;
    private double frame;
    private Point2D target;
    private Direction direction = Direction.UP;

    private NPCState npcState = new IdleState();

    private final int npcTileX = 4;
    private final int npcTileY = 3;

    private ArrayList<BufferedImage> spritesUp = new ArrayList<>(4);
    private ArrayList<BufferedImage> spritesDown = new ArrayList<>(4);
    private ArrayList<BufferedImage> spritesLeft = new ArrayList<>(4);
    private ArrayList<BufferedImage> spritesRight = new ArrayList<>(4);

    private static String[] characterFiles = {"char_1", "char_2"};

    private int centerX;
    private int centerY;

    private final int COLLISION_RADIUS = 32;

    /**
     * Constructor for the <code>NPC</code> class.
     * <p>
     * Sets the position to the parameter's value, and sets the appearance of the NPC by cutting up the imagine designated to it.
     * @param position  changes <code>this.position</code> to the parameter's value
     * @param spriteSheet  changes the appearance of the NPC in question
     */
    public NPC(Point2D position, int spriteSheet) {
        this.position = position;
        this.target = position;
        this.frame = Math.random() * 10;

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/characters/" + characterFiles[spriteSheet] + ".png"));
            int w = image.getWidth() / npcTileX;
            int h = image.getHeight() / npcTileY;

            for(int x = 0; x < npcTileX; x++) {
                for(int y = 0; y < npcTileY; y++) {
                    if (y == 2){
                        if (x == 0){
                            spritesLeft.add(image.getSubimage(0, 0, w,h));
                            spritesLeft.add(image.getSubimage(0, y * h, w,h));
                        } else if (x == 1){
                            spritesDown.add(image.getSubimage(w, 0, w,h));
                            spritesDown.add(image.getSubimage(w, y * h, w,h));
                        } else if (x == 2){
                            spritesUp.add(image.getSubimage(2 * w, 0, w,h));
                            spritesUp.add(image.getSubimage(2 * w, y * h, w,h));
                        } else {
                            spritesRight.add(image.getSubimage(3 * w, 0, w,h));
                            spritesRight.add(image.getSubimage(3 * w, y * h, w,h));
                        }
                    } else {
                        if (x == 0){
                            spritesLeft.add(image.getSubimage(0, y * h, w,h));
                        } else if (x == 1){
                            spritesDown.add(image.getSubimage(w, y * h, w,h));
                        } else if (x == 2){
                            spritesUp.add(image.getSubimage(2 * w, y * h, w,h));
                        } else {
                            spritesRight.add(image.getSubimage(3 * w, y * h, w,h));
                        }
                    }
                }
            }

            this.centerX = spritesLeft.get(0).getWidth() / 2;
            this.centerY = spritesLeft.get(0).getHeight() / 2;
            
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Merge Development and showExceptionPopUp.
        }
    }

    public void update(ArrayList<NPC> NPCs) {
        if(target.distanceSq(position) < 32) {
            return;
        }

        Point2D oldPosition = this.position;

        this.updateDirectionToFace();
        this.walkOnAxis();

        boolean hasCollision = false;
        hasCollision = hasCollision || checkCollision(NPCs);

        if(hasCollision) {
            this.position = oldPosition;
            this.frame = 0;
        } else {
            this.frame += 0.1;
        }
    }

    /**
     * Updates this NPC' direction to the direction it should face to walk straight forward to its' target.
     */
    public void updateDirectionToFace(){
        if (this.position.getX() != this.target.getX()){
            if (this.position.getX() < this.target.getX()){
                this.direction = Direction.RIGHT;
            } else {
                this.direction = Direction.LEFT;
            }
        } else if (this.position.getY() != this.target.getY()){
            if (this.position.getY() > this.target.getY()){
                this.direction = Direction.UP;
            } else {
                this.direction = Direction.DOWN;
            }
        }
        this.debugPrint();
    }

    /**
     * Updates the NPC' position to the current one, plus or minus the speed. This is dependent on the direction this NPC is facing.
     */
    public void walkOnAxis(){
        switch(this.direction){
            case UP:
                this.position = new Point2D.Double(
                        this.position.getX(),
                        this.position.getY() - this.SPEED);
                break;
            case DOWN:
                this.position = new Point2D.Double(
                        this.position.getX(),
                        this.position.getY() + this.SPEED);
                break;
            case LEFT:
                this.position = new Point2D.Double(
                        this.position.getX() - this.SPEED,
                        this.position.getY());
                break;
            case RIGHT:
                this.position = new Point2D.Double(
                        this.position.getX() + this.SPEED,
                        this.position.getY());
        }
    }

    /**
     * Returns a boolean, which is true if this NPC hits/enters another NPC' collider.
     * @param npcs  ArrayList of NPC instances to check
     * @return  boolean, whether the NPC hit another NPC instance
     */
    public boolean checkCollision(ArrayList<NPC> npcs) {
        boolean hasCollision = false;
        for(NPC visitor : npcs) {
            if(visitor != this) {
                if(visitor.position.distanceSq(position) < COLLISION_RADIUS * COLLISION_RADIUS) {
                    hasCollision = true;
                }
            }
        }
        return hasCollision;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - centerX, position.getY() - centerY);
        tx.translate(0, 20);

        //Draws the NPC-sprite on the canvas.
        drawImage(g2d, listOfDirection(), tx);
        //Draws a circle (resembling the collider), and a line (from the current position to the destination).
        debugDraw(g2d);
    }

    /**
     * Draws the <code>NPC</code>-sprite/character on the canvas.
     * @param g2d  Graphics2D-canvas to draw on
     * @param list  ArrayList containing images of the direction's walk-cycle
     * @param tx  AffineTransform which values will be used for positioning the image
     */
    public void drawImage(Graphics2D g2d, ArrayList<BufferedImage> list, AffineTransform tx){
        g2d.drawImage(list.get(((int)Math.floor(frame) % this.npcTileY + 1)), tx, null);
    }

    /**
     * Returns an ArrayList containing the images for the direction this <code>NPC</code> is facing.
     * @return  ArrayList containing images of the NPC' direction
     */
    @Nullable
    public ArrayList<BufferedImage> listOfDirection(){
        if (this.direction == Direction.UP){
            return this.spritesUp;
        } else if (this.direction == Direction.DOWN){
            return this.spritesDown;
        } else if (this.direction == Direction.LEFT){
            return this.spritesLeft;
        } else if (this.direction == Direction.RIGHT){
            return this.spritesRight;
        }
        return null;
    }

    /**
     * Sets a target/destination for this NPC to move to.
     * <p>
     * Sets <code>this.target</code> to the parameter's value, which is a Point2D.
     * @param newTarget  Point2D to travel to
     */
    public void setTarget(Point2D newTarget) {
        this.target = newTarget;
    }

    /**
     * Returns the length of the <code>characterFiles[]</code> array.
     * @return  length of characterFiles[]
     */
    public static int getCharacterFiles(){
        return characterFiles.length;
    }

    /**
     * Draws a circle (resembling the collider), and a line (from the current position to the destination).
     * <p>
     * <b>Is only used for debugging purposes.</b>
     * @param g2d  graphics2D to draw on
     */
    private void debugDraw(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.draw(new Ellipse2D.Double(position.getX() - COLLISION_RADIUS / 2f, position.getY() + COLLISION_RADIUS / 4f, COLLISION_RADIUS, COLLISION_RADIUS));
        g2d.draw(new Line2D.Double(position, target));
    }

    /**
     * Prints <code>this.position</code> and <code>this.target</code> for both the X- and Y-axis.
     * <p>
     * <b>Is only used for debugging purposes.</b>
     */
    private void debugPrint(){
        System.out.println("PosY: " + this.position.getY());
        System.out.println("TarY: " + this.target.getY());
        System.out.println("PosX: " + this.position.getX());
        System.out.println("TarX: " + this.target.getX());
    }
}

/**
 * Contains enumerators for all the directions an NPC can have.
 */
enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}