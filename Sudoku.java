package svv;

import java.awt.*;        // Uses AWT's Layout Managers
import java.awt.event.*;  // Uses AWT's Event Handlers
import javax.swing.*;     // Uses Swing's Container/Components

/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
public class Sudoku extends JFrame {
    // Name-constants for the game properties
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

    // Name-constants for UI control (sizes, colors and fonts)
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
    public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
    // Board width/height in pixels
    public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
    public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
    public static final Color OPEN_CELL_TEXT_NO = Color.RED;
    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    // The game board composes of 9x9 JTextFields,
    // each containing String "1" to "9", or empty String
    public JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

    // Puzzle to be solved and the mask (which can be used to control the
    //  difficulty level).
    // Hardcoded here. Extra credit for automatic puzzle generation
    //  with various difficulty levels.
    public int[][] puzzle =
            {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                    {3, 4, 5, 2, 8, 6, 1, 7, 9}};
    // For testing, open only 2 cells.
    public boolean[][] masks =
            {{true, true, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false}};

    /**
     * Constructor to setup the game and the UI Components
     */
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // 9x9 GridLayout

        // Allocate a common listener as the ActionEvent listener for all the
        //  JTextFields
        // ... [TODO 3] (Later) ....
        InputListener listener = new InputListener();

        // Construct 9x9 JTextFields and add to the content-pane
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                cp.add(tfCells[row][col]);            // ContentPane adds JTextField
                if (masks[row][col]) {
                    tfCells[row][col].setText("");     // set to empty string
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

                    // Add ActionEvent listener to process the input
                    // ... [TODO 4] (Later) ...
                    tfCells[row][col].addActionListener(listener);
                } else {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
                }
                // Beautify all the cells
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }

        // Set the size of the content-pane and pack all the components
        //  under this container.
        cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku");
        setVisible(true);
    }

    public void startNewGame() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (masks[row][col]) {
                    tfCells[row][col].setText("");     // set to empty string
                } else {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                }
            }
        }
    }

    public void makeMove(int row, int col, int number) {
        tfCells[row][col].setText(String.valueOf(number));
    }

    public int getWinner() {
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(tfCells[i][j].getText().equals("")) {
                    return 0;
                }
            }
        }
        if(isValid()) {
            return 1;
        }else {
            return 2;
        }
    }

    /**
     * The entry main() entry method
     */
    public static void main(String[] args) {
        //System.out.print(Integer.parseInt("sdf"));
        // [TODO 1] (Now)
        // Check Swing program template on how to run the constructor
        Sudoku sudoku = new Sudoku();
        boolean[][] masks = sudoku.masks;
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (masks[row][col]) {
                    sudoku.tfCells[row][col].setText("oh");     // set to empty string
                    sudoku.tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

                    // Add ActionEvent listener to process the input
                    // ... [TODO 4] (Later) ...
                }
                // Beautify all the cells
                sudoku.tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                sudoku.tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }
    }

    // Define the Listener Inner Class
    // ... [TODO 2] (Later) ...
    private class InputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // All the 9*9 JTextFileds invoke this handler. We need to determine
            // which JTextField (which row and column) is the source for this invocation.
            int rowSelected = -1;
            int colSelected = -1;

            // Get the source object that fired the event
            JTextField source = (JTextField) e.getSource();
            // Scan JTextFileds for all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < GRID_SIZE && !found; ++row) {
                for (int col = 0; col < GRID_SIZE && !found; ++col) {
                    if (tfCells[row][col] == source) {
                        rowSelected = row;
                        colSelected = col;
                        found = true;  // break the inner/outer loops
                    }
                }
            }

         /*
          * [TODO 5]
          * 1. Get the input String via tfCells[rowSelected][colSelected].getText()
          * 2. Convert the String to int via Integer.parseInt().
          * 3. Assume that the solution is unique. Compare the input number with
          *    the number in the puzzle[rowSelected][colSelected].  If they are the same,
          *    set the background to green (Color.GREEN); otherwise, set to red (Color.RED).
          */

         /*
          * [TODO 6] Check if the player has solved the puzzle after this move.
          * You could update the masks[][] on correct guess, and check the masks[][] if
          * any input cell pending.
          */
        }
    }
}