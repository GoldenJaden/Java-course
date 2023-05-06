package bullscows;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String code;
    static int turn = 0;

    static String symbols;

    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        String codeLenRaw = scanner.next();
        if (!codeLenRaw.matches("[+-]?\\d*(\\.\\d+)?")) {
            System.out.printf("Error: \"%s\" isn't a valid number.", codeLenRaw);
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        String maxSymbolsRaw = scanner.next();
        if (!maxSymbolsRaw.matches("[+-]?\\d*(\\.\\d+)?")) {
            System.out.printf("Error: \"%s\" isn't a valid number.", maxSymbolsRaw);
            return;
        }
        int codeLen = Integer.parseInt(codeLenRaw);
        int maxSymbols = Integer.parseInt(maxSymbolsRaw);
        if (codeLen == 0 || maxSymbols == 0) {
            System.out.println("Error: the number of symbols and the length of the code cannot be zero.");
        } else  if (codeLen > 36 || maxSymbols > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits (the maximum is 36).%n", codeLen);
        } else if (maxSymbols < codeLen) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols", codeLen, maxSymbols);
        }else {
            symbols = generateSymbols(maxSymbols);
            code = generateCode(codeLen);
            System.out.print("The secret code is prepared: ");
            for (int i = 0; i < code.length(); i++) {
                System.out.print('*');
            }
            if (maxSymbols <= 10) {
                System.out.printf(" (0-%c)%n", symbols.charAt(maxSymbols - 1));
            }
            else  if (maxSymbols == 11){
                System.out.printf(" (0-9, a)%n", symbols.charAt(maxSymbols - 1));
            } else {
                System.out.printf(" (0-9, a-%c)%n", symbols.charAt(maxSymbols - 1));
            }
            System.out.println("Okay, let's start a game!");
            game();
        }

    }
    public static String generateCode(int codeLen) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        while (code.length() != codeLen) {
            int randomIndex = random.nextInt(symbols.length());
            if (code.indexOf(String.valueOf(symbols.charAt(randomIndex))) == -1) {
                code.append(symbols.charAt(randomIndex));
            }
        }
        return String.valueOf(code);
    }

    public static String generateSymbols(int maxSymbols){
        StringBuilder symbols = new StringBuilder();
        char ch = '0';
        while (symbols.length() != maxSymbols) {
            symbols.append(ch);
            if (ch == '9') {
                ch += 40;
            }
            else {
                ch += 1;
            }
        }
        return String.valueOf(symbols);
    }

    public static void game() {
        turn += 1;
        System.out.printf("Turn %d:%n", turn);
        int cows = 0;
        int bulls = 0;
        String codeGuess = scanner.next();
        if (codeGuess.length() == code.length()) {
            for (int i = 0; i < code.length(); i++) {
                if (code.contains(String.valueOf(codeGuess.charAt(i)))) {
                    if (codeGuess.charAt(i) == code.charAt(i)) {
                        bulls += 1;
                    } else {
                        cows += 1;
                    }
                }
            }
            if (cows != 0 && bulls != 0) {
                System.out.printf("Grade: %d bull(s) and %d cow(s).%n", bulls, cows);
                game();
            } else if (bulls == code.length()) {
                System.out.printf("Grade: %d bulls(s)%n", code.length());
                System.out.println("Congratulations! You guessed the secret code.");
            } else if (cows != 0) {
                System.out.printf("Grade: %d cow(s).%n", cows);
                game();
            } else if (bulls != 0) {
                System.out.printf("Grade: %d bull(s).%n", bulls);
                game();
            } else {
                System.out.printf("Grade: None.%n");
                game();
            }
        } else {
            System.out.println("Error: your guess should be the length of the code.");
        }
    }
}
