package FestivalPlanner.Util.MathHandling;

/**
 * Contains methods for calculating moves (based on math) for <a href="{@docRoot}/FestivalPlanner/NPC/NPC.html">NPC</a> instances.
 */
public class NPCMathHandler {
    /**
     * Returns a random number specifically made for usage by the movement for separating the <b>NPC</b>s based on their colliders.
     * @param  COLLISION_RADIUS  collision-radius of NPC to use in calculations
     * @return  partially random integer
     */
    public static int separateRandomNumber(int COLLISION_RADIUS){
        double random = Math.random();

        if (random < 0.6){
            return 0;
        } else if (random < 0.8){
            return (COLLISION_RADIUS / 10);
        } else if (random < 0.9){
            return (COLLISION_RADIUS / 5);
        } else {
            return 3;
        }
    }
}