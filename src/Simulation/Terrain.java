package Simulation;

import java.util.Random;
/**
 *
 * @author Alex Mulkerrin
 */
public class Terrain {
    private int width;
    private int height;
    private Tile tile[][];
    private Agent[][] occupier;
    
    public int totalLand;
    Random random;
    
    public Terrain(int w, int h) {
        width = w;
        height = h;
        tile = new Tile[width][height];
        occupier = new Agent[width][height];
    }
    
    private class Tile {
        int elevation = 0;
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
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (i>0 && i<width-1 && j>0 && j<height-1) {
                    tile[i][j].elevation = random.nextInt(2);
                }
            }
        }
    }
    
    public Agent createOccupier(int x, int y) {
        Agent toAdd;
        if (occupier[x][y]==null && tile[x][y].elevation>0) {
            toAdd = new Agent(x,y);
            occupier[x][y] = toAdd;
            return toAdd;
        }
        return null;
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
    
}
