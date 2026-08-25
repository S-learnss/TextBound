package textbound;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private Room currentRoom;
    private List<Item> inventory;

    public Player(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
    currentRoom = room;
}

    public boolean move(String direction) {
        Room destination = currentRoom.getExit(direction);

        if (destination != null) {
            currentRoom = destination;
            return true;
        }

        return false;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public Item getItem(String name) {

        for (Item item : inventory) {

            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public void clearInventory() {
    inventory.clear();
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
