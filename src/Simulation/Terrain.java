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
    
    private Simulation sim;
    
    public Terrain(int w, int h, Simulation containingSim) {
        width = w;
        height = h;
        sim = containingSim;
        tile = new Tile[width][height];
        occupier = new Agent[width][height];
    }

    
    
    private class Tile {
        int elevation = 0;
        int fertility = 0;
        int maxFertility = 0;
        Boolean exploited = false;
    }
    
    public void clearMap() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                tile[i][j] = new Tile();
                occupier[i][j] = null;
            }
        }
    }
    
    public void generateWorld() {
        clearMap();
        generateHeightMap();
        generateFertility();
    }
    
    public void generateHeightMap() {
        totalLand=0;
        int border=2;
        int desiredLand = (width-border*2)*(height-border*2)/2;
        int[][] stamp = new int[][]{
            {0,0},{1,0},{0,1},{-1,0},{0,-1}
        };
        int[][] choices = new int[][]{
            {1,0},{0,1},{-1,0},{0,-1}
        };
        int[][] stencil;
        
        while (totalLand<desiredLand) {
            stencil = new int[width][height];
            int sx = sim.random.nextInt(width-2*border)+border;
            int sy = sim.random.nextInt(height-2*border)+border;
            int length = sim.random.nextInt(63)+1;
            Boolean insideBorder = true;
            while (length>0 && insideBorder) {
                for (int e=0; e<stamp.length; e++) {
                    stencil[sx+stamp[e][0]][sy+stamp[e][1]]=1;
                }
                int choice = sim.random.nextInt(choices.length);
                sx += choices[choice][0];
                sy += choices[choice][1];
                length--;
                if (sx<border || sx>=width-border || sy<border || sy>=height-border) {
                    insideBorder = false;
                }
            }
            
            for (int i=0; i<width; i++) {
                for (int j=0; j<height; j++) {
                    if (stencil[i][j]>0) {
                        if (tile[i][j].elevation<1) {
                            totalLand++;
                        }
                        tile[i][j].elevation += stencil[i][j];
                    }
                }
            }
        }
    }
    
    //public void 
    
    public void generateFertility() {
        totalFertile=0;
        totalAverage=0;
        totalBarren=0;
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (tile[i][j].elevation>0) {
                    int chance = 3-tile[i][j].elevation;
                    if (chance<0) chance=0;
                    tile[i][j].maxFertility = chance;
                    if (tile[i][j].maxFertility==2) {
                        tile[i][j].fertility=2;
                        totalFertile++;
                    } else if (tile[i][j].maxFertility==1) {
                        tile[i][j].fertility=1;
                        totalAverage++;
                    } else {
                        totalBarren++;
                    }
                }
                
            }
        }
    }
    
    public Agent createOccupier(int x, int y) {
        Agent toAdd;
        if (occupier[x][y]==null && tile[x][y].elevation>0) {
            toAdd = new Agent(x,y, this, sim);
            occupier[x][y] = toAdd;
            return toAdd;
        }
        return null;
    }
    
    public Agent createNewTribe(int x, int y) {
        Agent toAdd = new Agent(x,y, this, sim);
        occupier[x][y] = toAdd;
        return toAdd;

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
        replenishFertility();
        updateTotals();
    }
    
    public void updateTotals() {
        totalLand=0;
        totalFertile=0;
        totalAverage=0;
        totalBarren=0;
        for (int i=0; i<width; i++) {
                for (int j=0; j<height; j++) {
                    if (tile[i][j].elevation>0) {
                        totalLand++;
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
    
    public void replenishFertility() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (occupier[i][j]==null && tile[i][j].fertility<tile[i][j].maxFertility) {
                    int chance= (int)(Math.random()*20);
                    if (chance==0) tile[i][j].fertility++;
                }
            }
        }
    }
    
   public Boolean setElevation(int x, int y, int brushType) {
       Boolean success=false;
       if (tile[x][y].elevation>brushType) {
           tile[x][y].elevation--;
           setFertility(x,y);
           success=true;
       } else if (tile[x][y].elevation<brushType) {
           tile[x][y].elevation++;
           setFertility(x,y);
           success=true;
       }
       updateTotals();
       return success;
   }
   
   public void setFertility(int x, int y) {
        if (tile[x][y].elevation>0) {
            int chance = 3-tile[x][y].elevation;
            if (chance<0) chance=0;
            tile[x][y].maxFertility = chance;
            tile[x][y].fertility=0;

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
    
    public Boolean isExploited(int x, int y) {
        return tile[x][y].exploited;
    }
    
    public int getCityFertility(int x, int y) {
        int total=0;
        for (int i=-2; i<3; i++) {
              for (int j=-2; j<3; j++) {
                  int nx=i+x;
                  int ny=j+y;
                  if (nx>0 && nx<width && ny>0 && ny<height) {
                      if (tile[nx][ny].fertility>0) total+=tile[nx][ny].fertility;
                  }
              }
          }
        return total;
    }
    
      public void drainFertility(int x, int y) {
          if (tile[x][y].fertility>0) {
            tile[x][y].fertility--;
            
              
          }
      }
      
      public void drainCityFertility(int x, int y, int usage) {
          int drain=usage;
          for (int i=-2; i<3; i++) {
              for (int j=-2; j<3; j++) {
                  int nx=i+x;
                  int ny=j+y;
                  if (nx>0 && nx<width && ny>0 && ny<height) {
                      if (tile[nx][ny].fertility>0) {
                          tile[nx][ny].fertility--;
                          tile[nx][ny].exploited=true;
                          drain--;
                          if (drain<1) return;
                      }
                      
                  }
              }
          }
      }
    
}
