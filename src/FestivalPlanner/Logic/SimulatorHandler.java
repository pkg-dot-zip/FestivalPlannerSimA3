package FestivalPlanner.Logic;

import FestivalPlanner.Agenda.Agenda;
import FestivalPlanner.NPC.NPC;
import FestivalPlanner.TileMap.*;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SimulatorHandler {

    private ArrayList<NPC> npcList;
    private ArrayList<SimulatorObject> simulatorObjects;
    private TileMap tileMap;

    private Agenda agenda;

    private LocalTime time;
    private double speed; //value in game second per real second (s/s)

    private SimulatorObject spawn;
    private final int NUMBEER_OF_NPCS = 10;


    /**
     * Empty constructor for SimulatorHandler.
     */
    public SimulatorHandler() {
        this(new Agenda());
    }

    public SimulatorHandler(Agenda agenda) {
        //Todo: remember to remove when loading maps is implemented
        this(agenda, new JsonConverter().JSONToTileMap("/testMap.json"));
    }

    /**
     * Constructor for SimulatorHandler.
     *
     * @param tileMap The TileMap to set <code>this.tileMap</code> to
     */
    public SimulatorHandler(Agenda agenda, TileMap tileMap) {
        this(agenda, new ArrayList<>(), tileMap);
    }

    /**
     * Top constructor for SimulatorHandler
     *
     * @param npcList List of NPC to set <code>this.npcList</code> to
     * @param tileMap The TileMap to set <code>this.tileMap</code> to
     */
    public SimulatorHandler(Agenda agenda, ArrayList<NPC> npcList, TileMap tileMap) {
        this.npcList = npcList;
        this.tileMap = tileMap;

        this.simulatorObjects = generateObjects();
        this.agenda = agenda;

        this.time = LocalTime.MIDNIGHT;
        setupNPC();
        setSpeed((60 * 5));
    }


    private ArrayList<SimulatorObject> generateObjects() {
        ArrayList<SimulatorObject> objects = new ArrayList<>();
        for (Layer layer : this.tileMap.getLayers()) {
            if (layer instanceof ObjectLayer) {
                for (TileObject tileObject : ((ObjectLayer) layer).getTileObjects()) {
                    if (tileObject.getType().equals("Podium")) {
                        objects.add(new SimulatorPodium(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer()));
                    } else if (tileObject.getType().equals("Spawn")) {
                        this.spawn = new SimulatorObject(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer());
                    }
                }
            }
        }
        return objects;
    }

    public void setupNPC() {
        if (this.spawn != null) {
            while (this.npcList.size() < NUMBEER_OF_NPCS) {
                spawnNPC(new Point2D.Double(this.spawn.location.getX() + Math.random() * this.spawn.width, this.spawn.location.getY() + Math.random() * this.spawn.height));
            }
        } else {
            Random r = new Random(0);
            while (this.npcList.size() < 10) {
//                NPC npc = new NPC(new Point2D.Double((int) (Math.random() * 1000), (int) (Math.random() * 1000)), r.nextInt(NPC.getCharacterFiles()));
////              NPC npc = new NPC(new Point2D.Double(500, 500, r.nextInt(NPC.getCharacterFiles())));
//                npc.setTargetObject(this.simulatorObjects.get(1));
//                if (!npc.checkCollision(this.npcList)) {
//                    this.npcList.add(npc);
//                }
                spawnNPC(new Point2D.Double((int) (Math.random() * 1000), (int) (Math.random() * 1000)));
            }
        }
    }

    public void spawnNPC(Point2D location) {
        Random r = new Random(0);
        NPC npc = new NPC(location, r.nextInt(NPC.getCharacterFiles()));
        npc.setTargetObject(this.simulatorObjects.get(1));
        if (!npc.checkCollision(this.npcList)) {
            this.npcList.add(npc);
        }
    }

    /**
     * Draws <code>this.tileMap</code>, all the NPC's and the objects to the given screen.
     *
     * @param g2d The object to draw to
     */
    public void draw(FXGraphics2D g2d) {
        this.tileMap.draw(g2d);
        for (NPC npc : this.npcList) {
            npc.draw(g2d);
        }

        for (SimulatorObject object : this.simulatorObjects) {
            object.draw(g2d);
        }

        //Todo: debug
        g2d.setColor(Color.black);
        g2d.drawString(this.time.toString(), 0, 10);
    }

    /**
     * Updates all the NPC's and the objects to the given screen.
     *
     * @param deltaTime The time it took sinds last update
     */
    public void update(double deltaTime) {
        this.time = this.time.plusSeconds((long) (deltaTime * this.speed));

        for (NPC npc : this.npcList) {
            npc.update(this.npcList);
        }

        debugNPCTarget(deltaTime);
    }

    private double debugTimer = 15;

    private void debugNPCTarget(double deltaTime) {
        debugTimer -= deltaTime;
        if (debugTimer < 0) {
            SimulatorObject object = this.simulatorObjects.get((int) (Math.random() * this.simulatorObjects.size()));
            for (NPC npc : this.npcList) {
                npc.setTargetObject(object);
            }
            debugTimer = 15;
        }
    }

    /**
     * Getter for <code>this.npcList</code>
     *
     * @return <code>this.npcList</code>
     */
    public ArrayList<NPC> getNpcList() {
        return npcList;
    }

    /**
     * Setter for <code>this.npcList</code>
     *
     * @param npcList <code>this.npcList</code>
     */
    public void setNpcList(ArrayList<NPC> npcList) {
        this.npcList = npcList;
    }

    /**
     * Getter for the <code>this.tileMap</code>
     *
     * @return <code>this.tileMap</code>
     */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * Setter for the <code>this.tileMap</code>
     *
     * @param tileMap <code>this.tileMap</code>
     */
    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        for (NPC npc : this.npcList) {
            npc.setGameSpeed(speed);
        }
    }
}
