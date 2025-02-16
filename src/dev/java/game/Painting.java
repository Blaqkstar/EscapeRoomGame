package game;

public class Painting extends Item {
    private Room room;

    public Painting(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        System.out.println("You straighten the painting on its hangar and a key tumbles out from behind the frame!");

        // creates a new instance of Key
        Item key = new Key("key", "A rusty old key", "The key appears to be quite old. The shine of the metal has dulled with oxidation.");
        key.setObserved(true); // sets key as observed
        room.setItem(Main.Direction.north, key); // adds key to the north
    }
}
