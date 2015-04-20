package ludumdare32;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Alex Mulkerrin
 */
class MenuFrame extends JFrame{
    LudumDare32 targetProgram;
    
    public MenuFrame(LudumDare32 program, int score) {
        super("Game Over");
        setIconImage(new ImageIcon(getClass().getResource("/Resources/icon.png")).getImage());
        targetProgram = program;
        setLayout(new BorderLayout());
        
        if (score<=0) {
            getContentPane().add(new JLabel(" Civilization has been destroyed"),BorderLayout.NORTH);
        } else {
            getContentPane().add(new JLabel(" This era is over, congratulations!"),BorderLayout.NORTH);
         }
        
        getContentPane().add(new ScoreHistogram(),BorderLayout.CENTER);
          
        JPanel buttons = new JPanel(new GridLayout(0,3));
        
        JButton button = new JButton("retry");
        button.addActionListener(new RetryMapListener());
        buttons.add(button);
        
        button = new JButton("new world");
        button.addActionListener(new NewMapListener());
        buttons.add(button);
        
        button = new JButton("quit");
        button.addActionListener(new QuitButtonListener());
        buttons.add(button);
        getContentPane().add(buttons,BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560,440);
        setVisible(true);
        setLocationRelativeTo(null);
    }   

    private class ScoreHistogram extends JPanel{
        
        public ScoreHistogram() {
            add(new JLabel("Population: "+
                    targetProgram.sim.score[500]+
                    " (max: "+targetProgram.sim.maxScore+") "
                    +"        Rank: "+getGrade()));
            repaint();
        }
        
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.gray);
            g.fillRect(15, 22, 510, 325);
            g.setColor(Color.white);
            g.fillRect(15, 325, 510, 1);
            float scale= targetProgram.sim.maxScore;
            int power=10;
            while (power<targetProgram.sim.maxScore) {
                float s1=power/scale;
                g.fillRect(15, 325-(int)(s1*300), 510, 1);
                power*=10;
            }
            g.setColor(Color.blue);
            int[] values = targetProgram.sim.score;
            for (int i=1; i<values.length; i++) {
                if (i%50==0) {
                    g.setColor(Color.white);
                    g.fillRect(i, 22, 1, 325);
                    g.setColor(Color.blue);
                }
                float s1 = values[i-1]/scale;
                float s2 = values[i]/scale;
                g.drawLine(20+i-1, 325-(int)(s1*300), 20+i, 325-(int)(s2*300));
                
            }
        }
        
        public String getGrade() {
            String[] results = new String[]{
                "F  Banished sprite","E  Godling","D  Guardian Spirit",
                "C  Demi God","B  Promethean","A  Supreme Being",
                "A+  God of HAXX"
                
            };
            int grade=0;
            int power=10;
            while (power<targetProgram.sim.score[500]) {
                power*=10;
                grade++;
            }
            return results[grade];
        }
        
        
    }
    
    

    
    private class RetryMapListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            targetProgram.display.dispose();
            targetProgram.resetWithSeed();
        }
    }
    
    private class NewMapListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            targetProgram.display.dispose();
            targetProgram.reset();
        }
    }
    
    static public class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            System.exit(0);
        }
    }
    
}  
