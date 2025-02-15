package game;

public class Door extends Item{
    private int id;
    private Room currentRoom;
    private boolean isLocked;

    private String description;

    // Detailed description that is given when inspected
    private String inspection;

    public Door(Room currentRoom, int id, String description, String inspection, String name) {

        super(name, description, inspection);

        this.currentRoom = currentRoom;
        this.id = id;
    }

    public void unlockDoor() {
        isLocked = false;
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
        System.out.println("There is nothing to use.");
    };
}


