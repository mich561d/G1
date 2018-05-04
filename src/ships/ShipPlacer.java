package ships;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import maps.MapFrame;
import maps.Map;

public class ShipPlacer {

    private final boolean ADJECENTSHIPS = true, USEHEATMAP = true;
    private final Random RANDOM;
    private int shotValue;
    private final int XSIZE, YSIZE;
    private final Map HEATMAP;
    private final MapFrame SHIPMAP;
    private final Position[][] POSITIONS;

    public ShipPlacer(int xSize, int ySize, Random rnd) {
        this.RANDOM = rnd;
        this.XSIZE = xSize;
        this.YSIZE = ySize;
        this.HEATMAP = new Map(xSize, ySize);
        this.SHIPMAP = new MapFrame(xSize, ySize);
        this.POSITIONS = new Position[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                this.POSITIONS[x][y] = new Position(x, y);
            }
        }
    }

    public void placeShips(Fleet fleet, Board board) {
        this.shotValue = (this.XSIZE * this.YSIZE);
        this.SHIPMAP.clear();

        List<Ship> ships = new ArrayList(fleet.getNumberOfShips());
        for (Ship s : fleet) {
            ships.add(s);
        }
        Collections.shuffle(ships);
        for (Ship s : ships) {
            List<ShipOptions> confs = getConfigurations(s);
            ShipOptions conf = selectConf(confs);
            if (conf != null) {
                placeShip(conf, board);
            }
        }
    }

    public void incoming(Position pos) {
        this.HEATMAP.add(pos.x, pos.y, this.shotValue--);
    }

    private ShipOptions selectConf(List<ShipOptions> confs) {
        int count = confs.size();
        if (confs.isEmpty()) {
            return null;
        }
        if (this.USEHEATMAP) {
            Collections.sort(confs);
            int bestValue = ((ShipOptions) confs.get(0)).getValue();
            count = 1;
            for (int i = 1; i < confs.size(); i++) {
                ShipOptions c = (ShipOptions) confs.get(i);
                if (c.getValue() > bestValue) {
                    break;
                }
                count++;
            }
        }
        return (ShipOptions) confs.get(this.RANDOM.nextInt(count));
    }

    private void placeShip(ShipOptions conf, Board board) {
        int size = conf.getShip().size();
        board.placeShip(conf.getPosition(), conf.getShip(), conf.getVertical());
        if (conf.getVertical()) {
            int x = conf.getPosition().x;
            if (!this.ADJECENTSHIPS) {
                int y = conf.getPosition().y - 1;
                markShipPoint(x, y);

                y = conf.getPosition().y + size;
                markShipPoint(x, y);
            }
            int y = conf.getPosition().y;
            for (int i = 0; i < size; i++) {
                if (!this.ADJECENTSHIPS) {
                    markShipPoint(x - 1, y + i);
                }
                markShipPoint(x, y + i);
                if (!this.ADJECENTSHIPS) {
                    markShipPoint(x + 1, y + i);
                }
            }
        } else {
            int y = conf.getPosition().y;
            if (!this.ADJECENTSHIPS) {
                int x = conf.getPosition().x - 1;
                markShipPoint(x, y);

                x = conf.getPosition().x + size;
                markShipPoint(x, y);
            }
            int x = conf.getPosition().x;
            for (int i = 0; i < size; i++) {
                if (!this.ADJECENTSHIPS) {
                    markShipPoint(x + i, y - 1);
                }
                markShipPoint(x + i, y);
                if (!this.ADJECENTSHIPS) {
                    markShipPoint(x + i, y + 1);
                }
            }
        }
    }

    private void markShipPoint(int x, int y) {
        if ((x >= 0) && (x < this.XSIZE) && (y >= 0) && (y < this.YSIZE)) {
            this.SHIPMAP.mark(x, y);
        }
    }

    private List<ShipOptions> getConfigurations(Ship ship) {
        int size = ship.size();
        List<ShipOptions> res = new ArrayList();
        for (int y = 0; y < this.YSIZE; y++) {
            for (int x = 0; x <= this.XSIZE - size; x++) {
                boolean validPos = true;
                int value = 0;
                for (int i = 0; i < size; i++) {
                    if (this.SHIPMAP.getPos(x + i, y)) {
                        validPos = false;
                        break;
                    }
                    value += this.HEATMAP.get(x + i, y);
                }
                if (validPos) {
                    res.add(new ShipOptions(ship, this.POSITIONS[x][y], false, value));
                }
            }
        }
        for (int y = 0; y <= this.YSIZE - size; y++) {
            for (int x = 0; x < this.XSIZE; x++) {
                boolean validPos = true;
                int value = 0;
                for (int i = 0; i < size; i++) {
                    if (this.SHIPMAP.getPos(x, y + i)) {
                        validPos = false;
                        break;
                    }
                    value += this.HEATMAP.get(x, y + i);
                }
                if (validPos) {
                    res.add(new ShipOptions(ship, this.POSITIONS[x][y], true, value));
                }
            }
        }
        return res;
    }
}
