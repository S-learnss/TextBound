package textbound;

import java.util.Scanner;

public class Game {

    private Player player;
    private boolean running;
    private Room startingRoom;

    public Game() {
        createWorld();
        running = true;
    }

    private void createWorld() {

        // Create rooms
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

        // Connect rooms
        village.addExit("north", crossroads);

        crossroads.addExit("south", village);
        crossroads.addExit("north", forest);
        crossroads.addExit("east", shop);
        crossroads.addExit("west", cave);

        forest.addExit("south", crossroads);
        shop.addExit("west", crossroads);
        cave.addExit("east", crossroads);

        // Create items
        Item coin = new Item(
                "Old Coin",
                "An old silver coin covered in strange markings."
        );

        Item torch = new Item(
                "Torch",
                "A wooden torch that could illuminate a dark place."
        );

        Item key = new Item(
                "Iron Key",
                "A cold iron key with a strange symbol engraved on it."
        );

        // Place items in rooms
        village.addItem(coin);
        forest.addItem(torch);
        shop.addItem(key);

        // Set starting room
        startingRoom = village;

        // Create player
        player = new Player(
                "Adventurer",
                startingRoom
        );
    }

    public void start() {

        Scanner scanner = new Scanner(System.in);

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

        input = input.trim();

        if (input.isEmpty()) {
            return;
        }

        String lowerInput = input.toLowerCase();

        String[] parts = lowerInput.split("\\s+");

        String command = parts[0];

        String argument = "";

        if (parts.length > 1) {
            argument = input.substring(command.length()).trim();
            argument = argument.replaceAll("\\s+", " ");
        }

        switch (command) {

            case "look":

                Room currentRoom = player.getCurrentRoom();

                System.out.println();
                System.out.println(currentRoom.getName());
                System.out.println();
                System.out.println(currentRoom.getDescription());
                System.out.println();

                if (currentRoom.getItems().isEmpty()) {
                    System.out.println("Items: none");
                } else {

                    System.out.println("Items:");

                    for (Item item : currentRoom.getItems()) {
                        System.out.println("- " + item.getName());
                    }
                }

                System.out.println();
                System.out.println(
                        "Exits: " + currentRoom.getExitDescription()
                );
                System.out.println();

                break;

            case "go":

                if (argument.isEmpty()) {
                    System.out.println("Go where?");
                    break;
                }

                boolean moved = player.move(argument);

                if (moved) {

                    System.out.println(
                            "You move " + argument + "."
                    );

                    System.out.println(
                            player.getCurrentRoom().getName()
                    );

                } else {

                    System.out.println(
                            "You cannot go " + argument + "."
                    );
                }

                break;

            case "take":

                if (argument.isEmpty()) {
                    System.out.println("Take what?");
                    break;
                }

                Room roomForTake = player.getCurrentRoom();

                Item itemToTake = roomForTake.getItem(argument);

                if (itemToTake == null) {

                    System.out.println(
                            "There is no " + argument + " here."
                    );

                    break;
                }

                roomForTake.removeItem(itemToTake);

                player.addItem(itemToTake);

                System.out.println(
                        "You picked up the "
                                + itemToTake.getName()
                                + "."
                );

                break;

            case "drop":

                if (argument.isEmpty()) {
                    System.out.println("Drop what?");
                    break;
                }

                Room roomForDrop = player.getCurrentRoom();

                Item itemToDrop = player.getItem(argument);

                if (itemToDrop == null) {

                    System.out.println(
                            "You don't have " + argument + "."
                    );

                    break;
                }

                player.removeItem(itemToDrop);

                roomForDrop.addItem(itemToDrop);

                System.out.println(
                        "You dropped the "
                                + itemToDrop.getName()
                                + "."
                );

                break;

            case "inventory":

                System.out.println();
                System.out.println("Inventory:");

                if (player.getInventory().isEmpty()) {

                    System.out.println("- Empty");

                } else {

                    for (Item item : player.getInventory()) {

                        System.out.println(
                                "- " + item.getName()
                        );
                    }
                }

                System.out.println();

                break;

            case "help":

                System.out.println();
                System.out.println("Available commands:");
                System.out.println("- look");
                System.out.println("- go <direction>");
                System.out.println("- take <item>");
                System.out.println("- drop <item>");
                System.out.println("- inventory");
                System.out.println("- help");
                System.out.println("- quit");
                System.out.println();

                break;

            case "quit":

                running = false;

                System.out.println(
                        "Thanks for playing Textbound!"
                );

                break;

            default:

                System.out.println(
                        "Unknown command. Type 'help' for a list of commands."
                );

                break;
        }
    }
}
