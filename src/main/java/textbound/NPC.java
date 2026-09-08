package textbound;

public class NPC {

    private String name;
    private String description;
    private String dialogue;
    private Room room;

    public NPC(
            String name,
            String description,
            String dialogue,
            Room room) {

        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDialogue() {
        return dialogue;
    }

    public Room getRoom() {
        return room;
    }
}

