package Player;

import java.awt.event.*;
import Simulation.Simulation;
import Display.DisplayFrame;
import javax.swing.SwingUtilities;
/**
 *
 * @author Alex Mulkerrin
 */
public class Player extends MouseAdapter {
    public int mouseX=0;
    public int mouseY=0;
    public int brushType = 0;
    public int brushSize = 1;
    
    Simulation targetSim;
    DisplayFrame targetDisplay;
    
    public Player(Simulation sim) {
        targetSim=sim;   
    }
    
    public void linkObject(DisplayFrame display) {
        targetDisplay=display;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            brushType++;
            if (brushType>3) brushType=0;
        } else if (targetSim.mana>0) {
            areaEffect();
        }
        targetDisplay.repaint();
    }
    
    @Override 
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX()/targetDisplay.mapDisplay.sqSize;
        mouseY = e.getY()/targetDisplay.mapDisplay.sqSize;
        if (mouseX<0) mouseX=0;
        if (mouseX>=targetSim.map.getWidth()) mouseX=targetSim.map.getWidth()-1;
        
        if (mouseY<0) mouseY=0;
        if (mouseY>=targetSim.map.getHeight()) mouseY=targetSim.map.getHeight()-1;
        if (targetSim.mana>0) {
            areaEffect();
        }
        targetDisplay.repaint();
        
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        brushSize += e.getWheelRotation();
        if (brushSize<1) brushSize=1;
        targetDisplay.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX()/targetDisplay.mapDisplay.sqSize;
        mouseY = e.getY()/targetDisplay.mapDisplay.sqSize;
        if (mouseX<0) mouseX=0;
        if (mouseX>=targetSim.map.getWidth()) mouseX=targetSim.map.getWidth()-1;
        
        if (mouseY<0) mouseY=0;
        if (mouseY>=targetSim.map.getHeight()) mouseY=targetSim.map.getHeight()-1;
        targetDisplay.repaint();
    }
    
    public void areaEffect() {
        for (int range=0; range<brushSize; range++) {
            for (int i=-range; i<=range; i++) {
                changeTile(mouseX+range,mouseY+i);
                changeTile(mouseX-range,mouseY+i);
                changeTile(mouseX+i,mouseY+range);
                changeTile(mouseX+i,mouseY-range);
                
            }
        }
    }
    
    public void changeTile(int x, int y) {
        if (targetSim.mana>0) {
            if (x<0) x=0;
            if (x>=targetSim.map.getWidth()) x=targetSim.map.getWidth()-1;
            if (y<0) y=0;
            if (y>=targetSim.map.getHeight()) y=targetSim.map.getHeight()-1;

            if (targetSim.map.setElevation(x,y,brushType)) {
                targetSim.mana--;
            }
        }
    }

    
}
