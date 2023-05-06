package machine;

import java.util.Arrays;
import java.util.Scanner;

public class CoffeeMachine {
    static int waterAvailable;
    static int milkAvailable;
    static int beansAvailable;
    static int cupsAvailable;
    static int money;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        waterAvailable = 400;
        milkAvailable = 540;
        beansAvailable = 120;
        cupsAvailable = 9;
        money = 550;

        menu();

    }

    public static void outputSupplies() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water%n", waterAvailable);
        System.out.printf("%d ml of milk%n", milkAvailable);
        System.out.printf("%d ml of coffee beans%n", beansAvailable);
        System.out.printf("%d disposable cups%n", cupsAvailable);
        System.out.printf("$%d of money%n", money);
    }

    public static void menu() {
        StringBuilder[] isEnough = new StringBuilder[2];
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        String action = scanner.next();
        switch (action) {
            case "buy" -> {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                String coffeeChoice = scanner.next();
                switch (coffeeChoice) {
                    case "1" -> {
                        isEnough = isEnoughSupplies(0, 250, 16);
                        if (isEnough[0].toString().equals("true")) {
                            System.out.println("I have enough resources, making you a coffee!");
                            waterAvailable -= 250;
                            beansAvailable -= 16;
                            money += 4;
                            cupsAvailable -= 1;
                        } else {
                            System.out.println(isEnough[1]);
                        }
                        break;
                    }
                    case "2" -> {
                        isEnough = isEnoughSupplies(75, 350, 20);
                        if (isEnough[0].toString().equals("true")) {
                            System.out.println("I have enough resources, making you a coffee!");
                            waterAvailable -= 350;
                            beansAvailable -= 20;
                            milkAvailable -= 75;
                            money += 7;
                            cupsAvailable -= 1;
                        } else {
                            System.out.println(isEnough[1]);
                        }
                        break;
                    }
                    case "3" -> {
                        isEnough = isEnoughSupplies(100, 200, 12);
                        if (isEnough[0].toString().equals("true")) {
                            System.out.println("I have enough resources, making you a coffee!");
                            waterAvailable -= 200;
                            beansAvailable -= 12;
                            milkAvailable -= 100;
                            money += 6;
                            cupsAvailable -= 1;
                        } else {
                            System.out.println(isEnough[1]);
                        }
                        break;
                    }
                    case "back" -> {
                        break;
                    }
                }
                menu();
                break;
            }
            case "fill" -> {
                System.out.println("Write how many ml of water you want to add: ");
                waterAvailable += scanner.nextInt();
                System.out.println("Write how many ml of milk you want to add: ");
                milkAvailable += scanner.nextInt();
                System.out.println("Write how many grams of coffee beans you want to add: ");
                beansAvailable += scanner.nextInt();
                System.out.println("Write how many disposable cups you want to add: ");
                cupsAvailable += scanner.nextInt();
                menu();
                break;
            }
            case "take" -> {
                System.out.printf("I gave you $%d%n", money);
                money = 0;
                menu();
                break;
            }
            case "remaining" -> {
                outputSupplies();
                menu();
                break;
            }
            case "exit" -> {
                break;
            }
        }
    }

    public static StringBuilder[] isEnoughSupplies(int milk, int water, int beans) {
        StringBuilder[] isEnough = new StringBuilder[2];
        isEnough[1] = new StringBuilder("Sorry, not enough");
        Boolean[] supplies = new Boolean[4];
        supplies[0] = water < waterAvailable;
        supplies[1] = milk < milkAvailable;
        supplies[2] = beans < beansAvailable;
        supplies[3] = cupsAvailable >= 1;
        if (!Arrays.asList(supplies).contains(false)) {
            isEnough[0] = new StringBuilder("true");
        } else {
            isEnough[0] = new StringBuilder("false");
            if (!supplies[0]) {
                isEnough[1].append(" water,");
            }
            if (!supplies[1]) {
                isEnough[1].append(" milk,");
            }
            if (!supplies[2]) {
                isEnough[1].append(" coffee beans,");
            }
            if (!supplies[3]) {
                isEnough[1].append(" disposable cups,");
            }
            isEnough[1].deleteCharAt(isEnough[1].length() - 1);
            isEnough[1].append('!');
        }
        return isEnough;
    }
}
