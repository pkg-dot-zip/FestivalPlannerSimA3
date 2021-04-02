package FestivalPlanner.NPC;

/**
 * Handles the behaviour of NPCs who are moving.
 */
public class MovingState extends NPCState {
    @Override
    public void handle(NPC npc) {
        npc.updateDirectionToFace();
        npc.walkOnAxis();
    }
}
