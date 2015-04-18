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
    DisplayFrame display;
    Simulation sim;
    Player player;
    Timer timer;
    

    public static void main(String[] args) {
        LudumDare32 program = new LudumDare32();
        program.run();
    }
    
    public LudumDare32() {
        sim = new Simulation(80,50,10);
        player = new Player(sim);
        display = new DisplayFrame(sim,player, this);
        
        player.linkObject(display);
    }
    
    public void run() {
        
        timer = new Timer();
        timer.schedule(new UpdateTask(), 0, 1000);
    }
    
    public void update() {
        sim.update();
        display.update();
    }
    
    public class UpdateTask extends TimerTask {
        @Override
        public void run() {
            update();
        }
    }
    
}
