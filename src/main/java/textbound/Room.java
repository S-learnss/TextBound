package textbound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {

    private String name;
    private String description;
    private Map<String, Room> exits;
    private List<Item> items;
    private Map<String, String> lockedExits;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.lockedExits = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addExit(String direction, Room destination) {
        exits.put(direction, destination);
    }

    public void addLockedExit(
            String direction,
            Room destination,
            String requiredItem) {

        exits.put(direction, destination);
        lockedExits.put(direction, requiredItem);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public boolean isExitLocked(String direction) {
        return lockedExits.containsKey(direction);
    }

    public String getRequiredItem(String direction) {
        return lockedExits.get(direction);
    }

    public String getExitDescription() {
        return String.join(", ", exits.keySet());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Item getItem(String name) {

        for (Item item : items) {

            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;
    }

    public List<Item> getItems() {
        return items;
    }
}
