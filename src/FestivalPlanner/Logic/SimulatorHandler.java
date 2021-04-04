package FestivalPlanner.Logic;

import FestivalPlanner.Agenda.*;
import FestivalPlanner.NPC.NPC;
import FestivalPlanner.TileMap.Layer;
import FestivalPlanner.TileMap.ObjectLayer;
import FestivalPlanner.TileMap.TileMap;
import FestivalPlanner.TileMap.TileObject;
import FestivalPlanner.Util.ImageLoader;
import FestivalPlanner.Util.JsonHandling.JsonConverter;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class SimulatorHandler {

    //Final values.
    private final double SHOW_CHECK_TIME = 60 * 5; //how often to check per in-game second
    private final double NPC_SPAWN_TIME = 60; //how ofter to spawn a new NPC if not all NPC's have been spawned
    private static final double TOILET_CHANCE = 0.2;

    //NPC attributes.
    private ArrayList<NPC> npcList;
    private ArrayList<NPC> allNPCList;
    private int NPCAmount = 20;

    //Agenda attributes.
    private Agenda agenda;
    private PodiumManager podiumManager;
    private ArtistManager artistManager;

    private HashMap<String, SimulatorPodium> podiumObjectHashMap;
    private HashMap<String, NPC> artistNPCHashMap;
    private HashMap<String, SimulatorObject> danceObjectHashMap;
    private HashSet<Show> activeShows = new HashSet<>();

    //TileMap attributes.
    private TileMap tileMap;
    private ArrayList<SimulatorObject> simulatorObjects;
    private ArrayList<SimulatorToilet> simulatorToilets = new ArrayList<>();
    private SimulatorObject spawn;

    //Time attributes.
    private LocalTime time;
    private double speed = (60 * 2); //Value in game second per real second (s/s).

    private double currentNPCSpawnTime = this.NPC_SPAWN_TIME;
    private double currentShowTime = this.SHOW_CHECK_TIME;


    private boolean paused = false;

    public SimulatorHandler(Agenda agenda, PodiumManager podiumManager, ArtistManager artistManager) {
        this(agenda, podiumManager, artistManager, new JsonConverter().JSONToTileMap("/testMap.json"));
    }

    /**
     * Top constructor for SimulatorHandler.
     * @param agenda  a class containing all of the information about created shows
     * @param podiumManager  the manager containing all the created podiums
     * @param artistManager  the manager containing all the created Artists
     * @param tileMap  the TileMap to set <code>this.tileMap</code> to
     */
    private SimulatorHandler(Agenda agenda, PodiumManager podiumManager, ArtistManager artistManager, TileMap tileMap) {
        this.npcList = new ArrayList<>();
        this.allNPCList = new ArrayList<>();
        this.tileMap = tileMap;

        this.agenda = agenda;
        this.podiumManager = podiumManager;
        this.artistManager = artistManager;

        reset(agenda);
    }

    /**
     * Resets the different parts that need to be changed after a change in <code>this.agenda</code>.
     * <p>
     * The different parts that are changed are:
     * <ul>
     *     <li><code>this.agenda</code> to the new changed <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.</li>
     *     <li>{@link #generateObjects()}</li>
     *     <li>{@link #generatePodiumHashMap()}</li>
     *     <li><code>this.time</code> to 00:00.</li>
     *     <li>Resets all the <b>NPC</b>s</li>
     * </ul>
     * @param agenda  <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a> That is loaded into the simulator
     */
    public void reset(Agenda agenda) {
        this.agenda = agenda;

        this.danceObjectHashMap = new HashMap<>();
        this.podiumObjectHashMap = new HashMap<>();
        this.artistNPCHashMap = new HashMap<>();
        this.simulatorObjects = new ArrayList<>();

        generateObjects();
        generatePodiumHashMap();

        this.time = LocalTime.MIDNIGHT;

        this.npcList.clear();
        this.allNPCList.clear();
        spawnAllArtistNPCs();
    }

    /**
     * Looks trough all the <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorPodium.html">SimulatorPodiums</a> to find
     * the corresponding <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a>.
     * <p>
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
     * <p>
     * Creates the following objects:
     * <p>
     * <ul>
     *     <li>All the <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorPodium.html">SimulatorPodiums</a> in the list.</li>
     *     <li>The Spawn object in <code>this.spawn</code>.</li>
     *     <li>All the other normal <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorObject.html">SimulatorObject</a></li>
     * </ul>
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
                        case "Dixi":
                            SimulatorToilet toilet = new SimulatorToilet(tileObject.getLocation(), tileObject.getWidth(), tileObject.getHeight(), tileObject.getRotation(), tileObject.getName(), this.tileMap.getPathFindingLayer(), tileObject.getLocationString());
                            this.simulatorObjects.add(toilet);
                            this.simulatorToilets.add(toilet);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Draws <code>this.tileMap</code>, all the <b>NPC</b>s and the objects to the given screen.
     * @param g2d  the object to draw to
     */
    public void draw(FXGraphics2D g2d) {
        this.tileMap.draw(g2d);

        for (SimulatorObject object : this.podiumObjectHashMap.values()) {
            object.draw(g2d);
        }

        for(SimulatorToilet toilet : this.simulatorToilets) {
            toilet.draw(g2d);
        }

        for (NPC npc : this.allNPCList) {
            npc.draw(g2d);
        }

        g2d.setColor(Color.black);
    }

    /**
     * Updates all the <b>NPC</b>s and the objects to the given screen.
     * @param deltaTime  the time it took since last update
     */
    public void update(double deltaTime) {
        if (this.paused){
            return;
        }

        this.time = this.time.plusSeconds((long) (deltaTime * this.speed));

        //Updating NPCs.
        setupNPC(deltaTime * this.speed);
        for (NPC npc : this.allNPCList) {
            npc.update(this.allNPCList);
        }


        //Updating set Podiums.
        for (SimulatorObject object : this.simulatorObjects){
            object.update(deltaTime);
        }

        //Handling assigning shows.
        this.currentShowTime -= (deltaTime * this.speed);
        if (this.currentShowTime < 0) {
            checkShows();
            this.currentShowTime = this.SHOW_CHECK_TIME;
        }
    }

    /**
     * Handles spawning <b>NPC</b>s.
     * @param timePast  the time spend since last update
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
    private void spawnNPC() {
        Random r = new Random();
        Point2D location = new Point2D.Double(this.spawn.location.getX() + Math.random() * this.spawn.width, this.spawn.location.getY() + Math.random() * this.spawn.height);
        NPC npc = new NPC(location, r.nextInt(NPC.getCharacterFiles()));
        npc.setGameSpeed(this.speed);
        if (!npc.checkCollision(this.allNPCList)) {
            this.npcList.add(npc);
            this.allNPCList.add(npc);
        }
    }

    /**
     * Spawns new NPC on the spawn place. Makes sure there is no collision between NPCs
     * <p>
     * The amount of NPCs spawned is based on the set amount
     */
    private void spawnAllArtistNPCs() {
        for (String artist : this.artistManager.getAllArtistNames()) {
            Artist currentArtist = this.artistManager.getArtist(artist);

            ArrayList<ArrayList<BufferedImage>> spriteSheet;

            if (currentArtist.getSprite() != null) {
                spriteSheet = ImageLoader.loadImage(currentArtist.getSprite());
            } else {
                spriteSheet = ImageLoader.getLists((int)(Math.random()));
            }

            boolean running = true;
            while (running) {
                Point2D location = new Point2D.Double(this.spawn.location.getX() + Math.random() * this.spawn.width, this.spawn.location.getY() + Math.random() * this.spawn.height);
                NPC artistNPC = new NPC(location, spriteSheet);
                artistNPC.setGameSpeed(this.speed);
                if (!artistNPC.checkCollision(this.allNPCList)) {
                    this.allNPCList.add(artistNPC);
                    this.artistNPCHashMap.put(artist, artistNPC);
                    running = false;
                }
            }
        }
    }

    /**
     * Checks if, since last check, shows have started or ended.
     * <p>
     * Calls {@link #onShowStart(Show)} or {@link #onShowEnd(Show)} if shows have started or ended respectively.
     */
    private void checkShows() {
        HashSet<Show> oldActiveShows = this.activeShows;
        this.activeShows = getActiveShows(this.time);

        if (this.activeShows.size() > 0) {
            ArrayList<Show> startingShows = new ArrayList<>(activeShows);
            startingShows.removeAll(oldActiveShows);
            for (Show show : startingShows) {

                //Shows that have started.
                onShowStart(show);
            }
        }

        if (oldActiveShows.size() > 0) {
            ArrayList<Show> endedShows = new ArrayList<>(oldActiveShows);
            endedShows.removeAll(activeShows);
            for (Show show : endedShows) {
                //Shows that have ended.
                onShowEnd(show);
            }
        }
    }

    /**
     * Handles all the action that need to happen when a new shows starts playing.
     * @param show  The show that started
     */
    private void onShowStart(Show show) {
        double change = show.getExpectedPopularity();

        SimulatorPodium podium = this.podiumObjectHashMap.get(show.getPodium().getName());
        if (podium != null) {
            podium.setActive(true);
            for (Artist artist : show.getArtists()) {
                NPC artistNPC = this.artistNPCHashMap.get(artist.getName());
                this.artistNPCHashMap.get(artist.getName()).setTargetObject(podium);
                artistNPC.setCollisionIsEnabled(false);
            }
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

    /**
     * Handles everything that needs to happen when a show has ended.
     * @param show  The show that ended
     */
    private void onShowEnd(Show show) {
        SimulatorPodium podium = this.podiumObjectHashMap.get(show.getPodium().getName());
        if (podium != null) {
            podium.setActive(false);

            for (Artist artist : show.getArtists()) {
                this.artistNPCHashMap.get(artist.getName()).setTargetObject(this.simulatorToilets.get((int)(Math.random() * 5)));
            }

            for (NPC npc : this.npcList) {
                if (npc.getTargetObject() != null && npc.getTargetObject().equals(podium)) {

                    if (Math.random() < TOILET_CHANCE) {
                        for (SimulatorToilet toilet : this.simulatorToilets) {
                            if (!toilet.isOccupied()) {
                                npc.setTargetObject(toilet);
                                toilet.setOccupied(true);
                                break;
                            }
                        }
                    } else {
                        npc.setTargetObject(null);
                    }
                }
            }
        }
    }

    /**
     * Returns an ArrayList with current active shows.
     * @param currentTime  the time to check the shows for
     * @return  <code>ArrayList</code> with current active <code>Show</code>s
     */
    public HashSet<Show> getActiveShows(LocalTime currentTime) {
        HashSet<Show> activeShows = new HashSet<>();
        for (Show show : this.agenda.getShows()) {
            if (show.getStartTime().isBefore(currentTime) && show.getEndTime().isAfter(currentTime)) {
                activeShows.add(show);
            }
        }
        return activeShows;
    }

    /**
     * Returns <code>this.npcList</code>.
     * @return <code>this.npcList</code>
     */
    public ArrayList<NPC> getNpcList() {
        return npcList;
    }

    /**
     * Returns <code>this.tileMap</code>.
     * @return  <code>this.tileMap</code>
     */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * Returns <code>this.Agenda</code>.
     * @return  <code>this.Agenda</code>
     */
    public Agenda getAgenda() {
        return this.agenda;
    }

    /**
     * Returns <code>this.time</code>.
     * @return  <code>this.time</code>
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Setter for <code>this.time</code>.
     * @param time The value to set <code>this.time</code> to
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Returns <code>this.speed</code>.
     * @return <code>this.speed</code>
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Setter for <code>this.speed</code>.
     * <p>
     * Also sets the correct speed to all the <b>NPC</b>s in <code>this.NPCList</code>.
     * @param speed  the value to set <code>this.speed</code> to
     */
    public void setSpeed(double speed) {
        this.speed = speed;
        for (NPC npc : this.npcList) {
            npc.setGameSpeed(speed);
        }
        for (NPC npc : this.allNPCList) {
            npc.setGameSpeed(speed);
        }
    }

    /**
     * Returns <code>this.NPCAmount</code>.
     * @return <code>this.NPCAmount</code>
     */
    public int getNPCAmount() {
        return this.NPCAmount;
    }

    /**
     * Setter for <code>this.NPCAmount</code>
     * @param NPCAmount  sets <code>this.NPCAmount</code> to the given value
     */
    public void setNPCAmount(int NPCAmount) {
        this.NPCAmount = NPCAmount;
    }

    /**
     * Returns a list of all the SimulatorPodiums that are used in the <code>this.agenda</code>.
     * @return  a list with all the values in <code>this.podiumObjectHashMap</code>
     */
    public ArrayList<SimulatorPodium> getPodiums() {
        return new ArrayList<>(this.podiumObjectHashMap.values());
    }

    /**
     * Returns <code>this.ArtistNPCHasMap</code>.
     * @return  <code>this.ArtistNPCHasMap</code>
     */
    public HashMap<String, NPC> getArtistNPCHashMap() {
        return this.artistNPCHashMap;
    }

    /**
     * Returns <code>this.paused</code>.
     * @return  <code>this.paused</code>
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Sets <code>this.paused</code> to the parameter's value.
     * @param paused  parameter to set <code>this.paused</code> to
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}