package maps;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

public class Map {

    private final int[][] MAP;
    private final int XSIZE, YSIZE;

    public Map(int xSize, int ySize) {
        this.MAP = new int[xSize][ySize];
        this.XSIZE = xSize;
        this.YSIZE = ySize;
    }

    public int getXSize() {
        return this.XSIZE;
    }

    public int getYSize() {
        return this.YSIZE;
    }

    public void clear() {
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                this.MAP[x][y] = 0;
            }
        }
    }

    public void set(int x, int y, int value) {
        this.MAP[x][y] = value;
    }

    public void add(int x, int y, int i) {
        this.MAP[x][y] += i;
    }

    public int get(int x, int y) {
        return this.MAP[x][y];
    }

    public List<Position> getHighest() {
        int max = Integer.MIN_VALUE;
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                if (this.MAP[x][y] > max) {
                    max = this.MAP[x][y];
                }
            }
        }
        List<Position> res = new ArrayList();
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                if (this.MAP[x][y] == max) {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }

    public List<Position> getNonZero() {
        List<Position> res = new ArrayList();
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                if (this.MAP[x][y] > 0) {
                    res.add(new Position(x, y));
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int y = 0; y < this.YSIZE; y++) {
            for (int x = 0; x < this.XSIZE; x++) {
                res.append(this.MAP[x][y]);
                res.append(' ');
            }
            res.append('\n');
        }
        return res.toString();
    }
}
