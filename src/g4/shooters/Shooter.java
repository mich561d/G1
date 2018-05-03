package g4.shooters;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import g1.maps.IntMap;

public class Shooter {

    private final IntMap shots;
    private Position lastShot;
    private boolean isHunting;
    private final Hunter hunter;
    private final Seeker seeker;
    private final IntMap hotSpots;
    private final IntMap ownHeatMap;
    private int shotValue;

    public Shooter(int xSize, int ySize, Random rnd) {
        this.ownHeatMap = new IntMap(xSize, ySize);
        this.shots = new IntMap(xSize, ySize);
        this.hunter = new Hunter(this.shots, rnd);
        this.seeker = new Seeker(this.shots, rnd, this.ownHeatMap);
        this.hotSpots = new IntMap(xSize, ySize);
    }

    public void newRound(int round) {
        this.shots.clear();
        this.isHunting = false;
        this.shotValue = (this.shots.getXSize() * this.shots.getYSize());
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        if (this.isHunting) {
            this.lastShot = this.hunter.getFireCoordinates(enemyShips);
        } else {
            this.lastShot = this.seeker.getFireCoordinates(this.hotSpots, enemyShips);
        }
        this.ownHeatMap.add(this.lastShot.x, this.lastShot.y, this.shotValue--);
        return this.lastShot;
    }

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            this.hotSpots.add(this.lastShot.x, this.lastShot.y, 1);
        }
        if (this.isHunting) {
            this.isHunting = this.hunter.hitFeedback(hit, enemyShips);
        } else if (hit) {
            this.isHunting = true;
            this.shots.set(this.lastShot.x, this.lastShot.y, 1);
            this.hunter.startHunt(this.lastShot, enemyShips);
        } else {
            this.shots.set(this.lastShot.x, this.lastShot.y, -1);
        }
    }
}
