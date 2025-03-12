package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomSetup {


    /// -------------------------------------------- { TUTORIAL ROOM } ----------------------------
    public Room MakeRoom_TutorialRoom() {
        Door exitDoor = null;

        Room tutorialRoom = new Room();

        exitDoor = new Door(tutorialRoom,1, "a wooden "+ConsoleColors.CYAN+"door"+ConsoleColors.RESET+" with a rusty handle",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": There is a deadbolt that looks like it would accept an old key.", "door");

        tutorialRoom.SetExitDoor(exitDoor);
        tutorialRoom.setRoomPar(7); // ROOM PAR (7x3=21 turns to beat the room before game over)

        Desk itemDesk = new Desk("desk",
                "a "+ConsoleColors.CYAN+"desk"+ConsoleColors.RESET+" with a "+ConsoleColors.CYAN+"lamp"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You notice the shape of a hand in the dust on the surface of the desk. Someone has been here.");

        Painting itemPainting = new Painting("painting", "a "+ConsoleColors.CYAN+"painting"+ConsoleColors.RESET+" of an old house surrounded by neatly-trimmed hedges, askew and dust-covered from years of neglect",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The painting is a haunting depiction of an old house, its weathered facade looming against a backdrop of stormy skies. The house is surrounded by neatly-trimmed hedges, their precise lines contrasting\n" +
                        "sharply with the chaos of the clouds above. The brushstrokes are meticulous, capturing every detail of the house's cracked windows and sagging porch, but there's something unnerving about the scene - \n" +
                        "something that makes your skin crawl.\n\n" +
                        "The painting hangs askew, its frame thick with dust and cobwebs, as though it hasn't been touched in decades. As you lean closer, you notice faint details you hadn't seen before: shadows in the windows\n" +
                        "and a figure standing in the doorway, barely visible but unmistakably there. The figure's face is obscured, but you can feel its heavy gaze on you.",
                tutorialRoom);

        Lamp itemLamp = new Lamp("lamp", "judging by the flickering of the bulb its on its last leg",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The lamp's once polished brass base shows signs of tarnish and wear. The bulb flickers intermittently, casting unsettling shadows that dance across the room. The switch, slightly loose and worn from years\n" +
                        "of use, hints at the lamp's frailty. It's as if the lamp is holding on by a thread, inviting you to test its resilience one last time.");


        tutorialRoom.setItem(Main.Direction.north, itemPainting);
        tutorialRoom.setItem(Main.Direction.east, itemDesk);
        tutorialRoom.setItem(Main.Direction.east, itemLamp);
        tutorialRoom.setItem(Main.Direction.south, exitDoor);

        return tutorialRoom;
    }

    /// -------------------------------------------- { THE CONSERVATORY } ----------------------------
    public Room MakeRoom_Conservatory(){
        /// PLAYER ENTERS FROM THE NORTH!
        Room conservatoryRoom = new Room();
        Door exitDoor = null;
        // creates exit door
        exitDoor = new Door(conservatoryRoom,2, "a smooth steel "+ConsoleColors.CYAN+"door"+ConsoleColors.RESET+" with no handle",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You search for a keyhole but find none. It's a smooth, thick, steel plate.", "door");
        // defines SetExitDoor()
        conservatoryRoom.SetExitDoor(exitDoor);
        conservatoryRoom.setRoomPar(10); // ROOM PAR (10x3=30 turns to beat the room before game over)
        // creates levers and uses observer pattern to call checkLevers() automatically whenever a lever's position changes
        ///  LEVER A
        Lever leverA = new Lever("lever1", "a lever ("+ConsoleColors.CYAN + "Lever1" + ConsoleColors.RESET +") with an onyx grip",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The left-most lever.");
        leverA.setPositionalIndicator("The left-most lever. "); // sets left position relative to other levers
        leverA.setInspection(leverA.getPositionalIndicator() + " It's currently in the " + leverA.getPosition() + " position.");
        ///  LEVER B
        Lever leverB = new Lever("lever2", "a lever ("+ConsoleColors.CYAN + "Lever2" + ConsoleColors.RESET +") with a jade grip",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The middle lever.");
        leverB.setPositionalIndicator("The middle lever. "); // sets center position relative to other levers
        leverB.setInspection(leverB.getPositionalIndicator() + " It's currently in the " + leverB.getPosition() + " position.");
        ///  LEVER C
        Lever leverC = new Lever("lever3", "a lever ("+ConsoleColors.CYAN + "Lever3" + ConsoleColors.RESET +") with an emerald grip",
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

        Phonograph itemPhonograph = new Phonograph("phonograph",
                "a tarnished antique "+ConsoleColors.CYAN+"phonograph"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The phonograph is a relic of another era, its brass horn tarnished to a dull green and its wooden base cracked with age. The surface is etched with strange, angular symbols that make your\n" +
                        "stomach churn when looking directly at them. A record rests on the turntable, its label faded and illegible, except for a single word scrawled in jagged handwriting: \"Ascension.\"");
        Diary itemDiary = new Diary("diary", "a small, leather-bound "+ConsoleColors.CYAN+"diary"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The diary is a small, leather-bound book, its cover cracked and brittle with age. The pages are yellowed and fragile, filled with neat, precise handwriting that grows increasingly erratic as you read. The\n" +
                        "entries tell the story of a scientist who once occupied this room.\n\n" +
                        "\""+ConsoleColors.BLUE+"Day 1"+ConsoleColors.RESET+": I've finally deciphered the symbols on the levers. There's a pattern here, a logic to this madness. If I can just align them correctly, I might unlock the door. I can feel it. I'm so close.\"\n\n" +
                        "\""+ConsoleColors.BLUE+"Day 7"+ConsoleColors.RESET+": The levers are only part of it. The telescope, the orrery, the phonograph... They're all connected. I've spent hours staring at the stars through that damned telescope, and I swear they're\n" +
                        "watching me back. And that god damned hum... It grows louder every day. I can't sleep...\"\n\n" +
                        "\""+ConsoleColors.BLUE+"Day 14"+ConsoleColors.RESET+": The shadows are moving. I see them out of the corner of my eye, laughing at me. The lantern's light doesn't keep them at bay anymore. I think they're waiting for me to make a mistake.\"\n\n" +
                        "\""+ConsoleColors.BLUE+"Day 21"+ConsoleColors.RESET+": I was wrong. There is no escape. The levers, the telescope, the damned orrery, that cursed phonograph; they aren't puzzles. They're traps. The shadows are closer now, whispering things I\n" +
                        "can't understand. I can't do this anymore.\"\n\n" +
                        "The final entry is scrawled in a shaky hand, the ink smeared as though written in haste: \"I'm so sorry. I can't hold on. The shadows are here, inside me. Forgive me... \"");
        Corpse itemCorpse = new Corpse("corpse", "a mostly-decomposed "+ConsoleColors.CYAN+"corpse"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The corpse is slumped against the wall, its skeletal frame draped in the tattered remains of a lab coat. The bones are brittle and discolored, the flesh long-since decayed, but the posture is\n" +
                        "unmistakable - head bowed, hands clutching a rusted scalpel embedded in its chest. The air around the body is cold, and the shadows seem to gather here, thicker and darker than elsewhere in the room.\n" +
                        "A faint, almost imperceptible hum lingers in the air, as if the scientist's despair has left an indelible mark on this place.");


        // adds items to their respective walls
        conservatoryRoom.setItem(Main.Direction.north, portraitPainting);
        conservatoryRoom.setItem(Main.Direction.south, itemPhonograph);
        conservatoryRoom.setItem(Main.Direction.south, itemCorpse);
        conservatoryRoom.setItem(Main.Direction.south, itemDiary);
        conservatoryRoom.setItem(Main.Direction.west, leverA);
        conservatoryRoom.setItem(Main.Direction.west, leverB);
        conservatoryRoom.setItem(Main.Direction.west, leverC);
        conservatoryRoom.setItem(Main.Direction.east, exitDoor);

        conservatoryRoom.setIntroBlurb(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": The air here is cold and still, carrying the faint scent of ozone and old parchment. The conservatory feels like a cathedral to the unknown, its domed\n" +
                "ceiling lost in shadow, save for faint pinpricks of light that might be stars. The walls are lined with shelves, their contents obscured by layers of dust and time,\n" +
                "and the floor is a mosaic of cracked tiles, their patterns swirling in ways that make your head ache if you stare too long. A low hum fills the air, unsettling in\n" +
                "the way it seems to reverberate through your body. This room feels alive with some ancient, dormant power. You can't shake the feeling that this place was not built\n" +
                "to study the stars, but to commune with something far, far older.");

        return conservatoryRoom;
    }

    /// -------------------------------------------- { THE LAB } ----------------------------
    public Room MakeRoom_Lab() {
        Door exitDoor = null;

        Room labRoom = new Room();

        ArrayList<Item> items = new ArrayList<Item>();

        labRoom.setRoomPar(10); // ROOM PAR (10x3=30 turns to beat the room before game over)

        // machine attached to a wall. Player doesn't know how to use it (diegetically) until they inspect() on the scientist's research notebook
        // if they try to use transmorg before they read the notebook, they'll fall in and GAME OVER
        // when player uses transmorg, game prompts them to pick an item from the list of observed items we're already tracking
        Transmorgrifier transmorgrifier = new Transmorgrifier("transmorgrifier","an odd " +ConsoleColors.CYAN+"machine"+ConsoleColors.RESET +" with many knobs, levers, and buttons",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A small screen at the center of the "+ConsoleColors.CYAN+"machine"+ConsoleColors.RESET + " is covered with oily fingerprints.",labRoom);


        Sandwich sandwich = new Sandwich("sandwich","a moldy ham " +ConsoleColors.CYAN+"sandwich"+ConsoleColors.RESET +" on rye bread",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The "+ConsoleColors.CYAN+"sandwich"+ConsoleColors.RESET + " doesn't smell like something you'd eat.", transmorgrifier, labRoom);

        exitDoor = new Door(labRoom,1, "a heavy metal " +ConsoleColors.CYAN+"door"+ConsoleColors.RESET +" etched withs strange runes",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": There is a mechanism that looks like it would accept a " +ConsoleColors.CYAN+"key."+ConsoleColors.RESET, "door");

        labRoom.SetExitDoor(exitDoor);

        // scientist's research notebook
        Notebook notebook  = new Notebook("notebook", "a scientist's research " +ConsoleColors.CYAN+"notebook"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The "+ConsoleColors.CYAN+"notebook"+ConsoleColors.RESET + " documents experiments conducted by" +
                " a scientist experimenting with alchemy. You perceive that you could use it to learn his ways.", transmorgrifier);

        Flask flask = new Flask("flask","a clear crystal " +ConsoleColors.CYAN+"flask"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": You notice the flask has a tangy smell to it.", transmorgrifier, labRoom);

        // deck of cards
        DeckOfCards deckOfCards = new DeckOfCards("cards","a deck of " +ConsoleColors.CYAN+"cards"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": Stained with signs of use, the deck may have been used by lab workers to pass the time.", transmorgrifier, labRoom);

        // vat of strange liquid
        VatOfLiquid vatOfLiquid = new VatOfLiquid("vat", "a " +ConsoleColors.CYAN+"vat"+ConsoleColors.RESET +" of green of liquid",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The liquid bubbles ominously and smells awful.");

        Thermometer thermometer = new Thermometer("thermometer", "a dusty " +ConsoleColors.CYAN+"thermometer"+ConsoleColors.RESET +" sitting in a cup",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The "+ConsoleColors.CYAN+"thermometer"+ConsoleColors.RESET + "'s red mercury reads at a level much higher than the \n"+
                "temperature of the room. It must be broken.", transmorgrifier, labRoom);

        items.add(sandwich);
        items.add(flask);
        items.add(deckOfCards);
        items.add(thermometer);
        transmorgrifier.setItemsToTransmorgrify(items);

        // add items using room.setItem here
        labRoom.setItem(Main.Direction.west, exitDoor);
        labRoom.setItem(Main.Direction.north, notebook );
        labRoom.setItem(Main.Direction.north, sandwich);
        labRoom.setItem(Main.Direction.east, deckOfCards);
        labRoom.setItem(Main.Direction.south, vatOfLiquid);
        labRoom.setItem(Main.Direction.east, thermometer);
        labRoom.setItem(Main.Direction.north, flask);
        labRoom.setItem(Main.Direction.south, transmorgrifier);

        labRoom.setIntroBlurb(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                ": Dust hangs in the air as you enter the lab. Scientific instruments that have clearly gone unused for a long time are haphazardly arranged on tables. \n"+
                "You hear the sound of bubbling liquid and the whirring of machines. You are careful as you step slowly forward, making sure to dodge the broken glass \n"+
                "and strange chemicals littered around the floor. The walls are lined with mold that stretches up to the ceiling. The room feels wicked and dangerous, as if a \n"+
                "single wrong step could cause you to join the decay.");

        AssignRandomItems(labRoom);

        return labRoom;
    }

    public Room MakeRoom_FinalRoom() {
        Door exitDoor = null;
        Door westDoor = null;
        Door northDoor = null;
        Door southDoor = null;

        Room finalRoom = new Room();

        ArrayList<Item> items = new ArrayList<Item>();

        finalRoom.setRoomPar(5); // ROOM PAR (5x3=15 turns to beat the room before game over)

        exitDoor = new Door(finalRoom, 1,
                "The mirror-like surface of the " + ConsoleColors.CYAN + "door" + ConsoleColors.RESET + " glows faintly, its surface fractured like cracked glass. As you step closer, the image within begins to take shape — a\n" +
                        "moment you’ve tried to suppress. You see yourself at a crossroads, your hand clenched around an envelope containing words you never delivered. A shadowed figure stands before you, their face unreadable, as you\n" +
                        "turn away and crumple the envelope into your pocket. The sound of footsteps receding and the weight of missed opportunity linger like an ache in your chest. The door’s surface seems to shift, almost imploring\n" +
                        "you to face the choices you made — and the ones you didn’t.",
                ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": A faint outline of a keyhole becomes visible in the fractured surface.",
                "door");


        finalRoom.SetExitDoor(exitDoor);

        westDoor = new Door(finalRoom, 2,
                "The mirror-like surface of the " + ConsoleColors.CYAN + "door" + ConsoleColors.RESET + " shimmers faintly, its surface rippling like liquid silver. As you approach, the image sharpens into a scene from your\n" +
                        "past — a memory you’ve tried to bury. Your childhood friend lies in a hospital bed, their face pale and still, as doctors scramble frantically around them. The steady beep of the heart monitor falters, then\n" +
                        "flat-lines, and the sound echoes in your mind like a hammer striking an anvil. The door’s surface seems to pulse faintly, as though it’s alive, and you can’t shake the feeling that it’s not just showing you the\n" +
                        "past — it’s pulling you back into it.",
                ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": A faint outline of a keyhole becomes visible in the fractured surface.",
                "eastDoor");

        northDoor = new Door(finalRoom, 2,
                "The mirror-like surface of the " + ConsoleColors.CYAN + "door" + ConsoleColors.RESET + " glimmers with an unnatural light, drawing you closer despite yourself. The image that forms is one you’ve tried to\n" +
                        "forget: the person you loved, their face twisted in anger and sorrow, storming out after a bitter fight. You see yourself crumple to the ground, your hands clutching at empty air as the door slams shut behind them.\n" +
                        "The memory is so vivid you can almost feel the cold floor beneath your knees and the ache in your chest. The door’s surface seems to ripple, as though it’s inviting you to step through and relive the moment — or perhaps\n" +
                        "to change it.",
                ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": A faint outline of a keyhole becomes visible in the fractured surface.",
                "northDoor");

        southDoor = new Door(finalRoom, 2,
                "The mirror-like surface of the " + ConsoleColors.CYAN + "door" + ConsoleColors.RESET + " flickers faintly, its surface shifting like smoke. As you watch, the image resolves into a scene that haunts you still: a woman\n" +
                        "stands at your doorstep, her face streaked with tears. She begs you for a ride to the hospital, her voice trembling with desperation, but you refuse. The memory shifts, showing her son in the back of an ambulance,\n" +
                        "his small body lifeless as the paramedics work in vain. The door’s surface seems to hum faintly, as though it’s feeding on your guilt, and you can’t help but wonder if this is a memory — or a warning.",
                ConsoleColors.GREEN + "PERCEPTION" + ConsoleColors.RESET + ": A faint outline of a keyhole becomes visible in the fractured surface.",
                "southDoor");

        Key key = new Key("key","a mirror-like "+ConsoleColors.CYAN+"key"+ConsoleColors.RESET,"The key feels unnaturally cold in your hand, its surface shimmering faintly as if it exists just beyond the edge of reality.", finalRoom);
        key.setObserved(true);

        finalRoom.setItem(Main.Direction.south, key);
        finalRoom.setItem(Main.Direction.west,westDoor);
        finalRoom.setItem(Main.Direction.north,northDoor);
        finalRoom.setItem(Main.Direction.south,southDoor);
        finalRoom.setItem(Main.Direction.east,exitDoor);


        /// room should contain an intro blurb that spits out when player enters. This can be like the Consequences room where that blurb is the start of dialog
        finalRoom.setIntroBlurb(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": The air in this room is thick and heavy, carrying the distinct scent of ozone and decay. Four doors loom before you, one on each wall, containing more of the strange etching you've\n" +
                "seen elsewhere here. The walls are lined with dirt-caked tiles, and the, now all-too-familiar, low hum fills the air, its profane frequencies vibrating through your essence.");

        return finalRoom;
    }


    public Room MakeRoom_TheEnd(boolean rightDoor) {

        Room theEndRoom = new Room();
        theEndRoom.setRoomPar(1000); // ROOM PAR (1000x3=3000 turns to beat the room before game over). Ensuring that player doesn't receive a checkScore() here

        if(rightDoor) {
            theEndRoom.setIntroBlurb(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": You step through the door into a blinding light and realize you are lying in a bed. After a minute you can see and realize that your in a hospital and your body is in extreme pain." +
                    " You are no longer sure what is reality and what is your mind playing tricks. You're in so much pain you start to think you'd rather go back, but that voice still reverberates in your mind." +
                    "You start to shake with fear as your mind starts losing function again and you slip into unconsciousness.");
        }
        else {
            theEndRoom.setIntroBlurb(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                    ": You hear evil laughter and immediately are filled with the most intense fear you've ever felt. You feel this disembodied presence press towards you with evil glee and realize you are doomed!");
        }

        return theEndRoom;
    }

    /// -------------------------------------------- { CONSEQUENCES ROOM } ----------------------------
    public Room MakeRoom_Consequences() {
        Room consequences = new Room();
        consequences.setIntroBlurb(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": As you step through the door, you find yourself in a vast, endless void. The room behind dissolves into nothingness. The air is cold and still.\n\n" +
                ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+
                ": The void stretches endlessly in all directions, a sea of darkness so profound that it feels as though it might swallow you whole. There is no light, no sound, no sense of time or space - only the\n" +
                "faint, lingering memory of the rooms you left behind. The air is heavy, oppressive, as if the void itself is alive and watching.");
        consequences.setRoomPar(1000); // ROOM PAR (1000x3=3000 turns to beat the room before game over). Ensuring that player doesn't receive a checkScore() here

        return consequences;
    }


    // Method for randomly assigning prebuilt items to walls
    public void AssignRandomItems(Room room) {
        ArrayList<Item> items = new ArrayList<Item>(); // items will be added to this array for randomizing placement
        ArrayList<Main.Direction> dirs = new ArrayList<Main.Direction>(); // placeholder array for directions
        // adds directional refs to placeholder
        dirs.add(Main.Direction.north);
        dirs.add(Main.Direction.south);
        dirs.add(Main.Direction.east);
        dirs.add(Main.Direction.west);

        /// builds out items
        // Tutorial Room stuff
        Window itemWindow = new Window("window",
                "a "+ConsoleColors.CYAN+"window"+ConsoleColors.RESET+" overlooking a garden",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The window is streaked with rain, the glass fogged and cold to the touch. Beyond it, the fog churns - tendrils curling and twisting, defying the wind like something alive.\n" +
                        "Occasionally, you think you see shapes moving within - tall, slender shapes with too many limbs, or perhaps it's just the play of light and shadow.");

        PottedPlant itemPlant = new PottedPlant("plant",
                "a "+ConsoleColors.CYAN+"plant"+ConsoleColors.RESET+" in a pot",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A vibrant plant sits in a polished ceramic pot. Its lush green leaves stretch upward, and the soil looks rich and well-watered.");

        Bookshelf itemBookshelf = new Bookshelf("bookshelf",
                "a "+ConsoleColors.CYAN+"bookshelf"+ConsoleColors.RESET+" filled with books about the occult",
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A pungent aura of aged paper and leather pervades the air around the bookshelf. Among the many tomes, several of \n" +
                        "Aleister Crowley's occult works stand out; their dark, worn spines hinting at secrets and mysteries bound within.");
        // Conservatory stuff
        Lantern itemLantern = new Lantern("lantern", "a rusty "+ConsoleColors.CYAN+"lantern"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The lantern is an antique, its brass fittings tarnished and its glass panes cracked with age. The fuel inside glows faintly, even when unlit, with a color that defies description - something\n" +
                        "between blue and violet, but not quite either. The handle is cold to the touch, and when you hold it, you feel a faint vibration.");
        Orrery itemOrrery = new Orrery("orrery", "an old mechanical "+ConsoleColors.CYAN+"orrery"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The orrery is an intricate model of a solar system, its brass framework tarnished with age but still gleaming faintly in the dim light. The planets are crafted with meticulous detail, their surfaces\n" +
                        "etched with faint, angular symbols like the others you've seen since you arrived. The solar system has fourteen planets, their orbits intersecting like nothing you've ever seen. The model seems to be \n" +
                        "depicting a reality far-removed from your own. The crank on the side feels cold to the touch.");
        Telescope itemTelescope = new Telescope("telescope", "a large, ornately-carved "+ConsoleColors.CYAN+"telescope"+ConsoleColors.RESET,
                ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+
                        ": The telescope is a masterpiece of craftsmanship, its brass body adorned with intricate carvings of celestial bodies and angular, runic symbols. The eyepiece is cold to the touch and, when you peer through\n" +
                        "it, the stars outside seem closer, brighter. The carvings along the barrel depict scenes of astronomers gazing into the void, their faces twisted in expressions of awe and terror.");


        /// TODO: NEED TO PREBAKE CONSTRUCTORS FOR SOME ADDITIONAL TUTORIAL ROOM ITEMS AS WELL AS ITEMS FOR OTHER ROOMS. MAYBE AIM FOR >= 5 NON-QUEST PROPS IN EACH ROOM??
        /// WE CAN BUILD THEM OUT ABOVE AND THEN ADD THEM TO THEIR RESPECTIVE ROOMS BELOW

        //  items added is dependent upon the room being passed into params
        if (room.getName().equalsIgnoreCase("Tutorial Room")) {
            items.add(itemPlant);
            items.add(itemWindow);
            items.add(itemBookshelf);
            randomizePropSpawnDirection(room, items, dirs);
        } else if (room.getName().equalsIgnoreCase("The Conservatory")) {
            // adds relevant room items
            ///  for some reason, exitDoor cannot be found if less than three items are added to items. Worth looking into if we have time but not gamebreaking as long as we have
           ///  enough room items randomly spawning in
            items.add(itemOrrery);
            items.add(itemLantern);
            items.add(itemTelescope);
            randomizePropSpawnDirection(room, items, dirs);
        } else if (room.getName().equalsIgnoreCase("The Lab")) {
            // adds relevant room items
        }

    }
    private void randomizePropSpawnDirection(Room room, ArrayList<Item> items, ArrayList<Main.Direction> dirs) {
        // picks a random dir and assigns a random item from items to it
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
