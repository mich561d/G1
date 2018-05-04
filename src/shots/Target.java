package shots;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import maps.Map;

public class Target {

    private final Random RANDOM;
    private final Map SHOTS, SHIPDIST;
    private final List<Position> HITS;
    private Position lastShot;
    private int numHits, startFleetSum;

    public Target(Map shots, Random rnd) {
        this.RANDOM = rnd;
        this.SHOTS = shots;
        this.SHIPDIST = new Map(shots.getXSize(), shots.getYSize());
        this.HITS = new ArrayList();
    }

    public void startHunt(Position pos, Fleet fleet) {
        this.numHits = 1;
        this.startFleetSum = fleetSum(fleet);
        this.HITS.clear();
        this.HITS.add(pos);
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        generateShipDistribution(enemyShips);
        List<Position> bestCoordinates = this.SHIPDIST.getHighest();
        this.lastShot = ((Position) bestCoordinates.get(this.RANDOM.nextInt(bestCoordinates.size())));
        return this.lastShot;
    }

    public boolean hitFeedback(boolean hit, Fleet enemyShips) {
        this.SHOTS.set(this.lastShot.x, this.lastShot.y, hit ? 1 : -1);
        if (hit) {
            this.numHits += 1;
            this.HITS.add(this.lastShot);
            int curFleetSum = fleetSum(enemyShips);
            if (curFleetSum < this.startFleetSum) {
                this.numHits -= this.startFleetSum - curFleetSum;
                if (this.numHits == 0) {
                    for (Position p : this.HITS) {
                        this.SHOTS.set(p.x, p.y, -2);
                    }
                    return false;
                }
                this.startFleetSum = curFleetSum;
            }
        }
        return true;
    }

    private int fleetSum(Fleet fleet) {
        int res = 0;
        for (Ship s : fleet) {
            res += s.size();
        }
        return res;
    }

    private void generateShipDistribution(Fleet enemyShips) {
        this.SHIPDIST.clear();
        for (Ship s : enemyShips) {
            int maxX = this.SHIPDIST.getXSize() - s.size();
            int maxY = this.SHIPDIST.getYSize() - 1;
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    boolean canPlace = true;
                    int hitCount = 0;
                    for (int i = 0; i < s.size(); i++) {
                        int val = this.SHOTS.get(x + i, y);
                        if (val < 0) {
                            canPlace = false;
                            break;
                        }
                        if (val > 0) {
                            hitCount++;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); i++) {
                            int val = this.SHOTS.get(x + i, y);
                            if (val == 0) {
                                this.SHIPDIST.add(x + i, y, hitCount);
                            }
                        }
                    }
                }
            }
            maxX = this.SHIPDIST.getXSize() - 1;
            maxY = this.SHIPDIST.getYSize() - s.size();
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    boolean canPlace = true;
                    int hitCount = 0;
                    for (int i = 0; i < s.size(); i++) {
                        int val = this.SHOTS.get(x, y + i);
                        if (val < 0) {
                            canPlace = false;
                            break;
                        }
                        if (val > 0) {
                            hitCount++;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < s.size(); i++) {
                            int val = this.SHOTS.get(x, y + i);
                            if (val == 0) {
                                this.SHIPDIST.add(x, y + i, hitCount);
                            }
                        }
                    }
                }
            }
        }
    }
}
