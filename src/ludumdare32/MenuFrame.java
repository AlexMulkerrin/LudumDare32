package ludumdare32;

import javax.swing.*;

/**
 *
 * @author Alex Mulkerrin
 */
class MenuFrame extends JFrame{
    
    public MenuFrame(int score) {
        if (score<=0) {
            add(new JLabel("Everyone died! :("));
        } else {
            add(new JLabel("This era is over"));
            add(new JLabel(score+" people survived."));
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,200);
        setVisible(true);
    }
    
}
