package Display;

import Player.Player;
import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;
import Simulation.Agent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

/**
 *
 * @author Alex Mulkerrin
 */
public class MapPanel extends JPanel {
                                    //ocean     grass   shrubs      trees       field    desert
    String[] palette = new String[]{"#478CC1","#D8D8D8","#DAFF7F","#63E22D","#7F6A00","#FFE46D",
                                    //jungle    plain    swamp     forest    tundra   heath     taiga     arctic              
                                    "#C6E256","#DAFF7F","#007F7F","#267F00","#7C5F4C","#9992A3","#005D52","#EAEAEA",
                                    //ice?      coast
                                    "#ffffff","#B4DAF7"
    };
    BufferedImage hillSprite;
    BufferedImage mountainSprite;
    
    Simulation targetSim;
    Player targetPlayer;
    public int sqSize=10;

    public MapPanel(Simulation sim, Player player) {
        targetSim = sim;
        targetPlayer = player;
        
        addMouseListener(player);
        addMouseMotionListener(player);
        addMouseWheelListener(player);
        
        try {
            hillSprite = ImageIO.read(new File(getClass().getResource("/Resources/hill.png").toURI()));
            mountainSprite = ImageIO.read(new File(getClass().getResource("/Resources/mountain.png").toURI()));
        } catch (IOException | URISyntaxException ex) {
        }


        
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
                    int t = targetSim.map.getTemperature(i, j);
                    if (t==0) g.setColor(Color.decode(palette[n+5]));
                    if (t==1) g.setColor(Color.decode(palette[n+7]));
                    if (t==2) g.setColor(Color.decode(palette[n+1]));
                    if (t==3) g.setColor(Color.decode(palette[n+10]));
                    if (t==4) g.setColor(Color.decode(palette[n+13]));
                    if (targetSim.map.isExploited(i,j) && n==0) {
                        g.setColor(Color.decode(palette[4]));
                    }
                
                    g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
                    g.setColor(Color.BLACK);
                    if (elev==2) {
                        g.drawImage(hillSprite, i*sqSize, j*sqSize, this);
                       //g.fillRect(i*sqSize+4, j*sqSize+4, sqSize-8, sqSize-8); 
                    }
                    if (elev==3) {
                        g.drawImage(mountainSprite, i*sqSize, j*sqSize, this);
                       //g.fillRect(i*sqSize+3, j*sqSize+3, sqSize-6, sqSize-6); 
                    }
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
