package FestivalPlanner.GUI.SimulatorGUI;

import FestivalPlanner.GUI.AbstractGUI;
import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;
import FestivalPlanner.Logic.SimulatorHandler;
import FestivalPlanner.Util.LanguageHandling.LanguageHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class NPCEditGUI extends AbstractGUI {

    private SimulatorHandler handler;

    //LanguageHandling
    private ResourceBundle messages = LanguageHandler.getMessages();

    //Main Scene Components.
    private Stage stage = new Stage();

    //JavaFX Components
    private VBox mainPanel = new VBox();

    private Label npcAmountLabel = new Label(messages.getString("npc_Amount"));
    private TextField npcAmountField = new TextField();

    private HBox addNpcHBox = new HBox();

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
        this.stage.setTitle(messages.getString("show_editor"));
        this.stage.setScene(new Scene(gridPane));
        this.stage.setResizable(true);
        this.stage.setWidth(450);
        this.stage.setHeight(250);
        this.stage.setIconified(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.showAndWait();
    }

    @Override
    public void setup() {

        //NPC add HBOC setup
        addNpcHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        addNpcHBox.setMaxHeight(150);
        addNpcHBox.setAlignment(Pos.BASELINE_CENTER);
        addNpcHBox.setSpacing(10);

        addNpcHBox.getChildren().addAll(remove10Button, remove5Button, remove1Button, add1Button, add5Button, add10Button);


        //Main VBox setup
        mainPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), new Insets(-5))));
        mainPanel.setMaxHeight(150);
        mainPanel.setAlignment(Pos.BASELINE_CENTER);
        mainPanel.setSpacing(10);

        npcAmountField.setText(this.handler.getNPCAmount() + "");

        //Apply and close button
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().addAll(this.applyButton, this.closeButton);


        mainPanel.getChildren().addAll(npcAmountLabel, npcAmountField, addNpcHBox, new Label(), new Separator(), bottomHBox);

        //GridPane
        gridPane.setVgap(50);
        gridPane.setHgap(50);
        gridPane.setAlignment(Pos.CENTER);

        //Adding it all together
        gridPane.add(mainPanel, 0, 0);

    }

    @Override
    public void actionHandlingSetup() {

        this.remove1Button.setOnAction(e -> addNPC(-1));
        this.remove5Button.setOnAction(e -> addNPC(-5));
        this.remove10Button.setOnAction(e -> addNPC(-10));

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
