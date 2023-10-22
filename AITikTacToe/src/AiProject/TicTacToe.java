package AiProject;

import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        char[][] board = {
            { ' ', ' ', ' ' },
            { ' ', ' ', ' ' },
            { ' ', ' ', ' ' }
        };
        int currentPlayer = 1; // Player 1 is human, Player 2 is AI

        while (true) {
            printBoard(board);

            if (currentPlayer == 1) {
                playerMove(board);
            } else {
                aiMove(board);
            }

            if (checkWin(board, 'X')) {
                printBoard(board);
                System.out.println("Player 1 (X) wins!");
                break;
            } else if (checkWin(board, 'O')) {
                printBoard(board);
                System.out.println("Player 2 (O) wins!");
                break;
            } else if (isBoardFull(board)) {
                printBoard(board);
                System.out.println("It's a draw!");
                break;
            }

            currentPlayer = 3 - currentPlayer; // Switch players (1 -> 2 and 2 -> 1)
        }
    }

    public static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static void playerMove(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.print("Enter row (0, 1, 2) and column (0, 1, 2) for your move (e.g., 0 0): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
        } while (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ');

        board[row][col] = 'X';
    }

    public static void aiMove(char[][] board) {
        int[] bestMove = minimax(board, 'O');
        board[bestMove[0]][bestMove[1]] = 'O';
    }

    public static boolean checkWin(char[][] board, char player) {
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

    public static boolean isBoardFull(char[][] board) {
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
        if (checkWin(board, 'O')) return 1; // AI wins
        if (checkWin(board, 'X')) return -1; // Player wins
        if (isBoardFull(board)) return 0; // It's a draw

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
}
