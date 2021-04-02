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
import java.time.LocalTime;
import java.util.*;

public class SimulatorHandler {

    // Final values
    private final double SHOW_CHECK_TIME = 60 * 15; //how often to check per in-game second
    private final double NPC_SPAWN_TIME = 60 * 3; //how ofter to spawn a new NPC if not all NPC's have been spawned

    // NPC attributes
    private ArrayList<NPC> npcList;
    private int NPCAmount = 10;

    // Agenda attributes
    private Agenda agenda;
    private PodiumManager podiumManager;

    private HashMap<String, SimulatorPodium> podiumObjectHashMap;
    private HashMap<String, SimulatorObject> danceObjectHashMap;
    private ArrayList<Show> activeShows = new ArrayList<>();

    // TileMap attributes
    private TileMap tileMap;
    private ArrayList<SimulatorObject> simulatorObjects;
    private SimulatorObject spawn;

    // Time attributes
    private LocalTime time;
    private double speed = (60 * 5); //value in game second per real second (s/s)




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
     *     <li>Resets all the NPC's</li>
     * </ul>
     *
     * @param agenda <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a> That is loaded into the simulator
     */
    public void reset(Agenda agenda) {
        this.agenda = agenda;

        this.danceObjectHashMap = new HashMap<>();
        this.podiumObjectHashMap = new HashMap<>();
        this.simulatorObjects = new ArrayList<>();

        generateObjects();

        generatePodiumHashMap();

        this.time = LocalTime.MIDNIGHT;

        this.npcList.clear();
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
                Podium correspondingPodium = this.podiumManager.getPodiumAtLocation((simulatorObject).getLocationString());
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
     *
     */
    private void generateObjects() {
        for (Layer layer : this.tileMap.getLayers()) {
            if (layer instanceof ObjectLayer) {
                for (TileObject tileObject : ((ObjectLayer) layer).getTileObjects()) {
                    switch (tileObject.getType()) {
                        case "Podium":
                            this.simulatorObjects.add(new SimulatorPodium(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer(), tileObject.getLocationString()));
                            break;
                        case "Spawn":
                            this.spawn = new SimulatorObject(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer(), tileObject.getLocationString());
                            break;
                        case "StageDance":
                            SimulatorObject simulatorObject = new SimulatorObject(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer(), tileObject.getLocationString());
                            this.simulatorObjects.add(simulatorObject);

                            Podium correspondingPodium = this.podiumManager.getPodiumAtLocation(simulatorObject.getLocationString());
                            if (correspondingPodium != null) {
                                this.danceObjectHashMap.put(correspondingPodium.getName(), simulatorObject);
                            }
                            break;
                    }
                }
            }
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

        for (SimulatorObject object : this.podiumObjectHashMap.values()) {
            object.draw(g2d);
        }

        //Todo: remove bc debug
        g2d.setColor(Color.black);
        g2d.drawString(this.time.toString(), 0, 10);
    }


    private double currentShowTime = this.SHOW_CHECK_TIME;

    /**
     * Updates all the NPC's and the objects to the given screen.
     *
     * @param deltaTime The time it took sinds last update
     */
    public void update(double deltaTime) {
        this.time = this.time.plusSeconds((long) (deltaTime * this.speed));

        // Updating NPC's
        setupNPC(deltaTime * this.speed);
        for (NPC npc : this.npcList) {
            npc.update(this.npcList);
        }

        //Updating set Podiums
        for (SimulatorObject object : this.simulatorObjects){
            object.update(deltaTime);
        }

        // Handling assigning shows
        this.currentShowTime -= (deltaTime * this.speed);
        if (this.currentShowTime < 0) {
            checkShows();
            this.currentShowTime = this.SHOW_CHECK_TIME;
        }

        //debugNPCTarget(deltaTime);
    }

    private double currentNPCSpawnTime = this.NPC_SPAWN_TIME;

    /**
     * Handles spawning NPC's
     * @param timePast  The time spend since last update
     */
    private void setupNPC(double timePast) {
        this.currentNPCSpawnTime -= timePast;

        if (this.currentNPCSpawnTime < 0) {
            if (this.npcList.size() < this.NPCAmount) {
                spawnNPC();
            }
            this.currentNPCSpawnTime = this.NPC_SPAWN_TIME;
        }

    }

    /**
     * Creates a new <a href="{@docRoot}/FestivalPlanner/NPC/NPC.html">NPC</a> at a rondom location at <code>this.spawn</code>.
     */
    public void spawnNPC() {
        Random r = new Random();
        Point2D location = new Point2D.Double(this.spawn.location.getX() + Math.random() * this.spawn.width, this.spawn.location.getY() + Math.random() * this.spawn.height);
        NPC npc = new NPC(location, r.nextInt(NPC.getCharacterFiles()));
        npc.setGameSpeed(this.speed);
        if (!npc.checkCollision(this.npcList)) {
            this.npcList.add(npc);
        }
    }

    private void checkShows() {
        ArrayList<Show> oldActiveShows = this.activeShows;
        this.activeShows = getActiveShows(this.time);


        if (this.activeShows.size() > 0) {
            ArrayList<Show> startingShows = new ArrayList<>(activeShows);
            startingShows.removeAll(oldActiveShows);
            for (Show show : startingShows) {
                //shows that have started
                onShowStart(show);
            }
        }

        if (oldActiveShows.size() > 0) {
            ArrayList<Show> endedShows = new ArrayList<>(oldActiveShows);
            endedShows.removeAll(activeShows);
            for (Show show : endedShows) {
                // Shows that have ended
                onShowEnd(show);
            }
        }
    }

    private void onShowStart(Show show) {
        double change = show.getExpectedPopularity();

        SimulatorPodium podium = this.podiumObjectHashMap.get(show.getPodium().getName());
        if (podium != null) {
            podium.setActive(true);
        }

        SimulatorObject danceObject = this.danceObjectHashMap.get(show.getPodium().getName());
        if (danceObject != null) {
            for (NPC npc : this.npcList) {
                if (npc.getTargetObject() == null ||
                        Math.random() > (change / 100f))
                    npc.setTargetObject(danceObject);
            }
        }
    }

    private void onShowEnd(Show show) {


        SimulatorPodium podium = this.podiumObjectHashMap.get(show.getPodium().getName());
        if (podium != null) {
            podium.setActive(false);
        }

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
     * Returns an ArrayList with current active shows
     *
     * @param currentTime The time to check the shows for
     * @return <code>ArrayList</code> with current active <code>Show</code>s
     */
    public ArrayList<Show> getActiveShows(LocalTime currentTime) {
        ArrayList<Show> activeShows = new ArrayList<>();
        for (Show show : this.agenda.getShows()) {
            if (show.getStartTime().isBefore(currentTime) && show.getEndTime().isAfter(currentTime)) {
                activeShows.add(show);
            }
        }
        return activeShows;
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
     *
     * @return <code>this.Agenda</code>
     */
    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Getter for <code>this.time</code>
     *
     * @return <code>this.time</code>
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Setter for <code>this.time</code>
     *
     * @param time The value to set <code>this.time</code> to
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Getter for <code>this.speed</code>
     *
     * @return <code>this.speed</code>
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter for <code>this.speed</code>.
     * <b>
     * Also sets the correct speed to all the NPC's in <code>this.NPCList</code>
     *
     * @param speed The value to set <code>this.speed</code> to
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        for (NPC npc : this.npcList) {
            npc.setGameSpeed(speed);
        }
    }

    /**
     * Getter for <code>this.NPCAmount</code>
     * @return <code>this.NPCAmount</code>
     */
    public int getNPCAmount() {
        return NPCAmount;
    }

    /**
     * Setter for <code>this.NPCAmount</code>
     * @param NPCAmount  Sets <code>this.NPCAmount</code> to the given value
     */
    public void setNPCAmount(int NPCAmount) {
        this.NPCAmount = NPCAmount;
    }

    /**
     * Gets a list of all the SimulatorPodiums that are used in the <code>this.agenda</code>.
     * @return  A list with all the values in <code>this.podiumObjectHashMap</code>
     */
    public ArrayList<SimulatorPodium> getPodiums() {
        return new ArrayList<>(this.podiumObjectHashMap.values());
    }
}
