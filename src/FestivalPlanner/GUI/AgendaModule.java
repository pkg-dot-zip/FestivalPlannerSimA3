package FestivalPlanner.GUI;

import FestivalPlanner.Agenda.Agenda;
import FestivalPlanner.Agenda.Artist;
import FestivalPlanner.Agenda.Show;
import FestivalPlanner.Agenda.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AgendaModule{

    public AgendaModule() {
    }

    /**
     * muil dicht, tis tijdelijk @jesse
     */
    public Scene buildAgendaModule(){
        VBox mainPane = new VBox();

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Peter Gabriel", null, null));

        Stage stageA = new Stage("StageA", "Linksachter");
        Stage stageB = new Stage("Stage 62", "Rechtsvoor");
        Stage BauerMania = new Stage("BauerMania", "Bij Frans Bauer in de achtertuin");

        Agenda agenda = new Agenda("test");
        agenda.addShow(new Show("show Teun",
                LocalDateTime.of(2021,2,14,1,0),
                LocalDateTime.of(2021,2,14,4,30),
                100,
                stageA,
                artists));

        agenda.addShow(new Show("show Jesse",
                LocalDateTime.of(2021,2,14,4,5),
                LocalDateTime.of(2021,2,14,6,0),
                100,
                stageA,
                artists));

        agenda.addShow(new Show("show 12",
                LocalDateTime.of(2021,2,14,10,15),
                LocalDateTime.of(2021,2,14,13,0),
                100,
                stageA,
                artists));

        agenda.addShow(new Show("Show TI",
                LocalDateTime.of(2021,2,14,10,0),
                LocalDateTime.of(2021,2,14,15,30),
                100,
                stageB,
                artists));

        agenda.addShow(new Show("show TI V2 EXTREME",
                LocalDateTime.of(2021,2,14,15,40),
                LocalDateTime.of(2021,2,14,19,15),
                100,
                stageB,
                artists));

        agenda.addShow(new Show("Bauer in concert",
                LocalDateTime.of(2021,2,14,8,45),
                LocalDateTime.of(2021,2,14,20,45),
                100,
                BauerMania,
                artists));

        AgendaCanvas agendaCanvas = new AgendaCanvas(agenda);
        mainPane.getChildren().add(agendaCanvas.buildAgendaCanvas());

        return new Scene(mainPane);
    }
}