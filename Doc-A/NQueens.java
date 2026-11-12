/*
 * Assignment 4: N-Queens Problem (Backtracking, first queen placed at (0,0))
 *
 * HOW TO RUN ON UBUNTU:
 *   sudo apt update && sudo apt install default-jdk -y     (only once)
 *   javac NQueens.java
 *   java NQueens
 *
 * (The file name must stay NQueens.java)
 *
 * Time: O(n!) worst case   Space: O(n^2)
 */

import java.util.Scanner;

public class NQueens {
    static int[][] board;
    static int n;

    // Check whether a queen can be placed
    static boolean isSafe(int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1)
                return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    // Backtracking function
    static boolean solveNQueens(int row) {
        // All queens are placed
        if (row == n)
            return true;

        // Try each column
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                board[row][col] = 1;

                if (solveNQueens(row + 1))
                    return true;

                // Backtrack
                board[row][col] = 0;
            }
        }
        return false;
    }

    // Display the chessboard
    static void displayBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter value of N: ");
        n = sc.nextInt();

        // Fix: reject invalid N (board[0][0] would crash for N <= 0)
        if (n < 1) {
            System.out.println("N must be at least 1.");
            return;
        }

        board = new int[n][n];

        // Place the first Queen at row 0, column 0
        board[0][0] = 1;

        System.out.println("\nInitial Matrix:");
        displayBoard();

        // Start placing remaining queens from row 1
        if (solveNQueens(1)) {
            System.out.println("\nFinal N-Queens Matrix:");
            displayBoard();
        } else {
            System.out.println("\nNo solution exists with the first Queen at (1,1).");
        }
        sc.close();
    }
}

/*
 * ===================== SAMPLE INPUTS AND OUTPUTS =====================
 *
 * Run 1 (n = 5)
 *   Enter value of N: 5
 *
 *   Initial Matrix:
 *   1 0 0 0 0
 *   0 0 0 0 0
 *   0 0 0 0 0
 *   0 0 0 0 0
 *   0 0 0 0 0
 *
 *   Final N-Queens Matrix:
 *   1 0 0 0 0
 *   0 0 1 0 0
 *   0 0 0 0 1
 *   0 1 0 0 0
 *   0 0 0 1 0
 *
 * Run 2 (n = 8)   [the program prints the initial matrix too; not shown here]
 *   Enter value of N: 8
 *
 *   Final N-Queens Matrix:
 *   1 0 0 0 0 0 0 0
 *   0 0 0 0 1 0 0 0
 *   0 0 0 0 0 0 0 1
 *   0 0 0 0 0 1 0 0
 *   0 0 1 0 0 0 0 0
 *   0 0 0 0 0 0 1 0
 *   0 1 0 0 0 0 0 0
 *   0 0 0 1 0 0 0 0
 *
 * Run 3 (n = 4, no solution with the first queen at (1,1))
 *   Enter value of N: 4
 *
 *   Initial Matrix:
 *   1 0 0 0
 *   0 0 0 0
 *   0 0 0 0
 *   0 0 0 0
 *
 *   No solution exists with the first Queen at (1,1).
 *
 * Run 4 (n = 0, invalid input)
 *   Enter value of N: 0
 *   N must be at least 1.
 */
