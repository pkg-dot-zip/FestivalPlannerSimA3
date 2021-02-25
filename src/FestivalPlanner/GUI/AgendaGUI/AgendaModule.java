package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AboutPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.ArtistPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.PodiumPopup;
import FestivalPlanner.GUI.PreferencesGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import animatefx.animation.JackInTheBox;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Responsible for placing everything in the correct place in the GUI and making sure all the buttons work.
 */
public class AgendaModule extends AbstractGUI {

    private Stage stage;

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    // Agenda variables
    private Agenda agenda = new Agenda();
    private ArtistManager artistManager = new ArtistManager();
    private PodiumManager podiumManager = new PodiumManager();
    private ArrayList<Show> selectedShows = new ArrayList<>();

    // Panes
    private BorderPane mainLayoutPane = new BorderPane();

    // MenuBar
    private MenuBar menuBar = new MenuBar();
    //FileMenu
    private Menu fileMenu = new Menu(messages.getString("file"));
    private MenuItem loadAgendaMenuItem = new MenuItem(messages.getString("load"));
    private MenuItem saveAgendaMenuItem = new MenuItem(messages.getString("save"));
    private MenuItem exitMenuItem = new MenuItem(messages.getString("exit"));
    //EditMenu
    private Menu editMenu = new Menu(messages.getString("edit"));
    private MenuItem editCurrentlySelectedShow = new MenuItem(messages.getString("edit_show"));
    private MenuItem editArtistsAndPodiumsMenuItem = new MenuItem(messages.getString("edit_artists_and_podiums"));
    private MenuItem preferencesMenuItem = new MenuItem(messages.getString("preferences"));
    //HelpMenu
    private Menu helpMenu = new Menu(messages.getString("help"));
    private MenuItem helpGuideMenuItem = new MenuItem(messages.getString("help_guide"));
    private MenuItem javaDocMenuItem = new MenuItem(messages.getString("javadoc"));
    private MenuItem aboutMenuItem = new MenuItem(messages.getString("about"));

    // Layout components
    private AgendaCanvas agendaCanvas;

    //ContextMenu
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem swapContextItem = new MenuItem(messages.getString("swap")); //TODO: Allow multiple items to be selected. Only swap if 2 are selected.
    private MenuItem editContextItem = new MenuItem(messages.getString("edit"));
    private MenuItem removeContextItem = new MenuItem(messages.getString("remove"));

    /**
     * Constructor of <code>AgendaModule</code>.
     * <p>
     * The given <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> will be stored
     * as a parameter so this stage can be referenced as the main
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>.
     * </p>
     *
     * @param stage will be stored
     *              as a parameter so this stage can be referenced as the owner of the sub stages
     *              <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>
     */
    public AgendaModule(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void load() {
        //Setup methods.
        setup();
        actionHandlingSetup();

        //Stage Settings.
        stage.setScene(new Scene(this.mainLayoutPane));
        stage.setTitle(messages.getString("agenda"));
        stage.setWidth(1450);
        stage.setHeight(350);
        stage.show();
        //Play animation AFTER the show() method has been called, since the animation would
        //otherwise NOT be fully visible for the user.
        playAnimation();
    }

    @Override
    public void setup() {
        //Initialise values.
        this.agendaCanvas = new AgendaCanvas(this.agenda, this);

        //Adding all the children.
        //MenuBar
        fileMenu.getItems().addAll(loadAgendaMenuItem, saveAgendaMenuItem, new SeparatorMenuItem(), exitMenuItem);
        editMenu.getItems().addAll(editArtistsAndPodiumsMenuItem, new SeparatorMenuItem(), editCurrentlySelectedShow, new SeparatorMenuItem(), preferencesMenuItem);
        helpMenu.getItems().addAll(helpGuideMenuItem, javaDocMenuItem, aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        //ContextMenu
        contextMenu.getItems().addAll(swapContextItem, editContextItem, new SeparatorMenuItem(), removeContextItem);

        //Adding it all together.
        this.mainLayoutPane.setTop(this.menuBar);
        this.mainLayoutPane.setCenter(this.agendaCanvas.getMainPane());
    }

    @Override
    public void actionHandlingSetup() {
        //Generic
        this.stage.setOnCloseRequest(e -> { //When the main window is closed -> Close the entire program.
            Platform.exit();
        });

        //Canvas
        this.agendaCanvas.getCanvas().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) { //You can only select with a left-click.
                //In case it still shows it should be hidden, since a new item is selected and some
                //actions executed by the ContextMenu depend on selected items.
                contextMenu.hide();
                onPrimaryButton(e);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                if (getCurrentShow() != null){
                    contextMenu.show(agendaCanvas.getMainPane(), e.getScreenX(), e.getScreenY());
                }
            }
        });

