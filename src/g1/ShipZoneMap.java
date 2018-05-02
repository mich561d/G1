package g1;

import battleship.interfaces.Position;
import java.util.ArrayList;

/**
 * @author Michael
 */
public class ShipZoneMap {

    private final ArrayList<Position> DESPOS = new ArrayList();
    private final ArrayList<Position> CRUPOS = new ArrayList();
    private final ArrayList<Position> BATPOS = new ArrayList();
    private final ArrayList<Position> CARPOS = new ArrayList();

    public boolean destroyerMap(Position start, Position end) {
        if (DESPOS.isEmpty()) {
            setupDestroyerPos();
        }
        // A = Aproved
        boolean startA = false;
        boolean endA = false;
        for (int i = 0; i < DESPOS.size(); i++) {
            if (DESPOS.get(i).equals(start)) {
                startA = true;
            } else if (DESPOS.get(i).equals(end)) {
                endA = true;
            }
        }
        return startA && endA;
    }

    public boolean cruiserMap(Position start, Position mid, Position end) {
        if (CRUPOS.isEmpty()) {
            setupCruiserPos();
        }
        // A = Aproved
        boolean startA = false;
        boolean midA = false;
        boolean endA = false;
        for (int i = 0; i < DESPOS.size(); i++) {
            if (DESPOS.get(i).equals(start)) {
                startA = true;
            } else if (DESPOS.get(i).equals(mid)) {
                midA = true;
            } else if (DESPOS.get(i).equals(end)) {
                endA = true;
            }
        }
        return startA && midA && endA;
    }

    public boolean battleshipMap(Position start, Position startMid, Position endMid, Position end) {
        if (BATPOS.isEmpty()) {
            setupBattleshipPos();
        }
        // A = Aproved
        boolean startA = false;
        boolean startMidA = false;
        boolean endMidA = false;
        boolean endA = false;
        for (int i = 0; i < DESPOS.size(); i++) {
            if (DESPOS.get(i).equals(start)) {
                startA = true;
            } else if (DESPOS.get(i).equals(startMid)) {
                startMidA = true;
            } else if (DESPOS.get(i).equals(endMid)) {
                endMidA = true;
            } else if (DESPOS.get(i).equals(end)) {
                endA = true;
            }
        }
        return startA && startMidA && endMidA && endA;
    }

    public boolean carrierMap(Position start, Position startMid, Position mid, Position endMid, Position end) {
        if (CARPOS.isEmpty()) {
            setupCarrierPos();
        }
        // A = Aproved
        boolean startA = false;
        boolean startMidA = false;
        boolean midA = false;
        boolean endMidA = false;
        boolean endA = false;
        for (int i = 0; i < DESPOS.size(); i++) {
            if (DESPOS.get(i).equals(start)) {
                startA = true;
            } else if (DESPOS.get(i).equals(startMid)) {
                startMidA = true;
            } else if (DESPOS.get(i).equals(mid)) {
                midA = true;
            } else if (DESPOS.get(i).equals(endMid)) {
                endMidA = true;
            } else if (DESPOS.get(i).equals(end)) {
                endA = true;
            }
        }
        return startA && startMidA && midA && endMidA && endA;
    }

    private void setupDestroyerPos() {
        DESPOS.add(new Position(2, 4));
        DESPOS.add(new Position(2, 5));
        DESPOS.add(new Position(3, 4));
        DESPOS.add(new Position(3, 5));
        DESPOS.add(new Position(4, 2));
        DESPOS.add(new Position(4, 3));
        DESPOS.add(new Position(4, 6));
        DESPOS.add(new Position(4, 7));
        DESPOS.add(new Position(5, 2));
        DESPOS.add(new Position(5, 3));
        DESPOS.add(new Position(5, 6));
        DESPOS.add(new Position(5, 7));
        DESPOS.add(new Position(6, 4));
        DESPOS.add(new Position(7, 4));
        DESPOS.add(new Position(7, 5));
    }

    private void setupCruiserPos() {
        CRUPOS.add(new Position(0, 1));
        CRUPOS.add(new Position(0, 2));
        CRUPOS.add(new Position(0, 3));
        CRUPOS.add(new Position(0, 4));
        CRUPOS.add(new Position(1, 0));
        CRUPOS.add(new Position(1, 2));
        CRUPOS.add(new Position(1, 3));
        CRUPOS.add(new Position(2, 0));
        CRUPOS.add(new Position(2, 1));
        CRUPOS.add(new Position(2, 3));
        CRUPOS.add(new Position(3, 0));
        CRUPOS.add(new Position(3, 1));
        CRUPOS.add(new Position(3, 2));
        CRUPOS.add(new Position(4, 0));
        CRUPOS.add(new Position(5, 9));
        CRUPOS.add(new Position(6, 7));
        CRUPOS.add(new Position(6, 8));
        CRUPOS.add(new Position(6, 9));
        CRUPOS.add(new Position(7, 6));
        CRUPOS.add(new Position(7, 8));
        CRUPOS.add(new Position(7, 9));
        CRUPOS.add(new Position(8, 6));
        CRUPOS.add(new Position(8, 7));
        CRUPOS.add(new Position(8, 9));
        CRUPOS.add(new Position(9, 5));
        CRUPOS.add(new Position(9, 6));
        CRUPOS.add(new Position(9, 7));
        CRUPOS.add(new Position(9, 8));
    }

