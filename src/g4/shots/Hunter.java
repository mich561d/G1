package g4.shots;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import g1.map.IntMap;

public class Hunter {

    private final Random rnd;
    private final IntMap shots;
    private final IntMap shipDist;
    private final List<Position> hits;
    private Position lastShot;
    private int numHits;
    private int startFleetSum;

    public Hunter(IntMap shots, Random rnd) {
        this.rnd = rnd;
        this.shots = shots;
        this.shipDist = new IntMap(shots.getXSize(), shots.getYSize());
        this.hits = new ArrayList();
    }

    public void startHunt(Position pos, Fleet fleet) {
        this.numHits = 1;
        this.startFleetSum = fleetSum(fleet);
        this.hits.clear();
        this.hits.add(pos);
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        generateShipDistribution(enemyShips);
        List<Position> bestCoordinates = this.shipDist.getHighest();
        this.lastShot = ((Position) bestCoordinates.get(this.rnd.nextInt(bestCoordinates.size())));
        return this.lastShot;
    }

    public boolean hitFeedback(boolean hit, Fleet enemyShips) {
        this.shots.set(this.lastShot.x, this.lastShot.y, hit ? 1 : -1);
        if (hit) {
            this.numHits += 1;
            this.hits.add(this.lastShot);
            int curFleetSum = fleetSum(enemyShips);
            if (curFleetSum < this.startFleetSum) {
                this.numHits -= this.startFleetSum - curFleetSum;
                if (this.numHits == 0) {
                    for (Position p : this.hits) {
                        this.shots.set(p.x, p.y, -2);
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
        this.shipDist.clear();
        for (Ship s : enemyShips) {
            int maxX = this.shipDist.getXSize() - s.size();
            int maxY = this.shipDist.getYSize() - 1;
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    boolean canPlace = true;
                    int hitCount = 0;
                    for (int i = 0; i < s.size(); i++) {
                        int val = this.shots.get(x + i, y);
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
                            int val = this.shots.get(x + i, y);
                            if (val == 0) {
                                this.shipDist.add(x + i, y, hitCount);
                            }
                        }
                    }
                }
            }
            maxX = this.shipDist.getXSize() - 1;
            maxY = this.shipDist.getYSize() - s.size();
            for (int y = 0; y <= maxY; y++) {
                for (int x = 0; x <= maxX; x++) {
                    boolean canPlace = true;
                    int hitCount = 0;
                    for (int i = 0; i < s.size(); i++) {
                        int val = this.shots.get(x, y + i);
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
                            int val = this.shots.get(x, y + i);
                            if (val == 0) {
                                this.shipDist.add(x, y + i, hitCount);
                            }
                        }
                    }
                }
            }
        }
    }
}
