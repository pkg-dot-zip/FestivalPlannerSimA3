package FestivalPlanner.Agenda;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Top-class of the Agenda package; contains all the information needed for the GUI.
 */
public class Agenda implements Serializable {

    private String name;
    private ArrayList<Show> shows;

    /**
     * Constructor for <code>Agenda</code>.
     * <p>
     * Sets the name to the parameter's value and initialises the this.shows ArrayList.
     * @param name  changes <code>this.name</code> to the parameter's value
     */
    public Agenda(String name) {
        this.name = name;
        this.shows = new ArrayList<>();
    }

    /**
     * Specific constructor for <code>Agenda</code> only used
     * by <a href="{@docRoot}/FestivalPlanner/Agenda/SaveHandler.html">SaveHandler</a>.
     * <p>
     * Sets the name to the parameter's value and sets <code>this.shows</code> to the parameter's value.
     * @param name  changes <code>this.name</code> to the parameter's value
     * @param shows contains all the shows that will happen
     */
    public Agenda(String name, ArrayList<Show> shows) {
        this.name = name;
        this.shows = shows;
    }

    /**
     * Empty constructor for <code>Agenda</code> only used
     * by <a href="{@docRoot}/FestivalPlanner/Agenda/SaveHandler.html">SaveHandler</a> in <code>readAgendaFromFile()</code>.
     */
    public Agenda() {
        this.name = "";
        this.shows = new ArrayList<>();
    }

    /**
     * Returns a string containing the name of this agenda.
     * @return  this.name, a string containing this name of this agenda
     */
    public String getName() {
        return this.name;
    }

    /**
     * Changes the value of this.name to the parameter's value.
     * @param name  String <code>this.name</code> should be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns an ArrayList containing <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a>s of this agenda.
     * @return  this.shows, an ArrayList containing Show instances of this Agenda
     */
    public ArrayList<Show> getShows() {
        return this.shows;
    }

    /**
     * Adds the <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a> to the ArrayList <code>this.shows</code>.
     * @param show  Show instance that should be added to this ArrayList
     */
    public void addShow(Show show){
        this.shows.add(show);
    }

    /**
     * Removes the <a href="{@docRoot}/FestivalPlanner/Agenda/Show.html">Show</a> from the ArrayList <code>this.shows</code>.
     * @param show  Show instance that should be removed from this ArrayList
     */
    public void removeShow(Show show){
        this.shows.remove(show);
    }
}
