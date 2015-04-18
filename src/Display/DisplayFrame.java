package Display;

import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;
/**
 *
 * @author Alex Mulkerrin
 */
public class DisplayFrame extends JFrame{
    MapPanel mapDisplay;
    StatPanel statDisplay;
    Simulation targetSim;
    
    public DisplayFrame(Simulation sim) {
        super("Ludum Dare 32");
        targetSim = sim;
        
        setLayout(new BorderLayout());
        
        mapDisplay = new MapPanel(sim);
        JScrollPane scroller = new JScrollPane(mapDisplay);
        getContentPane().add(scroller,BorderLayout.CENTER);
        
        statDisplay = new StatPanel(sim);
        getContentPane().add(statDisplay,BorderLayout.EAST);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,550);
        setVisible(true);
    }
    
    public void update() {
        mapDisplay.repaint();
        statDisplay.repaint();
    }
}
