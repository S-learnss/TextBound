package textbound;

import java.util.Scanner;

public class Game {

    private Scanner scanner;
    private boolean running;

    private Player player;
    private Room startingRoom;

    public Game() {
        scanner = new Scanner(System.in);
        running = true;

        createWorld();
    }

    private void createWorld() {

        Room village = new Room(
                "Village Square",
                "You stand in the center of a quiet village."
        );

        Room forest = new Room(
                "Dark Forest",
                "Tall trees surround you on every side."
        );

        village.addExit("north", forest);
        forest.addExit("south", village);

        startingRoom = village;

        player = new Player("Adventurer", startingRoom);
    }

    public void start() {

        System.out.println("================================");
        System.out.println("          TEXTBOUND");
        System.out.println("================================");
        System.out.println();
        System.out.println("Welcome to Textbound.");
        System.out.println();

        while (running) {

            System.out.print("> ");

            String input = scanner.nextLine();

            processCommand(input);
        }

        scanner.close();
    }

   private void processCommand(String input) {

    String[] parts = input.toLowerCase().split(" ");

    String command = parts[0];

    switch (command) {

        case "help":
            System.out.println("Available commands:");
            System.out.println("- look");
            System.out.println("- go <direction>");
            System.out.println("- help");
            System.out.println("- quit");
            break;

        case "look":
            System.out.println(player.getCurrentRoom().getName());
            System.out.println(
                    player.getCurrentRoom().getDescription()
            );
            break;

        case "go":

            if (parts.length < 2) {
                System.out.println("Go where?");
                break;
            }

            String direction = parts[1];

            boolean moved = player.move(direction);

            if (moved) {
                System.out.println(
                        "You move " + direction + "."
                );

                System.out.println(
                        player.getCurrentRoom().getName()
                );
            } else {
                System.out.println(
                        "You cannot go " + direction + "."
                );
            }

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