package FestivalPlanner.Agenda;

import java.util.HashMap;

//TODO: Improve documentation.

/**
 * Manages all stages used by <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.
 * <p>
 * Contains a HashMap with a String containing the name of the stage, and an instance of the object.
 */
public class StageManager {

    private HashMap<String, Stage> stages;

    /**
     * Returns true if the stages HashMap contains the parameter's value. If it does not contain
     * that value it'll return false.
     * @param stageName  a string representing a possible key for the stages HashMap
     * @return  a boolean, which is true of the HashMap contains a key the parameter's value
     */
    public boolean containsStage(String stageName){
        return this.stages.containsKey(stageName);
    }

    /**
     * Adds the <code>Stage</code> object to the stages ArrayList if the list doesn't contain the value already.
     * @param stage  instance of <a href="{@docRoot}/FestivalPlanner/Agenda/Stage.html">Stage</a> used to check whether the Hashmap already contains it or not.
     */
    public void addArtist(Stage stage){
        if (!this.stages.containsKey(stage.getName())){ //Checks whether the stage already exists or not.
            this.stages.put(stage.getName(), stage);
        }
    }

    /**
     * Removes the <code>Stage</code> object from the stages ArrayList.
     * @param stageName  String representing the key of the object that should be removed from the HashMap
     */
    public void removeStage(String stageName){
        this.stages.remove(stageName);
    }

}
