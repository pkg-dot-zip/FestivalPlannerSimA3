package FestivalPlanner.Agenda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Set;

/**
 * Manages all artists used by <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.
 * <p>
 * Contains a HashMap with a String containing the name of the artist, and an instance of the object.
 */
public class ArtistManager {

    private HashMap<String, Artist> artists;

    private ObservableList<String> observableArtistList = FXCollections.observableArrayList();

    /**
     * Empty constructor for the <code>ArtistManager</code>.
     * <p>
     * The empty constructor initialises the <code>this.artists</code> HashMap.
     */
    public ArtistManager() {
        this.artists = new HashMap<>();
    }

    /**
     * Constructor for the <code>ArtistManager</code>.
     * <p>
     * Sets <code>this.artists</code> to the parameter's value.
     * @param artists  pre-existing HashMap containing Strings and Artists <code>this.artists</code> should be set to
     */
    public ArtistManager(HashMap<String, Artist> artists) {
        this.artists = artists;
    }

    /**
     * Returns true if the stages HashMap contains the parameter's value. If it does not contain
     * that value it'll return false.
     * @param artistName  a string representing a possible key for the artists HashMap
     * @return  a boolean, which is true of the HashMap contains a key the parameter's value
     */
    public boolean containsArtist(String artistName){
        return this.artists.containsKey(artistName);
    }

    /**
     * Adds the <code>Artist</code> object to the stages ArrayList if the list doesn't contain the value already.
     * @param artist  instance of <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> used to check whether the HashMap already contains it or not
     */
    public void addArtist(Artist artist){
        if (!this.artists.containsKey(artist.getName())){ //Checks whether the artist already exists or not.
            this.artists.put(artist.getName(), artist);
        }
    }

    /**
     * Removes the <code>Artist</code> object from the artists ArrayList.
     * @param artistName  String representing the key of the object that should be removed from the HashMap
     */
    public void removeArtist(String artistName){
        this.artists.remove(artistName);
    }

    /**
     * changes a <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> within PodiumManager to have the same
     * values as the given a <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a>.
     * @param originalName the name of the <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> you
     *      *                     want to edit
     * @param editedArtist the new version of the <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a>
     */
    public void editArtist(String originalName, Artist editedArtist) {
        Artist oldArtist = getArtist(originalName);

        oldArtist.setName(editedArtist.getName());
        oldArtist.setPicture(editedArtist.getPicture());
        oldArtist.setSprite(editedArtist.getSprite());

        this.artists.remove(originalName);
        this.artists.put(oldArtist.getName(),oldArtist);
    }

    /**
     * Searches for an <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> with the given name, returns
     * <code>null</code> if the <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> was not found.
     * @param name  String representing the name of the Artist that is being requested.
     * @return <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> with the given name, returns
     * <code>null</code> if the <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> was not found
     */

    public Artist getArtist(String name) {
        return this.artists.get(name);
    }



    /**
     * Returns a <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a> with all the
     * names of the known Artists.
     * @return  <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a> containing all the names
     * of the known Artists
     */

    public Set<String> getAllArtistNames() {
        return this.artists.keySet();
    }

    /**
     * Getter with an <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html">ObservableList</a>
     * that automatically updates when new <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artists</a> are made.
     * @return an <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html">ObservableList</a>
     * that automatically updates when new <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artists</a> are made.
     */
    public ObservableList<String> getObservableArtistList() {
        return this.observableArtistList;
    }
}
