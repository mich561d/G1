package g1;

import battleship.interfaces.Position;
import java.util.ArrayList;

public class Memory {

    // Singleton
    private static Memory instance = null;

    protected Memory() {
        // Exists only to defeat instantiation.
    }

    public static Memory getInstance() {
        if (instance == null) {
            instance = new Memory();
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
        count.set(count.get(in), 1);
    }

    public ArrayList<Integer> getCounter() {
        return count;
    }
}
