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

    /// -------------------------------------------- { TUTORIAL ROOM } ----------------------------
    public Room MakeRoom_TutorialRoom() {
        Door exitDoor = null;

        Room tutorialRoom = new Room();

        exitDoor = new Door(tutorialRoom,1, "a wooden "+ConsoleColors.CYAN+"door"+ConsoleColors.RESET+" with a rusty handle",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": There is a deadbolt that looks like it would accept an old key.", "door");

        tutorialRoom.SetExitDoor(exitDoor);

        Desk itemDesk = new Desk("desk",
                "a "+ConsoleColors.CYAN+"desk"+ConsoleColors.RESET+" with a "+ConsoleColors.CYAN+"lamp"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You notice the shape of a hand in the dust on the surface of the desk. Someone has been here.");

        Painting itemPainting = new Painting("painting", "a "+ConsoleColors.CYAN+"painting"+ConsoleColors.RESET+" of an old house surrounded by neatly-trimmed hedges, askew and dust-covered from years of neglect",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The painter's signature is inscribed in the corner: 'F.L. Romulus'.", tutorialRoom);

        Lamp itemLamp = new Lamp("lamp", "judging by the occasional flickering of the bulb, it's on its last leg",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The lamp's once polished brass base now shows signs of tarnish and wear. The bulb flickers intermittently, casting unsettling \n" +
                        "shadows that dance across the room. The switch, slightly loose and worn from years of use, hints at the lamp's frailty. It's as if \n" +
                        "the lamp is holding on by a thread, inviting you to test its resilience one last time.");

        tutorialRoom.setItem(Main.Direction.north, itemPainting);
        tutorialRoom.setItem(Main.Direction.east, itemDesk);
        tutorialRoom.setItem(Main.Direction.east, itemLamp);
        tutorialRoom.setItem(Main.Direction.south, exitDoor);

        AssignRandomItems(tutorialRoom);

        return tutorialRoom;
    }

    /// -------------------------------------------- { THE CONSERVATORY } ----------------------------
    public Room MakeRoom_Conservatory(){
        /// PLAYER ENTERS FROM THE NORTH!

        Room conservatoryRoom = new Room();
        Door exitDoor = null;
        // creates exit door
        exitDoor = new Door(conservatoryRoom,2, "a steel door with no handle",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You search for a keyhole but find none. It's a smooth, thick, steel plate.", "door");
        // defines SetExitDoor()
        conservatoryRoom.SetExitDoor(exitDoor);

        // creates levers and uses observer pattern to call checkLevers() automatically whenever a lever's position changes
        ///  LEVER A
        Lever leverA = new Lever("lever1", "a lever ("+ConsoleColors.YELLOW + "Lever1" + ConsoleColors.RESET +") with an onyx grip",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The left-most lever.");
        leverA.setPositionalIndicator("The left-most lever. "); // sets left position relative to other levers
        leverA.setInspection(leverA.getPositionalIndicator() + " It's currently in the " + leverA.getPosition() + " position.");
        ///  LEVER B
        Lever leverB = new Lever("lever2", "a lever ("+ConsoleColors.YELLOW + "Lever2" + ConsoleColors.RESET +") with a jade grip",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The middle lever.");
        leverB.setPositionalIndicator("The middle lever. "); // sets center position relative to other levers
        leverB.setInspection(leverB.getPositionalIndicator() + " It's currently in the " + leverB.getPosition() + " position.");
        ///  LEVER C
        Lever leverC = new Lever("lever3", "a lever ("+ConsoleColors.YELLOW + "Lever3" + ConsoleColors.RESET +") with an emerald grip",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The right-most lever.");
        leverC.setPositionalIndicator("The right-most lever. "); // sets right position relative to other levers
        leverC.setInspection(leverC.getPositionalIndicator() + " It's currently in the " + leverC.getPosition() + " position.");

        // registers room as an observer for each lever
        leverA.addObserver(conservatoryRoom);
        leverB.addObserver(conservatoryRoom);
        leverC.addObserver(conservatoryRoom);

        // creates other room props
        LargePortrait portraitPainting = new LargePortrait("portrait", "a large, dusty, "+ConsoleColors.CYAN+"portrait"+ConsoleColors.RESET+" looming imposingly on the wall where the door you just entered through should be",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A grand painting stands before you. Its subject appears to be a shadowy figure cloaked in darkness, their form indistinct save\n" +
                        "for a single glowing eye that seems to pierce your soul. Around them, three stones are suspended in the air, each radiating an otherworldly energy.\n" +
                        "The onyx stone dominates the upper portion of the painting, its dark surface shimmering with an eerie, almost predatory light.\n" +
                        "Below it, the jade and emerald stones rest side by side, their glow subdued as if overshadowed by the onyx. The arrangement feels deliberate, as if\n" +
                        "the stones are locked in a silent, eternal struggle.\n\n" +
                        "A faint inscription at the bottom of the frame reads: \"The void ascends; the others kneel.\"",
                conservatoryRoom);




        // adds items to their respective walls
        conservatoryRoom.setItem(Main.Direction.north, portraitPainting);
        conservatoryRoom.setItem(Main.Direction.west, leverA);
        conservatoryRoom.setItem(Main.Direction.west, leverB);
        conservatoryRoom.setItem(Main.Direction.west, leverC);
        conservatoryRoom.setItem(Main.Direction.east, exitDoor);


        return conservatoryRoom;
    }




    /// -------------------------------------------- { THE LAB } ----------------------------
    public Room MakeRoom_Lab() {
        Door exitDoor = null;

        Room labRoom = new Room();

        List<Item> items = new ArrayList<Item>();

        Sandwich sandwich = new Sandwich("sandwich","a moldy ham sandwich on rye bread",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The sandwich doesn't smell like something you'd eat.");

        items.add(sandwich);

        Transmorgrifier transmorgrifier = new Transmorgrifier("transmorgrifier","An odd machine with a large vat for converting objects",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You notice how odd looking this machine is",labRoom);



        exitDoor = new Door(labRoom,1, "a heavy metal door etched withs strange runes",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": There is a mechanism that looks like it would accept a key.", "door");

        labRoom.SetExitDoor(exitDoor);

        // create items here

        ///  ITEMS WE NEED
        // transmorgrifier: quest item



        // machine attached to a wall. Player doesn't know how to use it (diegetically) until they inspect() on the scientist's research notebook
        // if they try to use transmorg before they read the notebook, they'll fall in and GAME OVER
        // when player uses transmorg, game prompts them to pick an item from the list of observed items we're already tracking

        // scientist's research notebook
        Notebook notebook  = new Notebook("notebook", "a scientist's research notebook", "The notebook documents experiments conducted by" +
                "a scientist experimenting with alchemy. Through the incoherent ramblings about 'proving wrongs his ghastly skeptics', you learn how to use his cunning machine.");


        // deck of cards

        // vat of strange liquid

        // sandwich (consumer functional interface: placing the sandwich into transmorg causes liquid in vat to bubble)
        // placing sandwich into transmorg gives player the flask
        // placing flask into transmorg gives player chunk of lead ore

        // Organic Chemistry textbook

        // bunson burner

        // add items using room.setItem here
        labRoom.setItem(Main.Direction.west, exitDoor);
        labRoom.setItem(Main.Direction.north, notebook );
        labRoom.setItem(Main.Direction.south, transmorgrifier);


        AssignRandomItems(labRoom);

        return labRoom;
    }

    // Method for randomly assigning prebuilt items to walls
    public void AssignRandomItems(Room room) {
        Window itemWindow = new Window("window",
                "a "+ConsoleColors.CYAN+"window"+ConsoleColors.RESET+" overlooking a garden",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": It's too foggy to see very far. What little you're able to make out of the garden appears to be guarded \n" +
                        "by a scarecrow with a tattered black hat.");

        PottedPlant itemPlant = new PottedPlant("plant",
                "a "+ConsoleColors.CYAN+"plant"+ConsoleColors.RESET+" in a pot",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A vibrant plant sits in a polished ceramic pot. Its lush green leaves stretch upward, and the soil looks rich and well-watered.");

        Bookshelf itemBookshelf = new Bookshelf("bookshelf",
                "a "+ConsoleColors.CYAN+"bookshelf"+ConsoleColors.RESET+" filled with books about the occult",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A pungent aura of aged paper and leather pervades the air around the bookshelf. Among the many tomes, several of \n" +
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
