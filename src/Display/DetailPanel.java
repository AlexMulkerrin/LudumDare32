package Display;

import Player.Player;
import Simulation.Agent;
import Simulation.Simulation;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Alex Mulkerrin
 */
public class DetailPanel extends JPanel {
    JTextArea details;
    Simulation targetSim;
    Player targetPlayer;
    
    public DetailPanel(Simulation sim, Player player) {
        targetSim = sim;
        targetPlayer = player;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300,150));
        
        add (new JLabel("Current Moused Over"));
        details = new JTextArea();
        details.setEditable(false);
        add(details);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateDetails();
    }
    
    public void updateDetails() {
        int x=targetPlayer.mouseX;
        int y=targetPlayer.mouseY;
        
        String contents = "Mouse position: "+x+","+y+"\n";
        if (targetPlayer.brushType==0) {
            contents += "Current power: sink land.\n";
        } else if (targetPlayer.brushType==1) {
            contents += "Current power: raise land.\n";
        } else if (targetPlayer.brushType==2) {
            contents += "Current power: raise hill.\n";
        } else if (targetPlayer.brushType==3) {
            contents += "Current power: raise mountain.\n";
        }
        contents += "Power magnitute: "+targetPlayer.brushSize+"\n";
        contents += "Tile elevation: "+targetSim.map.getElevation(x,y)+"\n";
        contents += "Tile fertility: "+targetSim.map.getFertility(x,y)+"\n";
        if (targetSim.map.occupier[x][y]!=null) {
            Agent hovered = targetSim.map.occupier[x][y];
            contents += "Tribe: "+hovered.name+"\n";
            contents += "population: "+hovered.population+"\n";
        } else {
            contents +="Unoccupied\n";
        }
        details.setText(contents);
    }
}
