package g1;

import battleship.interfaces.Position;
import maps.Map;

public class StatsMaps {

    private final Map HITMAP; // Our shots
    private final Map INCOMINGMAP; // Enemy shots 
    private final Map SHIPMAP; // Our ships
    private final Map ESHIPMAP; // Enemy ships

    public StatsMaps(int xSize, int ySize) {
        HITMAP = new Map(xSize, ySize);
        INCOMINGMAP = new Map(xSize, ySize);
        SHIPMAP = new Map(xSize, ySize);
        ESHIPMAP = new Map(xSize, ySize);
    }

    public void PickMap(String map, Position pos) {
        switch (map) {
            case "HITMAP":
                Count(HITMAP, pos);
                break;
            case "INCOMINGMAP":
                Count(INCOMINGMAP, pos);
                break;
            case "SHIPMAP":
                Count(SHIPMAP, pos);
                break;
            case "ESHIPMAP":
                Count(ESHIPMAP, pos);
                break;
            default:
                break;
        }
    }

    private void Count(Map map, Position pos) {
        map.add(pos.x, pos.y, 1);
    }

    public Map GetMap(String map) {
        Map mapToReturn = null;
        switch (map) {
            case "HITMAP":
                mapToReturn = HITMAP;
                break;
            case "INCOMINGMAP":
                mapToReturn = INCOMINGMAP;
                break;
            case "SHIPMAP":
                mapToReturn = SHIPMAP;
                break;
            case "ESHIPMAP":
                mapToReturn = ESHIPMAP;
                break;
            default:
                break;
        }
        return mapToReturn;
    }
}
