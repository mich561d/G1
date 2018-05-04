package maps;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

public class HeatMap {

    private final float[][] MAP;
    private final int XSIZE, YSIZE;

    public HeatMap(int xSize, int ySize) {
        this.MAP = new float[xSize][ySize];
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
                this.MAP[x][y] = 0.0F;
            }
        }
    }

    public void setAll(float f) {
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                this.MAP[x][y] = f;
            }
        }
    }

    public void set(int x, int y, float f) {
        this.MAP[x][y] = f;
    }

    public void add(int x, int y, float f) {
        this.MAP[x][y] += f;
    }

    public float get(int x, int y) {
        return this.MAP[x][y];
    }

    public HeatMap getNormalized() {
        float max = 0.0F;
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                if (this.MAP[x][y] > max) {
                    max = this.MAP[x][y];
                }
            }
        }
        HeatMap res = new HeatMap(this.XSIZE, this.YSIZE);
        if (max > 0.0F) {
            float invMax = 1.0F / max;
            for (int x = 0; x < this.XSIZE; x++) {
                for (int y = 0; y < this.YSIZE; y++) {
                    res.MAP[x][y] = (invMax * this.MAP[x][y]);
                }
            }
        }
        return res;
    }

    public void normalize() {
        float max = 0.0F;
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                if (this.MAP[x][y] > max) {
                    max = this.MAP[x][y];
                }
            }
        }
        if (max > 0.0F) {
            float invMax = 1.0F / max;
            for (int x = 0; x < this.XSIZE; x++) {
                for (int y = 0; y < this.YSIZE; y++) {
                    this.MAP[x][y] *= invMax;
                }
            }
        }
    }

    public List<Position> getHighest() {
        float max = 0.0F;
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

    public HeatMap reverse() {
        HeatMap res = new HeatMap(this.XSIZE, this.YSIZE);
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                res.MAP[x][y] = (1.0F - this.MAP[x][y]);
            }
        }
        return res;
    }

    public HeatMap add(HeatMap fMap) {
        if ((this.XSIZE != fMap.XSIZE) || (this.YSIZE != fMap.YSIZE)) {
            throw new RuntimeException("FloatMap.add -> Maps are not the same size...");
        }
        HeatMap res = new HeatMap(this.XSIZE, this.YSIZE);
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                this.MAP[x][y] += fMap.MAP[x][y];
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

    public Position getWeightedRandom(float random) {
        float sum = 0.0F;
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                sum += this.MAP[x][y];
            }
        }
        float r = sum * random;
        sum = 0.0F;
        for (int x = 0; x < this.XSIZE; x++) {
            for (int y = 0; y < this.YSIZE; y++) {
                sum += this.MAP[x][y];
                if (sum >= r) {
                    return new Position(x, y);
                }
            }
        }
        return new Position(this.XSIZE - 1, this.YSIZE - 1);
    }
}
