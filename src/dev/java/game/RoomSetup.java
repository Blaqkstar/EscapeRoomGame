package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomSetup {

    public Room setGameRoom(Item item1, Item item2, Item item3, Item item4) {

        Room newRoom = new Room();

        newRoom.setItem(Main.Direction.east, item1);
        newRoom.setItem(Main.Direction.west, item2);
        newRoom.setItem(Main.Direction.north, item3);
        newRoom.setItem(Main.Direction.south, item4);

        return newRoom;
    }


    public Room makeRooms() {
        ////////////////////////////////////////////////////
        //Test code here
        Window itemWindow = new Window("window",
                "A window overlooking a garden. It's too foggy to see very far.",
                "The garden is guarded by a scarecrow with a tattered black hat.");

        Desk itemDesk = new Desk("desk",
                "A desk with a lamp. ",
                "You notice the shape of a hand in the dust on the surface of the desk. Someone has been here.");

        PottedPlant itemPlanet = new PottedPlant("pottedPlant",
                "A plant in a pot. ",
                "You move the potted plant and even dump the dirt out, but nothing of interest here.");

        Bookshelf itemBookshelf = new Bookshelf("bookshelf",
                "A bookshelf filled with books about the occult.",
                "A pungent aura of aged paper and leather pervades the air around the bookshelf. Among the many tomes, several of \n" +
                        "Aleister Crowley's occult works stand out; their dark, worn spines hinting at secrets and mysteries bound within.");

        ArrayList<Item> items = new ArrayList<Item>(){};

        items.add(itemDesk);
        items.add(itemPlanet);
        items.add(itemWindow);
        items.add(itemBookshelf);

        ArrayList<Main.Direction> dirs = new ArrayList<Main.Direction>(){};

        dirs.add(Main.Direction.north);
        dirs.add(Main.Direction.south);
        dirs.add(Main.Direction.east);
        dirs.add(Main.Direction.west);

        Room newRoom = new Room();

        int itemIndex;
        int dirIndex;

        for(int i = 0; i < 4; i++) {

            Random rand = new Random();

            if(!dirs.isEmpty()) {
                itemIndex = rand.nextInt(items.size());
                dirIndex = rand.nextInt(dirs.size());
            }
            else {
                itemIndex = 0;
                dirIndex = 0;
            }
            newRoom.setItem(dirs.get(dirIndex),items.get(itemIndex));

            dirs.remove(dirIndex);
            items.remove(itemIndex);

        }

        return newRoom;

    }
}
