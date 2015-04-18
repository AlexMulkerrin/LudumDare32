package Simulation;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Alex Mulkerrin
 */
public class Terrain {
    // ordered area around city to exploit
    private int[][] cityStencil = new int[][]{
        {0,0},{0,-1},{1,0},{0,1},{-1,0},
        {1,-1},{1,1},{-1,1},{-1,-1},{0,-2},
        {2,0},{0,2},{-2,0},{2,-1},{2,1},
        {1,2},{-1,2},{-2,1},{-2,-1},{-2,-1},
    };
    private int width;
    private int height;
    private Tile tile[][];
    public Agent[][] occupier;
    
    public int totalLand;
    public int totalFertile, totalAverage, totalBarren;
    
    public int mapTemperature=3;
    
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
        int temperature = 0;
        int rainfall = 0;
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
        generateTemperature();
        generateMapRainfall();
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
    
    public void generateTemperature() {
        int temp;
        int scaling =(mapTemperature+3)*2;
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                temp = j-height/2; // latitude
                temp = scaling*temp/height; //rescale from 0 to scale factor
                temp = (int)(Math.abs(temp));
                if (temp>4) temp=4;
                tile[i][j].temperature=temp;
                tile[i][j].rainfall=0;
            }
        }
    }
    
    public void generateMapRainfall() {
        for (int j=0; j<height; j++) {
            int wet=0;
            int temp =(Math.abs(height/2-j));
            for (int i=0; i<width; i++) { //easterly winds
                if (tile[i][j].elevation==0) {
                    int yield = Math.abs(temp-height/4) + 8;
                    if (yield>wet) wet++;
                } else if (wet>0) {
                    tile[i][j].rainfall=1;
                    wet-=2+tile[i][j].elevation;
                }
            }
            wet=0;
            for (int i=width-1; i>=0; i--) { //westerly winds
                if (tile[i][j].elevation==0) {
                    int yield = Math.abs(temp-height/4) + 8;
                    if (yield>wet) wet++;
                } else if (wet>0) {
                    tile[i][j].rainfall=1;
                    wet-=2+tile[i][j].elevation;
                }
            }
        }
    }
    
    
    public void generateFertility() {
        totalFertile=0;
        totalAverage=0;
        totalBarren=0;
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (tile[i][j].elevation>0) {
                    int chance = 3-tile[i][j].elevation;
                    int temp = tile[i][j].temperature;
                    if (temp==0 || temp==4) chance=0;
                    if (temp==1 || temp==3) chance--;
                    if (tile[i][j].rainfall>0) chance++;
                    if (chance>2) chance=2;
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
    
    public void climateChange() {
       mapTemperature+= (int)(Math.random()*3)-1;
       generateTemperature();
       generateMapRainfall();
       recalculateFertility();
       updateTotals(); 
    }
    
    public void recalculateFertility() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (tile[i][j].elevation>0) {
                    int chance = 3-tile[i][j].elevation;
                    int temp = tile[i][j].temperature;
                    if (temp==0 || temp==4) chance=0;
                    if (temp==1 || temp==3) chance--;
                    if (tile[i][j].rainfall>0) chance++;
                    if (chance>2) chance=2;
                    if (chance<0) chance=0;
                    tile[i][j].maxFertility = chance;
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
                if (tile[i][j].fertility<tile[i][j].maxFertility){//occupier[i][j]==null &&  {
                    int chance= (int)(Math.random()*20);
                    if (chance==0) tile[i][j].fertility++;
                } else if (tile[i][j].fertility>tile[i][j].maxFertility) {
                    //decay!
                    int chance= (int)(Math.random()*2);
                    if (chance==0) tile[i][j].fertility--;
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
            int temp = tile[x][y].temperature;
            if (temp==0 || temp==4) chance=0;
            if (temp==1 || temp==3) chance--;
            if (tile[x][y].rainfall>0) chance++;
            if (chance>2) chance=2;
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
    
    public int getTemperature(int x, int y) {
        return tile[x][y].temperature;    
    }
    
    public int getRainfall(int x, int y) {
        return tile[x][y].rainfall;    
    }
    
    public int getFertility(int x, int y) {
        return tile[x][y].fertility;    
    }
    
    public Boolean isExploited(int x, int y) {
        return tile[x][y].exploited;
    }
    
    public int getCityFertility(int x, int y) {
        int total=0;
//        int range=2;
//        for (int i=-range; i<=range; i++) {
//              for (int j=-range; j<=range; j++) {
        for (int e=0; e<cityStencil.length; e++) {
            int i=cityStencil[e][0];
            int j=cityStencil[e][1];
                  int nx=i+x;
                  int ny=j+y;
                  if (nx>0 && nx<width && ny>0 && ny<height) {
                      if (tile[nx][ny].fertility>0) total+=tile[nx][ny].fertility;
                  }
              }
//          }
        return total;
    }
    
      public void drainFertility(int x, int y) {
          if (tile[x][y].fertility>0) {
            tile[x][y].fertility--;
            
              
          }
      }
      
      public void drainCityFertility(int x, int y, int usage) {
          int drain=usage;
//          int range=2;
//          for (int i=-range; i<=range; i++) {
//              for (int j=-range; j<=range; j++) {
        for (int e=0; e<cityStencil.length; e++) {
            int i=cityStencil[e][0];
            int j=cityStencil[e][1];
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
//          }
      }
    
}
