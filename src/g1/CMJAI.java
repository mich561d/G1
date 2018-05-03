package g1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import g1.maps.FloatMap;
import g1.placers.HotspotBoard;
import g1.placers.ShipPlacer;
import g4.shooters.Shooter;

public class CMJAI
        implements BattleshipsPlayer {

    private final Random rnd;
    private FloatMap ownHotspots;
    private Shooter shooter;
    private ShipPlacer placer;
    private int round;

    public CMJAI() {
        this.rnd = new Random();
        this.shooter = null;
        this.placer = null;
    }

    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.shooter = new Shooter(sizeX, sizeY, this.rnd);
        this.placer = new ShipPlacer(sizeX, sizeY, this.rnd);
        this.ownHotspots = new FloatMap(sizeX, sizeY);
    }

    public void startRound(int round) {
        this.round = round;
    }

    public void placeShips(Fleet fleet, Board board) {
        this.shooter.newRound(this.round);
        this.placer.placeShips(fleet, new HotspotBoard(board, this.ownHotspots));
    }

    public void incoming(Position pos) {
        this.placer.incoming(pos);
    }

    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = this.shooter.getFireCoordinates(enemyShips);
        return shot;
    }

    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        this.shooter.hitFeedBack(hit, enemyShips);
    }

    public void endRound(int round, int points, int enemyPoints) {
    }

    public void endMatch(int won, int lost, int draw) {
    }
}
