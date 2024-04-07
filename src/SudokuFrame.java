import javax.swing.*;
import java.awt.*;

public class SudokuFrame extends JFrame {
    protected static JTextField[][] cells;

    public SudokuFrame() {
        setTitle("SUDOKU SOLVER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 550);
        setLayout(new BorderLayout());

        JPanel sudokuPanel = new JPanel(new GridLayout(9, 9));
        cells = new JTextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                sudokuPanel.add(cells[i][j]);
            }
        }

        // Add borders to 3x3 sub-grids
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if ((c + 1) % 3 == 0 && c != 8) cells[r][c].setBorder(
                        BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK));

                else if ((r + 1) % 3 == 0 && r != 8) cells[r][c].setBorder(
                        BorderFactory.createMatteBorder(1, 1, 5, 1, Color.BLACK));

                else cells[r][c].setBorder(
                            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            }
        }
        // Corners
        cells[2][2].setBorder(
                BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));
        cells[2][5].setBorder(
                BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));
        cells[5][2].setBorder(
                BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));
        cells[5][5].setBorder(
                BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));


        add(sudokuPanel, BorderLayout.CENTER);


        JPanel buttonPanel = buttonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buttonPanel() {
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> {
            int[][] newGrid = new int[9][9];
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    String value = cells[r][c].getText();

                    if (!value.matches("[0-9]+") && !value.matches("")) {
                        showMessage("Invalid characters at (" + r + ", " + c + ")\n" +
                                "Sudoku values should be from 0 - 9 (0 represents empty cell)");
                        return;
                    }

                    newGrid[r][c] = value.isEmpty() ? 0 : Integer.parseInt(value);
                }
            }
            Main.updateGrid(newGrid);
            Main.run();
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    cells[i][j].setText("");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);
        return buttonPanel;
    }

    protected void updateSudokuFrame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText(Integer.toString(Main.grid[i][j]));
            }
        }
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
