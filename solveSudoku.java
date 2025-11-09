import java.util.*;

public class solveSudoku {

    /**
     * Checks if it's safe to place 'val' at board[row][col].
     */
    public boolean isSafe(char[][] board, int row, int col, int val) {
        // 1. Check the row
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == (char) (val + '0')) {
                return false;
            }
        }

        // 2. Check the column
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == (char) (val + '0')) {
                return false;
            }
        }

        // 3. Check the 3x3 subgrid
        int sr = (row / 3) * 3; // Starting row of the grid
        int sc = (col / 3) * 3; // Starting col of the grid

        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (board[i][j] == (char) (val + '0')) {
                    return false;
                }
            }
        }
        
        // All checks passed
        return true;
    }

    /**
     * The main recursive backtracking function to solve the puzzle.
     */
    public boolean helper(char[][] board, int row, int col) {
        // Base Case: If we've gone past the last row, the board is solved
        if (row == board.length) {
            return true;
        }

        // Calculate the next cell's coordinates
        int nrow = 0;
        int ncol = 0;
        if (col != board.length - 1) { // If not the last column
            nrow = row;
            ncol = col + 1;
        } else { // If it is the last column
            nrow = row + 1; // Move to the next row
            ncol = 0;         // Start at the first column
        }

        // --- THIS IS THE KEY LOGIC FIX ---
        // If the current cell is already filled, skip it and
        // return whatever the result of solving the rest of the board is.
        if (board[row][col] != '.') {
            return helper(board, nrow, ncol);
        }

        // If we are here, the cell is empty ('.')
        // Try placing numbers 1 through 9
        for (int i = 1; i <= 9; i++) { // <-- FIX 2: Loop 1 to 9
            if (isSafe(board, row, col, i)) {
                board[row][col] = (char) (i + '0'); // Place the number

                // Recurse to solve the rest of the board
                if (helper(board, nrow, ncol)) {
                    return true; // Found a solution!
                } else {
                    // Backtrack: The number we placed didn't work.
                    board[row][col] = '.';
                }
            }
        }

        // If no number (1-9) worked for this empty cell,
        // then this path is incorrect. Return false to backtrack.
        return false;
    }

    /**
     * Main method to initialize and solve the Sudoku.
     */
    public static void main(String[] args) {
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        solveSudoku solver = new solveSudoku();

        // --- FIX 3: ADDED PRINTING AND CHECK ---
        if (solver.helper(board, 0, 0)) {
            System.out.println("Sudoku Solved:");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution exists.");
        }
    }
}