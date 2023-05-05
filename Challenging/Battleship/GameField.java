package battleship;

import java.util.Arrays;

public class GameField {
    public char[][] field = new char[10][10];
    int hits = 0;
    public GameField() {
        for (int i = 0; i < 10; i++) {
            Arrays.fill(this.field[i], '~');
        }
    }

    public void print() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char curLetter = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.println(curLetter + " " + Arrays.toString(this.field[i]).replace(",", "").replace("[", "").replace("]", ""));
            curLetter++;
        }
        System.out.println();
    }
}