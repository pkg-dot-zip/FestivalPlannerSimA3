package FestivalPlanner.Logic;

import FestivalPlanner.NPC.NPC;
import FestivalPlanner.TileMap.TileMap;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class SimulatorHandler {

    private ArrayList<NPC> npcList;
    //private ArrayList<SimulatorObjects> objects;
    private TileMap tileMap;

    public SimulatorHandler() {
        this(new TileMap());
    }

    public SimulatorHandler(TileMap tileMap) {
        this(new ArrayList<>(), tileMap);
        generateNPC();
    }

    public SimulatorHandler(ArrayList<NPC> npcList, TileMap tileMap) {
        this.npcList = npcList;
        this.tileMap = tileMap;
    }

    public void generateNPC(){
        Random r = new Random(0);
        while(this.npcList.size() < 100)
        {
            NPC npc = new NPC(new Point2D.Double((int)(Math.random() * 1000), (int)(Math.random() * 1000)), r.nextInt(NPC.getCharacterFiles()));
            if(!npc.checkCollision(this.npcList))
            {
                this.npcList.add(npc);
            }
        }
    }

    public void draw(FXGraphics2D g2d) {
        for (NPC npc : this.npcList) {
            npc.draw(g2d);
        }
    }

    public void update(double deltaTime) {
        for (NPC npc : this.npcList) {
            npc.update(this.npcList);
        }
    }

    public ArrayList<NPC> getNpcList() {
        return npcList;
    }

    public void setNpcList(ArrayList<NPC> npcList) {
        this.npcList = npcList;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }
}
