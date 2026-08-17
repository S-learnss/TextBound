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

    Room crossroads = new Room(
            "Crossroads",
            "Four paths meet here beneath an old wooden sign."
    );

    Room forest = new Room(
            "Dark Forest",
            "Tall trees surround you on every side."
    );

    Room shop = new Room(
            "Old Shop",
            "Dusty shelves line the walls of this abandoned shop."
    );

    Room cave = new Room(
            "Abandoned Cave",
            "A cold, dark cave stretches deep into the earth."
    );

    village.addExit("north", crossroads);

    crossroads.addExit("south", village);
    crossroads.addExit("north", forest);
    crossroads.addExit("east", shop);
    crossroads.addExit("west", cave);

    forest.addExit("south", crossroads);
    shop.addExit("west", crossroads);
    cave.addExit("east", crossroads);

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

    Room currentRoom = player.getCurrentRoom();

    System.out.println();
    System.out.println(currentRoom.getName());
    System.out.println();
    System.out.println(currentRoom.getDescription());
    System.out.println();
    System.out.println("Exits: " + currentRoom.getExitDescription());
    System.out.println();

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