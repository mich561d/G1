package g1.maps;

import battleship.interfaces.Position;
import java.util.ArrayList;
import java.util.List;

public class FloatMap {

    private final float[][] map;
    private final int xSize;
    private final int ySize;

    public FloatMap(int xSize, int ySize) {
        this.map = new float[xSize][ySize];
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
                this.map[x][y] = 0.0F;
            }
        }
    }

    public void setAll(float f) {
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                this.map[x][y] = f;
            }
        }
    }

    public void set(int x, int y, float f) {
        this.map[x][y] = f;
    }

    public void add(int x, int y, float f) {
        this.map[x][y] += f;
    }

    public float get(int x, int y) {
        return this.map[x][y];
    }

    public FloatMap getNormalized() {
        float max = 0.0F;
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                if (this.map[x][y] > max) {
                    max = this.map[x][y];
                }
            }
        }
        FloatMap res = new FloatMap(this.xSize, this.ySize);
        if (max > 0.0F) {
            float invMax = 1.0F / max;
            for (int x = 0; x < this.xSize; x++) {
                for (int y = 0; y < this.ySize; y++) {
                    res.map[x][y] = (invMax * this.map[x][y]);
                }
            }
        }
        return res;
    }

    public void normalize() {
        float max = 0.0F;
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                if (this.map[x][y] > max) {
                    max = this.map[x][y];
                }
            }
        }
        if (max > 0.0F) {
            float invMax = 1.0F / max;
            for (int x = 0; x < this.xSize; x++) {
                for (int y = 0; y < this.ySize; y++) {
                    this.map[x][y] *= invMax;
                }
            }
        }
    }

    public List<Position> getHighest() {
        float max = 0.0F;
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

    public FloatMap reverse() {
        FloatMap res = new FloatMap(this.xSize, this.ySize);
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                res.map[x][y] = (1.0F - this.map[x][y]);
            }
        }
        return res;
    }

    public FloatMap add(FloatMap fMap) {
        if ((this.xSize != fMap.xSize) || (this.ySize != fMap.ySize)) {
            throw new RuntimeException("FloatMap.add -> Maps are not the same size...");
        }
        FloatMap res = new FloatMap(this.xSize, this.ySize);
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                this.map[x][y] += fMap.map[x][y];
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

    public Position getWeightedRandom(float random) {
        float sum = 0.0F;
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                sum += this.map[x][y];
            }
        }
        float r = sum * random;
        sum = 0.0F;
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.ySize; y++) {
                sum += this.map[x][y];
                if (sum >= r) {
                    return new Position(x, y);
                }
            }
        }
        return new Position(this.xSize - 1, this.ySize - 1);
    }
}
