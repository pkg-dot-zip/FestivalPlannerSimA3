package FestivalPlanner.Logic;

import FestivalPlanner.Agenda.Agenda;
import FestivalPlanner.Agenda.Podium;
import FestivalPlanner.Agenda.PodiumManager;
import FestivalPlanner.Agenda.Show;
import FestivalPlanner.NPC.NPC;
import FestivalPlanner.TileMap.*;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class SimulatorHandler {

    private ArrayList<NPC> npcList;
    private ArrayList<SimulatorObject> simulatorObjects;
    private TileMap tileMap;

    private Agenda agenda;
    private PodiumManager podiumManager;

    private HashMap<String, SimulatorPodium> podiumObjectHashMap;

    private LocalTime time;
    private double speed; //value in game second per real second (s/s)

    private SimulatorObject spawn;
    private final int NUMBEER_OF_NPCS = 10;


    /**
     * Empty constructor for SimulatorHandler.
     */
    public SimulatorHandler() {
        this(new Agenda(), new PodiumManager());
    }

    public SimulatorHandler(Agenda agenda, PodiumManager podiumManager) {
        //Todo: remember to remove when loading maps is implemented
        this(agenda, podiumManager, new JsonConverter().JSONToTileMap("/testMap.json"));
    }

    /**
     * Top constructor for SimulatorHandler
     *
     * @param tileMap The TileMap to set <code>this.tileMap</code> to
     */
    public SimulatorHandler(Agenda agenda, PodiumManager podiumManager, TileMap tileMap) {
        this.npcList = new ArrayList<>();
        this.tileMap = tileMap;

        this.agenda = agenda;
        this.podiumManager = podiumManager;

        reset(agenda);
    }

    /**
     * Resets the different parts that need to be changed after a change in <code>this.agenda</code>
     * <b>
     * The different parts that are changed are:
     * <ul>
     *     <li><code>this.agenda</code> to the new changed <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.</li>
     *     <li>{@link #generateObjects()}</li>
     *     <li>{@link #generatePodiumHashMap()}</li>
     *     <li><code>this.time</code> to 00:00.</li>
     *     <li>{@link #setupNPC()}</li>
     * </ul>
     * @param agenda  <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a> That is loaded into the simulator
     */
    public void reset(Agenda agenda){
        this.agenda = agenda;

        this.simulatorObjects = generateObjects();

        this.podiumObjectHashMap = new HashMap<>();
        generatePodiumHashMap();

        this.time = LocalTime.MIDNIGHT;
        setupNPC();
        setSpeed((60 * 5));
    }

    /**
     * Looks trough all the <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorPodium.html">SimulatorPodiums</a> to find
     * the corresponding <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a>.
     * <b>
     * Adds the <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> to the <code>this.podiumObjectHashMap</code>.
     */
    private void generatePodiumHashMap() {
        for (SimulatorObject simulatorObject : this.simulatorObjects) {
            if (simulatorObject instanceof SimulatorPodium) {
                Podium correspondingPodium = this.podiumManager.getPodiumAtLocation(((SimulatorPodium) simulatorObject).getLocationString());
                if (correspondingPodium != null) {
                    this.podiumObjectHashMap.put(correspondingPodium.getName(), (SimulatorPodium) simulatorObject);
                }
            }
        }
    }

    /**
     * Scans through the <a href="{@docRoot}/FestivalPlanner/TileMap/Layer.html">Layers</a> in <code>this.tileMap</code>.
     * <b>
     * Creates the following objects:
     * <ul>
     *     <li>All the <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorPodium.html">SimulatorPodiums</a> in the list.</li>
     *     <li>The Spawn object in <code>this.spawn</code>.</li>
     *     <li>All the other normal <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorObject.html">SimulatorObject</a></li>
     * </ul>
     * @return  An ArrayList to set <code>this.objects</code> to
     */
    private ArrayList<SimulatorObject> generateObjects() {
        ArrayList<SimulatorObject> objects = new ArrayList<>();
        for (Layer layer : this.tileMap.getLayers()) {
            if (layer instanceof ObjectLayer) {
                for (TileObject tileObject : ((ObjectLayer) layer).getTileObjects()) {
                    if (tileObject.getType().equals("Podium")) {
                        objects.add(new SimulatorPodium(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer(), tileObject.getLocationString()));
                    } else if (tileObject.getType().equals("Spawn")) {
                        this.spawn = new SimulatorObject(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer());
                    }
                }
            }
        }
        return objects;
    }

    //Todo: needs rework
    public void setupNPC() {
        if (this.spawn != null) {
            while (this.npcList.size() < NUMBEER_OF_NPCS) {
                spawnNPC();
            }
        }
    }

    /**
     * Creates a new <a href="{@docRoot}/FestivalPlanner/NPC/NPC.html">NPC</a> at a rondom location at <code>this.spawn</code>.
     */
    public void spawnNPC() {
        Random r = new Random();
        Point2D location = new Point2D.Double(this.spawn.location.getX() + Math.random() * this.spawn.width, this.spawn.location.getY() + Math.random() * this.spawn.height);
        NPC npc = new NPC(location, r.nextInt(NPC.getCharacterFiles()));
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

        //debugNPCTarget(deltaTime);
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

    /**
     * Getter for <code>this.Agenda</code>
     * @return  <code>this.Agenda</code>
     */
    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Getter for <code>this.time</code>
     * @return  <code>this.time</code>
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Setter for <code>this.time</code>
     * @param time  The value to set <code>this.time</code> to
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Getter for <code>this.speed</code>
     * @return  <code>this.speed</code>
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter for <code>this.speed</code>.
     * <b>
     * Also sets the correct speed to all the NPC's in <code>this.NPCList</code>
     * @param speed  The value to set <code>this.speed</code> to
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        for (NPC npc : this.npcList) {
            npc.setGameSpeed(speed);
        }
    }

    /**
     * Returns an ArrayList with current active shows
     *
     * @param currentTime
     * @return <code>ArrayList</code> with current active <code>Show</code>s
     */
    public ArrayList<Show> getActiveShows(LocalTime currentTime){
        ArrayList<Show> activeShows = new ArrayList<>();
        for (Show show : this.agenda.getShows()){
            if (show.getStartTime().isBefore(currentTime) && show.getEndTime().isAfter(currentTime)){
                activeShows.add(show);
            }
        }
        return activeShows;
    }
}