        //MenuBar
            //FileMenu
        loadAgendaMenuItem.setOnAction(e -> {
            loadAgenda();
        });

        saveAgendaMenuItem.setOnAction(e -> {
            saveAgenda();
        });

        exitMenuItem.setOnAction(e -> {
            showExitConfirmationPopUp();
        });

            //EditMenu
        editArtistsAndPodiumsMenuItem.setOnAction(e -> {
            ArtistAndPodiumEditorGUI artistAndPodiumEditorGUI = new ArtistAndPodiumEditorGUI(this);
            artistAndPodiumEditorGUI.load();
        });

        editCurrentlySelectedShow.setOnAction(e -> {
            if (!this.artistManager.getAllArtistNames().isEmpty() && !this.podiumManager.getAllPodiumNames().isEmpty()) {
                ShowEditorGUI showEditorGUI = new ShowEditorGUI(this);
                showEditorGUI.load();
            } else {
                showNoArtistsOrPodiumsPopUp();
            }
        });

        preferencesMenuItem.setOnAction(e -> {
            PreferencesGUI preferencesGUI = new PreferencesGUI(this.stage);
            preferencesGUI.load();
        });
            //HelpMenu
        aboutMenuItem.setOnAction(e -> {
            AboutPopUp aboutPopUp = new AboutPopUp(this.stage);
            aboutPopUp.load();
        });

