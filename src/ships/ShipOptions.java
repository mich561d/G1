package ships;

import battleship.interfaces.Position;
import battleship.interfaces.Ship;

public class ShipOptions implements Comparable<ShipOptions> {

    private final Ship SHIP;
    private final Position POS;
    private final boolean VERTICAL;
    private final int VALUE;

    public ShipOptions(Ship ship, Position pos, boolean vertical, int value) {
        this.SHIP = ship;
        this.POS = pos;
        this.VERTICAL = vertical;
        this.VALUE = value;
    }

    public Ship getShip() {
        return this.SHIP;
    }

    public Position getPosition() {
        return this.POS;
    }

    public boolean getVertical() {
        return this.VERTICAL;
    }

    public int getValue() {
        return this.VALUE;
    }

    @Override
    public int compareTo(ShipOptions object) {
        return this.VALUE - object.VALUE;
    }
}
