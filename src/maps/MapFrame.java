package maps;

public class MapFrame {

    private final boolean[][] MAP;
    private final int XSIZE, YSIZE;

    public MapFrame(int xSize, int ySize) {
        this.MAP = new boolean[xSize][ySize];
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
                this.MAP[x][y] = false;
            }
        }
    }

    public void mark(int x, int y) {
        this.MAP[x][y] = true;
    }

    public void unMark(int x, int y) {
        this.MAP[x][y] = false;
    }

    public boolean getPos(int x, int y) {
        return this.MAP[x][y];
    }
}
