import FestivalPlanner.Util.JSONConverter;

public class Main {

    public static void main(String[] args) {

        String fileName = "/testMap.json";
        JSONConverter jsonConverter = new JSONConverter();
        jsonConverter.JSONToTileMap(fileName);

    }
}
