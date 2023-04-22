/**
 * File: Hangman.java
 * Author: Slavi Sotirov
 * Date: 04/23/2023
 */


import javax.swing.*; // GUI components
import java.awt.*; // Graphics, color and etc.
import java.awt.event.ActionEvent; // Interaction of user with GUI
import java.awt.event.ActionListener; // Handles action events
import java.io.BufferedReader; // Reads character input
import java.io.File; // File path
import java.io.FileReader; // Reads characters in files
import java.io.IOException; // Throws an exception when an operation fails
import java.util.ArrayList; // Creates array lists
import java.util.Random; // Generates random letters
import javax.swing.ImageIcon; // Images in GUI


//The main class that runs the game
public class Hangman {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Insures the creation and update of the GUI
            try { // Tries to create the GUI
                new HangmanFrame();
            } catch (IOException e) {
                e.printStackTrace(); // Prints error for debugging purposes
            }
        });
    }
}

class HangmanFrame extends JFrame {
    private JPanel startPanel; // Panel for start menu
    private StartPanel.GamePanel gamePanel; // Panel for game menu

    public HangmanFrame() throws IOException {

        //This code sets the properties of the frame
        setTitle("Hangman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout()); // Allows for only one panel to be visible at a time

        //This patch of the code creates the start menu panel and game panel
        startPanel = new StartPanel(this);
        startPanel.setPreferredSize(new Dimension(600, 600));
        gamePanel = new StartPanel.GamePanel(this);
        gamePanel.setPreferredSize(new Dimension(600, 600));

        //This code adds the panels to the frame
        add(startPanel, "StartPanel");
        add(gamePanel, "GamePanel");


        pack(); // Packs the frame - makes the contents of the frame the size that has been given to them
        setLocationRelativeTo(null); // Centres the frame on the screen
        setVisible(true); // Makes the frame visible
    }

    // This method switches to the game panel when the game starts
    public void startGame() {
        CardLayout layout = (CardLayout) getContentPane().getLayout(); // Shows the game panel
        layout.show(getContentPane(), "GamePanel");
        gamePanel.startNewGame();
    }

    // This method shows the start menu
    public void showStartMenu() {
        CardLayout layout = (CardLayout) getContentPane().getLayout(); // Shows the start menu
        layout.show(getContentPane(), "StartPanel");
    }
}

// Sets up the start menu
class StartPanel extends JPanel {
    public StartPanel(HangmanFrame frame) {

        // Sets the constraints for th panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel titleLabel = new JLabel("Hangman Game"); // Add title label at the top
        titleLabel.setFont(new Font("Monospaced", Font.ITALIC, 32)); // Sets the font and the size of the title
        gbc.gridx = 0; // Cell position 0.0 on the grid
        gbc.gridy = 0; // Cell position 0.0 on the grid
        gbc.weightx = 1; // Should fill entire space
        gbc.weighty = 1; // Should fill entire space
        gbc.anchor = GridBagConstraints.NORTH; // Hangman Game is placed at the top of the cell
        add(titleLabel, gbc);

        ImageIcon imageIcon = new ImageIcon("src/Resources/icon3.png"); // File with image icon
        JLabel imageLabel = new JLabel(imageIcon); // Pass image icon as a parameter
        gbc.gridy = 1; // Row 1
        gbc.anchor = GridBagConstraints.CENTER; // Center of cell
        add(imageLabel, gbc); // Adds the object to the layout of the panel



        JButton startButton = new JButton("Start Game"); // Creates the "Start Game" button
        startButton.setPreferredSize(new Dimension(200, 100)); // Dimensions for the start button
        startButton.addActionListener(e -> frame.startGame()); // The ActionListener makes it possible for the enter key input to work
        gbc.gridy = 2; // Row 2
        gbc.anchor = GridBagConstraints.CENTER; // Adds start button in the center
        add(startButton, gbc);


        JButton quitButton = new JButton("Quit Game"); // Creates the "Quit Game" button
        quitButton.setPreferredSize(new Dimension(200,100)); // Dimensions for the quit button
        quitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 3; // Row 3
        gbc.anchor = GridBagConstraints.SOUTH; // Adds button in the bottom of the page
        add(quitButton, gbc);
    }


