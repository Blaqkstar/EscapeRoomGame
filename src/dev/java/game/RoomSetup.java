package game;

import java.util.List;

public class RoomSetup {

    public Room setGameRoom(Item item1, Item item2, Item item3, Item item4) {

        Room newRoom = new Room();

        newRoom.setItem(Main.Direction.east, item1);
        newRoom.setItem(Main.Direction.west, item2);
        newRoom.setItem(Main.Direction.north, item3);
        newRoom.setItem(Main.Direction.south, item4);

        return newRoom;
    }
}
