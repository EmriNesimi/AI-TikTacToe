package AiProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI {
    private static JButton[][] buttons = new JButton[3][3];
    private static char[][] board = {
        { ' ', ' ', ' ' },
        { ' ', ' ', ' ' },
        { ' ', ' ', ' ' }
    };
    private static int currentPlayer = 1; // Player 1 is human, Player 2 is AI

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));

        initializeButtons(frame);

        frame.setVisible(true);
    }

    private static void initializeButtons(JFrame frame) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                frame.add(buttons[i][j]);
            }
        }
    }

    static class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == ' ') {
                board[row][col] = 'X';
                buttons[row][col].setText("X");

                if (checkWin('X')) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You win!");
                    resetBoard();
                    return;
                }

                if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetBoard();
                    return;
                }

                aiMove();

                if (checkWin('O')) {
                    JOptionPane.showMessageDialog(null, "AI wins!");
                    resetBoard();
                    return;
                }

                if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetBoard();
                }
            }
        }
    }

    public static void aiMove() {
        int[] bestMove = minimax(board, 'O');
        board[bestMove[0]][bestMove[1]] = 'O';
        buttons[bestMove[0]][bestMove[1]].setText("O");
    }

    public static boolean checkWin(char player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] minimax(char[][] board, char player) {
        int[] bestMove = new int[] { -1, -1 };
        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, (player == 'O') ? 'X' : 'O');
                    board[i][j] = ' '; // Undo the move

                    if ((player == 'O' && score > bestScore) || (player == 'X' && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    public static int minimaxHelper(char[][] board, char player) {
        if (checkWin('O')) return 1; // AI wins
        if (checkWin('X')) return -1; // Player wins
        if (isBoardFull()) return 0; // It's a draw

        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, (player == 'O') ? 'X' : 'O');
                    board[i][j] = ' '; // Undo the move

                    if ((player == 'O' && score > bestScore) || (player == 'X' && score < bestScore)) {
                        bestScore = score;
                    }
                }
            }
        }

        return bestScore;
    }
    
    public static void resetBoard() {
        // Clear the board and button text
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
        }
    }
}


