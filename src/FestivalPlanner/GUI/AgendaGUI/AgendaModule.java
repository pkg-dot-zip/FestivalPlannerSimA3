package FestivalPlanner.GUI.AgendaGUI;

import FestivalPlanner.Agenda.*;
import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.ArtistPopUp;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.PodiumPopup;
import FestivalPlanner.GUI.CommonNodeRetriever;
import FestivalPlanner.GUI.MainGUI;
import FestivalPlanner.GUI.PreferencesGUI;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import FestivalPlanner.Util.PreferencesHandling.SaveSettingsHandler;
import animatefx.animation.JackInTheBox;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Responsible for placing everything in the correct place in the Agenda GUI and making sure all the buttons work.
 */
public class AgendaModule extends AbstractGUI implements Serializable {

    private Stage stage;
    private MainGUI mainGUI;

    //LanguageHandling.
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Agenda variables.
    private Agenda agenda = new Agenda();
    private ArtistManager artistManager = new ArtistManager();
    private PodiumManager podiumManager = new PodiumManager();
    private ArrayList<Show> selectedShows = new ArrayList<>();

    //Scenes.
    private Scene agendaScene;

    //Panes.
    private BorderPane mainLayoutPane = new BorderPane();
    private VBox topVBox = new VBox();
    private HBox toggleHBox = new HBox();

    //ToggleButtons.
    private Button agendaButton = new Button(messages.getString("agenda"));
    private Button simulatorButton = new Button(messages.getString("simulator"));
    private Button useButton = new Button(messages.getString("simulate_agenda"));

    //MenuBar.
    private MenuBar menuBar = new MenuBar();
    //FileMenu.
    private Menu fileMenu = new Menu(messages.getString("file"));
    private MenuItem loadAgendaMenuItem = new MenuItem(messages.getString("load"));
    private MenuItem saveAgendaMenuItem = new MenuItem(messages.getString("save"));
    private MenuItem exitMenuItem = new MenuItem(messages.getString("exit"));
    //EditMenu.
    private Menu editMenu = new Menu(messages.getString("edit"));
    private MenuItem editCurrentlySelectedShow = new MenuItem(messages.getString("edit_show"));
    private MenuItem editArtistsAndPodiumsMenuItem = new MenuItem(messages.getString("edit_artists_and_podiums"));
    private MenuItem preferencesMenuItem = new MenuItem(messages.getString("preferences"));

    //Layout components.
    private AgendaCanvas agendaCanvas;

    //ContextMenu.
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem swapContextItem = new MenuItem(messages.getString("swap"));
    private MenuItem editContextItem = new MenuItem(messages.getString("edit"));
    private MenuItem removeContextItem = new MenuItem(messages.getString("remove"));

    /**
     * Constructor of <code>AgendaModule</code>.
     * <p>
     * The given <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a> will be stored
     * as a parameter so this stage can be referenced as the main
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>.
     * @param stage  will be stored
     *              as a parameter so this stage can be referenced as the owner of the sub stages
     *              <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html">Podium</a>
     */
    public AgendaModule(MainGUI mainGUI, Stage stage) {
        this.mainGUI = mainGUI;
        this.stage = stage;
    }

