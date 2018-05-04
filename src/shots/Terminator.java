package shots;

import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import maps.Map;

public class Terminator {

    private final Map SHOTS;
    private Position lastShot;
    private boolean isHunting;
    private final Target HUNTER;
    private final Hunt SEEKER;
    private final Map HOTSPOTS, OWNHEATMAP;
    private int shotValue;

    public Terminator(int xSize, int ySize, Random rnd) {
        this.OWNHEATMAP = new Map(xSize, ySize);
        this.SHOTS = new Map(xSize, ySize);
        this.HUNTER = new Target(this.SHOTS, rnd);
        this.SEEKER = new Hunt(this.SHOTS, rnd, this.OWNHEATMAP);
        this.HOTSPOTS = new Map(xSize, ySize);
    }

    public void newRound(int round) {
        this.SHOTS.clear();
        this.isHunting = false;
        this.shotValue = (this.SHOTS.getXSize() * this.SHOTS.getYSize());
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        if (this.isHunting) {
            this.lastShot = this.HUNTER.getFireCoordinates(enemyShips);
        } else {
            this.lastShot = this.SEEKER.getFireCoordinates(this.HOTSPOTS, enemyShips);
        }
        this.OWNHEATMAP.add(this.lastShot.x, this.lastShot.y, this.shotValue--);
        return this.lastShot;
    }

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            this.HOTSPOTS.add(this.lastShot.x, this.lastShot.y, 1);
        }
        if (this.isHunting) {
            this.isHunting = this.HUNTER.hitFeedback(hit, enemyShips);
        } else if (hit) {
            this.isHunting = true;
            this.SHOTS.set(this.lastShot.x, this.lastShot.y, 1);
            this.HUNTER.startHunt(this.lastShot, enemyShips);
        } else {
            this.SHOTS.set(this.lastShot.x, this.lastShot.y, -1);
        }
    }
}
