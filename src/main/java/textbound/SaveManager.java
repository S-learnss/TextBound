package textbound;

import java.io.FileWriter;
import java.io.IOException;

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
}