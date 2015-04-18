package Simulation;

/**
 *
 * @author Alex Mulkerrin
 */
public class Agent {
    public int x;
    public int y;
    public int oldx;
    public int oldy;
    public int population;
    private int environ;
    
    private Terrain map;

    
    public Agent(int placeX, int placeY, Terrain location) {
        x = placeX;
        y = placeY;
        oldx = x;
        oldy = y;
        map = location;
        environ = map.getFertility(x,y);
        population=50;
        
    }
    
    public void update() {
        wander();
        switch (environ) {
            case 0: {
                population-=5;
                break;
            }
            case 1: {
                //unchanged;
                break;
            }
            case 2: {
                population+=5;
                if (population>150) population=150;
                break;
            }
        }    
    }
    
    public void wander() {
        int nx=x;
        int ny=y;
        int choice = (int)(Math.random()*4);
        if (choice==0) ny--;
        if (choice==1) nx++;
        if (choice==2) ny++;
        if (choice==3) nx--;
        if (nx>0 && nx<map.getWidth() && ny>0 && ny<map.getHeight()) {
            if (map.checkValidMove(nx,ny)) {
                oldx=x;
                oldy=y;
                x=nx;
                y=ny;
                environ=map.getFertility(x, y);
                map.occupier[x][y] = this;
            }  
        }
    }
    
}