    // Panel for the actual game
    static class GamePanel extends JPanel {
        private JLabel livesLabel;
        private JLabel wordLabel;
        private JButton restartButton;
        private JButton quitButton;

        private JLabel guessedLettersLabel;

        private JTextField guessTextField; // Field to guess the word
        private JLabel hangmanImage; // Image of the hangman

        private ArrayList<String> words; // Words in the file
        private String currentWord;
        private StringBuilder currentMaskedWord; // Not guessed word
        private int lives; // Lives of player
        private StringBuilder guessedLetters; // letters that have already been guessed

        private JLabel hangmanImageLabel;
        private String[] hangmanImages = { // String for the hangman image that updates after every wrong guess
                "src/Resources/1.png",
                "src/Resources/7.png",
                "src/Resources/2.png",
                "src/Resources/3.png",
                "src/Resources/4.png",
                "src/Resources/5.png",
                "src/Resources/6.png"
        };


        public GamePanel(HangmanFrame frame) throws IOException {

            // Creates a JLabel to display the actual hangman image
            setLayout(new BorderLayout());
            hangmanImageLabel = new JLabel();
            add(hangmanImageLabel, BorderLayout.WEST); // Adds the hangman image on the left side of the panel

            // Add a new JPanel for the hangman image and word label
            JPanel centerPanel = new JPanel();
            wordLabel = new JLabel();
            wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
            centerPanel.add(wordLabel);
            add(centerPanel, BorderLayout.CENTER); // Positions panel at the centre

            // Adds a JPanel for the lives label
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            livesLabel = new JLabel();
            topPanel.add(livesLabel);
            add(topPanel, BorderLayout.NORTH); // Positions panel at the top

            // Adds a JPanel for the word
            JPanel bottomPanel = new JPanel();
            wordLabel = new JLabel();
            wordLabel.setFont(new Font("Arial", Font.BOLD, 24));
            bottomPanel.add(wordLabel);
            add(bottomPanel, BorderLayout.CENTER); // Positions it at the center of the bottom

            JPanel inputPanel = new JPanel();
            guessTextField = new JTextField(10);
            guessTextField.setPreferredSize(new Dimension(200,30));
            inputPanel.add(guessTextField);
            guessedLettersLabel = new JLabel("Guessed words: "); // Initialize guessedWordsLabel
            inputPanel.add(guessedLettersLabel); // Add guessedWordsLabel to inputPanel

            // Add ActionListener for the Enter key
            guessTextField.addActionListener(new GuessTextFieldActionListener());

            JButton submitWordButton = new JButton("Submit Word"); // Creates a new button for the submission of a whole word
            submitWordButton.addActionListener(new SubmitWordButtonActionListener()); // Adds ActionListener
            inputPanel.add(submitWordButton); // Adds submit word to input panel
            add(inputPanel, BorderLayout.SOUTH); // Places input panel at the bottom of the page

            JPanel buttonPanel = new JPanel(); // New JPanel for buttons
            restartButton = new JButton("Restart"); // New button to restart the game
            restartButton.addActionListener(e -> startNewGame());
            buttonPanel.add(restartButton); // Adds to buttonPanel

            quitButton = new JButton("Quit"); // New button to quit the game
            quitButton.addActionListener(e -> frame.showStartMenu());
            buttonPanel.add(quitButton); // Adds to buttonPanel
            add(buttonPanel, BorderLayout.EAST); // Places them to the right of the frame

            loadWords(); // Loads words for the game
            guessedLetters = new StringBuilder(); // Initialize guessedWords here
        }



        // ActionListener for the guess field
        class GuessTextFieldActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gets user's input
                String input = guessTextField.getText();
                if (input.length() > 0) {
                    // Gets first character
                    char guess = input.charAt(0);
                    // Checks whether its correct
                    checkGuess(guess);
                    // Clears for next input
                    guessTextField.setText("");
                }
            }
        }

        // ActionListener for the submission of a whole word
        class SubmitWordButtonActionListener implements ActionListener {
            @Override
            // Does the same as above but for th whole word
            public void actionPerformed(ActionEvent e) {
                String inputWord = guessTextField.getText();
                if (inputWord.length() > 0) {
                    submitWord(inputWord);
                    guessTextField.setText("");
                }
            }
        }

