import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MastermindGraphic extends JPanel {

    public MastermindGraphic() {
        setPreferredSize(new Dimension(850, 650)); // Set preferred size
    }
    public MastermindGraphic(CardLayout c) {
        super(c);
        //setPreferredSize(new Dimension(850, 650)); // Set preferred size
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawLine(100,0,100,650);
        g.drawLine(100,80,850,80);
        g.drawLine(600,82,600,650);
        //Division of Panel
        g.drawLine(100,170,850,170);
        g.drawLine(100,260,850,260);
        g.drawLine(100,350,850,350);
        g.drawLine(100,440,850,440);
        g.drawLine(100,525,850,525);
    }
}
class Winner extends JFrame{
    Main obj;
    JButton playAgain = new JButton("Play Again");
    JButton mainMenu = new JButton("Main Menu");
    JLabel Score = new JLabel("Your Score is:  ;)");
    JLabel Score2 = new JLabel();
    JLabel Win_or_Lose = new JLabel("Winner Winner Chicken Dienner!");
    Winner_panel panel = new Winner_panel();
    public Winner(){
        //setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(470,330);
        setLocationRelativeTo(null);
        panel.setBackground(Color.blue);
        panel.setLayout(null);
        this.add(panel);
        Win_or_Lose.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
        Win_or_Lose.setForeground(Color.YELLOW);
        Win_or_Lose.setBounds(50,20,1000,20);
        Score.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
        Score.setForeground(Color.YELLOW);
        Score.setBounds(70,55,1000,20);
        playAgain.setBounds(270,150,140,60);
        mainMenu.setBounds(50,150,140,60);
        playAgain.setBackground(Color.green);
        mainMenu.setBackground(Color.orange);
        panel.add(playAgain);
        panel.add(mainMenu);
        panel.add(Score);
        panel.add(Win_or_Lose);

        panel.add(Score2);
        Score2.setBounds(240,55,1000,20);
        Score2.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
        Score2.setForeground(Color.YELLOW);
        playAgain.addActionListener(new Winner.actions());
        mainMenu.addActionListener(new Winner.actions());

    }
    void setObj(Main c){
        obj = c;
    }
    public void win(int score,boolean w){
        Score2.setText(String.valueOf(score));
        setVisible(true);
        if(w){
            Win_or_Lose.setText("Winner Winner Chicken Dienner!");
        }else{
            Win_or_Lose.setText("    You Lose..., Try Again ):");
        }
        setLocationRelativeTo(null);

    }
    private class actions implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == playAgain){
                dispose();
                obj.reset();
            }
            if(e.getSource() == mainMenu){
                obj.openMainMenu();
                dispose();
                obj.reset();
            }
        }
    }



}
class Winner_panel extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(40,18,380,25);
    }
}