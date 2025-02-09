package org;

public class Door {
    private int id;
    private Room currentRoom;
    private boolean isLocked;

    public Door(Room currentRoom, int id) {
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
}
