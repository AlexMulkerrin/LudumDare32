package Display;

import java.awt.*;
import javax.swing.*;
import Simulation.Simulation;

/**
 *
 * @author Alex Mulkerrin
 */
class StatPanel extends JPanel{
    JTextArea worldStats;
    JTextArea landStats;
    JTextArea factionStats;
    Simulation targetSim;

    public StatPanel(Simulation sim) {
        targetSim = sim;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("World Stats "));
        worldStats = new JTextArea();
        worldStats.setEditable(false);
        add(worldStats);
        
        add(new JLabel("Land Stats "));
        landStats = new JTextArea();
        landStats.setEditable(false);
        add(landStats);
        
        add(new JLabel("Faction Stats "));
        factionStats = new JTextArea();
        factionStats.setEditable(false);
        add(factionStats);
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateWorldStats();
        updateLandStats();
        updateFactionStats();
        
    }
    
    private void updateWorldStats() {
        String contents ="World: "+targetSim.name+"\n";
        contents += "Seed: "+targetSim.seed+"\n";
        contents += "Size: "+targetSim.map.getWidth()+","+targetSim.map.getHeight()+"\n";
        contents += "Turn: "+targetSim.turn;
        worldStats.setText(contents);
    }
    
    private void updateLandStats() {
        String contents = "Total Area: "+targetSim.map.getWidth()*targetSim.map.getHeight()+"\n";
        
        contents += "Total Land: "+targetSim.map.totalLand;
        int percent = 100*targetSim.map.totalLand/(targetSim.map.getWidth()*targetSim.map.getHeight());
        contents +=" ("+percent+"%)\n";
        
        contents += "of which: \n";
        contents += "Fertile: "+targetSim.map.totalFertile;
        percent = 100*targetSim.map.totalFertile/targetSim.map.totalLand;
        contents +=" ("+percent+"%)\n";
        contents += "Average: "+targetSim.map.totalAverage;
        percent = 100*targetSim.map.totalAverage/targetSim.map.totalLand;
        contents +=" ("+percent+"%)\n";
        contents += "Barren: "+targetSim.map.totalBarren;
        percent = 100*targetSim.map.totalBarren/targetSim.map.totalLand;
        contents +=" ("+percent+"%)\n";
        
        landStats.setText(contents);
    }
    
    private void updateFactionStats() {
        String contents ="TotalFactions: "+targetSim.unit.size()+"\n";
        contents += "Hunter Gatherer Tribes: "+targetSim.unit.size()+"\n";
        contents += "Average Faction population: "+targetSim.getAveragePop()+"\n";
        contents += "World population: "+targetSim.getTotalPop();
        factionStats.setText(contents);
    }
}
