package g1;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.Random;

/**
 * @author Michael
 */
public class ShooterAntiFleet implements BattleshipsPlayer {

    private final static Random RANDOM = new Random();
    private final static ShipZoneMap SZM = new ShipZoneMap();
    private final static EnemyShootPosCounter ESPC = new EnemyShootPosCounter();
    private final static HitPosCounter HPC = new HitPosCounter();
    // Board
    private int sizeX, sizeY;
    private Board myBoard;
    // Ships
    private int nextX;
    private int nextY;
    // Shoot
    private int shotX, shotY;

    public ShooterAntiFleet() {
    }

    @Override
    public void placeShips(Fleet fleet, Board board) {
        /*myBoard = board;
        sizeX = board.sizeX();
        sizeY = board.sizeY();

        for (int i = 0; i < fleet.getNumberOfShips(); i++) {
            Ship ship = fleet.getShip(i);
            int shipSize = ship.size();
            switch (shipSize) {
                case 2:
                    placeDestroyer(ship);
                    break;
                case 3:
                    placeCruiser(ship);
                    break;
                case 4:
                    placeBattleship(ship);
                    break;
                case 5:
                    placeCarrier(ship);
                    break;
                default:
                    break;
            }
        }*/
        nextX = 0;
        nextY = 0;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        for(int i = 0; i < fleet.getNumberOfShips(); ++i)
        {
            Ship s = fleet.getShip(i);
            boolean vertical = RANDOM.nextBoolean();
            Position pos;
            if(vertical)
            {
                int x = RANDOM.nextInt(sizeX);
                int y = RANDOM.nextInt(sizeY-(s.size()-1));
                pos = new Position(x, y);
            }
            else
            {
                int x = RANDOM.nextInt(sizeX-(s.size()-1));
                int y = RANDOM.nextInt(sizeY);
                pos = new Position(x, y);
            }
            board.placeShip(pos, s, vertical);
        }
    }

    private void placeDestroyer(Ship ship) {
        boolean validPlacement = false, vertical = true; // Vertical = Lodret
        int x = 0, y = 0;
        Position startPos = new Position(x, y);

        while (!validPlacement) {
            vertical = RANDOM.nextBoolean();
            x = RANDOM.nextInt(myBoard.sizeX());
            y = RANDOM.nextInt(myBoard.sizeY());
            startPos = new Position(x, y);
            Position endPos;

            if (vertical) {
                endPos = new Position(x, y + 1);
            } else {
                endPos = new Position(x + 1, y);
            }
            boolean valid = SZM.destroyerMap(startPos, endPos);
            if (valid) {
                validPlacement = true;
            }
        }
        myBoard.placeShip(startPos, ship, vertical);
    }

    private void placeCruiser(Ship ship) {
        boolean validPlacement = false, vertical = true; // Vertical = Lodret
        int x = 0, y = 0;
        Position startPos = new Position(x, y);

        while (!validPlacement) {
            vertical = RANDOM.nextBoolean();
            x = RANDOM.nextInt(myBoard.sizeX());
            y = RANDOM.nextInt(myBoard.sizeY());
            startPos = new Position(x, y);
            Position midPos;
            Position endPos;

            if (vertical) {
                midPos = new Position(x, y + 1);
                endPos = new Position(x, y + 2);
            } else {
                midPos = new Position(x + 1, y);
                endPos = new Position(x + 2, y);
            }
            boolean valid = SZM.cruiserMap(startPos, midPos, endPos);
            if (valid) {
                validPlacement = true;
            }
        }
        myBoard.placeShip(startPos, ship, vertical);
    }

    private void placeBattleship(Ship ship) {
        boolean validPlacement = false, vertical = true; // Vertical = Lodret
        int x = 0, y = 0;
        Position startPos = new Position(x, y);

        while (!validPlacement) {
            vertical = RANDOM.nextBoolean();
            x = RANDOM.nextInt(myBoard.sizeX());
            y = RANDOM.nextInt(myBoard.sizeY());
            startPos = new Position(x, y);
            Position startMidPos;
            Position endMidPos;
            Position endPos;

            if (vertical) {
                startMidPos = new Position(x, y + 1);
                endMidPos = new Position(x, y + 2);
                endPos = new Position(x, y + 3);
            } else {
                startMidPos = new Position(x + 3, y);
                endMidPos = new Position(x + 3, y);
                endPos = new Position(x + 3, y);
            }
            boolean valid = SZM.battleshipMap(startPos, startMidPos, endMidPos, endPos);
            if (valid) {
                validPlacement = true;
            }
        }
        myBoard.placeShip(startPos, ship, vertical);
    }

    private void placeCarrier(Ship ship) {
        boolean validPlacement = false, vertical = true; // Vertical = Lodret
        int x = 0, y = 0;
        Position startPos = new Position(x, y);

        while (!validPlacement) {
            vertical = RANDOM.nextBoolean();
            x = RANDOM.nextInt(myBoard.sizeX());
            y = RANDOM.nextInt(myBoard.sizeY());
            startPos = new Position(x, y);
            Position startMidPos;
            Position midPos;
            Position endMidPos;
            Position endPos;

            if (vertical) {
                startMidPos = new Position(x, y + 1);
                midPos = new Position(x, y + 2);
                endMidPos = new Position(x, y + 3);
                endPos = new Position(x, y + 4);
            } else {
                startMidPos = new Position(x + 1, y);
                midPos = new Position(x + 2, y);
                endMidPos = new Position(x + 3, y);
                endPos = new Position(x + 4, y);
            }
            boolean valid = SZM.carrierMap(startPos, startMidPos, midPos, endMidPos, endPos);
            if (valid) {
                validPlacement = true;
            }
        }
        myBoard.placeShip(startPos, ship, vertical);
    }

    @Override // Our code!
    public void incoming(Position pos) {
        ESPC.countPosition(pos);
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot = new Position(shotX, shotY);
        ++shotX;
        if (shotX >= sizeX) {
            shotX = 0;
            ++shotY;
            if (shotY >= sizeY) {
                shotY = 0;
            }
        }
        return shot;
    }

    @Override // Half our code!
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            HPC.countPosition(new Position(shotX, shotY));
        }
        //Do nothing
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {

    }

    @Override
    public void startRound(int round) {

    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }
}
