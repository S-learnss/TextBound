package textbound;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveManager {

    private static final String SAVE_FILE = "save.txt";

    public static void save(Player player) {

        try (FileWriter writer = new FileWriter(SAVE_FILE)) {

            writer.write(
                    "room=" + player.getCurrentRoom().getName()
            );

            writer.write(System.lineSeparator());

            writer.write("inventory=");

            for (Item item : player.getInventory()) {

                writer.write(item.getName() + ",");
            }

            writer.write(System.lineSeparator());

            System.out.println("Game saved successfully.");

        } catch (IOException e) {

            System.out.println("Unable to save the game.");

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    public static void load(Game game, Player player) {

        File saveFile = new File(SAVE_FILE);

        if (!saveFile.exists()) {

            System.out.println("No save file found.");

            return;
        }

        try (Scanner scanner = new Scanner(saveFile)) {

            String roomName = "";
            String inventoryData = "";

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                if (line.startsWith("room=")) {

                    roomName = line.substring(5);

                } else if (line.startsWith("inventory=")) {

                    inventoryData = line.substring(10);
                }
            }

            // Find the saved room
            Room savedRoom = game.findRoom(roomName);

            if (savedRoom != null) {

                player.setCurrentRoom(savedRoom);

            } else {

                System.out.println(
                        "Saved room could not be found."
                );

                return;
            }

            // Clear the player's current inventory
            player.clearInventory();

            // Restore saved inventory
            if (!inventoryData.isEmpty()) {

                String[] itemNames =
                        inventoryData.split(",");

                for (String itemName : itemNames) {

                    if (itemName.isEmpty()) {
                        continue;
                    }

                    Item item = game.findItem(itemName);

                    if (item != null) {

                        savedRoom.removeItem(item);

                        player.addItem(item);
                    }
                }
            }

            System.out.println(
                    "Game loaded successfully."
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to load the game."
            );

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}
