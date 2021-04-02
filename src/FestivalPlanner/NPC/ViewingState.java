package FestivalPlanner.NPC;

/**
 * Handles the behaviour of the NPCs who are viewing a concert.
 */
public class ViewingState extends NPCState {

    int timer = (int)(Math.random()*100);
    @Override
    public void handle(NPC npc) {
        timer++;
        //System.out.println(timer);
        if (timer % 500 == 0) {
            if (npc.getDirection().equals(Direction.LEFT)){
                npc.setDirection(Direction.RIGHT);
            } else {
                npc.setDirection(Direction.LEFT);
            }
        }
    }
}
