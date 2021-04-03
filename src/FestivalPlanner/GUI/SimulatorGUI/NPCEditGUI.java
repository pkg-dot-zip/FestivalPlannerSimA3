package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.HelpMenu;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class NPCEditGUI extends AbstractGUI {

    private SimulatorHandler handler;

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Main Scene Components.
    private Stage stage = new Stage();

    //JavaFX Components
    private VBox mainPanel = HelpMenu.getEditGUIMainPanel();

    private Label npcAmountLabel = new Label(messages.getString("npc_Amount"));
    private TextField npcAmountField = new TextField();

    private HBox addNpcHBox = HelpMenu.getEditGUIHBox();
    private HBox bottomHBox = new HBox();

        //Remove Buttons
    private Button remove10Button = new Button(messages.getString("remove") + " " + 10);
    private Button remove5Button = new Button(messages.getString("remove") + " " + 5);
    private Button remove1Button = new Button(messages.getString("remove") + " " + 1);
        //Add Buttons
    private Button add10Button = new Button(messages.getString("add") + " " + 10);
    private Button add5Button = new Button(messages.getString("add") + " " + 5);
    private Button add1Button = new Button(messages.getString("add") + " " + 1);

        //Apply buttons
    private Button applyButton = new Button(messages.getString("apply"));
    private Button closeButton = new Button(messages.getString("close"));

    public NPCEditGUI(SimulatorHandler handler) {
        this.handler = handler;
    }

    @Override
    public void load() {
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        HelpMenu.processEditGUIStage(this.stage, this.gridPane);
    }

    @Override
    public void setup() {
        //Initialising Values.
        npcAmountField.setText(this.handler.getNPCAmount() + "");

        //Alignment & spacing.
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);
        bottomHBox.setAlignment(Pos.CENTER);

        //Adding all the children.
        addNpcHBox.getChildren().addAll(remove10Button, remove5Button, remove1Button, add1Button, add5Button, add10Button);
        bottomHBox.getChildren().addAll(this.applyButton, this.closeButton);
        mainPanel.getChildren().addAll(npcAmountLabel, npcAmountField, addNpcHBox, new Label(), new Separator(), bottomHBox);

        //Adding it all together.
        gridPane.add(mainPanel, 0, 0);
    }

    @Override
    public void actionHandlingSetup() {
        //Remove buttons.
        this.remove1Button.setOnAction(e -> addNPC(-1));
        this.remove5Button.setOnAction(e -> addNPC(-5));
        this.remove10Button.setOnAction(e -> addNPC(-10));

        //Add buttons.
        this.add1Button.setOnAction(e -> addNPC(1));
        this.add5Button.setOnAction(e -> addNPC(5));
        this.add10Button.setOnAction(e -> addNPC(10));

        this.applyButton.setOnAction(e -> {
            try {
                this.handler.setNPCAmount(Integer.parseInt(this.npcAmountField.getText().trim()));
            } catch (Exception ex) {
                AbstractDialogPopUp.showExceptionPopUp(ex);
            }
        });

        closeButton.setOnAction(e -> {
            this.stage.close();
        });
    }

    private void addNPC(int number) {
        String text = this.npcAmountField.getText().trim();
        if (Integer.parseInt(text) + number >= 0) {
            this.npcAmountField.setText((Integer.parseInt(text) + number) + " ");
        }
    }
}