    private void setupBattleshipPos() {
        BATPOS.add(new Position(0, 5));
        BATPOS.add(new Position(0, 6));
        BATPOS.add(new Position(0, 7));
        BATPOS.add(new Position(0, 8));
        BATPOS.add(new Position(1, 6));
        BATPOS.add(new Position(1, 7));
        BATPOS.add(new Position(1, 8));
        BATPOS.add(new Position(1, 9));
        BATPOS.add(new Position(2, 6));
        BATPOS.add(new Position(2, 7));
        BATPOS.add(new Position(2, 8));
        BATPOS.add(new Position(2, 9));
        BATPOS.add(new Position(3, 7));
        BATPOS.add(new Position(3, 8));
        BATPOS.add(new Position(3, 9));
        BATPOS.add(new Position(4, 9));
        BATPOS.add(new Position(5, 0));
        BATPOS.add(new Position(6, 0));
        BATPOS.add(new Position(6, 1));
        BATPOS.add(new Position(6, 2));
        BATPOS.add(new Position(7, 0));
        BATPOS.add(new Position(7, 1));
        BATPOS.add(new Position(7, 2));
        BATPOS.add(new Position(7, 3));
        BATPOS.add(new Position(8, 0));
        BATPOS.add(new Position(8, 1));
        BATPOS.add(new Position(8, 2));
        BATPOS.add(new Position(8, 3));
        BATPOS.add(new Position(9, 1));
        BATPOS.add(new Position(9, 2));
        BATPOS.add(new Position(9, 3));
        BATPOS.add(new Position(9, 4));
    }

    private void setupCarrierPos() {
        CARPOS.add(new Position(0, 1));
        CARPOS.add(new Position(0, 2));
        CARPOS.add(new Position(0, 7));
        CARPOS.add(new Position(0, 8));
        CARPOS.add(new Position(1, 0));
        CARPOS.add(new Position(1, 1));
        CARPOS.add(new Position(1, 2));
        CARPOS.add(new Position(1, 3));
        CARPOS.add(new Position(1, 4));
        CARPOS.add(new Position(1, 5));
        CARPOS.add(new Position(1, 6));
        CARPOS.add(new Position(1, 7));
        CARPOS.add(new Position(1, 8));
        CARPOS.add(new Position(1, 9));
        CARPOS.add(new Position(2, 0));
        CARPOS.add(new Position(2, 1));
        CARPOS.add(new Position(2, 2));
        CARPOS.add(new Position(2, 3));
        CARPOS.add(new Position(2, 4));
        CARPOS.add(new Position(2, 5));
        CARPOS.add(new Position(2, 6));
        CARPOS.add(new Position(2, 7));
        CARPOS.add(new Position(2, 8));
        CARPOS.add(new Position(2, 9));
        CARPOS.add(new Position(3, 1));
        CARPOS.add(new Position(3, 2));
        CARPOS.add(new Position(3, 7));
        CARPOS.add(new Position(3, 8));
        CARPOS.add(new Position(4, 1));
        CARPOS.add(new Position(4, 2));
        CARPOS.add(new Position(4, 7));
        CARPOS.add(new Position(4, 8));
        CARPOS.add(new Position(5, 1));
        CARPOS.add(new Position(5, 2));
        CARPOS.add(new Position(5, 7));
        CARPOS.add(new Position(5, 8));
        CARPOS.add(new Position(6, 1));
        CARPOS.add(new Position(6, 2));
        CARPOS.add(new Position(6, 7));
        CARPOS.add(new Position(6, 8));
        CARPOS.add(new Position(7, 0));
        CARPOS.add(new Position(7, 1));
        CARPOS.add(new Position(7, 2));
        CARPOS.add(new Position(7, 3));
        CARPOS.add(new Position(7, 4));
        CARPOS.add(new Position(7, 5));
        CARPOS.add(new Position(7, 6));
        CARPOS.add(new Position(7, 7));
        CARPOS.add(new Position(7, 8));
        CARPOS.add(new Position(7, 9));
        CARPOS.add(new Position(8, 0));
        CARPOS.add(new Position(8, 1));
        CARPOS.add(new Position(8, 2));
        CARPOS.add(new Position(8, 3));
        CARPOS.add(new Position(8, 4));
        CARPOS.add(new Position(8, 5));
        CARPOS.add(new Position(8, 6));
        CARPOS.add(new Position(8, 7));
        CARPOS.add(new Position(8, 8));
        CARPOS.add(new Position(8, 9));
        CARPOS.add(new Position(9, 1));
        CARPOS.add(new Position(9, 2));
        CARPOS.add(new Position(9, 7));
        CARPOS.add(new Position(9, 8));
    }
}
