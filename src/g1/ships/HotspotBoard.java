package g1.ships;

import battleship.interfaces.Board;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import g1.maps.FloatMap;

public class HotspotBoard implements Board {

    private final Board original;
    private final FloatMap hotspots;

    public HotspotBoard(Board original, FloatMap hotspots) {
        this.original = original;
        this.hotspots = hotspots;
    }

    public int sizeX() {
        return this.original.sizeX();
    }

    public int sizeY() {
        return this.original.sizeY();
    }

    public void placeShip(Position pos, Ship ship, boolean vertical) {
        int size = ship.size();
        if (vertical) {
            for (int y = pos.y; y < pos.y + size; y++) {
                this.hotspots.add(pos.x, y, 1.0F);
            }
        } else {
            for (int x = pos.x; x < pos.x + size; x++) {
                this.hotspots.add(x, pos.y, 1.0F);
            }
        }
        this.original.placeShip(pos, ship, vertical);
    }
}
