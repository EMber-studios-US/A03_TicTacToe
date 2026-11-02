import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean playAgain = false;

        do {
            clearBoard();
            String currentPlayer = "X";
            int moveCount = 0;
            boolean gameOver = false;

            while (!gameOver) {
                display();
                System.out.println("Current player: " + currentPlayer);

                int rowMove = -1;
                int colMove = -1;
                boolean validMove = false;

                while (!validMove) {
                    int userRow = SafeInput.getRangedInt(in, "Enter row coordinate", 1, ROWS);
                    int userCol = SafeInput.getRangedInt(in, "Enter column coordinate", 1, COLS);

                    rowMove = userRow - 1;
                    colMove = userCol - 1;

                    if (isValidMove(rowMove, colMove)) {
                        validMove = true;
                    } else {
                        System.out.println("Invalid move! Try again.");
                    }
                }

                board[rowMove][colMove] = currentPlayer;
                moveCount = moveCount + 1;

                if (moveCount >= 5 && isWin(currentPlayer)) {
                    display();
                    System.out.println("Player " + currentPlayer + " wins");
                    gameOver = true;
                } else if (isTie()) {
                    display();
                    System.out.println("The game is a tie");
                    gameOver = true;
                } else {
                    if (currentPlayer.equals("X")) {
                        currentPlayer = "O";
                    } else {
                        currentPlayer = "X";
                    }
                }
            }

            playAgain = SafeInput.getYNConfirm(in, "Do you want to play again?");
        } while (playAgain);

        System.out.println("Thanks for playing");
    }

    private static void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = " ";
            }
        }
    }

    private static void display() {
        System.out.println();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(" " + board[r][c] + " ");
                if (c < COLS - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (r < ROWS - 1) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    private static boolean isValidMove(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            if (board[row][col].equals(" ")) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWin(String player) {
        if (isRowWin(player) || isColWin(player) || isDiagWin(player)) {
            return true;
        }
        return false;
    }

    private static boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][0].equals(player) && board[r][1].equals(player) && board[r][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c].equals(player) && board[1][c].equals(player) && board[2][c].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagWin(String player) {
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    private static boolean isTie() {
        boolean isFull = true;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c].equals(" ")) {
                    isFull = false;
                }
            }
        }
        if (isFull) {
            return true;
        }

        int blockedVectors = 0;

        for (int r = 0; r < ROWS; r++) {
            boolean hasX = board[r][0].equals("X") || board[r][1].equals("X") || board[r][2].equals("X");
            boolean hasO = board[r][0].equals("O") || board[r][1].equals("O") || board[r][2].equals("O");
            if (hasX && hasO) {
                blockedVectors = blockedVectors + 1;
            }
        }

        for (int c = 0; c < COLS; c++) {
            boolean hasX = board[0][c].equals("X") || board[1][c].equals("X") || board[2][c].equals("X");
            boolean hasO = board[0][c].equals("O") || board[1][c].equals("O") || board[2][c].equals("O");
            if (hasX && hasO) {
                blockedVectors = blockedVectors + 1;
            }
        }

        boolean diag1HasX = board[0][0].equals("X") || board[1][1].equals("X") || board[2][2].equals("X");
        boolean diag1HasO = board[0][0].equals("O") || board[1][1].equals("O") || board[2][2].equals("O");
        if (diag1HasX && diag1HasO) {
            blockedVectors = blockedVectors + 1;
        }

        boolean diag2HasX = board[0][2].equals("X") || board[1][1].equals("X") || board[2][0].equals("X");
        boolean diag2HasO = board[0][2].equals("O") || board[1][1].equals("O") || board[2][0].equals("O");
        if (diag2HasX && diag2HasO) {
            blockedVectors = blockedVectors + 1;
        }

        if (blockedVectors == 8) {
            return true;
        }

        return false;
    }
}