    @Override
    public void load() {
        //Setup methods.
        setup();
        actionHandlingSetup();

        //Stage Settings.
        stage.setScene(this.agendaScene);
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
        this.agendaButton.setDisable(true);
        this.simulatorButton.setDisable(true);

        //Alignment & Spacing.
        this.topVBox.setSpacing(VBOX_SPACING);
        this.toggleHBox.setAlignment(Pos.CENTER);
        this.toggleHBox.setSpacing(HBOX_SPACING);

        //Adding all the children.
            //MenuBar.
        this.fileMenu.getItems().addAll(this.loadAgendaMenuItem, this.saveAgendaMenuItem, new SeparatorMenuItem(), this.exitMenuItem);
        this.editMenu.getItems().addAll(this.editArtistsAndPodiumsMenuItem, new SeparatorMenuItem(), this.editCurrentlySelectedShow, new SeparatorMenuItem(), this.preferencesMenuItem);
        this.menuBar.getMenus().addAll(this.fileMenu, this.editMenu, CommonNodeRetriever.getHelpMenu(this.stage));
            //ContextMenu.
        this.contextMenu.getItems().addAll(this.swapContextItem, this.editContextItem, new SeparatorMenuItem(), this.removeContextItem);
            //Panes.
        this.topVBox.getChildren().addAll(this.menuBar, this.toggleHBox);
        this.toggleHBox.getChildren().addAll(this.agendaButton, this.simulatorButton, this.useButton);

        //Adding it all together.
        this.mainLayoutPane.setTop(this.topVBox);
        this.mainLayoutPane.setCenter(this.agendaCanvas.getMainPane());
        this.agendaScene = new Scene(this.mainLayoutPane);
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
                this.contextMenu.hide();
                onPrimaryButton(e);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                if (getCurrentShow() != null){
                    this.contextMenu.show(this.agendaCanvas.getMainPane(), e.getScreenX(), e.getScreenY());
                }
            }
        });

        //Top Buttons
        this.simulatorButton.setOnAction(event -> {
            this.mainGUI.loadSimulatorCallBack();
        });

        this.useButton.setOnAction(e -> {
            this.mainGUI.constructSimulatorCallBack(this);
            this.simulatorButton.setDisable(false);
        });

        //MenuBar
            //FileMenu
        this.loadAgendaMenuItem.setOnAction(e -> {
            loadAgenda();
        });

        this.saveAgendaMenuItem.setOnAction(e -> {
            saveAgenda();
        });

        this.exitMenuItem.setOnAction(e -> {
            AbstractDialogPopUp.showExitConfirmationPopUp();
        });

            //EditMenu
        this.editArtistsAndPodiumsMenuItem.setOnAction(e -> {
            ArtistAndPodiumEditorGUI artistAndPodiumEditorGUI = new ArtistAndPodiumEditorGUI(this,
                    this.podiumManager.getObservablePodiumList(),
                    this.artistManager.getObservableArtistList());
            artistAndPodiumEditorGUI.load();
        });

        this.editCurrentlySelectedShow.setOnAction(e -> {
            if (!this.artistManager.getAllArtistNames().isEmpty() && !this.podiumManager.getAllPodiumNames().isEmpty()) {
                ShowEditorGUI showEditorGUI = new ShowEditorGUI(this);
                showEditorGUI.load();
            } else {
                AbstractDialogPopUp.showNoArtistsOrPodiumsPopUp();
            }
        });

        this.preferencesMenuItem.setOnAction(e -> {
            PreferencesGUI preferencesGUI = new PreferencesGUI(this.stage);
            preferencesGUI.load();
        });

        //ContextMenu
            //Edit
        this.editContextItem.setOnAction(e -> {
            if (getCurrentShow() != null) {
                ShowEditorGUI showEditorGUI = new ShowEditorGUI(this);
                showEditorGUI.load();
            } else {
                AbstractDialogPopUp.showNoLayerSelectedPopUp();
            }
            this.agendaCanvas.reBuildAgendaCanvas();
        });

            //Swap
        this.swapContextItem.setOnAction(e -> {
            if (getSelectedShows().size() < 2){
                AbstractDialogPopUp.showNoLayerSelectedPopUp();
            } else if (getSelectedShows().size() > 2){
                AbstractDialogPopUp.showTooManyLayersSelectedPopUp(getSelectedShows().size());
            } else {
                Show showA = new Show(getSelectedShows().get(0));
                Show showB = new Show(getSelectedShows().get(1));

                getSelectedShows().get(0).setStartTime(showB.getStartTime());
                getSelectedShows().get(0).setEndTime(showB.getEndTime());
                getSelectedShows().get(1).setStartTime(showA.getStartTime());
                getSelectedShows().get(1).setEndTime(showA.getEndTime());

                this.agendaCanvas.reBuildAgendaCanvas();
            }
        });

            //Remove
        this.removeContextItem.setOnAction(e -> {
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
    void artistPopupCallBack() {
        ArtistPopUp artistPopUp = new ArtistPopUp(this.stage, this.artistManager);
        artistPopUp.load();
    }

    /**
     * CallBack method to open <code>this.podiumCallBack</code>.
     */
    void podiumPopupCallBack() {
        PodiumPopup podiumPopup = new PodiumPopup(this.stage, this.podiumManager);
        podiumPopup.load(); //TODO: Refactor so this is one line.
    }

    /**
     * CallBack method to open <code>this.artistPopup</code> to edit an
     * <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a>.
     * @param selectedArtist  the <a href="{@docRoot}/FestivalPlanner/Agenda/Artist.html">Artist</a> that will be edited
     */
    void artistPopupEditCallBack(Artist selectedArtist) {
        ArtistPopUp artistPopUp = new ArtistPopUp(this.stage, this.artistManager, selectedArtist);
        artistPopUp.load(); //TODO: Refactor so this is one line.
        this.agendaCanvas.reBuildAgendaCanvas();
    }

    /**
     * CallBack method to open <code>this.podiumPopup</code> to edit a
     * <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a>.
     * @param selectedPodium  the <a href="{@docRoot}/FestivalPlanner/Agenda/Podium.html">Podium</a> that will be edited
     */
    void podiumPopupEditCallBack(Podium selectedPodium) {
        PodiumPopup podiumPopup = new PodiumPopup(this.stage, this.podiumManager, selectedPodium);
        podiumPopup.load();
        this.agendaCanvas.reBuildAgendaCanvas();
    }

    @Nullable
    private String getLoadAgendaPath() {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Agenda File", "*.dat"));
            return fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        } catch (NullPointerException e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
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
            AbstractDialogPopUp.showExceptionPopUp(e);
        }
    }

    /**
     * Handles the selecting of <a href="{@docRoot}/FestivalPlanner/AgendaGUI/ShowRectangle2D.html">Rectangle2D</a> in
     * <code>this.agendaCanvas</code>.
     * @param e  MouseEvent that was set on the mouseButtonClick
     */
    private void onPrimaryButton(MouseEvent e) {
        Show selectedShow = this.agendaCanvas.showAtPoint(new Point2D.Double(e.getX(), e.getY()));

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
            if (AbstractDialogPopUp.showDeleteConfirmationPopUp()){
                this.agenda.removeShow(this.getCurrentShow());
            }
        } else if (getSelectedShows().size() > 1){
            if (AbstractDialogPopUp.showDeleteConfirmationPopUp()){
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
     */
    void setCurrentShow(Show show) {
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
    Show getCurrentShow() {
        if (!this.selectedShows.isEmpty()){
            return this.selectedShows.get(this.selectedShows.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Returns the <code>this.selectedShows</code> attribute.
     * @return  this.selectedShows
     */
    ArrayList<Show> getSelectedShows() {
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

    /**
     * Returns the <code>this.agendaScene</code> attribute.
     * @return  this.agendaScene
     */
    @NotNull
    public Scene getAgendaScene() {
        return this.agendaScene;
    }
}
