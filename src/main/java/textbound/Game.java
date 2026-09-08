package textbound;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Game {

    private Player player;
    private boolean running;
    private Room startingRoom;
    private List<Room> rooms;

    public Game() {

        rooms = new ArrayList<>();

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

        Room lockedArea = new Room(
                "Sealed Chamber",
                "A mysterious chamber lies beyond the ancient iron door."
        );

        // Add all rooms to the room list
        rooms.add(village);
        rooms.add(crossroads);
        rooms.add(forest);
        rooms.add(shop);
        rooms.add(cave);
        rooms.add(lockedArea);

        // Connect rooms
        village.addExit("north", crossroads);

        crossroads.addExit("south", village);
        crossroads.addExit("north", forest);
        crossroads.addExit("east", shop);
        crossroads.addExit("west", cave);

        forest.addExit("south", crossroads);

        shop.addExit("west", crossroads);

        cave.addExit("east", crossroads);

        // The chamber is locked and requires the Iron Key
        cave.addLockedExit(
                "north",
                lockedArea,
                "Iron Key"
        );

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

    public Room findRoom(String roomName) {

        for (Room room : rooms) {

            if (room.getName().equalsIgnoreCase(roomName)) {
                return room;
            }
        }

        return null;
    }

    public Item findItem(String itemName) {

        for (Room room : rooms) {

            Item item = room.getItem(itemName);

            if (item != null) {
                return item;
            }
        }

        return null;
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

                        System.out.println(
                                "- " + item.getName()
                        );
                    }
                }

                System.out.println();

                System.out.println(
                        "Exits: " +
                        currentRoom.getExitDescription()
                );

                System.out.println();

                break;

            case "go":

                if (argument.isEmpty()) {

                    System.out.println("Go where?");

                    break;
                }

                Room current = player.getCurrentRoom();

                // Check whether the requested exit exists
                Room destination = current.getExit(argument);

                if (destination == null) {

                    System.out.println(
                            "You cannot go " + argument + "."
                    );

                    break;
                }

                // The cave requires a Torch to explore
                if (current.getName().equalsIgnoreCase("Abandoned Cave")
                        && argument.equalsIgnoreCase("north")) {

                    Item torch = player.getItem("Torch");

                    if (torch == null) {

                        System.out.println();
                        System.out.println(
                                "The cave is too dark to continue."
                        );

                        System.out.println(
                                "You need a Torch to see your way forward."
                        );

                        System.out.println();

                        break;
                    }

                    System.out.println();
                    System.out.println(
                            "You raise the Torch and illuminate the passage."
                    );

                    System.out.println(
                            "You can now see the way forward."
                    );

                    System.out.println();
                }

                // Check whether the exit is locked
                if (current.isExitLocked(argument)) {

                    String requiredItem =
                            current.getRequiredItem(argument);

                    Item keyItem =
                            player.getItem(requiredItem);

                    if (keyItem == null) {

                        System.out.println();
                        System.out.println(
                                "The way is locked."
                        );

                        System.out.println(
                                "You need the " +
                                requiredItem +
                                "."
                        );

                        System.out.println();

                        break;
                    }

                    System.out.println();
                    System.out.println(
                            "You use the " +
                            requiredItem +
                            "."
                    );

                    System.out.println(
                            "The lock clicks open."
                    );

                    System.out.println();
                }

                player.setCurrentRoom(destination);

                System.out.println(
                        "You move " + argument + "."
                );

                System.out.println(
                        player.getCurrentRoom().getName()
                );

                // Check whether the player has completed the game
                checkWinCondition();

                break;

            case "take":

                if (argument.isEmpty()) {

                    System.out.println("Take what?");

                    break;
                }

                Room roomForTake = player.getCurrentRoom();

                Item itemToTake =
                        roomForTake.getItem(argument);

                if (itemToTake == null) {

                    System.out.println(
                            "There is no " +
                            argument +
                            " here."
                    );

                    break;
                }

                roomForTake.removeItem(itemToTake);

                player.addItem(itemToTake);

                System.out.println(
                        "You picked up the " +
                        itemToTake.getName() +
                        "."
                );

                break;

            case "drop":

                if (argument.isEmpty()) {

                    System.out.println("Drop what?");

                    break;
                }

                Room roomForDrop =
                        player.getCurrentRoom();

                Item itemToDrop =
                        player.getItem(argument);

                if (itemToDrop == null) {

                    System.out.println(
                            "You don't have " +
                            argument +
                            "."
                    );

                    break;
                }

                player.removeItem(itemToDrop);

                roomForDrop.addItem(itemToDrop);

                System.out.println(
                        "You dropped the " +
                        itemToDrop.getName() +
                        "."
                );

                break;

            case "inventory":

                System.out.println();
                System.out.println("Inventory:");

                if (player.getInventory().isEmpty()) {

                    System.out.println("- Empty");

                } else {

                    for (Item item :
                            player.getInventory()) {

                        System.out.println(
                                "- " + item.getName()
                        );
                    }
                }

                System.out.println();

                break;

            case "save":

                SaveManager.save(player);

                break;

            case "load":

                SaveManager.load(this, player);

                break;

            case "help":

                System.out.println();
                System.out.println("Available commands:");
                System.out.println("- look");
                System.out.println("- go <direction>");
                System.out.println("- take <item>");
                System.out.println("- drop <item>");
                System.out.println("- inventory");
                System.out.println("- save");
                System.out.println("- load");
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
                        "Unknown command. " +
                        "Type 'help' for a list of commands."
                );

                break;
        }
    }

    private void checkWinCondition() {

        if (player.getCurrentRoom().getName()
                .equalsIgnoreCase("Sealed Chamber")) {

            Item coin = player.getItem("Old Coin");

            if (coin != null) {

                System.out.println();
                System.out.println("================================");
                System.out.println("          VICTORY!");
                System.out.println("================================");
                System.out.println();
                System.out.println(
                        "You place the Old Coin upon the"
                );
                System.out.println(
                        "ancient stone pedestal."
                );
                System.out.println();
                System.out.println(
                        "The markings upon the coin begin"
                );
                System.out.println(
                        "to glow."
                );
                System.out.println();
                System.out.println(
                        "The chamber awakens."
                );
                System.out.println();
                System.out.println(
                        "You have uncovered the mystery"
                );
                System.out.println(
                        "hidden beneath the village."
                );
                System.out.println();
                System.out.println("You have completed Textbound!");
                System.out.println();

                running = false;
            }
        }
    }
}

