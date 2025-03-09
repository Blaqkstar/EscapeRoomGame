package game;

public class Key extends Item {

    private Room room;

    public Key(String name, String description, String inspection,Room room) {
        super(name, description, inspection);
        this.room = room;
    }

    @Override
    public void use() {
        this.setUsed(true); // used!
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You use the key to unlock the door.");
    }
}
