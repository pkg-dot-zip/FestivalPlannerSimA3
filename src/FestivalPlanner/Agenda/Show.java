package FestivalPlanner.Agenda;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Contains information about a show, such as the time, name, artist & podium.
 * <p>
 * Show is used by <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a> to collect all shows.
 * @see Agenda#getShows()
 */
public class Show implements Serializable {

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private int expectedPopularity;

    private Podium podium;
    private ArrayList<Artist> artists;

    /**
     * Constructor for <code>Show</code>.
     * <p>
     * Must take in an ArrayLift of artists, since <code>this.artists</code> must contain <b>at least</b> one artist.
     * @param name  sets <code>this.name</code> to the given parameter
     * @param startTime  sets <code>this.startTime</code> to the given parameter
     * @param endTime  sets <code>this.endTime</code> to the given parameter
     * @param expectedPopularity  sets <code>this.expectedPopularity</code> to the given parameter
     * @param podium  sets <code>this.podium</code> to the given parameter
     * @param artists  sets <code>this.artists</code> to the given parameter
     */
    public Show(String name, LocalTime startTime, LocalTime endTime, int expectedPopularity, Podium podium, ArrayList<Artist> artists) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expectedPopularity = expectedPopularity;
        this.podium = podium;
        this.artists = artists;
    }

    /**
     * Returns the amount of seconds between <code>this.startTime</code> & <code>this.startTime</code> in a long.
     * @return  long, amount of seconds between the start and end of a show
     */
    public long getDuration(){
        return ChronoUnit.SECONDS.between(startTime, endTime);
    }

    //TODO: Write documentation for all getters below this line.

    public String getName() {
        return this.name;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public int getExpectedPopularity() {
        return this.expectedPopularity;
    }

    public Podium getPodium() {
        return this.podium;
    }

    public ArrayList<Artist> getArtists() {
        return this.artists;
    }
}
