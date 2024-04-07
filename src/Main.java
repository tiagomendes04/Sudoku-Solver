import java.util.ArrayList;

public class Main {
    public static int[][] grid = new int[9][9];

    public static SudokuFrame frame;

    public static void main(String[] args) {
        frame = new SudokuFrame();
    }

    public static void run() {
        if (inputValidation()) {
            if (solve(0, 0)) {
                System.out.println("Solution:\n");
                printGrid();
                frame.updateSudokuFrame();
                return;
            }
            System.out.println("No solution.");
            frame.showMessage("No solution.");
            return;
        }
        System.out.println("Try again with a valid grid.");
    }

    public static void printGrid() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print(grid[r][c] + " ");
                if (c == 2 || c == 5) System.out.print("| ");
            }
            System.out.println();
            if (r == 2 || r == 5) System.out.println("- - - + - - - + - - -");
        }
        System.out.println();
    }

    public static void updateGrid(int[][] newGrid) {
        for (int r = 0; r < 9; r++) {
            System.arraycopy(newGrid[r], 0, grid[r], 0, 9);
        }
    }

    public static boolean inputValidation() {
        ArrayList<Integer> memorizedValues = new ArrayList<>();
        int k;

        // Search for invalid/repeated values on rows
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                k = grid[r][c];

                // Invalid value
                if (k < 0 || k > 9) {
                    System.out.println("Invalid value [" + k + "] at (" + r + ", " + c + ").\n" +
                            "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                    frame.showMessage("Invalid value [" + k + "] at (" + r + ", " + c + ").\n" +
                            "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                    return false;
                }

                // Repeated value
                if (memorizedValues.contains(k)) {
                    System.out.println("Value repeated in row: [" + k + "] at (" + r + ", " + c + ").");
                    frame.showMessage("Value repeated in row: [" + k + "] at (" + r + ", " + c + ").");
                    return false;
                }

                if (k != 0) memorizedValues.add(k);
            }
            memorizedValues.clear();
        }

        // Search for invalid/repeated values on collumns
        for (int c = 0; c < 9; c++) {
            for (int r = 0; r < 9; r++) {
                k = grid[r][c];

                // Invalid value
                if (k < 0 || k > 9) {
                    System.out.println("Invalid value [" + k + "] at (" + r + ", " + c + ").\n" +
                            "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                    frame.showMessage("Invalid value [" + k + "] at (" + r + ", " + c + ").\n" +
                            "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                    return false;
                }

                // Repeated value
                if (memorizedValues.contains(k)) {
                    System.out.println("Value repeated in collumn: [" + k + "] at (" + r + ", " + c + ").");
                    frame.showMessage("Value repeated in collumn: [" + k + "] at (" + r + ", " + c + ").");
                    return false;
                }

                if (k != 0) memorizedValues.add(k);
            }
            memorizedValues.clear();
        }

        // Serch for invalid/repeated values on sub-grids
        // Traverse sub-grids
        for (int sr = 0; sr < 3; sr++) {
            for (int sc = 0; sc < 3; sc++) {
                // Traverse sub-grid cells
                for (int r = sr * 3; r < sr * 3 + 3; r++) {
                    for (int c = sc * 3; c < sc * 3 + 3; c++) {
                        k = grid[r][c];

                        // Invalid value
                        if (k < 0 || k > 9) {
                            System.out.println("Invalid value [" + k + "] at (" + r + ", " + c + ").\n" +
                                    "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                            frame.showMessage("Invalid value [" + k + "] at (" + r + ", " + c + ").\n" +
                                    "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                            return false;
                        }

                        // Repeated value
                        if (memorizedValues.contains(k)) {
                            System.out.println("Value repeated in sub-grid: [" + k + "] at (" + r + ", " + c + ").");
                            frame.showMessage("Value repeated in sub-grid: [" + k + "] at (" + r + ", " + c + ").");
                            return false;
                        }

                        if (k != 0) memorizedValues.add(k);
                    }
                }
                memorizedValues.clear();
            }
        }

        return true;
    }

    public static boolean isValid(int r, int c, int k) {
        // Check row and collumn
        for (int i = 0; i < 9; i++) {
            if (grid[r][i] == k) return false;
            if (grid[i][c] == k) return false;
        }

        // Check sub-grid
        for (int i = (r / 3) * 3; i < (r / 3) * 3 + 3; i++) {
            for (int j = (c / 3) * 3; j < (c / 3) * 3 + 3; j++) {
                if (grid[i][j] == k) return false;
            }
        }

        return true;
    }

    public static boolean solve(int r, int c) {
        // Fills from left to right, top to bottom

        // --- Traversal ---
        // After filling all rows, it's done
        if (r == 9) return true;

        // Moving to next row
        else if (c == 9) return solve(r + 1, 0);

        // Moving to next cell (on a row)
        else if (grid[r][c] != 0) return solve(r, c + 1);


        // Backtracking trial and error
        else {
            // Trying values from 1 to 9
            for (int k = 1; k < 10; k++) {
                // if value is valid, move on using it
                if (isValid(r, c, k)) {
                    grid[r][c] = k;
                    if (solve(r, c + 1)) return true;
                    // if it can't be solved with current value, try another
                    grid[r][c] = 0;
                }
            }
            // if no value is valid, try another value for previous cells
            // if there are no previous cells, returns false
            return false;
        }
    }
}