package textbound;

public class Main {

    public static void main(String[] args) {

        Room village = new Room(
            "Town of Beginnings",
            "The blackness which floods your psyche gradually splits open, blue flooding yours eyes as numerous crowds of adventurers move about the village square. The smell of fresh bread and the sound of a blacksmith's hammer fill the air, as you take in your surroundings."
        );

        System.out.println(village.getName());
        System.out.println(village.getDescription());
    }
}