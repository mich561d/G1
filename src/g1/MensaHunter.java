package g1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import g1.maps.FloatMap;
import g1.ships.HotspotBoard;
import g1.ships.ShipPlacer;
import g4.shots.Shooter;

public class MensaHunter implements BattleshipsPlayer {

    private final Random rnd;
    private FloatMap ownHotspots;
    private Shooter shooter;
    private ShipPlacer placer;
    private int round;

    public MensaHunter() {
        this.rnd = new Random();
        this.shooter = null;
        this.placer = null;
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.shooter = new Shooter(sizeX, sizeY, this.rnd);
        this.placer = new ShipPlacer(sizeX, sizeY, this.rnd);
        this.ownHotspots = new FloatMap(sizeX, sizeY);
    }

    @Override
    public void startRound(int round) {
        this.round = round;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        this.shooter.newRound(this.round);
        this.placer.placeShips(fleet, new HotspotBoard(board, this.ownHotspots));
    }

    @Override
    public void incoming(Position pos) {
        this.placer.incoming(pos);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = this.shooter.getFireCoordinates(enemyShips);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        this.shooter.hitFeedBack(hit, enemyShips);
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
    }
}
