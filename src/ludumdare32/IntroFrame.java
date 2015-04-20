package ludumdare32;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Alex Mulkerrin
 */
public class IntroFrame extends JFrame{
    
    public IntroFrame(String name) {
        super("Introduction");
        setIconImage(new ImageIcon(getClass().getResource("/Resources/icon.png")).getImage());
    
        JTextArea blurb = new JTextArea();
        String contents ="Welcome to the world of "+name+",";
        contents += " where the forces of nature have run amok.\n\n";
        contents += "You, a minor forest spirit must use your supernatural powers to stop this assault.\n";
        contents += "Hopefully you can survive the onslaught before the Civilizations of this world are wiped out!\n\n";
        
        contents +="Controls:\n";
        contents +="use the mouse to select a power from the bottom right buttons.\n";
        contents +="Select a tribe and make them settle somewhere with lots of fertile, food producing land.\n";
        contents +="If the land is not to your liking you can change its elevation.\n";
        contents +="Your powers take mana, only the passage of time can replenish it.\n\n";
        contents +="Tribes will be more likely to survive as farmers but beware, the seasons might chance drastically.\n";
        contents +="Select a tribe and use the goto command to have then journey to a safer location.\n";
        contents +="If too many tribes gather in one area they might begin fighting!\n\n";
        contents +="Close this window when you are ready to begin";
        blurb.setText(contents);
        add(blurb);

        setSize(540,340);
        setVisible(true);
        setLocationRelativeTo(null);
    
    
    }
}
