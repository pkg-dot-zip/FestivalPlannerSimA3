package FestivalPlanner.NPC;

/**
 * Abstract class containing the main method all our NPC-state classes use.
 */
public abstract class NPCState {
    /**
     * Handles the behaviour of the NPC in the particular state
     */
    public abstract void handle();
}
