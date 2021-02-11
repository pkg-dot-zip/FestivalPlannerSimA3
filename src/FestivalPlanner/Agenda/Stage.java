package FestivalPlanner.Agenda;

/**
 * Contains all information relevant to a stage, such as the name and location.
 */
public class Stage {

    private String name;
    private String location; //(!TODO) WARNING: Datatype might change in the future!

    /**
     * Constructor for the <code>Stage</code> class.
     * @param name  sets <code>this.name</code> to the given parameter's value
     * @param location  sets <code>this.location</code> to the given parameter's value
     */
    public Stage(String name, String location) {
        this.name = name;
        this.location = location;
    }

    /**
     * Returns a string containing the name of this artist.
     * @return  this.name, a string containing this name of this artist
     */
    public String getName() {
        return this.name;
    }
}