package battleship;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static String player = "Player 1";
    static int[][] aircraftCarrier1 = new int[5][2];
    static int[][] battleShip1 = new int[4][2];
    static int[][] submarine1 = new int[3][2];
    static int[][] cruiser1 = new int[3][2];
    static int[][] destroyer1 = new int[2][2];
    static int[][][] ships1 = {aircraftCarrier1, battleShip1, submarine1, cruiser1, destroyer1};

    static boolean[] aircraftCarrierShot1 = {false, false, false, false, false};
    static boolean[] battleShipShot1 = {false, false, false, false};
    static boolean[] submarineShot1 = {false, false, false};
    static boolean[] cruiserShot1 = {false, false, false};
    static boolean[] destroyerShot1 = {false, false};
    static boolean[][] shipsShot1 = {aircraftCarrierShot1, battleShipShot1, submarineShot1, cruiserShot1, destroyerShot1};
    static int[][] aircraftCarrier2 = new int[5][2];
    static int[][] battleShip2 = new int[4][2];
    static int[][] submarine2 = new int[3][2];
    static int[][] cruiser2 = new int[3][2];
    static int[][] destroyer2 = new int[2][2];
    static int[][][] ships2 = {aircraftCarrier2, battleShip2, submarine2, cruiser2, destroyer2};

    static boolean[] aircraftCarrierShot2 = {false, false, false, false, false};
    static boolean[] battleShipShot2 = {false, false, false, false};
    static boolean[] submarineShot2 = {false, false, false};
    static boolean[] cruiserShot2 = {false, false, false};
    static boolean[] destroyerShot2 = {false, false};
    static boolean[][] shipsShot2 = {aircraftCarrierShot2, battleShipShot2, submarineShot2, cruiserShot2, destroyerShot2};
    static int shipCnt1 = 0;
    static int shipCnt2 = -1;
    static String curShip;
    static Scanner scanner = new Scanner(System.in);
    static GameField field1 = new GameField();
    static GameField fogField1 = new GameField();
    static GameField devField1 = new GameField();
    static GameField field2 = new GameField();
    static GameField fogField2 = new GameField();
    static GameField devField2 = new GameField();
    public enum Ships {
        AIRCRAFTCARRIER(5),
        BATTLESHIP(4),
        SUBMARINE(3),
        CRUISER(3),
        DESTROYER(2);

        private final int size;

        Ships(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }
    }

    public static void main(String[] args) {
        System.out.println("Player 1, place your ships on the game field");
        field1.print();
        placingStage();
    }

        /*
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.println(curLetter + " " + Arrays.toString(devField1[i]).replace(",", "").replace("[", "").replace("]", ""));
            curLetter++;
        }

         */

    public static void placingStage() {
        if (shipCnt1 == 5) {
            if (shipCnt2 == -1) {
                player = "Player 2";
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                System.out.println("Player 2, place your ships to the game field");
                shipCnt2++;
                field2.print();
                placingStage();
                return;
            } else if (shipCnt2 == 5) {
                player = "Player 1";
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
                shootingStage();
                return;
            }
        }
        if (player.equals("Player 1")) { enterPrint(shipCnt1); }
        else { enterPrint(shipCnt2); }
        String coordinate1 = scanner.next();
        String coordinate2 = scanner.next();
        scanner.nextLine();
        int[][] coordinates = new int[][] {convertCoordinate(coordinate1), convertCoordinate(coordinate2)};

        if (player.equals("Player 1")){
            if (isCoordinatesLegit(coordinates, shipCnt1, devField1)) {
                placeShip(coordinates, devField1, field1, shipCnt1, ships1);
                shipCnt1++;
            } else {
                placingStage();
                return;
            }
            placingStage();
        } else if (player.equals("Player 2")) {
            if (isCoordinatesLegit(coordinates, shipCnt2, devField2)) {
                placeShip(coordinates, devField2, field2, shipCnt2, ships2);
                shipCnt2++;
            } else {
                placingStage();
                return;
            }
        }
        placingStage();
    }

    public static boolean isCoordinatesLegit(int[][] coordinates, int shipCnt, GameField devField) {
        if (!(coordinates[0][0] >= 0 && coordinates[0][0] < 10
        && coordinates[0][1] >= 0 && coordinates[0][1] < 10
        && coordinates[1][0] >= 0 && coordinates[1][0] < 10
        && coordinates[1][1] >= 0 && coordinates[1][1] < 10)) {
            System.out.printf("Error! The %s is out of field1! Try again:%n", curShip);
            return false;
        } else if (!(((Math.abs(coordinates[0][1] - coordinates[1][1]) + 1) == (Ships.values()[shipCnt].getSize()))
        || ((Math.abs(coordinates[0][0] - coordinates[1][0]) + 1) == (Ships.values()[shipCnt].getSize())))) {
            System.out.printf("Error! Wrong length of the %s! Try again:%n", curShip);
            return false;
        } else if ((coordinates[0][0] != coordinates[1][0]) && (coordinates[0][1] != coordinates[1][1])) {
            System.out.println("Error! Wrong coordinates! Try again:");
            return false;
        } else {
            for (int i = Math.min(coordinates[0][0], coordinates[1][0]); i <= Math.max(coordinates[1][0], coordinates[0][0]); i++) {
                for (int j = Math.min(coordinates[0][1], coordinates[1][1]); j <= Math.max(coordinates[1][1], coordinates[0][1]); j++) {
                    if (devField.field[i][j] == '1') {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int[] convertCoordinate(String coordinate) {
        int coordinateRow = coordinate.substring(0, 1).charAt(0) - 65;
        int coordinateColumn = Integer.parseInt(coordinate.substring(1)) - 1;
        return new int[] {coordinateRow, coordinateColumn};
    }

    public static void placeShip(int[][] coordinates, GameField devField, GameField field, int shipCnt, int[][][] ships) {
        int cnt = 0;
        for (int i = Math.min(coordinates[0][0], coordinates[1][0]); i <= Math.max(coordinates[1][0], coordinates[0][0]); i++) {
            for (int j = Math.min(coordinates[0][1], coordinates[1][1]); j <= Math.max(coordinates[1][1], coordinates[0][1]); j++) {
                ships[shipCnt][cnt] = new int[]{i, j};
                cnt++;
                field.field[i][j] = 'O';
                devField.field[i][j] = 'O';
                if (i - 1 >= 0 && field.field[i - 1][j] != 'O') {
                    devField.field[i - 1][j] = '1';
                }
                if (i + 1 < 10 && field.field[i + 1][j] != 'O') {
                    devField.field[i + 1][j] = '1';
                }
                if (j - 1 >= 0 && field.field[i][j - 1] != 'O') {
                    devField.field[i][j - 1] = '1';
                }
                if (j + 1 < 10 && field.field[i][j + 1] != 'O') {
                    devField.field[i][j + 1] = '1';
                }
            }
        }
        field.print();
    }

    public static void enterPrint(int shipCnt) {
        if (shipCnt < 5) {
            if (shipCnt == 0) {
                curShip = "Aircraft Carrier";
                System.out.printf("Enter the coordinates of the " + curShip + " (%d cells):%n", Ships.values()[shipCnt].getSize());
            } else {
                curShip = Ships.values()[shipCnt].toString().substring(0, 1) + Ships.values()[shipCnt].toString().toLowerCase().substring(1);
                System.out.printf("Enter the coordinates of the " + curShip + " (%d cells):%n", Ships.values()[shipCnt].getSize());
            }
        }
    }

    public static void shootingStage() {
        if (player.equals("Player 1")) {
            fogField2.print();
            System.out.println("---------------------");
            field1.print();
            System.out.println("Player 1, it's your turn:");
            String coordinate = scanner.nextLine();
            boolean gameOver = shot(convertCoordinate(coordinate), field2, fogField2, ships2, shipsShot2);
            if (gameOver) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return;
            }
            System.out.println();
            player = "Player 2";
            shootingStage();
        } else {
            fogField1.print();
            System.out.println("---------------------");
            field2.print();
            System.out.println("Player 2, it's your turn:");
            String coordinate = scanner.nextLine();
            boolean gameOver = shot(convertCoordinate(coordinate), field1, fogField1, ships1, shipsShot1);
            if (gameOver) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return;
            }
            System.out.println();
            player = "Player 1";
            shootingStage();
        }
    }
    public static boolean shot(int[] coordinate, GameField field, GameField fogField, int[][][] ships, boolean[][] shipsShot) {
        if (!(coordinate[0] >= 0 && coordinate[0] < 10 &&
                coordinate[1] >= 0 && coordinate[1] < 10)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            shootingStage();
        } else {
            if (field.field[coordinate[0]][coordinate[1]] == 'O' || field.field[coordinate[0]][coordinate[1]] == 'X') {
                if (field.field[coordinate[0]][coordinate[1]] == 'O') {field.hits++;}
                field.field[coordinate[0]][coordinate[1]] = 'X';
                fogField.field[coordinate[0]][coordinate[1]] = 'X';
                fogField.print();
                if (field.hits == 17) {
                    return true;
                }
                // reduce ship's coordinates
                boolean isFound = false;
                boolean isKilled = true;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < ships[i].length; j++) {
                        if (Arrays.equals(ships[i][j], coordinate)){
                            shipsShot[i][j] = true;
                            isFound = true;
                            break;
                        }
                    }
                    if (isFound) {
                        for (int k = 0; k < ships[i].length; k++) {
                            if (!shipsShot[i][k]) {
                                isKilled = false;
                            }
                        }
                        if (isKilled) { System.out.println("You sank a ship! Specify a new target:"); }
                        else { System.out.println("You hit a ship!"); }
                        System.out.println("Press Enter and pass the move to another player");
                        scanner.nextLine();
                        break;
                    }
                }
            } else {
                field.field[coordinate[0]][coordinate[1]] = 'M';
                fogField.field[coordinate[0]][coordinate[1]] = 'M';
                System.out.println("You missed!");
                System.out.println("Press Enter and pass the move to another player");
                scanner.nextLine();
            }
        }
        return false;
    }
}
