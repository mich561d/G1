package g1.maps;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

public class IntMap {

    private final int[][] map;
    private final int xSize;
    private final int ySize;

    public IntMap(int xSize, int ySize) {
        this.map = new int[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int getXSize() {
        return this.xSize;
    }

    public int getYSize() {
        return this.ySize;
    }

    public void clear() {
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                this.map[x][y] = 0;
            }
        }
    }

    public void set(int x, int y, int value) {
        this.map[x][y] = value;
    }

    public void add(int x, int y, int i) {
        this.map[x][y] += i;
    }

    public int get(int x, int y) {
        return this.map[x][y];
    }

    public List<Position> getHighest() {
        int max = Integer.MIN_VALUE;
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                if (this.map[x][y] > max) {
                    max = this.map[x][y];
                }
            }
        }
        List<Position> res = new ArrayList();
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                if (this.map[x][y] == max) {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }

    public List<Position> getNonZero() {
        List<Position> res = new ArrayList();
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                if (this.map[x][y] > 0) {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int y = 0; y < this.ySize; y++) {
            for (int x = 0; x < this.xSize; x++) {
                res.append(this.map[x][y]);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }
}
