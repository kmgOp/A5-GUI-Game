
import java.awt.*;
import javax.swing.*;

// This class represents our whole Tic-Tac-Toe game window
public class TicTacToe extends JFrame {

    // These 9 buttons represent the 9 squares of our board
    private JButton[] buttons = new JButton[9];

    // Shows whose turn it is, or who won
    private JLabel statusLabel;

    // X starts the game
    private String currentPlayer = "X";

    // Once the game ends, no more moves are allowed
    private boolean gameOver = false;

    // Colours used to make the game look better
    private final Color background = new Color(20, 27, 45);
    private final Color cellColor = new Color(35, 45, 68);
    private final Color blue = new Color(65, 170, 255);
    private final Color pink = new Color(255, 90, 125);
    private final Color white = new Color(235, 240, 255);

    // Constructor: sets up the game window and its components
    public TicTacToe() {

        setTitle("Tic-Tac-Toe");
        setSize(430, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Opens the window in the centre
        setResizable(false);
        getContentPane().setBackground(background);
        setLayout(new BorderLayout(10, 10));

        // Heading at the top of the window
        JLabel title = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(blue);
        title.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        add(title, BorderLayout.NORTH);

        // Panel that holds the status and the 3x3 board
        JPanel centerPanel = new JPanel(new BorderLayout(8, 12));
        centerPanel.setBackground(background);
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 12, 5, 12));

        // Displays whose turn it is
        statusLabel = new JLabel(
                "Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 19));
        statusLabel.setForeground(white);
        centerPanel.add(statusLabel, BorderLayout.NORTH);

        // GridLayout arranges the 9 buttons in 3 rows and 3 columns
        JPanel board = new JPanel(new GridLayout(3, 3, 7, 7));
        board.setBackground(background);

        // Create each square and make it respond to a click
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 48));
            buttons[i].setForeground(white);
            buttons[i].setBackground(cellColor);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(
                    new Color(65, 80, 115), 2));
            buttons[i].setOpaque(true);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Save this square's position for its click event
            final int index = i;
            buttons[i].addActionListener(e -> makeMove(index));

            board.add(buttons[i]);
        }

        centerPanel.add(board, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Button to start a fresh game
        JButton newGameButton = new JButton("New Game");
        styleButton(newGameButton, blue);
        newGameButton.addActionListener(e -> resetGame());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(background);
        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(0, 10, 15, 10));
        bottomPanel.add(newGameButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Applies the same style to a button
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(
                BorderFactory.createEmptyBorder(10, 24, 10, 24));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Runs whenever a player clicks a square
    private void makeMove(int index) {

        // Ignore clicks if the game ended or the square is occupied
        if (gameOver || !buttons[index].getText().isEmpty()) {
            return;
        }

        // Put the current player's symbol in the chosen square
        buttons[index].setText(currentPlayer);

        // Give X and O different colours
        buttons[index].setForeground(
                currentPlayer.equals("X") ? blue : pink);

        // Check whether the move wins the game
        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            statusLabel.setForeground(
                    currentPlayer.equals("X") ? blue : pink);
            gameOver = true;

        // If nobody won and every square is filled, it's a draw
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
            statusLabel.setForeground(white);
            gameOver = true;

        } else {
            // Switch between X and O for the next turn
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";

            statusLabel.setText("Player " + currentPlayer + "'s Turn");
            statusLabel.setForeground(
                    currentPlayer.equals("X") ? blue : pink);
        }
    }

    // Checks all possible winning combinations
    private boolean checkWinner() {

        // Each group contains 3 squares that can form a winning line
        int[][] wins = {
            {0, 1, 2}, // Top row
            {3, 4, 5}, // Middle row
            {6, 7, 8}, // Bottom row
            {0, 3, 6}, // Left column
            {1, 4, 7}, // Middle column
            {2, 5, 8}, // Right column
            {0, 4, 8}, // Diagonal: top-left to bottom-right
            {2, 4, 6}  // Diagonal: top-right to bottom-left
        };

        for (int[] line : wins) {
            String first = buttons[line[0]].getText();

            // All 3 squares must contain the same non-empty symbol
            if (!first.isEmpty()
                    && first.equals(buttons[line[1]].getText())
                    && first.equals(buttons[line[2]].getText())) {
                return true;
            }
        }

        return false; // No winning line was found
    }

    // Checks whether every square is filled
    private boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false; // An empty square remains
            }
        }
        return true;
    }

    // Clears the board and starts again with X
    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(cellColor);
        }

        currentPlayer = "X";
        gameOver = false;
        statusLabel.setText("Player X's Turn");
        statusLabel.setForeground(blue);
    }

    // Program starts here
    public static void main(String[] args) {

        // Start the Swing window safely on Java's GUI event thread
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
