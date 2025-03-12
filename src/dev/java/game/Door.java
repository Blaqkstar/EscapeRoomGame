package game;

/**
 * Class for our door logic which allows players to move between rooms
 */
public class Door extends Item{
    private int id;
    private Room currentRoom;
    private Room nextRoom;
    private boolean isLocked = true;
    private boolean lockedForever = false;

    private String description;

    // Detailed description that is given when inspected
    private String inspection;

    /**
     * Constructor with parameters for Door.
     * @param currentRoom Takes in a room.
     * @param id Takes in an Integer.
     * @param description Takes in a string.
     * @param inspection Takes in a string.
     * @param name Takes in a string.
     */
    public Door(Room currentRoom, int id, String description, String inspection, String name) {

        super(name, description, inspection);

        this.currentRoom = currentRoom;
        this.nextRoom = nextRoom;
        this.id = id;
    }

    /**
     * Method to unlock the door.
     */
    public void unlockDoor() {
        isLocked = false;

    }

    /**
     * Method to see if a door is locked.
     * @return Returns a boolean.
     */
    public boolean getIsLocked() {
        return isLocked;
    }

    /**
     * Method to see if a door is locked forever.
     * @return Returns a boolean.
     */
    public boolean getLockedForever() {
        return lockedForever;
    }

    /**
     * Method to set whether a door is locked forever.
     * @param lockedForever Takes in a boolean.
     */
    public void setLockedForever(boolean lockedForever) {
        this.lockedForever = lockedForever;
    }

    /**
     * Method to open a door once it's unlocked.
     * @param roomToMoveTo Takes in a room.
     */
    public void OpenDoor(Room roomToMoveTo) {
        if(!isLocked) {
            currentRoom = nextRoom;
            this.currentRoom = roomToMoveTo;
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You have moved to a new room!");
        }
    }
    int counter = 0;

    /**
     * Use method for when someone uses the door
     */
    public  void use(){
        this.setUsed(true); // used!
        if (this.currentRoom.getName().equalsIgnoreCase("Tutorial Room")) {
            counter++;
            if (counter >= 5) {
                // door is broken and can no longer be opened
                if (!this.lockedForever) {
                    this.setLockedForever(true); // locks forever
                }
                if (counter == 5) {
                    System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": The door seems to glaze over. You can sense it's grown dormant and will no longer open.");
                } else {
                    System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": Nothing happens.");
                }

            } else if (counter == 4) {
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": The door appears to have sunken in on itself.");
            } else if (counter == 3) {
                // door leaks a strange liquid
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": A strange pool of glowing liquid slowly forms beneath the door.");
            } else {
                System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": The door groans lightly.");
            }
        } else {
            System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": You can't use it like that.");
        }
    }
}


