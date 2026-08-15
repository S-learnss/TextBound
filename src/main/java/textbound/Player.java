package textbound;

public class Player {

    private String name;
    private Room currentRoom;

    public Player(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public boolean move(String direction) {
        Room destination = currentRoom.getExit(direction);

        if (destination != null) {
            currentRoom = destination;
            return true;
        }

        return false;
    }
}
