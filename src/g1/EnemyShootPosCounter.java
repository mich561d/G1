package g1;

import java.util.ArrayList;
import battleship.interfaces.Position;

/**
 * @author Michael
 * This class only purpose is to count
 * how many times the enemy hits which
 * position. So we can calculate a better
 * position for our own fleet.
 */
public class EnemyShootPosCounter {
    
    // Singleton
    private static EnemyShootPosCounter instance = null;

    protected EnemyShootPosCounter() {
        // Exists only to defeat instantiation.
    }

    public static EnemyShootPosCounter getInstance() {
        if (instance == null) {
            instance = new EnemyShootPosCounter();
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
                Position pos = new Position(i,j);
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
        count.set(count.get(in), count.get(in)+1);
    }
    
    public ArrayList<Integer> getCounter() {
        return count;
    }
}
