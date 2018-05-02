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
    // Shoot
    private int shotX, shotY;

    public ShooterAntiFleet() {
    }

    /**
     * The method called when its time for the AI to place ships on the board
     * (at the beginning of each round).
     *
     * The Ship object to be placed MUST be taken from the Fleet given (do not
     * create your own Ship objects!).
     *
     * A ship is placed by calling the board.placeShip(..., Ship ship, ...) for
     * each ship in the fleet (see board interface for details on placeShip()).
     *
     * A player is not required to place all the ships. Ships placed outside the
     * board or on top of each other are wrecked.
     *
     * @param fleet Fleet all the ships that a player should place.
     * @param board Board the board were the ships must be placed.
     */
    @Override
    public void placeShips(Fleet fleet, Board board) {
        myBoard = board;
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
        }

        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);
            boolean vertical = RANDOM.nextBoolean();
            Position pos;
            if (vertical) {
                int x = RANDOM.nextInt(sizeX);
                int y = RANDOM.nextInt(sizeY - (s.size() - 1));
                pos = new Position(x, y);
            } else {
                int x = RANDOM.nextInt(sizeX - (s.size() - 1));
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
                endPos = new Position(x, y - 1);
            } else {
                endPos = new Position(x - 1, y);
            }

            boolean valid = SZM.destroyerMap(startPos, endPos);
            if (valid) {
                validPlacement = true;
            } else {
                if (vertical) {
                    endPos = new Position(x, y + 1);
                } else {
                    endPos = new Position(x + 1, y);
                }
                valid = SZM.destroyerMap(startPos, endPos);
                if (valid) {
                    validPlacement = true;
                }
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
                midPos = new Position(x, y - 1);
                endPos = new Position(x, y - 2);
            } else {
                midPos = new Position(x - 1, y);
                endPos = new Position(x - 2, y);
            }

            boolean valid = SZM.cruiserMap(startPos, midPos, endPos);
            if (valid) {
                validPlacement = true;
            } else {
                if (vertical) {
                    midPos = new Position(x, y + 1);
                    endPos = new Position(x, y + 2);
                } else {
                    midPos = new Position(x + 1, y);
                    endPos = new Position(x + 2, y);
                }
                valid = SZM.cruiserMap(startPos, midPos, endPos);
                if (valid) {
                    validPlacement = true;
                }
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
                startMidPos = new Position(x, y - 1);
                endMidPos = new Position(x, y - 2);
                endPos = new Position(x, y - 3);
            } else {
                startMidPos = new Position(x - 3, y);
                endMidPos = new Position(x - 3, y);
                endPos = new Position(x - 3, y);
            }

            boolean valid = SZM.battleshipMap(startPos, startMidPos, endMidPos, endPos);
            if (valid) {
                validPlacement = true;
            } else {
                if (vertical) {
                    startMidPos = new Position(x, y + 1);
                    endMidPos = new Position(x, y + 2);
                    endPos = new Position(x, y + 3);
                } else {
                    startMidPos = new Position(x + 3, y);
                    endMidPos = new Position(x + 3, y);
                    endPos = new Position(x + 3, y);
                }
                valid = SZM.battleshipMap(startPos, startMidPos, endMidPos, endPos);
                if (valid) {
                    validPlacement = true;
                }
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
                startMidPos = new Position(x, y - 1);
                midPos = new Position(x, y - 2);
                endMidPos = new Position(x, y - 3);
                endPos = new Position(x, y - 4);
            } else {
                startMidPos = new Position(x - 1, y);
                midPos = new Position(x - 2, y);
                endMidPos = new Position(x - 3, y);
                endPos = new Position(x - 4, y);
            }

            boolean valid = SZM.carrierMap(startPos, startMidPos, midPos, endMidPos, endPos);
            if (valid) {
                validPlacement = true;
            } else {
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
                valid = SZM.carrierMap(startPos, startMidPos, midPos, endMidPos, endPos);
                if (valid) {
                    validPlacement = true;
                }
            }
        }
        myBoard.placeShip(startPos, ship, vertical);
    }

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    @Override // Our code!
    public void incoming(Position pos) {
        ESPC.countPosition(pos);
    }

    /**
     * Called by the Game application to get the Position of your shot.
     * hitFeedBack(...) is called right after this method.
     *
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet
     * supplied in the hitFeedBack(...) method to see if you have sunk any
     * ships.
     *
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        int x = RANDOM.nextInt(sizeX);
        int y = RANDOM.nextInt(sizeY);
        return new Position(x, y);
    }

    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not.
     *
     * Compare the number of ships in the enemyShips with that given in
     * getFireCoordinates in order to see if you sunk a ship.
     *
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    @Override // Half our code!
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            HPC.countPosition(new Position(shotX, shotY));
        }
        //Do nothing
    }

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {

    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round) {

    }

    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     *
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink
     * all of the enemy's ships.
     *
     * @param enemyPoints int enemy's points this round.
     */
    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you
     * know how many losses, victories and draws you scored.
     *
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }
}
