package FestivalPlanner.NPC;

import FestivalPlanner.Logic.SimulatorObject;
import FestivalPlanner.Util.ImageLoader;
import FestivalPlanner.Util.MathHandling.NPCMathHandler;
import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Contains methods for everything related to NPC instances, which can be seen in <a href="{@docRoot}/FestivalPlanner/GUI/SimulatorGUI/SimulatorCanvas.html">SimulatorCanvas</a>.
 */
public class NPC {

    //Movement.
    private NPCState npcState = new MovingState();
    private Direction direction = Direction.UP;
    private Point2D position;
    private Point2D target;
    private SimulatorObject targetObject;
    private final double SPEED = 1.0 / (60*4);
    private double gameSpeed;

    //Images.
    private static String[] characterFiles = {"char_1", "char_2"};
    private double frame;

    private final int npcTileX = 4;
    private final int npcTileY = 3;

    private ArrayList<BufferedImage> spritesUp;
    private ArrayList<BufferedImage> spritesDown;
    private ArrayList<BufferedImage> spritesLeft;
    private ArrayList<BufferedImage> spritesRight;

    //Collision.
    private final int COLLISION_RADIUS = 20;
    private int centerX;
    private int centerY;
    private boolean isSeparating = false;

    /**
     * Constructor for the <code>NPC</code> class.
     * <p>
     * Sets the position to the parameter's value, and sets the appearance of the <b>NPC</b> by cutting up the imagine designated to it.
     * @param position  changes <code>this.position</code> to the parameter's value
     * @param spriteSheet  changes the appearance of the NPC in question
     */
    public NPC(Point2D position, int spriteSheet) {
        this.targetObject = null;
        this.position = position;
        this.target = position;
        this.frame = Math.random() * 10;

        this.gameSpeed = (60 * 5);
        ArrayList<ArrayList<BufferedImage>> spriteSheets = ImageLoader.getLists(spriteSheet);
        this.spritesUp = spriteSheets.get(2);
        this.spritesDown = spriteSheets.get(1);
        this.spritesLeft = spriteSheets.get(0);
        this.spritesRight = spriteSheets.get(3);

        this.centerX = (spritesLeft.get(0).getWidth() / 2);
        this.centerY = (spritesLeft.get(0).getHeight() / 2) - 10;
    }

    public NPC(Point2D position, ArrayList<ArrayList<BufferedImage>> spriteSheets) {
        this.targetObject = null;
        this.position = position;
        this.target = position;
        this.frame = Math.random() * 10;

        this.gameSpeed = (60 * 5);
        this.spritesUp = spriteSheets.get(2);
        this.spritesDown = spriteSheets.get(1);
        this.spritesLeft = spriteSheets.get(0);
        this.spritesRight = spriteSheets.get(3);

        this.centerX = (spritesLeft.get(0).getWidth() / 2);
        this.centerY = (spritesLeft.get(0).getHeight() / 2) - 10;
    }

    public void update(ArrayList<NPC> NPCs) {
        if (this.targetObject != null) {
            this.target = this.targetObject.getNextDirection(this.position);
        }

        if (this.targetObject != null) {
            if ((targetObject.isWithin(this.position)) &&
                    !this.npcState.getClass().equals(ViewingState.class)) {
                this.npcState = new ViewingState();
            }if (this.target.distanceSq(position) > 2) {
                this.npcState = new MovingState();
            }
        } else if (this.target.distanceSq(position) > 2) {
            this.npcState = new MovingState();
        } else {
            this.isSeparating = false;
            this.npcState = new IdleState();
        }

        Point2D oldPosition = this.position;

        this.npcState.handle(this);

        boolean hasCollision = false;
        hasCollision = hasCollision || checkCollision(NPCs);

        if (this.npcState.getClass().equals(MovingState.class)) {
            this.frame += Math.random() * 0.07;
        } else if(hasCollision && !this.isSeparating) {
            this.position = oldPosition;
            this.frame = 1;

            for (NPC npc : NPCs) {
                if (npc.isSeparating()) {
                    return;
                }
            }

            separateNPC(NPCs);
        } else {
            this.frame = 1;
        }
    }

    /**
     * Moves the <b>NPC</b> away from the others to make sure no collisions are happening.
     * <p>
     * It does this one by one; there can never be two <b>NPC</b>s moving at the same time.
     * In order to do this we check the boolean isSeparating for every <b>NPC</b> in the list given as a parameter.
     * @param npcs  list of <b>NPC</b> to check
     */
    private void separateNPC(ArrayList<NPC> npcs) {
        this.isSeparating = true;

        //Checks whether other NPCs are separating, and if so wait until that NPC is done.
        for (NPC npc : npcs){
            if (npc.isSeparating() && npc != this){
                return;
            }
        }

        //Calculating target to move to.
        int xToMove = NPCMathHandler.separateRandomNumber(COLLISION_RADIUS);
        int yToMove = NPCMathHandler.separateRandomNumber(COLLISION_RADIUS);

        if (xToMove != 0){
           xToMove += npcTileX;
        }
        if (yToMove != 0){
            yToMove += npcTileY;
        }

        //Setting target to move to.
        this.target = new Point2D.Double(this.target.getX() + xToMove, this.target.getY() + yToMove);
    }

