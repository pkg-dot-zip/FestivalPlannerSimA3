package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.CommonNodeRetriever;
import FestivalPlanner.Logic.SimulatorHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javax.tools.Tool;

/**
 * Contains methods and attributes seen in the NPCEditGUI window.
 * <p>
 * This window is used to edit the amount of <b>NPC</b>s currently in the simulation.
 */
public class NPCEditGUI extends AbstractEditGUI {

    private Label npcAmountLabel = new Label(messages.getString("npc_Amount"));
    private TextField npcAmountField = new TextField();

    private HBox addNpcHBox = CommonNodeRetriever.getEditGUIHBox();

    //Buttons.
        //Remove Buttons.
    private Button remove10Button = new Button(messages.getString("remove") + " " + 10);
    private Button remove5Button = new Button(messages.getString("remove") + " " + 5);
    private Button remove1Button = new Button(messages.getString("remove") + " " + 1);
        //Add Buttons.
    private Button add10Button = new Button(messages.getString("add") + " " + 10);
    private Button add5Button = new Button(messages.getString("add") + " " + 5);
    private Button add1Button = new Button(messages.getString("add") + " " + 1);

    //Tooltips.
        //Remove Button-tooltips.
    private Tooltip remove10Tooltip = new Tooltip(messages.getString("remove") + " " + 10);
    private Tooltip remove5Tooltip = new Tooltip(messages.getString("remove") + " " + 5);
    private Tooltip remove1Tooltip = new Tooltip(messages.getString("remove") + " " + 1);
        //Add Button-tooltips.
    private Tooltip add10Tooltip = new Tooltip(messages.getString("add") + " " + 10);
    private Tooltip add5Tooltip = new Tooltip(messages.getString("add") + " " + 5);
    private Tooltip add1Tooltip = new Tooltip(messages.getString("add") + " " + 1);

    /**
     * Constructor of <b>NPCEditGUI</b> taking a <a href="{@docRoot}/FestivalPlanner/Logic/SimulatorHandler.html">SimulatorHandler</a> as parameter.
     * @param handler  handler to set <code>this.handler</code> to
     */
    public NPCEditGUI(SimulatorHandler handler) {
        this.handler = handler;
    }

    @Override
    public void load() {
        this.setup();
        this.actionHandlingSetup();

        //Stage Settings.
        CommonNodeRetriever.processEditGUIStage(this.stage, this.gridPane);
    }

    @Override
    public void setup() {
        genericSetup();

        //Initialising Values.
        this.npcAmountField.setText(this.handler.getNPCAmount() + "");

        //Adding Tooltips.
            //Remove Buttons.
        this.remove10Button.setTooltip(remove10Tooltip);
        this.remove5Button.setTooltip(remove5Tooltip);
        this.remove1Button.setTooltip(remove1Tooltip);
            //Add Buttons.
        this.add10Button.setTooltip(add10Tooltip);
        this.add5Button.setTooltip(add5Tooltip);
        this.add1Button.setTooltip(add1Tooltip);

        //Adding all the children.
        this.addNpcHBox.getChildren().addAll(remove10Button, remove5Button, remove1Button, add1Button, add5Button, add10Button);
        this.mainPanel.getChildren().addAll(npcAmountLabel, npcAmountField, addNpcHBox, new Label(), new Separator(), bottomHBox);

        //Adding it all together.
        this.gridPane.add(mainPanel, 0, 0);
    }

    @Override
    public void actionHandlingSetup() {
        genericActionHandlingSetup();

        //Remove buttons.
        this.remove1Button.setOnAction(e -> addNPC(-1));
        this.remove5Button.setOnAction(e -> addNPC(-5));
        this.remove10Button.setOnAction(e -> addNPC(-10));

        //Add buttons.
        this.add1Button.setOnAction(e -> addNPC(1));
        this.add5Button.setOnAction(e -> addNPC(5));
        this.add10Button.setOnAction(e -> addNPC(10));
    }

    /**
     * Adds an amount of <b>NPC</b>s to the current simulation, based on the parameter's value.
     * @param number  amount of <b>NPC</b>s to add
     */
    private void addNPC(int number) {
        String text = this.npcAmountField.getText().trim();
        if (Integer.parseInt(text) + number >= 0) {
            this.npcAmountField.setText((Integer.parseInt(text) + number) + " ");
        }
    }

    @Override
    void onApply() {
        try {
            this.handler.setNPCAmount(Integer.parseInt(this.npcAmountField.getText().trim()));
        } catch (Exception ex) {
            AbstractDialogPopUp.showExceptionPopUp(ex);
        }
    }
}