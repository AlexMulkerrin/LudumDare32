package ludumdare32;

import Simulation.Simulation;
import Display.DisplayFrame;
import java.util.*;
/**
 *
 * @author Alex Mulkerrin
 */
public class LudumDare32 {
    DisplayFrame display;
    Simulation sim;
    Timer timer;

    public static void main(String[] args) {
        LudumDare32 program = new LudumDare32();
        program.run();
    }
    
    public LudumDare32() {
        sim = new Simulation(80,50,10);
        display = new DisplayFrame(sim);
    }
    
    public void run() {
        
        timer = new Timer();
        timer.schedule(new update(), 0, 100);
    }
    
    public class update extends TimerTask {
        @Override
        public void run() {
            sim.update();
            display.update();
        }
    }
    
}
