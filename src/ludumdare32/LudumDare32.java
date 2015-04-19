package ludumdare32;

import Simulation.Simulation;
import Display.DisplayFrame;
import Player.Player;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alex Mulkerrin
 */
public class LudumDare32 {
    MenuFrame mainMenu;
    DisplayFrame display;
    Simulation sim;
    Player player;
    Timer timer;
    TimerTask updater;
    

    public static void main(String[] args) {
        LudumDare32 program = new LudumDare32();
        //program.run();
    }
    
    public LudumDare32() {
        sim = new Simulation(80,50,10);
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
        if (sim.turn>=500 || sim.getTotalPop()<=0) {
            timer.cancel();
            gameEnd();
        }
    }
    
    public class UpdateTask extends TimerTask {
        @Override
        public void run() {
            update();
        }
    }
    
    public void gameEnd() {
        mainMenu = new MenuFrame();
    }
    
}
