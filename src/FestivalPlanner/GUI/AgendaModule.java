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

    public Scene buildAgendaModule(){
        VBox mainPane = new VBox();

        ArrayList<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Peter Gabriel", null, null));

        Stage stageA = new Stage("StageA", "Linksachter");
        Stage stageB = new Stage("StageB", "Rechtsvoor");

        Agenda agenda = new Agenda("test");
        agenda.addShow(new Show("show1",
                LocalDateTime.of(2021,2,14,10,0),
                LocalDateTime.of(2021,2,14,21,0),
                100,
                stageA,
                artists));

        agenda.addShow(new Show("show2",
                LocalDateTime.of(2021,2,14,11,0),
                LocalDateTime.of(2021,2,14,18,30),
                100,
                stageB,
                artists));

        agenda.addShow(new Show("show1",
                LocalDateTime.of(2021,2,14,10,0),
                LocalDateTime.of(2021,2,14,21,0),
                100,
                stageA,
                artists));

        AgendaCanvas agendaCanvas = new AgendaCanvas(agenda);
        mainPane.getChildren().addAll(agendaCanvas.buildAgendaCanvas());

        return new Scene(mainPane);
    }
}