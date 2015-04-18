package Display;

import javax.swing.*;
import Simulation.Simulation;
/**
 *
 * @author Alex Mulkerrin
 */
public class DisplayFrame extends JFrame{
    MapPanel mapDisplay;
    Simulation targetSim;
    
    public DisplayFrame(Simulation sim) {
        super("Ludum Dare 32");
        targetSim = sim;
        
        mapDisplay = new MapPanel(sim);
        JScrollPane scroller = new JScrollPane(mapDisplay);
        //scroller.
        getContentPane().add(scroller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640,480);
        setVisible(true);
    }
    
    public void update() {
        mapDisplay.repaint();
    }
}
