package g1.ships;

import battleship.interfaces.Board;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import g1.maps.FloatMap;

public class HotspotBoard implements Board {

    private final Board ORIGINAL;
    private final FloatMap HOTSPOTS;

    public HotspotBoard(Board original, FloatMap hotspots) {
        this.ORIGINAL = original;
        this.HOTSPOTS = hotspots;
    }

    @Override
    public int sizeX() {
        return this.ORIGINAL.sizeX();
    }

    @Override
    public int sizeY() {
        return this.ORIGINAL.sizeY();
    }

    @Override
    public void placeShip(Position pos, Ship ship, boolean vertical) {
        int size = ship.size();
        if (vertical) {
            for (int y = pos.y; y < pos.y + size; y++) {
                this.HOTSPOTS.add(pos.x, y, 1.0F);
            }
        } else {
            for (int x = pos.x; x < pos.x + size; x++) {
                this.HOTSPOTS.add(x, pos.y, 1.0F);
            }
        }
        this.ORIGINAL.placeShip(pos, ship, vertical);
    }
}
