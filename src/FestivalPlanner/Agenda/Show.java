package FestivalPlanner.Agenda;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Contains information about a show, such as the time, name, artist and podium.
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
     * Constructor containing no parameters, used by
     * the <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/ShowEditorGUI.html">ShowEditorGUI</a>.
     * <p>
     * This constructor sets:
     * <p><ul>
     * <li>This name to "New Show".
     * <li>This startTime to "00:00".
     * <li>This endTime to "23:00".
     * <li>This expectedPopularity to 50.
     * <li>This podium to <i>null</i>.
     * <li>This artists to <i>null</i>.
     * </ul>
     */
    public Show() {
        this.name = "New Show";
        this.startTime = LocalTime.parse("00:00");
        this.endTime = LocalTime.parse("23:00");
        this.expectedPopularity = 50;
        this.podium = null;
        this.artists = null;
    }

    /**
     * Constructor containing another Show instance as a parameter.
     * <p>
     * This constructor sets all parameters of this instance to the parameter's values.
     * @param show  instance from whom all values will be copied
     */
    public Show(Show show){
        this.name = show.getName();
        this.startTime = show.getStartTime();
        this.endTime = show.getEndTime();
        this.expectedPopularity = show.getExpectedPopularity();
        this.podium = show.getPodium();
        this.artists = show.getArtists();
    }

    /**
     * Returns the amount of seconds between <code>this.startTime</code> and <code>this.startTime</code> in a long.
     * @return  long, amount of seconds between the start and end of a show
     */
    public long getDuration(){
        return ChronoUnit.SECONDS.between(startTime, endTime);
    }

    /**
     * Returns this show's name.
     * @return  this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns this show's startTime.
     * @return  this.startTime
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }

    /**
     * Returns this show's endTime.
     * @return  this.endTime
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }

    /**
     * Returns this show's expectedPopularity.
     * @return  this.expectedPopularity
     */
    public int getExpectedPopularity() {
        return this.expectedPopularity;
    }

    /**
     * Returns this show's podium.
     * @return  this.podium
     */
    public Podium getPodium() {
        return this.podium;
    }

    /**
     * Returns this show's artists.
     * @return  this.artists
     */
    public ArrayList<Artist> getArtists() {
        return this.artists;
    }

    /**
     * Sets <code>this.name</code> to the parameter's value.
     * @param name  the new name for this show
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets <code>this.startTime</code> to the parameter's value.
     * @param startTime  the new start-time for this show
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets <code>this.endTime</code> to the parameter's value.
     * @param endTime  the new end-time for this show
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets <code>this.expectedPopularity</code> to the parameter's value.
     * @param expectedPopularity  the new expected popularity for this show
     */
    public void setExpectedPopularity(int expectedPopularity) {
        this.expectedPopularity = expectedPopularity;
    }

    /**
     * Sets <code>this.podium</code> to the parameter's value.
     * @param podium  the new podium for this show
     */
    public void setPodium(Podium podium) {
        this.podium = podium;
    }

    /**
     * Sets <code>this.artists</code> to the parameter's value.
     * @param artists  the new artists for this show
     */
    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }
}
