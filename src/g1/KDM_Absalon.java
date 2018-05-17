package g1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import java.util.Random;
import maps.HeatMap;
import ships.Stats;
import ships.ShipPlacer;
import shots.Terminator;

public class KDM_Absalon implements BattleshipsPlayer {

    private final Random RANDOM;
    private StatsMaps stats;
    private HeatMap hotspots;
    private Terminator shooter;
    private ShipPlacer placer;
    private int round;

    public KDM_Absalon() {
        this.RANDOM = new Random();
        this.shooter = null;
        this.placer = null;
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.stats = new StatsMaps(sizeX, sizeY);
        this.shooter = new Terminator(sizeX, sizeY, this.RANDOM);
        this.placer = new ShipPlacer(sizeX, sizeY, this.RANDOM);
        this.hotspots = new HeatMap(sizeX, sizeY);
    }

    @Override
    public void startRound(int round) {
        this.round = round;
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        this.shooter.newRound(this.round);
        this.placer.placeShips(fleet, new Stats(board, this.hotspots), stats);
    }

    @Override
    public void incoming(Position pos) {
        this.placer.incoming(pos);
        stats.PickMap("INCOMINGMAP", pos);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = this.shooter.getFireCoordinates(enemyShips);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        this.shooter.hitFeedBack(hit, enemyShips, stats);
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        System.out.println("HITMAP:");
        System.out.println(stats.GetMap("HITMAP"));
        System.out.println("INCOMINGMAP:");
        System.out.println(stats.GetMap("INCOMINGMAP"));
        System.out.println("SHIPMAP:");
        System.out.println(stats.GetMap("SHIPMAP"));
        System.out.println("ESHIPMAP:");
        System.out.println(stats.GetMap("ESHIPMAP"));
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        System.out.println("HITMAP:");
        System.out.println(stats.GetMap("HITMAP"));
        System.out.println("INCOMINGMAP:");
        System.out.println(stats.GetMap("INCOMINGMAP"));
        System.out.println("SHIPMAP:");
        System.out.println(stats.GetMap("SHIPMAP"));
        System.out.println("ESHIPMAP:");
        System.out.println(stats.GetMap("ESHIPMAP"));
    }
}