    /**
     * Updates this <b>NPC</b>' direction to the direction it should face to walk straight forward to its' target.
     */
    void updateDirectionToFace(){
        if ((int)this.position.getX() != (int)this.target.getX()) {
            if (this.position.getX() < this.target.getX()) {
                this.direction = Direction.RIGHT;
            } else {
                this.direction = Direction.LEFT;
            }
        } else if ((int)this.position.getY() != (int)this.target.getY()) {
            if (this.position.getY() > this.target.getY()) {
                this.direction = Direction.UP;
            } else {
                this.direction = Direction.DOWN;
            }
        }
    }

    /**
     * Updates the <b>NPC</b>' position to the current one, plus or minus the speed. This is dependent on the direction this <b>NPC</b> is facing.
     */
    public void walkOnAxis(){
        switch(this.direction){
            case UP:
                this.position = new Point2D.Double(
                        this.position.getX(),
                        this.position.getY() - (this.SPEED * this.gameSpeed));
                break;
            case DOWN:
                this.position = new Point2D.Double(
                        this.position.getX(),
                        this.position.getY() + (this.SPEED * this.gameSpeed));
                break;
            case LEFT:
                this.position = new Point2D.Double(
                        this.position.getX() - (this.SPEED * this.gameSpeed),
                        this.position.getY());
                break;
            case RIGHT:
                this.position = new Point2D.Double(
                        this.position.getX() + (this.SPEED * this.gameSpeed),
                        this.position.getY());
        }
    }

    /**
     * Returns a boolean, which is true if this <b>NPC</b> hits/enters another <b>NPC</b>' collider.
     * @param npcs  ArrayList of <b>NPC</b> instances to check
     * @return  boolean, whether the <b>NPC</b> hit another <b>NPC</b> instance
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
        tx.translate(position.getX() - centerX, position.getY() - centerY - 25);
        tx.translate(0, 0);

        //Draws the NPC-sprite on the canvas.
        drawImage(g2d, listOfDirection(), tx);
        //Draws a circle (resembling the collider), and a line (from the current position to the destination).
        debugDraw(g2d);
    }

    /**
     * Draws the <b>NPC</b>-sprite/character on the canvas.
     * @param g2d  Graphics2D-canvas to draw on
     * @param list  ArrayList containing images of the direction's walk-cycle
     * @param tx  AffineTransform which values will be used for positioning the image
     */
    private void drawImage(Graphics2D g2d, ArrayList<BufferedImage> list, AffineTransform tx){
        g2d.drawImage(list.get(((int)Math.floor(frame) % this.npcTileY + 1)), tx, null);
    }

    /**
     * Returns an ArrayList containing the images for the direction this <b>NPC</b> is facing.
     * @return  ArrayList containing images of the <b>NPC</b>' direction
     */
    @Nullable
    private ArrayList<BufferedImage> listOfDirection(){
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
     * Sets a target/destination for this <b>NPC</b> to move to.
     * <p>
     * Sets <code>this.target</code> to the parameter's value, which is an instance of Point2D.
     * @param newTarget  Point2D to travel to
     */
    public void setTarget(Point2D newTarget) {
        this.target = newTarget;
    }

    /**
     * Getter for <code>this.TargetObject</code>.
     * @return  <code>this.TargetObject</code>
     */
    public SimulatorObject getTargetObject() {
        return targetObject;
    }

    /**
     * Setter for <code>this.TargetObject</code>.
     * @param targetObject  the object to set <code>this.TargetObject</code> to
     */
    public void setTargetObject(SimulatorObject targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Returns the length of the <code>characterFiles[]</code> array.
     * @return  length of characterFiles[]
     */
    public static int getCharacterFiles(){
        return characterFiles.length;
    }

    /**
     * Getter for <code>this.gameSpeed</code>.
     * @param gameSpeed  <code>this.gameSpeed</code>
     */
    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    /**
     * Sets <code>this.direction</code>.
     * @param direction  <code>this.direction</code>
     */
    void setDirection(Direction direction){
        this.direction = direction;
    }

    /**
     * Returns <code>this.direction</code>.
     * @return  current <code>Direction</code> of the <code>NPC</code>
     */
    Direction getDirection(){
        return this.direction;
    }

    /**
     * Returns <code>this.target</code>.
     * @return  <code>this.target</code>
     */
    public Point2D getTarget(){
        return this.target;
    }

    /**
     * Returns <code>this.position</code>.
     * @return <code>this.position</code>
     */
    public Point2D getPosition(){
        return this.position;
    }

    /**
     * Returns <code>this.isSeparating</code>.
     * @return  <code>this.isSeparating</code>
     */
    private boolean isSeparating() {
        return this.isSeparating;
    }

    /**
     * Draws a circle (resembling the collider), and a line (from the current position to the destination).
     * <p>
     * <b>Is only used for debugging purposes.</b>
     * @param g2d  graphics2D to draw on
     */
    private void debugDraw(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.draw(new Ellipse2D.Double(position.getX() - COLLISION_RADIUS / 2f, position.getY() + COLLISION_RADIUS / 4f -25 , COLLISION_RADIUS, COLLISION_RADIUS));
        g2d.draw(new Line2D.Double(new Point2D.Double(this.position.getX(), this.position.getY() - (double) this.centerY*2), new Point2D.Double(this.target.getX() + 0.01, this.target.getY() + 0.01)));

        if(this.targetObject != null) {
            this.targetObject.debugDraw(g2d);
        }
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