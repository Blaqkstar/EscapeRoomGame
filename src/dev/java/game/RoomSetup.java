package game;

import java.util.ArrayList;
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

    /// -------------------------------------------- { TUTORIAL ROOM } ----------------------------
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

    /// -------------------------------------------- { CONSERVATORY ROOM } ----------------------------
    public Room MakeConservatoryRoom(){
        Room conservatoryRoom = new Room();
        // for the moment, this room will contain the same items as tutorial room
        // mechanically, maybe this could be more of a MYST style puzzle. No key, but some kind of config trigger that results in the door unlocking
        // three levers??

        Door exitDoor = null;

        // creates exit door
        exitDoor = new Door(conservatoryRoom,2, "A steel door with no handle.",
                "You search for a keyhole but find none. It's a smooth, thick, steel plate.", "door");

        // defines SetExitDoor()
        conservatoryRoom.SetExitDoor(exitDoor);

        // creates levers. Need to figure out a way to distinguish between them for player input
        ///  LEVER A
        Lever leverA = new Lever("lever1", "An ancient, mechanical lever ("+ConsoleColors.YELLOW + "Lever1" + ConsoleColors.RESET +").",
                "The left-most lever.");
        leverA.setPositionalIndicator("The left-most lever. "); // sets left position relative to other levers
        leverA.setInspection(leverA.getPositionalIndicator() + " It's currently in the " + leverA.getPosition() + " position.");
        ///  LEVER B
        Lever leverB = new Lever("lever2", "An ancient, mechanical lever ("+ConsoleColors.YELLOW + "Lever2" + ConsoleColors.RESET +").",
                "The middle lever.");
        leverB.setPositionalIndicator("The middle lever. "); // sets center position relative to other levers
        leverB.setInspection(leverB.getPositionalIndicator() + " It's currently in the " + leverB.getPosition() + " position.");
        ///  LEVER C
        Lever leverC = new Lever("lever3", "An ancient, mechanical lever ("+ConsoleColors.YELLOW + "Lever3" + ConsoleColors.RESET +").",
                "The right-most lever.");
        leverC.setPositionalIndicator("The right-most lever. "); // sets right position relative to other levers
        leverC.setInspection(leverC.getPositionalIndicator() + " It's currently in the " + leverC.getPosition() + " position.");


        // adds items to their respective walls
        conservatoryRoom.setItem(Main.Direction.west, leverA);
        conservatoryRoom.setItem(Main.Direction.west, leverB);
        conservatoryRoom.setItem(Main.Direction.west, leverC);
        conservatoryRoom.setItem(Main.Direction.east, exitDoor);

        //AssignRandomItems(tutorialRoom);

        return conservatoryRoom;
    }

    // Method for randomly assigning prebuilt items to walls
    public void AssignRandomItems(Room room) {
        Window itemWindow = new Window("window",
                "A window overlooking a garden. It's too foggy to see very far.",
                "The garden is guarded by a scarecrow with a tattered black hat.");

        PottedPlant itemPlant = new PottedPlant("plant",
                "A plant in a pot.",
                "A vibrant plant sits in a polished ceramic pot. Its lush green leaves stretch upward, and the soil looks rich and well-watered.");

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
