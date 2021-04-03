package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.GUI.CommonNodeRetriever;
import FestivalPlanner.Logic.SimulatorHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

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
        npcAmountField.setText(this.handler.getNPCAmount() + "");

        //Adding all the children.
        addNpcHBox.getChildren().addAll(remove10Button, remove5Button, remove1Button, add1Button, add5Button, add10Button);
        mainPanel.getChildren().addAll(npcAmountLabel, npcAmountField, addNpcHBox, new Label(), new Separator(), bottomHBox);

        //Adding it all together.
        gridPane.add(mainPanel, 0, 0);
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