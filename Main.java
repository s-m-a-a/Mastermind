
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    // Constants and Variables
    static int score = 0;
    Winner win_or_lose = new Winner();
    int turn = 0;
    boolean gamestatue = false;
    JLabel scoreLabel = new JLabel("0");
    //Game atrribute
    boolean hidden_guess[] = {true,true,true,true};
    boolean hidden_turn[] = {true,true,true,true};
    int res[] = {0,0};
    //private static int currentIndex = 4;
    private Color[] redoColors = new Color[4];
    // Graphic component for the game
    //MastermindGraphic g = new MastermindGraphic();
    JLabel playerMessage = new JLabel("");
    // Main components
    JLabel gamePlayerScore = new JLabel("Score :");
    JPanel hiddenPanelOne = new JPanel();
    JLabel hidePanelLabelOne = new JLabel("Guess What !");
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    JPanel mainMenu = new JPanel();
    static MastermindGraphic gamePlayerPanel = new MastermindGraphic();
    JPanel chooseGame = new JPanel(); // Panel for choosing player mode
    JPanel gameDescription = new JPanel();

    // Buttons for the main menu
    JButton startGame = new JButton("Start Game");
    JButton aboutGame = new JButton("About Game");
    JButton mainExit = new JButton("EXIT");
    JButton exitChooseGamePanel = new JButton("EXIT");
    JButton exitGameDescription = new JButton("EXIT");
    JButton chooseColor = new JButton("Choose Color");
    JButton onePlayerBtn = new JButton("One Player");
    JButton twoPlayerBtn = new JButton("Two Player");

    String logo = "Mastermind"; // Game logo
    JLabel[] label = new JLabel[logo.length()]; // Array for logo characters

    // Create buttons and components for One Player Panel
    JButton[] gamePlayerButtons = new JButton[28];// turn
    JButton[] gamePLayerPins = new JButton[24];
    JButton gamePlayerGuess = new JButton("Guess The Color");
    JButton exitGamePlayer = new JButton("EXIT");
    JButton gamePlayerRedo = new JButton("Redo>>");
    JButton gamePlayerUndo = new JButton("<<Undo");
    JButton gamePlayerDelete = new JButton("Delete All");

    // Color selection radio buttons
    JRadioButton gameYellow = new JRadioButton("Yellow");
    JRadioButton gameRed = new JRadioButton("Red");
    JRadioButton gameBlue = new JRadioButton("Blue");
    JRadioButton gameGreen = new JRadioButton("Green");
    ButtonGroup gameColorGroup = new ButtonGroup();

    // Constructor
    Main() {
        win_or_lose.setObj(this);
        new Describton(); // Initialize game description
        setMainComponent(); // Set up main menu components
        setLogo(); // Set up logo display
        setHiddenPanel(); // Set up hidden panel
        setGamePlayerComponent(); // Set up one player game components
        setActions(); // Set up button actions

        // Set up the main frame
        this.add(mainPanel);
        this.setName("Mastermind");
        this.setSize(850, 650);
        this.setLocation(200, 80);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // Update the UI after setting up the frame
        SwingUtilities.updateComponentTreeUI(this);

    }
    void addRedo(Color c){
        for(int i = 0;i<4;i++){
            if(redoColors[i].equals(Color.LIGHT_GRAY)){
                redoColors[i] = c;
                break;
            }
        }
    }
    void resetredo(){
        for(int i = 0; i <4;i++){
            redoColors[i] = Color.LIGHT_GRAY;
        }
    }
    void redo(){
        for(int i = 3;i>=0;i--){
            if(!redoColors[i].equals(Color.LIGHT_GRAY)){
                changeButtonColor(redoColors[i]);
                redoColors[i] = Color.LIGHT_GRAY;
                break;
            }
        }
    }
    boolean checkRedo(){
        for(int i = 0;i<4;i++){
            if(!redoColors[i].equals(Color.LIGHT_GRAY)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new); // Launch the application
    }
    // Set main components for the UI
    private void setMainComponent() {
        // Set layout for Main Menu
        mainMenu.setLayout(null);
        startGame.setBounds(330, 160, 150, 50);
        aboutGame.setBounds(330, 260, 150, 50);
        chooseColor.setBounds(330, 360, 150, 50);
        mainExit.setBounds(330, 460, 150, 50);

        // Set layout for Choose Game
        chooseGame.setLayout(null);
        onePlayerBtn.setBounds(320, 100, 170, 50);
        twoPlayerBtn.setBounds(320, 250, 170, 50);
        exitChooseGamePanel.setBounds(320, 400, 170, 50);
        // Add buttons to choose game
        chooseGame.add(onePlayerBtn);
        chooseGame.add(twoPlayerBtn);
        chooseGame.add(exitChooseGamePanel);

        // Add buttons to Main Menu
        mainMenu.add(startGame);
        mainMenu.add(aboutGame);
        mainMenu.add(chooseColor);
        mainMenu.add(mainExit);

        // Add buttons to Game Description
        gameDescription.setLayout(null);
        gameDescription.add(exitGameDescription);
        exitGameDescription.setBackground(Color.RED);
        exitGameDescription.setBounds(5, 579, 80, 30);

        // Add panels to mainPanel
        mainPanel.add(mainMenu, "Main Menu");
        mainPanel.add(gamePlayerPanel, "GAME");
        mainPanel.add(chooseGame, "Choose Game");
        mainPanel.add(gameDescription, "Game Description");

        // Set backgrounds for panels
        gamePlayerPanel.setBackground(Color.WHITE);
        mainMenu.setBackground(Color.BLACK);
        chooseGame.setBackground(Color.BLACK);
        gameDescription.setBackground(Color.BLACK);

        // Set button backgrounds
        onePlayerBtn.setBackground(Color.YELLOW);
        twoPlayerBtn.setBackground(Color.GREEN);
        exitChooseGamePanel.setBackground(Color.RED);
        startGame.setBackground(Color.MAGENTA);
        aboutGame.setBackground(Color.GREEN);
        chooseColor.setBackground(Color.YELLOW);
        mainExit.setBackground(Color.CYAN);

        // Update the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    // Set up the logo part
    private void setLogo() {
        Color[] colors = {
                Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA,
                Color.ORANGE, Color.CYAN, Color.PINK, Color.YELLOW,
                Color.LIGHT_GRAY, Color.DARK_GRAY
        };

        // Create labels for each character in the logo
        for (int i = 0; i < logo.length(); i++) {
            label[i] = new JLabel(String.valueOf(logo.charAt(i)));
            label[i].setFont(new Font("Mastermind", Font.PLAIN, 45));
            mainMenu.add(label[i]);
        }

        // Timer for changing logo colors
        Timer timer = new Timer(300, new ActionListener() {
            int in = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < label.length; i++) {
                    Color letterColor = colors[(in + i) % colors.length];
                    label[i].setForeground(letterColor);
                }
                in = (in + 1) % colors.length;
            }
        });
        timer.start();

        // Position labels
        for (int i = 0; i < label.length; i++) {
            label[i].setBounds(220 + (i * 40), 40, 100, 50);
        }
    }
    // Set up Hidden Panel One
    private void setHiddenPanel() {
        hiddenPanelOne.setLayout(null);
        hiddenPanelOne.setBackground(Color.WHITE);
        hidePanelLabelOne.setForeground(Color.RED);
        hidePanelLabelOne.setFont(new Font("Guess What !", Font.BOLD, 30));
        hiddenPanelOne.add(hidePanelLabelOne);
        hiddenPanelOne.setBounds(101, 0, 800, 80);
        hidePanelLabelOne.setBounds(250, 15, 500, 50);
    }
    // Set bounds for onePlayerPanel Component
    private void setGamePlayerComponent() {
        gamePlayerPanel.setLayout(null);

        // Create buttons for player guesses
        for (int i = 0; i < gamePlayerButtons.length; i++) {
            gamePlayerButtons[i] = new JButton("" + (i + 1));
            gamePlayerButtons[i].setBackground(Color.lightGray);
            int x = (i % 4) * 130;
            int y = (i / 4) * 84;
            gamePlayerButtons[i].setBounds(x + 117, y + 25, 80, 50);
            gamePlayerPanel.add(gamePlayerButtons[i]);
        }

        // Set bounds for other components
        exitGamePlayer.setBounds(10, 580, 80, 30);
        gameRed.setBounds(15, 110, 70, 30);
        gameYellow.setBounds(15, 190, 70, 30);
        gameBlue.setBounds(15, 270, 70, 30);
        gameGreen.setBounds(15, 350, 70, 30);
        gamePlayerUndo.setBounds(10, 420, 80, 30);
        gamePlayerRedo.setBounds(10, 470, 80, 30);
        gamePlayerDelete.setBounds(5, 520, 90, 30);
        gamePlayerGuess.setBounds(101, 583, 501, 30);
        gamePlayerScore.setBounds(10, 10, 100, 50);

        // Create buttons for player pins
        for (int i = 0; i < gamePLayerPins.length; i++) {
            gamePLayerPins[i] = new JButton();
            int x = (i % 4) * 60;
            int y = (i / 4) * 87;
            gamePLayerPins[i].setBounds(x + 610, y + 120, 40, 30);
            gamePlayerPanel.add(gamePLayerPins[i]);
        }

        // Add components to One Player Panel
        gamePlayerPanel.add(scoreLabel);
        gamePlayerPanel.add(hiddenPanelOne);
        //g.setBounds(0, 0, 850, 650);
        gamePlayerPanel.add(gamePlayerScore);
        //gamePlayerPanel.add(g);
        gamePlayerPanel.add(exitGamePlayer);
        gamePlayerPanel.add(gameRed);
        gamePlayerPanel.add(gameYellow);
        gamePlayerPanel.add(gameGreen);
        gamePlayerPanel.add(gameBlue);
        gamePlayerPanel.add(gamePlayerUndo);
        gamePlayerPanel.add(gamePlayerRedo);
        gamePlayerPanel.add(gamePlayerDelete);
        gamePlayerPanel.add(gamePlayerGuess);
        gamePlayerScore.setFont(new Font("Score :", Font.PLAIN, 25));
        scoreLabel.setFont(new Font("Score :", Font.PLAIN, 25));
        scoreLabel.setBounds(50, 40, 100, 50);
        scoreLabel.setForeground(Color.RED);


        // Add radio buttons to color group
        gameColorGroup.add(gameRed);
        gameColorGroup.add(gameYellow);
        gameColorGroup.add(gameGreen);
        gameColorGroup.add(gameBlue);

        // Set colors for one player panel
        gameRed.setBackground(Color.RED);
        gameYellow.setBackground(Color.YELLOW);
        gameGreen.setBackground(Color.GREEN);
        gameBlue.setBackground(Color.BLUE);
        gamePlayerGuess.setBackground(Color.RED);
        gamePlayerScore.setForeground(Color.RED);
        gamePlayerScore.setFont(new Font("Score :", Font.PLAIN, 25));
        playerMessage.setForeground(Color.WHITE);
        playerMessage.setFont(new Font("", Font.BOLD, 16));
        playerMessage.setBounds(15, 400, 300, 30); // Adjust the position as needed
        gamePlayerPanel.add(playerMessage);

        // Update panel
        gamePlayerPanel.revalidate();
        gamePlayerPanel.repaint();
        SwingUtilities.updateComponentTreeUI(gamePlayerPanel);

    }
    // Set action listeners for buttons
    private void setActions() {
        // Set action listeners for buttons
        startGame.addActionListener(e -> cardLayout.show(mainPanel, "Choose Game"));
        aboutGame.addActionListener(e -> cardLayout.show(mainPanel, "Game Description"));
        exitGameDescription.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));
        exitGamePlayer.addActionListener(e -> cardLayout.show(mainPanel, "Choose Game"));
        exitChooseGamePanel.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));

        onePlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamestatue = false;
                reset();
                turn = 5;
                cardLayout.show(mainPanel, "GAME");
                // Set each button to a random color
                for (int i = 0; i < 4; i++) {
                    gamePlayerButtons[i].setBackground(getRandomColor());
                }
                deleteAllColors();
                for(int i =0;i<4;i++){
                    System.out.println(gamePlayerButtons[i].getBackground());
                }
            }

        });
        twoPlayerBtn.addActionListener(e -> {
            gamestatue = true;
            reset();
            turn = 1;
            //System.out.println(gamePlayerButtons[5].getBackground());
            cardLayout.show(mainPanel, "GAME");
            for (int i = 0; i < 4; i++) {
                gamePlayerButtons[i].setBackground(Color.WHITE);
            }
            deleteAllColors();
            //An idea
            /*
            Color selectedColor= Color.WHITE;
                if(gameRed.isSelected()){
                    selectedColor=Color.RED;
                } else if (gameYellow.isSelected()) {
                    selectedColor=Color.YELLOW;
                } else if (gameBlue.isSelected()) {
                    selectedColor=Color.BLUE;
                }else {
                    selectedColor=Color.GREEN;
                }
                for(int i=0; i<27;i++){
                setButtonColor(i,selectedColor);
            }*/
            //currentIndex = 0;
        });


        // Add action listeners to radio buttons
        gameRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtonColor(Color.RED);
                resetredo();

            }
        });

        gameYellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtonColor(Color.YELLOW);
                resetredo();
            }
        });

        gameBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtonColor(Color.BLUE);
                resetredo();
            }
        });

        gameGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtonColor(Color.GREEN);
                resetredo();
            }
        });

        gamePlayerDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTurnColors();
            }
        });
        gamePlayerUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoColorChange();
            }
        });

        gamePlayerRedo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkRedo()){
                    redo();
                }
                else{
                    JOptionPane.showMessageDialog(gamePlayerPanel, "Theres nothing to redo", "Times", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // Color chooser action
        chooseColor.addActionListener(e -> {
            Color co = JColorChooser.showDialog(null, "Choose color", Color.YELLOW);
            if (co != null) {
                mainMenu.setBackground(co);
            }
        });

        // Exit application action
        mainExit.addActionListener(e -> System.exit(0));
        gamePlayerGuess.addActionListener(e->{
            resetredo();
            if(turn == 1){
                turn+=4;
                JOptionPane.showMessageDialog(gamePlayerPanel, "You can play now", "Times", JOptionPane.PLAIN_MESSAGE);
            }
            else{
                if(check()){
                    System.out.println("comp");
                    if(Compare(turn - 1)){
                        //JOptionPane.showMessageDialog(gamePlayerPanel, "You Win", "Times", JOptionPane.PLAIN_MESSAGE);
                        addScore();
                        for(int i= 0;i<4;i++){
                            gamePlayerButtons[i].setVisible(true);
                        }
                        win_or_lose.win(score,true);

                    }else{
                        setPins(turn-5);
                        turn +=4;
                        resetTurn();
                        if(turn>25){
                            for(int i= 0;i<4;i++){
                                gamePlayerButtons[i].setVisible(true);
                            }
                            win_or_lose.win(score,false);
                        }

                    }
                }
            }
        });

    }
    void deleteTurnColors(){
        for(int i = turn-1;i<turn+3;i++){
            addRedo(gamePlayerButtons[i].getBackground());
            gamePlayerButtons[i].setBackground(Color.LIGHT_GRAY);
        }
    }
    private void redoColorChange() {
        // Check if there are states to redo
    }
    private void undoColorChange() {
        if(gamePlayerButtons[turn-1].getBackground().equals(Color.LIGHT_GRAY)){
            JOptionPane.showMessageDialog(gamePlayerPanel, "No more actions to undo", "Undo", JOptionPane.PLAIN_MESSAGE);
        }
        else{ // Ensure we can undo only from button 5 onwards
            for(int i = turn + 2;i>turn - 2;i--){
                if(!compareTwoColor(gamePlayerButtons[i].getBackground(),Color.LIGHT_GRAY)){
                    addRedo(gamePlayerButtons[i].getBackground());
                    gamePlayerButtons[i].setBackground(Color.LIGHT_GRAY);
                    break;
                }

            }
        }
    }
    private void deleteAllColors() {
        for (int i = 4; i < gamePlayerButtons.length; i++) {
            gamePlayerButtons[i].setBackground(Color.LIGHT_GRAY);
            resetredo();
        }
        //currentIndex = 4; // Reset current index to the start point
    }
    private Color[] getCurrentColors() {
        Color[] currentColors = new Color[gamePlayerButtons.length];
        for (int i = 0; i < gamePlayerButtons.length; i++) {
            currentColors[i] = gamePlayerButtons[i].getBackground();
        }
        return currentColors;
    }
    public void setButtonColor(int index, Color color) {
        gamePlayerButtons[index].setBackground(color);
    }
    private void changeButtonColor(Color color) {
//        if (currentIndex < gamePlayerButtons.length) {
//            if (twoPlayerBtn.isSelected()) {
//                currentIndex -= 4;
//            }
//            previousColors[currentIndex] = gamePlayerButtons[currentIndex].getBackground(); // Store previous color
//            gamePlayerButtons[currentIndex].setBackground(color);
//            currentIndex++;
//            undoIndex = currentIndex - 1; // Update the undo index
//        } else {
//            JOptionPane.showMessageDialog(gamePlayerPanel, "End Of Attempts", "Times", JOptionPane.PLAIN_MESSAGE);
//        }

        //gamePlayerButtons[3].setBackground(Color.LIGHT_GRAY);

        if((!gamePlayerButtons[turn+2].getBackground().equals(Color.LIGHT_GRAY))   && (!gamePlayerButtons[turn+2].getBackground().equals( Color.WHITE)) ){
            JOptionPane.showMessageDialog(gamePlayerPanel, "End Of Attempts", "Times", JOptionPane.PLAIN_MESSAGE);
        }else{
            for(int i = turn-1;i<turn+3;i++){
                if(gamePlayerButtons[i].getBackground() == Color.LIGHT_GRAY  || gamePlayerButtons[i].getBackground() == Color.WHITE){
                    gamePlayerButtons[i].setBackground(color);
                    break;
                }
            }
        }
    }
    void addScore(){
        score++;
        scoreLabel.setText(String.valueOf(score));
    }
    // for Compare guess with currunt turn
    boolean Compare(int startCopmare){
        for(int i = 0;i<4;i++){
            if(gamePlayerButtons[i].getBackground().equals(gamePlayerButtons[i+startCopmare].getBackground())){
                hidden_guess[i] = false;
                hidden_turn[i] = false;
                res[0]++;
            }
        }
        System.out.println(res[0]);
        for(int i = startCopmare;i<4 + startCopmare;i++){
//            System.out.println(Arrays.toString(hidden_guess) +Arrays.toString(hidden_turn)  );
            if(hidden_turn[i - startCopmare]){
                for(int j = 0;j<4;j++){
                    if(hidden_guess[j]){
                        if(compareTwoColor(gamePlayerButtons[j].getBackground(),gamePlayerButtons[i].getBackground())){
                            res[1]++;
                            hidden_guess[j] = false;
                            hidden_turn[i-startCopmare] = false;
                            break;
                        }
                    }
                }
            }
        }
        if(res[0] == 4){
            return true;
        }
        else{
            return false;
        }
    }
    void resetTurn(){
        res[0] = 0;
        res[1] = 0;
        for (int i = 0; i < 4; i++) {
            hidden_guess[i] = true;
            hidden_turn[i] = true;
        }
        resetredo();
    }
    boolean check(){
        if(gamePlayerButtons[turn + 2].getBackground() == Color.LIGHT_GRAY){
            JOptionPane.showMessageDialog(gamePlayerPanel, "Enter valid colors", "Times", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        return true;
    }
    void reset(){
        for(int i = 4;i<28;i++){
            gamePlayerButtons[i].setBackground(Color.LIGHT_GRAY);
        }
        for (int i = 0; i < 4; i++) {
            hidden_guess[i] = true;
            hidden_turn[i] = true;
        }
        resetredo();
        res[0] =0;
        res[1] = 0;
        for(int i = 0 ;i<24;i++) {
            gamePLayerPins[i].setBackground(Color.white);
        }
        for(int i= 0;i<4;i++){
            gamePlayerButtons[i].setVisible(false);
        }
        if(gamestatue){
            turn = 1;
            for (int i = 0; i < 4; i++) {
                gamePlayerButtons[i].setBackground(Color.WHITE);
            }
            deleteAllColors();
        }else{
            turn = 5;
            for(int i= 0;i<4;i++){
                gamePlayerButtons[i].setBackground(getRandomColor());
            }
        }
    }
    void setPins(int starts){
        for(int i = 0;i<4;i++){
            if(res[0]>0){
                res[0]--;
                gamePLayerPins[i+starts].setBackground(Color.black);
            }
            else if(res[1]>0){
                res[1]--;
                gamePLayerPins[i+starts].setBackground(Color.gray);
            }else{

            }
        }

    }
    void Win_or_lose(boolean w){
        addScore();
        //g2.Winner obj = new g2.Winner();
    }
    void openMainMenu(){
        cardLayout.show(mainPanel,"Main Menu");
    }
    // Method to get a random color
    private Color getRandomColor() {
        Color[] randomColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        int randomIndex = (int) (Math.random() * randomColors.length);
        return randomColors[randomIndex];
    }
    boolean compareTwoColor(Color one,Color two){
        if(one.equals(two)){
            return true;
        }
        return false;
    }
    // Game description inner class
    private class Describton {
        // Labels for game description
        JLabel logo = new JLabel("Welcome to the MasterMind Game!");
        JLabel description1 = new JLabel("If you guess the right color and it’s in the correct place");
        JLabel description2 = new JLabel("you will get a green ball");
        JLabel description3 = new JLabel("If the color is correct but not in the correct position");
        JLabel description4 = new JLabel("you will get a black ball");
        JLabel description5 = new JLabel("If the color is incorrect");
        JLabel description6 = new JLabel("you will get nothing. :");
        JLabel description7 = new JLabel("Enjoy the game, bro! ;)");
        JLabel team_member1 = new JLabel("Team Member:");
        JLabel team_member2 = new JLabel("-Moataz Essam");
        JLabel team_member3 = new JLabel("-AbdulRahman Mahmoud");
        JLabel team_member4 = new JLabel("-Asmaa Osama");
        JLabel team_member5 = new JLabel("-Sama Alaa");
        JLabel team_member6 = new JLabel("-Haidy Mohamed");
        // Constructor for Describton class
        Describton() {
            // Set up fonts and bounds for description labels
            logo.setFont(new Font("0", Font.BOLD, 30));
            logo.setBounds(165, 15, 1000, 100);
            logo.setForeground(Color.red);

            // Set up description texts
            setupDescriptionLabel(description1, 65, 80);
            setupDescriptionLabel(description2, 65, 110);
            setupDescriptionLabel(description3, 65, 140);
            setupDescriptionLabel(description4, 65, 170);
            setupDescriptionLabel(description5, 65, 200);
            setupDescriptionLabel(description6, 65, 230);
            setupDescriptionLabel(description7, 65, 260);
            team_member6.setVisible(false);
            // Set up team member labels
            setupTeamMemberLabel(team_member1, 70, 325);
            setupTeamMemberLabel(team_member2, 150, 360);
            setupTeamMemberLabel(team_member3, 150, 395);
            setupTeamMemberLabel(team_member4, 150, 430);
            setupTeamMemberLabel(team_member5, 150, 465);
            setupTeamMemberLabel(team_member6, 150, 500);

            // Add all labels to the game description panel
            gameDescription.add(logo);
            gameDescription.add(description1);
            gameDescription.add(description2);
            gameDescription.add(description3);
            gameDescription.add(description4);
            gameDescription.add(description5);
            gameDescription.add(description6);
            gameDescription.add(description7);
            gameDescription.add(team_member1);
            gameDescription.add(team_member2);
            gameDescription.add(team_member3);
            gameDescription.add(team_member4);
            gameDescription.add(team_member5);
            gameDescription.add(team_member6);
        }

        // Helper method to set up description labels
        private void setupDescriptionLabel(JLabel label, int x, int y) {
            label.setFont(new Font(Font.DIALOG, Font.ITALIC + Font.BOLD, 17));
            label.setBounds(x, y, 1000, 100);
            label.setForeground(Color.GREEN);
        }
        // Helper method to set up team member labels
        private void setupTeamMemberLabel(JLabel label, int x, int y) {
            label.setFont(new Font(Font.DIALOG, Font.ITALIC + Font.BOLD, 17));
            label.setBounds(x, y, 1000, 100);
            label.setForeground(Color.BLUE);
        }
    }
}