# HangmanGame
# Project Title: Hangman

# Description :
* The program is a simple hangman game. Firstly, it has a starting menu where the user has the option to choose whether to begin or to quit the application.
* Secondly, after the user has started the application, they have several options to play the game: to enter the word letter by letter or to submit the whole word.
* There is a lives counter, the unknown letters are represented by dashes, there is a pool to show the user the letters that have already been input.
* Furthermore, there is a visual representation of the hangman on the left side of the program.
* Finally, the user has the option whether to reset the game or to quit and return to the start menu.

# Technologies used:
* Java Swing to create the interface
* Java IO to read the files
* Java AWT for the color and graphics
* Java Util to create the array list of words
* Java Random to generate random letters
* Java ImageIcon to use Icons
* 

# Challenges (In order of difficulty) :
* Creating the frame, panels and the interface as a whole
* How to make the hangman image update after a wrong guess
* How position the buttons on both panels
* Changing the file paths, so that another user can open the program on their computer

# Features to add in the future
* Improvements to overall look of game panel
* High score system
* Hints 
* Difficulty levels

# How to install and run the project
* After you have downloaded the file folder , make sure the images and words text file are in the Resources folder, which is located in the src of the file folder
* Open the project in IntelliJ IDEA or another IDE
* Run the program
* If everything done correctly, the game should start

# How to use the project
* After the game has started running, a start menu will appear
* Press start if you want to play and quit if you do not want to quit the game
* After you have pressed "Start Game", a masked version of the word will appear
* Enter a letter in the input field and press "Enter" on your keyboard
* If you want to submit a whole word, write it in the input field and press the submit word button
* The game will continue running until the user is out of lives
* Keep in mind that if the user has guessed a whole word incorrectly, the game will end
* After the game ends a new masked word will appear 
* The player has the option to quit the game menu or to restart the game word during every stage of the game

# Credits:
* This game was developed by Slavi Sotirov 
* COS2030B/Individual project/Spring 2023 Semester/AUBG
* Inspiration:
* [Pictures] (https://replit.com/@cymophic/Hangman-Game-with-Clean-GUI)
* [Hangman Game] (https://www.youtube.com/watch?v=_FEdpNUD9M4)
* [Hangman Game] (https://www.youtube.com/watch?v=vN6YXUbcw54)
* [Tutorials for Frame, Panels, Labels and Buttons] (https://www.youtube.com/watch?v=EON2hLTAl9g)
* [JPanels] (https://www.youtube.com/watch?v=8pEXo1oVWvU)
* [JLabel Tutorial] (https://www.youtube.com/watch?v=PLvIyoWPfmM)

# Implementation
* The game is implemented using Swing library
* It is designed with a MVC pattern in mind 
* Model-View-Controller 
* Model: masked word, lives , guessed letters
* View: interface
* Controller: input and model updates
* The program uses File and BufferReader to read the file 
* The program uses StringBuilder to keep track of the guessed letters 
* ActionListener is used to handle input
* JLabel, JTExtField, JButton and ImageIcon are used to create GUI

# Design
* Classes:
* Hangman - main class
* HangmanFrame - frame
* StartPanel.GamePanel - UI.GameLogic
* CardLayout is used to switch from start panel to game panel
* Game data updated through methods
* The program displays error messages after victory or loss
* HangmanFrame extends JFrame/ Creates a start menu and game panel
* Program allows user to restart and return to start menu during game


