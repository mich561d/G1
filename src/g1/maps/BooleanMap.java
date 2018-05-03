package g1.maps;

public class BooleanMap {

    private final boolean[][] map;
    private final int xSize;
    private final int ySize;

    public BooleanMap(int xSize, int ySize) {
        this.map = new boolean[xSize][ySize];
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
                this.map[x][y] = false;
            }
        }
    }

    public void mark(int x, int y) {
        this.map[x][y] = true;
    }

    public void unMark(int x, int y) {
        this.map[x][y] = false;
    }

    public boolean getPos(int x, int y) {
        return this.map[x][y];
    }
}
