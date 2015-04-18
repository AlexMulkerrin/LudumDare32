package Display;

import Player.Player;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import ludumdare32.LudumDare32;
/**
 *
 * @author Alex Mulkerrin
 */
public class ButtonPanel extends JPanel{
    LudumDare32 targetProgram;
    Player targetPlayer;
    
    public ButtonPanel(LudumDare32 program, Player player) {
        
        targetProgram = program;
        targetPlayer= player;
        setLayout(new GridLayout(3,2));
        
        JButton button = new JButton("next turn");
        button.addActionListener(new endTurnButton());
        add(button);
        
        button = new JButton("Settle");
        button.addActionListener(new settleButton());
        add(button);
    }

    private class endTurnButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            targetProgram.update();        
        }
    }
    
    private class settleButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            targetPlayer.commandToSettle();        
        }
    }
}
