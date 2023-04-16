package tictactoe;
import java.util.Scanner;

public class Main {
    static char[][] field =
            {{' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}};

    static char currentMove = 'X';
    static boolean hasEmptyCells;
    static boolean xWon;
    static boolean oWon;
    static String gameState;
    static int xs = 0;
    static int os = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        hasEmptyCells = false;
        xWon = false;
        oWon = false;

        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            // Printing
            System.out.print("| ");
            System.out.print(field[i][0] + " "
                    + field[i][1] + " "
                    + field[i][2] + " ");
            System.out.print("|\n");

            // Count Xs and Os
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == 'X') {
                    xs += 1;
                } else if (field[i][j] == 'O') {
                    os += 1;
                } else if (field[i][j] == ' ' && !hasEmptyCells) {
                    hasEmptyCells = true;
                }
            }
        }
        System.out.println("---------");

        makeMove();
    }

    public static void makeMove() {
        String rowRaw = scanner.next();
        String columnRaw = scanner.next();

        if (!rowRaw.matches("\\d+") || !columnRaw.matches("\\d+")) {
            System.out.println("You should enter numbers!");
            makeMove();
            return;
        }
        int row = Integer.parseInt(rowRaw);
        int column = Integer.parseInt(columnRaw);


        if (row < 1 || row > 3 || column < 1 || column > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            makeMove();
            return;
        } else if (field[row - 1][column - 1] != ' ') {
            System.out.println("This cell is occupied! Choose another one!");
            makeMove();
            return;
        }
        field[row - 1][column - 1] = currentMove;
        if (currentMove == 'X') {
            currentMove = 'O';
        } else {
            currentMove = 'X';
        }

        showField();
    }

    public static void showField() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            System.out.print(field[i][0] + " "
                    + field[i][1] + " "
                    + field[i][2] + " ");
            System.out.print("|\n");
        }
        System.out.println("---------");

        String currentState = checkGameState();
        if (currentState.equals("Continues")) {
            makeMove();
        } else {
            System.out.println(currentState);
        }
    }

    public static String checkGameState() {
        int rowSum;
        int columnSum;
        for (int i = 0; i < 3; i++) {
            rowSum = (int) field[i][0] + (int) field[i][1] + (int) field[i][2];
            columnSum = (int) field[0][i] + (int) field[1][i] + (int) field[2][i];
            if (rowSum == 264 || columnSum == 264) {
                xWon = true;
            }
            if (rowSum == 237 || columnSum == 237) {
                oWon = true;
            }
        }
        // Diagonals
        if ((int) field[0][0] + (int) field[1][1] + (int) field[2][2] == 264 ||
                (int) field[0][2] + (int) field[1][1] + (int) field[2][0] == 264) {
            xWon = true;
        } else if ((int) field[0][0] + (int) field[1][1] + (int) field[2][2] == 237 ||
                (int) field[0][2] + (int) field[1][1] + (int) field[2][0] == 237) {
            oWon = true;
        }

        // Determine current game state
        if (!xWon && !oWon && !hasEmptyCells) {
            gameState = "Draw";
        } else if (xWon) {
            gameState = "X wins";
        } else if (oWon){
            gameState = "O wins";
        } else {
            gameState = "Continues";
        }
        return gameState;
    }
}