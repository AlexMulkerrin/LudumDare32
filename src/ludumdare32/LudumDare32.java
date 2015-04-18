package ludumdare32;

import Simulation.Simulation;
import Display.DisplayFrame;
/**
 *
 * @author Alex Mulkerrin
 */
public class LudumDare32 {
    DisplayFrame display;
    Simulation sim;

    public static void main(String[] args) {
        LudumDare32 program = new LudumDare32();
        program.run();
    }
    
    public LudumDare32() {
        sim = new Simulation(80,50,10);
        display = new DisplayFrame(sim);
    }
    
    public void run() {
        sim.update();
        display.update();
    }
    
}
