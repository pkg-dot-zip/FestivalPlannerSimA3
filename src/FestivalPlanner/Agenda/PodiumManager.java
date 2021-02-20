package FestivalPlanner.Agenda;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

//TODO: Improve documentation.

/**
 * Manages all podiums used by <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.
 * <p>
 * Contains a HashMap with a String containing the name of the stage, and an instance of the object.
 */
public class PodiumManager {

    private HashMap<String, Podium> podiums;

    /**
     * Empty constructor for the <code>PodiumManager</code>.
     * <p>
     * The empty constructor initialises the <code>this.podiums</code> HashMap.
     */

    public PodiumManager() {
        this.podiums = new HashMap<>();
    }

    /**
     * Returns true if the podiums HashMap contains the parameter's value. If it does not contain
     * that value it'll return false.
     * @param podiumName  a string representing a possible key for the podiums HashMap
     * @return  a boolean, which is true of the HashMap contains a key the parameter's value
     */
    public boolean containsPodium(String podiumName){
        return this.podiums.containsKey(podiumName);
    }

    /**
     * Adds the <code>Podium</code> object to the podiums ArrayList if the list doesn't contain the value already.
     * @param podium  instance of <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> used to check whether the Hashmap already contains it or not.
     */
    public void addPodium(Podium podium){
        if (!this.podiums.containsKey(podium.getName())){ //Checks whether the podium already exists or not.
            this.podiums.put(podium.getName(), podium);
        }
    }

    /**
     * Removes the <code>Podium</code> object from the podiums ArrayList.
     * @param podiumName  String representing the key of the object that should be removed from the HashMap
     */
    public void removePodium(String podiumName){
        this.podiums.remove(podiumName);
    }

    /**
     * Searches for a <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> with the given name, returns
     * <code>null</code> if the <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> was not found.
     * @param name String representing the name of the podium that is being requested.
     * @return <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> with the given name, returns
     * <code>null</code> if the <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> was not found
     */

    public Podium getPodium(String name) {
        return this.podiums.get(name);
    }

    /**
     * Returns a <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a> with all the
     * names of the known podiums.
     * @return <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a> containing all the names
     * of the known podiums
     */

    public Set<String> getAllPodiumNames() {
        return this.podiums.keySet();
    }
}