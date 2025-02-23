package game;

public class Door extends Item{
    private int id;
    private Room currentRoom;
    private Room nextRoom;
    private boolean isLocked;

    private String description;

    // Detailed description that is given when inspected
    private String inspection;

    public Door(Room currentRoom, Room nextRoom ,int id, String description, String inspection, String name) {

        super(name, description, inspection);

        this.currentRoom = currentRoom;
        this.nextRoom = nextRoom;
        this.id = id;
    }

    public void unlockDoor() {
        isLocked = false;
        currentRoom = nextRoom;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void OpenDoor(Room roomToMoveTo) {
        if(!isLocked) {
            this.currentRoom = roomToMoveTo;
            System.out.println("You have moved to a new room!");
        }
    }

    public  void use(){
        this.setUsed(true); // used!
        System.out.println("There is nothing to use.");
    };
}


