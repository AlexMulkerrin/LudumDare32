package Display;

import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;
import Simulation.Agent;

/**
 *
 * @author Alex Mulkerrin
 */
class MapPanel extends JPanel {
    Simulation targetSim;
    int sqSize=10;

    public MapPanel(Simulation sim) {
        targetSim = sim;
        
        setPreferredSize(
                new Dimension(
                sqSize*targetSim.map.getWidth(),
                sqSize*targetSim.map.getHeight())
        );
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTerrain(g);
        paintAgents(g);
    }
    
    public void paintTerrain(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);
        for (int i=0; i<targetSim.map.getWidth(); i++) {
            for (int j=0; j<targetSim.map.getHeight(); j++) {
                int elev = targetSim.map.getElevation(i, j);
                if (elev>0) {
                    switch (targetSim.map.getFertility(i,j)) {
                        case 0: g.setColor(Color.gray);
                            break;
                        case 1: g.setColor(Color.yellow);
                            break;
                        case 2: g.setColor(Color.green);
                                 
                            
                    }
                    g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
                }
            }
        }
    }
    
    public void paintAgents(Graphics g) {
        g.setColor(Color.red);
        for (int i=0; i<targetSim.unit.size(); i++) {
            Agent toPaint = (Agent)targetSim.unit.get(i);
            int x = toPaint.x;
            int y = toPaint.y;
            g.fillRect(x*sqSize+1, y*sqSize+1, sqSize-2, sqSize-2);
        }
    }
    
}
