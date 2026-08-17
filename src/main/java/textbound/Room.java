package textbound;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private String name;
    private String description;
    private Map<String, Room> exits;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
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

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitDescription() {
        return String.join(", ", exits.keySet());
    }
}