        //ContextMenu
            //Edit
        editContextItem.setOnAction(e -> {
            if (getCurrentShow() != null) {
                ShowEditorGUI showEditorGUI = new ShowEditorGUI(this);
                showEditorGUI.load();
            } else {
                showNoLayerSelectedPopUp();
            }
            this.agendaCanvas.reBuildAgendaCanvas();
        });
            //Remove
        removeContextItem.setOnAction(e -> {
            removeCurrentShows();
        });
    }

    /**
     * Plays a simple
     * <a href="https://javadoc.io/doc/io.github.typhon0/AnimateFX/latest/animatefx/animation/JackInTheBox.html">JackInTheBox</a>
     * animation after all other methods in {@link #load()} have been called, and animations are enabled in preferences.
     * @see SaveSettingsHandler
     */
    private void playAnimation() {
        if (SaveSettingsHandler.getPreference("use_animations").contains("true")){
            new JackInTheBox(this.mainLayoutPane).play();
        }
    }

    /**
     * CallBack method to open <code>this.artistPopup</code>.
     */
    public void artistPopupCallBack() {
        ArtistPopUp artistPopUp = new ArtistPopUp(this.stage, this.artistManager);
        artistPopUp.load();
    }

    /**
     * CallBack method to open <code>this.podiumCallBack</code>.
     */
    public void podiumPopupCallBack() {
        PodiumPopup podiumPopup = new PodiumPopup(this.stage, this.podiumManager);
        podiumPopup.load();
    }

    @Nullable
    private String getLoadAgendaPath() {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            return fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        } catch (NullPointerException e) {
            showExceptionPopUp(e);
        }
        return null;
    }

    private void loadAgenda() {
        SaveHandler saveHandler = new SaveHandler();
        this.agenda = saveHandler.readAgendaFromFile(getLoadAgendaPath());
        this.agendaCanvas.setAgenda(this.agenda);
        this.selectedShows.clear();

        //Update podiumManager and ArtistManager.
        for (Show show : this.agenda.getShows()) {
            if (show != null && show.getPodium() != null) {
                if (!this.podiumManager.containsPodium(show.getPodium().getName()))
                    this.podiumManager.addPodium(show.getPodium());
            }

            if (show != null && show.getArtists() != null) {
                for (Artist artist : show.getArtists()) {
                    if (artist != null && !this.artistManager.containsArtist(artist.getName()))
                        this.artistManager.addArtist(artist);
                }
            }
        }
    }

    /**
     * Opens a SaveDialog to let the user save the <code>Agenda</code> object as a <i>.dat</i> file.
     */
    private void saveAgenda() {
        FileChooser fileChooser = new FileChooser();
        SaveHandler saveHandler = new SaveHandler();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            String path = fileChooser.showSaveDialog(new Stage()).getAbsolutePath();
            saveHandler.writeAgendaToFile(path, this.agenda);
        } catch (NullPointerException e) {
            showExceptionPopUp(e);
        }
    }

    /**
     * Handles the selecting of <a href="{@docRoot}/FestivalPlanner/AgendaGUI/ShowRectangle2D.html">Rectangle2D</a> in
     * <code>this.agendaCanvas</code>.
     *
     * @param e  MouseEvent that was set on the mouseButtonClick
     */
    private void onPrimaryButton(MouseEvent e) {
        Show selectedShow = this.agendaCanvas.showAtPoint(new Point2D.Double(e.getX(), e.getY()));
//        this.agendaCanvas.rectangleOnShow(selectedShow).setColor(java.awt.Color.getHSBColor(100 / 360f, .7f, .7f));

        if (selectedShow != null) {
            //Allowing multiple shows to be selected.
                //If the person has clicked on the show AND is holding ctrl ->
            if (e.isControlDown()) {
                if (selectedShows.contains(selectedShow)) {
                    selectedShows.remove(selectedShow);
                } else if (!selectedShows.contains(selectedShow)) {
                    selectedShows.add(selectedShow);
                }
            } else if(selectedShow == getCurrentShow()) {
                selectedShows.clear();
            } else {
                selectedShows.clear();
                selectedShows.add(selectedShow);
            }
        }
        //Redraw canvas regardless of whether a show has been selected.
        this.agendaCanvas.reBuildAgendaCanvas();
    }

    /**
     * Removes the currently selected show from the ArrayList in <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.
     * <p>
     * This method does the following in this specific order:
     * <p><ul>
     * <li>Checks whether there is a show is selected, continues if it returns true.
     * <li>Shows the <code>showDeleteConfirmationPopUp()</code> in
     * <a href="{@docRoot}/FestivalPlanner/GUI/AgendaGUI/PopUpGUI/AbstractDialogPopUp.html">AbstractDialogPopUp</a>. Continues if user chooses to delete the show.
     * <li>Removes the show from <a href="{@docRoot}/FestivalPlanner/Agenda/Agenda.html">Agenda</a>.
     * <li>Sets the selected show to <i>null</i>.
     * <li>Rebuilds the <a href="{@docRoot}/AgendaGUI/AgendaCanvas.html">AgendaCanvas</a>.
     * </ul>
     */
    private void removeCurrentShows(){
        if (getSelectedShows().size() == 1){
            if (showDeleteConfirmationPopUp()){
                this.agenda.removeShow(this.getCurrentShow());
            }
        } else if (getSelectedShows().size() > 1){
            if (showDeleteConfirmationPopUp()){
                getSelectedShows().forEach(e -> {
                    this.agenda.removeShow(e);
                });
            }
        }
        this.setCurrentShow(null);
        this.agendaCanvas.reBuildAgendaCanvas();
    }

    /**
     * Sets the <code>this.currentShow</code> attribute to the parameter's value.
     * @return  this.currentShow
     */
    public void setCurrentShow(Show show) {
        this.selectedShows.clear();
        this.selectedShows.add(show);

        if (show != null) {
            if (this.agenda.getShows().contains(show)) {
                this.agenda.getShows().remove(show);
            }
            this.agenda.addShow(show);
            this.agendaCanvas.reBuildAgendaCanvas();
        }
    }

    /**
     * Returns the <code>this.currentShow</code> attribute.
     * @return  this.currentShow
     */
    @Nullable
    public Show getCurrentShow() {
        if (!this.selectedShows.isEmpty()){
            return this.selectedShows.get(this.selectedShows.size() - 1);
        } else {
            return null;
        }
    }

    public ArrayList<Show> getSelectedShows() {
        return this.selectedShows;
    }

    /**
     * Returns the <code>this.agenda</code> attribute.
     * @return  this.agenda
     */
    @NotNull
    public Agenda getAgenda() {
        return this.agenda;
    }

    /**
     * Returns the <code>this.artistManager</code> attribute.
     * @return  this.artistManager
     */
    @NotNull
    public ArtistManager getArtistManager() {
        return this.artistManager;
    }

    /**
     * Returns the <code>this.podiumManager</code> attribute.
     * @return  this.podiumManager
     */
    @NotNull
    public PodiumManager getPodiumManager() {
        return this.podiumManager;
    }
}
