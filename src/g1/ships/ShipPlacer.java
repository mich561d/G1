package g1.ships;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import g1.map.BooleanMap;
import g1.map.IntMap;

public class ShipPlacer {

    private boolean adjacentShips = true;
    private boolean useHeatMap = true;
    private final Random rnd;
    private int shotValue;
    private final int xSize;
    private final int ySize;
    private final IntMap heatMap;
    private final BooleanMap shipMap;
    private final Position[][] positions;

    public ShipPlacer(int xSize, int ySize, Random rnd) {
        this.rnd = rnd;
        this.xSize = xSize;
        this.ySize = ySize;
        this.heatMap = new IntMap(xSize, ySize);
        this.shipMap = new BooleanMap(xSize, ySize);
        this.positions = new Position[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                this.positions[x][y] = new Position(x, y);
            }
        }
    }

    public void placeShips(Fleet fleet, Board board) {
        this.shotValue = (this.xSize * this.ySize);
        this.shipMap.clear();

        List<Ship> ships = new ArrayList(fleet.getNumberOfShips());
        for (Ship s : fleet) {
            ships.add(s);
        }
        Collections.shuffle(ships);
        for (Ship s : ships) {
            List<ShipConf> confs = getConfigurations(s);
            ShipConf conf = selectConf(confs);
            if (conf != null) {
                placeShip(conf, board);
            }
        }
    }

    public void incoming(Position pos) {
        this.heatMap.add(pos.x, pos.y, this.shotValue--);
    }

    private ShipConf selectConf(List<ShipConf> confs) {
        int count = confs.size();
        if (confs.isEmpty()) {
            return null;
        }
        if (this.useHeatMap) {
            Collections.sort(confs);
            int bestValue = ((ShipConf) confs.get(0)).getValue();
            count = 1;
            for (int i = 1; i < confs.size(); i++) {
                ShipConf c = (ShipConf) confs.get(i);
                if (c.getValue() > bestValue) {
                    break;
                }
                count++;
            }
        }
        return (ShipConf) confs.get(this.rnd.nextInt(count));
    }

    private void placeShip(ShipConf conf, Board board) {
        int size = conf.getShip().size();
        board.placeShip(conf.getPosition(), conf.getShip(), conf.getVertical());
        if (conf.getVertical()) {
            int x = conf.getPosition().x;
            if (!this.adjacentShips) {
                int y = conf.getPosition().y - 1;
                markShipPoint(x, y);

                y = conf.getPosition().y + size;
                markShipPoint(x, y);
            }
            int y = conf.getPosition().y;
            for (int i = 0; i < size; i++) {
                if (!this.adjacentShips) {
                    markShipPoint(x - 1, y + i);
                }
                markShipPoint(x, y + i);
                if (!this.adjacentShips) {
                    markShipPoint(x + 1, y + i);
                }
            }
        } else {
            int y = conf.getPosition().y;
            if (!this.adjacentShips) {
                int x = conf.getPosition().x - 1;
                markShipPoint(x, y);

                x = conf.getPosition().x + size;
                markShipPoint(x, y);
            }
            int x = conf.getPosition().x;
            for (int i = 0; i < size; i++) {
                if (!this.adjacentShips) {
                    markShipPoint(x + i, y - 1);
                }
                markShipPoint(x + i, y);
                if (!this.adjacentShips) {
                    markShipPoint(x + i, y + 1);
                }
            }
        }
    }

    private void markShipPoint(int x, int y) {
        if ((x >= 0) && (x < this.xSize) && (y >= 0) && (y < this.ySize)) {
            this.shipMap.mark(x, y);
        }
    }

    private List<ShipConf> getConfigurations(Ship ship) {
        int size = ship.size();
        List<ShipConf> res = new ArrayList();
        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x <= this.xSize - size; x++) {
                boolean validPos = true;
                int value = 0;
                for (int i = 0; i < size; i++) {
                    if (this.shipMap.getPos(x + i, y)) {
                        validPos = false;
                        break;
                    }
                    value += this.heatMap.get(x + i, y);
                }
                if (validPos) {
                    res.add(new ShipConf(ship, this.positions[x][y], false, value));
                }
            }
        }
        for (int y = 0; y <= this.ySize - size; y++) {
            for (int x = 0; x < this.xSize; x++) {
                boolean validPos = true;
                int value = 0;
                for (int i = 0; i < size; i++) {
                    if (this.shipMap.getPos(x, y + i)) {
                        validPos = false;
                        break;
                    }
                    value += this.heatMap.get(x, y + i);
                }
                if (validPos) {
                    res.add(new ShipConf(ship, this.positions[x][y], true, value));
                }
            }
        }
        return res;
    }
}
