package Simulation;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Alex Mulkerrin
 */
public class Terrain {
    private int width;
    private int height;
    private Tile tile[][];
    public Agent[][] occupier;
    
    public int totalLand;
    public int totalFertile, totalAverage, totalBarren;
    Random random;
    
    public Terrain(int w, int h) {
        width = w;
        height = h;
        tile = new Tile[width][height];
        occupier = new Agent[width][height];
    }

    
    
    private class Tile {
        int elevation = 0;
        int fertility = 0;
    }
    
    public void clearMap() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                tile[i][j] = new Tile();
                occupier[i][j] = null;
            }
        }
    }
    
    public void generateWorld(Random randomGenerator) {
        random = randomGenerator;
        clearMap();
        generateHeightMap();
    }
    
    public void generateHeightMap() {
        totalLand=0;
        totalFertile=0;
        totalAverage=0;
        totalBarren=0;
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (i>0 && i<width-1 && j>0 && j<height-1) {
                    tile[i][j].elevation = random.nextInt(2);
                    if (tile[i][j].elevation>0) {
                        totalLand++;
                        tile[i][j].fertility = random.nextInt(3);
                        if (tile[i][j].fertility==2) {
                            totalFertile++;
                        } else if (tile[i][j].fertility==1) {
                            totalAverage++;
                        } else {
                            totalBarren++;
                        }
                    }
                }
            }
        }
    }
    
    public Agent createOccupier(int x, int y) {
        Agent toAdd;
        if (occupier[x][y]==null && tile[x][y].elevation>0) {
            toAdd = new Agent(x,y, this);
            occupier[x][y] = toAdd;
            return toAdd;
        }
        return null;
    }
    
    public Boolean checkValidMove(int x, int y) {
        Boolean success = false;
        if (tile[x][y].elevation>0 && occupier[x][y]==null) success=true;
        return success;
    }
    
    public void update(ArrayList<Agent> unit) {
        occupier = new Agent[width][height];
        for (int i=0; i<unit.size(); i++) {
            Agent toSet = unit.get(i);
            occupier[toSet.x][toSet.y]= toSet;
        }
    }
    
    
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getElevation(int x, int y) {
        return tile[x][y].elevation;
    }
    
    public int getFertility(int x, int y) {
        return tile[x][y].fertility;    
    }
    
}
