package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomSetup {

    /// Kyle commented this out on 3/1 update because it wasn't being used -- if it had a purpose that I didn't realize please correct me!
//    public Room setGameRoom(Item item1, Item item2, Item item3, Item item4) {
//
//        Room newRoom = new Room();
//
//        newRoom.setItem(Main.Direction.east, item1);
//        newRoom.setItem(Main.Direction.west, item2);
//        newRoom.setItem(Main.Direction.north, item3);
//        newRoom.setItem(Main.Direction.south, item4);
//
//        return newRoom;
//    }

    public Room MakeTutorialRoom() {
        Door exitDoor = null;

        Room tutorialRoom = new Room();

        exitDoor = new Door(tutorialRoom,1, "A wooden door with a rusty handle.",
                "There is a deadbolt that looks like it would accept an old key.", "door");

        tutorialRoom.SetExitDoor(exitDoor);

        Desk itemDesk = new Desk("desk",
                "A desk with a lamp. ",
                "You notice the shape of a hand in the dust on the surface of the desk. Someone has been here.");

        Painting itemPainting = new Painting("painting", "A painting of an old house surrounded by neatly-trimmed hedges, askew and dust-covered from years of neglect.",
                "The painter's signature is inscribed in the corner: 'F.L. Romulus'.", tutorialRoom);

        Lamp itemLamp = new Lamp("lamp", "Judging by the occasional flickering of the bulb, it's on its last leg.",
                "The lamp's once polished brass base now shows signs of tarnish and wear. The bulb flickers intermittently, casting unsettling \n" +
                        "shadows that dance across the room. The switch, slightly loose and worn from years of use, hints at the lamp's frailty. It's as if \n" +
                        "the lamp is holding on by a thread, inviting you to test its resilience one last time.");

        tutorialRoom.setItem(Main.Direction.north, itemPainting);
        tutorialRoom.setItem(Main.Direction.east, itemDesk);
        tutorialRoom.setItem(Main.Direction.east, itemLamp);
        tutorialRoom.setItem(Main.Direction.south, exitDoor);

        AssignRandomItems(tutorialRoom);

        return tutorialRoom;
    }

    // Make Room #2
    // Nothing in here right now
    public Room MakeRoom2(){
        Room room2 = new Room();

        return room2;
    }

    // Method for randomly assigning prebuilt items to walls
    public void AssignRandomItems(Room room) {
        Window itemWindow = new Window("window",
                "A window overlooking a garden. It's too foggy to see very far.",
                "The garden is guarded by a scarecrow with a tattered black hat.");

        PottedPlant itemPlant = new PottedPlant("pottedPlant",
                "A plant in a pot. ",
                "You move the potted plant and even dump the dirt out, but nothing of interest here.");

        Bookshelf itemBookshelf = new Bookshelf("bookshelf",
                "A bookshelf filled with books about the occult.",
                "A pungent aura of aged paper and leather pervades the air around the bookshelf. Among the many tomes, several of \n" +
                        "Aleister Crowley's occult works stand out; their dark, worn spines hinting at secrets and mysteries bound within.");


        ArrayList<Item> items = new ArrayList<Item>() {
        };

        items.add(itemPlant);
        items.add(itemWindow);
        items.add(itemBookshelf);

        ArrayList<Main.Direction> dirs = new ArrayList<Main.Direction>() {
        };

        dirs.add(Main.Direction.north);
        dirs.add(Main.Direction.south);
        dirs.add(Main.Direction.east);
        dirs.add(Main.Direction.west);

        int itemIndex;
        int dirIndex;

        for (int i = 0; i < 3; i++) {

            Random rand = new Random();

            if (!dirs.isEmpty()) {
                itemIndex = rand.nextInt(items.size());
                dirIndex = rand.nextInt(dirs.size());
            } else {
                itemIndex = 0;
                dirIndex = 0;
            }
            room.setItem(dirs.get(dirIndex), items.get(itemIndex));

            dirs.remove(dirIndex);
            items.remove(itemIndex);
        }
    }
}
