package g4.shots;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import g1.maps.FloatMap;
import g1.maps.IntMap;

public class Seeker {

    private final boolean useOwnHeat = true;
    private final Random rnd;
    private final IntMap shots;
    private final FloatMap shipDist;
    private final IntMap ownHeat;
    private Position lastShot;

    public Seeker(IntMap shots, Random rnd, IntMap ownHeat) {
        this.rnd = rnd;
        this.shots = shots;
        this.shipDist = new FloatMap(shots.getXSize(), shots.getYSize());
        this.ownHeat = ownHeat;
    }

    public Position getFireCoordinates(IntMap hotSpots, Fleet enemyShips) {
        generateShipDistribution(enemyShips);
        List<Position> bestCoordinates = this.shipDist.getHighest();

        bestCoordinates = avoidHeat(bestCoordinates, this.ownHeat);

        this.lastShot = ((Position) bestCoordinates.get(this.rnd.nextInt(bestCoordinates.size())));
        return this.lastShot;
    }

    private List<Position> avoidHeat(List<Position> positions, IntMap ownHeat) {
        int min = Integer.MAX_VALUE;
        for (Iterator localIterator = positions.iterator(); localIterator.hasNext();) {
            Position p = (Position) localIterator.next();

            int val = ownHeat.get(p.x, p.y);
            if (val < min) {
                min = val;
            }
        }
        Position p;
        Object res = new ArrayList();
        for (Position pos : positions) {
            int val = ownHeat.get(pos.x, pos.y);
            if (val == min) {
                ((List) res).add(pos);
            }
        }
        return (List<Position>) res;
    }

    private void generateShipDistribution(Fleet enemyShips) {
        this.shipDist.clear();
        for (Ship s : enemyShips) {
            int maxX = this.shipDist.getXSize() - s.size();
            int maxY = this.shipDist.getYSize() - 1;
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    boolean canPlace = true;
                    for (int i = 0; i < s.size(); i++) {
                        int val = this.shots.get(x + i, y);
                        if (val != 0) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); i++) {
                            this.shipDist.add(x + i, y, 1.0F);
                        }
                    }
                }
            }
            maxX = this.shipDist.getXSize() - 1;
            maxY = this.shipDist.getYSize() - s.size();
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    boolean canPlace = true;
                    for (int i = 0; i < s.size(); i++) {
                        int val = this.shots.get(x, y + i);
                        if (val != 0) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); i++) {
                            this.shipDist.add(x, y + i, 1.0F);
                        }
                    }
                }
            }
        }
    }
}
