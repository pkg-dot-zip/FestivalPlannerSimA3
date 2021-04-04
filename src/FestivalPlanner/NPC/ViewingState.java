package FestivalPlanner.NPC;

/**
 * Handles the behaviour of the NPCs who are viewing a concert.
 */
public class ViewingState extends NPCState {

    private int timer = (int)(Math.random()*1000);

    @Override
    public void handle(NPC npc) {
        timer++;

        if (timer >= 5000) {
            if (npc.getDirection().equals(Direction.LEFT)){
                npc.setDirection(Direction.RIGHT);
            } else {
                npc.setDirection(Direction.LEFT);
            }

            timer = (int)(Math.random()*1000);
        }

    }
}