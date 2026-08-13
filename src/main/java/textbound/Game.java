package textbound;

import java.util.Scanner;

public class Game {

    private Scanner scanner;
    private boolean running;

    public Game() {
        scanner = new Scanner(System.in);
        running = true;
    }

    public void start() {

        System.out.println("================================");
        System.out.println("          TEXTBOUND");
        System.out.println("================================");
        System.out.println();
        System.out.println("Welcome to Textbound.");
        System.out.println("Type 'help' to see available commands.");
        System.out.println();

        while (running) {

            System.out.print("> ");

            String input = scanner.nextLine();

            processCommand(input);
        }

        scanner.close();
    }

    private void processCommand(String input) {

        switch (input.toLowerCase()) {

            case "help":
                System.out.println("Available commands:");
                System.out.println("- look");
                System.out.println("- help");
                System.out.println("- quit");
                break;

            case "look":
                System.out.println("You look around, but there isn't much to see yet.");
                break;

            case "quit":
                System.out.println("Thank you for playing Textbound.");
                running = false;
                break;

            default:
                System.out.println("I don't understand that command.");
        }
    }
}