        // Method for the submission of a whole word
        private void submitWord(String inputWord) {
            // Makes the input lowercase since all words in file are lowercase
            inputWord = inputWord.toLowerCase();

            // Statement that requires user to enter only letters
            if (!inputWord.matches("^[a-zA-Z]*$")) {
                JOptionPane.showMessageDialog(this, "Please enter only characters of the alphabet!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }



            // Boolean value for the guess of the whole word
            boolean incorrectWord = false;
            if (inputWord.equals(currentWord)) { //  Statement for correct input
                gameWon();
            } else { // Statement for incorrect input
                incorrectWord = true;
                lives--;
                gameOver();

            }


        }
        
        // Method for the start of a new game
        public void startNewGame() {
            Random random = new Random(); // Random object to select random word
            currentWord = words.get(random.nextInt(words.size()));
            currentMaskedWord = new StringBuilder(); // generates masked version of word
            for(int i = 0; i < currentWord.length(); i++){ // Loop to print dashes for every letter of the word
                currentMaskedWord.append("_ ");
            }
            lives = 6; // Amount of lives
            guessedLetters = new StringBuilder(); // Added this line to initialize guessedWords
            updateLabels(); // Updates all labels
            updateHangmanImage(); // Initializes method
        }

        // Method for the update of Hangman image after every wrong guess
        private void updateHangmanImage() {
            ImageIcon imageIcon = new ImageIcon(hangmanImages[6 - lives]);
            hangmanImageLabel.setIcon(imageIcon);
        }

        // Method for generating a word from the file
        private void loadWords() throws IOException {
            words = new ArrayList<>();
            File file = new File("src/Resources/Words.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Reads characters
                String line;
                while ((line = br.readLine()) != null) { // Reads each line from the file and adds each word to the list of arrays
                    words.add(line.trim());
                }
            }
        }

        // Method to update labels
        private void updateLabels() {
            livesLabel.setText("Lives: " + lives); // Updates lives
            wordLabel.setText(currentMaskedWord.toString()); // Updates wordLabel
            guessedLettersLabel.setText("Guessed letters: " + guessedLetters.toString()); // Added this line to update the guessedWordsLabel

        }


        private void checkGuess(char guess) { // Turns the upper case input into lower case so that if the
            guess = Character.toLowerCase(guess);

            if(!Character.isLetter(guess)) { // If statement that prompts the user to enter only characters of the alphabet
                JOptionPane.showMessageDialog(this, "Please enter only characters of the alphabet!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }



            // If the letter has already been guessed, return immediately
            if (guessedLetters.toString().contains(String.valueOf(guess))) {
                return;
            }

            // Updates masked word
            boolean correct = false;
            for (int i = 0; i < currentWord.length(); i++) { // Iterates through each character to check if it matches
                if (currentWord.charAt(i) == guess) {
                    currentMaskedWord.setCharAt(i*2, guess);
                    correct = true;
                }
            }

            if (!correct) { // If statement that updates the image after wrong guesses
                lives--;
                updateHangmanImage();
            }

            guessedLetters.append(guess).append(", "); // Add the guessed letter to guessedLetters

            updateLabels();

            if (lives == 0) {
                gameOver(); // Calls method after the game has been lost or there are no lives left
            } else if (currentMaskedWord.toString().replace(" ", "").equals(currentWord)) { // Statement that checks whether the input matches the word
                gameWon(); // Calls method after the game has been won to display the victory message
            }
        }


        // Method that displays a message after a loss and restarts the game
        private void gameOver() {
            JOptionPane.showMessageDialog(this, "Game Over! The word was: " + currentWord, "Game Over", JOptionPane.ERROR_MESSAGE); // Determines type of error message and displays the word
            startNewGame(); // Calls method to start the game over
        }

        // Method that displays a message after a victory
        private void gameWon() {
            JOptionPane.showMessageDialog(this, "Congratulations! You won! ", "Victory", JOptionPane.INFORMATION_MESSAGE);
            startNewGame();
        }
    }
}

