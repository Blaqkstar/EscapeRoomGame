package game;

/**
 * Class for the Key object that helps unlock doors.
 */
public class Key extends Item {

    private Room room;

    /**
     * Constructor with parameters for the key class.
     * @param name Takes in a string.
     * @param description Takes in a string.
     * @param inspection Takes in a string.
     * @param room Takes in a room.
     */
    public Key(String name, String description, String inspection,Room room) {
        super(name, description, inspection);
        this.room = room;
    }

    /**
     * Method for when the player uses this key.
     */
    @Override
    public void use() {
        this.setUsed(true); // used!
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You use the key to unlock the door.");
    }
}
