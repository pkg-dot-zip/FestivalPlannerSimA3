package FestivalPlanner.Logic;

import FestivalPlanner.NPC.NPC;
import FestivalPlanner.TileMap.*;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class SimulatorHandler {

    private ArrayList<NPC> npcList;
    private ArrayList<SimulatorObject> simulatorObjects;
    private TileMap tileMap;


    /**
     * Empty constructor for SimulatorHandler.
     */
    public SimulatorHandler() {
        //Todo: remember to remove when loading maps is implemented
        this(new JsonConverter().JSONToTileMap("/testMap.json"));
    }

    /**
     * Constructor for SimulatorHandler.
     * @param tileMap  The TileMap to set <code>this.tileMap</code> to
     */
    public SimulatorHandler(TileMap tileMap) {
        this(new ArrayList<>(40), tileMap);
        this.simulatorObjects = generateObjects();
        generateNPC();
    }

    private ArrayList<SimulatorObject> generateObjects() {
        ArrayList<SimulatorObject> objects = new ArrayList<>();
        for (Layer layer : this.tileMap.getLayers()) {
            if (layer instanceof ObjectLayer) {
                for (TileObject tileObject : ((ObjectLayer) layer).getTileObjects()) {
                    if (tileObject.getType().equals("Podium")) {
                        objects.add(new SimulatorPodium(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), this.tileMap.getPathFindingLayer()));
                    }
                }

            }
        }
        return objects;
    }

    /**
     * Top constructor for SimulatorHandler
     * @param npcList  List of NPC to set <code>this.npcList</code> to
     * @param tileMap  The TileMap to set <code>this.tileMap</code> to
     */
    public SimulatorHandler(ArrayList<NPC> npcList, TileMap tileMap) {
        this.npcList = npcList;
        this.tileMap = tileMap;
    }

    //Todo: temporary
    public void generateNPC(){
        Random r = new Random(0);
        while(this.npcList.size() < 10)
        {
          NPC npc = new NPC(new Point2D.Double((int)(Math.random() * 1000), (int)(Math.random() * 1000)), r.nextInt(NPC.getCharacterFiles()));
//            NPC npc = new NPC(new Point2D.Double(500, 500, r.nextInt(NPC.getCharacterFiles())));
            npc.setTargetObject(this.simulatorObjects.get(1));
            if(!npc.checkCollision(this.npcList))
            {
                this.npcList.add(npc);
            }
        }
    }

    /**
     * Draws <code>this.tileMap</code>, all the NPC's and the objects to the given screen.
     * @param g2d  The object to draw to
     */
    public void draw(FXGraphics2D g2d) {
        this.tileMap.draw(g2d);
        for (NPC npc : this.npcList) {
            npc.draw(g2d);
        }

        for (SimulatorObject object : this.simulatorObjects) {
            object.draw(g2d);
        }
    }

    private double debugTime = 0.0;

    /**
     * Updates all the NPC's and the objects to the given screen.
     * @param deltaTime  The time it took sinds last update
     */
    public void update(double deltaTime) {
        for (NPC npc : this.npcList) {
            npc.update(this.npcList);
        }

        debugTime += deltaTime;
        if(debugTime > 15) {
            SimulatorObject object = this.simulatorObjects.get((int)(Math.random() * this.simulatorObjects.size()));
            System.out.println(object);
            for (NPC npc : this.npcList) {
                npc.setTargetObject(object);
            }
            debugTime = 0;
        }
    }

    /**
     * Getter for <code>this.npcList</code>
     * @return  <code>this.npcList</code>
     */
    public ArrayList<NPC> getNpcList() {
        return npcList;
    }

    /**
     * Setter for <code>this.npcList</code>
     * @param npcList  <code>this.npcList</code>
     */
    public void setNpcList(ArrayList<NPC> npcList) {
        this.npcList = npcList;
    }

    /**
     * Getter for the <code>this.tileMap</code>
     * @return  <code>this.tileMap</code>
     */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * Setter for the <code>this.tileMap</code>
     * @param tileMap  <code>this.tileMap</code>
     */
    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
}
