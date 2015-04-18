package Display;

import Player.Player;
import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;
/**
 *
 * @author Alex Mulkerrin
 */
public class DisplayFrame extends JFrame{
    public MapPanel mapDisplay;
    StatPanel statDisplay;
    LogPanel logDisplay;
    DetailPanel detailDisplay;
    Simulation targetSim;
    Player targetPlayer;
    
    public DisplayFrame(Simulation sim, Player player) {
        super("Ludum Dare 32");
        targetSim = sim;
        targetPlayer =player;
        
        setLayout(new BorderLayout());
        
        mapDisplay = new MapPanel(sim, player);
        JScrollPane scroller = new JScrollPane(mapDisplay);
        getContentPane().add(scroller,BorderLayout.CENTER);
        
        statDisplay = new StatPanel(sim);
        getContentPane().add(statDisplay,BorderLayout.EAST);
        
        logDisplay = new LogPanel(sim);
        detailDisplay = new DetailPanel(sim, player);
        
        JPanel lowerPanel = new JPanel();
        lowerPanel.add(logDisplay);
        lowerPanel.add(detailDisplay);
        getContentPane().add(lowerPanel,BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,720);
        setVisible(true);
    }
    
    public void update() {
        mapDisplay.repaint();
        statDisplay.repaint();
        logDisplay.repaint();
        detailDisplay.repaint();
    }
}
