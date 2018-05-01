package g1;

import battleship.interfaces.Position;
import java.util.ArrayList;

/**
 * @author Michael This class only purpose is to count how many times we hit the
 * enemies ships and which position they are placed on. So we can calculate
 * better shoot algorithme.
 */
public class HitPosCounter {

    // Singleton
    private static HitPosCounter instance = null;

    protected HitPosCounter() {
        // Exists only to defeat instantiation.
    }

    public static HitPosCounter getInstance() {
        if (instance == null) {
            instance = new HitPosCounter();
        }
        return instance;
    }

    private ArrayList<Position> positions = new ArrayList();
    private ArrayList<Integer> count = new ArrayList();

    public void setupData(boolean needsReset) {
        if (needsReset) {
            resetCounter();
        }
        setupPositions();
        setupCounter();
    }

    private void setupPositions() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Position pos = new Position(i, j);
                positions.add(pos);
            }
        }
    }

    private void setupCounter() {
        for (int i = 0; i < 100; i++) {
            count.add(0);
        }
    }

    private void resetCounter() {
        for (int i = 0; i < 100; i++) {
            count.set(i, 0);
        }
    }

    public void countPosition(Position pos) {
        int in = positions.indexOf(pos); // in = Index Number
        count.set(count.get(in), count.get(in) + 1);
    }

    public ArrayList<Integer> getCounter() {
        return count;
    }
}
