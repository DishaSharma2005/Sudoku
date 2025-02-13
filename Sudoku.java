import java.util.Scanner;

public class Sudoku {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] sudokuGrid = new int[9][9];

        getUserInput(sudokuGrid, scanner);
        displayGrid(sudokuGrid);

        if (sudokuSolver(sudokuGrid, 0, 0)) {
            System.out.println("\nSolved Sudoku:");
            displayGrid(sudokuGrid);
        } else {
            System.out.println("No solution exists.");
        }

        scanner.close();
    }

    public static void getUserInput(int[][] grid, Scanner scanner) {
        System.out.println("Enter the Sudoku grid numbers (use 0 for empty cells):");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num;
                while (true) {
                    System.out.print("Enter value for cell (" + (i + 1) + ", " + (j + 1) + "): ");
                    num = scanner.nextInt();
                    if (num >= 0 && num <= 9) {
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter a number between 0 and 9.");
                    }
                }
                grid[i][j] = num;
            }
        }
    }

    public static void displayGrid(int[][] grid) {
        System.out.println("\nSudoku Grid:");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("- - - - - - - - - - - - -");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print(" | ");
                }
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean sudokuSolver(int[][] grid, int row, int col) {
        // Base case: If we have reached the end, the grid is solved
        if (row == 9) {
            return true;
        }

        // Move to the next row if we are at the last column
        int nextRow = row, nextCol = col + 1;
        if (col + 1 == 9) {
            nextRow = row + 1;
            nextCol = 0;
        }

        // If the current cell is not empty, move to the next cell
        if (grid[row][col] != 0) {
            return sudokuSolver(grid, nextRow, nextCol);
        }

        // Try placing numbers 1-9
        for (int digit = 1; digit <= 9; digit++) {
            if (isSafe(grid, row, col, digit)) {
                grid[row][col] = digit;
                if (sudokuSolver(grid, nextRow, nextCol)) {
                    return true;
                }
                grid[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    public static boolean isSafe(int[][] grid, int row, int col, int digit) {
        // Check the column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == digit) {
                return false;
            }
        }

        // Check the row
        for (int j = 0; j < 9; j++) {
            if (grid[row][j] == digit) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int sr = (row / 3) * 3;
        int sc = (col / 3) * 3;
        for (int i = sr; i < sr + 3; i++) {
            for (int j = sc; j < sc + 3; j++) {
                if (grid[i][j] == digit) {
                    return false;
                }
            }
        }
        return true;
    }
}
