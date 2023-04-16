package cinema;
import jdk.jfr.Percentage;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    static char[][] cinema;
    static int rows;
    static int columns;
    static int seats;

    static int numOfTickets = 0;
    static int currentIncome = 0;
    static int totalIncome = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        columns = scanner.nextInt();
        seats = rows * columns;

        cinema = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(cinema[i], 'S');
        }
        if (seats <= 60) {
            totalIncome = seats * 10;
        } else {
            totalIncome = rows / 2 * columns * 10 + (rows / 2 + 1) * columns * 8;
        }


        menu();
    }
    public static void menu() {

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> showSeats();
            case 2 -> buyTicket();
            case 3 -> statistics();
            default -> {
                return;
            }
        }
    }

    public static void showSeats() {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= Cinema.cinema[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for (int i = 1; i <= rows; i++) {
            System.out.print(i + " ");
            System.out.print(Arrays.toString(Cinema.cinema[i - 1]).replace("[", "").replace("]", "").replace(",", "") + "\n");
        }
        menu();
    }

    public static void buyTicket() {
        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int column = scanner.nextInt();
        if (row > rows || row <= 0 || column > columns || column <= 0) {
            System.out.println("Wrong input!");
            System.out.println("Please try again.");
            buyTicket();
        } else if (cinema[row - 1][column - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            System.out.println("Please choose a different seat.");
            buyTicket();
        }
        int price;

        if (seats <= 60 || row <= rows / 2) {
            price = 10;
        } else {
            price = 8;
        }

        cinema[row - 1][column - 1] = 'B';
        System.out.println("Ticket price: $" + price);
        currentIncome += price;
        numOfTickets += 1;

        menu();
    }

    public static void statistics() {
        System.out.printf("Number of purchased tickets: %d%n", numOfTickets);
        System.out.printf("Percentage: %.2f%%%n", (float) numOfTickets / (float) seats * 100);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", totalIncome);

        menu();
    }
}