package FestivalPlanner.NPC;

/**
 * Contains the main method all NPC-state classes use.
 */
public abstract class NPCState {
    /**
     * Handles the behaviour of the NPC.
     */
    public abstract void handle(NPC npc);
}