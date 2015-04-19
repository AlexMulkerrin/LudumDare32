package ludumdare32;

import Simulation.Simulation;
import Display.DisplayFrame;
import Player.Player;
import java.util.*;
/**
 *
 * @author Alex Mulkerrin
 */
public class LudumDare32 {
    public MenuFrame mainMenu;
    public DisplayFrame display;
    public Simulation sim;
    Player player;
    public Timer timer;
    TimerTask updater;
    int seed;
    

    public static void main(String[] args) {
        LudumDare32 program = new LudumDare32();
        //program.run();
    }
    
    public LudumDare32() {
        seed = (int)(System.nanoTime());
        sim = new Simulation(80,50,10,seed);
        player = new Player(sim);
        display = new DisplayFrame(sim,player, this);
        
        player.linkObject(display);
    }
    
    public void reset() {
        mainMenu.dispose();
        seed = (int)(System.nanoTime());
        sim = new Simulation(80,50,10,seed);
        player = new Player(sim);
        display = new DisplayFrame(sim,player, this);
        
        player.linkObject(display);
    }
    
    public void resetWithSeed() {
        mainMenu.dispose();
        sim = new Simulation(80,50,10,seed);
        player = new Player(sim);
        display = new DisplayFrame(sim,player, this);
        
        player.linkObject(display);
    }
    
    public void run() {
        
        timer = new Timer();
        updater =new UpdateTask();
        timer.schedule(updater, 0, 10);
    }
    
    public void update() {
        sim.update();
        display.update();
        if (sim.turn>=500 ){
            timer.cancel();
            gameEnd(sim.getTotalPop());
        } 
        if (sim.getTotalPop()<=0) {
            timer.cancel();
            gameEnd(0);
        }
    }
    
    public class UpdateTask extends TimerTask {
        @Override
        public void run() {
            update();
        }
    }
    
    public void gameEnd(int score) {
        mainMenu = new MenuFrame(this, score);
    }
    
}
