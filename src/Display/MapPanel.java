package Display;

import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;

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
    }
    
    public void paintTerrain(Graphics g) {
        g.setColor(Color.red);
        for (int i=0; i<targetSim.map.getWidth(); i++) {
            for (int j=0; j<targetSim.map.getHeight(); j++) {
                int elev = targetSim.map.getElevation(i, j);
                if (elev>0) {
                    g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
                }
            }
        }
    }
    
}
