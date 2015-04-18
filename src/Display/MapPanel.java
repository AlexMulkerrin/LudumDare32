package Display;

import Player.Player;
import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;
import Simulation.Agent;

/**
 *
 * @author Alex Mulkerrin
 */
public class MapPanel extends JPanel {
    String[] palette = new String[]{"#478CC1","#D8D8D8","#DAFF7F","#63E22D","#7F6A00","#FFE46D"};
    
    Simulation targetSim;
    Player targetPlayer;
    public int sqSize=10;

    public MapPanel(Simulation sim, Player player) {
        targetSim = sim;
        targetPlayer = player;
        
        addMouseListener(player);
        addMouseMotionListener(player);
        addMouseWheelListener(player);
        
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
        paintMouseHover(g);
    }
    
    public void paintTerrain(Graphics g) {
        g.setColor(Color.decode(palette[0]));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        for (int i=0; i<targetSim.map.getWidth(); i++) {
            for (int j=0; j<targetSim.map.getHeight(); j++) {
                int elev = targetSim.map.getElevation(i, j);
                if (elev>0) {
                    int n = targetSim.map.getFertility(i,j); 
                    g.setColor(Color.decode(palette[n+1]));
                    if (targetSim.map.isExploited(i,j) && n==0) {
                        g.setColor(Color.decode(palette[4]));
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
            if (toPaint.isNomadic) {
                g.setColor(Color.orange);
            } else {
                g.setColor(Color.red);
            }
            g.fillRect(x*sqSize+2, y*sqSize+2, sqSize-4, sqSize-4);
        }
    }
    
    public void paintMouseHover(Graphics g) {
        g.setColor(Color.red);
        
        g.drawRect(targetPlayer.mouseX*sqSize, targetPlayer.mouseY*sqSize,
                sqSize, sqSize);
    }
    
}
